package com.software.demo.services;

import com.software.demo.config.ReglasNegocioConfig;
import com.software.demo.entities.Asignatura;
import com.software.demo.entities.Estudiante;
import com.software.demo.entities.Inscripcion;
import com.software.demo.repositories.AsignaturaRepository;
import com.software.demo.repositories.InscripcionRepository;
import com.software.demo.utils.CalendarioUtils;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private AsignaturaService asignaturaService;

    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    @Autowired
    private ReglasNegocioConfig rulesConfig;

    private static final double NOTA_MINIMA_PRERREQ = 3.0;

    public List<Inscripcion> findAll() {
        return inscripcionRepository.findAll();
    }

    public Optional<Inscripcion> findById(Long id) {
        return inscripcionRepository.findById(id);
    }

    @Transactional
    public Inscripcion inscribirAsignatura(Long estudianteId, Long asignaturaId, String operador) {
        Estudiante estudiante = estudianteService.findById(estudianteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

        Asignatura asignatura = asignaturaService.findById(asignaturaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asignatura no encontrada"));

        // RN03 - Estado académico
        if (!"ACTIVO".equalsIgnoreCase(estudiante.getEstado())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo estudiantes con estado ACTIVO pueden inscribirse");
        }

        // RN02 - Límite de cupos (Lo hacemos más específico para que Angular lo detecte)
        if (asignatura.getCupoActual() >= asignatura.getCupoMaximo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cupo máximo alcanzado");
        }

        // RN01 - Prerrequisitos: verificar que haya aprobado cada prerrequisito con nota >= 3.0
        for (Asignatura prereq : asignatura.getPrerrequisitos()) {
            boolean ok = historialAcademicoService.hasApproved(estudianteId, prereq.getId(), NOTA_MINIMA_PRERREQ);
            if (!ok) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No ha aprobado prerrequisito: " + prereq.getNombre());
            }
        }

        // Regla de límite de inscripciones activas por estudiante
        List<Inscripcion> activas = inscripcionRepository.findByEstudianteIdAndEstado(estudianteId, "ACTIVA");
        if (activas.size() >= rulesConfig.getMaxActiveEnrollments()) {
            // 🚨 MEJORA: Incluir el número en el mensaje.
            String limite = String.valueOf(rulesConfig.getMaxActiveEnrollments());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ha alcanzado el límite máximo de " + limite + " inscripciones activas");
        }

        // RN04 - Conflictos de horario (verificación básica)
        if (hasScheduleConflict(estudianteId, asignatura)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflicto de horario con otra asignatura inscrita");
        }

        boolean alreadyActive = inscripcionRepository.existsByEstudianteIdAndAsignaturaIdAndEstado(estudianteId, asignaturaId, "ACTIVA");
        if (alreadyActive) {
            // Código y mensaje perfectos para Angular
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya está inscrito en esta asignatura");
        }

        int filasAfectadas = asignaturaRepository.incrementCupoIfAvailable(asignaturaId);
        if (filasAfectadas == 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No hay cupo disponible para esta asignatura");
        }

        Inscripcion inscripcion = new Inscripcion(estudiante, asignatura, operador);
        Inscripcion guardada = inscripcionRepository.save(inscripcion);

        auditLogService.log("INSCRIPCION_CREADA", operador,
            "Estudiante " + estudiante.getNombre() + " inscrito en " + asignatura.getNombre());

        return guardada;
    }

    private boolean hasScheduleConflict(Long estudianteId, Asignatura newAsignatura) {
        if (newAsignatura.getHorario() == null || newAsignatura.getHorario().isBlank()) return false;

        List<Inscripcion> activas = inscripcionRepository.findByEstudianteIdAndEstado(estudianteId, "ACTIVA");

        // Parsear los horarios del nuevo curso
        List<CalendarioUtils.TimeSlot> slotsNuevo = CalendarioUtils.parseHorario(newAsignatura.getHorario());

        for (Inscripcion ins : activas) {
            Asignatura a = ins.getAsignatura();
            if (a != null && a.getHorario() != null) {
                List<CalendarioUtils.TimeSlot> slotsExistentes = CalendarioUtils.parseHorario(a.getHorario());
                if (CalendarioUtils.hasConflict(slotsExistentes, slotsNuevo)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public void cancelarInscripcion(Long inscripcionId, String operador) {
        Inscripcion ins = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada"));

        if ("CANCELADA".equalsIgnoreCase(ins.getEstado())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inscripción ya cancelada");
        }

        // Validar periodo de cancelación
        long diasDesdeInscripcion = ChronoUnit.DAYS.between(ins.getFechaInscripcion(), LocalDate.now());
        if (diasDesdeInscripcion > rulesConfig.getCancelationDays()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No se puede cancelar: periodo de cancelación vencido");
        }

        // Marcar cancelada, registrar fecha y auditoría
        ins.setEstado("CANCELADA");
        ins.setFechaCancelacion(LocalDate.now());
        ins.setOperadorAudit(operador);
        ins.setAuditTimestamp(java.time.LocalDateTime.now());
        inscripcionRepository.save(ins);

        // Liberar cupo de forma atómica
        asignaturaRepository.decrementCupoIfPossible(ins.getAsignatura().getId());

        // Registrar auditoría
        auditLogService.log("INSCRIPCION_CANCELADA", operador,
                "Inscripción " + ins.getId() + " cancelada para estudiante " + ins.getEstudiante().getNombre());
    }

    public void deleteById(Long id) {
        inscripcionRepository.deleteById(id);
    }

    public List<Inscripcion> findInscripcionesActivas(Long estudianteId) {
        return inscripcionRepository.findByEstudianteIdAndEstado(estudianteId, "ACTIVA");
    }
}
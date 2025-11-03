package com.software.demo.controllers;

import com.itextpdf.text.DocumentException;
import com.software.demo.entities.Inscripcion;
import com.software.demo.repositories.InscripcionRepository;
import com.software.demo.services.PDFGeneratorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "http://localhost:4200")
public class HorarioController {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;
    
    @Autowired
    private InscripcionRepository inscripcionRepository;

    @GetMapping("/pdf/{estudianteId}") // 👈 Endpoint llamado desde Angular
    public ResponseEntity<byte[]> generarHorarioPdf(@PathVariable Long estudianteId) {
        try {
            // 1. Obtener la data del horario (Solo inscripciones ACTIVA)
            List<Inscripcion> inscripcionesActivas = inscripcionRepository.findByEstudianteIdAndEstado(estudianteId, "ACTIVA");
            
            // 2. Generar el PDF
            byte[] pdfBytes = pdfGeneratorService.generarHorario(inscripcionesActivas);

            // 3. Devolver la respuesta como PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // Esto le dice al navegador que descargue el archivo
            headers.setContentDispositionFormData("filename", "horario_activo_" + estudianteId + ".pdf");
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (DocumentException e) {
            // Error específico de la librería iText
            // logger.error("Error iText al generar PDF:", e); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Captura cualquier otro error (ej. NullPointer)
            // logger.error("Error general al generar PDF:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

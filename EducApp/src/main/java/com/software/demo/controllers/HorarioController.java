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

    @GetMapping("/pdf/{estudianteId}") 
    public ResponseEntity<byte[]> generarHorarioPdf(@PathVariable Long estudianteId) {
        try {
            List<Inscripcion> inscripcionesActivas = inscripcionRepository.findByEstudianteIdAndEstado(estudianteId, "ACTIVA");
            
            byte[] pdfBytes = pdfGeneratorService.generarHorario(inscripcionesActivas);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "horario_activo_" + estudianteId + ".pdf");
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (DocumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

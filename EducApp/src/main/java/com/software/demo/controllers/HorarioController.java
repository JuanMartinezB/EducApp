package com.software.demo.controllers;

import com.software.demo.services.InscripcionService;
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
    private InscripcionService inscripcionService;

    // 🚨 CORRECCIÓN: Devuelve ResponseEntity<byte[]> para archivos binarios
    @GetMapping("/pdf/{estudianteId}")
    public ResponseEntity<byte[]> generarPDF(@PathVariable Long estudianteId) {
        
        // 1. Llama al servicio para generar el PDF (devuelve byte[])
        byte[] pdfBytes = inscripcionService.generarHorarioPDF(estudianteId);

        // 2. Configura las cabeceras HTTP
        HttpHeaders headers = new HttpHeaders();
        
        // Indica que el contenido es un PDF
        headers.setContentType(MediaType.APPLICATION_PDF);
        
        // Fuerza la descarga y le da un nombre al archivo
        String filename = "horario_estudiante_" + estudianteId + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        
        // Indica el tamaño del archivo
        headers.setContentLength(pdfBytes.length);

        // 3. Devuelve la respuesta con los bytes, las cabeceras y el estado OK
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}

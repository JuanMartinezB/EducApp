package com.software.demo.services;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.software.demo.entities.Inscripcion;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PDFGeneratorService {

    public byte[] generarHorario(List<Inscripcion> inscripciones) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // El escritor de PDF apunta al flujo de bytes en memoria
        PdfWriter.getInstance(document, outputStream);
        document.open();
        
        // Definir la fuente para el título
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLUE);
        
        // Título del Documento
        Paragraph titulo = new Paragraph("Horario de Clases Activas", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(15);
        document.add(titulo);

        // --- Contenido del Horario ---
        
        if (inscripciones.isEmpty()) {
            document.add(new Paragraph("No hay asignaturas activas inscritas en este momento."));
        } else {
            // Asumiendo que todas las inscripciones son del mismo estudiante
            document.add(new Paragraph("Estudiante: " + inscripciones.get(0).getEstudiante().getNombre()));
            document.add(new Paragraph("Período: 2025-II"));
            document.add(Chunk.NEWLINE);

            // Iterar sobre las asignaturas activas para listarlas
            for (Inscripcion inscripcion : inscripciones) {
                // Comprobaciones de Nulos Críticas (CAUSA FRECUENTE DEL ERROR 500)
                if (inscripcion.getAsignatura() == null) continue; 
                
                String nombreAsignatura = inscripcion.getAsignatura().getNombre();
                String horario = inscripcion.getAsignatura().getHorario(); 
                
                document.add(new Paragraph("• Asignatura: " + nombreAsignatura, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                document.add(new Paragraph("  Horario: " + (horario != null ? horario : "No Definido")));
                document.add(new Paragraph("  Créditos: " + inscripcion.getAsignatura().getCreditos()));
                document.add(Chunk.NEWLINE);
            }
        }

        document.close();
        return outputStream.toByteArray();
    }
}

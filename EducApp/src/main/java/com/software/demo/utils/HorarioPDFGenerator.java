package com.software.demo.utils;

import com.software.demo.entities.Inscripcion;
import com.lowagie.text.*; 
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class HorarioPDFGenerator {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static byte[] generarPDF(String nombreEstudiante, List<Inscripcion> inscripcionesActivas) {
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // Inicialización del documento
            Document document = new Document(PageSize.LETTER);
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. Título y Encabezado
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new java.awt.Color(0, 0, 150));
            Paragraph title = new Paragraph("Horario Académico", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            document.add(new Paragraph(" ")); 
            
            Font fontSubtitle = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Estudiante: " + nombreEstudiante, fontSubtitle));
            document.add(new Paragraph("Fecha: " + java.time.LocalDate.now().toString(), fontSubtitle));
            document.add(new Paragraph(" "));

            // 2. Tabla del Horario
            // 4 columnas: Asignatura, Código, Horario, Fecha Inscripción
            PdfPTable table = new PdfPTable(4); 
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            
            // Estilos para encabezados
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new java.awt.Color(255, 255, 255));
            java.awt.Color headerColor = new java.awt.Color(50, 50, 150);
            
            String[] headers = {"ASIGNATURA", "CÓDIGO", "HORARIO", "FECHA INSCRIPCIÓN"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontHeader));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // 3. Contenido de la Tabla
            Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 10);
            for (Inscripcion ins : inscripcionesActivas) {
                
                // Asignatura
                table.addCell(new Phrase(ins.getAsignatura().getNombre(), fontBody));
                
                // Código
                table.addCell(new Phrase(ins.getAsignatura().getCodigo(), fontBody));
                
                // Horario
                table.addCell(new Phrase(ins.getAsignatura().getHorario() != null ? ins.getAsignatura().getHorario() : "-", fontBody));

                // Fecha Inscripción
                table.addCell(new Phrase(DF.format(ins.getFechaInscripcion()), fontBody));
            }

            document.add(table);
            
            document.close();
            
        } catch (DocumentException e) {
            // Manejo de errores de PDF
            throw new RuntimeException("Error al generar el PDF: " + e.getMessage(), e);
        }

        // Retorna el array de bytes
        return out.toByteArray();
    }
}

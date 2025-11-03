package com.software.demo.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarioUtils {

    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");

    public static class TimeSlot {
        public final String day; // LUN, MAR, MIE, JUE, VIE, SAB, DOM
        public final LocalTime start;
        public final LocalTime end;

        public TimeSlot(String day, LocalTime start, LocalTime end) {
            this.day = day.toUpperCase();
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Parsea un string de horario como:
     * "LUN 08:00-10:00" o múltiples separados por ';'
     * Ejemplo: "LUN 08:00-10:00; MIE 14:00-16:00"
     */
    public static List<TimeSlot> parseHorario(String horario) {
        List<TimeSlot> slots = new ArrayList<>();
        if (horario == null || horario.isBlank()) return slots;
        String[] parts = horario.split(";");
        for (String p : parts) {
            p = p.trim();
            if (p.isEmpty()) continue;
            // formato esperado "DIA HH:mm-HH:mm"
            String[] tokens = p.split("\\s+", 2);
            if (tokens.length < 2) continue;
            String dia = tokens[0].trim();
            String horas = tokens[1].trim();
            String[] he = horas.split("-");
            if (he.length != 2) continue;
            try {
                LocalTime start = LocalTime.parse(he[0].trim(), TF);
                LocalTime end = LocalTime.parse(he[1].trim(), TF);
                slots.add(new TimeSlot(dia, start, end));
            } catch (Exception ex) {
                // ignorar slot mal formado
            }
        }
        return slots;
    }

    public static boolean overlaps(TimeSlot a, TimeSlot b) {
        if (!a.day.equalsIgnoreCase(b.day)) return false;
        // solapan si startA < endB y startB < endA
        return a.start.isBefore(b.end) && b.start.isBefore(a.end);
    }

    public static boolean hasConflict(List<TimeSlot> existing, List<TimeSlot> candidate) {
        for (TimeSlot c : candidate) {
            for (TimeSlot e : existing) {
                if (overlaps(e, c)) return true;
            }
        }
        return false;
    }
}

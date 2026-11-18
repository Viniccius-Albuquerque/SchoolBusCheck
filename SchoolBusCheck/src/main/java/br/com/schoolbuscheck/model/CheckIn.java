package br.com.schoolbuscheck.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record CheckIn(String ra, String studentName, LocalDate date, LocalTime time) {
    public CheckIn {
        if (ra == null || ra.isBlank()) throw new IllegalArgumentException("RA inválido.");
        if (studentName == null || studentName.isBlank()) throw new IllegalArgumentException("Nome inválido.");
        if (date == null || time == null) throw new IllegalArgumentException("Data e horário são obrigatórios.");
    }
}

package br.com.schoolbuscheck.model;

public record Student(String ra, String name) {
    public Student {
        if (ra == null || ra.isBlank()) throw new IllegalArgumentException("O RA é obrigatório.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("O nome é obrigatório.");
        ra = ra.trim();
        name = name.trim();
    }
}

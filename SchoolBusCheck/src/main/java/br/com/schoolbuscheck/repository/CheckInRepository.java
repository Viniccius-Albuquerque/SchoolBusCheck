package br.com.schoolbuscheck.repository;

import br.com.schoolbuscheck.model.CheckIn;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface CheckInRepository {
    void save(CheckIn checkIn);
    boolean existsForStudentOnDate(String ra, LocalDate date);
    List<CheckIn> findByDate(LocalDate date);
    Set<LocalDate> findAllDates();
}

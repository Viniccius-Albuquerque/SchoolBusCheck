package br.com.schoolbuscheck.facade;

import br.com.schoolbuscheck.model.CheckIn;
import br.com.schoolbuscheck.service.CheckInService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class SchoolBusFacade {
    private final CheckInService service;

    public SchoolBusFacade(CheckInService service) {
        this.service = service;
    }

    public CheckIn registerStudent(String ra, String name) {
        return service.registerStudent(ra, name);
    }

    public Set<LocalDate> getAvailableDates() {
        return service.getAvailableDates();
    }

    public List<CheckIn> getCheckInsByDate(LocalDate date) {
        return service.getCheckInsByDate(date);
    }
}

package br.com.schoolbuscheck.service;

import br.com.schoolbuscheck.factory.CheckInFactory;
import br.com.schoolbuscheck.model.CheckIn;
import br.com.schoolbuscheck.model.Student;
import br.com.schoolbuscheck.observer.CheckInNotifier;
import br.com.schoolbuscheck.repository.CheckInRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class CheckInService {
    private final CheckInRepository repository;
    private final CheckInFactory factory;
    private final CheckInNotifier notifier;

    public CheckInService(CheckInRepository repository, CheckInNotifier notifier) {
        this(repository, notifier, Clock.systemDefaultZone());
    }

    public CheckInService(CheckInRepository repository, CheckInNotifier notifier, Clock clock) {
        this.repository = repository;
        this.notifier = notifier;
        this.factory = new CheckInFactory(clock);
    }

    public CheckIn registerStudent(String ra, String name) {
        Student student = new Student(ra, name);
        CheckIn checkIn = factory.createForToday(student);
        if (repository.existsForStudentOnDate(student.ra(), checkIn.date())) {
            throw new IllegalStateException("Este aluno já possui entrada registrada hoje.");
        }

        repository.save(checkIn);
        notifier.notifyRegistered(checkIn);
        return checkIn;
    }

    public Set<LocalDate> getAvailableDates() {
        return repository.findAllDates();
    }

    public List<CheckIn> getCheckInsByDate(LocalDate date) {
        return repository.findByDate(date);
    }
}

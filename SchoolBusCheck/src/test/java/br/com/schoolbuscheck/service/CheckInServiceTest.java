package br.com.schoolbuscheck.service;

import br.com.schoolbuscheck.model.CheckIn;
import br.com.schoolbuscheck.observer.CheckInNotifier;
import br.com.schoolbuscheck.repository.CheckInRepository;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CheckInServiceTest {
    @Test
    void deveRegistrarAlunoComDataAtual() {
        LocalDate fixedDate = LocalDate.of(2026, 11, 17);
        Clock clock = Clock.fixed(fixedDate.atTime(8, 15).atZone(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault());
        FakeRepository repository = new FakeRepository();
        CheckInService service = new CheckInService(repository, new CheckInNotifier(), clock);

        CheckIn result = service.registerStudent("12345", "Ana Silva");

        assertEquals("12345", result.ra());
        assertEquals("Ana Silva", result.studentName());
        assertEquals(fixedDate, result.date());
        assertEquals(LocalTime.of(8, 15), result.time());
        assertEquals(1, repository.records.size());
    }

    @Test
    void naoDevePermitirDuasEntradasDoMesmoRaNoMesmoDia() {
        FakeRepository repository = new FakeRepository();
        Clock clock = Clock.systemDefaultZone();
        CheckInService service = new CheckInService(repository, new CheckInNotifier(), clock);

        service.registerStudent("12345", "Ana Silva");

        assertThrows(IllegalStateException.class,
                () -> service.registerStudent("12345", "Ana Silva"));
        assertEquals(1, repository.records.size());
    }

    private static class FakeRepository implements CheckInRepository {
        private final List<CheckIn> records = new ArrayList<>();

        public void save(CheckIn checkIn) { records.add(checkIn); }

        public boolean existsForStudentOnDate(String ra, LocalDate date) {
            return records.stream().anyMatch(c -> c.ra().equalsIgnoreCase(ra) && c.date().equals(date));
        }

        public List<CheckIn> findByDate(LocalDate date) {
            return records.stream().filter(c -> c.date().equals(date)).toList();
        }

        public Set<LocalDate> findAllDates() {
            Set<LocalDate> dates = new HashSet<>();
            records.forEach(c -> dates.add(c.date()));
            return dates;
        }
    }
}

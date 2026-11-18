package br.com.schoolbuscheck.factory;

import br.com.schoolbuscheck.model.CheckIn;
import br.com.schoolbuscheck.model.Student;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public class CheckInFactory {
    private final Clock clock;

    public CheckInFactory(Clock clock) {
        this.clock = clock;
    }

    public CheckIn createForToday(Student student) {
        return new CheckIn(
                student.ra(),
                student.name(),
                LocalDate.now(clock),
                LocalTime.now(clock).withNano(0)
        );
    }
}

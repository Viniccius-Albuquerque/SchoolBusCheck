package br.com.schoolbuscheck.observer;

import br.com.schoolbuscheck.model.CheckIn;

public class ConsoleCheckInListener implements CheckInListener {
    @Override
    public void onCheckInRegistered(CheckIn checkIn) {
        System.out.println("\n[AVISO] Entrada registrada para " + checkIn.studentName() + ".");
    }
}

package br.com.schoolbuscheck.observer;

import br.com.schoolbuscheck.model.CheckIn;

public interface CheckInListener {
    void onCheckInRegistered(CheckIn checkIn);
}

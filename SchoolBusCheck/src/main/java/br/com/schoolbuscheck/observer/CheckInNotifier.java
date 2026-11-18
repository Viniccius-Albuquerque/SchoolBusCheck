package br.com.schoolbuscheck.observer;

import br.com.schoolbuscheck.model.CheckIn;

import java.util.ArrayList;
import java.util.List;

public class CheckInNotifier {
    private final List<CheckInListener> listeners = new ArrayList<>();

    public void addListener(CheckInListener listener) {
        listeners.add(listener);
    }

    public void notifyRegistered(CheckIn checkIn) {
        for (CheckInListener listener : listeners) {
            listener.onCheckInRegistered(checkIn);
        }
    }
}

package br.com.schoolbuscheck;

import br.com.schoolbuscheck.facade.SchoolBusFacade;
import br.com.schoolbuscheck.observer.CheckInNotifier;
import br.com.schoolbuscheck.observer.ConsoleCheckInListener;
import br.com.schoolbuscheck.repository.JsonCheckInRepository;
import br.com.schoolbuscheck.service.CheckInService;
import br.com.schoolbuscheck.ui.TerminalMenu;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        var repository = new JsonCheckInRepository(Path.of("dados", "registros.json"));
        var notifier = new CheckInNotifier();
        notifier.addListener(new ConsoleCheckInListener());

        var service = new CheckInService(repository, notifier);
        var facade = new SchoolBusFacade(service);
        new TerminalMenu(facade).start();
    }
}

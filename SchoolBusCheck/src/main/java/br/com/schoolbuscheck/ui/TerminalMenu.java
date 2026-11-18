package br.com.schoolbuscheck.ui;

import br.com.schoolbuscheck.facade.SchoolBusFacade;
import br.com.schoolbuscheck.model.CheckIn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalMenu {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final SchoolBusFacade facade;
    private final Scanner scanner = new Scanner(System.in);

    public TerminalMenu(SchoolBusFacade facade) {
        this.facade = facade;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n====================================");
            System.out.println("          SCHOOL BUS CHECK");
            System.out.println("====================================");
            System.out.println("1 - Área do Aluno");
            System.out.println("2 - Área Administrativa");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> studentMenu();
                case "2" -> adminMenu();
                case "0" -> running = false;
                default -> System.out.println("Opção inválida.");
            }
        }
        System.out.println("Sistema encerrado.");
    }

    private void studentMenu() {
        System.out.println("\n--- ENTRADA DO ALUNO ---");
        System.out.print("Informe seu RA: ");
        String ra = scanner.nextLine();
        System.out.print("Informe seu nome: ");
        String name = scanner.nextLine();

        try {
            CheckIn checkIn = facade.registerStudent(ra, name);
            System.out.println("\nENTRADA CONFIRMADA!");
            System.out.println("Aluno: " + checkIn.studentName());
            System.out.println("RA: " + checkIn.ra());
            System.out.println("Data: " + checkIn.date().format(DATE_FORMAT));
            System.out.println("Horário: " + checkIn.time());
            System.out.println("Você entrou no ônibus escolar.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("\nNão foi possível registrar: " + e.getMessage());
        }
    }

    private void adminMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- ÁREA ADMINISTRATIVA ---");
            List<LocalDate> dates = new ArrayList<>(facade.getAvailableDates());
            if (dates.isEmpty()) {
                System.out.println("Ainda não existem entradas registradas.");
                return;
            }

            System.out.println("Datas com registros:");
            for (int i = 0; i < dates.size(); i++) {
                System.out.printf("%d - %s%n", i + 1, dates.get(i).format(DATE_FORMAT));
            }
            System.out.println("0 - Voltar");
            System.out.print("Selecione uma data: ");
            String option = scanner.nextLine().trim();

            if (option.equals("0")) {
                back = true;
                continue;
            }

            try {
                int index = Integer.parseInt(option) - 1;
                if (index < 0 || index >= dates.size()) {
                    System.out.println("Data inválida.");
                    continue;
                }
                showDailyList(dates.get(index));
            } catch (NumberFormatException e) {
                System.out.println("Digite o número de uma das datas.");
            }
        }
    }

    private void showDailyList(LocalDate date) {
        List<CheckIn> records = facade.getCheckInsByDate(date);
        System.out.println("\nENTRADAS DO DIA " + date.format(DATE_FORMAT));
        System.out.println("------------------------------------");
        if (records.isEmpty()) {
            System.out.println("Nenhum aluno registrado nesta data.");
            return;
        }
        for (int i = 0; i < records.size(); i++) {
            CheckIn record = records.get(i);
            System.out.printf("%d. %s | RA: %s | Horário: %s%n",
                    i + 1, record.studentName(), record.ra(), record.time());
        }
        System.out.println("------------------------------------");
        System.out.println("Total de alunos: " + records.size());
        System.out.println("Pressione ENTER para voltar.");
        scanner.nextLine();
    }
}

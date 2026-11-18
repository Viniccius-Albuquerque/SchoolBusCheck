package br.com.schoolbuscheck.repository;

import br.com.schoolbuscheck.model.CheckIn;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JsonCheckInRepository implements CheckInRepository {
    private final Path file;
    private final Gson gson;
    private final Type listType = new TypeToken<List<CheckIn>>() {}.getType();

    public JsonCheckInRepository(Path file) {
        this.file = file;
        this.gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(java.time.LocalTime.class, new LocalTimeAdapter())
                .create();
    }

    @Override
    public synchronized void save(CheckIn checkIn) {
        List<CheckIn> records = readAll();
        records.add(checkIn);
        writeAll(records);
    }

    @Override
    public synchronized boolean existsForStudentOnDate(String ra, LocalDate date) {
        return readAll().stream().anyMatch(c -> c.ra().equalsIgnoreCase(ra) && c.date().equals(date));
    }

    @Override
    public synchronized List<CheckIn> findByDate(LocalDate date) {
        return readAll().stream()
                .filter(c -> c.date().equals(date))
                .sorted(Comparator.comparing(CheckIn::time))
                .toList();
    }

    @Override
    public synchronized Set<LocalDate> findAllDates() {
        Set<LocalDate> dates = new LinkedHashSet<>();
        readAll().stream().map(CheckIn::date).sorted(Comparator.reverseOrder()).forEach(dates::add);
        return dates;
    }

    private List<CheckIn> readAll() {
        try {
            if (Files.notExists(file)) return new ArrayList<>();
            String json = Files.readString(file);
            if (json.isBlank()) return new ArrayList<>();
            List<CheckIn> result = gson.fromJson(json, listType);
            return result == null ? new ArrayList<>() : new ArrayList<>(result);
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível ler o arquivo JSON: " + e.getMessage(), e);
        }
    }

    private void writeAll(List<CheckIn> records) {
        try {
            Path parent = file.getParent();
            if (parent != null) Files.createDirectories(parent);
            Files.writeString(file, gson.toJson(records, listType));
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível salvar o arquivo JSON: " + e.getMessage(), e);
        }
    }
}

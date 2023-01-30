package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import static ru.javawebinar.basejava.util.DateUtil.NOW;

public class Period {
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String description;
    private final String position;

    public Period(String position, String description, LocalDate dateFrom, LocalDate dateTo) {
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        this.position = position;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Period(String position, String description, int dateFrom, Month monthFrom, int dateTo, Month monthTo){
        this.position = position;
        this.description = description;
        this.dateFrom = DateUtil.of(dateFrom, monthFrom);
        this.dateTo = DateUtil.of(dateTo, monthTo);
    }

    public Period(String position, String description, int dateFrom, Month monthFrom){
        this.position = position;
        this.description = description;
        this.dateFrom = DateUtil.of(dateFrom, monthFrom);
        this.dateTo = NOW;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getDescription() {
        return description;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!dateFrom.equals(period.dateFrom)) return false;
        if (!dateTo.equals(period.dateTo)) return false;
        if (!description.equals(period.description)) return false;
        return position.equals(period.position);
    }

    @Override
    public int hashCode() {
        int result = dateFrom.hashCode();
        result = 31 * result + dateTo.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM.yyyy");
        return dateFormat.format(dateFrom) + "-" + dateFormat.format(dateTo) + "\n"
                + description + "\n" + position + "\n";
    }
}

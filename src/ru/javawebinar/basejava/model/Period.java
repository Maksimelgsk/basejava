package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Period {

    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String description;
    private final String title;

    public Period(LocalDate dateFrom, LocalDate dateTo, String description, String title) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!dateFrom.equals(period.dateFrom)) return false;
        if (!dateTo.equals(period.dateTo)) return false;
        if (!description.equals(period.description)) return false;
        return title.equals(period.title);
    }

    @Override
    public int hashCode() {
        int result = dateFrom.hashCode();
        result = 31 * result + dateTo.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM.yyyy");
        return dateFormat.format(dateFrom) + " - " + dateFormat.format(dateTo) +"\n"
                + description + "\n" + title + "\n";
    }
}

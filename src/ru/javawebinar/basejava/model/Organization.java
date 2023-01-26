package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Organization {
    private final LocalDate start;
    private final LocalDate end;
    private final String description;
    private final String title;

    public Organization(LocalDate start, LocalDate end, String description, String title) {
        this.start = start;
        this.end = end;
        this.description = description;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!start.equals(that.start)) return false;
        if (!end.equals(that.end)) return false;
        if (!description.equals(that.description)) return false;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return start.toString() + " - " + end.toString() + "\n" + description + "\n" + title + "\n";
    }
}
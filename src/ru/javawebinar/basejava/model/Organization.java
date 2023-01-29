package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String link;
    private final String title;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String link, String title, String position, String description, LocalDate dateFrom, LocalDate dateTo) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(description, "description must not be null");
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        this.link = link;
        this.title = title;
        periods.add(new Period(description, position, dateFrom, dateTo));
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        if (!title.equals(that.title)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder period = new StringBuilder("");
        for (Period list : periods) {
            period.append(list);
        }
        return link + "\n" + title + "\n" + period;
    }
}
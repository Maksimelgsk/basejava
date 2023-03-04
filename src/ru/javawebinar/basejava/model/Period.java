package ru.javawebinar.basejava.model;

import com.google.gson.annotations.JsonAdapter;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.JsonLocalDateAdapter;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final Period EMPTY = new Period();
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonAdapter(JsonLocalDateAdapter.class)
    private LocalDate dateFrom;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonAdapter(JsonLocalDateAdapter.class)
    private LocalDate dateTo;
    private String description;
    private String position;

    public Period() {
    }

    public Period(String position, String description, LocalDate dateFrom, LocalDate dateTo) {
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        this.position = position;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Period(String position, String description, int dateFrom, Month monthFrom, int dateTo, Month monthTo) {
        this.position = position;
        this.description = description;
        this.dateFrom = DateUtil.of(dateFrom, monthFrom);
        this.dateTo = DateUtil.of(dateTo, monthTo);
    }

    public Period(String position, String description, int dateFrom, Month monthFrom) {
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
        return dateFrom.equals(period.dateFrom) && dateTo.equals(period.dateTo) &&
                Objects.equals(description, period.description) && position.equals(period.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo, description, position);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM.yyyy");
        return dateFormat.format(dateFrom) + "-" + dateFormat.format(dateTo) + "\n"
                + description + "\n" + position + "\n";
    }
}

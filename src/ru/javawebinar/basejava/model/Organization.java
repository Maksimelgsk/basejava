package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final Organization EMPTY = new Organization("", "", Period.EMPTY);
    private String link;
    private String title;
    private List<Period> periods;

    public Organization() {
    }

    public Organization(String link, String title, Period... periods) {
        Objects.requireNonNull(title, "title must not be null");
        this.link = link;
        this.title = title;
        this.periods = Arrays.asList(periods);
    }

    public Organization(String link, String title, List<Period> periods) {
        this.link = link;
        this.title = title;
        this.periods = periods;
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
        return link.equals(that.link) && title.equals(that.title) && periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, title, periods);
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
package ru.javawebinar.basejava.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    @Serial
    private static final long serialVersionUID = 1L;
    private List<String> sections;

    public static final ListSection EMPTY = new ListSection("");

    public ListSection() {
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> section) {
        Objects.requireNonNull(section, "sections must not be null");
        this.sections = section;
    }

    public List<String> getSections() {
        return sections;
    }

    public void addSection(String text) {
        if (sections == null) {
            sections = new ArrayList<>();
        }
        sections.add(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return sections.equals(that.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sections);
    }

    @Override
    public String toString() {
        StringBuilder listToString = new StringBuilder("");
        for (String text : sections) {
            listToString.append(text).append("\n");
        }
        return listToString.toString();
    }
}

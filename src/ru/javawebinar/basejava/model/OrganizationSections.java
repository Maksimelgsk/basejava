package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSections extends AbstractSection {

    private final List<Organization> sections;

    public OrganizationSections(List<Organization> sections) {
        Objects.requireNonNull(sections, "organizations must not be null");
        this.sections = sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSections that = (OrganizationSections) o;

        return sections.equals(that.sections);
    }

    @Override
    public int hashCode() {
        return sections.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder organization = new StringBuilder("");
        for (Organization list : sections) {
            organization.append(list).append("\n");
        }
        return organization.toString();
    }
}

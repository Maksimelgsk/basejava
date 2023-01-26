package ru.javawebinar.basejava.model;

import java.util.Map;

public class OrganizationSections extends AbstractSection {
    private final Map<Link, Organization> sections;

    public OrganizationSections(Map<Link, Organization> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        StringBuilder organization = new StringBuilder("");
        for (Map.Entry<Link, Organization> entry : sections.entrySet()) {
            organization.append(entry.getKey()).append(entry.getValue());
        }
        return organization.toString();
    }
}

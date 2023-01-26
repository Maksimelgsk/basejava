package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Map;

public class OrganizationSections extends AbstractSection {

    private final Map<Organization, Period> sections;

    public OrganizationSections(Map<Organization, Period> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        StringBuilder organization = new StringBuilder("");
        for (Map.Entry<Organization, Period> entry : sections.entrySet()) {
            organization.append(entry.getKey()).append(entry.getValue());
        }
        return organization.toString();
    }
}

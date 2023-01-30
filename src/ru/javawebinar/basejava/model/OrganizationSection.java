package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {

    private final List<Organization> organizationList;

    public OrganizationSection(List<Organization> sections) {
        Objects.requireNonNull(sections, "organizationList must not be null");
        this.organizationList = sections;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizationList.equals(that.organizationList);
    }

    @Override
    public int hashCode() {
        return organizationList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder organization = new StringBuilder("");
        for (Organization list : organizationList) {
            organization.append(list).append("\n");
        }
        return organization.toString();
    }
}

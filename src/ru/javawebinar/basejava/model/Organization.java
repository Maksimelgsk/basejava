package ru.javawebinar.basejava.model;

public class Organization {
    private final String web;
    private final String title;

    public Organization(String title, String web) {
        this.web = web;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!web.equals(that.web)) return false;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        int result = web.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return title+ "\n" + web + "\n";
    }
}
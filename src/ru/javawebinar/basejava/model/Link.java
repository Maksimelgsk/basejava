package ru.javawebinar.basejava.model;

public class Link {
    private final String web;
    private final String name;

    public Link(String web, String name) {
        this.web = web;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!web.equals(link.web)) return false;
        return name.equals(link.name);
    }

    @Override
    public int hashCode() {
        int result = web.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name+ "\n" + web + "\n";
    }
}

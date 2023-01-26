package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Телефон: "),
    SKYPE("Skype: "),
    EMAIL("Mail: "),
    LINKEDIN("Профиль LinkedId: "),
    GITHUB("Профиль GitHub: "),
    STACKOVERFLOW("Профиль StackOverflow: "),
    HOME_PAGE("Домашняя страница: ");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

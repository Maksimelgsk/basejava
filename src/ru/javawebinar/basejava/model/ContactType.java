package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Телефон: "),
    SKYPE("Skype: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype" + value + "'>" + value + "<a/>";
        }
    },
    EMAIL("Mail: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto" + value + "'>" + value + "<a/>";
        }
    },
    LINKEDIN("Профиль LinkedId: ") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Профиль GitHub: ") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль StackOverflow: ") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOME_PAGE("Домашняя страница: "){
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}

package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String uuid;

    private String fullName;

    public static final Resume EMPTY = new Resume();

    static {
        EMPTY.setSections(SectionType.OBJECTIVE, TextSection.EMPTY);
        EMPTY.setSections(SectionType.PERSONAL, TextSection.EMPTY);
        EMPTY.setSections(SectionType.ACHIEVEMENT, ListSection.EMPTY);
        EMPTY.setSections(SectionType.QUALIFICATIONS, ListSection.EMPTY);
        EMPTY.setSections(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
        EMPTY.setSections(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
    }

    public Resume() {
    }

    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSections(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    public void setContacts(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    public AbstractSection getSections(SectionType type) {
        return sections.get(type);
    }

    public String getContacts(ContactType type) {
        return contacts.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) &&
                sections.equals(resume.sections) && contacts.equals(resume.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections, contacts);
    }

    @Override
    public String toString() {
        return "Resume: " + uuid + "; " + fullName;
    }
}

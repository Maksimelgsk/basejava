package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StrategyStream {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeResume(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeResume(dos, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> {
                        String text = ((TextSection) section).getText();
                        dos.writeUTF(text);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) section).getSections();
                        writeResume(dos, list, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizationsList = ((OrganizationSection) section).getOrganizationList();
                        writeResume(dos, organizationsList, organization -> {
                            dos.writeUTF(organization.getLink());
                            dos.writeUTF(organization.getTitle());
                            writeResume(dos, organization.getPeriods(), period -> {
                                dos.writeUTF(period.getPosition());
                                dos.writeUTF(period.getDescription());
                                dos.writeInt(period.getDateFrom().getYear());
                                dos.writeInt(period.getDateFrom().getMonth().getValue());
                                dos.writeInt(period.getDateTo().getYear());
                                dos.writeInt(period.getDateTo().getMonth().getValue());
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readResume(dis, () -> {
                String contact = dis.readUTF();
                ContactType contactType = ContactType.valueOf(contact);
                resume.setContacts(contactType, dis.readUTF());
            });
            readResume(dis, () -> {
                String sectionTitle = dis.readUTF();
                SectionType sectionType = SectionType.valueOf(sectionTitle);
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE ->
                            resume.setSections(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            resume.setSections(sectionType, new ListSection(readList(dis, dis::readUTF)));
                    case EXPERIENCE, EDUCATION ->
                            resume.setSections(sectionType, new OrganizationSection(readList(dis, () ->
                                    new Organization(dis.readUTF(), dis.readUTF(), readList(dis, () ->
                                            new Period(dis.readUTF(), dis.readUTF(),
                                                    LocalDate.of(dis.readInt(), dis.readInt(), 1),
                                                    LocalDate.of(dis.readInt(), dis.readInt(), 1)))))));
                }
            });
            return resume;
        }
    }

    private <T> void writeResume(DataOutputStream dos, Collection<T> collection, WriterInterface<T> t) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            t.write(item);
        }
    }

    private void readResume(DataInputStream dis, ReaderInterface processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.read();
        }
    }

    private <T> List<T> readList(DataInputStream dis, ListReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readList());
        }
        return list;
    }

    @FunctionalInterface
    private interface WriterInterface<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    private interface ReaderInterface {
        void read() throws IOException;
    }

    @FunctionalInterface
    private interface ListReader<T> {
        T readList() throws IOException;
    }
}

package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view" -> r = storage.get(uuid);
            case "add" -> r = Resume.EMPTY;
            case "edit" -> {
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSections(type);
                    switch (type) {
                        case OBJECTIVE, PERSONAL -> {
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                        }
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                        }
                        case EDUCATION, EXPERIENCE -> {
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (section != null) {
                                for (Organization org : orgSection.getOrganizations()) {
                                    List<Period> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Period.EMPTY);
                                    emptyFirstPositions.addAll(org.getPeriods());
                                    emptyFirstOrganizations.add(new Organization(org.getTitle(),
                                            org.getLink(), emptyFirstPositions));
                                }
                            }
                            r.setSections(type, new OrganizationSection(emptyFirstOrganizations));
                        }
                    }
                }
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;

        if (uuid == null || uuid.length() == 0) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        if (HtmlUtil.isEmpty(fullName)) {
            response.sendRedirect("resume");
            return;
        }
//        for (ContactType type : ContactType.values()) {
//            String value = request.getParameter(type.name());
//            if (HtmlUtil.isEmpty(value)) {
//                r.getContacts().remove(type);
//            } else {
//                r.setContacts(type, value);
//            }
//        }
        addContact(request, r);
        addSection(request, r);
//        for (SectionType type : SectionType.values()) {
//            String value = request.getParameter(type.name());
//            String[] values = request.getParameterValues(type.name());
//            if (HtmlUtil.isEmpty(value) && values.length < 2) {
////                r.getSections().remove(type);
//                response.sendRedirect("resume");
//                return;
//            } else {
//                switch (type) {
//                    case OBJECTIVE, PERSONAL -> r.setSections(type, new TextSection(value));
//                    case ACHIEVEMENT, QUALIFICATIONS -> r.setSections(type,
//                            new ListSection(List.of(value.split("\n"))));
//                    case EDUCATION, EXPERIENCE -> {
//                        List<Organization> orgs = new ArrayList<>();
//                        String[] urls = request.getParameterValues(type.name() + "url");
//                        for (int i = 0; i < values.length; i++) {
//                            String name = values[i];
//                            if (!HtmlUtil.isEmpty(name)) {
//                                List<Period> positions = new ArrayList<>();
//                                String pfx = type.name() + i;
//                                String[] startDates = request.getParameterValues(pfx + "startDate");
//                                String[] endDates = request.getParameterValues(pfx + "endDate");
//                                String[] titles = request.getParameterValues(pfx + "title");
//                                String[] descriptions = request.getParameterValues(pfx + "description");
//                                for (int j = 0; j < titles.length; j++) {
//                                    if (!HtmlUtil.isEmpty(titles[j])) {
//                                        positions.add(new Period(titles[j], descriptions[j],
//                                                DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j])));
//                                    }
//                                }
//                                orgs.add(new Organization(urls[i], name, positions));
//                            }
//                        }
//                        r.setSections(type, new OrganizationSection(orgs));
//                    }
//                }
//            }
//        }
        if (HtmlUtil.isEmpty(uuid)) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    private void addContact(HttpServletRequest request, Resume r) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                r.getContacts().remove(type);
            } else {
                r.setContacts(type, value);
            }
        }
    }

    private void addSection(HttpServletRequest request, Resume r) {
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
//                response.sendRedirect("resume");
//                return;
            } else {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> r.setSections(type, new TextSection(value));
                    case ACHIEVEMENT, QUALIFICATIONS -> r.setSections(type,
                            new ListSection(List.of(value.split("\n"))));
                    case EDUCATION, EXPERIENCE -> {
                        List<Organization> orgs = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Period> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Period(titles[j], descriptions[j],
                                                DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j])));
                                    }
                                }
                                orgs.add(new Organization(urls[i], name, positions));
                            }
                        }
                        r.setSections(type, new OrganizationSection(orgs));
                    }
                }
            }
        }
    }
}

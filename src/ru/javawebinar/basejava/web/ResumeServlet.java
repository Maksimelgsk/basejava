package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

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
        if (fullName.equals("")) {
            response.sendRedirect("resume");
            return;
        }
        boolean isExist;
        if (uuid != null && !uuid.equals("")) {
            isExist = true;
            r = storage.get(uuid);
            r.setFullName(fullName);
        } else {
            isExist = false;
            r = new Resume(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.setContacts(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String section = request.getParameter(type.name());

            if (section == null && section.equals("")) {
                r.getSections().remove(type);
                break;
            }

            switch (type) {
                case PERSONAL, OBJECTIVE -> {
                    TextSection textSection = new TextSection(section);
                    r.setSections(type, textSection);
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    List<String> listTmp = new ArrayList<>();
                    String[] strings = section.split("\n");
                    for (String s : strings) {
                        if (!s.equals("\r")) {
                            listTmp.add(s);
                        }
                    }
                    ListSection listSection = new ListSection(listTmp);
                    r.setSections(type, listSection);
                }
                case EXPERIENCE, EDUCATION -> {
                    List<Organization> orgs = new ArrayList<>();
                    String[] values = request.getParameterValues(type.name());
                    String[] url = request.getParameterValues(type.name() + "url");
                    for (int i = 0; i < values.length; i++) {
                        String name = values[i];
                        if (name != null && !name.equals("null") && !name.equals("")) {
                            List<Period> periods = new ArrayList<>();
                            String periodCounter = type.name() + i;
                            String[] startDates = request.getParameterValues(periodCounter + "startDate");
                            String[] endDates = request.getParameterValues(periodCounter + "endDate");
                            String[] titles = request.getParameterValues(periodCounter + "title");
                            String[] descriptions = request.getParameterValues(periodCounter + "description");

                            for (int j = 0; j < titles.length; j++) {
                                if (titles[j] != null && !titles[j].equals("null") && !titles[j].equals("")) {
                                    periods.add(new Period(titles[j], descriptions[j],
                                            DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j])));
                                }
                            }
                            orgs.add(new Organization(url[i], name, periods));
                        }
                    }
                    r.setSections(type, new OrganizationSection(orgs));
                }
            }
        }
        if (isExist) {
            storage.update(r);
        } else {
            storage.save(r);
        }
        response.sendRedirect("resume");
    }
}

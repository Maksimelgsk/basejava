package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(
                "<html>   " +
                        "<head> " +
                        "<style> " +
                        "table, th, td {" +
                        "border: 1px solid black;" +
                        "border-collapse: collapse;" +
                        "}th, td {" +
                        "padding: 10px;" +
                        "} " +
                        "table#t01 " +
                        "{background-color: #f1f1c1;" +
                        "}"+
                        "</style>" +
                        "</head> ");
        writer.write(
                "<table id=\"t01\"> " +
                        "<tr> " +
                        "<caption>" +
                        "<h3 style=\"color:blue;\"><em>LIST RESUMES</em></h3>" +
                        "</caption>"+
                        "" +
                        "<th>uuid</th> " +
                        "<th >fullName</th> " +
                        "</tr> ");
        List<Resume> allResumes = storage.getAllSorted();
        for (Resume resume : allResumes) {
            response.getWriter().write("  " +
                    "   <tr>" +
                    "<td>" + resume.getUuid() + "</td>" +
                    "<td>" + resume.getFullName() + "</td>");}
        writer.write("</tr> " +
                "</table>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

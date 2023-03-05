<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="static ru.javawebinar.basejava.model.SectionType.OBJECTIVE" %>
<%@ page import="static ru.javawebinar.basejava.model.SectionType.PERSONAL" %>
<%@ page import="static ru.javawebinar.basejava.model.SectionType.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContacts(type)}"></dd>
            </dl>
        </c:forEach>

        <hr>
        <hr>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSections(type)}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
            <h2><a>${type.title}</a></h2>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <input type='text' name='${type}' size=75 value='<%=section%>'>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <textarea name='${type}' cols=75 rows=5><%=section%></textarea>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ListSection) section).getSections())%></textarea>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizationList()%>"
                               varStatus="counter">
                        <dl>
                            <dt>Название учереждения:</dt>
                            <dd><input type="text" name='${type}' size=100 value="${org.title}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт учереждения:</dt>
                            <dd><input type="text" name='${type}url' size=100 value="${org.link}"></dd>
                            </dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="pos" items="${org.periods}">
                                <jsp:useBean id="pos" type="ru.javawebinar.basejava.model.Period"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}startDate" size=10
                                               value="<%=DateUtil.format(pos.getDateFrom())%>" placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}endDate" size=10
                                               value="<%=DateUtil.format(pos.getDateTo())%>" placeholder="MM/yyyy">
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name='${type}${counter.index}title' size=75
                                               value="${pos.position}">
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${type}${counter.index}title" rows=5
                                                  cols=75>${pos.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>





<%--        <c:forEach var="type" items="<%=SectionType.values()%>">--%>
<%--            <dl>--%>
<%--                <h3><a>${type.title}</a></h3>--%>
<%--                <c:choose>--%>
<%--                <c:when test="${type=='OBJECTIVE'}">--%>
<%--                <dd><textarea name='${type}' cols=75--%>
<%--                              rows=5><%=((TextSection) resume.getSections(OBJECTIVE)).getText()%></textarea>--%>
<%--                    </c:when>--%>

<%--                    <c:when test="${type=='PERSONAL'}">--%>
<%--                <dd><textarea name='${type}' cols=75--%>
<%--                              rows=5><%=((TextSection) resume.getSections(PERSONAL)).getText()%></textarea>--%>
<%--                    </c:when>--%>

<%--                    <c:when test="${type=='QUALIFICATIONS'}">--%>
<%--                <dd><textarea name='${type}' cols=75--%>
<%--                              rows=10><%=String.join("\n",--%>
<%--                        ((ListSection) resume.getSections(QUALIFICATIONS)).getSections())%></textarea>--%>
<%--                    </c:when>--%>

<%--                    <c:when test="${type=='ACHIEVEMENT'}">--%>
<%--                <dd><textarea name='${type}' cols=75--%>
<%--                              rows=10><%=String.join("\n", ((ListSection) resume.getSections(ACHIEVEMENT)).getSections())%></textarea>--%>
<%--                    </c:when>--%>

<%--                    <c:when test="${type.name().equals('EXPERIENCE')}">--%>
<%--                    <c:forEach var="orgExp"--%>
<%--                               items="<%=((OrganizationSection)resume.getSections(EXPERIENCE)).getOrganizationList()%>"--%>
<%--                               varStatus="loopExp">--%>
<%--                        <jsp:useBean id="orgExp" type="ru.javawebinar.basejava.model.Organization"/>--%>
<%--                    <dl>--%>
<%--                        <dt><b>Название организации:</b></dt>--%>
<%--                        <dd><input type="text" name="${type.name()}" size=50 value="<%=orgExp.getLink()%>">--%>
<%--                    </dl>--%>

<%--                    <dl>--%>
<%--                        <dt><b>Сайт организации:</b></dt>--%>
<%--                        <dd><input type="text" name="${type.name()}website" size=50 value="<%=orgExp.getTitle()%>">--%>
<%--                    </dl>--%>


<%--                    <div style="margin-left: 30px">--%>
<%--                        <c:forEach var="periodExp" items="${orgExp.periods}">--%>
<%--                            <jsp:useBean id="periodExp" type="ru.javawebinar.basejava.model.Period"/>--%>
<%--                            <dl>--%>
<%--                                <dt>Начальная дата:</dt>--%>
<%--                                <dd><input type="text" name="${type.name()}${loopExp.index}startDate" size=10--%>
<%--                                           value="<%=DateUtil.format(periodExp.getDateFrom())%>">--%>
<%--                            </dl>--%>
<%--                            <dl>--%>
<%--                                <dt>Конечная дата:</dt>--%>
<%--                                <dd><input type="text" name="${type.name()}${loopExp.index}endDate" size=10--%>
<%--                                           value="<%=DateUtil.format(periodExp.getDateTo())%>">--%>
<%--                            </dl>--%>

<%--                            <dl>--%>
<%--                                <dt>Должность:</dt>--%>
<%--                                <c:choose>--%>
<%--                                <c:when test="${(periodExp.position != null)}">--%>
<%--                                <dd><input type="text" name="${type.name()}${loopExp.index}title" size=70--%>
<%--                                           value="<%=periodExp.getPosition()%>">--%>
<%--                                    </c:when>--%>
<%--                                    <c:when test="${(periodExp.position == null)}">--%>
<%--                                <dd><input type="text" name="${type.name()}${loopExp.index}title" size=70--%>
<%--                                           value="">--%>
<%--                                    </c:when>--%>
<%--                                    </c:choose>--%>
<%--                            </dl>--%>

<%--                            <dl>--%>
<%--                                <dt>Описание:</dt>--%>
<%--                                <c:choose>--%>
<%--                                <c:when test="${(periodExp.description != null)}">--%>
<%--                                <dd><textarea name="${type.name()}${loopExp.index}description" cols=75--%>
<%--                                              rows=5> <%=String.join("\n", periodExp.getDescription())%></textarea>--%>
<%--                                    </c:when>--%>

<%--                                    <c:when test="${(periodExp.description == null)}">--%>
<%--                                <dd><textarea name="${type.name()}${loopExp.index}description" cols=75--%>
<%--                                              rows=5> <%=String.join("\n", periodExp.getDescription())%></textarea>--%>
<%--                                    </c:when>--%>

<%--                                    </c:choose>--%>
<%--                            </dl>--%>
<%--                        </c:forEach>--%>
<%--                    </div>--%>
<%--                    </c:forEach>--%>
<%--                    <br/>--%>
<%--                    </c:when>--%>



<%--                    <c:when test="${type.name().equals('EDUCATION')}">--%>
<%--                    <c:forEach var="orgEdu"--%>
<%--                               items="<%=((OrganizationSection)resume.getSections(EDUCATION)).getOrganizationList()%>"--%>
<%--                               varStatus="loopEdu">--%>
<%--                        <jsp:useBean id="orgEdu" type="ru.javawebinar.basejava.model.Organization"/>--%>
<%--                    <dl>--%>
<%--                        <dt><b>Название организации:</b></dt>--%>
<%--                        <dd><input type="text" name="${type.name()}" size=30 value="<%=orgEdu.getLink()%>">--%>
<%--                    </dl>--%>

<%--                    <dl>--%>
<%--                        <dt><b>Сайт организации:</b></dt>--%>
<%--                        <dd><input type="text" name="${type.name()}website" size=30 value="<%=orgEdu.getTitle()%>">--%>
<%--                    </dl>--%>

<%--                    <div style="margin-left: 25px">--%>
<%--                        <c:forEach var="periodEdu" items="${orgEdu.periods}">--%>
<%--                            <jsp:useBean id="periodEdu" type="ru.javawebinar.basejava.model.Period"/>--%>
<%--                            <dl>--%>
<%--                                <dt>Начальная дата:</dt>--%>
<%--                                <dd><input type="text" name="${type.name()}${loopEdu.index}startDate" size=10--%>
<%--                                           value="<%=DateUtil.format(periodEdu.getDateFrom())%>">--%>
<%--                            </dl>--%>

<%--                            <dl>--%>
<%--                                <dt>Конечная дата:</dt>--%>
<%--                                <dd><input type="text" name="${type.name()}${loopEdu.index}endDate" size=10--%>
<%--                                           value="<%=DateUtil.format(periodEdu.getDateTo())%>">--%>
<%--                            </dl>--%>
<%--                            <dl>--%>
<%--                                <dt>Название:</dt>--%>
<%--                                <c:choose>--%>
<%--                                <c:when test="${(periodEdu.position != null)}">--%>
<%--                                <dd><input type="text" name="${type.name()}${loopEdu.index}title" size=100--%>
<%--                                           value="<%=periodEdu.getPosition()%>">--%>
<%--                                    </c:when>--%>
<%--                                    <c:when test="${(periodEdu.position == null)}">--%>
<%--                                <dd><input type="text" name="${type.name()}${loopEdu.index}title" size=100--%>
<%--                                           value="">--%>
<%--                                    </c:when>--%>
<%--                                    </c:choose>--%>
<%--                            </dl>--%>
<%--                            <dl>--%>
<%--                                <dt>Описание:</dt>--%>
<%--                                <c:choose>--%>
<%--                                <c:when test="${(periodEdu.description != null)}">--%>
<%--                                <dd><textarea name="${type.name()}${loopExp.index}description" cols=75--%>
<%--                                              rows=5> <%=String.join("\n", periodEdu.getDescription())%></textarea>--%>
<%--                                    </c:when>--%>

<%--                                    <c:when test="${(periodEdu.description == null)}">--%>
<%--                                <dd><textarea name="${type.name()}${loopExp.index}description" cols=75--%>
<%--                                              rows=5> <%=String.join("\n", periodEdu.getDescription())%></textarea>--%>
<%--                                    </c:when>--%>
<%--                                    </c:choose>--%>
<%--                            </dl>--%>
<%--                            <br/>--%>
<%--                        </c:forEach>--%>
<%--                    </div>--%>
<%--                    </c:forEach>--%>
<%--                    <br/>--%>
<%--                    </c:when>--%>
<%--                    </c:choose>--%>
<%--            </dl>--%>
<%--        </c:forEach>--%>
<%--        <button type="submit">Сохранить</button>--%>
<%--        <button onclick="window.history.back()">Отменить</button>--%>
<%--    </form>--%>
<%--</section>--%>
<%--<jsp:include page="fragments/footer.jsp"/>--%>
<%--</body>--%>
<%--</html>--%>
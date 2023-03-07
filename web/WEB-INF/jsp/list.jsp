<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>

    <style type="text/css">
        table {
            width: 100%;
            margin-bottom: 20px;
            border: 5px solid #fff;
            border-top: 5px solid #fff;
            border-bottom: 3px solid #fff;
            border-collapse: collapse;
            outline: 3px solid #ffd300;
            font-size: 15px;
            background: #fff!important;
        }
        table th {
            font-weight: bold;
            padding: 7px;
            background: #ffd300;
            border: none;
            text-align: left;
            font-size: 15px;
            border-top: 3px solid #fff;
            border-bottom: 3px solid #ffd300;
        }
        table td {
            padding: 7px;
            border: none;
            border-top: 3px solid #fff;
            border-bottom: 3px solid #fff;
            font-size: 15px;
        }
        table tbody tr:nth-child(even){
            background: #f8f8f8!important;
        }

        body {
            font-family: Arial, Helvetica, sans-serif;
        }

        button {
            display: inline-block;
            font-family: arial,sans-serif;
            font-size: 11px;
            font-weight: bold;
            color: rgb(68,68,68);
            text-decoration: none;
            user-select: none;
            padding: .2em 1.2em;
            outline: none;
            border: 1px solid rgba(0,0,0,.1);
            border-radius: 2px;
            background: rgb(245,245,245) linear-gradient(#f4f4f4, #f1f1f1);
            transition: all .218s ease 0s;
        }
        button:hover {
            color: rgb(24,24,24);
            border: 1px solid rgb(198,198,198);
            background: #f7f7f7 linear-gradient(#f7f7f7, #f1f1f1);
            box-shadow: 0 1px 2px rgba(0,0,0,.1);
        }
        button:active {
            color: rgb(51,51,51);
            border: 1px solid rgb(204,204,204);
            background: rgb(238,238,238) linear-gradient(rgb(238,238,238), rgb(224,224,224));
            box-shadow: 0 1px 2px rgba(0,0,0,.1) inset;
        }

    </style>

</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th><img src="img/id-card.png" alt="" style="vertical-align:middle"> NAME</th>
            <th><img src="img/email.png" alt="" style="vertical-align:middle"> EMAIL</th>
            <th><center><img src="img/delete.png" alt="" style="vertical-align:middle"> DELETE</center></th>
            <th><center><img src="img/edit.png" alt="" style="vertical-align:middle"> EDIT</center></th>
        </tr>
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContacts(ContactType.EMAIL))%></td>
                <td><center><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/trash.png"></a></center></td>
                <td><center><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/writing.png"></a></center></td>
            </tr>
        </c:forEach>
    </table>
</section>
<br>
<a href="resume?&action=add">
    <button type="submit">
        <img src="img/add.png" alt="" style="vertical-align:middle"> ADD RESUME
    </button>
</a>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

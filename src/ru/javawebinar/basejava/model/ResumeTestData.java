package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.*;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("id1", "Grigory Kislin");
        // CONTACT
        resume.setContacts(PHONE, "+7-999-123-45-67");
        resume.setContacts(SKYPE, "@skype_name");
        resume.setContacts(EMAIL, "email@email.com");
        resume.setContacts(LINKEDIN, "@linkedid");
        resume.setContacts(GITHUB, "@gitHud");
        resume.setContacts(STACKOVERFLOW, "@stackoverflow");
        resume.setContacts(HOME_PAGE, "www.javaops.ru");

        //OBJECTIVE
        resume.setSections(PERSONAL, new TextSection("""
                Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.\n"""));

        //PERSONAL
        resume.setSections(OBJECTIVE, new TextSection("""
                Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям.\n"""));

        //QUALIFICATIONS
        List<String> qualifications = new ArrayList<>();
        TextSection qualificationsText1 = new TextSection("""
                * JEE AS: GlassFish (v2.1, v3), OC4J, JBoss,Tomcat, Jetty, WebLogic, WSO2""");
        TextSection qualificationsText2 = new TextSection("""
                * Version control: Subversion, Git, Mercury, ClearCase, Perforce""");
        TextSection qualificationsText3 = new TextSection("""
                * DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite,
                MS SQL, HSQLDB""");
        TextSection qualificationsText4 = new TextSection("""
                * Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy""");
        TextSection qualificationsText5 = new TextSection("* XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        TextSection qualificationsText6 = new TextSection("""
                * Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security,
                Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin,
                Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).""");
        TextSection qualificationsText7 = new TextSection("* Python: Django.");
        TextSection qualificationsText8 = new TextSection("* JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        TextSection qualificationsText9 = new TextSection("* Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        TextSection qualificationsText10 = new TextSection("""
                * Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT,
                MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2,
                JWT.""");
        TextSection qualificationsText11 = new TextSection("""
                * Инструменты: Maven + plugin development, Gradle, настройка Ngnix""");
        TextSection qualificationsText12 = new TextSection("""
                * Администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport,
                OpenCmis, Bonita, pgBouncer""");
        TextSection qualificationsText13 = new TextSection("""
                * Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектирования, архитектурных шаблонов,
                UML, функционального программирования""");
        TextSection qualificationsText14 = new TextSection("* Родной русский, английский \"upper intermediate\"");
        qualifications.add(qualificationsText1.getText());
        qualifications.add(qualificationsText2.getText());
        qualifications.add(qualificationsText3.getText());
        qualifications.add(qualificationsText4.getText());
        qualifications.add(qualificationsText5.getText());
        qualifications.add(qualificationsText6.getText());
        qualifications.add(qualificationsText7.getText());
        qualifications.add(qualificationsText8.getText());
        qualifications.add(qualificationsText9.getText());
        qualifications.add(qualificationsText10.getText());
        qualifications.add(qualificationsText11.getText());
        qualifications.add(qualificationsText12.getText());
        qualifications.add(qualificationsText13.getText());
        qualifications.add(qualificationsText14.getText());
        resume.setSections(QUALIFICATIONS, new ListSection(qualifications));

        //EXPERIENCE
        List<Organization> experience = new ArrayList<>();
        experience.add(new Organization("https://javaops.ru/", "Java Online Projects",
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.",
                LocalDate.of(2013, 10, 1), LocalDate.now()));
        experience.add(new Organization("https://www.wrike.com/", "Wrike",
                "Старший разработчик (backend)",
                """
                        Проектирование и разработка онлайн платформы управления проектами
                        Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная
                        аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.""",
                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1)));
        experience.add(new Organization("n/d", "RIT Center",
                "Java архитектор",
                """
                        Организация процесса разработки системы ERP для разных окружений: релизная политика,
                        версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование
                        системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка
                        интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта,
                        экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера
                        документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring
                        MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via
                        ssh tunnels, PL/Python""",
                LocalDate.of(2012, 12, 1), LocalDate.of(2014, 10, 1)));
        experience.add(new Organization("https://www.luxoft.ru/", "Luxoft (Deutsche Bank)",
                "Ведущий программист",
                """
                        Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT,
                        Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для
                        администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA,
                        Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.""",
                LocalDate.of(2010, 12, 1), LocalDate.of(2012, 4, 1)));
        experience.add(new Organization("https://www.yota.ru/", "Yota",
                "Ведущий специалист",
                """
                        Дизайн и имплементация Java EE фреймворка для отдела "Платежные Системы" (GlassFish v2.1, v3,
                        OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования,
                        статистики и мониторинга фреймворка. Разработка online JMX клиента
                        (Python/ Jython, Django, ExtJS)""",
                LocalDate.of(2008, 6, 1), LocalDate.of(2010, 12, 1)));
        experience.add(new Organization("http://enkata.com/", "Enkata",
                "Разработчик ПО",
                """
                        Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS)
                        частей кластерного J2EE приложения (OLAP, Data mining).""",
                LocalDate.of(2007, 3, 1), LocalDate.of(2008, 6, 1)));
        experience.add(new Organization("https://www.siemens.com/global/en.html", "Siemens AG",
                "Разработчик ПО",
                """
                        Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на
                        мобильной IN платформе Siemens @vantage (Java, Unix).""",
                LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1)));
        experience.add(new Organization("http://www.alcatel.ru/", "Alcatel",
                "Инженер по аппаратному и программному тестированию",
                """
                        Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12
                        (CHILL, ASM).""",
                LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1)));
        resume.setSections(EXPERIENCE, new OrganizationSections(experience));

        //EDUCATION
        List<Organization> education = new ArrayList<>();
        education.add(new Organization("https://www.coursera.org/learn/scala-functional-programming", "Coursera",
                "-",
                "Functional Programming Principles in Scala' by Martin Odersky",
                LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1)));
        education.add(new Organization("http://www.luxoft-training.ru/training/", "Luxoft",
                "-",
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1)));
        education.add(new Organization("https://www.siemens.com/global/en.html", "Siemens AG",
                "-", "3 месяца обучения мобильным IN сетям (Берлин)",
                LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1)));
        education.add(new Organization("http://www.alcatel.ru/", "Alcatel",
                "-", "6 месяцев обучения цифровым телефонным сетям (Москва)",
                LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1)));
        education.add(new Organization("https://itmo.ru/", "Санкт-Петербургский национальный " +
                "исследовательский университет информационных технологий, механики и оптики",
                "-", "Аспирантура (программист С, С++)",
                LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1)));
        education.add(new Organization("https://itmo.ru/", "Санкт-Петербургский национальный " +
                "исследовательский университет информационных технологий, механики и оптики",
                "-", "Инженер (программист Fortran, C)",
                LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1)));
        education.add(new Organization("https://mipt.ru/", "Заочная физико-техническая школа при МФТИ",
                "-", "Закончил с отличием",
                LocalDate.of(1984, 9, 1), LocalDate.of(1987, 8, 1)));
        resume.setSections(EDUCATION, new OrganizationSections(education));

        System.out.println(resume);
        System.out.println(PHONE.getTitle() + resume.getContacts(PHONE));
        System.out.println(SKYPE.getTitle() + resume.getContacts(SKYPE));
        System.out.println(EMAIL.getTitle() + resume.getContacts(EMAIL));
        System.out.println(LINKEDIN.getTitle() + resume.getContacts(LINKEDIN));
        System.out.println(GITHUB.getTitle() + resume.getContacts(GITHUB));
        System.out.println(STACKOVERFLOW.getTitle() + resume.getContacts(STACKOVERFLOW));
        System.out.println(HOME_PAGE.getTitle() + resume.getContacts(HOME_PAGE) + "\n");
        System.out.println(PERSONAL.getTitle() + resume.getSections(PERSONAL));
        System.out.println(OBJECTIVE.getTitle() + resume.getSections(OBJECTIVE));
        System.out.println(QUALIFICATIONS.getTitle() + resume.getSections(QUALIFICATIONS));
        System.out.println(EXPERIENCE.getTitle() + resume.getSections(EXPERIENCE));
        System.out.println(EDUCATION.getTitle() + resume.getSections(EDUCATION));
    }
}

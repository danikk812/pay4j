<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../../static/css/core.css">
</head>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="header.button.current_lang" var="locale_button_current_lang"/>
<fmt:message key="header.button.ru" var="locale_button_ru"/>
<fmt:message key="header.button.en" var="locale_button_en"/>
<fmt:message key="header.about_page" var="locale_about_page"/>
<fmt:message key="header.my_profile" var="locale_my_profile"/>
<fmt:message key="header.exit" var="locale_exit"/>
<fmt:message key="admin_panel" var="locale_admin_panel"/>
<fmt:message key="user_panel" var="locale_user_panel"/>
<fmt:message key="sign_in" var="locale_log_in"/>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
    <a class="navbar-brand" href="Controller?command=go_to_accounts_command" style="font-size: 3ex">PAY4J</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">

            <c:set var="user" value="${sessionScope.user}"/>
            <c:if test="${user.name == null}">
                <li class="nav-item">

                    <a type="button" class="btn cl-white" href="Controller?command=go_to_sign_in_command">${locale_log_in}</a>

                </li>
            </c:if>

            <c:if test="${user.name != null}">

                <li class="nav-item">
                    <a type="button" class="btn cl-white" href="Controller?command=go_to_accounts_command">${locale_user_panel}</a>
                </li>

                <c:if test="${user.status.id == 2}">

                    <li class="nav-item">
                        <a type="button" class="btn cl-white" href="Controller?command=go_to_admin_accounts_command">${locale_admin_panel}</a>
                    </li>

                </c:if>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle cl-white" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                            ${user.name}
                    </a>

                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">

                        <a type="button" class="btn dropdown-item" href="Controller?command=go_to_personal_page_command">${locale_my_profile}</a>

                        <div class="dropdown-divider"></div>

                        <button form="headerForm" class="btn btn-outline-danger dropdown-item" type="submit"
                                name="command" value="log_out_command">${locale_exit}
                        </button>

                    </div>
                </li>
            </c:if>

        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle cl-white" href="#" id="navLang" role="button"
                   data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    ${locale_button_current_lang}

                </a>

                <div class="dropdown-menu" aria-labelledby="navLang">

                    <button form="localeForm" class="btn dropdown-item" type="submit" name="locale"
                            value="ru">${locale_button_ru}
                    </button>
                    <div class="dropdown-divider"></div>
                    <button form="localeForm" class="btn btn-outline-danger dropdown-item" type="submit"
                            name="locale" value="en">${locale_button_en}
                    </button>

                </div>
            </li>
            <li class="nav-item ml-auto mr-auto">
                <a class="nav-link" href="Controller?command=go_to_about_page_command">${locale_about_page}</a>
            </li>
        </ul>
    </div>
</nav>

<form id="localeForm" action="Controller" method="post">
    <input type="hidden" name="command" value="change_locale_command">
</form>

<form id="headerForm" action="Controller" method="post"></form>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="sign_in" var="locale_sign_in"/>
<fmt:message key="sign_up" var="locale_sign_up"/>
<fmt:message key="sign_in_label" var="locale_sign_in_label"/>
<fmt:message key="user.login" var="locale_user_login"/>
<fmt:message key="user.password" var="locale_user_password"/>

<fmt:message key="${message}" var="locale_message"/>

<fmt:message key="from" var="locale_from"/>
<fmt:message key="to" var="locale_to"/>
<fmt:message key="letters" var="locale_letters"/>
<fmt:message key="digits" var="locale_digits"/>
<fmt:message key="symbols" var="locale_symbols"/>
<fmt:message key="format" var="locale_format"/>
<fmt:message key="and" var="locale_and"/>

<html>
<head>
    <title>PAY4J: ${locale_sign_in_label}</title>
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>

<form id="signUpForm" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_sign_up_command"/>
</form>

<div class="container">
    <div class="row justify-content-md-center">
        <div class="login_form col-md-6 text_block mt-5 pt-5 pb-5 pl-5 pr-5">

            <p class="login_label">${locale_sign_in_label.toUpperCase()}</p>

            <form action="Controller" method="post">
                <input type="hidden" name="command" value="sign_in_command"/>
                <div class="form-group">
                    <label for="loginInput">${locale_user_login}:</label>
                    <input type="text" class="form-control" name="login" id="loginInput" aria-describedby="emailHelp"
                           required pattern="${attribute_regexp_login}" placeholder="${locale_from} 6 ${locale_to} 16 ${locale_letters}/${locale_digits} ${locale_and} '_'">
                </div>
                <div class="form-group">
                    <label for="passwordInput">${locale_user_password}:</label>
                    <input type="password" class="form-control" name="password" id="passwordInput" required
                           pattern="${attribute_regexp_password}" placeholder="${locale_from} 6 ${locale_to} 18 ${locale_symbols}">
                </div>

                <div class="form-group mt-5">
                    <button type="submit" class="btn btn-success">${locale_sign_in.toUpperCase()}</button>
                </div>

                <div class="form-group">
                    <button form="signUpForm" type="submit"
                            class="btn btn-warning">${locale_sign_up.toUpperCase()}</button>
                </div>

            </form>

            <c:if test="${message != null}">
                <div class="mt-5">
                    <p class="message_label">${locale_message}</p>
                </div>
            </c:if>

        </div>
    </div>
</div>

<jsp:include page="/views/common/footer.jsp"/>
</body>
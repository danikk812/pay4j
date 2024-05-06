<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="editing" var="locale_editing"/>
<fmt:message key="save" var="locale_save"/>
<fmt:message key="do_sign_up" var="locale_do_sign_up"/>
<fmt:message key="user.login" var="locale_user_login"/>
<fmt:message key="user.name" var="locale_user_name"/>
<fmt:message key="user.surname" var="locale_user_surname"/>
<fmt:message key="user.patronymic" var="locale_user_patronymic"/>
<fmt:message key="input_password_to_confirm" var="locale_input_password_to_confirm"/>
<fmt:message key="user.birthdate" var="locale_user_birthdate"/>
<fmt:message key="user.phone_number" var="locale_user_phone_number"/>

<fmt:message key="from" var="locale_from"/>
<fmt:message key="to" var="locale_to"/>
<fmt:message key="letters" var="locale_letters"/>
<fmt:message key="digits" var="locale_digits"/>
<fmt:message key="symbols" var="locale_symbols"/>
<fmt:message key="format" var="locale_format"/>
<fmt:message key="and" var="locale_and"/>

<fmt:message key="${message}" var="locale_message"/>

<html>
<body>
<jsp:include page="/views/common/header.jsp"/>

<jsp:useBean id="user" scope="request" class="com.paramonau.pay4j.dto.user.UserBean"/>
<jsp:setProperty name="user" property="login" value="${sessionScope.user.login}"/>
<jsp:setProperty name="user" property="name" value="${sessionScope.user.name}"/>
<jsp:setProperty name="user" property="surname" value="${sessionScope.user.surname}"/>
<jsp:setProperty name="user" property="patronymic" value="${sessionScope.user.patronymic}"/>
<jsp:setProperty name="user" property="birthDate" value="${sessionScope.user.birthDate}"/>
<jsp:setProperty name="user" property="phone" value="${sessionScope.user.phone}"/>

<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-md-7">

            <div class="login_form text_block mt-3 pt-3 pb-3 pl-5 pr-5 mb-3">
                <p class="login_label">${locale_editing.toUpperCase()}</p>

                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="personal_edit_command"/>
                    <div class="form-group">
                        <label for="loginInput">${locale_user_login}:</label>
                        <input type="text" class="form-control" name="login" id="loginInput" value="${user.login}"
                               required pattern="${attribute_regexp_login}"
                               placeholder="${locale_from} 6 ${locale_to} 16 ${locale_letters}/${locale_digits} ${locale_and} '_'">
                    </div>
                    <div class="form-group">
                        <label for="nameInput">${locale_user_name}:</label>
                        <input type="text" class="form-control" name="name" id="nameInput" value="${user.name}" required
                               pattern="${attribute_regexp_fullname}"
                               placeholder="${locale_from} 2 ${locale_to} 20 ${locale_letters}">
                    </div>
                    <div class="form-group">
                        <label for="surnameInput">${locale_user_surname}:</label>
                        <input type="text" class="form-control" name="surname" id="surnameInput" value="${user.surname}"
                               required pattern="${attribute_regexp_fullname}"
                               placeholder="${locale_from} 2 ${locale_to} 20 ${locale_letters}">
                    </div>
                    <div class="form-group">
                        <label for="patromymicInput">${locale_user_patronymic}:</label>
                        <input type="text" class="form-control" name="patronymic" id="patromymicInput"
                               value="${user.patronymic}" required pattern="${attribute_regexp_fullname}"
                               placeholder="${locale_from} 2 ${locale_to} 20 ${locale_letters}">
                    </div>
                    <div class="form-group">
                        <label for="birthdateInput">${locale_user_birthdate}:</label>
                        <input type="date" class="form-control" name="birthdate" id="birthdateInput"
                               value="${user.birthDate}">
                    </div>
                    <div class="form-group">
                        <label for="phoneInput">${locale_user_phone_number}:</label>
                        <input type="tel" class="form-control" name="phone_number" id="phoneInput"
                               value="${user.phone}" required pattern="${attribute_regexp_phone_number}"
                               placeholder="${locale_format} +000123456789">
                    </div>
                    <hr class="separator" style="background-color: white">
                    <div class="form-group">
                        <label for="passwordInput">${locale_input_password_to_confirm}:</label>
                        <input type="password" class="form-control" name="password" id="passwordInput" required
                               pattern="${attribute_regexp_password}"
                               placeholder="${locale_from} 6 ${locale_to} 18 ${locale_symbols}">
                    </div>
                    <div class="form-group pt-3">
                        <button type="submit" class="btn btn-success">${locale_save}</button>
                    </div>
                </form>

                <c:if test="${message != null}">
                    <p class="message_label">${locale_message}</p>
                </c:if>

            </div>
        </div>
    </div>
</div>

<jsp:include page="/views/common/footer.jsp"/>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="admin_panel" var="locale_admin_panel"/>
<fmt:message key="admin.control_accounts" var="locale_control_accounts"/>
<fmt:message key="admin.control_orgs" var="locale_control_orgs"/>
<fmt:message key="admin.control_users" var="locale_control_users"/>
<fmt:message key="account" var="locale_account"/>
<fmt:message key="select" var="locale_select"/>
<fmt:message key="search_results" var="locale_search_results"/>
<fmt:message key="search" var="locale_search"/>
<fmt:message key="is_missing" var="locale_is_missing"/>
<fmt:message key="admin.input_user_fullname" var="locale_input_user_fullname"/>
<fmt:message key="owner" var="locale_owner"/>
<fmt:message key="user.birthdate" var="locale_user_birthdate"/>
<fmt:message key="user.phone_number" var="locale_phone_number"/>


<form id="searchUser" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_users_command">
</form>

<form id="selectUser" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_user_command">
</form>

<div class="container mt-3">
    <h1>${locale_control_users.toUpperCase()}</h1>

    <div class="card my-2">
        <div class="card-body d-flex">
            <input form="searchUser" style="width: 90%" type="text" class="form-control" required name="searchName" placeholder="${locale_input_user_fullname}" value="${searchName}">
            <input form="searchUser" type="submit" class="btn ml-auto btn-success" value="${locale_search.toUpperCase()}">
        </div>
    </div>

    <c:if test="${searchName != null}">
        <c:if test="${userList.size() == 0}">
            <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">${locale_search_results} "${searchName}"
                    ${locale_is_missing}</p>
        </c:if>
    </c:if>

    <c:forEach items="${userList}" var="user">
        <div class="card">
            <div class="card-body center_box" style="padding: 10px">
                <div>
                    <p class="mx-3 my-0">${user.name} ${user.surname} ${user.patronymic}</p>
                </div>
                <div>
                    <p class="mx-3 my-0">${locale_user_birthdate}: ${user.birthDate}</p>
                </div>
                <div>
                    <p class="mx-3 my-0">${locale_phone_number}: ${user.phone}</p>
                </div>
                <button form="selectUser" name="userId" type="submit" class="btn btn-info ml-auto mr-2" value="${user.id}">${locale_select}</button>
            </div>
        </div>
    </c:forEach>
</div>
</div>
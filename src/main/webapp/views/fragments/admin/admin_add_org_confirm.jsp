<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="admin.confirm_add_org" var="locale_confirm_add_org"/>
<fmt:message key="organization.name" var="locale_org_name"/>
<fmt:message key="organization.link_account" var="locale_org_link_account"/>
<fmt:message key="account" var="locale_account"/>
<fmt:message key="owner" var="locale_owner"/>
<fmt:message key="create" var="locale_create"/>

<form id="confirmAddOrg" action="Controller" method="post">
    <input type="hidden" name="command" value="add_org_command">
    <input type="hidden" name="name" value="${name}">
    <input type="hidden" name="accountId" value="${accountInfo.id}">
</form>
<div class="container mt-3">
    <h1>${locale_confirm_add_org.toUpperCase()}</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 50%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 50%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <div class="card">
        <div class="card-header">
            ${locale_org_name}:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">${name}</p>
            </div>
        </div>
    </div>
    <div class="card mt-2">
        <div class="card-header">
            ${locale_org_link_account}
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">${locale_account} №: ${accountInfo.id}</p>
                <p class="mb-0 mr-3">
                    ${locale_owner}: ${accountInfo.userSurname} ${accountInfo.userName} ${accountInfo.userPatronymic}</p>
            </div>
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <input form="confirmAddOrg" style="width: 30%" type="submit" class="btn ml-auto btn-success" value="${locale_create}">
        </div>
    </div>


</div>
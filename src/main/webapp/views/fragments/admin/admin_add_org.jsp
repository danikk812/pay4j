<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="admin.add_org" var="locale_add_org"/>
<fmt:message key="admin.input_org_name" var="locale_input_org_name"/>
<fmt:message key="admin.input_account_num" var="locale_input_account_num"/>
<fmt:message key="admin.control_orgs" var="locale_control_orgs"/>
<fmt:message key="continue" var="locale_сontinue"/>

<fmt:message key="${message}" var="locale_message"/>

<fmt:message key="from" var="locale_from"/>
<fmt:message key="to" var="locale_to"/>
<fmt:message key="symbols" var="locale_symbols"/>

<div class="container mt-3">
    <h1>${locale_add_org.toUpperCase()}</h1>
    <div class="progress">
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 50%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <form id="addOrgForm" action="Controller" method="post">
        <input type="hidden" name="command" value="go_to_add_org_confirm_command">

        <div class="card">
            <div class="card-body d-flex">
                <label class="my-auto" for="orgNameInput">${locale_input_org_name}</label>
                <input form="addOrgForm" style="width: 80%" type="text" class="mx-3 form-control" name="name" id="orgNameInput" required pattern="${attribute_regexp_org_name}" placeholder="${locale_from} 5 ${locale_to} 45 ${locale_symbols}">
            </div>
        </div>

        <div class="card mt-2">
            <div class="card-body d-flex">
                <label class="my-auto" for="accountInput">${locale_input_account_num}</label>
                <input form="addOrgForm" style="width: 80%" type="number" class="mx-3 form-control" name="accountId" id="accountInput" required>
            </div>
        </div>

        <div class="card mt-2">
            <div class="card-body d-flex">
                <input form="addOrgForm" style="width: 30%" type="submit" class="btn ml-auto btn-success" value="${locale_сontinue}">
            </div>
        </div>

        <c:if test="${message != null}">
        <div class="mt-2">
            <p class="message_label">${locale_message}</p>
        </div>
        </c:if>

</div>

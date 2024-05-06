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


<html>
<head>
    <title>PAY4J: ${locale_admin_panel}</title>
</head>
<body>
<jsp:include page="/views/common/header.jsp"/>


<div class="container bg-dark py-2 mt-5 label_window">
    <h3 class="mb-0 ml-3" style="color: white">${locale_admin_panel.toUpperCase()}</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-5">

    <div class="row justify-content-md-center">
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group mr-5" role="group" aria-label="First group">
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_admin_accounts_command">${locale_control_accounts}</a>
            </div>
            <div class="btn-group mr-5" role="group" aria-label="Second group">
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_admin_users_command">${locale_control_users}</a>
            </div>
            <div class="btn-group mr-5" role="group" aria-label="Third group">
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_admin_orgs_command">${locale_control_orgs}</a>
            </div>
        </div>
    </div>
    <jsp:include page="${admin_fragment}"/>

</div>

<jsp:include page="/views/common/footer.jsp"/>

</body>
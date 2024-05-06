<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>


<fmt:message key="account.accounts" var="locale_account_accounts"/>
<fmt:message key="account.creation_date" var="locale_creation_date"/>
<fmt:message key="control" var="locale_control"/>
<fmt:message key="account.blocked_account" var="locale_account_blocked_account"/>
<fmt:message key="account.active_account" var="locale_account_active_account"/>


<div class="container mt-3">

    <div class="d-flex account-header">
        <h1>${locale_account_accounts.toUpperCase()}</h1>
    </div>
    <form id="accountForm" action="Controller" method="post">
        <input type="hidden" name="command" value="go_to_admin_account_command"/>
    </form>

    <c:forEach items="${accounts}" var="account">

        <div class="card">
            <div class="card-header">
                <c:if test="${account.status.id == 1}">
                    ${locale_account_active_account}
                </c:if>
                <c:if test="${account.status.id == 2}">
                    ${locale_account_blocked_account}
                </c:if>
                №: ${account.getId()}
            </div>
            <div class="card-body center_box">
                <div>
                    <p class="m-0">${locale_creation_date}: ${account.creationDate}</p>
                </div>
                <button form="accountForm" name="accountId" type="submit" class="btn btn-info ml-auto mr-2"
                        value="${account.getId()}">${locale_control}
                </button>
            </div>
        </div>
    </c:forEach>
</div>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="account.control" var="locale_account_control"/>
<fmt:message key="data" var="locale_data"/>
<fmt:message key="status" var="locale_status"/>
<fmt:message key="num" var="locale_num"/>
<fmt:message key="control" var="locale_control"/>
<fmt:message key="account" var="locale_account"/>
<fmt:message key="owner" var="locale_owner"/>
<fmt:message key="account.creation_date" var="locale_account_creation_date"/>
<fmt:message key="account.status_active" var="locale_account_status_active"/>
<fmt:message key="account.status_blocked" var="locale_account_status_blocked"/>
<fmt:message key="account.status_deleted" var="locale_account_status_deleted"/>
<fmt:message key="account.block" var="locale_account_block"/>
<fmt:message key="account.unlock" var="locale_account_unlock"/>

<div class="container mt-3">
    <h1>${locale_account_control.toUpperCase()}</h1>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                ${locale_data}:
            </div>
            <div class="card-body display_block">
                <div>
                    <p class="card-text m-0">${locale_status}:
                        <c:if test="${account.status.id == 1}">
                            ${locale_account_status_active}
                        </c:if>
                        <c:if test="${account.status.id == 2}">
                            ${locale_account_status_blocked}
                        </c:if>
                        <c:if test="${account.status.id == 3}">
                            ${locale_account_status_deleted}
                        </c:if>
                    </p>
                </div>
                <div>
                    <p class="card-text m-0">${locale_account} №: <c:out value="${account.getId()}"/></p>
                </div>
                <div>
                    <p class="card-text m-0">${locale_account_creation_date}: <c:out
                            value="${account.getCreationDate()}"/></p>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                ${locale_control}:
            </div>
            <div class="card-body center_box btn-group-vertical">
                <form class="fullfill mb-0" id="accountControl" action="Controller" method="post">
                    <input type="hidden" name="accountId" value="${account.getId()}"/>
                    <input type="hidden" name="userId" value="${account.user}"/>
                    <button name="command" type="submit" class="btn btn-info control-button fullfill"
                            value="go_to_admin_user_command">${locale_owner}
                    </button>
                    <c:if test="${account.status.id == 1}">
                        <button name="command" type="submit" class="btn btn-warning control-button fullfill"
                                value="block_account_command">${locale_account_block}
                        </button>
                    </c:if>
                    <c:if test="${account.status.id == 2}">
                        <button name="command" type="submit" class="btn btn-success control-button fullfill"
                                value="unlock_account_command">${locale_account_unlock}
                        </button>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
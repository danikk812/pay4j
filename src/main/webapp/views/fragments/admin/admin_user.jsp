<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="user.control" var="locale_user_control"/>
<fmt:message key="data" var="locale_data"/>
<fmt:message key="user.user" var="locale_user"/>
<fmt:message key="status" var="locale_status"/>
<fmt:message key="control" var="locale_control"/>
<fmt:message key="user.status_user" var="locale_user_status_user"/>
<fmt:message key="user.status_admin" var="locale_user_status_admin"/>
<fmt:message key="user.birthdate" var="locale_user_birthdate"/>
<fmt:message key="user.phone_number" var="locale_user_phone_number"/>
<fmt:message key="user.user_accounts" var="locale_user_accounts"/>
<fmt:message key="user.grant_admin_rights" var="locale_user_grant_admin_rights"/>
<fmt:message key="user.revoke_admin_rights" var="locale_user_revoke_admin_rights"/>


<div class="container mt-3">
    <h1>${locale_user_control.toUpperCase()}</h1>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                ${locale_data}:
            </div>
            <div class="card-body display_block">
                <div>
                    <p class="card-text m-0">${locale_user}:
                        ${user.surname} ${user.name} ${user.patronymic}
                    </p>
                </div>
                <div>${locale_status}:
                    <c:if test="${user.status.id == 1}">
                        ${locale_user_status_user}
                    </c:if>
                    <c:if test="${user.status.id == 2}">
                        ${locale_user_status_admin}
                    </c:if>
                    <p class="card-text m-0">${locale_user_birthdate}: ${user.birthDate}</p>
                </div>
                <div>
                    <p class="card-text m-0">${locale_user_phone_number}: ${user.phone}</p>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                ${locale_control}:
            </div>
            <div class="card-body center_box btn-group-vertical">
                <form class="fullfill mb-0" id="userControl" action="Controller" method="post">
                    <input type="hidden" name="userId" value="${user.getId()}"/>

                    <button name="command" type="submit" class="btn btn-primary control-button fullfill"
                            value="go_to_admin_user_accounts_command">${locale_user_accounts}
                    </button>
                    <c:if test="${user.status.id == 1}">
                        <button name="command" type="submit" class="btn btn-info control-button fullfill"
                                value="grand_admin_rights_command">${locale_user_grant_admin_rights}
                        </button>
                    </c:if>
                    <c:if test="${user.status.id == 2}">
                        <button name="command" type="submit" class="btn btn-info control-button fullfill"
                                value="revoke_admin_rights_command">${locale_user_revoke_admin_rights}
                        </button>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
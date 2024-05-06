<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>


<fmt:message key="owner" var="locale_owner"/>
<fmt:message key="control" var="locale_control"/>
<fmt:message key="data" var="locale_data"/>
<fmt:message key="status" var="locale_status"/>
<fmt:message key="account" var="locale_account"/>
<fmt:message key="organization.control" var="locale_organization_control"/>
<fmt:message key="organization.status_active" var="locale_organization_status_active"/>
<fmt:message key="organization.status_blocked" var="locale_organization_status_blocked"/>
<fmt:message key="organization.linked_account" var="locale_organization_linked_account"/>
<fmt:message key="organization.control_account" var="locale_organization_control_account"/>
<fmt:message key="organization.edit" var="locale_organization_edit"/>
<fmt:message key="organization.block" var="locale_organization_block"/>
<fmt:message key="organization.unlock" var="locale_organization_unlock"/>
<fmt:message key="organization.delete" var="locale_organization_delete"/>
<fmt:message key="organization.account_not_linked" var="locale_organization_not_linked"/>
<fmt:message key="account.status_active" var="locale_account_status_active"/>
<fmt:message key="account.status_blocked" var="locale_account_status_blocked"/>
<fmt:message key="account.status_deleted" var="locale_account_status_deleted"/>

<fmt:message key="${message}" var="locale_message"/>

<div class="container mt-3">
    <h1>${locale_organization_control.toUpperCase()}</h1>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                ${locale_data}:
            </div>
            <div class="card-body display_block">
                <div>
                </div>
                <div>
                    <p class="card-text m-0">${organization.name}</p>
                </div>
                <p class="card-text m-0">${locale_status}:
                    <c:if test="${organization.status.id == 1}">
                        ${locale_organization_status_active}
                    </c:if>
                    <c:if test="${organization.status.id == 2}">
                        ${locale_organization_status_blocked}
                    </c:if>
                </p>
                <div>
                    <p class="card-text my-3 m-0">${locale_organization_linked_account}: </p>
                    <c:choose>
                        <c:when test="${accountInfo != null}">
                            <p class="card-text m-0">${locale_account} №: ${accountInfo.id}</p>
                            <p class="card-text m-0">${locale_owner}: ${accountInfo.userName} ${accountInfo.userSurname} ${accountInfo.userPatronymic}</p>
                            <c:if test="${accountInfo.status.id == 1}">
                                <p class="card-text m-0">${locale_status}: ${locale_account_status_active}</p>
                            </c:if>
                            <c:if test="${accountInfo.status.id == 2}">
                                <p class="card-text m-0">${locale_status}: ${locale_account_status_blocked}</p>
                            </c:if>
                            <c:if test="${accountInfo.status.id == 3}">
                                <p class="card-text m-0">${locale_status}: ${locale_account_status_deleted}</p>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <p class="card-text m-0">${locale_organization_not_linked}</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                ${locale_control}:
            </div>
            <div class="card-body center_box btn-group-vertical">
                <form class="fullfill mb-0" id="orgControl" action="Controller" method="post">
                    <input type="hidden" name="orgId" value="${organization.id}"/>
                    <input type="hidden" name="accountId" value="${accountInfo.id}"/>
                    <button name="command" type="submit" class="btn btn-primary control-button fullfill"
                            value="go_to_admin_account_command">${locale_organization_control_account}
                    </button>
                    <button name="command" type="submit" class="btn btn-info control-button fullfill"
                            value="go_to_edit_org_command">${locale_organization_edit}
                    </button>
                    <c:if test="${organization.status.id == 1}">
                        <button name="command" type="submit" class="btn btn-warning control-button fullfill"
                                value="block_org_command">${locale_organization_block}
                        </button>
                    </c:if>
                    <c:if test="${organization.status.id == 2}">
                        <button name="command" type="submit" class="btn btn-success control-button fullfill"
                                value="unlock_org_command">${locale_organization_unlock}
                        </button>
                    </c:if>
                    <button name="command" type="submit" class="btn btn-danger control-button fullfill"
                            value="delete_org_command">${locale_organization_delete}
                    </button>
                </form>
            </div>
        </div>
    </div>

    <c:if test="${message != null}">
        <div class="mt-2">
            <p class="message_label">${locale_message}</p>
        </div>
    </c:if>

</div>
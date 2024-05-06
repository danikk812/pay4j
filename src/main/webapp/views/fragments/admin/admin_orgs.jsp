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

<fmt:message key="admin.add_org" var="locale_add_org"/>
<fmt:message key="admin.input_org_name" var="locale_input_org_name"/>


<form id="searchOrg" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_orgs_command">
</form>

<form id="selectOrg" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_org_command">
</form>

<div class="container mt-3">
    <div class="d-flex">
        <h1>${locale_control_orgs.toUpperCase()}</h1>
        <a class="btn btn-warning ml-auto my-auto" href="Controller?command=go_to_add_org_command">${locale_add_org}</a>
    </div>
    <div class="card my-2">
        <div class="card-body d-flex">
            <input form="searchOrg" style="width: 90%" type="text" class="form-control" name="searchName"
                   placeholder="${locale_input_org_name}" value="${searchName}">
            <input form="searchOrg" type="submit" class="btn ml-auto btn-success" value="${locale_search.toUpperCase()}">
        </div>
    </div>

    <c:if test="${searchName != null}">
        <c:if test="${orgList.size() == 0}">
            <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">${locale_search_results} "${searchName}"
                    ${locale_is_missing}</p>
        </c:if>
    </c:if>

    <c:forEach items="${orgList}" var="organization">
        <div class="card">
            <div class="card-body center_box" style="padding: 10px">
                <div>
                    <p class="mx-3 my-0">${organization.name}</p>
                </div>
                <c:if test="${organization.account != null}">
                    <div>
                        <p class="mx-3 my-0">${locale_account} №: ${organization.account}</p>
                    </div>
                </c:if>
                <button form="selectOrg" name="orgId" type="submit" class="btn btn-info ml-auto mr-2"
                        value="${organization.id}">${locale_select}
                </button>
            </div>
        </div>
    </c:forEach>
</div>
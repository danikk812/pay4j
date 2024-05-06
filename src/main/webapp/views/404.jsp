<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="error.404" var="locale_error_404"/>
<fmt:message key="error.head" var="locale_error_head"/>
<fmt:message key="error.title" var="locale_error_title"/>

<html>
<head>
    <title>PAY4J: ${locale_error_head.toUpperCase()}!</title>
</head>
<body>
<jsp:include page="/views/common/header.jsp" />

<div class="container bg-dark py-2 mt-5 label_window">
    <h3 class="mb-0 ml-3" style="color: white">${locale_error_title}!</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-5">
    <div class="container mt-5">
        <h1 class="ml-5">${locale_error_head}! 404</h1>
        <h2 class="mt-5">${locale_error_404}</h2>
    </div>
</div>

<jsp:include page="/views/common/footer.jsp"/>
</body>
</html>
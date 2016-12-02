<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="errs" scope="page" value="${not empty errors ? errors : sessionScope.errors }"/>

<c:forEach items="${errs}" var="error">
    <p>${error}</p>
</c:forEach>
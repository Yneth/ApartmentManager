<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="requests" value="${page.content}" scope="page"/>
<h1><fmt:message key="requests" bundle="${locale}"/></h1>
<table class="table">
    <thead>
    <tr>
        <td>Id</td>
        <td><fmt:message key="request.roomCount" bundle="${locale}"/></td>
        <td><fmt:message key="apartment.type" bundle="${locale}"/></td>
        <td><fmt:message key="request.from" bundle="${locale}"/></td>
        <td><fmt:message key="request.to" bundle="${locale}"/></td>
        <td><fmt:message key="request.status" bundle="${locale}"/></td>
        <td><fmt:message key="view" bundle="${locale}"/></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requests}" var="request">
        <tr>
            <td>${request.id}</td>
            <td>${request.lookup.roomCount}</td>
            <td><fmt:message key="apartment.type.${request.lookup.type.name}" bundle="${locale}"/></td>
            <td>${request.from}</td>
            <td>${request.to}</td>
            <td><fmt:message key="request.status.${fn:toLowerCase(request.status)}" bundle="${locale}"/></td>
            <td><a class="btn btn-primary"
                   href="/${fn:toLowerCase(sessionScope.user.authority.name)}/request?id=${request.id}"
                   role="button"><fmt:message key="view" bundle="${locale}"/></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
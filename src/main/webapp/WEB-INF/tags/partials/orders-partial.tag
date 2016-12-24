<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="orders" value="${page.content}" scope="page"/>
<h1><fmt:message key="orders" bundle="${bundle}"/></h1>
<table class="table">
    <thead>
    <tr>
        <td>Id</td>
        <td><fmt:message key="apartment.name" bundle="${bundle}"/></td>
        <td><fmt:message key="request.id" bundle="${bundle}"/></td>
        <td><fmt:message key="order.price" bundle="${bundle}"/></td>
        <td><fmt:message key="order.payed" bundle="${bundle}"/></td>
        <td><fmt:message key="view" bundle="${bundle}"/></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.id}</td>
            <td>${order.apartment.id}</td>
            <td>${order.request.id}</td>
            <td>
                <fmt:setLocale value="en_US"/>
                <fmt:formatNumber type="CURRENCY" value="${order.price}"/>
                <fmt:setLocale value="${locale}"/>
            </td>
            <td><fmt:message key="${order.payed ? 'yes' : 'no'}" bundle="${bundle}"/></td>
            <td><a class="btn btn-primary"
                   href="/${fn:toLowerCase(sessionScope.user.authority.name)}/order?id=${order.id}"
                   role="button"><fmt:message key="view" bundle="${bundle}"/></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
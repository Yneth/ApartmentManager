<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h1>Orders</h1>
<table class="table">
    <thead>
    <tr>
        <td>Id</td>
        <td>Room count</td>
        <td>Apartment type</td>
        <td>View</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.id}</td>
            <td>${order.lookup.roomCount}</td>
            <td>${order.lookup.type.name}</td>
            <td><a class="btn btn-primary"
                   href="/${fn:toLowerCase(sessionScope.user.authority.name)}/order?id=${order.id}"
                   role="button">View</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
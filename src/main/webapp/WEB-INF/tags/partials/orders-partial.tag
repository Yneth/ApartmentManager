<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<h1>Orders</h1>
<table class="table">
    <thead>
    <tr>
        <td>Id</td>
        <td>Apartment name</td>
        <td>Request id</td>
        <td>Price</td>
        <td>Payed</td>
        <td>View</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.id}</td>
            <td>${order.apartment.id}</td>
            <td>${order.request.id}</td>
            <td>${order.price}</td>
            <td>${order.payed}</td>
            <td><a class="btn btn-primary"
                   href="/${fn:toLowerCase(sessionScope.user.authority.name)}/order?id=${order.id}"
                   role="button">View</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
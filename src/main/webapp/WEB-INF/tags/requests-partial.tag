<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h1>Requests</h1>
<table class="table">
    <thead>
    <tr>
        <td>Id</td>
        <td>Room count</td>
        <td>Apartment type</td>
        <td>From date</td>
        <td>To date</td>
        <td>Status</td>
        <td>View</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requests}" var="request">
        <tr>
            <td>${request.id}</td>
            <td>${request.lookup.roomCount}</td>
            <td>${request.lookup.type.name}</td>
            <td>${request.from}</td>
            <td>${request.to}</td>
            <td>${request.status}</td>
            <td><a class="btn btn-primary"
                   href="/${fn:toLowerCase(sessionScope.user.authority.name)}/request?id=${request.id}"
                   role="button">View</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
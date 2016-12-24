<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="javatime" tagdir="/WEB-INF/tags/time" %>

<c:set var="isAdmin" value="${not empty sessionScope.user && sessionScope.user.authority.name == 'ADMIN'}"/>
<c:set var="isSupersu" value="${not empty sessionScope.user && sessionScope.user.authority.name == 'SUPERSU'}"/>
<c:set var="requests" value="${page.content}" scope="page"/>
<h1><fmt:message key="requests" bundle="${bundle}"/></h1>
<table class="table">
    <thead>
    <tr>
        <td>Id</td>
        <td><fmt:message key="request.roomCount" bundle="${bundle}"/></td>
        <td><fmt:message key="apartment.type" bundle="${bundle}"/></td>
        <td><fmt:message key="request.from" bundle="${bundle}"/></td>
        <td><fmt:message key="request.to" bundle="${bundle}"/></td>
        <td><fmt:message key="request.status" bundle="${bundle}"/></td>
        <td><fmt:message key="view" bundle="${bundle}"/></td>
        <c:if test="${isAdmin}">
            <td><fmt:message key="order.reject" bundle="${bundle}"/></td>
        </c:if>
        <c:if test="${isSupersu}">
            <td><fmt:message key="delete" bundle="${bundle}"/></td>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requests}" var="request">
        <tr>
            <td>${request.id}</td>
            <td>${request.lookup.roomCount}</td>
            <td><fmt:message key="apartment.type.${request.lookup.type.name}" bundle="${bundle}"/></td>
            <td><javatime:parseLocalDate value="${request.from}"/></td>
            <td><javatime:parseLocalDate value="${request.to}"/></td>
            <td><fmt:message key="request.status.${fn:toLowerCase(request.status)}" bundle="${bundle}"/></td>
            <td><a class="btn btn-primary"
                   href="/${fn:toLowerCase(sessionScope.user.authority.name)}/request?id=${request.id}"
                   role="button"><fmt:message key="view" bundle="${bundle}"/></a>
            </td>
            <c:if test="${isAdmin}">
                <td>
                    <c:if test="${request.status == 'CREATED'}">
                        <form class="form-group" action="/admin/request/reject" method="POST">
                            <input type="hidden" name="id" value="${request.id}"/>
                            <input class="btn btn-danger" type="submit"
                                   value="<fmt:message key="order.reject" bundle="${bundle}"/>"/>
                        </form>
                    </c:if>
                </td>
            </c:if>
            <c:if test="${isSupersu}">
                <td>
                    <form class="form-group" action="/supersu/request/delete" method="POST">
                        <input type="hidden" name="id" value="${request.id}"/>
                        <input class="btn btn-danger" type="submit"
                               value="<fmt:message key="delete" bundle="${bundle}"/>"/>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
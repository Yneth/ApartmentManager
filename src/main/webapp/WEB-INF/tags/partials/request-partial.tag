<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="javatime" tagdir="/WEB-INF/tags/time" %>

<%@ attribute name="request" required="true" type="ua.abond.lab4.domain.Request" %>

<h1><fmt:message key="request" bundle="${bundle}"/></h1>
<p><fmt:message key="request.roomCount" bundle="${bundle}"/>: ${request.lookup.roomCount}</p>
<p>
    <fmt:message key="apartment.type" bundle="${bundle}"/>:
    <fmt:message key="apartment.type.${fn:toLowerCase(request.lookup.type.name)}" bundle="${bundle}"/>
</p>
<p><fmt:message key="request.from" bundle="${bundle}"/>
    : <javatime:parseLocalDate value="${request.from}"/>
</p>
<p><fmt:message key="request.to" bundle="${bundle}"/>
    : <javatime:parseLocalDate value="${request.to}"/>
</p>
<p>
    <fmt:message key="request.status" bundle="${bundle}"/>:
    <fmt:message key="request.status.${fn:toLowerCase(request.status)}" bundle="${bundle}"/>
</p>
<c:if test="${not empty request.statusComment}">
    <p><fmt:message key="request.status.comment" bundle="${bundle}"/>: ${request.statusComment}</p>
</c:if>

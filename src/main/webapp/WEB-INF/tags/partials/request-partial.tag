<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="request" required="true" type="ua.abond.lab4.domain.Request" %>

<h1><fmt:message key="request" bundle="${locale}"/></h1>
<p><fmt:message key="request.roomCount" bundle="${locale}"/>: ${request.lookup.roomCount}</p>
<p>
    <fmt:message key="apartment.type" bundle="${locale}"/>:
    <fmt:message key="apartment.type.${fn:toLowerCase(request.lookup.type.name)}" bundle="${locale}"/>
</p>
<p><fmt:message key="request.from" bundle="${locale}"/> : ${request.from}</p>
<p><fmt:message key="request.to" bundle="${locale}"/>: ${request.to}</p>
<p>
    <fmt:message key="request.status" bundle="${locale}"/>:
    <fmt:message key="request.status.${fn:toLowerCase(request.status)}" bundle="${locale}"/>
</p>
<c:if test="${not empty request.statusComment}">
    <p><fmt:message key="request.status.comment" bundle="${locale}"/>: ${request.statusComment}</p>
</c:if>

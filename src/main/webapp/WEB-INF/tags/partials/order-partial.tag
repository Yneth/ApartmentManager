<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="javatime" tagdir="/WEB-INF/tags/time" %>

<h1><fmt:message key="order" bundle="${bundle}"/></h1>
<p><fmt:message key="apartment.name" bundle="${bundle}"/>: ${order.apartment.name}</p>
<p><fmt:message key="apartment.roomCount" bundle="${bundle}"/>: ${order.apartment.roomCount}</p>
<p><fmt:message key="apartment.type" bundle="${bundle}"/>:
    <fmt:message key="apartment.type.${fn:toLowerCase(order.apartment.type.name)}" bundle="${bundle}"/>
</p>
<p><fmt:message key="request.from" bundle="${bundle}"/>
    : <javatime:parseLocalDate value="${order.request.from}"/>
</p>
<p><fmt:message key="request.to" bundle="${bundle}"/>
    : <javatime:parseLocalDate value="${order.request.to}"/>
</p>
<p><fmt:message key="order.payed" bundle="${bundle}"/>
    :<fmt:message key="${order.payed ? 'yes' : 'no'}" bundle="${bundle}"/>
</p>
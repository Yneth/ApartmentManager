<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<h1><fmt:message key="order" bundle="${locale}"/></h1>
<p><fmt:message key="apartment.name" bundle="${locale}"/>: ${order.apartment.name}</p>
<p><fmt:message key="apartment.roomCount" bundle="${locale}"/>: ${order.apartment.roomCount}</p>
<p><fmt:message key="apartment.type" bundle="${locale}"/>:
    <fmt:message key="apartment.type.${fn:toLowerCase(order.apartment.type.name)}" bundle="${locale}"/>
</p>
<p><fmt:message key="request.from" bundle="${locale}"/>: ${order.request.from}</p>
<p><fmt:message key="request.to" bundle="${locale}"/>: ${order.request.to}</p>
<p><fmt:message key="order.payed" bundle="${locale}"/>
    :<fmt:message key="${order.payed ? 'yes' : 'no'}" bundle="${locale}"/>
</p>
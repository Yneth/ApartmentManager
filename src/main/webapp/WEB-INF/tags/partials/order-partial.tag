<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<h1><fmt:message key="order" bundle="${locale}"/></h1>
<%--<p>Apartment name: ${order.request.lookup.name}</p>--%>
<%--<p>Apartment room count: ${order.request.lookup.roomCount}</p>--%>
<%--<p>Apartment type: ${order.lookup.type.name}</p>--%>
<p><fmt:message key="request.from" bundle="${locale}"/>: ${order.request.from}</p>
<p><fmt:message key="request.to" bundle="${locale}"/>: ${order.request.to}</p>
<p><fmt:message key="order.payed" bundle="${locale}"/>: ${order.payed}</p>
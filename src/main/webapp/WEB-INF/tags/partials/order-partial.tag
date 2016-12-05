<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<h1>Order</h1>
<%--<p>Apartment name: ${order.request.lookup.name}</p>--%>
<%--<p>Apartment room count: ${order.request.lookup.roomCount}</p>--%>
<%--<p>Apartment type: ${order.lookup.type.name}</p>--%>
<p>From date: ${order.request.from}</p>
<p>To date: ${order.request.to}</p>
<p>Payed: ${order.payed}</p>
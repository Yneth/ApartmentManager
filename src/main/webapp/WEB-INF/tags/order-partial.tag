<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="order" required="true" type="ua.abond.lab4.domain.Order" %>

<h1>Order</h1>
<p>Room count: ${order.lookup.roomCount}</p>
<p>Apartment type: ${order.lookup.type.name}</p>
<p>Duration: ${order.duration}</p>
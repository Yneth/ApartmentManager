<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="order" required="true" %>

<h1>Order</h1>
<p>Room count: ${order.lookUp.roomCount}</p>
<p>Apartment type; ${order.lookUp.apartmentType}</p>
<p>Duration: ${order.duration}</p>
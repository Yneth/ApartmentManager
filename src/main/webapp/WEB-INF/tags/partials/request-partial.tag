<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="request" required="true" type="ua.abond.lab4.domain.Request" %>

<h1>Request</h1>
<p>Room count: ${request.lookup.roomCount}</p>
<p>Apartment type: ${request.lookup.type.name}</p>
<p>From date: ${request.from}</p>
<p>To date: ${request.to}</p>
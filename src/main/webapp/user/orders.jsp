<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:user-page>
    <t:orders-partial/>
    <c:if test="${empty orders}">
        No orders yet, but you can make one <a href="/user/order/new">here</a>.
    </c:if>
</t:user-page>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <t:orders-partial/>
            <c:if test="${empty orders}">
                <p>No orders yet.</p>
            </c:if>
        </div>
    </div>
</t:admin-page>
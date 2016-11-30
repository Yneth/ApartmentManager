<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <t:orders-partial/>
                <c:if test="${empty orders}">
                    <p>No orders yet, but you can make one <a href="/user/order/new">here</a>.</p>
                </c:if>
            </div>
        </div>
    </div>
</t:user-page>
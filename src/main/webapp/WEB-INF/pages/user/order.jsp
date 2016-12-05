<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <p:order-partial/>
            <p:error-partial/>

            <c:if test="${not empty order.payed && !order.payed}">
                <form class="form-group" method="POST" action="/user/order/pay">
                    <input type="hidden" name="id" value="${order.id}"/>
                    <input class="form-control btn btn-success" type="submit" value="Pay"/>
                </form>
            </c:if>
        </div>
    </div>
</t:user-page>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <c:if test="${not empty order}">
                <t:order-partial order="${order}"/>
                <form class="form-group" action="/user/order/delete" method="post">
                    <input type="hidden" name="id" value="${order.id}"/>
                    <input class="form-control btn btn-danger" type="submit" value="Delete"/>
                </form>
            </c:if>
            <c:if test="${empty order}">
                <h1>No such order.</h1>
            </c:if>
        </div>
    </div>
</t:user-page>
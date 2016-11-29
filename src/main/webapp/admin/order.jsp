<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <c:if test="${not empty order}">
                <form class="form-group" method="post" action="/admin/order/confirm">
                    <input type="hidden" name="id" value="${order.id}"/>
                    <t:order-partial order="${order}"/>
                    <input class="form-control" type="text" name="cost"/>
                    <input class="form-control btn btn-success" type="submit" value="Confirm"/>
                </form>
            </c:if>
            <c:if test="${empty order}">
                <p>No such order.</p>
            </c:if>
        </div>
    </div>
</t:admin-page>
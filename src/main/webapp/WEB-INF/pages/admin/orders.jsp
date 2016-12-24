<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="orders" value="${page.content}" scope="page"/>
<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <h1><fmt:message key="orders" bundle="${bundle}"/></h1>
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td><fmt:message key="apartment.name" bundle="${bundle}"/></td>
                    <td><fmt:message key="request.id" bundle="${bundle}"/></td>
                    <td><fmt:message key="order.price" bundle="${bundle}"/></td>
                    <td><fmt:message key="order.payed" bundle="${bundle}"/></td>
                    <td><fmt:message key="order.pay" bundle="${bundle}"/></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.apartment.name}</td>
                        <td>${order.request.id}</td>
                        <td>${order.price}</td>
                        <td><fmt:message key="${order.payed ? 'yes' : 'no'}" bundle="${bundle}"/></td>
                        <td>
                            <c:if test="${not empty order.payed && !order.payed}">
                                <form class="form-group" method="POST" action="/admin/order/pay">
                                    <input type="hidden" name="id" value="${order.id}"/>
                                    <input class="form-control btn btn-success" type="submit"
                                           value="<fmt:message key="order.pay" bundle="${bundle}"/>"/>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty orders}">
                <p><fmt:message key="admin.orders.empty" bundle="${bundle}"/></p>
            </c:if>
            <p:pagination-partial uri="/admin/orders"/>
        </div>
    </div>
</t:admin-page>
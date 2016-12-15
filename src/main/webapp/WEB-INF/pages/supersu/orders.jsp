<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="orders" value="${page.content}"/>
<t:supersu-page>
    <div class="container">
        <div class="jumbotron">
            <h1><fmt:message key="orders" bundle="${locale}"/></h1>
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td><fmt:message key="apartment.name" bundle="${locale}"/></td>
                    <td><fmt:message key="request.id" bundle="${locale}"/></td>
                    <td><fmt:message key="order.price" bundle="${locale}"/></td>
                    <td><fmt:message key="order.payed" bundle="${locale}"/></td>
                    <td><fmt:message key="delete" bundle="${locale}"/></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.apartment.name}</td>
                        <td>${order.request.id}</td>
                        <td>${order.price}</td>
                        <td><fmt:message key="${order.payed ? 'yes' : 'no'}" bundle="${locale}"/></td>
                        <td>
                            <form class="form-group" action="/supersu/order/delete" method="POST">
                                <input type="hidden" name="id" value="${order.id}"/>
                                <input class="btn btn-danger" type="submit"
                                       value="<fmt:message key="delete" bundle="${locale}"/>"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty orders}">
                <p><fmt:message key="supersu.orders.empty" bundle="${locale}"/></p>
            </c:if>
            <p:pagination-partial uri="/supersu/orders"/>
        </div>
    </div>
</t:supersu-page>
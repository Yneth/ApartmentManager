<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="orders" value="${page.content}" scope="page"/>
<t:admin-page>
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
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.apartment.name}</td>
                        <td>${order.request.id}</td>
                        <td>${order.price}</td>
                        <td>${order.payed}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty orders}">
                <p>No orders yet.</p>
            </c:if>
            <p:pagination-partial uri="/admin/orders"/>
        </div>
    </div>
</t:admin-page>
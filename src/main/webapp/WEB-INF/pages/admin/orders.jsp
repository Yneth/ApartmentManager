<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <h1>Orders</h1>
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td>Apartment name</td>
                    <td>Request id</td>
                    <td>Price</td>
                    <td>Payed</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.apartment.id}</td>
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
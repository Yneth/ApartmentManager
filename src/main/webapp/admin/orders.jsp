<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:admin-page>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 center">
                <h1>Orders</h1>
                <table class="table">
                    <thead>
                    <tr>
                        <td>Id</td>
                        <td>Room count</td>
                        <td>Apartment type</td>
                        <td>View</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty orders}">
                        <c:forEach items="${orders}" var="order">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.roomCount}</td>
                                <td>${order.apartmentType.name}</td>
                                <td><a class="btn btn-primary" href="/admin/order/${order.id}">View</a></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
                <c:if test="${empty orders}">
                    <p>No orders yet.</p>
                </c:if>
            </div>
        </div>
    </div>
</t:admin-page>
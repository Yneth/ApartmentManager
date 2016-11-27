<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:site-page>
    <c:if test="${not empty orders && not empty sessionScope.user && sessionScope.user.authority.name == 'ADMIN' }">
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
                        <c:forEach items="${orders}" var="order">
                            <tr>
                                <td>order.id</td>
                                <td>order.roomCount</td>
                                <td>order.apartmentType.name</td>
                                <td><a class="btn btn-primary" href="/admin/order">View</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </c:if>
</t:site-page>
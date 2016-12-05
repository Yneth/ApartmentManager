<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <h1>Apartments</h1>
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td>Name</td>
                    <td>Room count</td>
                    <td>Apartment type</td>
                    <td>Price</td>
                    <td>Edit</td>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty apartments}">
                    <c:forEach items="${apartments}" var="apartment">
                        <tr>
                            <td>${apartment.id}</td>
                            <td>${apartment.name}</td>
                            <td>${apartment.roomCount}</td>
                            <td>${apartment.type.name}</td>
                            <td>${apartment.price}</td>
                            <td>
                                <a class="btn btn-primary" href="/admin/apartment?id=${apartment.id}">View</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <c:if test="${page.totalElements == 0}">
                <p>No apartments yet.</p>
            </c:if>
            <p:pagination-partial uri="/admin/apartments"/>
        </div>
    </div>

</t:admin-page>
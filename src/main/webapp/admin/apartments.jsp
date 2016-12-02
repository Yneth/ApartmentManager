<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <h1>Apartments</h1>
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td>Room count</td>
                    <td>Apartment type</td>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty apartments}">
                    <c:forEach items="${apartments}" var="apartment">
                        <tr>
                            <td>${apartment.id}</td>
                            <td>${apartment.roomCount}</td>
                            <td>${apartment.type.name}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <c:if test="${empty apartments}">
                <p>No apartments yet.</p>
            </c:if>
        </div>
    </div>
</t:admin-page>
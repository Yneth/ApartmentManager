<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:admin-page>
    <c:if test="${not empty apartments}">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 center">
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
                        <c:forEach items="${apartments}" var="apartment">
                            <tr>
                                <td>${apartment.id}</td>
                                <td>${apartment.roomCount}</td>
                                <td>${apartment.apartmentType.name}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </c:if>
</t:admin-page>
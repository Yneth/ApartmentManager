<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="apartments" value="${page.content}" scope="page"/>
<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <h1><fmt:message key="apartments" bundle="${bundle}"/></h1>
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td><fmt:message key="apartment.name" bundle="${bundle}"/></td>
                    <td><fmt:message key="apartment.roomCount" bundle="${bundle}"/></td>
                    <td><fmt:message key="apartment.type" bundle="${bundle}"/></td>
                    <td><fmt:message key="apartment.price" bundle="${bundle}"/></td>
                    <td><fmt:message key="edit" bundle="${bundle}"/></td>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty apartments}">
                    <c:forEach items="${apartments}" var="apartment">
                        <tr>
                            <td>${apartment.id}</td>
                            <td>${apartment.name}</td>
                            <td>${apartment.roomCount}</td>
                            <td>
                                <fmt:message key="apartment.type.${apartment.type.name}" bundle="${bundle}"/>
                            </td>
                            <td>${apartment.price}</td>
                            <td>
                                <a class="btn btn-primary" href="/admin/apartment?id=${apartment.id}">
                                    <fmt:message key="edit" bundle="${bundle}"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <c:if test="${empty apartments}">
                <p><fmt:message key="admin.apartments.empty" bundle="${bundle}"/></p>
            </c:if>
            <p:pagination-partial uri="/admin/apartments"/>
        </div>
    </div>

</t:admin-page>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="admins" value="${page.content}" scope="page"/>
<t:supersu-page>
    <div class="container">
        <div class="jumbotron">
            <h1><fmt:message key="admins" bundle="${bundle}"/></h1>
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td><fmt:message key="user.firstname" bundle="${bundle}"/></td>
                    <td><fmt:message key="user.lastname" bundle="${bundle}"/></td>
                    <td><fmt:message key="user.login" bundle="${bundle}"/></td>
                    <td><fmt:message key="view" bundle="${bundle}"/></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${admins}" var="admin">
                    <tr>
                        <td>${admin.id}</td>
                        <td>${admin.firstName}</td>
                        <td>${admin.lastName}</td>
                        <td>${admin.login}</td>
                        <td>
                            <form action="/supersu/admin/delete" method="POST">
                                <input type="hidden" name="id" value="${admin.id}"/>
                                <input class="btn btn-danger" type="submit"
                                       value="<fmt:message key="delete" bundle="${bundle}"/>"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty admins}">
                <p><fmt:message key="supersu.admins.empty" bundle="${bundle}"/></p>
            </c:if>
            <p:error-partial/>
            <p:pagination-partial uri="/supersu/admins"/>
        </div>
    </div>
</t:supersu-page>
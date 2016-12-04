<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:supersu-page>
    <div class="container">
        <div class="jumbotron">
            <h1>Admins</h1>
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td>First name</td>
                    <td>Last name</td>
                    <td>Login</td>
                    <td>View</td>
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
                                <input type="submit" value="Delete"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty admins}">
                <p>No admins yet.</p>
            </c:if>
            <p:error-partial/>
        </div>
    </div>
</t:supersu-page>
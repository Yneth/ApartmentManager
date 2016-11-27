<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:site-page>
    <c:if test="${not empty orders && not sessionScope.user && sessionScope.user.authority.name == 'ADMIN' }">
        <table class="table">
            <thead>
            <tr>
                <td></td>
                <td></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td></td>
                    <td></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</t:site-page>
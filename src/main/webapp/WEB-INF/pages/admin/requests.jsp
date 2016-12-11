<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="requests" value="${page.content}" scope="page"/>
<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <p:requests-partial/>
            <p:pagination-partial uri="/admin/requests"/>
            <c:if test="${empty requests}">
                <p>No orders yet.</p>
            </c:if>
        </div>
    </div>
</t:admin-page>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <p:order-partial/>
            <c:if test="${empty orders}">
                <p><fmt:message key="admin.orders.empty" bundle="${locale}"/></p>
            </c:if>
            <p:error-partial/>
        </div>
    </div>
</t:admin-page>
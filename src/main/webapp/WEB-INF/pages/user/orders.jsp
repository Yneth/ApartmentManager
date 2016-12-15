<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <p:orders-partial/>
            <c:if test="${empty page.content}">
                <p><fmt:message key="user.orders.empty" bundle="${locale}"/></p>
            </c:if>
            <p:pagination-partial uri="/user/orders"/>
            <p:error-partial/>
        </div>
    </div>
</t:user-page>
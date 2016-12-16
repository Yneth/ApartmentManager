<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="requests" value="${page.content}" scope="page"/>
<t:supersu-page>
    <div class="container">
        <div class="jumbotron">
            <p:requests-partial/>
            <p:pagination-partial uri="/supersu/requests"/>
            <c:if test="${empty requests}">
                <p><fmt:message key="admin.requests.empty" bundle="${locale}"/></p>
            </c:if>
        </div>
    </div>
</t:supersu-page>
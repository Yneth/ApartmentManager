<%@ tag description="Base page for super user" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:site-page>
    <c:if test="${not empty sessionScope.user && sessionScope.user.authority.name == 'SUPERSU'}">
        <jsp:body>
            <jsp:doBody/>
        </jsp:body>
    </c:if>
</t:site-page>
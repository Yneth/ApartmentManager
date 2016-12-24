<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <c:set var="requests" value="${page.content}" scope="page"/>

                <p:requests-partial/>
                <p:pagination-partial uri="/user/requests"/>
                <c:if test="${empty requests}">
                    <p>
                        <fmt:message key="user.requests.empty" bundle="${bundle}"/>
                    </p>
                </c:if>
                <p:error-partial/>
            </div>
        </div>
    </div>
</t:user-page>
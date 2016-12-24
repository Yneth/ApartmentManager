<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <c:if test="${not empty request}">
                <p:request-partial request="${request}"/>
                <c:if test="${request.status == 'CREATED'}">
                    <form class="form-group" action="/user/request/reject" method="post">
                        <input type="hidden" name="id" value="${request.id}"/>
                        <input class="form-control btn btn-danger" type="submit"
                               value="<fmt:message key="order.reject" bundle="${bundle}"/>"/>
                    </form>
                </c:if>
            </c:if>
            <p:error-partial/>
        </div>
    </div>
</t:user-page>
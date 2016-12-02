<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <c:if test="${not empty request}">
                <t:request-partial request="${request}"/>
                <form class="form-group" action="/user/request/reject" method="post">
                    <input type="hidden" name="id" value="${request.id}"/>
                    <input class="form-control btn btn-danger" type="submit" value="Reject"/>
                </form>
            </c:if>
            <c:if test="${empty request}">
                <h1>No such order.</h1>
            </c:if>
        </div>
    </div>
</t:user-page>
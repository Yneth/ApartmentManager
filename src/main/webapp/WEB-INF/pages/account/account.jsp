<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="sessionUser" value="${sessionScope.user}"/>
<t:site-page>
    <c:if test="${not empty sessionUser}">
        <div class="container">
            <div class="jumbotron">
                <div class="col-sm-10 center">
                    <h1>Account management page</h1>
                    <form class="center col-sm-5 form-group" action="/account/update" method="POST">
                        <input type="hidden" value="${user.id}"/>
                        <label for="first-name">
                            <fmt:message key="user.firstname" bundle="${locale}"/>
                        </label>
                        <input id="first-name" class="form-control" name="firstName"
                               value="${requestScope.user.firstName}"/>
                        <label for="last-name">
                            <fmt:message key="user.lastname" bundle="${locale}"/>
                        </label>
                        <input id="last-name" class="form-control" name="lastName"
                               value="${requestScope.user.lastName}"/>
                        <br/>
                        <input class="form-control btn btn-primary" type="submit"
                               value="<fmt:message key="update" bundle="${locale}"/>"/>
                    </form>
                </div>
            </div>
        </div>
    </c:if>
</t:site-page>
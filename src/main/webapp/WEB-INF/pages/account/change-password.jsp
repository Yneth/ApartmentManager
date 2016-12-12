<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:site-page>
    <c:set var="user" value="${sessionScope.user}"/>
    <c:if test="${not empty user}">
        <div class="container">
            <div class="jumbotron">
                <h1>Change password</h1>
                <form class="form-group" action="/account/password/change" method="POST">
                    <label for="old-password"><fmt:message key="user.password.old" bundle="${locale}"/></label>
                    <input id="old-password" class="form-control" type="password" name="oldPassword"/>
                    <label for="new-password"><fmt:message key="user.password.new" bundle="${locale}"/></label>
                    <input id="new-password" class="form-control" type="password" name="newPassword"/>
                    <label for="new-password-copy">
                        <fmt:message key="user.password.new.copy" bundle="${locale}"/>
                    </label>
                    <input id="new-password-copy" class="form-control" type="password" name="newPasswordCopy"/>
                    <input class="btn btn-success" type="submit"
                           value="<fmt:message key="update" bundle="{locale}"/>"/>
                </form>
            </div>
        </div>
    </c:if>
</t:site-page>
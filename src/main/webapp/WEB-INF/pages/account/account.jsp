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
                    <h1>
                        <fmt:message key="account.message" bundle="${locale}"/>
                    </h1>
                    <div class="center col-sm-5">
                        <h2>
                            <fmt:message key="account.update" bundle="${locale}"/>
                        </h2>
                        <form class="form-group" action="/account/update" method="POST">
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
                        <h2>
                            <fmt:message key="account.change.password" bundle="${locale}"/>
                        </h2>
                        <form class="form-group" action="/account/password/change" method="POST">
                            <label for="old-password"><fmt:message key="user.password.old" bundle="${locale}"/></label>
                            <input id="old-password" class="form-control" type="password" name="oldPassword"/>
                            <label for="new-password"><fmt:message key="user.password.new" bundle="${locale}"/></label>
                            <input id="new-password" class="form-control" type="password" name="newPassword"/>
                            <label for="new-password-copy">
                                <fmt:message key="user.password.new.copy" bundle="${locale}"/>
                            </label>
                            <input id="new-password-copy" class="form-control" type="password" name="newPasswordCopy"/>
                            <br/>
                            <input class="form-control btn btn-success" type="submit"
                                   value="<fmt:message key="update" bundle="${locale}"/>"/>
                        </form>
                        <p:error-partial/>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</t:site-page>
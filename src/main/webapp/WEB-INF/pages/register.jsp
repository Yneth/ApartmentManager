<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:site-page>
    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <form class="col-sm-5 center form-group" method="post" action="/register">
                    <label for="login-input">
                        <fmt:message key="login" bundle="${bundle}"/>:
                    </label>
                    <input class="form-control" id="login-input" type="text" name="login"
                           value="${user.login}"/>

                    <label for="password-input">
                        <fmt:message key="password" bundle="${bundle}"/>:
                    </label>
                    <input class="form-control" id="password-input" type="password" name="password"/>

                    <label for="first-name-input">
                        <fmt:message key="register.firstname" bundle="${bundle}"/>:
                    </label>
                    <input class="form-control" id="first-name-input" type="text" name="firstName"
                           value="${user.firstName}"/>

                    <label for="last-name-input">
                        <fmt:message key="register.lastname" bundle="${bundle}"/>:
                    </label>
                    <input class="form-control" id="last-name-input" type="text" name="lastName"
                           value="${user.lastName}"/>

                    <br/>
                    <input class="btn btn-primary form-control" type="submit"
                           value="<fmt:message key="register" bundle="${bundle}"/>"/>
                </form>
                <p:error-partial/>
            </div>
        </div>
    </div>
</t:site-page>
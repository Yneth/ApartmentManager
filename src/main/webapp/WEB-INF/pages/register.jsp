<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
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
                        <fmt:message key="login" bundle="${locale}"/>:
                    </label>
                    <input class="form-control" id="login-input" type="text" name="login"/>

                    <label for="password-input">
                        <fmt:message key="password" bundle="${locale}"/>:
                    </label>
                    <input class="form-control" id="password-input" type="password" name="password"/>

                    <label for="first-name-input">
                        <fmt:message key="register.firstname" bundle="${locale}"/>:
                    </label>
                    <input class="form-control" id="first-name-input" type="text" name="firstName"/>

                    <label for="last-name-input">
                        <fmt:message key="register.firstname" bundle="${locale}"/>:
                    </label>
                    <input class="form-control" id="last-name-input" type="text" name="lastName"/>

                    <input class="btn btn-primary form-control" type="submit" value="Register"/>
                </form>
                <p:error-partial/>
            </div>
        </div>
    </div>
</t:site-page>
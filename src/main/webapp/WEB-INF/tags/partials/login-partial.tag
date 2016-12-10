<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<div class="container">
    <div class="jumbotron">
        <div class="row">
            <form class="form-group col-sm-5 center" method="post" action="/login">
                <label for="login-input">
                    <fmt:message key="login" bundle="${locale}"/>:
                </label>
                <input class="form-control" id="login-input" type="text" name="login"/>
                <label for="password-input">
                    <fmt:message key="password" bundle="${locale}"/>:
                </label>
                <input class="form-control" id="password-input" type="password" name="password"/>
                <br/>
                <input class="form-control btn btn-primary" type="submit"
                       value="<fmt:message key="login" bundle="${locale}"/>"/>
                <a class="btn-right" href="/register">
                    <fmt:message key="register" bundle="${locale}"/>
                </a>
            </form>
        </div>
        <p:error-partial/>
    </div>
</div>
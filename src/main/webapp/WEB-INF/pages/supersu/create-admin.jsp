<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:supersu-page>
    <div class="container">
        <div class="jumbotron">
            <form class="col-sm-5 center form-group" method="post" action="/supersu/admin/new">
                <label for="login-input"><fmt:message key="login" bundle="${locale}"/>: </label>
                <input class="form-control" id="login-input" type="text" name="login"/>

                <label for="password-input"><fmt:message key="password" bundle="${locale}"/>: </label>
                <input class="form-control" id="password-input" type="password" name="password"/>

                <label for="first-name-input"><fmt:message key="register.firstname" bundle="${locale}"/>: </label>
                <input class="form-control" id="first-name-input" type="text" name="firstName"/>

                <label for="last-name-input"><fmt:message key="register.lastname" bundle="${locale}"/>: </label>
                <input class="form-control" id="last-name-input" type="text" name="lastName"/>
                <br>
                <input class="btn btn-primary form-control" type="submit"
                       value="<fmt:message key="create" bundle="${locale}"/>"/>
            </form>
            <p:error-partial/>
        </div>
    </div>
</t:supersu-page>
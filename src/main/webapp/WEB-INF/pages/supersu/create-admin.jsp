<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:supersu-page>
    <div class="container">
        <div class="jumbotron">
            <form class="col-sm-5 center form-group" method="post" action="/supersu/admin/new">
                <label for="login-input">Login: </label>
                <input class="form-control" id="login-input" type="text" name="login"/>

                <label for="password-input">Password: </label>
                <input class="form-control" id="password-input" type="password" name="password"/>

                <label for="first-name-input">First Name: </label>
                <input class="form-control" id="first-name-input" type="text" name="firstName"/>

                <label for="last-name-input">Last Name: </label>
                <input class="form-control" id="last-name-input" type="text" name="lastName"/>

                <input class="btn btn-primary form-control" type="submit" value="Register"/>
            </form>
            <p:error-partial/>
        </div>
    </div>
</t:supersu-page>
<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<div class="container">
    <div class="jumbotron">
        <div class="row">
            <form class="form-group col-sm-5 center" method="post" action="/login">
                <label for="login-input">Login:</label>
                <input class="form-control" id="login-input" type="text" name="login"/>
                <label for="password-input">Password:</label>
                <input class="form-control" id="password-input" type="password" name="password"/>
                <input class="form-control btn btn-primary" type="submit" name="Login"/>
                <a class="btn-right" href="/register">Register</a>
            </form>
        </div>
    </div>
</div>
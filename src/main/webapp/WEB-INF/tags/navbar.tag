<%@ tag description="ApartmentOrder navigation bar tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<nav class="navbar navbar-default navbar-static-top ">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">ApartmentOrder</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/">Home</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:if test="${not empty sessionScope.user}">
                <li><a href="/logout">Logout</a></li>
            </c:if>
            <c:if test="${empty sessionScope.user}">
                <li><a href="/register">Register</a></li>
                <li><a href="/login">Login</a></li>
            </c:if>
        </ul>
    </div>

</nav>
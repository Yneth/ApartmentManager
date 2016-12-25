<%@ tag description="ApartmentOrder navigation bar tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<nav class="navbar navbar-static-top ">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                aria-expanded="false" aria-controls="navbar">
            <span class="sr-only"><fmt:message key="navigation.toggle" bundle="${bundle}"/></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/"><fmt:message key="project.name" bundle="${bundle}"/></a>
    </div>
    <ul class="nav navbar-nav navbar-right">
        <c:if test="${not empty sessionScope.user}">
            <li>
                <a href="/account"><fmt:message key="account" bundle="${bundle}"/></a>
            </li>
            <li>
                <a href="/logout"><fmt:message key="logout" bundle="${bundle}"/></a>
            </li>
        </c:if>
        <c:if test="${empty sessionScope.user}">
            <li>
                <a href="/register"><fmt:message key="register" bundle="${bundle}"/></a>
            </li>
            <li><a href="/login"><fmt:message key="login" bundle="${bundle}"/></a></li>
        </c:if>
    </ul>
</nav>
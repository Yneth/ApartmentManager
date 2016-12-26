<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <h1><fmt:message key="index.hello" bundle="${bundle}"/>, ${sessionScope.user.login}.</h1>
            <p><fmt:message key="index.user.hello.message" bundle="${bundle}"/>.</p>
            <p>
                <a class="btn btn-primary" href="/user/orders" role="button">
                    <fmt:message key="index.user.view.orders" bundle="${bundle}"/> »
                </a>
            </p>
            <p>
                <a class="btn btn-primary" href="/user/requests" role="button">
                    <fmt:message key="index.user.view.requests" bundle="${bundle}"/> »
                </a>
            </p>
            <p>
                <a class="btn btn-primary" href="/user/request/new" role="button">
                    <fmt:message key="index.user.create.request" bundle="${bundle}"/> »
                </a>
            </p>
            <p>
                <a class="btn btn-primary" href="/user/apartments" role="button">
                    <fmt:message key="index.user.view.apartments" bundle="${bundle}"/>
                </a>
            </p>
        </div>
    </div>
</t:user-page>

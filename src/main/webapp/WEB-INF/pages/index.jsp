<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:site-page>
    <c:if test="${empty sessionScope.user}">
        <p:login-partial/>
    </c:if>
    <c:if test="${not empty sessionScope.user}">
        <div class="container">
            <div class="jumbotron">
                <c:if test="${sessionScope.user.authority.name == 'ADMIN'}">
                    <h1><fmt:message key="index.hello" bundle="${locale}"/>, ${sessionScope.user.login}</h1>
                    <p>
                        <fmt:message key="index.admin.hello.message" bundle="${locale}"/>.
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/admin/orders" role="button">
                            <fmt:message key="index.admin.view.orders" bundle="${locale}"/> »
                        </a>
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/admin/requests" role="button">
                            <fmt:message key="index.admin.view.requests" bundle="${locale}"/> »
                        </a>
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/admin/apartments" role="button">
                            <fmt:message key="index.admin.view.apartments" bundle="${locale}"/> »
                        </a>
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/admin/apartment/new" role="button">
                            <fmt:message key="index.admin.create.apartment" bundle="${locale}"/> »
                        </a>
                    </p>
                </c:if>
                <c:if test="${sessionScope.user.authority.name == 'USER'}">
                    <h1><fmt:message key="index.hello" bundle="${locale}"/>, ${sessionScope.user.login}.</h1>
                    <p><fmt:message key="index.user.hello.message" bundle="${locale}"/>.</p>
                    <p>
                        <a class="btn btn-primary" href="/user/orders" role="button">
                            <fmt:message key="index.user.view.orders" bundle="${locale}"/> »
                        </a>
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/user/requests" role="button">
                            <fmt:message key="index.user.view.requests" bundle="${locale}"/> »
                        </a>
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/user/request/new" role="button">
                            <fmt:message key="index.user.create.request" bundle="${locale}"/> »
                        </a>
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/user/apartments" role="button">
                            <fmt:message key="index.user.view.apartments" bundle="${locale}"/>
                        </a>
                    </p>
                </c:if>
                <c:if test="${sessionScope.user.authority.name == 'SUPERSU'}">
                    <h1>Hi, ${sessionScope.user.login}.</h1>
                    <p><fmt:message key="index.supersu.hello.message" bundle="${locale}"/>.</p>
                    <p>
                        <a class="btn btn-primary" href="/supersu/admin/new" role="button">
                            <fmt:message key="index.supersu.create.admin" bundle="${locale}"/> »
                        </a>
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/supersu/admins" role="button">
                            <fmt:message key="index.supersu.view.admins" bundle="${locale}"/> »
                        </a>
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/supersu/orders" role="button">
                            <fmt:message key="index.supersu.edit.orders" bundle="${locale}"/> »
                        </a>
                    </p>
                    <p>
                        <a class="btn btn-primary" href="/supersu/requests" role="button">
                            <fmt:message key="index.admin.view.requests" bundle="${locale}"/> »
                        </a>
                    </p>
                </c:if>
            </div>
        </div>
    </c:if>
</t:site-page>
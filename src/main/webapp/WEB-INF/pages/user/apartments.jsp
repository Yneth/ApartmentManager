<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <div class="col-sm-5 center">
                <form class="form-group" action="/user/apartments" method="GET">
                    <label for="from-datetime-input">
                        <fmt:message key="request.from" bundle="${locale}"/>:
                    </label>
                    <input class="form-control" id="from-datetime-input" type="datetime-local" name="from"
                           value="${from}"/>

                    <label for="to-datetime-input">
                        <fmt:message key="request.to" bundle="${locale}"/>:
                    </label>
                    <input class="form-control" id="to-datetime-input" type="datetime-local" name="to"
                           value="${to}"/>
                    <input class="btn btn-primary" type="submit"
                           value="<fmt:message key="filter" bundle="${locale}"/>"/>
                </form>
            </div>

            <c:set var="apartments" value="${page.content}" scope="page"/>
            <h1><fmt:message key="apartments" bundle="${locale}"/></h1>
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td><fmt:message key="apartment.name" bundle="${locale}"/></td>
                    <td><fmt:message key="apartment.roomCount" bundle="${locale}"/></td>
                    <td><fmt:message key="apartment.type" bundle="${locale}"/></td>
                    <td><fmt:message key="apartment.price" bundle="${locale}"/></td>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty apartments}">
                    <c:forEach items="${apartments}" var="apartment">
                        <tr>
                            <td>${apartment.id}</td>
                            <td>${apartment.name}</td>
                            <td>${apartment.roomCount}</td>
                            <td>
                                <fmt:message key="apartment.type.${apartment.type.name}" bundle="${locale}"/>
                            </td>
                            <td>${apartment.price}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>

            <c:if test="${empty page.content}">
                <p><fmt:message key="user.apartments.empty" bundle="${locale}"/></p>
            </c:if>
            <p:pagination-partial uri="/user/apartments" params="from=${from}&to=${to}"/>
            <p:error-partial/>
        </div>
    </div>
</t:user-page>
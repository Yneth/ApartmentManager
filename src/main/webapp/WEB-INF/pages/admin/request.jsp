<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<c:set var="apartments" value="${page.content}" scope="page"/>
<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <c:if test="${not empty request}">
                <form class="form-group" method="post" action="/admin/request/confirm">
                    <input type="hidden" name="id" value="${request.id}"/>
                    <input type="hidden" name="userId" value="${sessionScope.user.id}"/>

                    <p:request-partial request="${request}"/>
                    <c:if test="${request.status == 'CREATED'}">
                        <c:if test="${empty apartments}">
                            <p>
                                <fmt:message key="admin.request.confirm.apartments.null" bundle="${bundle}"/>
                            </p>
                        </c:if>
                        <c:if test="${not empty apartments}">
                            <c:forEach items="${apartments}" var="apartment">
                                <div class="radio">
                                    <label><input type="radio" name="apartmentId" value="${apartment.id}"/>
                                            ${apartment.name}
                                    </label>
                                </div>
                            </c:forEach>
                            <br>
                            <input class="form-control btn btn-success" type="submit"
                                   value="<fmt:message key="request.confirm" bundle="${bundle}"/>"/>
                        </c:if>
                    </c:if>
                </form>
            </c:if>
            <p:error-partial/>
        </div>
    </div>
</t:admin-page>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

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
                            <p>No appropriate apartments.</p>
                        </c:if>
                        <c:if test="${not empty apartments}">
                            <label for="cost-text">Price</label>
                            <input class="form-control" id="cost-text" type="text" name="price"/>

                            <c:forEach items="${apartments}" var="apartment">
                                <div class="radio">
                                    <label><input type="radio" name="apartmentId" value="${apartment.id}"/>
                                            ${apartment.type.name}
                                    </label>
                                </div>
                            </c:forEach>
                            <input class="form-control btn btn-success" type="submit" value="Confirm"/>
                        </c:if>
                    </c:if>
                </form>
            </c:if>
            <p:error-partial/>
        </div>
    </div>
</t:admin-page>
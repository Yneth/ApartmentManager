<%@ tag description="" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<div class="container">
    <div class="jumbotron">
        <form class="form-group col-sm-5 center" method="post" action="/user/request/new">
            <label for="room-count-input">Room count:</label>
            <input class="form-control" id="room-count-input" type="text" name="roomCount"/>
            <label for="apartment-type-select">Apartment type:</label>
            <select class="form-control" id="apartment-type-select" name="apartmentTypeId">
                <c:forEach items="${apartmentTypes}" var="apartmentType">
                    <option value="${apartmentType.id}">${apartmentType.name}</option>
                </c:forEach>
            </select>
            <label for="from-datetime-input">From date:</label>
            <input class="form-control" id="from-datetime-input" type="datetime-local" name="from"/>
            <label for="to-datetime-input">To date:</label>
            <input class="form-control" id="to-datetime-input" type="datetime-local" name="to"/>
            <input class="form-control btn btn-primary" type="submit" name="Make order"/>
        </form>
        <c:if test="${not empty errors}">
            <c:forEach items="${errors}" var="error">
                <p>${error}</p>
            </c:forEach>
        </c:if>
    </div>
</div>
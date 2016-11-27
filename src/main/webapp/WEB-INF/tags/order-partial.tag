<%@ tag description="" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<div class="container row">
    <form class="form-group col-sm-5 center" method="post" action="/order">
        <label for="room-count-input">Room count:</label>
        <input class="form-control" id="room-count-input" type="text" name="roomCount"/>
        <label for="apartment-type-input">Apartment type:</label>
        <input class="form-control" id="apartment-type-input" type="text" name="apartmentType"/>
        <input class="form-control btn btn-primary" type="submit" name="Make order"/>
    </form>
</div>
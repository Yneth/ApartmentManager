<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:site-page>
    <script>var dates = [];</script>

    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <div class="box filter-box clearfix">
                    <div class="filter-item dates input-daterange clearfix">
                        <div class="filter-label"><fmt:message key="search.filter.dates" bundle="${bundle}"/></div>
                        <input class="date-input" type="text" name="from"
                               placeholder="<fmt:message key="search.filter.check.in" bundle="${bundle}"/> "/>
                        <input class="date-input" type="text" name="to"
                               placeholder="<fmt:message key="search.filter.check.out" bundle="${bundle}"/> "/>
                    </div>
                    <div class="filter-item rooms clearfix">
                        <div class="filter-label"><fmt:message key="search.filter.rooms" bundle="${bundle}"/></div>
                        <select name="roomCount">
                            <c:forEach begin="1" end="16" var="i">
                                <option value="${i}">
                                        ${i}
                                    <fmt:message key="${i > 1 ? 'apartment.room.plural' : 'apartment.room'}"
                                                 bundle="${bundle}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filter-item room-types clearfix">
                        <div class="row">
                            <div class="col-sm-4 col-lg-3">
                                <div class="filter-label">
                                    <fmt:message key="search.filter.room.types" bundle="${bundle}"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <label><input type="checkbox" name="roomType" value="1"/>Entire Home</label>
                            </div>
                            <div class="col-sm-4">
                                <label><input type="checkbox" name="roomType" value="2"/>Private Room</label>
                            </div>
                            <div class="col-sm-4">
                                <label><input type="checkbox" name="roomType" value="3"/>Shared Room</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:site-page>
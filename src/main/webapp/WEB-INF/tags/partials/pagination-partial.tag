<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>
<%@ attribute name="uri" required="true" %>

<c:if test="${pageCount > 0}">
    <ul class="pagination">
        <c:forEach begin="1" end="${pageCount}" var="index">
            <li class="${(param.page == index) || (param.page == null && index == 1) ? 'active' : ''}">
                <a href="${uri}?page=${index}">${index}</a>
            </li>
        </c:forEach>
    </ul>
</c:if>

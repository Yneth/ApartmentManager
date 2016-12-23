<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>
<%@ attribute name="uri" required="true" %>
<%@ attribute name="params" required="false" %>

<c:set var="pageCount" value="${page.totalPages}" scope="page"/>
<c:if test="${pageCount > 0}">
    <ul class="pagination">
        <c:forEach begin="1" end="${pageCount}" var="index">
            <li class="${(param.page == index) || (empty param.page && index == 1) ? 'active' : ''}">
                <a href="${uri}?page=${index}&${params}">${index}</a>
            </li>
        </c:forEach>
    </ul>
</c:if>

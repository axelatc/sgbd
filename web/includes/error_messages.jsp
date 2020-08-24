<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty systemErrorMessages && systemErrorMessages.size() > 0}">
    <div id="systemErrorMessages" class="alert alert-danger" role="alert">
        <ul>
            <c:forEach items="${systemErrorMessages}" var="item">
                <li>${item}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

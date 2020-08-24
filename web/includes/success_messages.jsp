<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty systemSuccessMessages && systemSuccessMessages.size() > 0}">
    <div id="systemSuccessMessages" class="alert alert-success" role="alert">
        <ul>
            <c:forEach items="${systemSuccessMessages}" var="item">
                <li>${item}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty formErrorMessages && formErrorMessages.size() > 0}">
    <div id="formErrorMessages" class="alert alert-danger" role="alert">
        <ul>
            <c:forEach items="${formErrorMessages}" var="item">
                <li>${item}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

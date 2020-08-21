<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p id="errorMessages">
    <c:forEach items="${errorMessages}" var="item">
        <span>${item}</span>
    </c:forEach>
</p>
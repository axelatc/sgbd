<div class="roles-edit-form">
    <form action="${pageContext.request.contextPath}/roles/edit" method="post">
        <h2 class="text-center">Edit new role</h2>
        <jsp:include page="/WEB-INF/includes/form_error_messages.jsp"></jsp:include>
        <input name="editID" type="hidden" value="${role.id}">
        <div class="form-group">
            <label for="label">label</label>
            <input name="label" value="${role.label}"
                   id="label"
                   type="text" class="form-control"
                   placeholder="label" required="required">
        </div>
        <div class="form-group">
            <label for="description">description</label>
            <input name="description" value="${role.description}"
                   id="description"
                   type="textarea" class="form-control"
                   placeholder="description" required="required">
        </div>

        <c:if test="${authorities.size() > 0}">
            <c:forEach items="${authorities}" var="authority">
                <div class="form-check">
                    <input class="form-check-input" <c:if test="${role.authorities.contains(authority)}">checked="true"</c:if>
                           type="checkbox" value="${authority.id}"
                           id="selected-authorities"
                            name="selected-authorities">
                    <label class="form-check-label" for="selected-authorities">
                            ${authority.label}
                    </label>
                </div>
            </c:forEach>
        </c:if>

        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Save</button>
        </div>

    </form>
</div>


<div class="workers-edit-form">
    <form action="${pageContext.request.contextPath}/workers/edit" method="post">
        <h2 class="text-center">Edit new worker</h2>
        <jsp:include page="/WEB-INF/includes/form_error_messages.jsp"></jsp:include>
        <input name="editID" type="hidden" value="${param.editID}">
        <div class="form-group">
            <label for="firstName">firstName</label>
            <input name="firstName" value="${worker.firstName}"
                   id="firstName"
                   type="text" class="form-control"
                   placeholder="firstname" required="required">
        </div>
        <div class="form-group">
            <label for="lastName">lastName</label>
            <input name="lastName" value="${worker.lastName}"
                   id="lastName"
                   type="text" class="form-control"
                   placeholder="lastName" required="required">
        </div>
        <div class="form-group">
            <label for="username">username</label>
            <input name="username" value="${worker.username}"
                   id="username"
                   type="text" class="form-control"
                   placeholder="username" required="required">
        </div>
        <%--        <div class="form-group">
                    <label for="password">password</label>
                    <input name="password" value="${worker.password}"
                           id="password"
                           type="password" class="form-control"
                           placeholder="Password" required="required">
                </div>--%>
        <div class="form-group">
            <label for="birthdate">birthdate</label>
            <input name="birthdate" value="${worker.birthdate}"
                   id="birthdate"
                   type="date" class="form-control"
                   placeholder="birthdate" required="required">
        </div>
        <div class="form-group">
            <label for="selected-role">role</label>
            <select class="custom-select" name="selected-role" id="selected-role">
                <c:if test="${roles.size() > 0}">
                    <c:forEach items="${roles}" var="role">
                        <option
                                <c:if test="worker.role == role">selected="selected"</c:if>
                                value="${role.id}">${role.label}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>


        <div class="form-group">
            <label for="selected-team">team</label>
            <select class="custom-select" name="selected-team" id="selected-team">

                <c:if test="${teams.size() > 0}">
                    <c:forEach items="${teams}" var="team">
                        <option
                                <c:if test="worker.team == team">selected</c:if>
                                value="${team.id}">${team.label}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>


        <div class="form-group">
            <label for="select-sexe">sexe</label>
            <select class="custom-select" name="selected-sexe" id="select-sexe">
                <c:if test="${not empty sexes}">
                    <c:forEach items="${sexes}" var="sexe">
                        <option
                                <c:if test="worker.sexe == sexe">selected</c:if>
                                value="${sexe}">${sexe}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>


        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Save</button>
        </div>

    </form>
</div>

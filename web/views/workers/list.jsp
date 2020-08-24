<h2>Workers</h2>
<c:choose>
    <c:when test="${workers.size() > 0}">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Username</th>
                    <th scope="col">FirstName</th>
                    <th scope="col">LastName</th>
                    <th scope="col">Birthdate</th>
                    <th scope="col">Sex</th>
                    <th scope="col">Role</th>
                    <th scope="col">Team</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach items="${workers}" var="worker">
                    <tr>
                        <td>${worker.username}</td>
                        <td>${worker.firstName}</td>
                        <td>${worker.lastName}</td>
                        <td>${worker.birthdate}</td>
                        <td>${worker.sexe}</td>
                        <td>${worker.role.label}</td>
                        <td>${worker.team.label}</td>
                        <td>
                            <form name="form-workers-edit" method="POST"
                                  action="${pageContext.request.contextPath}/workers/edit/checkID">
                                <div class="form-group">
                                    <input name="editID" type="hidden" value="${worker.id}">
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary btn-block">Edit</button>
                                </div>
                            </form>
                            <form name="form-workers-delete" method="POST"
                                  action="${pageContext.request.contextPath}/workers/delete">
                                <div class="form-group">
                                    <input name="deleteID" type="hidden" value="${worker.id}">
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary btn-block">Delete</button>
                                </div>
                            </form>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <p>Il n'y a aucun worker à afficher</p>
    </c:otherwise>
</c:choose>

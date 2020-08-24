<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Roles</h2>
<c:choose>
    <c:when test="${roles.size() > 0}">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Label</th>
                    <th scope="col">Description</th>
                    <th scope="col">Permissions #</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach items="${roles}" var="role">
                    <tr>
                        <td>${role.label}</td>
                        <td>${role.description}</td>
                        <td>${role.authorities.size()}</td>
                        <td>
                            <form name="form-roles-edit" method="POST"
                                  action="${pageContext.request.contextPath}/roles/edit">
                                <div class="form-group">
                                    <input name="editID" type="hidden" value="${role.id}">
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary btn-block">Edit</button>
                                </div>
                            </form>
                            <form name="form-roles-delete" method="POST"
                                  action="${pageContext.request.contextPath}/roles/delete">
                                <div class="form-group">
                                    <input name="deleteID" type="hidden" value="${role.id}">
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
        <p>Il n'y a aucun role à afficher</p>
    </c:otherwise>
</c:choose>



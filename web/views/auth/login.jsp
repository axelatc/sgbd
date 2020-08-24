<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <div class="login-form">
        <form action="${pageContext.request.contextPath}/login" method="post">
            <h2 class="text-center">Log in</h2>
            <jsp:include page="/includes/form_error_messages.jsp"></jsp:include>
            <div class="form-group">
                <label for="username">username</label>
                <input name="username" id="username" type="text" class="form-control" placeholder="Username"
                       required="required">
                <%--                        <ul class="text-danger">
                                            <c:forEach items="${usernameErrors}" var="error">
                                                <li class="text-danger" value="{error}"></li>
                                            </c:forEach>
                                        </ul>--%>
            </div>

            <div class="form-group">
                <label for="password">password</label>
                <input name="password" type="password" id="password" class="form-control" placeholder="Password"
                       required="required">
                <%--                        <ul class="text-danger">
                                            <c:forEach items="${passwordErrors}" var="error">
                                                <li class="text-danger" value="{error}"></li>
                                            </c:forEach>
                                        </ul>--%>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block">Log in</button>
            </div>
        </form>
    </div>
</div>

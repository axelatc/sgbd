<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <div class="container">
            <div class="login-form">
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <h2 class="text-center">Log in</h2>
                    <jsp:include page="/includes/form_error_messages.jsp"></jsp:include>
                    <div class="form-group">
                        <input name="username" type="text" class="form-control" placeholder="Username" required="required">
                    </div>
                    <div class="form-group">
                        <input name="password" type="password" class="form-control" placeholder="Password" required="required">
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block">Log in</button>
                    </div>
                    <div class="clearfix">
                        <label class="float-left form-check-label"><input type="checkbox"> Remember me</label>
                        <a href="#" class="float-right">Forgot Password?</a>
                    </div>
                </form>
            </div>
        </div>

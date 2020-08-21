<div class="workers-create-form">
    <form action="${pageContext.request.contextPath}/workers/create" method="post">
        <h2 class="text-center">Create new worker</h2>
        <p>${requestScope.errors}</p>
        <div class="form-group">
            <label for="username">username</label>
            <input name="username" value="${worker.login}"
                   id="username"
                   type="text" class="form-control"
                   placeholder="username" required="required">
        </div>
        <div class="form-group">
            <label for="password">password</label>
            <input name="password" value="${worker.passwordKey}"
                   id="password"
                   type="password" class="form-control"
                   placeholder="Password" required="required">
        </div>
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
            <label for="birthdate">birthdate</label>
            <input name="birthdate" value="${worker.birthdate}"
                   id="birthdate"
                   type="date" class="form-control"
                   placeholder="birthdate" required="required">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Save</button>
        </div>
    </form>
</div>
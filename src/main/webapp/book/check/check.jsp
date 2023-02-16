<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Check Page</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>
<div class="container" style="width: 30%;margin-top: 10%">
    <span>${credentials}</span>
        <form action="/check" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" aria-describedby="emailHelp" name="email">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
</div>

<jsp:include page="/fragments/js.jsp"/>
</body>
</html>

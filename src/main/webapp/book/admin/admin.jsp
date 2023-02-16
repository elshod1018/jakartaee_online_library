<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Admin page</title>
    <jsp:include page="/fragments/css.jsp"/>
    <style>
        .container a {
            margin: 1%;
            width: 250px;
            height: 50px;
            padding: 2px;
            font-size: 23px;
            align-items: center;
            justify-content: center;
        }
    </style>
</head>
<body>
<div class="container" style="width: 80%;margin-left:30%;margin-top: 4%;">
    <a class="btn btn-success" href="/category/add?email=${user.getEmail()}&password=${user.getPassword()}">
        Category Create
    </a>
    <a class="btn btn-warning" href="/category/update?email=${user.getEmail()}&password=${user.getPassword()}">
        Category Edit
    </a>
    <a class="btn btn-danger" href="/category/delete?email=${user.getEmail()}&password=${user.getPassword()}">
        Category Delete
    </a>
    <br>
    <a class="btn btn-success" href="/book/add?email=${user.getEmail()}&password=${user.getPassword()}">
        Book Create
    </a>
    <a class="btn btn-warning" href="/book/update?email=${user.getEmail()}&password=${user.getPassword()}">
        Book Edit
    </a>
    <a class="btn btn-danger" href="/book/delete?email=${user.getEmail()}&password=${user.getPassword()}">
        Book Delete
    </a>
</div>
</body>
</html>

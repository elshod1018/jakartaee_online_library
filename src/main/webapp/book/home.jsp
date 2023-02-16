<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Online Library</title>
    <jsp:include page="/fragments/css.jsp"/>
    <style>
        .card-body span {
            color: #e018bf;
        }

        .card-body h5, .card-body h6 {
            color: #1f45da;
        }

        .card-body p {
            color: #91800c;
        }

        .card-body i {
            position: absolute;
            top: 94%;
            left: 4%;
            color: red;
        }

        .card-body a {
            position: absolute;
            top: 92%;
            left: 72%;
        }
    </style>
</head>
<body>
<div class="head">
    <div class="navbar">
        <div class="nav">
            <ul>
                <li><a href="/home">Home</a></li>
                <li><a href="/check">For admins</a></li>
                <li><a href="#">Contact</a></li>
                <li><a href="#">About</a></li>
            </ul>
        </div>
        <a href="#" class="btn btn-secondary" style="margin-right: 1.5%">Log in</a>
        <a href="#" class="btn btn-primary" style="margin-right: 3%">Sign up</a>
    </div>
</div>

<div class="container">
    <form method="post" action="/home">
        <select class="form-select" aria-label="Default select example"
                style="margin:10px auto;width: 30%;display: inline-block" name="categoryName">
            <option value="0">All</option>
            <c:forEach items="${categories}" var="category">
                <c:if test="${category.getId() == selected}">
                    <option value="${category.getId()}" selected>${category.getName()}</option>
                </c:if>
                <c:if test="${category.getId() != selected}">
                    <option value="${category.getId()}">${category.getName()}</option>
                </c:if>
            </c:forEach>
        </select>
        <button type="submit" class="btn btn-light" style="margin-left: 10px">Search</button>
    </form>
    <div class="row">
        <c:forEach items="${books}" var="book">
            <div class="col-lg-4 col-md-6 col-sm-12" style="height: 640px;width: 440px;margin-top: 5px">
                <div class="card" style="height: 640px">
                    <img src="/download?filename=${book.getCoverGeneratedFileName()}" class="card-img-top"
                         alt="${book.getCoverOriginalFileName()}">
                    <div class="card-body">
                        <h5 class="card-title"><span>Nomi: </span>${book.getTitle()}</h5>
                        <h6 class="card-title"><span>Yozuvchi: </span>${book.getAuthor()}</h6>
                        <h6 class="card-title"><span>Kategoriya: </span>${book.getCategory()}</h6>
                        <p class="card-text">${book.getDescription()}</p>
                        <i>${book.getDocumentFileSize()}</i>
                        <a href="/download?filename=${book.getDocumentGeneratedFileName()}"
                           class="card-link btn btn-secondary" style="">Download</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%--

<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
    Launch demo modal
</button>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>--%>

<jsp:include page="../fragments/js.jsp"/>
</body>
</html>
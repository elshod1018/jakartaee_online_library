<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Add Book</title>
    <jsp:include page="/fragments/css.jsp"/>
    <style>
        .row span {
            color: red;
        }
    </style>
</head>
<body>

<h1 class="text-center" style="margin-top: 30px; margin-bottom: 30px">Fill in the book form</h1>

<form class="row g-3" method="post" enctype="multipart/form-data">
    <div class="col-md-5 offset-1">
        <label for="validationServer01" class="form-label">Title: <span>${constraints.get("title_error")}</span> </label>
        <input type="text" class="form-control is-valid" id="validationServer01" name="title"
               placeholder="Otamdan qolgan dalalar" required>
        <div class="valid-feedback">
            Looks good!
        </div>
    </div>
    <div class="col-md-4 offset-1">
        <label for="validationServer02" class="form-label">Author :<span>${constraints.get("author_error")}</span></label>
        <input type="text" class="form-control is-valid" id="validationServer02" name="author"
               placeholder="Tog'ay Murod" required>
        <div class="valid-feedback">
            Looks good!
        </div>
    </div>

    <div class="col-md-5 offset-1">
        <label for="validationServer03" class="form-label">Publisher :<span>${constraints.get("publisher_error")}</span></label>
        <input type="text" class="form-control" id="validationServer03" name="publisher"
               aria-describedby="validationServer03Feedback" required>
        <div id="validationServer03Feedback" class="invalid-feedback">
            Please provide a publisher's name.
        </div>
    </div>

    <div class="col-md-4 offset-1">
        <select class="form-select" required aria-label="select example" style="margin-top: 30px" id="categoryId"
                name="categoryId">
            <option value="1" selected>Select category : <span>${constraints.get("category_error")}</span></option>
            <c:forEach items="${categories}" var="category">
                <option value="${category.getId()}">${category.getName()}</option>
            </c:forEach>
        </select>
        <div class="invalid-feedback">Example invalid select feedback</div>
    </div>

    <div class="col-md-10 offset-1" style="margin-bottom: 10px">
        <label for="validationTextarea1" class="form-label">Description :<span>${constraints.get("description_error")}</span></label>
        <textarea class="form-control " rows="3" id="validationTextarea1" name="description"
                  placeholder="Write the description of the book here" required></textarea>
        <div class="invalid-feedback">
            Required field
        </div>
    </div>

    <div class="col-sm-1 col-md-2 offset-1">
        <label for="pagespicker" class="form-label">Page count :<span>${constraints.get("pages_error")}</span></label>
        <div class="list-group-numbered" id="pages">
            <input type="number" class="form-control" id="pagespicker" name="pages" placeholder="678" required/>
        </div>
    </div>
    <div class="col-sm-1 col-md-2" style="margin-left: 5.5%">
        <label for="date" class="form-label">Date of publication :<span>${constraints.get("published_error")}</span></label>
        <div class="input-group date" id="datepicker1">
            <input type="date" class="form-control" id="date" name="publishedAt" placeholder="02/09/2023" required/>
            <i class="fa fa-calendar"></i>
        </div>
    </div>

    <div class="col-md-2" style="margin-left: 5.5%">
        <label for="image" class="form-label">Upload the Image File</label>
        <input type="file" class="form-control" id="image" name="image" aria-label="file example" required>
        <div class="invalid-feedback">Example invalid form file feedback</div>
    </div>

    <div class="col-md-2" style="margin-left: 5.5%">
        <label for="file" class="form-label">Upload the PDF File</label>
        <input type="file" class="form-control" id="file" name="file" aria-label="file example" required>
        <div class="invalid-feedback">Example invalid form file feedback</div>
    </div>

    <div class="col-12 offset-1">
        <div class="form-check">
            <input class="form-check-input is-invalid" type="checkbox" value="" id="invalidCheck3"
                   aria-describedby="invalidCheck3Feedback" required>
            <label class="form-check-label" for="invalidCheck3">
                <a href="">Agree to terms and conditions</a>
            </label>
            <div id="invalidCheck3Feedback" class="invalid-feedback">
                You must agree before submitting.
            </div>
        </div>
    </div>
    <div class="col-12 offset-1">
        <a class="btn btn-warning" href="/home">Back to home</a>
        <button class="btn btn-success" type="submit" style="margin-left: 5px">Create</button>
    </div>
</form>
<jsp:include page="/fragments/css.jsp"/>
</body>
</html>
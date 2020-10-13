<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>

    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="lang"/>
    <title>ADD ACTIVITY</title>
    <meta charset="utf-8">
    <meta charset=utf-8>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</head>
<body style="padding-top: 70px">
<c:import url="/header.jsp"></c:import>


<c:if test="${errorMessage!=null}">
<div class="alert alert-danger" style="display: inline-block" role="alert">
<c:out value="${errorMessage}"/>
</c:if>
</div>
<c:if test="${infoMessage!=null}">
<div class="alert alert-success" style="display: inline-block" role="alert">
<c:out value="${infoMessage}"/>
</div>
</c:if>

<div class="container">
<c:if test="${sessionScope.get('userRole') == 'ADMIN'}">

<form id="create-activity" style="line-height: 10px" action="controller" method="POST" accept-charset="UTF-8">
  <input type="hidden" name="command" value="createActivity" />
  <div class="form-group">
    <label for="name">Name</label>
    <input required type="text" class="form-control" id="name" name="name" >
  </div>
   <div class="form-group">
      <label for="nameEn">Name English</label>
      <input required type="text" class="form-control" id="nameEn" name="nameEn" >
    </div>
    <div class="form-group">
        <label for="nameUa">Name Russian</label>
        <input required type="text" class="form-control" id="nameUa" name="nameUa" >
    </div>
  <div class="form-group">
    <label for="description">Description</label>
    <input required type="text" name="description" class="form-control" id="description" >
  </div>

  <div class="form-group">
    <label for="descriptionEn">Description English</label>
    <input required type="text" name="descriptionEn" class="form-control" id="descriptionEn">
  </div>
    <div class="form-group">
      <label for="descriptionUa">Description Russian</label>
      <input required type="text" name="descriptionUa" class="form-control" id="descriptionUa">
    </div>
  <select name="category" class="form-control">
      <c:forEach items="${categories}" var="cat">
          <option value="${cat.name}" text="${cat.name}"><c:out value="${cat.name}"></c:out></option>
      </c:forEach>
  </select>

  <button type="submit" class="btn btn-success">CREATE NEW</button>
</form>
</c:if>

    </div>



</body>
</html>
<%@ page isELIgnored="false" %>
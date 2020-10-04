<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>VIEW ACTIVITY</title>
    <meta charset="utf-8">
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

<c:if test="${sessionScope.get('userRole') != 'ADMIN'}">
    <c:redirect url="index.jsp"/>
</c:if>


<form id="edit-activity" action="controller" method="POST" style="width: 60%; margin: auto">
  <input type="hidden" name="command" value="editActivity" />
  	<input type="hidden" name="id" value="${activity.id}" />
  <div class="form-group">
    <label for="name">Name</label>
    <input required type="text" class="form-control" id="name" name="name" value="${activity.name}" >
  </div>
  <div class="form-group">
      <label for="nameEn">Name English</label>
      <input required type="text" class="form-control" id="nameEn" name="nameEn" value="${activity.nameEn}" >
  </div>
  <div class="form-group">
      <label for="nameUa">Name Ukrainian</label>
      <input required type="text" class="form-control" id="nameUa" name="nameUa" value="${activity.nameUa}" >
  </div>
  <div class="form-group">
    <label for="description">Description</label>
    <input required type="text" name="description" class="form-control" id="description" value="${activity.description}" >
  </div>
   <div class="form-group">
     <label for="descriptionEn">Description English</label>
     <input required type="text" name="descriptionEn" class="form-control" id="descriptionEn" value="${activity.descriptionEn}" >
   </div>
    <div class="form-group">
      <label for="description">Description Ukrainian</label>
      <input required type="text" name="descriptionUa" class="form-control" id="descriptionUa" value="${activity.descriptionUa}" >
    </div>
  <select name="category" class="form-control">
      <c:forEach items="${categories}" var="cat">
          <option value="${cat.name}" text="${cat.name}" ${cat eq activity.category ? 'selected="selected"' : ''}><c:out value="${cat.name}"></c:out></option>
      </c:forEach>
  </select>

  <button type="submit" class="btn btn-primary">Submit</button>
</form>


</body>
</html>
<%@ page isELIgnored="false" %>
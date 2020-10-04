<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${sessionScope.get('userRole') != 'USER'}">
    <c:redirect url="/index.jsp"/>
</c:if>
<html>
<head>
    <title>MY PROFILE</title>
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

 <p>    				<c:out value="${user.firstName}"></c:out></p>
<p>						<c:out value="${user.lastName}"></c:out></p>
	<p>  					<c:out value="${user.login}"></c:out></p>
<p>						<c:out value="${user.email}"></c:out></p>

   MY REQUESTS
   <div class="list-group col-sm" style="width: 60%; margin: auto;">
  <c:forEach var="req" items="${myRequests}">

  <div class="list-group-item list-group-item-action flex-column align-items-start">
    <div class="d-flex w-100 justify-content-between">
      <h5 class="mb-1">  <c:out value="${req.status}"></c:out></h5>
      <small>  <c:out value="${req.createdAt}"></c:out></small>
    </div>
	<p class="mb-1"> <c:out value="${req.motif}"></c:out> <c:out value="${req.activity.name}"></c:out>	</p>

	</div>
  </c:forEach>
  </div>



</body>
</html>
<%@ page isELIgnored="false" %>
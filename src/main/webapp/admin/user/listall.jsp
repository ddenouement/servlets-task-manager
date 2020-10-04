<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${sessionScope.get('userRole') != 'ADMIN'}">
    <c:redirect url="/index.jsp"/>
</c:if>
<html>
<head>
    <title>ALL USERS</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</head>
<body style="padding-top: 70px">
<c:import url="/header.jsp"></c:import>


<c:if test="${errorMessage}">
<div class="alert alert-danger" style="display: inline-block" role="alert">
<c:out value="${errorMessage}"/>
</div>
</c:if>
<c:if test="${infoMessage}">
<div class="alert alert-success" style="display: inline-block" role="alert">
<c:out value="${infoMessage}"/>
</div>
</c:if>

<table class="table">
  <thead>
    <tr>
      <th scope="col">First</th>
      <th scope="col">Last</th>
      <th scope="col">Login</th>
      <th scope="col">Email</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="u" items="${users}">
    <tr> 
      <td><a href="<c:url value="controller?command=viewUser"> <c:param name="id" value="${u.id}"/></c:url>">
						<c:out value="${u.firstName}"></c:out>
	 </a></td>
	 <td><a href="<c:url value="controller?command=viewUser"> <c:param name="id" value="${u.id}"/></c:url>">
						<c:out value="${u.lastName}"></c:out>
	 </a></td>		
	  <td><a href="<c:url value="controller?command=viewUser"> <c:param name="id" value="${u.id}"/></c:url>">
						<c:out value="${u.login}"></c:out>
	  </a></td>
	   <td><a href="<c:url value="controller?command=viewUser"> <c:param name="id" value="${u.id}"/></c:url>">
						<c:out value="${u.email}"></c:out>
		</a></td>

	  
    </tr>
	</c:forEach>
  </tbody>
</table> 

</body>
</html>
<%@ page isELIgnored="false" %>
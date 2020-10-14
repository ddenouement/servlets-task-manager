<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${sessionScope.get('userRole') != 'ADMIN'}">
    <c:redirect url="/index.jsp"/>
</c:if>
<html>
<head>
    <title>ALL REQUESTS</title>
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

<div style="margin: auto; width: 60%;">
<c:if test="${currentPage != 1}">
       <a href="controller?command=requests&page=${currentPage - 1}">Previous</a>
</c:if>
<table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="controller?command=requests&page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
</table>
<c:if test="${currentPage lt noOfPages}">
        <td><a href="controller?command=requests&page=${currentPage + 1}">Next</a></td>
</c:if>
</div>



 <div class="list-group" style="width: 60%; margin: auto">
  <c:forEach var="req" items="${requests}"> 
  
  <div class="list-group-item list-group-item-action flex-column align-items-start">
    <div class="d-flex w-100 justify-content-between">
      <h5 class="mb-1">  <c:out value="${req.status}"></c:out></h5>
      <small> Created at: <c:out value="${req.createdAt}"></c:out></small>
    </div>  
	<p class="mb-1">
		<a href="<c:url value="controller?command=viewUser"> <c:param name="id" value="${req.user.id}"/></c:url>">

	First Name :  <c:out value="${req.user.firstName}"></c:out>
	    </a>
	</p>
	<p class="mb-1"> 
	To: <c:out value="${req.motif}"></c:out>
	</p>
	<p class="mb-1"> 
		<a href="<c:url value="controller?command=viewActivity"> <c:param name="id" value="${req.activity.id}"/></c:url>">
		Activity: <c:out value="${req.activity.name}"></c:out>
		</a>
	</p>
	
	
	<c:if test="${req.status.name == 'CREATED'}">
	
		<form id="accept_request" action="controller" method="POST">
			<input type="hidden" name="command" value="acceptRequest" />
			<input
				type="hidden" name="id" value="${req.id}" />
				<input type="submit" class = "btn btn-success" value="Accept" />
		</form>
		
		<form id="delete_request" action="controller" method="POST">
			<input type="hidden" name="command" value="dismissRequest" />
			<input	type="hidden" name="id" value="${req.id}" />
			<input type="submit" class = "btn btn-danger"	value="Dismiss" />
		</form>
	
	</c:if>
	</div>
	
  
  
  </c:forEach>
  </div>
 
</body>
</html>
<%@ page isELIgnored="false" %>
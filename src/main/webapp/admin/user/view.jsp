<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${sessionScope.get('userRole') != 'ADMIN'}">
    <c:redirect url="/index.jsp"/>
</c:if>
<html>
<head>
    <title>View User</title>
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

    <p>Name:        	<c:out value="${userview.firstName}"></c:out> </p>
	<p>Last name:		<c:out value="${userview.lastName}"></c:out></p>
	<p>Login:			<c:out value="${userview.login}"></c:out></p>
	<p>Email:			<c:out value="${userview.email}"></c:out></p>

<a href="<c:url value="controller?command=tasks"> <c:param name="user_id" value="${userview.id}"/></c:url>">
    View User Tasks ->
</a>



</body>
</html>
<%@ page isELIgnored="false" %>
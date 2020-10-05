<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${sessionScope.get('userRole') != 'ADMIN'}">
    <c:redirect url="/index.jsp"/>
</c:if>
<html>
<head>
    <title>View STATs</title>
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
Current statistics:
<div class="card">
  <div class="card-body">
    Number of users: <c:out value="${numUsers}"/>
  </div>
</div>
<div class="card">
  <div class="card-body">
    Number of requests waiting: <c:out value="${numRequestsWaiting}"/>
  </div>
</div>
<div class="card">
  <div class="card-body">
    Number of activities: <c:out value="${numActivities}"/>
  </div>
</div>
</div>

</body>
</html>
<%@ page isELIgnored="false" %>
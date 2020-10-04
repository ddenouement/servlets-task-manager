<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${sessionScope.get('user') != null}">
    <c:redirect url="/index.jsp"/>
</c:if>
<html>
<head>
    <title>REGISTER</title>
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


                <form name="registration-form"method="post" action="controller" style="width: 60%; margin: auto;">
                            <input type="hidden" name="command" value="register"/>

                 <div class="form-group">
                     <label for="name">Name</label>
                     <input type="text" id="name" name="name" required>
                 </div>
                 <div class="form-group">
                     <label for="lastname">Last Name</label>
                     <input type="text" name="lastname" id="lastname" required>
                 </div>
                 <div class="form-group">
                     <label for="email">Email</label>
                     <input type="text" name="email" id="email" required>
                 </div>
                 <div class="form-group">
                     <label for="login">Login</label>
                     <input type="text" name="login" id="login" required>
                 </div>
                 <div class="form-group">
                     <label for="password">Password</label>
                     <input type="password" id="password" name="password" required>
                  </div>
                  <div class="form-group">
                     <label for="role">Select Role</label>
                     <select class="form-control" id="role" name="role">
                       <option value="USER">user</option>
                       <option value="ADMIN">admin</option>
                     </select>
                   </div>
                    <input type = "submit" text="Send"></input>
                </form>
</body>
</html>
<%@ page isELIgnored="false" %>
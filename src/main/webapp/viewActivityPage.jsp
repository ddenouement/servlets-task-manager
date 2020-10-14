<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>

    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="lang"/>
    <title><fmt:message key="view_activity" /></title>
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

<div style="width: 60%; margin: auto">
	<p>  <c:out value="${lang eq 'ua' ? activity.nameUa : activity.nameEn}"/>
	       <span class="badge badge-info"><c:out value="${lang eq 'ua'? activity.category.nameUa : activity.category.nameEn}"></c:out></span>
	</p>
	<p>  <c:out value="${lang eq 'ua' ? activity.descriptionUa : activity.descriptionEn}"/></p>

<c:if test="${sessionScope.get('userRole') == 'ADMIN'}">
    <a class="btn btn-primary" href="/controller?command=editActivity&id=${activity.id}" role="button">EDIT</a>
    <form method="POST"  action="controller" style="margin-bottom: 10px;">
            <input type="hidden" name="command" value="deleteActivity"/>
            <input type="hidden" name="id" value="${activity.id}"/>
    <button class="btn btn-danger" type="submit" style="margin-bottom: 10px;">
             DELETE
    </button>
    </form>
    <p class="text-danger">WARNING: if you delete activity, all related requests and tasks delete on cascade</p>
</c:if>

<c:if test="${sessionScope.get('userRole') == 'USER'}">
		<form id="create_request" action="controller" method="POST">
			<input type="hidden" name="command" value="createRequest" />
			<input	type="hidden" name="user_id" value="${sessionScope.get('user').id}" />
			<input	type="hidden" name="motif" value="ADD" />
			<input	type="hidden" name="activity_id" value="${activity.id}" />
			<input type="submit" class = "btn btn-success"  value="<fmt:message key="enroll_me_to_activity" />"/>
		</form>
</c:if>

</div>
<fmt:message key="people" />

<form id="view_activity" action="controller" method="GET">
			<input type="hidden" name="command" value="viewActivity" />
			<input type="hidden" name="id" value="${activity.id}" />

<select name="progress" class="form-control">
      <c:forEach items="${progresses}" var="pro">
          <option value="${pro.name}" text="${pro.name}" ${pro eq  filterByProgress? 'selected="selected"' : ''}><fmt:message key="${pro.name}"/></option>
      </c:forEach>
</select>
<button type="submit" class="btn btn-primary"><fmt:message key="filter"/></button>
</form>


<table class="table">
  <thead>
    <tr>
      <th scope="col"><fmt:message key="name"/></th>
      <th scope="col"><fmt:message key="lastname"/></th>
      <th scope="col"><fmt:message key="email"/></th>
      <th scope="col"><fmt:message key="progress"/></th>
      <th scope="col"><fmt:message key="date_finished"/></th>
      <th scope="col"><fmt:message key="hours"/></th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="task" items="${tasks}">
    <tr>
      <td><a href="<c:url value="controller?command=viewUser"> <c:param name="id" value="${task.user.id}"/></c:url>">
						<c:out value="${task.user.firstName}"></c:out>
	 </a></td>
	 <td>
	     <a href="<c:url value="controller?command=viewUser"> <c:param name="id" value="${task.user.id}"/></c:url>">
			 <c:out value="${task.user.lastName}"></c:out>
	     </a>
	 </td>
	  <td>
	  	<c:out value="${task.user.email}"></c:out>
	 </td>
     <td>
        <fmt:message key="${task.progress.name}" />
		</td>
     <td>
        <c:if test="${not empty task.finishedOn}">
	    	<fmt:formatDate type="date" value="${task.finishedOn}"/>
		</c:if>
	 </td>
	 <td>
        <c:out value="${task.timeSpentInHours}"></c:out>
     </td>

    </tr>
	</c:forEach>
  </tbody>
 </table>


</body>
</html>
<%@ page isELIgnored="false" %>
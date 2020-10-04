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

<div style="width: 60%; margin: auto">
	<p>Name: <c:out value="${activity.name}"></c:out>
	       <span class="badge badge-info"><c:out value="${activity.category.name}"></c:out></span>
	</p>
	<p>Description: <c:out value="${activity.description}"></c:out></p>

<c:if test="${sessionScope.get('userRole') == 'ADMIN'}">
    <a class="btn btn-primary" href="/controller?command=editActivity&id=${activity.id}" role="button">EDIT</a>
</c:if>
<c:if test="${sessionScope.get('userRole') == 'USER'}">
ENROLL ME TO THIS ACTIVITY
		<form id="create_request" action="controller" method="POST">
			<input type="hidden" name="command" value="createRequest" />
			<input	type="hidden" name="user_id" value="${sessionScope.get('user').id}" />
			<input	type="hidden" name="motif" value="ADD" />
			<input	type="hidden" name="activity_id" value="${activity.id}" />
			<input type="submit" class = "btn btn-success" text="Enroll" value="Enroll"/>
		</form>
</c:if>

</div>

PEOPLE DOING THIS ACTIVITY:


<label for="view_activity"> Sort by progress:</label>
<form id="view_activity" action="controller" method="GET">
			<input type="hidden" name="command" value="viewActivity" />
			<input type="hidden" name="id" value="${activity.id}" />

<select name="progress" class="form-control">
      <c:forEach items="${progresses}" var="pro">
          <option value="${pro.name}" text="${pro.name}" ${pro eq  filterByProgress? 'selected="selected"' : ''}><c:out value="${pro.name}"></c:out></option>
      </c:forEach>
</select>
<button type="submit" class="btn btn-primary">FILTER</button>
</form>


<table class="table">
  <thead>
    <tr>
      <th scope="col">First</th>
      <th scope="col">Last</th>
      <th scope="col">Email</th>
      <th scope="col">Task progress</th>
      <th scope="col">End Date</th>
      <th scope="col">Hours Spent</th>
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
		<c:out value="${task.progress.name}"></c:out>
	 </td>
     <td>
		<c:out value="${task.finishedOn}"></c:out>
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
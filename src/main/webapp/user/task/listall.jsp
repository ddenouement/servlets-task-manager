<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>

    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="lang"/>

    <title> <fmt:message key="my_tasks" /></title>
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


<ul>
<c:forEach var="task" items="${tasks}">
  <c:if test="${task.progress.name != 'CANCELLED'}">

    <li>
        <p><fmt:message key="activity"/>: <c:out value="${lang eq 'ua' ? task.activity.nameUa : task.activity.nameEn }"/></p>
        <p><fmt:message key="description"/>: <c:out value="${lang eq 'ua'? task.activity.descriptionUa : task.activity.descriptionEn }"/></p>
        <p><fmt:message key="progress"/>: <fmt:message key="${task.progress.name}"/>  </p>
        <c:if test="${(task.progress.name eq 'FINISHED')}">
                <p><fmt:message key="hours"/> <c:out value="${task.timeSpentInHours}"/></p>
                <p><fmt:message key="date_finished"/><c:out value="${task.finishedOn}"/></p>
        </c:if>

        <c:if test="${(sessionScope.get('user').id == currentUserId) && (task.progress.name != 'REQUESTED' && task.progress.name != 'FINISHED')}">

	    	<form id="create_request" action="controller" method="POST">
		    	<input type="hidden" name="command" value="createRequest" />
			    <input	type="hidden" name="motif" value="REMOVE" />
			    <input	type="hidden" name="user_id" value="${sessionScope.get('user').id}" />
			    <input	type="hidden" name="task_id" value="${task.id}" />
			    <input type="submit" class = "btn btn-danger"   value="<fmt:message key="remove"/>"/>
		    </form>
		    	<form id="finishTask" action="controller" method="POST">
            		    	<input type="hidden" name="command" value="finishTask" />
            			    <input	type="hidden" name="id" value="${task.id}" />
            			    <input required	type="number" name="hours" value="2" />
            			    <input type="submit" class = "btn btn-success"   value="<fmt:message key="finish"/>"/>
                </form>
        </c:if>
    <li>
    </c:if>
</c:forEach>
</ul>


</body>
</html>
<%@ page isELIgnored="false" %>
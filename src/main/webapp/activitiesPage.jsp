<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>

    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="lang"/>
    <title><fmt:message key="header.all_activities" /></title>
    <meta charset="utf-8">
    <meta charset=utf-8>
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

<div class="container" style="width: 60%; margin: 0 auto;">

<c:if test="${sessionScope.get('userRole') == 'ADMIN'}">
    <a class="btn btn-success" href="/controller?command=addActivity">ADD ACTIVITY</a>
    <a class="btn btn-success" href="/controller?command=addCategory">ADD CATEGORY</a>
</c:if>

<form  id="activities" action="controller" method="GET">
      			<input type="hidden" name="command" value="activities" />
<fmt:message key="sort" />:
  <input type="radio" id="ppl" name="sortBy" ${sortBy eq '0'? 'checked="checked"' : '' } value="0">
  <label for="ppl"><fmt:message key="people_working" /></label><br>
  <input type="radio" id="cat" name="sortBy" ${sortBy eq '1'? 'checked="checked"' : '' } value="1">
  <label for="cat"><fmt:message key="category" /></label><br>
  <input type="radio"  id="name" name="sortBy" ${sortBy eq '2'? 'checked="checked"' : '' } value="2">
  <label for="name"><fmt:message key="name" /></label>
<button type="submit" class="btn btn-info"><fmt:message key="sort" /></button>
</form>




<c:if test="${currentPage != 1}">
       <a href="controller?command=activities&page=${currentPage - 1}&sortBy=${sortBy}">Previous</a>
</c:if>
<table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="controller?command=activities&page=${i}&sortBy=${sortBy}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
</table>
<c:if test="${currentPage lt noOfPages}">
        <td><a href="controller?command=activities&page=${currentPage + 1}&sortBy=${sortBy}">Next</a></td>
</c:if>



<div >
<ul class="list-group">
  <c:forEach var="task" items="${activities}">
        <a class="list-group-item" href="<c:url value="controller?command=viewActivity"> <c:param name="id" value="${task.id}"/></c:url>">
        <h3><c:out value="${lang eq 'ua'? task.nameUa : task.nameEn}"></c:out> <span class="badge badge-info"> <c:out value="${lang eq 'ua'? task.category.nameUa : task.category.nameEn}"></c:out></span> </h3>
        <p><c:out value="${lang eq 'ua'? task.descriptionUa : task.descriptionEn}"></c:out></p>
         <p><fmt:message key="people_working" /> <c:out value="${task.peopleAmount}"></c:out></p>
        </a>
  </c:forEach>
  </ul>
  </div>
  </div>



</body>
</html>
<%@ page isELIgnored="false" %>
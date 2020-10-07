<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
     <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="/css/home.css">

 </head>
<body >


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang"/>

<nav class="navbar navbar-light bg-white fixed-top navbar-expand-md" th:fragment="header">

    <a class="navbar-brand" href="/"> <fmt:message key="siteName" /></a>

    <div class="collapse navbar-collapse justify-content-end" id="navbarCollapse">

        <ul  class="nav navbar-nav">
<c:if test="${sessionScope.userRole eq 'ADMIN'}">

            <li class="nav-item">
                <p   class="navbar-text"> <c:out value="${sessionScope.user.login}"></c:out>
                </p>
            </li>
            <li class="nav-item"><a class="nav-link" href="/controller?command=users">USERS</a></li>
            <li class="nav-item"><a class="nav-link" href="/controller?command=requests">REQUESTS</a></li>
            <li class="nav-item"><a class="nav-link" href="/controller?command=activities">ACTIVITIES</a></li>
            <li class="nav-item"><a class="nav-link" href="/controller?command=stats">STATS</a></li>

        </c:if>
     <li class="nav-item">
                                  <form method="POST"  action="controller">
                                                   <input type="hidden" name="command" value="change_language"/>
                                                   <input type="hidden" name="lang" value="ua"/>
                                                    <button class="btn btn-sm align-middle btn-secondary ml-auto mr-3 order-lg-last" type="submit">
                                                        Ua
                                                    </button>
                                  </form>
            </li>
            <li class="nav-item">
                          <form method="POST"  action="controller">
                                           <input type="hidden" name="command" value="change_language"/>
                                           <input type="hidden" name="lang" value="en"/>
                                            <button class="btn btn-sm align-middle btn-secondary ml-auto mr-3 order-lg-last" type="submit">
                                                En
                                            </button>
                          </form>
            </li>

<c:if test="${sessionScope.userRole eq 'USER'}">

            <li class="nav-item">
                <p class="navbar-text"> <fmt:message key="user" />
                </p>
            </li>
            <li class="nav-item"><a class="nav-link" href="controller?command=me"><fmt:message key="header.my_profile" /></a></li>
            <li class="nav-item"><a class="nav-link" href="controller?command=tasks"><fmt:message key="header.my_tasks" /></a></li>
            <li class="nav-item"><a class="nav-link" href="controller?command=activities"><fmt:message key="header.all_activities" /></a></li>


        </c:if>

<c:if test="${sessionScope.userRole != null}">
            <li class="nav-item">
                <form method="POST"  action="controller">
                   <input type="hidden" name="command" value="logout"/>
                    <button class="btn btn-sm align-middle btn-secondary ml-auto mr-3 order-lg-last" type="submit">
                        <fmt:message key="logout" />
                    </button>
                </form>
            </li>
        </c:if>

<c:if test="${sessionScope.user == null}">
            <li class="nav-item"><a class="nav-link" href="controller?command=register"><span class="glyphicon glyphicon-user"></span>
                <fmt:message key="register" /></a></li>
            <li class="nav-item"><a class="nav-link" href="controller?command=login"><span class="glyphicon glyphicon-log-in"></span>
                <fmt:message key="login" /></a></li>

</c:if>
    </ul>
    </div>
</nav>
</body>
<%@ page isELIgnored="false" %>
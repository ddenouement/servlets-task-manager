<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-dark bg-dark fixed-top navbar-expand-md" th:fragment="header">

    <a class="navbar-brand" href="/">Task Tracker</a>

    <div class="collapse navbar-collapse justify-content-end" id="navbarCollapse">

<c:if test="${sessionScope.userRole eq 'ADMIN'}">
        <ul  class="navbar-nav">
            <li class="nav-item">
                <p   class="navbar-text">Logged in as <c:out value="${sessionScope.userRole}"></c:out>
                </p>
            </li>
            <li class="nav-item"><a class="nav-link" href="/controller?command=users">USERS</a></li>
            <li class="nav-item"><a class="nav-link" href="/controller?command=requests">REQUESTS</a></li>
            <li class="nav-item"><a class="nav-link" href="/controller?command=activities">ACTIVITIES</a></li>
            <li class="nav-item"><a class="nav-link" href="/controller?command=stats">STATS</a></li>
        </ul>
        </c:if>

<c:if test="${sessionScope.userRole eq 'USER'}">
        <ul   class="navbar-nav">
            <li class="nav-item">
                <p class="navbar-text">Logged in as <c:out value="${sessionScope.userRole}"></c:out>
                </p>
            </li>
            <li class="nav-item"><a class="nav-link" href="controller?command=me">MY PROFILE</a></li>
            <li class="nav-item"><a class="nav-link" href="controller?command=tasks">MY TASKS</a></li>
            <li class="nav-item"><a class="nav-link" href="controller?command=activities">BROWSE ACTIVITIES</a></li>


        </ul>
        </c:if>

<c:if test="${sessionScope.userRole != null}">
        <ul   class="nav navbar-nav">
            <li class="nav-item">
                <form method="POST"  action="controller">
                   <input type="hidden" name="command" value="logout"/>
                    <button class="btn btn-sm align-middle btn-secondary ml-auto mr-3 order-lg-last" type="submit">
                        Logout
                    </button>
                </form>
            </li>
        </ul>
        </c:if>

<c:if test="${sessionScope.user == null}">
        <ul  class="nav navbar-nav">
            <li class="nav-item"><a class="nav-link" href="controller?command=register"><span class="glyphicon glyphicon-user"></span>
                Sign Up</a></li>
            <li class="nav-item"><a class="nav-link" href="controller?command=login"><span class="glyphicon glyphicon-log-in"></span>
                Login</a></li>
        </ul>
</c:if>
    </div>
</nav>
<%@ page isELIgnored="false" %>
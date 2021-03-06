<%--
  Created by IntelliJ IDEA.
  User: karols
  Date: 12.11.2020
  Time: 08:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<jsp:include page="header.jsp"/>

<body>
<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <jsp:include page="menu.jsp"/>


        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding"><h3 class="color-header text-uppercase">Potwierdź usunięcie przepisu </h3></div>
                    <form action="/app/recipe/delete" method="get"><button type="submit" value="${id}" name="id" class="btn btn-info rounded-2 text-light m-1">Usuń</button></form>
                    <a href="/app/recipes" class="btn btn-danger rounded-2 text-light m-1">Anuluj</a>
                </div>
            </div>
        </div>
    </div>
</section>


<jsp:include page="footer.jsp"/>
</body>
</html>
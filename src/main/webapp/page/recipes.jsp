<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 10.11.2020
  Time: 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<body class="recipes-section">
<jsp:include page="header.jsp"/>

<section>
    <div class="row padding-small">
        <i class="fas fa-users icon-users"></i>
        <h1>Przepisy naszych użytkowników:</h1>
        <hr>
        <div class="orange-line w-100"></div>
    </div>
</section>
<form class="center" method="post" action="/recipes">
<input type="text" name="param" placeholder="wpisz..."><button type="submit">Szukaj</button>
</form>
<section class="mr-4 ml-4">
    <table class="table">
        <thead>
        <tr class="d-flex text-color-darker">
            <th scope="col" class="col-1">ID</th>
            <th scope="col" class="col-5">NAZWA</th>
            <th scope="col" class="col-5">OPIS</th>
            <th scope="col" class="col-1">AKCJE</th>
        </tr>
        </thead>
        <tbody class="text-color-lighter">
        <c:forEach items="${recipes}" var="recipe">
        <tr class="d-flex">
            <th scope="row" class="col-1">${recipe.getLp()}</th>
            <td class="col-5">
                ${recipe.getName()}
            </td>
            <td class="col-5">${recipe.getDescription()}</td>
            <td class="col-1"><form method="post" action="/recipes/details"><button type="submit" value="${recipe.getId()}" name="id" class="btn btn-info rounded-0 text-light">Szczegóły</button></form></td>
            </c:forEach>
        </tbody>
    </table>
</section>

<jsp:include page="footer.jsp"/>

</body>
</html>
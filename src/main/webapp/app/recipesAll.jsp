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
                    <div class="col noPadding"><h3 class="color-header text-uppercase">Wszystkie przepisy</h3></div>
                    <div class="col noPadding d-flex justify-content-end mb-2">
                        <form action="/app/recipes"><button type="submit" class="btn btn-primary rounded-2 text-light m-1">Moje przepisy</button></form>
                        <form action="/app/recipesAll"><button type="submit" class="btn btn-primary rounded-2 text-light m-1">Wszystkie przepisy</button></form>
                        <a href="/app/recipe/add" class="btn btn-success rounded-2 m-1">Dodaj przepis</a></div>
                </div>
                <form style="width:300px;float:right;text-align:left" method="post" action="/app/recipesAll">
                    <input type="text" name="param" placeholder="wpisz..."><button type="submit">Szukaj</button>
                </form>
                <table class="table border-bottom schedules-content">
                    <thead>
                    <tr class="d-flex text-color-darker">
                        <th scope="col" class="col-1 center">ID</th>
                        <th scope="col" class="col-2 center">NAZWA</th>
                        <th scope="col" class="col-5 center">OPIS</th>
                        <th scope="col" class="col-4 center">AKCJE</th>
                    </tr>
                    </thead>
                    <tbody class="text-color-lighter">
                    <c:forEach items="${recipes}" var="recipe">
                        <tr class="d-flex">
                            <th scope="row" class="col-1 center">${recipe.getLp()}</th>
                            <td class="col-2 center">
                                    ${recipe.getName()}
                            </td>

                            <td class="col-5 center">${recipe.getDescription()}</td>
                            <td class="col-4 d-flex align-items-center justify-content-center flex-wrap">
                                <form action="/app/recipe/details" method="post"><button type="submit" value="${recipe.getId()}" name="id" class="btn btn-info rounded-2 text-light m-1">Szczegóły</button></form>
                                <form action="/app/recipesAll" method="post"><button type="submit" value="${recipe.getId()}" name="id" class="btn btn-warning rounded-2 text-light m-1">Dodaj</button></form>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="col noPadding d-flex justify-content-end mb-2">
                <form action="/app/recipesAll" method="get"><button type="submit" value="prev" name="page" class="btn btn-info rounded-2 text-light m-2">Prev</button></form>
                <form action="/app/recipesAll" method="get"><button type="submit" value="next" name="page" class="btn btn-info rounded-2 text-light m-2">Next</button></form>
                </div>
            </div>
        </div>
    </div>
</section>


<jsp:include page="footer.jsp"/>
</body>
</html>


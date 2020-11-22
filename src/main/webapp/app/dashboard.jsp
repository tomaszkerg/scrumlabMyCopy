<%@ page import="pl.coderslab.model.Admin" %><%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 10.11.2020
  Time: 12:41
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

        <div class="m-4 p-4 width-medium">
            <div class="dashboard-header m-4">
                <div class="dashboard-menu">
                    <div class="menu-item border-dashed">
                        <a href="/app/recipe/add">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href="/app/schedule/add">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj plan</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href="/app/schedule/recipe/add">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis do planu</span>
                        </a>
                    </div>
                </div>

                <div class="dashboard-alerts">
                    <div class="alert-item alert-info">
                        <i class="fas icon-circle fa-info-circle"></i>
                        <span class="font-weight-bold">Liczba przepisów: ${numberOfRecipes} </span>
                    </div>
                    <div class="alert-item alert-light">
                        <i class="far icon-calendar fa-calendar-alt"></i>
                        <span class="font-weight-bold">Liczba planów: ${numberOfPlans}</span>
                    </div>
                </div>
            </div>
            <div class="m-4 p-4 border-dashed">
                <h2 class="dashboard-content-title">
                    <span>Ostatnio dodany plan:</span> ${planName}
                </h2>

                    <c:forEach var="dayname" items="${dayName}">
                        <table class="table table-secondary table-bordered table-striped">
                            <thead bgcolor="#4b4c4a">
                            <tr class="d-flex">
                                <th class="col-2 center" style="color:whitesmoke">${dayname.name}</th>
                                <th class="col-8"></th>
                                <th class="col-2"></th>
                            </tr>
                            </thead>
                            <tbody>
                    <c:forEach var="element" items="${planInfo}">
                        <c:if test="${element.getDayName() == dayname.name}">
                                <tr class="d-flex">
                                    <td class="col-2 center">${element.getMealName()}</td>
                                    <td class="col-8">${element.getRecipeName()}</td>
                                    <td class="col-2 center"><form action="/app/recipe/details" method="post"><button type="submit" value="${element.getRecipeId()}" name="id" class="btn btn-primary rounded-2">Szczegóły</button></form></td>
                                </tr>
                        </c:if>
                    </c:forEach>
                            </tbody>
                </table>
                    </c:forEach>
            </div>
        </div>
    </div>
</section>


<jsp:include page="footer.jsp"/>
</body>
</html>
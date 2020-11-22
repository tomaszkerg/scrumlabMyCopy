<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 11.11.2020
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<jsp:include page="header.jsp"/>

<body>

<section class="dashboard-section">
    <section class="dashboard-section">
        <div class="row dashboard-nowrap">
            <jsp:include page="menu.jsp"/>


            <div class="m-4 p-3 width-medium ">
                <div class="dashboard-content border-dashed p-3 m-4">
                    <div class="row border-bottom border-3 p-1 m-1">
                        <div class="col noPadding">
                            <h3 class="color-header text-uppercase">SZCZEGÓŁY PLANU</h3>
                        </div>
                        <div class="col d-flex justify-content-end mb-2 noPadding">
                            <a href="/app/schedules" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>
                        </div>
                    </div>

                    <div class="schedules-content">
                        <div class="schedules-content-header">
                            <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Nazwa planu
                                </span>
                                <div class="col-sm-10">
                                    <p class="schedules-text">${planName}</p>
                                </div>
                            </div>
                            <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Opis planu
                                </span>
                                <div class="col-sm-10">
                                    <p class="schedules-text">${planDescription}</p>
                                </div>
                            </div>
                        </div>

                        <c:forEach var="dayname" items="${dayName}">
                            <table class="table table-secondary table-bordered table-striped">
                                <thead bgcolor="#4b4c4a">
                                <tr class="d-flex">
                                    <th class="col-2 center" style="color:whitesmoke">${dayname.name}</th>
                                    <th class="col-7"></th>
                                    <th class="col-3"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="element" items="${planInfo}">
                                    <c:if test="${element.getDayName() == dayname.name}">
                                        <tr class="d-flex">
                                            <td class="col-2 center">${element.getMealName()}</td>
                                            <td class="col-7">${element.getRecipeName()}</td>
                                            <td class="col-1 center"><form action="/app/schedules/recipe/delete" method="post"> <button type="submit" name="id" value="${element.getRecipePlanId()}" class="btn btn-danger rounded-2">Usuń</button></form></td>
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
        </div>
    </section>

<jsp:include page="footer.jsp"/>
</body>
</html>

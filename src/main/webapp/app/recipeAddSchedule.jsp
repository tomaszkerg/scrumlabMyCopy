<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 11.11.2020
  Time: 14:48
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
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">DODAJ PRZEPIS DO PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <button type="submit" form="recipeadd" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz</button>
                    </div>
                </div>

                <div class="schedules-content">
                    <form id="recipeadd" method="post" action="/app/schedule/recipe/add">
                        <div class="form-group row">
                            <label for="choosePlan" class="col-sm-2 label-size col-form-label">
                                Wybierz plan
                            </label>
                            <div class="col-sm-3">
                                <select class="form-control" id="choosePlan" name="plan">
                                    <c:forEach items="${schedules}" var="plan">
                                        <option value="${plan.getId()}">${plan.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="name" class="col-sm-2 label-size col-form-label">
                                Nazwa posiłku
                            </label>
                            <div class="col-sm-10">
                                <input type="text" required="required" pattern="[A-Za-z0-9]{1,20}" class="form-control" value="" name="mealname" id="name" placeholder="Nazwa posiłku">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="number" class="col-sm-2 label-size col-form-label">
                                Numer posiłku
                            </label>
                            <div class="col-sm-2">
                                <input type="number" required="required" min=1 max= 10 class="form-control" value="" name="mealnumber" id="number" placeholder="Numer posiłki">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-2 label-size col-form-label">
                                Przepis
                            </label>
                            <input type="hidden" name="id" value="${recipe.id}">
                            <div class="col-sm-4"><h3>${recipe.name}</h3></div>
                        </div>
                        <div class="form-group row">
                            <label for="day" class="col-sm-2 label-size col-form-label">
                                Dzień
                            </label>
                            <div class="col-sm-2">
                                <select class="form-control" id="day" name="dayname">
                                    <c:forEach items="${daynames}" var="dayname">
                                        <option value="${dayname.getId()}">${dayname.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>
</body>
</html>


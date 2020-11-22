<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 10.11.2020
  Time: 19:06
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
                        <h3 class="color-header text-uppercase">LISTA PLANÓW</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="/app/schedule/add" class="btn btn-success rounded-2 m-1">Dodaj plan</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <table class="table border-bottom">
                        <thead>
                        <tr class="d-flex">
                            <th class="col-1 center">ID</th>
                            <th class="col-2 center">NAZWA</th>
                            <th class="col-5 center">OPIS</th>
                            <th class="col-4 center">AKCJE</th>
                        </tr>
                        </thead>
                        <tbody class="text-color-lighter">
                        <c:forEach var="plan" items="${schedules}" varStatus="index">
                        <tr class="d-flex">
                            <th class="col-1 center">${index.count}</th>
                            <td class="col-2 center">${plan.getName()}</td>
                            <td class="col-5 center">
                                ${plan.getDescription()}
                            </td>
                            <td class="col-4 d-flex align-items-center justify-content-center flex-wrap">
<%--                                <a href="/app/schedules/delete" class="btn btn-danger rounded-2 text-light m-1">Usuń</a>--%>
                                <form action="/app/schedule/delete" method="post"><button type="submit" value="${plan.getId()}" name="id" class="btn btn-danger rounded-2 text-light m-1">Usuń</button></form>
                                <form action="/app/schedule/details" method="post"><button type="submit" value="${plan.getId()}" name="id" class="btn btn-info rounded-2 text-light m-1">Szczegóły</button></form>
                                <form action="/app/schedule/edit" method="post"><button type="submit" value="${plan.getId()}" name="id" class="btn btn-warning rounded-2 text-light m-1">Edytuj</button></form>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>
</body>
</html>

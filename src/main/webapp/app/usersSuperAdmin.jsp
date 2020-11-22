<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 10.11.2020
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="header.jsp"/>

<body>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <jsp:include page="menu.jsp"/>

        <div class="m-4 p-3 width-medium">
            <div class="m-4 p-3 border-dashed view-height">

                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">LISTA UŻYTKOWNIKÓW</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="/app/dashboard" class="btn btn-warning rounded-2 m-1">Powrót</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <table class="table">
                        <thead>
                        <tr class="d-flex">
                            <th class="col-1 center">ID</th>
                            <th class="col-2 center">IMIĘ</th>
                            <th class="col-3 center">NAZWISKO</th>
                            <th class="col-4 center">EMAIL</th>
                            <th class="col-2 center">AKCJE</th>
                        </tr>
                        </thead>
                        <tbody class="text-color-lighter">
                        <c:forEach var="admin" items="${admins}">
                        <tr class="d-flex">
                            <th class="col-1 center">${admin.id}</th>
                            <td class="col-2 center">${admin.firstName}</td>
                            <td class="col-3 center">${admin.lastName}</td>
                            <td class="col-4 center">${admin.email}</td>
                            <td class="col-2 d-flex align-items-center justify-content-center flex-wrap">
                                <c:if test="${admin.enable == 0}">
                                    <form action="/app/users-list" method="post"><button type="submit" value="${admin.id}" name="id" class="btn btn-primary rounded-2 text-light m-1">Odblokuj</button></form>
                                </c:if>
                                <c:if test="${admin.enable == 1}">
                                <form action="/app/users-list" method="post"><button type="submit" value="${admin.id}" name="id" class="btn btn-danger rounded-2 text-light m-1">Blokuj</button></form>
                                </c:if>
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

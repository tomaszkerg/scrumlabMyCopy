<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 10.11.2020
  Time: 19:08
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

        <div class="m-4 p-3 width-medium text-color-darker">
            <div class="m-4 border-dashed view-height">
                <!-- fix action, method -->
                <!-- add name attribute for all inputs -->
                <form action="/app/edit-user" method="post">
                    <div class="mt-4 ml-4 mr-4">
                        <div class="row border-bottom border-3">
                            <div class="col"><h3 class="color-header text-uppercase">Edytuj dane</h3></div>
                            <div class="col d-flex justify-content-end mb-2">
                                <button type="submit" class="btn btn-warning rounded-2 m-1">Zapisz
                                </button>
                            </div>
                        </div>

                        <table class="table borderless">
                            <tbody>
                            <output style="color:red"><c:out value="${emptyname}"></c:out> </output>
                            <tr class="d-flex">
                                <th scope="row" class="col-2"><h4>Imię</h4></th>
                                <td class="col-7">
                                    <input class="w-100 p-1" name="name" value="${user.getFirstName()}">
                                </td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2"><h4>Nazwisko</h4></th>
                                <td class="col-7">
                                    <input class="w-100 p-1" name="surname" value="${user.getLastName()}">
                                </td>
                            </tr>
                            <output style="color:red"><c:out value="${emailfail}"></c:out> </output>
                            <tr class="d-flex">
                                <th scope="row" class="col-2"><h4>Email</h4></th>
                                <td class="col-3">
                                    <input class="p-1 w-100" type="text" name="email" value="${user.getEmail()}">
                                </td>
                                <c:out value="${error}"></c:out>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>
</body>
</html>

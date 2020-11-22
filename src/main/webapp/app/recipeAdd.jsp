<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 11.11.2020
  Time: 10:11
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
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <form method="post" action="/app/recipe/add">
                    <div class="mt-4 ml-4 mr-4">
                        <div class="row border-bottom border-3">
                            <div class="col"><h3 class="color-header text-uppercase">Nowy przepis</h3></div>
                            <div class="col d-flex justify-content-end mb-2">
                                <button type="submit" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz</button>
                            </div>
                        </div>

                        <table class="table borderless">
                            <tbody>
                            <output style="color:red"><c:out value="${recipefail}"></c:out></output>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Nazwa Przepisu</th>
                                <td class="col-7">

                                    <input name="name" value="${recipe.name}" class="w-100 p-1" value="">
                                </td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Opis przepisu</th>
                                <td class="col-7"><textarea name="description" class="w-100 p-1" rows="5"></textarea></td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Przygotowanie (minuty)</th>
                                <td class="col-3">
                                    <input name="time" class="p-1" type="number" placeholder="0" min="0" max="1440" value="">
                                </td>
                            </tr>
                            </tbody>
                        </table>

                        <div class="row d-flex">
                            <div class="col-5 border-bottom border-3"><h3 class="text-uppercase">Sposób
                                przygotowania</h3></div>
                            <div class="col-2"></div>
                            <div class="col-5 border-bottom border-3"><h3 class="text-uppercase">Składniki</h3></div>
                        </div>
                        <div class="row d-flex">
                            <div class="col-5 p-4">
                                <textarea name="preparation" class="w-100 p-1" rows="10"></textarea>
                            </div>
                            <div class="col-2"></div>

                            <div class="col-5 p-4">
                                <textarea name="ingredients" class="w-100 p-1" rows="10"></textarea>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 10.11.2020
  Time: 09:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<body>
<jsp:include page="header.jsp"/>

<section class="dashboard-section">
    <div class="container pt-4 pb-4">
        <div class="border-dashed view-height">
            <div class="container w-25">
                <form class="padding-small text-center" method="post" action="/login">
                    <h1 class="text-color-darker">Logowanie</h1>
                    <div class="form-group">Podaj adres email:
                        <output style="color:red"><c:out value='${notexist}'></c:out> </output>
                        <output style="color:red"><c:out value='${block}'></c:out> </output>
                        <input type="text" class="form-control" id="email" name="email" placeholder="podaj adres email">
                    </div>
                    <div class="form-group">Podaj hasło:
                        <output style="color:red"><c:out value='${notok}'></c:out> </output>
                        <input type="password" class="form-control" id="password" name="password" placeholder="podaj hasło">
                    </div>
                    <button class="btn btn-color rounded-0" type="submit">Zaloguj</button>
                </form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>

</body>
</html>

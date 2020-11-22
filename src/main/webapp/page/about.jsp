<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<body>
<jsp:include page="header.jsp"/>

<section class="padding-medium story bg-light" id="about">
    <div class="container d-flex justify-content-center align-items-center">

        <div class="container py-5">
            <h1><p class="lead" style="display: flex; align-items: center; justify-content: center; color: darkslategray;
            font-family: Chilanka">Opis aplikacji</p></h1>
            <h3><p class="lead" style="display: flex; align-items: center; justify-content: center; color: darkslategray;
            font-family: Chilanka">${aboutApp.getDesc()}</p></h3>
        </div>
        </div>
</section>
<jsp:include page="footer.jsp"/>
</body>
</html>

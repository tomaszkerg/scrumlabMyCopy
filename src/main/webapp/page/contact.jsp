<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<body>
<jsp:include page="header.jsp"/>
<section class="last-info-section padding-small" id="contact" style="display:flex; align-items: center; justify-content: center" >
    <div class="container py-5">
        <h1 class="display-4 tagline" style="display: flex; justify-content: center">
            Kontakt </h1>
        <div class="row" style="display: flex; justify-content: center">
            <div class="col-md-10">

                <p class="lead" style="display:flex; justify-content:center">Jeśli masz pytania dotyczące naszego produktu, skontaktuj się z nami. Wystarczy wypełnić
                    formularz kontaktowy. Odpowiemy tak szybko, jak to możliwe.</p>
                <div class="row">
                    <div class="col-md-4">
                    </div>
                </div>
            </div>
            <form class="padding-small text-center" method="post" action="/contact">
            <div class="field">
                <label class="label" style="color: black">Name</label>
                <div class="control">
                    <input class="input" name="name" id="name" type="text" placeholder="Jak się nazywasz?">
                </div>
            </div>
            <div class="field">
                <label class="label" style="color: black">Email</label>
                <div class="control has-icons-left has-icons-right">
                    <input class="input is-danger" type="email" id="email" name="email" placeholder="Jaki masz email?">
                </div>
            </div>

            <div class="field">
                <label class="label" style="color: black">Message</label>
                <div class="control">
                    <textarea class="textarea" id="message" name="message" placeholder="O co chcesz nas spytać?"></textarea>
                </div>
            </div>
            <div class="control">
                <button class="button is-link" type="submit" >Submit</button>
            </div>
               <output style="color: darkred"><c:out value="${confirmed}"></c:out></output>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="container py-5">
            <div class="field">
                <form class="center" method="post" action="/contact">
                    <p class="lead" style="color: black">Nazwa właściciela:</p> <p> ${owner.getName()}</p>
                    <p class="lead" style="color: black">Adres właściciela: </p><p>${owner.getAdress()}</p>
                    <p class="lead" style="color: black">Telefon: </p><p>${owner.getTelefon()}</p>
                    <p class="lead" style="color: black">Email:</p><p> ${owner.getEmail()}</p>
                </form>
            </div>
        </div>
    </div>

</section>

<jsp:include page="footer.jsp"/>
</body>
</html>

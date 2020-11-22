package pl.coderslab.web.page;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/page/register.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Można przenieść do osobnej metody tworzącej Admina
        Admin admin = new Admin();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        admin.setFirstName(name);
        admin.setLastName(surname);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setSuperAdmin(0);
        admin.setEnable(1);
        AdminDao adminDao = new AdminDao();

        // TODO To też można wydzielić jako osobna metoda i przede wszystkim używać spacji i
        // łamania wierszy, bo to jest coś strasznego dla oczu...

        // TODO Do obsługi błędów pomyśleć o mapie
        Map<String, String> errors = new HashMap<>();
        if (name.isEmpty() || name.isBlank() | surname.isBlank() || surname.isEmpty() || email.isEmpty() || email.isBlank() || password.isEmpty() || password.isBlank() || repassword.isBlank() || repassword.isEmpty()) {
            // TODO Jak jest błąd to warto umieścić w requescie również obiekt z wprowadzonymi danymi
            errors.put("*", "Wszystkie pola muszą być wypełnione");
//            request.setAttribute("allinput", "wszystkie pola muszą być wypełnione");
//            getServletContext().getRequestDispatcher("/page/register.jsp").forward(request, response);
        } else if (!password.equals(repassword)) {
            errors.put("password", "Hasła muszą być takie same");
//            request.setAttribute("passwordfail", "hasła muszą być takie same");
//            getServletContext().getRequestDispatcher("/page/register.jsp").forward(request, response);
        } else if (adminDao.checkIfLoginExist(email)) {
            errors.put("email", "Użytkownik o takim email już jest zarejestrowany");
//            request.setAttribute("error", "Użytkownik o takim emailu jest już zarejestrowany");
//            getServletContext().getRequestDispatcher("/page/register.jsp").forward(request, response);
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            getServletContext().getRequestDispatcher("/page/register.jsp").forward(request, response);
        } else {
            adminDao.create(admin);
            response.sendRedirect("/login");
        }
    }
}






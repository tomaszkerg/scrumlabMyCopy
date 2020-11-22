package pl.coderslab.web.page;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/page/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        HttpSession session = req.getSession();
        String password = req.getParameter("password");
        AdminDao adminDao = new AdminDao();
        // TODO Strasznie zły pomysł z tym info w Stringu :)
        if (email.equals("") || email.isBlank() || !email.matches(".+[@].+")) {
            req.setAttribute("notexist", "Podaj prawidłowy adres email");
            getServletContext().getRequestDispatcher("/page/login.jsp").forward(req, resp);
        } else if (password.equals("") || password.isBlank()) {
            req.setAttribute("notok", "Hasło nie może być puste");
            getServletContext().getRequestDispatcher("/page/login.jsp").forward(req, resp);
        } else {
            String check = adminDao.checkLogin(email, password);
            // TODO Zło, zło, zło, zło :)
            String notok = "notok";
            String notexist = "notexist";
            String ok = AdminDao.CHECK_LOGIN_OK;
            // TODO Po co wcześinejsze sprawdzenie (checkLogin), skoro tutaj znowu się to dzieje?
            Admin admin = adminDao.readByEmail(email);
            // TODO Walidacja danych logowania powinna być dopiero tutaj
            if (notexist.equals(check)) {
                req.setAttribute("notexist", "Taki użytkownik nie istnieje");
                getServletContext().getRequestDispatcher("/page/login.jsp").forward(req, resp);
            } else if (admin.getEnable() == 0) {
                req.setAttribute("block", "użytkownik zablokowany");
                getServletContext().getRequestDispatcher("/page/login.jsp").forward(req, resp);
            } else if (notok.equals(check)) {
                req.setAttribute("notok", "Nieprawidłowe hasło!");
                getServletContext().getRequestDispatcher("/page/login.jsp").forward(req, resp);
            } else if (ok.equals(check)) {
                if (session.getAttribute("username") == null) {
                    session.setAttribute("username", admin);
                }
                resp.sendRedirect("/app/dashboard");
            }
        }
    }
}

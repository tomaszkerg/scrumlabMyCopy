package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/app/edit-user")
public class EditAdminAppServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        req.setAttribute("user", admin);
        getServletContext().getRequestDispatcher("/app/editUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String firstName = req.getParameter("name");
        String lastName = req.getParameter("surname");
        String email = req.getParameter("email");
        Admin admin = (Admin)session.getAttribute("username");
        AdminDao adminDao = new AdminDao();
        if(firstName.isEmpty() ||firstName.isBlank() || lastName.isBlank() || lastName.isEmpty() || email.isEmpty() || email.isBlank()) {
            req.setAttribute("user", admin);
            req.setAttribute("emptyname","musisz wypełnić wszystkie pola");
            getServletContext().getRequestDispatcher("/app/editUser.jsp").forward(req, resp);
        }else{
            if (!adminDao.checkIfLoginExist(email)) {
                admin.setEmail(email);
                admin.setFirstName(firstName);
                admin.setLastName(lastName);
                adminDao.update(admin);
                resp.sendRedirect("/app/dashboard");
            }else if(admin.getEmail().equals(email)) {
                admin.setFirstName(firstName);
                admin.setLastName(lastName);
                adminDao.update(admin);
                resp.sendRedirect("/app/dashboard");
            }else{
                req.setAttribute("user", admin);
                req.setAttribute("emailfail","Taki adres email już istnieje w naszej bazie");
                getServletContext().getRequestDispatcher("/app/editUser.jsp").forward(req, resp);
            }
        }
    }
}

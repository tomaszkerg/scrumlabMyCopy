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


@WebServlet("/app/edit-password")
public class EditPasswordAppServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        req.setAttribute("user", admin);
        getServletContext().getRequestDispatcher("/app/editPassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword");
        AdminDao adminDao = new AdminDao();
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        if (!password.isBlank() && !password.isEmpty()) {
            if (password.equals(repassword)) {
                admin.setPassword(password);
                adminDao.updatePassword(admin);
                resp.sendRedirect("/app/dashboard");
            }else{
                req.setAttribute("passfail","hasła muszą być takie same");
                req.setAttribute("user", admin);
                getServletContext().getRequestDispatcher("/app/editPassword.jsp").forward(req, resp);
            }
        }else {
            resp.sendRedirect("/app/edit-password");
        }
    }
}

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
import java.util.List;


@WebServlet("/app/users-list")
public class UsersListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminDao adminDao = new AdminDao();
        List<Admin> admins = adminDao.findAll();
        req.setAttribute("admins",admins);
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        req.setAttribute("user", admin);
        getServletContext().getRequestDispatcher("/app/usersSuperAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.read(id);
        if(admin.getEnable()==0){
            admin.setEnable(1);
            adminDao.update(admin);
            resp.sendRedirect("/app/users-list");
        }else{
            admin.setEnable(0);
            adminDao.update(admin);
            resp.sendRedirect("/app/users-list");
        }
    }
}

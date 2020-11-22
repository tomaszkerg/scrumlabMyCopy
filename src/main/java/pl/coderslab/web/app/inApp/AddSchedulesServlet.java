package pl.coderslab.web.app.inApp;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/schedule/add")
public class AddSchedulesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        req.setAttribute("user", admin);
        getServletContext().getRequestDispatcher("/app/scheduleAdd.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        Plan plan = new Plan();

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        plan.setName(name);
        plan.setDescription(description);
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        req.setAttribute("user", admin);
        if(name.isEmpty()||name.isBlank()||description.isBlank()||description.isEmpty()){
            req.setAttribute("planfail","wszystkie pola muszą być wypełnione");
            req.setAttribute("user", admin);
            getServletContext().getRequestDispatcher("/app/scheduleAdd.jsp").forward(req, resp);
        }else{
            plan.setAdminId(admin.getId());
            PlanDao planDao = new PlanDao();
            planDao.create(plan);
            resp.sendRedirect("/app/schedules");
        }
    }
}

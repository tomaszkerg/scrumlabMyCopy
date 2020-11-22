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

@WebServlet("/app/schedule/edit")
public class EditScheduleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        // TODO Hura optymizm :)
        int id = Integer.parseInt(req.getParameter("id"));
        Plan plan = planDao.read(id);
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("username");
        req.setAttribute("user", admin);
        req.setAttribute("plan", plan);
        getServletContext().getRequestDispatcher("/app/editPlan.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Co tu się dzieje na GET i dlaczego?
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(id);
        plan.setName(name);
        plan.setDescription(description);
        planDao.update(plan);

        resp.sendRedirect("/app/schedules");
    }
}

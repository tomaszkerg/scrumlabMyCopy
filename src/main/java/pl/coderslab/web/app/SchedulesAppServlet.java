package pl.coderslab.web.app;

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
import java.util.List;


@WebServlet("/app/schedules")
public class SchedulesAppServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        List<Plan> schedules = planDao.findAllForUser(admin);
        for(int i = 1; i<=schedules.size();i++){
            schedules.get(i-1).setLp(i);
        }
        req.setAttribute("schedules", schedules);
        req.setAttribute("user", admin);
        getServletContext().getRequestDispatcher("/app/schedules.jsp").forward(req, resp);
    }
}

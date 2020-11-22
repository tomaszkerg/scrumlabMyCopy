package pl.coderslab.web.app.inApp;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/schedule/details")
public class ScheduleDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int id = Integer.parseInt(req.getParameter("id"));
        Admin admin = (Admin)session.getAttribute("username");
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(id);
        DayNameDao dayNameDao = new DayNameDao();
        List<PlanInfo> planInfo = planDao.getPlanInfoById(plan);

        req.setAttribute("user",admin);
        req.setAttribute("planInfo",planInfo);

        req.setAttribute("planName",planDao.read(id).getName());
        req.setAttribute("planDescription",planDao.read(id).getDescription());
        req.setAttribute("dayName",dayNameDao.dayNames(planInfo));
        getServletContext().getRequestDispatcher("/app/scheduleDetails.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app/scheduleDetails.jsp").forward(req, resp);

    }
}

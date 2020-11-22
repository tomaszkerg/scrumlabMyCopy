package pl.coderslab.web.app.inApp;

import pl.coderslab.dao.PlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/schedule/delete")
public class DeletePlanServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //HttpSession session = req.getSession();
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("id", id);
        getServletContext().getRequestDispatcher("/app/deleteSchedule.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        PlanDao planDao = new PlanDao();
        planDao.deleteAllforPlan(id);
        planDao.delete(id);
        resp.sendRedirect("/app/schedules");

    }
}

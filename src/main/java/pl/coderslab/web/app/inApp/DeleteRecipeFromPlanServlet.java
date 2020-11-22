package pl.coderslab.web.app.inApp;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/schedules/recipe/delete")
public class DeleteRecipeFromPlanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        req.setAttribute("id",id);
        getServletContext().getRequestDispatcher("/app/delRecipeFromPlan.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        PlanDao planDao = new PlanDao();
        planDao.deleteRecipeFromPlan(id);
        resp.sendRedirect("/app/schedules");
    }
}


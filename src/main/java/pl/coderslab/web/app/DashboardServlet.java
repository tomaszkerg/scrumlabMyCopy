package pl.coderslab.web.app;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.PlanInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/app/dashboard")
public class DashboardServlet extends HttpServlet {

    RecipeDao recipeDao = new RecipeDao();
    PlanDao planDao = new PlanDao();
    DayNameDao dayNameDao = new DayNameDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // TODO Skoro username, to czemu Admin a nie String?
        Admin admin = (Admin)session.getAttribute("username");
        // TODO To z kolei można przenieść do poziomu pól klasy

        List<PlanInfo> planInfo = planDao.getPlanInfo(admin);
        req.setAttribute("numberOfRecipes", recipeDao.getRecipiesById(admin) );
        req.setAttribute("numberOfPlans", planDao.getPlansById(admin) );
        req.setAttribute("user",admin);
        req.setAttribute("planInfo",planInfo);
        req.setAttribute("planName",planDao.getPlanName(admin));
        req.setAttribute("dayName",dayNameDao.dayNames(planInfo));
        getServletContext().getRequestDispatcher("/app/dashboard.jsp").forward(req, resp);

    }

}

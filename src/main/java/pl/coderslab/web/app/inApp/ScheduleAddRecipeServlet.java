package pl.coderslab.web.app.inApp;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/app/schedule/recipe/add")
public class ScheduleAddRecipeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        PlanDao planDao = new PlanDao();
        RecipeDao recipeDao = new RecipeDao();
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        req.setAttribute("user", admin);
        req.setAttribute("recipes", recipeDao.findAllForUser(admin));
        List<Plan> schedules = planDao.findAllForUser(admin);
        req.setAttribute("schedules", schedules);
        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> dayNames = dayNameDao.findAll();
        req.setAttribute("daynames",dayNames);
        getServletContext().getRequestDispatcher("/app/scheduleAddRecipe.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        String mealname = req.getParameter("mealname");
        String mealnumber = req.getParameter("mealnumber");
        String dayName = req.getParameter("dayname");
        String plan = req.getParameter("plan");
        String recipe = req.getParameter("recipe");
        int displayOrder = 0;
        int dayNameId = 0;
        int planId = 0;
        int recipeId = 0;
        try{
            displayOrder = Integer.parseInt(mealnumber);
            dayNameId = Integer.parseInt(dayName);
            planId = Integer.parseInt(plan);
            recipeId = Integer.parseInt(recipe);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        RecipePlan recipePlan = new RecipePlan();
        recipePlan.setDayNameId(dayNameId);
        recipePlan.setDisplayOrder(displayOrder);
        recipePlan.setMealName(mealname);
        recipePlan.setRecipeId(recipeId);
        recipePlan.setPlanId(planId);
        PlanDao planDao = new PlanDao();
        planDao.createRecipeForPlan(recipePlan);
        resp.sendRedirect("/app/dashboard");

    }
}

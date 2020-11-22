package pl.coderslab.web.app.inApp;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/recipe/delete")
public class DeleteRecipeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        int id = Integer.parseInt(req.getParameter("id"));
        recipeDao.delete(id);
        resp.sendRedirect("/app/recipes");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("id",id);
        RecipeDao recipeDao = new RecipeDao();
        // TODO Co to u licha jest?!?!?!
        String check = recipeDao.checkRecipePlan(recipeDao.read(id));
        if(check.equals("yes")){
            // TODO Redirect
            resp.sendRedirect("/app/recipes?recipeInPlan");
        }else if(check.equals("no")){
            getServletContext().getRequestDispatcher("/app/recipeDelete.jsp").forward(req, resp);
        }
    }
}

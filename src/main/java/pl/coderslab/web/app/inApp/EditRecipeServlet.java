package pl.coderslab.web.app.inApp;


import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/app/recipe/edit")
public class EditRecipeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        int id = Integer.parseInt(req.getParameter("id"));
        Recipe recipe = recipeDao.read(id);
        List<String> ingredients = new ArrayList<>();
        for(String s:recipe.getIngredients().split(",")){
            s=s.replaceAll(" ","");
            s=s.replaceAll("\n","");
            for(String h:s.split("\n")) {
                ingredients.add("-"+h+"\n");
            }
        }
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        req.setAttribute("user", admin);
        req.setAttribute("ingredients",ingredients);
        req.setAttribute("recipe",recipe);
        getServletContext().getRequestDispatcher("/app/editRecipe.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        int preparationTime = Integer.parseInt(req.getParameter("preparationTime"));
        String preparation = req.getParameter("preparation");
        String ingredients = req.getParameter("ingredients");
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(id);
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setPreparation(preparation);
        recipe.setPreparationTime(preparationTime);
        String ingredientsfix = "";
        for(String s:ingredients.split("-")){
            ingredientsfix += s+",";
        }
        recipe.setIngredients(ingredientsfix);
        System.out.println(ingredientsfix);
        recipeDao.update(recipe);

        resp.sendRedirect("/app/recipes");
    }
}

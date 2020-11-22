package pl.coderslab.web.page;


import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;;
import java.io.IOException;
import java.util.List;


@WebServlet("/recipes")
public class RecipesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes = recipeDao.findAll();
        // TODO To jest logika warstwy prezentacji, absolutnie na stronach jsp takie coś
        for(int i = 1; i<=recipes.size(); i++){
            recipes.get(i-1).setLp(i);
        }
        req.setAttribute("recipes",recipes);
        getServletContext().getRequestDispatcher("/page/recipes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("param");
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes = recipeDao.findAllSearch(param);
        for(int i = 1; i<=recipes.size(); i++){
            recipes.get(i-1).setLp(i);
        }
        req.setAttribute("recipes",recipes);
        getServletContext().getRequestDispatcher("/page/recipes.jsp").forward(req, resp);
    }
}

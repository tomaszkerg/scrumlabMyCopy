package pl.coderslab.web.page;

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
import java.util.Arrays;
import java.util.List;

@WebServlet("/recipes/details")
public class RecipesDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        int id = Integer.parseInt(req.getParameter("id"));
        Recipe recipe = recipeDao.read(id);
        req.setAttribute("recipe", recipe);
        List<String> ingredients = List.of(recipe.getIngredients().split(","));
        // TODO Da się prościej & czytelniej
//        for(String s:recipe.getIngredients().split(",")){
//            ingredients.add(s);
//        }
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        req.setAttribute("user", admin);
        req.setAttribute("ingredients",ingredients);
        getServletContext().getRequestDispatcher("/page/recipeDetails.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/page/recipeDetails.jsp").forward(req, resp);
    }
}

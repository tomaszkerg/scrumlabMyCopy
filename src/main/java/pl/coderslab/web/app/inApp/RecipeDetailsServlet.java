package pl.coderslab.web.app.inApp;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/app/recipe/details")
public class RecipeDetailsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        int id = Integer.parseInt(request.getParameter("id"));
        Recipe recipe = recipeDao.read(id);
        request.setAttribute("recipe", recipe);
        List<String> ingredients = new ArrayList<>();
        for(String s:recipe.getIngredients().split(",")){
            for(String h:s.split("\n")) {
                ingredients.add(h);
            }
        }
        HttpSession session = request.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        request.setAttribute("user", admin);
        request.setAttribute("ingredients",ingredients);
        getServletContext().getRequestDispatcher("/app/recipeDetails.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app/recipeDetails.jsp").forward(req, resp);

    }
}
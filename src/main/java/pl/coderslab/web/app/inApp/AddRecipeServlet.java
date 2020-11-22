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

@WebServlet("/app/recipe/add")
public class AddRecipeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("username");
        req.setAttribute("recipe", new Recipe());
        req.setAttribute("user", admin);
        getServletContext().getRequestDispatcher("/app/recipeAdd.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        Recipe recipe = new Recipe();
        String name = req.getParameter("name");
        String ingredients = req.getParameter("ingredients");
        String description = req.getParameter("description");
        String time = req.getParameter("time");
        String preparation = req.getParameter("preparation");

        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("username");
        Recipe recipe = new Recipe(0, name, ingredients, description, null, null, Integer.parseInt(time), preparation, admin.getId(), 0);
boolean valid = validate(recipe);

        recipe.setAdminId(admin.getId());
        req.setAttribute("user", admin);
        // TODO Walidować obiekt recipe zamiast samodzielnych pól
        if (name.isBlank() || name.isEmpty()
                || ingredients.isEmpty() || ingredients.isBlank()
                || description.isBlank() || description.isEmpty()
                || preparation.isEmpty() || preparation.isBlank()
                || time.isBlank() || time.isEmpty()) {
            // TODO Uwzględnić oryginalne dane
            req.setAttribute("recipe", recipe);
            req.setAttribute("recipefail", "wszystkie pola muszą być wypełnione");
            getServletContext().getRequestDispatcher("/app/recipeAdd.jsp").forward(req, resp);

        } else {
            RecipeDao recipeDao = new RecipeDao();
            recipe.setName(name);
            recipe.setIngredients(ingredients);
            recipe.setDescription(description);
            recipe.setPreparation(preparation);
            int timeint = Integer.parseInt(req.getParameter("time"));
            recipe.setPreparationTime(timeint);
            recipeDao.create(recipe);
            resp.sendRedirect("/app/recipes");
        }

    }

    private boolean validate(Recipe recipe) {
        return false;
    }
}

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


@WebServlet("/app/recipesAll")
public class RecipesAllServlet extends HttpServlet {

    // TODO Zło absolutne. Nie wolno!
    private int p = 0;
    private RecipeDao recipeDao = new RecipeDao();
    private List<Recipe> recipes = recipeDao.findAll();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        for (int i = 1; i <= recipes.size(); i++) {
            recipes.get(i - 1).setLp(i);
        }
        List<Recipe> recipes10 = new ArrayList<>();
        if ("next".equals(page)) {
            if (recipes.size() - p > 10) p += 10;
        }
        if ("prev".equals(page)) {
            if (p >= 10) p -= 10;
        }
        if (recipes.size() - p < 10) {
            for (int i = p; i < recipes.size(); i++) {
                recipes10.add(recipes.get(i));
            }
        } else {
            for (int i = p; i < p + 10; i++) {
                recipes10.add(recipes.get(i));
            }
        }
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("username");
        req.setAttribute("user", admin);
        req.setAttribute("recipes", recipes10);
        getServletContext().getRequestDispatcher("/app/recipesAll.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("param");
        String page = req.getParameter("page");
        if(param!=null) {
            recipes = recipeDao.findAllSearch(param);
            for (int i = 1; i <= recipes.size(); i++) {
                recipes.get(i - 1).setLp(i);
            }
            List<Recipe> recipes10 = new ArrayList<>();
            if ("next".equals(page)) {
                if (recipes.size() - p > 10) p += 10;
            }
            if ("prev".equals(page)) {
                if (p >= 10) p -= 10;
            }
            if (recipes.size() - p < 10) {
                for (int i = p; i < recipes.size(); i++) {
                    recipes10.add(recipes.get(i));
                }
            } else {
                for (int i = p; i < p + 10; i++) {
                    recipes10.add(recipes.get(i));
                }
            }
            req.setAttribute("recipes", recipes10);
            getServletContext().getRequestDispatcher("/app/recipesAll.jsp").forward(req, resp);
        }
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("username");
        int id = Integer.parseInt(req.getParameter("id"));
        Recipe recipe = recipeDao.read(id);
        recipe.setAdminId(admin.getId());
        req.setAttribute("user", admin);
        recipeDao.create(recipe);
        resp.sendRedirect("/app/recipesAll");

    }
}



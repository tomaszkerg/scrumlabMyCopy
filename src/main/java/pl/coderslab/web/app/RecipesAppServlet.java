package pl.coderslab.web.app;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
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


@WebServlet("/app/recipes")
public class RecipesAppServlet extends HttpServlet {

    private int p = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        List<Recipe> recipes = recipeDao.findAllForUser(admin);
        String page = req.getParameter("page");
        for(int i = 1; i<=recipes.size();i++){
            recipes.get(i-1).setLp(i);
        }
        List<Recipe> recipes10 = new ArrayList<>();
        if("next".equals(page)){
            if(recipes.size()-p>=10) p+= 10;
        }
        if("prev".equals(page)){
            if(p>=10) p -=10;
        }
        if(recipes.size()-p<10){
            for(int i = p; i<recipes.size();i++){
                recipes10.add(recipes.get(i));
            }
        }else{
            for(int i = p; i<p+10;i++){
                recipes10.add(recipes.get(i));
            }
        }
        req.setAttribute("recipes", recipes10);
        req.setAttribute("user", admin);
        getServletContext().getRequestDispatcher("/app/recipes.jsp").forward(req, resp);
    }
}

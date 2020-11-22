package pl.coderslab.web.page;

import lombok.extern.slf4j.Slf4j;
import pl.coderslab.dao.AboutAppDao;
import pl.coderslab.dao.OwnersDao;
import pl.coderslab.model.AboutApp;
import pl.coderslab.model.Owners;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/about")
public class AboutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AboutAppDao aboutAppDao = new AboutAppDao();
        AboutApp aboutApp = aboutAppDao.read(1);
        req.setAttribute("aboutApp", aboutApp);
        getServletContext().getRequestDispatcher("/page/about.jsp").forward(req, resp);
    }
}

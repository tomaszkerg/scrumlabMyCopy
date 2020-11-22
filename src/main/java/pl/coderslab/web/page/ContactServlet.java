package pl.coderslab.web.page;

import pl.coderslab.dao.ContactDataDao;
import pl.coderslab.dao.OwnersDao;
import pl.coderslab.model.ContactData;
import pl.coderslab.model.Owners;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/contact")
public class ContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OwnersDao ownersDao = new OwnersDao();
        Owners owner = ownersDao.read(1);
        req.setAttribute("owner", owner);
        getServletContext().getRequestDispatcher("/page/contact.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ContactData contactData = new ContactData();
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String message = req.getParameter("message");
        contactData.setName(name);
        contactData.setEmail(email);
        contactData.setMessage(message);

        ContactDataDao contactDataDao = new ContactDataDao();
        contactDataDao.create(contactData);
        req.setAttribute("confirmed", "Formularz wysłano poprawnie!");
        OwnersDao ownersDao = new OwnersDao();
        Owners owner = ownersDao.read(1);
        req.setAttribute("owner", owner);
        getServletContext().getRequestDispatcher("/page/contact.jsp").forward(req, resp);


        //        OwnersDao ownersDao = new OwnersDao();
//        Owners owner = ownersDao.read(1);
//        req.setAttribute("owner", owner);
//        getServletContext().getRequestDispatcher("/page/contact.jsp").forward(req, resp);
    }
}

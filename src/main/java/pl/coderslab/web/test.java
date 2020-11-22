package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
public class test extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.read(1);
        admin.setPassword("12345");
        adminDao.update(admin);


        int test = planDao.getPlansById(admin);
        System.out.println(test);

    }

    public static void main(String[] args) {

    }
}

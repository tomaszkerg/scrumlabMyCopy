package pl.coderslab.utils;

import pl.coderslab.model.Admin;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

public class AdminConverter {

    public static Admin from(HttpServletRequest request) {
        Admin admin = new Admin();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        admin.setFirstName(name);
        admin.setLastName(surname);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setSuperAdmin(0);
        admin.setEnable(1);
        return admin;
    }

    public static Admin from(ResultSet resultSet) {
        //
        return null;
    }
}

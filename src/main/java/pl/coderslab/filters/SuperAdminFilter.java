package pl.coderslab.filters;

import pl.coderslab.model.Admin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/app/users-list")
public class SuperAdminFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Admin admin = (Admin)session.getAttribute("username");
        if(session.getAttribute("username")==null){
            res.sendRedirect("/login");
        }else{
            if(admin.getSuperAdmin()==0){
                res.sendRedirect("/app/dashboard");
            }else {
                chain.doFilter(req, res);
            }
        }

    }
}
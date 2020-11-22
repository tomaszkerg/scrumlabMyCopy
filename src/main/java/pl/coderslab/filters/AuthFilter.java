package pl.coderslab.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// TODO Częściej filtrujemy na głównych ścieżkach, a w konfiguracji robimy, co filtrujemy
@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    private Set<String> securedPaths = new HashSet<>();
    private Map<String, Set<String>> securedPathsByRoles = new HashMap<>();
    public AuthFilter() {
        securedPaths.add("/app");
        securedPathsByRoles.put("ADMIN", Set.of());
        securedPathsByRoles.put("SUPERADMIN", Set.of());
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        for (String securedPath : securedPaths) {
            if (requestURI.startsWith(securedPath)) {
                HttpSession session = req.getSession();
                if(session.getAttribute("username")==null){
                    res.sendRedirect("/login");
                }else{
                    chain.doFilter(req, res);
                }
            }
        }
        chain.doFilter(req, res);

    }
}
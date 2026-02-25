package com.oceanview.filter;

import com.oceanview.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * AuthFilter
 * Central authentication & role-based authorization filter
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();

        /* ========= PUBLIC / UNPROTECTED ========= */
        if (uri.endsWith("login.html")
                || uri.endsWith("/login")
                || uri.contains("/css/")
                || uri.contains("/api/")) {

            chain.doFilter(request, response);
            return;
        }

        /* ========= SESSION CHECK ========= */
        HttpSession session = req.getSession(false);

        if (session == null ||
            session.getAttribute("loggedUser") == null) {

            res.sendRedirect(ctx + "/login.html");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        String role = user.getRole();

        /* ========= ADMIN ONLY ========= */
        if (uri.contains("admin")
                && !"ADMIN".equalsIgnoreCase(role)) {

            res.sendRedirect(ctx + "/error.html");
            return;
        }

        chain.doFilter(request, response);
    }
}

package com.oceanview.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/bill-error")
public class BillErrorApiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);

        resp.setContentType("text/plain");

        if (session == null) {
            return;
        }

        Object error = session.getAttribute("billError");

        if (error != null) {
            resp.getWriter().print(error.toString());

            // ✅ IMPORTANT: remove after showing ONCE
            session.removeAttribute("billError");
        }
    }
}

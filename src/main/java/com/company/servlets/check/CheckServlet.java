package com.company.servlets.check;

import com.company.daos.UserDAO;
import com.company.domains.User;
import com.company.utils.PasswordUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CheckServlet", urlPatterns = {"/check"})
public class CheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/book/check/check.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDAO userDAO = UserDAO.getInstance();
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            User user = userDAO.get(email);
            if (!(PasswordUtil.checkPassword(password, user.getPassword()) && user.getRole().equalsIgnoreCase("ADMIN"))) {
                throw new RuntimeException("Bad credentials");
            }
            request.setAttribute("user", user);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/book/admin/admin.jsp");
            requestDispatcher.forward(request, response);


        } catch (RuntimeException e) {
            request.setAttribute("credentials", "Permission denied");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/book/check/check.jsp");
            requestDispatcher.forward(request, response);
            e.printStackTrace();
        }
    }
}

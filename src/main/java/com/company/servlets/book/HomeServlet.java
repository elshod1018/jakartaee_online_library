package com.company.servlets.book;

import com.company.daos.BookDAO;
import com.company.daos.CategoryDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Path;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
@MultipartConfig(location = "/home/elshod/apps/library/upload")
public class HomeServlet extends HttpServlet {

    private static final Path rootPath = Path.of(System.getProperty("user.home"), "/apps/library/upload");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.err.println("HomeServlet.doGet");
        BookDAO bookDAO = BookDAO.getInstance();
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        request.setAttribute("books", bookDAO.findAll());
        request.setAttribute("selected", 0);
        request.setAttribute("categories", categoryDAO.findAll());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/book/home.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDAO bookDAO = BookDAO.getInstance();
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        String categoryName = request.getParameter("categoryName");
        int categoryId = Integer.parseInt(categoryName);
        if (categoryId == 0) {
            request.setAttribute("books", bookDAO.findAll());
        } else if (categoryId > 0) {
            request.setAttribute("books", bookDAO.findById(categoryId));
        }
        request.setAttribute("categories", categoryDAO.findAll());
        request.setAttribute("selected", categoryId);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/book/home.jsp");
        requestDispatcher.forward(request, response);
    }
}

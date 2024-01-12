package com.company.filters;

import com.company.daos.CategoryDAO;
import com.company.daos.UserDAO;
import com.company.domains.Category;
import com.company.domains.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

//@WebFilter(filterName = "BookAddFilter", urlPatterns = "/book/add")
public class BookAddFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        if (req.getMethod().equalsIgnoreCase("post")) {
            try {
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                UserDAO userDAO = UserDAO.getInstance();
                User user = userDAO.get(email);
                if (user.getPassword().equals(password) && user.getRole().equalsIgnoreCase("admin")) {
                    ConcurrentHashMap<String, String> constraints = new ConcurrentHashMap<>();
                    String title = req.getParameter("title");
                    String author = req.getParameter("author");
                    String publisher = req.getParameter("publisher");

                    String categoryId = req.getParameter("categoryId");
                    System.err.printf("%s %s %s %s%n", categoryId,title,author,publisher);
                    Short categoryID = Short.valueOf(categoryId);
                    CategoryDAO categoryDAO = CategoryDAO.getInstance();
                    Category category = categoryDAO.findById(categoryID);

                    String description = req.getParameter("description");

                    String publishedAt1 = req.getParameter("publishedAt");
                    LocalDate publishedAt = LocalDate.parse(publishedAt1);

                    int pages = Integer.parseInt(req.getParameter("pages"));
                    if (title == null || title.isBlank()) {
                        constraints.put("title_error", "Title can not be empty");
                    }
                    if (author == null || author.isBlank()) {
                        constraints.put("author_error", "Author can not be empty");
                    }
                    if (publisher == null || publisher.isBlank()) {
                        constraints.put("publisher_error", "Publisher can not be null");
                    }
                    if (Objects.isNull(category)) {
                        constraints.put("category_error", "Category must be chosen");
                    }
                    if (description == null || description.isBlank()) {
                        constraints.put("description_error", "Description can not be empty");
                    }
                    if (publishedAt == null) {
                        constraints.put("published_error", "Published year can not be empty");
                    }
                    if (pages < 1) {
                        constraints.put("pages_error", "Pages can not be %d".formatted(pages));
                    }
                    if (!constraints.isEmpty()) {
                        List<Category> categories = categoryDAO.findAll();
                        req.setAttribute("categories", categories);
                        req.setAttribute("constraints", constraints);
                        RequestDispatcher dispatcher = req.getRequestDispatcher("/book/create.jsp");
                        dispatcher.forward(req, res);
                    } else {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }

                } else {
                    throw new RuntimeException("Permission denied");
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                req.setAttribute("credentials", "Permission denied");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/book/check/check.jsp");
                dispatcher.forward(req, res);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}

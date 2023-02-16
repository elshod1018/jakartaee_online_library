package com.company.servlets.book;

import com.company.daos.BookDAO;
import com.company.daos.CategoryDAO;
import com.company.daos.DocumentDAO;
import com.company.domains.Book;
import com.company.domains.Category;
import com.company.domains.Document;
import com.company.dtos.ResizeImage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "BookAddServlet", urlPatterns = "/book/add")
@MultipartConfig(location = "/home/elshod/apps/library/upload")
public class BookAddServlet extends HttpServlet {

    private static final Path rootPath = Path.of(System.getProperty("user.home"), "/apps/library/upload");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        List<Category> categories = categoryDAO.findAll();
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/book/create.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String publishedAt1 = request.getParameter("publishedAt");
        LocalDate publishedAt = LocalDate.parse(publishedAt1);
        Part file = request.getPart("file");
        Part image = request.getPart("image");
        String description = request.getParameter("description");
        String publisher = request.getParameter("publisher");
        String categoryId = request.getParameter("categoryId");
        Integer pages = Integer.valueOf(request.getParameter("pages"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        Document imageDocument = saveFile(image);
        Document fileDocument = saveFile(file);
        BookDAO bookDAO = BookDAO.getInstance();
        Book book = Book.builder()
                .title(title)
                .author(author)
                .description(description)
                .publisher(publisher)
                .publishedAt(publishedAt)
                .coverId(imageDocument.getId())
                .documentId(fileDocument.getId())
                .categoryId(Integer.parseInt(categoryId))
                .pages(pages)
                .build();
        bookDAO.save(book);
        response.sendRedirect("/home");
    }

    private static Document saveFile(Part file) {
        try {
            DocumentDAO documentDAO = DocumentDAO.getInstance();
            String originalName = file.getSubmittedFileName();
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String generatedName = UUID.randomUUID() + extension;
            String mimeType = file.getContentType();
            long size = file.getSize();
            Document document = Document.builder()
                    .generatedFileName(generatedName)
                    .originalFileName(originalName)
                    .fileSize(size)
                    .mimeType(mimeType)
                    .filePath(rootPath.resolve(generatedName).toString())
                    .extension(extension)
                    .build();
            document = documentDAO.save(document);
            if (List.of(".png", ".jpg", ".jpeg").contains(extension)) {
                BufferedImage bufferedImage = ResizeImage.crop(file.getInputStream());
                ResizeImage.write(bufferedImage, rootPath.resolve(generatedName));
            } else {
                file.write(generatedName);
            }
            return document;
        } catch (Exception e) {
            // TODO: 08/02/23 redirect to error page
            throw new RuntimeException(e);
        }
    }
}

package ru.job4j.servlet;

import ru.job4j.model.Category;
import ru.job4j.store.HbmStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CategoriesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Category> allCategories = HbmStore.instOf().getAllCategories();
        final Gson gson = new GsonBuilder().create();
        String categoriesToJson = gson.toJson(allCategories);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        resp.getWriter().write(categoriesToJson);
    }
}

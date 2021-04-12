package ru.job4j.servlet;

import ru.job4j.model.Item;
import ru.job4j.model.User;
import ru.job4j.store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String[] categoriesIds = req.getParameter("categoryIds").split(",");
        HttpSession session = req.getSession();
        Item item = new Item(
                req.getParameter("description"),
                new Timestamp(System.currentTimeMillis()),
                "Not done",
                (User) session.getAttribute("user")
        );
        HbmStore.instOf().save(item, categoriesIds);
    }
}

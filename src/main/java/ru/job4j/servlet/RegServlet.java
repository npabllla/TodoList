package ru.job4j.servlet;

import ru.job4j.model.User;
import ru.job4j.store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Optional<User> user = HbmStore.instOf().findByEmail(email);
        if (user.isPresent()) {
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("Данная почта уже используется");
        } else {
            HttpSession sc = req.getSession();
            User ur = new User();
            ur.setName(name);
            ur.setEmail(email);
            ur.setPassword(password);
            HbmStore.instOf().save(ur);
            sc.setAttribute("user", ur);
        }
    }
}
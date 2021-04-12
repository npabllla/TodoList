package ru.job4j.store;

import ru.job4j.model.Category;
import ru.job4j.model.Item;
import ru.job4j.model.User;

import java.util.Collection;
import java.util.Optional;

public interface Store {
    void save(Item item, String[] categoriesIds);

    void save(User user);

    void update(int id);

    Collection<Item> findAllItemsForUser(User user);

    Collection<Category> getAllCategories();

    Optional<User> findByEmail(String email);
}

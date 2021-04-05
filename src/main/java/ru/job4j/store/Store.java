package ru.job4j.store;

import ru.job4j.model.Item;

import java.util.Collection;

public interface Store {
    void save(Item item);

    void update(int id);

    Collection<Item> findAll();
}

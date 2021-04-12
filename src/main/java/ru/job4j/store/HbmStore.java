package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.Category;
import ru.job4j.model.Item;
import ru.job4j.model.User;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class HbmStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(HbmStore.class.getName());

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory factory = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new HbmStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public void save(Item item, String[] cIds) {
        transaction(session -> {
            for (String id : cIds) {
                Category  category = session.find(Category.class, Integer.parseInt(id));
                item.addCategory(category);
            }
            session.save(item);
            return null;
        });
    }

    @Override
    public void save(User user) {
        transaction(session -> session.save(user));
    }

    @Override
    public void update(int id) {
        transaction(session -> {
                final Query query = session.createQuery("update Item set status='Done' where id=:id");
                query.setParameter("id", id);
                query.executeUpdate();
                return null;
            }
        );
    }

    @Override
    public Collection<Item> findAllItemsForUser(User user) {
        return  (List<Item>) transaction(
                session -> {
                    final Query query =  session.createQuery("from Item where user=:user");
                    query.setParameter("user", user);
                    return query.getResultList();
                }
        );
    }

    @Override
    public Collection<Category> getAllCategories() {
        return (Collection<Category>) transaction(session -> {
            final Query query = session.createQuery("from Category");
            return query.getResultList();
        });
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return transaction(session -> {
                final Query query = session.createQuery("from User where email=:email");
                query.setParameter("email", email);
                return query.getResultList().size() > 0 ? Optional.of((User) query.getResultList().get(0))
                        : Optional.empty();
            }
        );
    }

    private <T> T transaction(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception in transaction", e);
            return null;
        } finally {
            session.close();
        }
    }
}

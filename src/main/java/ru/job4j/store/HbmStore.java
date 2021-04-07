package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Item;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class HbmStore implements Store {
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
    public void save(Item item) {
        transaction(session -> session.save(item));
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
    public Collection<Item> findAll() {
        return  (List<Item>) transaction(
                session1 -> session1.createQuery("from Item").list()
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
            throw e;
        } finally {
            session.close();
        }
    }
}

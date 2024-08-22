package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sessionFactory;

    public User create(User user) {
        Session session = sessionFactory.openSession();
        try  {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    public void update(User user) {
        Session session = sessionFactory.openSession();
        try  {
            session.beginTransaction();
            session.createQuery(
                    "update User u set u.password = :password where u.id = :id")
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void delete(int id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery("delete from User u where u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public List<User> findAllOrderById() {
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User u order by u.id", User.class);
            users = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return users;
    }

    public Optional<User> findById(int id) {
        Optional<User> user = Optional.empty();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User u where u.id = :id", User.class);
            query.setParameter("id", id);
            user = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    public List<User> findByLikeLogin(String key) {
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User u where u.login like :likeKey ", User.class);
            query.setParameter("likeKey", "%" + key + "%");
            users = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return users;
    }

    public Optional<User> findByLogin(String login) {
        Optional<User> rsl = Optional.empty();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User u where u.login = :login", User.class);
            query.setParameter("login", login);
            rsl = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }
}

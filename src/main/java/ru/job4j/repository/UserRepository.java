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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        return user;
    }

    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery(
                    "update User u set u.password = :password where u.id = :id")
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User u where u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    public List<User> findAllOrderById() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User u order by u.id", User.class);
            users = query.list();
            session.getTransaction().commit();
        }
        return users;
    }

    public Optional<User> findById(int id) {
        Optional<User> user = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User u where u.id = :id", User.class);
            query.setParameter("id", id);
            user = Optional.ofNullable(query.uniqueResult());
            session.getTransaction().commit();
        }
        return user;
    }

    public List<User> findByLikeLogin(String key) {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User u where u.login like :likeKey ", User.class);
            query.setParameter("likeKey", "%" + key + "%");
            users = query.list();
            session.getTransaction().commit();
        }
        return users;
    }

    public Optional<User> findByLogin(String login) {
        Optional<User> rsl = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User u where u.login = :login", User.class);
            query.setParameter("login", login);
            rsl = Optional.ofNullable(query.uniqueResult());
            session.getTransaction().commit();
        }
        return rsl;
    }
}

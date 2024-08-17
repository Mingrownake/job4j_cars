package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sessionFactory;

    public User create(User user) {
        return user;
    }

    public void update(User user) {

    }

    public void delete(int id) {

    }

    public List<User> findAllOrderById() {
        return List.of();
    }

    public Optional<User> findById(int id) {
        return Optional.empty();
    }

    public List<User> findByLikeLogin(String key) {
        return List.of();
    }

    public Optional<User> findByLogin(String login) {
        return Optional.empty();
    }
}

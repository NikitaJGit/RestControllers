package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    User getUser(long id);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
    User loadUserByUsername(String s);
    User getByLogin(String login);

}

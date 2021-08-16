package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;

    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u",  User.class).getResultList();
    }

    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        Query query = entityManager.createQuery("select u from User u where u.username =:login");
        query.setParameter("login", s);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        entityManager.remove(entityManager.find(User.class,id));
    }

    public User getByLogin(String login) {
        return entityManager.find(User.class, login);
    }

}

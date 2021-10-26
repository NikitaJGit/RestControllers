package web.service;

import org.springframework.stereotype.Service;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private UserDao userDao;
    private RoleDao roleDao;
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        Set<Role> role = new HashSet<Role>();
        role.add(roleDao.getRoleUser());
        user.setRoles(role);
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }
    @Override
    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    @Override
    public User loadUserByUsername(String s) {
        return userDao.loadUserByUsername(s);
    }

}

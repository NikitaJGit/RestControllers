package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao{

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Role getRoleUser() {
        return entityManager.find(Role.class,1L);
    }

    @Override
    public Role getRoleAdmin() {
        return entityManager.find(Role.class, 2L);
    }

    @Override
    public Set<Role> getAllRoles() {
        Set<Role> roleList = new HashSet<>();
        roleList.add(getRoleUser());
        roleList.add(getRoleAdmin());
        return roleList;
    }
}

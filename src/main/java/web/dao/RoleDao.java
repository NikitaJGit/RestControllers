package web.dao;

import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Role getRoleUser();
    Role getRoleAdmin();
    Set<Role> getAllRoles();
}

package web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;
import web.model.WebModelUser;
import web.service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {

    private final RoleService roleService;

    public Mapper(RoleService roleService) {
        this.roleService = roleService;
    }


    public User toUser(WebModelUser webModelUser){
        User user = new User();
        user.setId(webModelUser.getId());
        user.setUsername(webModelUser.getLogin());
        user.setPassword(webModelUser.getPassword());
        user.setName(webModelUser.getName());
        user.setSurname(webModelUser.getSurname());
        user.setAge(webModelUser.getAge());
        Set<Role> roles = new HashSet<>();
        for (String role : webModelUser.getRoleList()) {
            if(role.equals(roleService.getRoleAdmin().getRole())) {
                roles.add(roleService.getRoleAdmin());
            }
            if(role.equals(roleService.getRoleUser().getRole())) {
                roles.add(roleService.getRoleUser());
            }
        }
        user.setRoles(roles);
        return user;
    }

    public static List<WebModelUser> toWebModelList(List<User> users) {
        return users.stream().map(Mapper::toWebModel)
                .collect(Collectors.toList());

    }

    public static WebModelUser toWebModel(User user) {
        WebModelUser webModelUser = new WebModelUser();
        webModelUser.setId(user.getId());
        webModelUser.setLogin(user.getUsername());
        webModelUser.setPassword(user.getPassword());
        webModelUser.setName(user.getName());
        webModelUser.setSurname(user.getSurname());
        webModelUser.setAge(user.getAge());
        webModelUser.setRoleList(user.getRoles().stream().map( Role::getRole).map(x -> x.substring(5)).collect(Collectors.toSet()));
        return webModelUser;
    }
}

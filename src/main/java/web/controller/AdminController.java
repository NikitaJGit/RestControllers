package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;


@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/admin";
    }

    @GetMapping(value = "/admin/new")
    public String newUser(@ModelAttribute("user") User user, Model model){
        model.addAttribute("roleList", roleService.getAllRoles() );
        return "/admin/new";
    }

    @PostMapping(value = "/admin/addUser")
    public String addUser(@ModelAttribute("user") User user){
        user.setRoles(user.getRoles());
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roleList", roleService.getAllRoles());
        return "admin/edit";
    }

    @PostMapping("/admin/{id}/update")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id){
        Set<Role> roles = new HashSet<>();
        user.setId(id);
        for (Role role : user.getRoles()) {
            if(role.getRole().equals(roleService.getRoleAdmin().getRole())) {
                roles.add(roleService.getRoleAdmin());
            }
            if(role.getRole().equals(roleService.getRoleUser().getRole())) {
                roles.add(roleService.getRoleUser());
            }
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/delete")
    public String delete(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roleList", roleService.getAllRoles());
        return "admin/delete";
    }

    @PostMapping("/admin/drop")
    public String drop(@ModelAttribute("user") User user){
        User userGet = userService.getUser(user.getId());
        userService.deleteUser(userGet.getId());
        return "redirect:/admin";
    }
}























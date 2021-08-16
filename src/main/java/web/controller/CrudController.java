package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;

@Validated
@Controller
public class CrudController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public CrudController(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String adminHome(Model model){
        model.addAttribute("roleList", roleService.getAllRoles());
        return "admin/admin";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal){
        String s = principal.getName();
        User user =  userService.loadUserByUsername(s);
        model.addAttribute("user", user);
        return "user/userdata";
    }
}
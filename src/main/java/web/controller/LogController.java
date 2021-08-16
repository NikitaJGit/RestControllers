package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class LogController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public LogController (UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "page/login";
    }

    @GetMapping(value = "/registration")
    public String newUser(@ModelAttribute("user") User user, Model model){
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleService.getRoleUser());
        model.addAttribute("roleList", roleList );
        return "page/registration";
    }
    @PostMapping(value = "/registration/addUser")
    public String addUser(@ModelAttribute("user") User user){
        user.setRoles(user.getRoles());
        userService.addUser(user);
        return "redirect:/user";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "page/login";
    }
}

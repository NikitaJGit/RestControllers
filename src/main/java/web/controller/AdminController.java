package web.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.mapper.Mapper;
import web.model.User;
import web.model.WebModelUser;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }
    @PostMapping("/api/admin/new")
    public ResponseEntity<WebModelUser> addUser(@RequestBody @Valid WebModelUser webModelUser) {
        User user = Mapper.toUser(webModelUser);
        userService.addUser(user);
        return ResponseEntity.ok(Mapper.toWebModel(userService.getByLogin(user.getUsername())));
    }

    @GetMapping("/api/admin/{id}")
    public ResponseEntity<WebModelUser> getUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roleList", roleService.getAllRoles());
        return ResponseEntity.ok(Mapper.toWebModel(userService.getUser(id)));
    }

    @PatchMapping("/api/admin/{id}")
    public ResponseEntity<WebModelUser> update(@PathVariable("id") long id, @RequestBody @Valid WebModelUser webModelUser) {
        User user = Mapper.toUser(webModelUser);
        userService.updateUser(user);
        return ResponseEntity.ok(webModelUser);
    }

    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/api/users")
    public ResponseEntity<List<WebModelUser>> allUsers(){
        return ResponseEntity.ok(Mapper.toWebModelList(userService.getAllUsers()));
    }
    @GetMapping(value = "/api/users/me")
    public ResponseEntity<WebModelUser> me(Principal principal){
        return ResponseEntity.ok(Mapper.toWebModel(userService.loadUserByUsername(principal.getName())));
    }
}























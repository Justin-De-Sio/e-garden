package com.e_garden.api.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profil/{id}")
    public ResponseEntity<User> getUserProfil(@PathVariable Long id) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(userService.getUserByEmail(user.getUsername()).getId(), id))
            return ResponseEntity.notFound().build();
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    @Secured({"ADMINISTRATEUR"})
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @PostMapping
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // TODO
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            userService.deleteUSer(id);
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO user) {
        return userService.verify(new User(user.getEmail(), user.getPassword()));
    }

    @GetMapping("/roles")
    @Secured({"ADMINISTRATEUR"})
    public List<Roles> getRoles() {
        return Arrays.asList(Roles.values());
    }
}

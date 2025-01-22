package com.e_garden.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profil")
    public ResponseEntity<User> getUserProfil() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userE = userService.getUserByEmail(user.getUsername());
        if (userE != null) {
            return ResponseEntity.ok(userE);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/profil/{id}")
    public ResponseEntity<User> updateUserProfil(@PathVariable Long id, @RequestBody User newUser) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByEmail(user.getUsername());
        if (!Objects.equals(currentUser.getId(), id))
            return ResponseEntity.notFound().build();
        newUser.setRole(currentUser.getRole());
        return ResponseEntity.ok(userService.updateUser(currentUser, newUser));
    }

    @PostMapping("/changePassword/{id}")
    public ResponseEntity<User> changeUserPassword(@PathVariable Long id,
                                                   @RequestBody ObjectChangePassword objectChangePassword) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        if (!Objects.equals(userByEmail.getId(), id))
            return ResponseEntity.notFound().build();
        if (!Objects.equals(objectChangePassword.getNewPassword(), objectChangePassword.getConfirmNewPassword())) {
            return ResponseEntity.status(406).build(); // Not Acceptable
        }
        return userService.updatePassword(userByEmail, objectChangePassword.getCurrentPassword(), objectChangePassword.getNewPassword()) ?
                ResponseEntity.ok(userByEmail) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/profil/{id}")
    public ResponseEntity<Void> deleteUserProfil(@PathVariable Long id) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(userService.getUserByEmail(user.getUsername()).getId(), id))
            return ResponseEntity.notFound().build();
        return deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO user) {
        Object verify = userService.verify(new User(user.getEmail(), user.getPassword()));
        if (verify.equals(false))
            return ResponseEntity.status(403).body("Identifiant ou mot de passe incorrecte");
        return ResponseEntity.ok(verify.toString());
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
        user.setPassword(System.getenv("DEFAULT_PASSWORD"));
        return userService.createUser(user);
    }

    @PostMapping("/resetPassword/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<User> resetPassword(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user ->
                        ResponseEntity.ok(userService.resetPassword(user))).orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userById = userService.getUserById(id);
        if (userById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User existingUser = userById.get();
        return ResponseEntity.ok(userService.updateUser(existingUser, user));
    }

    @DeleteMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            userService.archiveUSer(id);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/block/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<Void> blockUnblockUSer(@PathVariable Long id) {
        if (userService.getUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            userService.blockUnBlock(id);
            return ResponseEntity.ok().build();
        }

    }

    @GetMapping("/roles")
    @Secured({"ADMINISTRATEUR"})
    public List<Roles> getRoles() {
        return Arrays.asList(Roles.values());
    }
}

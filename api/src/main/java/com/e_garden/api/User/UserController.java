package com.e_garden.api.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody  User userDetails) {
        // TODO
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            userService.deleteUSer(id);
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user)  {
        String token = userService.verify(user);// Cette méthode retourne directement le token ou null

// Si un token est trouvé, renvoyer une réponse 200 OK avec le token
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.notFound().build();// Si aucun token n'est trouvé, renvoyer une réponse 404 Not Found
        }
    }

    @PostMapping("/firstLogin")
    public String firstLogin() {
        return null;
        // tODO
    }
}

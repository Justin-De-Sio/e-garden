package com.e_garden.api.user;

import com.e_garden.api.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Le type User controller.
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    /**
     * Instancie un nouveau User controller.
     *
     * @param userService le user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets user profil.
     *
     * @return le user profil
     */
    @GetMapping("/profil")
    public ResponseEntity<User> getUserProfil() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.getUserByEmail(user.getUsername()));
    }

    /**
     * Update user profil response entity.
     *
     * @param id      l'identifiant
     * @param newUser le new user
     * @return la response entity
     */
    @PutMapping("/profil/{id}")
    public ResponseEntity<User> updateUserProfil(@PathVariable Long id, @RequestBody User newUser) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByEmail(user.getUsername());
        if (!Objects.equals(currentUser.getId(), id))
            throw new ObjectNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        newUser.setRole(currentUser.getRole());
        return ResponseEntity.ok(userService.updateUser(currentUser, newUser));
    }

    /**
     * Change user password response entity.
     *
     * @param id                   l'identifiant
     * @param objectChangePassword object change password
     * @return le response entity
     */
    @PostMapping("/changePassword/{id}")
    public ResponseEntity<User> changeUserPassword(@PathVariable Long id,
                                                   @RequestBody ObjectChangePassword objectChangePassword) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        if (!Objects.equals(userByEmail.getId(), id))
            throw new ObjectNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        if (!Objects.equals(objectChangePassword.getNewPassword(), objectChangePassword.getConfirmNewPassword())) {
            return ResponseEntity.status(406).build(); // Not Acceptable
        }
        return userService.updatePassword(userByEmail, objectChangePassword.getCurrentPassword(), objectChangePassword.getNewPassword()) ?
                ResponseEntity.ok(userByEmail) : ResponseEntity.badRequest().build();
    }

    /**
     * Delete user profil response entity.
     *
     * @param id le id
     * @return le response entity
     */
    @DeleteMapping("/profil/{id}")
    public ResponseEntity<Void> deleteUserProfil(@PathVariable Long id) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(userService.getUserByEmail(user.getUsername()).getId(), id))
            throw new ObjectNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        return deleteUser(id);
    }

    /**
     * Login response entity.
     *
     * @param user l'user
     * @return le response entity
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO user) {
        Object verify = userService.verify(new User(user.getEmail().toLowerCase(Locale.ROOT), user.getPassword()));
        if (verify.equals(false))
            return ResponseEntity.status(403).body("Identifiant ou mot de passe incorrecte");
        return ResponseEntity.ok(verify.toString());
    }

    /**
     * Gets user by an ID.
     *
     * @param id the ID
     * @return le user by ID
     */
    @GetMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Gets all users.
     *
     * @return le all user
     */
    @GetMapping("/all")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public List<User> getAllUser() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByEmail(user.getUsername());
        Roles roles = Roles.valueOf(currentUser.getRole());
        if (roles == Roles.ADMINISTRATEUR)
            return userService.getAllUsers();
        else
            return userService.getAllResponsableUsers();
    }

    /**
     * Create user.
     *
     * @param user l'user
     * @return l'user
     */
    @PostMapping
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public User createUser(@RequestBody User user) {
        user.setPassword(System.getenv("DEFAULT_PASSWORD"));
        user.setEmail(user.getEmail().toLowerCase(Locale.ROOT));
        return userService.createUser(user);
    }

    /**
     * Reset password response entity.
     *
     * @param id le id
     * @return le response entity
     */
    @PostMapping("/resetPassword/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<User> resetPassword(@PathVariable Long id) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByEmail(user.getUsername());
        if (Objects.equals(currentUser.getId(), id))
            return ResponseEntity.status(406).build();
        userService.resetPassword(userService.getUserById(id));
        return ResponseEntity.noContent().build();
    }

    /**
     * Update user response entity.
     *
     * @param id   l'ID
     * @param user l'user
     * @return le response entity
     */
    @PutMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByEmail(userPrincipal.getUsername());
        if (Objects.equals(currentUser.getId(), id))
            return ResponseEntity.status(406).build();
        return ResponseEntity.ok(userService.updateUser(userService.getUserById(id), user));
    }

    /**
     * Delete user response entity.
     *
     * @param id le id
     * @return le response entity
     */
    @DeleteMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByEmail(user.getUsername());
        if (Objects.equals(currentUser.getId(), id))
            return ResponseEntity.status(406).build();
        userService.archiveUSer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Block unblock user response entity.
     *
     * @param id le id
     * @return le response entity
     */
    @GetMapping("/block/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public ResponseEntity<Void> blockUnblockUser(@PathVariable Long id) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByEmail(user.getUsername());
        if (Objects.equals(currentUser.getId(), id))
            return ResponseEntity.status(406).build();
        userService.blockUnBlock(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Gets roles.
     *
     * @return le roles
     */
    @GetMapping("/roles")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE"})
    public List<Roles> getRoles() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByEmail(user.getUsername());
        Roles roles = Roles.valueOf(currentUser.getRole());

        if (roles == Roles.RESPONSABLE)
            return Arrays.asList(Roles.UTILISATEUR, Roles.RESPONSABLE);
        if (roles == Roles.ADMINISTRATEUR)
            return Arrays.asList(Roles.values());
        else
            return List.of(Roles.UTILISATEUR);
    }
}

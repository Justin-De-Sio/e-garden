package com.e_garden.api.user;

import com.e_garden.api.exception.ObjectNotFoundException;
import com.e_garden.api.log.Levels;
import com.e_garden.api.log.LogService;
import com.e_garden.api.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final LogService log;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JWTService jwtService, LogService log) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.log = log;
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByEnable(true);
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findByIdAndEnable(id, true);
        if (user.isEmpty())
            throw new ObjectNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        return user.get();
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }
        user = saveUser(encodePassword(user));
        log.createLog(String.valueOf(Levels.USER), "Utilisateur ajouté", user.toString());
        return user;
    }

    /**
     * Méthode privée permettant d'encoder le mot de passe de l'utilisateur.
     *
     * @param user avec un nouveau mot de passe
     * @return user avec un mot de passe encodéan
     */
    private User encodePassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }

    public List<User> saveUsers(Iterable<User> users) {
        List<User> usersSaved = new ArrayList<>();
        for (User u : users) {
            usersSaved.add(createUser(u));
        }
        return usersSaved;
    }

    public User resetPassword(User user) {
        user.setPassword(System.getenv("DEFAULT_PASSWORD"));
        user = saveUser(encodePassword(user));
        log.createLog(String.valueOf(Levels.USER), "Utilisateur resetPassword", user.toString());
        return user;
    }

    public User saveUser(User user) {
        user.setSurname(user.getSurname().toUpperCase(Locale.ROOT));
        user = userRepository.save(user);
        log.createLog(String.valueOf(Levels.USER), "Utilisateur mis à jour", user.toString());
        return user;
    }

    public void deleteUSer(Long id) {
        userRepository.deleteById(id);
        log.createLog(String.valueOf(Levels.USER), "Utilisateur supprimé", "user id : " + id);
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmailAndEnable(email, true);
        if (user.isEmpty()) {
            log.createLog(String.valueOf(Levels.USER), "Utilisateur échec d'authentification", user.toString());
            throw new ObjectNotFoundException("Utilisateur non trouvé avec l'email : " + email);
        } else
            return user.get();
    }

    public Object verify(User user) {
        User userInfo = getUserByEmail(user.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            if (authentication.isAuthenticated() && userInfo.isEnable() && userInfo.isLocked()) {
                log.createLog(String.valueOf(Levels.USER), "Utilisateur authentifié", user.getEmail());
                return jwtService.generateToken(user.getEmail(), userInfo.getRole());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            log.createLog(String.valueOf(Levels.USER), "Utilisateur échec d'authentification", user.toString());
            return false;
        }
        log.createLog(String.valueOf(Levels.USER), "Utilisateur échec d'authentification", user.toString());
        return false;
    }

    public boolean updatePassword(User user, String currentPassword, String newPassword) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), currentPassword)
        );
        if (authentication.isAuthenticated()) {
            user.setPassword(newPassword);
            user = saveUser(encodePassword(user));
            log.createLog(String.valueOf(Levels.USER), "Utilisateur a change de mot de passe", user.getEmail());
            return true;
        } else {
            log.createLog(String.valueOf(Levels.USER), "Utilisateur échec de changement de mot de passe", user.toString());
            return false;
        }
    }


    public User updateUser(User currentUser, User newUser) {
        currentUser.setName(newUser.getName());
        currentUser.setSurname(newUser.getSurname());
        currentUser.setGroupNumber(newUser.getGroupNumber());
        currentUser.setClassName(newUser.getClassName());
        currentUser.setRole(newUser.getRole());

        return saveUser(currentUser);
    }

    public void archiveUSer(Long id) {
        User u = getUserById(id);
        u.setEnable(false);
        saveUser(anonymisationUser(u));
        log.createLog(String.valueOf(Levels.USER), "Utilisateur archivé", "user id : " + id);
    }

    /**
     * Efface les données personnelles d'un utilisateur
     *
     * @param user avec des données
     * @return user anonymisé
     */
    public User anonymisationUser(User user) {
        user.setName("name");
        user.setSurname("surname");
        user.setClassName("");
        user.setGroupNumber(0);
        user.setEmail(new Random().toString());
        user.setPassword(new Random().toString());
        return encodePassword(user);
    }

    public void blockUnBlock(Long id) {
        User u = getUserById(id);
        boolean lockedState = u.isLocked();
        u.setLocked(u.isLocked());
        saveUser(u);
        log.createLog(String.valueOf(Levels.USER), "Utilisateur " + (lockedState ? "bloqué" : "débloquer"), "user id : " + id);
    }
}

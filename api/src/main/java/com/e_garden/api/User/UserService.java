package com.e_garden.api.User;

import com.e_garden.api.Log.Levels;
import com.e_garden.api.Log.LogService;
import com.e_garden.api.Security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }
        user = userRepository.save(encodePassword(user));
        log.createLog(String.valueOf(Levels.USER), "Utilisateur ajouté", user.toString());
        return user;
    }

    private User encodePassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }

    public List<User> saveUsers(Iterable<User> users) {
        List<User> usersSaved = new ArrayList<>();
        for (User u: users) {
            usersSaved.add(saveUser(u));
        }
        return usersSaved;
    }

    public void deleteUSer(Long id) {
        userRepository.deleteById(id);
        log.createLog(String.valueOf(Levels.USER), "Utilisateur supprimé", "user id : " + id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String verify(User user) {
        User userInfo = getUserByEmail(user.getEmail());
        if (userInfo == null) {
            return "false";
        }
        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
                );
        if (authentication.isAuthenticated()){
            log.createLog(String.valueOf(Levels.USER), "Utilisateur authentifié", user.getEmail());
            return jwtService.generateToken(user.getEmail(), userInfo.getRole());
        } else {
            log.createLog(String.valueOf(Levels.USER), "Utilisateur échec d'authentification", user.toString());
            return "false";
        }
    }
    public boolean updatePassword(User user, String currentPassword, String newPassword) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), currentPassword)
        );
        if (authentication.isAuthenticated()){
            user.setPassword(newPassword);
            user = userRepository.save(encodePassword(user));
            log.createLog(String.valueOf(Levels.USER), "Utilisateur a change de mot de passe", user.getEmail());
            return true;
        } else {
            log.createLog(String.valueOf(Levels.USER), "Utilisateur échec de changement de mot de passe", user.toString());
            return false;
        }
    }


}

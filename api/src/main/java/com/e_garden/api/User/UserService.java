package com.e_garden.api.User;

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
public class UserService  {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;

    private JWTService jwtService;
    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }
        return userRepository.save(user);
    }

    public List<User> saveUsers(Iterable<User> users) {
        List<User> usersSaved = new ArrayList<User>();
        for (User u: users) {
            usersSaved.add(saveUser(u));
        }
        return usersSaved;
    }

    public void deleteUSer(Long id) {
        userRepository.deleteById(id);
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
            return jwtService.generateToken(user.getEmail(), userInfo.getRole());
        } else {
            return "false";
        }
    }
}

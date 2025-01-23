package com.e_garden.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Méthode permettant de charger un object utilisateur principal.
     * UserPrincipal est renseigné à partir d'un utilisateur trouvé en DB avec un email.
     *
     * @param username email de l'utilisateur
     * @return l'UserPrincipal correspondant à l'email
     * @throws UsernameNotFoundException Exception en cas d'absence de l'utilisateur
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAndEnable(username, true);
        if (user.isEmpty()) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user.get());
    }
}

package com.e_garden.api.user;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    /**
     * @return Collections
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    /**
     * @return le mot de passe
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Méthode qui retourne username, dans ce projet username unique correspond à l'email.
     *
     * @return Email de l'utilisateur
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Return True user non bloqué.
     * Return False user bloqué.
     *
     * @return un boolean indiquant si le compte est bloqué ou non.
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.isLocked();
    }

    /**
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * True l'utilisateur est activé, False l'utilisateur est archivé.
     * @return un boolean si l'utilisateur est activé ou archivé
     */
    @Override
    public boolean isEnabled() {
        return user.isEnable();
    }
}

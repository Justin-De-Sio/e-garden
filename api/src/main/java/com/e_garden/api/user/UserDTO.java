package com.e_garden.api.user;

/**
 * Le type User dto.
 */
public class UserDTO {
    private String email;
    private String password;

    /**
     * Gets password.
     *
     * @return le password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password le password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets email.
     *
     * @return l'email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email l'email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

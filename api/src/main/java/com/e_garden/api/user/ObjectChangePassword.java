package com.e_garden.api.user;

/**
 * Le type Object change password.
 */
public class ObjectChangePassword {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    /**
     * Sets a current password.
     *
     * @param currentPassword le current password
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * Sets a new password.
     *
     * @param newPassword le new password
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Sets confirm a new password.
     *
     * @param confirmNewPassword le confirm new password
     */
    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    /**
     * Gets a current password.
     *
     * @return le current password
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Gets a new password.
     *
     * @return le new password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Gets confirm a new password.
     *
     * @return le confirm new password
     */
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }
}

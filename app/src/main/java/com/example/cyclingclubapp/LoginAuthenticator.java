package com.example.cyclingclubapp;

public class LoginAuthenticator {

    private boolean passwordValid;
    private boolean usernameValid;

    /**
     * Constructs new LoginAuthenticator
     * Sets all variables to false by default
     */
    public LoginAuthenticator() {
        this.passwordValid = false;
        this.usernameValid = false;
    }

    /**
     * Sets passwordValid variable to true
     */
    protected void setPasswordValidTrue() {
        this.passwordValid = true;
    }

    /**
     * Stes usernameValid variable to true
     */
    protected void setUsernameValidTrue() {
        this.usernameValid = true;
    }

    /**
     * Checks if both username and password are valid
     * @return true if password is validated and username is validated, false otherwise
     */
    protected boolean allValid() {
        return passwordValid && usernameValid;
    }


}

package com.example.project31;

public class Authenticator {
//use static authenticator
    private boolean usernameValid;
    private boolean passwordValid;
    private boolean emailValid;

    /**
     * Constructs new Authenticator
     * Sets all boolean fields to false
     */
    public Authenticator() {
        usernameValid = false;
        passwordValid = false;
        emailValid = false;
    }

    /**
     * Checks that username fits minimum requirements
     * Must be atleast 5 characters long
     * Must contain a special character
     *
     * @param password password to be checked
     */
    protected void validatePassword(String password) {
        boolean flag = true;
        if(password.length() < 5) {
            flag = false;
        }
        if(!password.contains("!") && !password.contains("@") && !password.contains("#") && !password.contains("$") && !password.contains("%") &&
                !password.contains("^") && !password.contains("&") && !password.contains("*") && !password.contains("'") && !password.contains("-") &&
                !password.contains("+") && !password.contains("-") && !password.contains("?") && !password.contains("~") && !password.contains("<") &&
                !password.contains(">")) {
            flag = false;
        }
        this.passwordValid = flag;
    }

    protected static boolean validatePasswordUnitTest(String password) {
        boolean flag = true;
        if(password.length() < 5) {
            flag = false;
        }
        if(!password.contains("!") && !password.contains("@") && !password.contains("#") && !password.contains("$") && !password.contains("%") &&
                !password.contains("^") && !password.contains("&") && !password.contains("*") && !password.contains("'") && !password.contains("-") &&
                !password.contains("+") && !password.contains("-") && !password.contains("?") && !password.contains("~") && !password.contains("<") &&
                !password.contains(">")) {
            flag = false;
        }
        return flag;
    }

    /**
     * Sets usernameValid variable to true
     */
    protected void setUsernameValidTrue() {this.usernameValid = true;
    }

    /**
     * Sets emailValid variable to true
     */
    protected void setEmailValidTrue() {this.emailValid = true;
    }
    /**
     * Sets passwordValid variable to true
     */
    protected void setPasswordValid() {this.passwordValid = true;}

    /**
     * Checks if all variables are true
     * @return true if password, email, and username are valid, false otherwise
     */
    protected boolean allValid() {
        return usernameValid && passwordValid && emailValid;
    }

    /**
     *
     * @return returns if username is valid as boolean
     */
    protected boolean getUsernameValid() {return usernameValid;}

    /**
     *
     * @return returns if password is valid as boolean
     */
    protected boolean getPasswordValid() {return passwordValid;}

    /**
     *
     * @return returns if email is valid as boolean
     */
    protected  boolean getEmailValid() {return emailValid;}

}

package com.example.project31;

import java.io.Serializable;

public class User implements Serializable {

    public UserType role;
    public String roleString;
    public String username;
    public String email;
    private String password;

    public User(){

    }

    public User(String role, String username){
        this.roleString = role;
        this.username = username;
    }
    /**
     * Constructs new User instance
     *
     * @param role new users role
     * @param username new users username
     * @param email new users email
     * @param password new users password
     */
    public User(UserType role, String username, String email, String password) {
        this.role = role;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    /**
     * Changes the users role
     * @param role Role to be changed to
     */
    protected void setRole(UserType role) {
        this.role = role;
    }

    /**
     * Changes users username
     * @param username Username to be changed to
     */
    protected void setUsername(String username) {
        this.username = username;
    }

    /**
     * Changes user password
     * @param password Password to be changed to
     */
    protected void setPassword(String password) {
        this.password = password;
    }

    /**
     * Changes users email
     * @param email Email to be changed to
     */
    protected void setEmail(String email) {
        this.email = email;
    }

    /**
     * Fetches users username
     * @return username variable
     */
    public String getUsername() {
        return this.username;
    }


    /**
     * Fetches users role
     * @return role variable
     */
    public UserType getRole() {
        return this.role;
    }

    public String getRoleString(){
        return this.roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }
}

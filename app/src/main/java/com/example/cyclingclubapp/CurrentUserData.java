package com.example.cyclingclubapp;

public class CurrentUserData {
    private static User currentUser;

    /**
     * Changes the currently logged in user
     * @param user User to be logged in
     */
    protected static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Logs out current user
     * Sets currentUser variable to null
     */
    protected static void logoutCurrentUser() {
        currentUser = null;
    }

    /**
     * Checks if any user is logged in
     * @return false if currentUser is null, true otherwise
     */
    protected static boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Gets User instance for currently logged in user
     * @return currentUser variable
     */
    protected static User getCurrentUser() {
        return currentUser;
    }
}

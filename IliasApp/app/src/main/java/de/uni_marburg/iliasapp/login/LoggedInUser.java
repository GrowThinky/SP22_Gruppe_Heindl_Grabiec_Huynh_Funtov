package de.uni_marburg.iliasapp.login;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String sessionId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.sessionId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return sessionId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
package de.uni_marburg.iliasapp.login;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String sessionId;
    private String displayName;

    public LoggedInUser(String sessionId, String displayName) {
        this.sessionId = sessionId;
        this.displayName = displayName;

    }

    public String getSessionId() {
        return sessionId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
package de.uni_marburg.iliasapp.login;


import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public LoginResult<LoggedInUser> login(String username, String password)  {

            try {
                // TODO: handle loggedInUser authentication
                // this is just for testing purposes
                if (!username.equals("hello")) {
                    throw new IOException("user not in database");
                }

                    LoggedInUser fakeUser =
                            new LoggedInUser(
                                    java.util.UUID.randomUUID().toString(),
                                    "user_name");
                    return new LoginResult.Success<>(fakeUser);


            } catch (Exception e) {
                return new LoginResult.Error(new IOException("Error logging in", e));
            }
        }


    public void logout() {
        // TODO: revoke authentication
    }
}
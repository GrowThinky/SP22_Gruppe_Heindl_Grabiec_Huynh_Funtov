package de.uni_marburg.iliasapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import de.uni_marburg.iliasapp.HomeScreen;
import de.uni_marburg.iliasapp.R;
import de.uni_marburg.iliasapp.databinding.ActivityLoginBinding;

/**
 * Displays the Login-Screen, passes username and password on to LoginRepository
 * and updates UI with userdata, if login is successful.
 */
public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoggedInUser user;
    private LoginRepository loginRepository;
    ProgressBar loadingProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginRepository = new LoginRepository(new LoginDataSource());

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        loadingProgressBar = binding.loading;



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                loadingProgressBar.setVisibility(View.VISIBLE);
                login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        LoginResult result = loginRepository.login(username, password);

        if (result instanceof LoginResult.Success) {

            LoggedInUser data = ((LoginResult.Success<LoggedInUser>) result).getData();

            this.user = data;
            System.out.println(user.getDisplayName());
            updateUiWithUser(user);
            finish();
        }  else {
            loadingProgressBar.setVisibility(View.GONE);
            showLoginFailed(((LoginResult.Error) result).getError().getMessage());
        }
    }

    private void updateUiWithUser(LoggedInUser user) {
        String welcome = getString(R.string.welcome) + " " + user.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent moveToHome = new Intent(this, HomeScreen.class);

        moveToHome.putExtra("sid", user.getSessionId());
        startActivity(moveToHome);
    }

    private void showLoginFailed(String message) {
        Toast.makeText(getApplicationContext(),message , Toast.LENGTH_SHORT).show();
    }

}
package it.uniba.dib.sms222315;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private EditText password_view;
    private EditText email_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
/**
        // Take instance of Action Bar
        // using getSupportActionBar and
        // if it is not Null
        // then call hide function
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
*/
    }//End onCreate
    // [START on_start_check_user]






    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }

    }//END onStart
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        //TODO : prova login , login è null ? fai creaione vito e massi
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    //rimandare a stringa questo toast non si vede, è in basso  TODO
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }// [END create_user_with_email]

    private void signInAccount(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }//END signin Account


    private void reload() {
        Log.d(TAG, "RELOAD ");
        Intent intent_ok_log = new Intent(this, UserActivity.class);
        startActivity(intent_ok_log);
    }


    private void updateUI(FirebaseUser user) {

    }



    public void button_create_new_account(View view) {
        Log.d(TAG, "BUTTON CREATE OK ");
        password_view = findViewById(R.id.password_text);
        email_view = findViewById(R.id.email_text);
        String email = email_view.getText().toString();
        String password = email_view.getText().toString();
        createAccount(email , password);
        reload();
    }//END chiamata bottone

    public void button_SignIn_account(View view) {
        Log.d(TAG, "BUTTON SIGNIN OK ");
        password_view = findViewById(R.id.password_text);
        email_view = findViewById(R.id.email_text);
        String email = email_view.getText().toString();
        String password = email_view.getText().toString();
        signInAccount(email , password);
        reload();
    }//End bottone sign in



    public void button_logout(View view) {
        FirebaseAuth.getInstance().signOut();
        //Richiama la main activity
        Intent okLogut = new Intent(this, MainActivity.class);
        startActivity(okLogut);
    }//END loggout button


}//END Activity
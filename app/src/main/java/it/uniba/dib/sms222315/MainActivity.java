package it.uniba.dib.sms222315;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG_MainActivity";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }//END OnCreate

    @Override
    protected void onStart() {
        super.onStart();

        //CONTROLLO AUTENTICAZIONE UTENTE
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "User is Logged");
        } else {
            // No user is signed in
            Log.d(TAG, "User isn't Logged");

        }
    }//END onStart

        public void button_lunchLogin (View view){
            Intent login_prova = new Intent(this, EmailPasswordActivity.class);
            startActivity(login_prova);
        }//END LunchLogin

}//END MainActivity
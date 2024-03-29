package it.uniba.dib.sms222315;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.uniba.dib.sms222315.Autentication.LoginOrRegisterActivity;
import it.uniba.dib.sms222315.Friends.Fragment_MyFriends_Home;
import it.uniba.dib.sms222315.TestListView.Activity_MemberList;
import it.uniba.dib.sms222315.UserProfile.ProfileUserActivity;
import it.uniba.dib.sms222315.oldFile.User_information_Fragment;

public class MainActivity extends AppCompatActivity {

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    private static final String TAG = "TAG_MainActivity";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar set as app bar
        Toolbar myToolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Home");
        //tasto back
        //getSupportActionBar().setDisplayHomeAsUpEnabled();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        firstFragment();



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_profile:
                Log.d(TAG, "collegamento profilo utente");

                //CONTROLLO AUTENTICAZIONE UTENTE
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "User is Logged");
                    Intent intent_ok_log = new Intent(this, ProfileUserActivity.class);
                    startActivity(intent_ok_log);
                    return true;

                } else {
                    // No user is signed in
                    Log.d(TAG, "User isn't Logged");
                    Intent login_prova = new Intent(this, LoginOrRegisterActivity.class);
                    startActivity(login_prova);
                    return true;

                }


            case R.id.action_find:
                Intent intent_search = new Intent(this, Activity_MemberList.class);
                startActivity(intent_search);
                Log.d(TAG, "search activity");
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }


    public void hard_logout(View view) {

        Log.d(TAG, "BUTTON LOGOUT OK ");
        FirebaseAuth.getInstance().signOut();
    }

    private void firstFragment() {
        Fragment_HomePost my_fragment = new Fragment_HomePost();
        //my_fragment.setMy_callbackFragment(this);
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        my_frag_trans.add(R.id.Frame_Act_Main , my_fragment);
        my_frag_trans.commit();
    }

}//END MainActivity
package it.uniba.dib.sms222315;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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

import it.uniba.dib.sms222315.Autentication.Fragment_Login;
import it.uniba.dib.sms222315.Autentication.LoginOrRegisterActivity;
import it.uniba.dib.sms222315.TestListView.Activity_MemberList;
import it.uniba.dib.sms222315.UserExpense.Fragment_MyExpense_Home;
import it.uniba.dib.sms222315.UserPets.Fragment_MyPets_Home;
import it.uniba.dib.sms222315.UserProfile.ProfileUserActivity;
import it.uniba.dib.sms222315.oldFile.User_information_Fragment;

public class MainActivity extends AppCompatActivity {

    //Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;
    FragmentManager my_frag_manager_menu;
    FragmentTransaction my_frag_trans_menu;

    private static final String TAG = "TAG_MainActivity";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addFragment();
        addFragmentHome();

        //toolbar set as app bar
    //   Toolbar myToolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(myToolbar);



        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]




    }//END OnCreate

    private void addFragmentHome() {

        Fragment_MyPets_Home my_fragment = new Fragment_MyPets_Home();


        //my_fragment.setMy_callbackFragment(this);

        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();

        my_frag_trans.add(R.id.FramContHome , my_fragment);
        my_frag_trans.commit();
    }

    private void addFragment() {
        MenuBar_FullUser my_fragment_menu = new MenuBar_FullUser();
        my_frag_manager_menu = getSupportFragmentManager();
        my_frag_trans_menu = my_frag_manager_menu.beginTransaction();
        my_frag_trans_menu.add(R.id.FragContMenuBar , my_fragment_menu);
        my_frag_trans_menu.commit();
    }

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

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
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

    }        */


    public void hard_logout(View view) {

        Log.d(TAG, "BUTTON LOGOUT OK ");
        FirebaseAuth.getInstance().signOut();
    }
}//END MainActivity
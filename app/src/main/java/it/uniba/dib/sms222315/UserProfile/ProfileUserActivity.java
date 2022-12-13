package it.uniba.dib.sms222315.UserProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.uniba.dib.sms222315.MainActivity;
import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserProfile.Fragment_UserProfile;
import it.uniba.dib.sms222315.UserProfile.Fragment_menu_profile;
import it.uniba.dib.sms222315.UserProfile.Interf_UserProfile;

public class ProfileUserActivity extends AppCompatActivity implements Interf_UserProfile {

    //inizialiamo i fragment
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    private FirebaseAuth mAuth;

    private static final String TAG = "TAG_Act_ProfUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Log.d(TAG , "onCreate ProfileUser");

        addFragment();

    }//END onCreate

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "on Start activity log or register ");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }


    }

    private void reload() {

        //forza uscita utente da questa activity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent_ok_log = new Intent(this, MainActivity.class);
            startActivity(intent_ok_log);
            Log.d(TAG, "logout profile ");
        }
    }

    private void addFragment() {

        //primo fragment profile classico
        //con new scegliamo il fragment da istanziare

        Fragment_UserProfile my_fragment = new Fragment_UserProfile();
        my_fragment.setMyCallBackFrag(this);
        //
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();

        my_frag_trans.add(R.id.FragProfileUser , my_fragment);
        my_frag_trans.commit();
    }

    public void replaceFragment(){
        //ora chiama registrati fragment
        my_fragment = new Fragment_menu_profile();
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        //si aggiunge il richiamo allo stack
        //QUESTO COMANDO
        my_frag_trans.addToBackStack(null);
        //add diventa replace
        my_frag_trans.replace(R.id.FragProfileUser , my_fragment);
        my_frag_trans.commit();

    }//END addFrag
//segno blu
    @Override
    public void changeFragment() {
        replaceFragment();
    }
}
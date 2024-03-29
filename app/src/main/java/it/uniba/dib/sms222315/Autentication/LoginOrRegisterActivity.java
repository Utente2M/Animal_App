package it.uniba.dib.sms222315.Autentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.uniba.dib.sms222315.Friends.MyFriends;
import it.uniba.dib.sms222315.UserPets.Pets;
import it.uniba.dib.sms222315.UserProfile.ProfileUserActivity;
import it.uniba.dib.sms222315.R;

public class LoginOrRegisterActivity extends AppCompatActivity implements CallbackFragment {

    //Variable for public profile
    String NamePublic , MailPublic;

    //FIREBASE VAR
    private FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //inizialiamo i fragment
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;



    private static final String TAG = "TAG_Act_LogOrRegis";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        Log.d(TAG, "on create activity log or register ");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.ITALIAN);
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent_ok_log = new Intent(this, ProfileUserActivity.class);
            startActivity(intent_ok_log);
        }else{
            //carichiamo i due frgment
            addFragment();
        }

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



    public void addFragment(){
        //con new scegliamo il fragment da istanziare

        Fragment_Login  my_fragment = new Fragment_Login();
        my_fragment.setMy_callbackFragment(this);
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();

        my_frag_trans.add(R.id.FragAutentic , my_fragment);
        my_frag_trans.commit();

    }//END addFrag

    public void replaceFragment(){
        //qui cambia il new rispetto a prima
        my_fragment = new Fragment_Register();
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        //si aggiunge il richiamo allo stack
        my_frag_trans.addToBackStack(null);
        //add diventa replace
        my_frag_trans.replace(R.id.FragAutentic , my_fragment);
        my_frag_trans.commit();

    }//END addFrag


    @Override
    public void changeFragment() {
        replaceFragment();
    }


    @Override
    public void createUSerWithMailPassword(String email, String password) {
        Log.d(TAG , "createUSerWithMailPassword mail = " + email);
        Log.d(TAG , "createUSerWithMailPassword pass = " + password);

        MailPublic = email;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginOrRegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            reload();
                        }
                    }
                });



    }

    @Override
    public void logAccountWithMailePass(String email, String password) {
        Log.d(TAG , "createUSerWithMailPassword mail = " + email);
        Log.d(TAG , "createUSerWithMailPassword pass = " + password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            reload();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginOrRegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            reload();
                        }
                    }
                });

    }//END login function

    @Override
    public void addInformationToProfile(String name) {

        NamePublic = name;

        //setta display name in autFirabese
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");

                        }
                    }
                });


    }

    @Override
    public void addBasicInfoToUser(String phone, String address,
                                   String dateBorn) {
        //qui vanno creati gli animali nel DB con le info base
        //Questa risposta arriva dal Fragment Add new Pet

        /*

         */
        // Create a new user with a first and last name
        Map<String, Object> new_user = new HashMap<>();
        new_user.put("Phone", phone);
        new_user.put("address", address);
        new_user.put("dateBorn", dateBorn);


        // Add a new document with a document = ID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);


        //PROVA DI CREAZIONE SUBCOLLECTION

        db.collection("User Basic Info").document(userID)
                .set(new_user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        createPublicProfile();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void createPublicProfile() {

        //qui vanno creati gli animali nel DB con le info base
        //Questa risposta arriva dal Fragment Add new Pet

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"Public Profile " + userID);

        MyFriends newPublicProfile = new MyFriends(NamePublic , MailPublic , "" , userID , "");


        //PROVA DI CREAZIONE SUBCOLLECTION

        db.collection("Public User")
                .add(newPublicProfile)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        reload();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        //inserire reload
    }


    private void updateUI() {


        my_fragment = new Fragment_Regis_Basic_info();
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        //si aggiunge il richiamo allo stack
        //my_frag_trans.addToBackStack(null);
        //add diventa replace
        my_frag_trans.replace(R.id.FragAutentic , my_fragment);
        my_frag_trans.commit();
        //reload();

    }


    private void reload() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent_ok_log = new Intent(this, ProfileUserActivity.class);
            startActivity(intent_ok_log);
        }Log.d(TAG, "RELOAD ");

    }//end reload

}
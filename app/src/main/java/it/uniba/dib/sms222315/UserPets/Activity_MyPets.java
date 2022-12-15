package it.uniba.dib.sms222315.UserPets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.dib.sms222315.Autentication.Fragment_Register;
import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.TestListView.Person;
import it.uniba.dib.sms222315.TestListView.PersonListAdapter;
import it.uniba.dib.sms222315.UserProfile.Fragment_UserProfile;
import it.uniba.dib.sms222315.UserProfile.Fragment_menu_profile;

public class Activity_MyPets extends AppCompatActivity implements Interf_UserPets{

    private static final String TAG = "TAG_Act_MyPets";

    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        Log.d(TAG, "onCreate: Started.");



        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG, "DB started.");

        addFragment();

    }//END ON CREATE

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "on Start activity MyPets ");

    }

    private void addFragment() {

        //primo fragment profile classico
        //con new scegliamo il fragment da istanziare

        Fragment_MyPets_Home my_fragment = new Fragment_MyPets_Home();

        //Fragment_UserProfile my_fragment = new Fragment_UserProfile();
        my_fragment.setMy_callbackFragment(this);
        //my_fragment.myCallBackFrag(this);
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();

        my_frag_trans.add(R.id.Frame_Act_MyPets , my_fragment);
        my_frag_trans.commit();
    }


    public void replaceFragment(){
        //qui cambia il new rispetto a prima
        my_fragment = new Fragment_AddNewPet();
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        //si aggiunge il richiamo allo stack
        my_frag_trans.addToBackStack("Pippo");
        //add diventa replace
        my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
        my_frag_trans.commit();

    }//END addFrag


    @Override
    public void turnTohome() {

        // questo metodo non funzione
        my_fragment = new Fragment_AddNewPet();
        my_frag_manager = getSupportFragmentManager();
        my_frag_manager.popBackStack();
    }

    @Override
    public void changeFragment() {
        replaceFragment();
    }

    @Override
    public void createAnimalInDB(String Name, String Specie, String Sex, String Razza) {
        //qui vanno creati gli animali nel DB con le info base
        //Questa risposta arriva dal Fragment Add new Pet

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);





        /*


        // Create a new user with a first and last name
        Map<String, Object> pets_map = new HashMap<>();
        pets_map.put("Name", Name);
        pets_map.put("Specie", Specie);
        pets_map.put("Sex", Sex);
        pets_map.put("Razza", Razza);
         */


        Pets MimmoPetes = new Pets(Name , Specie , Sex , Razza , "" , "" , "",
                Arrays.asList(userID));

        // Add a new document with a document = ID



        //PROVA DI CREAZIONE SUBCOLLECTION

        db.collection("Animal DB")
                .add(MimmoPetes)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });




    }//END create Animal DB



}//END ACTIVITY
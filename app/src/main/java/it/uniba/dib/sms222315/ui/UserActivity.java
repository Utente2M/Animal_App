package it.uniba.dib.sms222315.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;




import java.util.HashMap;
import java.util.Map;

import it.uniba.dib.sms222315.MainActivity;
import it.uniba.dib.sms222315.R;

public class UserActivity extends AppCompatActivity {

//FINESTRA DIALOG EDIT
    Button edit;
    AlertDialog MyDialog_edit;

    TextView ProfileInfo_var;
    ScrollView MyScrollFullProfile_var;

    //DB variable
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_UserActivity";
    private FirebaseAuth mAuth;
    private Button but_logout;
    private FirebaseUser userAuth;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Inside onCreate UserActivity");
        //load name profile

        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();


        userAuth = mAuth.getCurrentUser();

        if (userAuth != null) {
            // User is signed in
            //andiamo in onStart
        } else {
            // No user is signed in
            //altrimenti dovremmo uscire
        }


    } //end onCreate


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Inside onStart");

        userAuth = mAuth.getCurrentUser();



        if (userAuth != null) {
            // User is signed in
            String userID = userAuth.getUid();
            Log.d(TAG,"This is UID " + userID);


            //qua bisognerebbe popolare una classe
            DocumentReference docRef = db.collection("users_basic_information").document(userID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            //Salva nome del profile
                            ProfileInfo_var.setText(document.getString("Name"));
                            //todo altre info da salvare al caricamento




                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d(TAG, "No such document");
                            MyScrollFullProfile_var = findViewById(R.id.ProfileFullPannel);
                            MyScrollFullProfile_var.setVisibility(View.INVISIBLE);
                            buildDialog();
                            MyDialog_edit.show();

                            MyScrollFullProfile_var = findViewById(R.id.ProfileFullPannel);
                            MyScrollFullProfile_var.setVisibility(View.VISIBLE);



                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        } else {
            Log.d(TAG,"user = null");
            // No user is signed in
            //finish();
        }




    }//END onStart


    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.my_dialog , null );

        EditText aboutMe_var = view.findViewById(R.id.dialog_text_edit);

        builder.setView(view);
        builder.setTitle("Insert Your Name here")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "dialog button ok");

                        addText_aboutMe(aboutMe_var.getText().toString());


                    }

                })
                .setNegativeButton(getString(R.string.Back_Dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "dialog button back");
                    }
                });
        MyDialog_edit = builder.create();

    }

    private void addText_aboutMe(String textAboutMe) {

        TextView NameTextView = findViewById(R.id.ProfileName);
        //todo ProfileName id del nome profilo
        NameTextView.setText(textAboutMe);



        String SaveNameDb_String = NameTextView.getText().toString();
        Log.d(TAG, SaveNameDb_String);

        // Create a new user with a first and last name
        Map<String, Object> user_Map = new HashMap<>();
        user_Map.put("Name", SaveNameDb_String);
        user_Map.put("Second", "poi 2");
        user_Map.put("Anno", 118);

        // Add a new document with a document = ID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);


        db.collection("users_basic_information").document(userID)
                .set(user_Map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }




    public void Logout_hard_test(View view) {
        Log.d(TAG, "BUTTON LOGOUT OK ");
        FirebaseAuth.getInstance().signOut();
        //Richiama la main activity
        Intent okLogut = new Intent(this, MainActivity.class);
        startActivity(okLogut);

    }
}//END ACTIVITY
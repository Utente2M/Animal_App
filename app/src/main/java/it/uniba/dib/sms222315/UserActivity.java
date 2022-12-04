package it.uniba.dib.sms222315;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

//FINESTRA DIALOG EDIT
    Button edit;
    AlertDialog MyDialog_edit;

    TextView ProfileInfo_var;
    String userID;

    //DB variable
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth dbAuth;
    private static final String TAG = "TAG_UserActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Inside onCreate");
        setContentView(R.layout.activity_user);

        //load name profile
        ProfileInfo_var = findViewById(R.id.ProfileName);
        //get map from db
        Map<String, Object> user_Map_get = new HashMap<>();
        //TODO da completare sono arrivato qui
        // Create a reference to the cities collection
        CollectionReference NameProfile = db.collection("users_basic_information");

        // Create a query against the collection.
        Query query = NameProfile.whereEqualTo("Name",true );


        //get first field from map
        user_Map_get.get("Name");



        ProfileInfo_var.setText("info da db");

        String checkInfo = ProfileInfo_var.getText().toString();
        Log.d(TAG, checkInfo);

        if (checkInfo == "info da db") {
            buildDialog();
            MyDialog_edit.show();

        }

    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.my_dialog , null );

        EditText aboutMe = view.findViewById(R.id.dialog_text_edit);

        builder.setView(view);
        builder.setTitle("Insert Your Name here")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "dialog button ok");

                        addText_aboutMe(aboutMe.getText().toString());

                        // TODO qua va inserito il salvataggio dei nuovi dati
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


        ProfileInfo_var = findViewById(R.id.ProfileName);
        String SaveNameDb_String = ProfileInfo_var.getText().toString();
        Log.d(TAG, SaveNameDb_String);

        // Create a new user with a first and last name
        Map<String, Object> user_Map = new HashMap<>();
        user_Map.put("Name", SaveNameDb_String);
        user_Map.put("Second", "poi 2");
        user_Map.put("Anno", 118);

        // Add a new document with a document = ID
        userID = dbAuth.getCurrentUser().getUid();

        DocumentReference MyDocReference = db.collection("users_basic_information").document(userID);

                MyDocReference.set(user_Map);

                //TODO UID INSERIMENTO COME DOCUMENT ID request.auth != null
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


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Inside onStart");






            }//END onStart




}//END ACTIVITY
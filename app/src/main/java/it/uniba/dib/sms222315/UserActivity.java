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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

//FINESTRA DIALOG EDIT
    Button edit;
    AlertDialog MyDialog_edit;

    TextView ProfileInfo_var;



    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "TAG_UserActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Inside onCreate");
        setContentView(R.layout.activity_user);

        //load name profile
        ProfileInfo_var = findViewById(R.id.ProfileName);

        db.collection("users_basic_information")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


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

        // Add a new document with a generated ID
        db.collection("users_basic_information")
                .add(user_Map)
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
package it.uniba.dib.sms222315;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;




import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

//FINESTRA DIALOG EDIT
    Button edit;
    AlertDialog MyDialog_edit;

    TextView ProfileInfo_var;
    ScrollView MyScrollFullProfile_var;

    //DB variable
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_UserActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Inside onCreate");
        setContentView(R.layout.activity_user);

        //load name profile
        ProfileInfo_var = findViewById(R.id.ProfileName);


        /**
         *
         * //get map from db
         *         Map<String, Object> user_Map_get = new HashMap<>();
         *         //TODO da completare sono arrivato qui
         *         // Create a reference to the cities collection
         * CollectionReference NameProfile = db.collection("users_basic_information");
         *
         * // Create a query against the collection.
         *         Query query = NameProfile.whereEqualTo("Name",true );
         *
         *
         *         //get first field from map
         *         user_Map_get.get("Name");
         *
         */




        ProfileInfo_var.setText("info da db");

        String checkInfo = ProfileInfo_var.getText().toString();
        Log.d(TAG, checkInfo);

        if (checkInfo == "info da db") {
            MyScrollFullProfile_var = findViewById(R.id.ProfileFullPannel);
            MyScrollFullProfile_var.setVisibility(View.INVISIBLE);
            buildDialog();
            MyDialog_edit.show();
            Log.d(TAG, "inside checkInfo");


        }

    }

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

                        MyScrollFullProfile_var = findViewById(R.id.ProfileFullPannel);
                        MyScrollFullProfile_var.setVisibility(View.VISIBLE);
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Inside onStart");

    }//END onStart




}//END ACTIVITY
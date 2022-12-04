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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

//FINESTRA DIALOG EDIT
    Button edit;
    AlertDialog MyDialog_edit;




    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "TAG_UserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        edit = findViewById(R.id.edit_button_UserProfile);

        buildDialog();

//TODO forse da modificare edit per controllare se è primo avvio
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog_edit.show();
            }
        });


    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.my_dialog , null );

        EditText aboutMe = view.findViewById(R.id.dialog_text_edit);

        builder.setView(view);
        builder.setTitle(R.string.Text_dialog_AboutYou_Edit)
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

        TextView AboutMeTextView = findViewById(R.id.TextView_AboutMe);
        AboutMeTextView.setText(textAboutMe);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Inside onStart");
        // Create a new user with a first and last name
        Map<String, Object> user_Map = new HashMap<>();
        user_Map.put("first", "vito");
        user_Map.put("last", "Lovelace");
        user_Map.put("born", 1815);

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

    public void launch_hamburger_menu(View view) {


    }

    public void button_edit_AboutMe(View view) {
        //SETUP DIALOG
        AlertDialog MyDialogVar = null;
        MyDialogVar.show();
        //todo MASSI ARRIVATO QUI
    }
}//END ACTIVITY
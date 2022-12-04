package it.uniba.dib.sms222315;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "TAG_UserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);



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
        //SETUP DIALOG
        AlertDialog MyDialogVar = null;
        MyDialogVar.show();
        //todo MASSI ARRIVATO QUI

    }
}//END ACTIVITY
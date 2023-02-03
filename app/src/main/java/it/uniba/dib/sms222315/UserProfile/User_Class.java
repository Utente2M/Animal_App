package it.uniba.dib.sms222315.UserProfile;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class User_Class {

    private String prv_str_nome;
    private String prv_str_email;
    private String prv_phone;
    private String prv_street;
    private String prv_DateBorn;
    private String prv_str_UID;
    private Uri prv_Uri_ProfImg;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "TAG_Class_User";



    public User_Class() {

        Log.d(TAG , "Costructor init.");

        infoAut_current_user();
        Log.d(TAG , "info profile init.");
        //infoProfileBasic();
        Log.d(TAG , "info ok.");

    }

    private void infoProfileBasic() {
        // Add a new document with a document = ID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        DocumentReference userRef = db.collection("User Basic Info").
                document(userID);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> data = document.getData();
                        if(data.containsKey("Phone")){

                            prv_phone = data.get("Phone").toString();
                            Log.d(TAG,"CLASS_Phone : " + prv_phone);
                        }
                        if(data.containsKey("address")){
                            prv_street = data.get("address").toString();
                            Log.d(TAG,"CLASS_address : " + prv_street);
                        }
                        if(data.containsKey("dateBorn")){
                            prv_DateBorn = data.get("dateBorn").toString();
                            Log.d(TAG,"CLASS_dateBorn : " + prv_DateBorn);
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                }
            }});



    }


    private void infoAut_current_user (){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            prv_str_nome = user.getDisplayName();

            Log.d(TAG,"CLASS_nome : " + prv_str_nome);

            //set email
            String email = user.getEmail();
            prv_str_email =email;
            Log.d(TAG, "CLASS_email : " + email);

            Uri photoUrl = user.getPhotoUrl();
            prv_Uri_ProfImg = photoUrl;
            Log.d(TAG, "CLASS_uri : " + photoUrl);

            //NON USATO
            // Check if user's email is verified
           // boolean emailVerified = user.isEmailVerified();


            // set UID
            String uid = user.getUid();
            Log.d(TAG, "CLASS_UID : " + uid);
            prv_str_UID = uid;

            infoProfileBasic();



        }

    }


    public String getPrv_str_nome() {
        return prv_str_nome;
    }

    public String getPrv_str_email() {
        return prv_str_email;
    }


    public String getPrv_str_UID() {
        return prv_str_UID;
    }

    public Uri getUri_ProfImg() {
        return prv_Uri_ProfImg;
    }

    public String getPrv_street() {
        return prv_street;
    }

    public String getPrv_phone() {
        return prv_phone;
    }
    public String getPrv_dateBorn() {
        return prv_DateBorn;
    }
}//END CLASS




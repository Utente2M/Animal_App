package it.uniba.dib.sms222315.UserProfile;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User_Class {

    private String prv_str_nome;
    private String prv_str_email;
    private String prv_str_UID;
    private Uri prv_Uri_ProfImg;


    private static final String TAG = "TAG_Class_User";



    public User_Class() {

        Log.d(TAG , "Costructor init.");

        infoAut_current_user();
        Log.d(TAG , "info Auth ok.");
        infoProfileBasic();
        Log.d(TAG , "info Auth ok.");

        Log.d(TAG , "Costructor ok.");

    }

    private void infoProfileBasic() {

    }


    private void infoAut_current_user (){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            prv_str_nome = name;
            Log.d(TAG,"CLASS_nome : " + name);

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
}//END CLASS




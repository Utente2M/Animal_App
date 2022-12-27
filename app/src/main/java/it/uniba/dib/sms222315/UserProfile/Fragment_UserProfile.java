package it.uniba.dib.sms222315.UserProfile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import it.uniba.dib.sms222315.MainActivity;
import it.uniba.dib.sms222315.R;

public class Fragment_UserProfile extends Fragment {

    TextView TV_UID , TV_email , TV_name ;
    Button but_logout , but_menu;
    ImageView Img_profileUser;

    Interf_UserProfile myCallBackFrag;
    User_Class my_User;


    private static final String TAG = "TAG_Frag_UserProfile";
    private static final int REQUEST_CODE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG , "onCreateView , try create class");
        View my_view = inflater.inflate(R.layout.fragm_userprofile_basic , container , false);

        allfind(my_view);


        my_User = new User_Class();

        Log.d(TAG , "onCreateView , OK create class");

        allSetClick();
        setTextandImage();


        return my_view;
    }

    private void setTextandImage() {
        TV_name.setText(my_User.getPrv_str_nome());
        TV_email.setText(my_User.getPrv_str_email());
        TV_UID.setText(my_User.getPrv_str_UID());

        Uri UriImgProfile = null;
        UriImgProfile = my_User.getUri_ProfImg();
        if (UriImgProfile==(null) ){
            Log.d(TAG, " no profile image");
        }
        else{
            Img_profileUser.setImageURI(my_User.getUri_ProfImg());
            Log.d(TAG, " ok profile image");
        }
    }

    private void allSetClick() {
        //button menu
        but_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "BUTTON menu OK ");
                if (myCallBackFrag != null ){
                    myCallBackFrag.changeFragment();
                }

            }
        });
        //button logout
        but_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "BUTTON LOGOUT OK ");
                FirebaseAuth.getInstance().signOut();
                //Richiama la main activity
                Intent okLogut = new Intent (getActivity(), MainActivity.class);
                startActivity(okLogut);

            }
        });
        //Change foto profile
        Img_profileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyPermissions()){
                    Log.d(TAG , "Permission ok");

                }

            }
        });
    }

    private boolean verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){
            return true;
            //setupViewPager();
        }else{
            /*
            ActivityCompat.requestPermissions(SearchActivity.this,
                    permissions,
                    REQUEST_CODE);
             */
            //in fragment :
            requestPermissions(permissions, REQUEST_CODE);
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }


    private void allfind(View my_view) {
        TV_name = my_view.findViewById(R.id.show_profil_name);
        TV_email = my_view.findViewById(R.id.show_profil_mail);
        TV_UID = my_view.findViewById(R.id.show_profil_uid);
        but_logout = my_view.findViewById(R.id.logout_profile);
        but_menu = my_view.findViewById(R.id.button_menu);
        Img_profileUser = my_view.findViewById(R.id.frag_userbasic_imageView_UserProfile);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //listner di Class Interface per scambio dati
       // myListnerCall = (CallbackFragment) context;
    }

    public void setMyCallBackFrag (Interf_UserProfile varCallback ){
        this.myCallBackFrag = varCallback;
    }


}//END CLASS

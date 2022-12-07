package it.uniba.dib.sms222315.UserProfile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import it.uniba.dib.sms222315.Autentication.CallbackFragment;
import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserProfile.Interf_UserProfile;

public class Fragment_UserProfile extends Fragment {

    TextView TV_UID , TV_email , TV_name ;
    Button but_logout , but_menu;

    Interf_UserProfile myCallBackFrag;
    User_Class my_User;


    private static final String TAG = "TAG_Frag_UserProfile";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG , "onCreateView , try create class");
        View my_view = inflater.inflate(R.layout.fragm_userprofile_basic , container , false);
        TV_name = my_view.findViewById(R.id.show_profil_name);
        TV_email = my_view.findViewById(R.id.show_profil_mail);
        TV_UID = my_view.findViewById(R.id.show_profil_uid);
        but_logout = my_view.findViewById(R.id.logout_profile);
        but_menu = my_view.findViewById(R.id.button_menu);

        my_User = new User_Class();
        Log.d(TAG , "onCreateView , OK create class");
        TV_name.setText(my_User.getPrv_str_nome());
        TV_email.setText(my_User.getPrv_str_email());
        TV_UID.setText(my_User.getPrv_str_UID());

        but_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, "BUTTON LOGOUT OK ");
            }
        });

        but_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "BUTTON menu OK ");
                if (myCallBackFrag != null ){
                    myCallBackFrag.changeFragment();
                }

            }
        });

        return my_view;
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

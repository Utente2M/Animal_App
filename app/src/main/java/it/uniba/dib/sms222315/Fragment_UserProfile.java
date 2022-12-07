package it.uniba.dib.sms222315;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class Fragment_UserProfile extends Fragment {

    TextView TV_UID , TV_email , TV_name ;
    Button but_logout , but_menu;

    Interf_UserProfile myCallBackFrag;

    private static final String TAG = "TAG_Frag_UserProfile";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragm_userprofile_basic , container , false);
        TV_name = my_view.findViewById(R.id.show_profil_name);
        TV_email = my_view.findViewById(R.id.show_profil_mail);
        TV_UID = my_view.findViewById(R.id.show_profil_uid);
        but_logout = my_view.findViewById(R.id.logout_profile);
        but_menu = my_view.findViewById(R.id.button_menu);

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


    public void setMyCallBackFrag (Interf_UserProfile varCallback ){
        this.myCallBackFrag = varCallback;
    }


}//END CLASS

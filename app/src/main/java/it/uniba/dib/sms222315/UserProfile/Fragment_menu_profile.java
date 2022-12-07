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

public class Fragment_menu_profile extends Fragment {

    TextView TV_UID , TV_email , TV_name ;
    Button but_logout , but_menu;

    Interf_UserProfile myCallBackFrag;


    private static final String TAG = "TAG_Frag_UserProfile";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragm_menu_profile , container , false);

        but_logout = my_view.findViewById(R.id.button_logout_menu_profile);


        but_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, "BUTTON LOGOUT OK ");
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


}

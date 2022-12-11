package it.uniba.dib.sms222315.UserProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import it.uniba.dib.sms222315.UserPets.Activity_MyPets;
import it.uniba.dib.sms222315.MainActivity;
import it.uniba.dib.sms222315.R;

public class Fragment_menu_profile extends Fragment {

    ImageView IV_my_pets;
    Button but_logout , but_menu;

    Interf_UserProfile myCallBackFrag;


    private static final String TAG = "TAG_Frag_UserProfile";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragm_menu_profile , container , false);

        but_logout = my_view.findViewById(R.id.button_logout_menu_profile);
        IV_my_pets = my_view.findViewById(R.id.FragMenu_IV_my_pet);


        //myPet start Activity
        IV_my_pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG , "Try start Activity my pets");
                Intent myPets = new Intent(getActivity(),Activity_MyPets.class);
                startActivity(myPets);
            }//end onClick myPets
        });

        //bottone logout
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

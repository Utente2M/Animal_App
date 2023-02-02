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

import it.uniba.dib.sms222315.Enti.Activity_Enti;
import it.uniba.dib.sms222315.Friends.Activity_MyFriends;
import it.uniba.dib.sms222315.MeetPets.Activity_Pets_Meet;
import it.uniba.dib.sms222315.Reporting.Activity_Reporting;
import it.uniba.dib.sms222315.UserExpense.Activity_MyExpense;
import it.uniba.dib.sms222315.UserPets.Activity_MyPets;
import it.uniba.dib.sms222315.MainActivity;
import it.uniba.dib.sms222315.R;

public class Fragment_menu_profile extends Fragment {

    ImageView IV_my_pets , IV_my_expense, IV_my_friends , IV_my_report,IV_Associations ,but_meetPet, but_logout;
    Button but_menu;

    private static final String TAG = "TAG_Frag_MENUProfile";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragm_menu_profile , container , false);

        allfind(my_view);
        allsetClick();

        return my_view;
    }

    private void allfind(View my_view) {
        but_logout = my_view.findViewById(R.id.button_logout_menu_profile);
        IV_my_pets = my_view.findViewById(R.id.FragMenu_IV_my_pet);
        IV_my_expense = my_view.findViewById(R.id.FragMenu_IV_my_expense);
        IV_my_friends =my_view.findViewById(R.id.FragMenu_IV_my_friends);
        IV_my_report = my_view.findViewById(R.id.FragMenu_IV_my_report);
        IV_Associations  = my_view.findViewById(R.id.FragMenu_IV_associations);
        but_meetPet = my_view.findViewById(R.id.button_MeetAnimal);
    }

    private void allsetClick() {
        //myPet start Activity
        IV_my_pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG , "Try start Activity my pets");
                Intent myPets = new Intent(getActivity(),Activity_MyPets.class);
                startActivity(myPets);

            }//end onClick myPets
        });

        //button my expense
        IV_my_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG , "Try start Activity my expense");
                Intent myExpense = new Intent(getActivity(), Activity_MyExpense.class);
                startActivity(myExpense);

            }//end onClick my expense
        });

        //button my fiends
        IV_my_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG , "Try start Activity my friends");
                Intent myFriend = new Intent(getActivity(), Activity_MyFriends.class);
                startActivity(myFriend);

            }//end onClick my expense
        });

        //Button MyReport
        IV_my_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG , "Try start Activity my friends");
                Intent myReport = new Intent(getActivity(), Activity_Reporting.class);
                startActivity(myReport);

            }//end onClick my expense
        });



        //bottone associations
        IV_Associations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG , "Try start Activity associations");
                Intent associations = new Intent(getActivity(), Activity_Enti.class);
                startActivity(associations);

            }
        });

        //button meet pet
        but_meetPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent meet = new Intent(getActivity(), Activity_Pets_Meet.class);
                startActivity(meet);
            }
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
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }





}

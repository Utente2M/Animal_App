package it.uniba.dib.sms222315.MeetPets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserExpense.Fragment_MyExpense_Home;
import it.uniba.dib.sms222315.UserPets.Fragment_MyPets_Home;
import it.uniba.dib.sms222315.UserPets.Fragment_MyPets_Profile;

public class Activity_Pets_Meet extends AppCompatActivity {

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    private static final String TAG = "TAG_Activity_Pets_Meet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_meet);
        addFragment();
    }


    private void addFragment() {

        my_fragment = new Fragment_PetsMeet_Home();
        //my_frag_manager = getActivity().getSupportFragmentManager();
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        /*
        Bundle bundle = new Bundle();
        //this is pass
        bundle.putParcelable("modPets", clickPet);
        my_fragment.setArguments(bundle);
         */
        //si aggiunge il richiamo allo stack
        my_frag_trans.addToBackStack(null);
        //add or replace
        my_frag_trans.add(R.id.Frame_Act_PetsMeet , my_fragment );
        my_frag_trans.commit();

    }//END ADD FRAGM
}
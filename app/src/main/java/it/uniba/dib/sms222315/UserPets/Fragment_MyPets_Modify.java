package it.uniba.dib.sms222315.UserPets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserExpense.MyExpense;


public class Fragment_MyPets_Modify extends Fragment {


    private static final String TAG = "TAG_Frag_MyPet_MODIFY";
    Pets receivedPet;


    public Fragment_MyPets_Modify() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LOAD DATA FROM FRAG MY EXPENSE HOME
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedPet = bundle.getParcelable("modPets"); // Key
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG , "onCreateView ");
        View my_view = inflater.inflate(R.layout.fragment__my_pets__modify , container , false);

        Log.d(TAG , " nome animale : " +receivedPet.getPrv_str_namePets());


        // Inflate the layout for this fragment
        return my_view;
    }
}
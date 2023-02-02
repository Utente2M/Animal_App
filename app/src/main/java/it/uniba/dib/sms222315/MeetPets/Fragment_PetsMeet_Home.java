package it.uniba.dib.sms222315.MeetPets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.dib.sms222315.R;


public class Fragment_PetsMeet_Home extends Fragment {



    public Fragment_PetsMeet_Home() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragment__pets_meet__home, container, false);


        return my_view;
    }
}
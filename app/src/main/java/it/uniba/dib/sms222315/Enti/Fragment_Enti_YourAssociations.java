package it.uniba.dib.sms222315.Enti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.dib.sms222315.R;


public class Fragment_Enti_YourAssociations extends Fragment {



    public Fragment_Enti_YourAssociations() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment__enti__your_associations, container, false);
        // Inflate the layout for this fragment
        return my_view;
    }
}
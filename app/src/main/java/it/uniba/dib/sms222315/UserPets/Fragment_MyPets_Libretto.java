package it.uniba.dib.sms222315.UserPets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.uniba.dib.sms222315.R;



public class Fragment_MyPets_Libretto extends Fragment {

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    Pets receivedPet;
    FloatingActionButton BT_new_libretto;
    private static final String TAG = "TAG_Frag_MyPet_LIBRETTO";

    public Fragment_MyPets_Libretto() {
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
        // Inflate the layout for this fragment
        View my_view = inflater.inflate(R.layout.fragment__my_pets__libretto, container, false);
        BT_new_libretto = my_view.findViewById(R.id.BT_Act_myPets_Libretto);


        Log.d(TAG, "Nome animale: " + receivedPet.getPrv_str_namePets());

        BT_new_libretto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ok button click.");

                        my_fragment = new Fragment_MyPets_NewLibretto();
                        my_frag_manager = getActivity().getSupportFragmentManager();
                        my_frag_trans = my_frag_manager.beginTransaction();
                        Bundle bundle = new Bundle();
                        //this is pass
                        bundle.putParcelable("modPets", receivedPet);
                        my_fragment.setArguments(bundle);
                        //si aggiunge il richiamo allo stack
                        my_frag_trans.addToBackStack(null);
                        //add diventa replace
                        my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
                        my_frag_trans.commit();

            }
        });





        return my_view;
    }
}
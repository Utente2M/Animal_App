package it.uniba.dib.sms222315.UserPets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import it.uniba.dib.sms222315.R;


public class Fragment_MyPets_Modify extends Fragment {

    //BUNDLE
    Pets receivedPet;

    EditText data_nasc, sex , razza, mantello , segniPart ;
    ImageButton BT_confermePet , BT_backPet;


    private static final String TAG = "TAG_Frag_MyPet_MODIFY";



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

        View my_view = inflater.inflate(R.layout.fragment__my_pets__modify, container, false);
        Log.d(TAG , "onCreateView ");


        setfind(my_view);
        setTextfromPets();
        setAllOnClick();


        return my_view;
    }

    private void setAllOnClick() {
        BT_backPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        BT_confermePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    private void setTextfromPets() {

        if (!receivedPet.getPrv_DataNascita().isEmpty()){
            data_nasc.setText(receivedPet.getPrv_DataNascita());
        }
        if (!receivedPet.getPrv_sex().isEmpty()){
            sex.setText(receivedPet.getPrv_sex());
        }
        if (!receivedPet.getPrv_Razza().isEmpty()){
            razza.setText(receivedPet.getPrv_Razza());
        }
        if (!receivedPet.getPrv_Mantello().isEmpty()){
            mantello.setText(receivedPet.getPrv_Mantello());
        }
        if (!receivedPet.getPrv_SegniParticolari().isEmpty()){
            segniPart.setText(receivedPet.getPrv_SegniParticolari());
        }


    }//END SET TEXT

    //set all find from layout
    private void setfind(View my_view) {


        data_nasc = my_view.findViewById(R.id.ET_MyPetModify_data);
        sex = my_view.findViewById(R.id.ET_MyPetModify_sex);

        razza = my_view.findViewById(R.id.ET_MyPetModify_razza);
        mantello = my_view.findViewById(R.id.ET_MyPetModify_mantello);
        segniPart = my_view.findViewById(R.id.ET_MyPetModify_segPartic);

        BT_backPet = my_view.findViewById(R.id.BT_Back_MyPetsModify);
        BT_confermePet = my_view.findViewById(R.id.BT_Apply_MyPetsModify);

    }

}//END CLASS
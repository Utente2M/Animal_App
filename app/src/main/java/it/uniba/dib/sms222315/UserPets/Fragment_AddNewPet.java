package it.uniba.dib.sms222315.UserPets;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserProfile.Interf_UserProfile;


public class Fragment_AddNewPet extends Fragment {

    EditText ET_Name , ET_Specie ,ET_Sex, ET_Razza ;
    Button BT_Create;
    Interf_UserPets MyInterf_Pets;


    public Fragment_AddNewPet() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragment__add_new_pet, container, false);

        /*
        prv_str_namePets = namePets; //required
        prv_specie = specie; //required
        prv_sex = sex; //required
        prv_Razza = razza; //required
         */

        ET_Name = my_view.findViewById(R.id.et_Frag_NewPetDB_name);
        ET_Specie = my_view.findViewById(R.id.et_Frag_NewPetDB_specie);
        ET_Sex = my_view.findViewById(R.id.et_Frag_NewPetDB_sex);
        ET_Razza = my_view.findViewById(R.id.et_Frag_NewPetDB_razza) ;



        BT_Create = my_view.findViewById(R.id.bt_Frag_NewPetDB_create);
        BT_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendName = ET_Name.getText().toString();
                String sendSpecie = ET_Specie.getText().toString();
                String sendSex = ET_Sex.getText().toString();
                String sendRazza = ET_Razza.getText().toString();
                // da implementare interfaccia e controllo in cactivty
                createPetInDB (sendName, sendSpecie,sendSex,sendRazza);
            }
        });



        // Inflate the layout for this fragment
        return my_view;
    }//END CReatView

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MyInterf_Pets = (Interf_UserPets) context;

    }

    private void createPetInDB(String Name, String Specie, String Sex, String Razza) {
        MyInterf_Pets.createAnimalInDB(Name,Specie,Sex,Razza);

    }





}//END CLASS
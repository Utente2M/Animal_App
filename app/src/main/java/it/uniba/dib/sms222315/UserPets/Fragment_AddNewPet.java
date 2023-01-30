package it.uniba.dib.sms222315.UserPets;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import it.uniba.dib.sms222315.R;


public class Fragment_AddNewPet extends Fragment {

    EditText ET_Name , ET_Specie ,ET_Sex, ET_Razza ;

    Spinner SP_Specie, SP_Sex;
    String sendSpecie , sendSex;
    Button BT_Create;
    Interf_UserPets MyInterf_Pets;

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;




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

        View my_view = inflater.inflate(R.layout.fragment__my_pets_add_new_pet, container, false);

        setAllfind(my_view);
        setAllSpinner(my_view);
        setAllButton(my_view);








        // Inflate the layout for this fragment
        return my_view;
    }//END CReatView

    private void setAllButton(View my_view) {
        BT_Create = my_view.findViewById(R.id.bt_Frag_NewPetDB_create);
        BT_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendName = ET_Name.getText().toString();
                //String sendSpecie = ET_Specie.getText().toString();
                //String sendSex = ET_Sex.getText().toString();
                String sendRazza = ET_Razza.getText().toString();


                if( sendName.isEmpty()){
                    ET_Name.setError("NAME is required");
                }
                /*
                else if(sendSpecie.isEmpty()){
                    ET_Specie.setError("SPECIE is required");
                }


                else if(sendSex.isEmpty()){
                    ET_Sex.setError("SEX is required");
                }
                */

                else if(sendRazza.isEmpty()){
                    ET_Razza.setError("Razza is required");
                }
                else{
                    // da implementare interfaccia e controllo in cactivty
                    createPetInDB (sendName, sendSpecie,sendSex,sendRazza);
                    //tornare al fragment precedente
                    MyInterf_Pets.turnTohome ();
                    Toast.makeText(getContext() , "Congratulation ! \n" +
                            " You have one new Pet " , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setAllfind(View my_view) {

        ET_Name = my_view.findViewById(R.id.et_Frag_NewPetDB_name);
        //ET_Specie = my_view.findViewById(R.id.et_Frag_NewPetDB_specie);
        //ET_Sex = my_view.findViewById(R.id.et_Frag_NewPetDB_sex);
        ET_Razza = my_view.findViewById(R.id.et_Frag_NewPetDB_razza) ;
    }

    private void setAllSpinner(View my_view) {

        SP_Specie = my_view.findViewById(R.id.spinner_specie);
        ArrayAdapter <CharSequence> adapter_spin = ArrayAdapter.createFromResource(getContext(), R.array.Specie_supportate, android.R.layout.simple_spinner_item);
        adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_Specie.setAdapter(adapter_spin);
        SP_Specie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sendSpecie = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SP_Sex =my_view.findViewById(R.id.spinner_sex);
        ArrayAdapter <CharSequence> adapter_spin_sex = ArrayAdapter.createFromResource(getContext(), R.array.Sex_supportate, android.R.layout.simple_spinner_item);
        adapter_spin_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_Sex.setAdapter(adapter_spin_sex);
        SP_Sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sendSex = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MyInterf_Pets = (Interf_UserPets) context;

    }

    private void createPetInDB(String Name, String Specie, String Sex, String Razza ) {

        MyInterf_Pets.createAnimalInDB(Name,Specie,Sex,Razza);

    }


}//END CLASS
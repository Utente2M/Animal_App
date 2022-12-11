package it.uniba.dib.sms222315.UserPets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.uniba.dib.sms222315.Autentication.CallbackFragment;
import it.uniba.dib.sms222315.R;



public class Fragment_MyPets_Home extends Fragment {

    private static final String TAG = "TAG_Frag_MyPets_Home";
    FloatingActionButton BT_new_pet;
    Interf_UserPets myCallBackFrag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG , "onCreateView , try create class");
        View my_view = inflater.inflate(R.layout.fragment__my_pets__home , container , false);

        //tutti i find e gli onclick

        ListView mListView = (ListView) my_view.findViewById(R.id.listView_MyPets);


//Create Pets example
        //TODO con il db funzionante questa diventerà una query che rimepie l'array
        Pets dog_1 = new Pets("Pluto" , "Cane", "Maschio",
                "Coocker", "", "","");

        Pets cat_1 = new Pets("Gomma" , "Gatto", "Femmina",
                "Killer", "", "","");

        Pets dog_2 = new Pets("Charlie Hope" , "Cane", "Femmina",
                "Lupo", "", "","");

        Pets rabbit_1 = new Pets("Melissa Mellessa" , "Coniglio", "Femmina",
                "Saccc", "", "","");




        //Add the Person objects to an ArrayList
        ArrayList<Pets> petList = new ArrayList<>();
        petList.add(dog_1);
        petList.add(cat_1);
        petList.add(dog_2);
        petList.add(rabbit_1);


        // FINE MODIFICHE TODO


        MyPetsListAdapter adapter = new MyPetsListAdapter(getContext(), R.layout.adapter_my_pets_list, petList);
        mListView.setAdapter(adapter);






        BT_new_pet = my_view.findViewById(R.id.BT_Act_myPets_addAnimal);
        Log.d(TAG, "ok button find.");
        BT_new_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change Frgment
                Log.d(TAG, "ok button click.");

                if (myCallBackFrag != null ){
                    myCallBackFrag.changeFragment();
                }

            }
        });


        return my_view;
    }



    //potenziale errore
    public void setMy_callbackFragment (Interf_UserPets my_callFrag ){
        this.myCallBackFrag = my_callFrag;
    }
}
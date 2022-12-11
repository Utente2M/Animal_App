package it.uniba.dib.sms222315.UserPets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.TestListView.Person;
import it.uniba.dib.sms222315.TestListView.PersonListAdapter;
import it.uniba.dib.sms222315.UserProfile.Fragment_UserProfile;

public class Activity_MyPets extends AppCompatActivity {

    private static final String TAG = "TAG_Act_MyPets";

    Button BT_new_pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listView_MyPets);

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


        MyPetsListAdapter adapter = new MyPetsListAdapter(this, R.layout.adapter_my_pets_list, petList);
        mListView.setAdapter(adapter);

        BT_new_pet = mListView.findViewById(R.id.BT_addAnimal);
        BT_new_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*


                //primo fragment profile classico
                //con new scegliamo il fragment da istanziare

                Fragment_UserProfile my_fragment = new Fragment_UserProfile();

                //Fragment_UserProfile my_fragment = new Fragment_UserProfile();
                my_fragment.setMyCallBackFrag(this);
                //my_fragment.myCallBackFrag(this);
                my_frag_manager = getSupportFragmentManager();
                my_frag_trans = my_frag_manager.beginTransaction();

                my_frag_trans.add(R.id.FragProfileUser , my_fragment);
                my_frag_trans.commit();

                */
            }
        });

    }//END ON CREATE


}
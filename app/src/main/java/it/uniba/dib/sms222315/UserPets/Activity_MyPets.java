package it.uniba.dib.sms222315.UserPets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.TestListView.Person;
import it.uniba.dib.sms222315.TestListView.PersonListAdapter;

public class Activity_MyPets extends AppCompatActivity {

    private static final String TAG = "TAG_Act_MyPets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listView_MyPets);

        //Create Pets example
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


        MyPetsListAdapter adapter = new MyPetsListAdapter(this, R.layout.adapter_my_pets_list, petList);
        mListView.setAdapter(adapter);

    }//END ON CREATE
}
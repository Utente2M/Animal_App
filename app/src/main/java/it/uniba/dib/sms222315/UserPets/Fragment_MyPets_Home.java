package it.uniba.dib.sms222315.UserPets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.uniba.dib.sms222315.Autentication.CallbackFragment;
import it.uniba.dib.sms222315.R;




public class Fragment_MyPets_Home extends Fragment {

    //try db load in fragment
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    ArrayList<Pets> petList = new ArrayList<>();
    ListView mListView;


    FloatingActionButton BT_new_pet;
    Interf_UserPets myCallBackFrag;



    private static final String TAG = "TAG_Frag_MyPets_Home";

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
        mListView = (ListView) my_view.findViewById(R.id.listView_MyPets);



        popolateList();







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

    private void popolateList() {
        //Create Pets example
        //TODO con il db funzionante questa diventerà una query che rimepie l'array

        petList.clear();



        //user id sarà sostituito dal codice padrone
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);

        db.collection("Animal From User").document(userID).
                collection("List Pets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Boolean check;
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                petList.add(document.toObject(Pets.class));

                                MyPetsListAdapter adapter = new MyPetsListAdapter(getContext(),
                                        R.layout.adapter_my_pets_list, petList);

                                mListView.setAdapter(adapter);
                            }
                            //qui messaggio forza visibilità off


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            //qui messaggio visibilità si

                        }
                    }
                });








        //try load from db
        //Query capitalCities = db.collection("cities").whereEqualTo("capital", true);


        // TODO sono arrivato qua




    }


    //potenziale errore
    public void setMy_callbackFragment (Interf_UserPets my_callFrag ){
        this.myCallBackFrag = my_callFrag;
    }
}
package it.uniba.dib.sms222315.UserPets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;


public class Fragment_MyPets_Home extends Fragment {

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    //ISTANCE DB
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
        Log.d(TAG , "onCreateView ");
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


        //MODIFY ELEMENT FROM LIST
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Pets clickPet = petList.get(position);
                openDetailPets(clickPet);

            }
        });



        return my_view;
    }

    private void openDetailPets(Pets clickPet) {
        my_fragment = new Fragment_MyPets_Profile();
        my_frag_manager = getActivity().getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        Bundle bundle = new Bundle();
        //this is pass
        bundle.putParcelable("modPets", clickPet);
        my_fragment.setArguments(bundle);
        //si aggiunge il richiamo allo stack
        my_frag_trans.addToBackStack(null);
        //add diventa replace
        my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
        my_frag_trans.commit();

    }

    private void popolateList() {
        //Create Pets example
        //TODO con il db funzionante questa diventerà una query che rimepie l'array

        petList.clear();



        //user id sarà sostituito dal codice padrone
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);


        CollectionReference animalRef = db.collection("Animal DB");
        Query MyPets = animalRef.whereArrayContains("prv_Str_responsabili" , userID);

        MyPets.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                        Pets mypet = document.toObject(Pets.class);
                        mypet.setPrv_doc_id(document.getId());
                        petList.add(mypet);
                        Log.d(TAG , "uid doc : " + mypet.getPrv_doc_id());

                    }//end for

                        MyPetsListAdapter adapter = new MyPetsListAdapter(getContext(),
                                R.layout.adapter_my_pets_list, petList);

                        mListView.setAdapter(adapter);


                }//end if
                else {
                    //nessun animale da mostrare
                }//fine else

            }//end on complete

        }); //end Listners


    }//END popolateList


    //potenziale errore
    public void setMy_callbackFragment (Interf_UserPets my_callFrag ){
        this.myCallBackFrag = my_callFrag;
    }
}
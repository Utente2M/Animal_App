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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;



public class Fragment_MyPets_Libretto extends Fragment {

    EditText Et_filter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ListView mListView;
    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    Pets receivedPet;
    FloatingActionButton BT_new_libretto;

    VisiteAdapter adapter;

    //Control ListView
    ArrayList<Visite> originalList = new ArrayList<>();

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


        if (adapter ==null){
            originalList.clear();
            //filteredList.clear();
            popolateList();
            Log.d(TAG , "ok popolateList ");
        }
        Log.d(TAG, "Nome animale: " + receivedPet.getPrv_str_namePets());

        setAllfind(my_view);
        setAllclick();






        return my_view;
    }

    private void setAllfind(View my_view) {
        BT_new_libretto = my_view.findViewById(R.id.BT_Act_myPets_Libretto);
        mListView = my_view.findViewById(R.id.LV_Libretto);
        Et_filter = my_view.findViewById(R.id.ET_Libretto);

    }

    private void setAllclick() {
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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Visite clickVisite = originalList.get(position);
                openDetails(clickVisite);
            }
        });
    }

    private void openDetails(Visite clickVisite) {
        my_fragment = new Fragment_Libretto_Modify();
        my_frag_manager = getActivity().getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        Bundle bundle = new Bundle();

        //this is pass
        bundle.putParcelable("modVisite", clickVisite);
        bundle.putParcelable("modPet", receivedPet);
        my_fragment.setArguments(bundle);

        //si aggiunge il richiamo allo stack
        my_frag_trans.addToBackStack(null);
        //add diventa replace
        my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
        my_frag_trans.commit();
    }

    private void popolateList() {
        originalList.clear();

        /*
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);
         */


        CollectionReference animalRef = db.collection("Animal DB")
                .document(receivedPet.getPrv_doc_id())
                .collection("Visite");


        animalRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                        Visite myvisita = document.toObject(Visite.class);
                        myvisita.setDocID(document.getId());
                        originalList.add(myvisita);
                        Log.d(TAG , "uid doc : " + myvisita.getDocID());

                    }//end for

                    adapter = new VisiteAdapter(getContext(),
                            R.layout.adapter_libretto_medico, originalList);

                    mListView.setAdapter(adapter);


                }//end if
                else {
                    //nessun animale da mostrare
                }//fine else

            }//end on complete

        }); //end Listners


    }//END popolateList


}
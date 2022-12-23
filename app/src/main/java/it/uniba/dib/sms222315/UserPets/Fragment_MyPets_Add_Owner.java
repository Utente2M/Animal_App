package it.uniba.dib.sms222315.UserPets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.uniba.dib.sms222315.Friends.Fragment_MyFriends_SearchNewFriend;
import it.uniba.dib.sms222315.Friends.Fragment_addNewFriend;
import it.uniba.dib.sms222315.Friends.MyFriends;
import it.uniba.dib.sms222315.Friends.MyFriendsListAdapter;
import it.uniba.dib.sms222315.R;


public class Fragment_MyPets_Add_Owner extends Fragment {

    //BUNDLE
    Pets receivedPet;

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    //Controlli ListView
    ArrayList<MyFriends> friendsList = new ArrayList<>();
    ListView mListView;
    MyFriendsListAdapter adapter;

    //CONTROL FOR FILTER
    EditText textFilter; //ET_FILTER_MyExpense
    ArrayList<MyFriends> filteredList = new ArrayList<>();

    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private static final String TAG = "TAG_Frag_MyPet_Add_Owner";


    public Fragment_MyPets_Add_Owner() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedPet = bundle.getParcelable("modPets"); // Key
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment__my_pets__add__owner, container, false);

        //tutti i find e gli onclick
        allFind(my_view);
        setupFilter(my_view);
        allOnClick();

        if (adapter ==null){
            friendsList.clear();
            filteredList.clear();
            popolateList();
            Log.d(TAG , "ok popolateList ");
        }

        return my_view;
    }

    private void allFind(View my_view) {

        mListView=my_view.findViewById(R.id.listView_addOwner);


    }

    private void setupFilter(View my_view) {
        //CONTROL FOR FILTER
        textFilter = my_view.findViewById(R.id.ET_FILTER_addOwner);
        textFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //filterList(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void allOnClick() {


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                MyFriends clickFriend = friendsList.get(position);
                openDetail(clickFriend); //funzione da riempire, dettagli amico
            }
        });
    }

    private void openDetail(MyFriends clickFriend) {

        my_fragment = new Fragment_Confirm_NewOwner();
        my_frag_manager = getActivity().getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        Bundle bundle = new Bundle();
        //this is pass
        bundle.putParcelable("newFriend", clickFriend);
        bundle.putParcelable("modPets" , receivedPet);
        //todo da problemi il tasto back
        //my_frag_trans.addToBackStack(null);
        my_fragment.setArguments(bundle);
        my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
        my_frag_trans.commit();
    }

    private void popolateList() {
        Log.d(TAG , "inside popolate ");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        //change in query for change order
        db.collection("User Basic Info").document(userID).
                collection("My Friends")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                MyFriends oneFriend = document.toObject(MyFriends.class);
                                Log.d(TAG, "PROVA " + oneFriend.getSecretId());
                                if (!userID.equals(oneFriend.getSecretId()) &&
                                !receivedPet.getPrv_Str_responsabili().contains(oneFriend.getSecretId())) {

                                    friendsList.add(oneFriend);
                                }

                            }//END FOR
                            adapter = new MyFriendsListAdapter(getContext(),
                                    R.layout.adapter_my_friend, friendsList);


                            mListView.setAdapter(adapter);
                        }else {

                        }
                    }
                });



    }

}
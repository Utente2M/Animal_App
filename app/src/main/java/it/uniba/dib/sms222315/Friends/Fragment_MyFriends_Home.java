package it.uniba.dib.sms222315.Friends;

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
import java.util.Locale;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserExpense.MyExpense;
import it.uniba.dib.sms222315.UserExpense.MyExpenseListAdapter;
import it.uniba.dib.sms222315.UserPets.Pets;


public class Fragment_MyFriends_Home extends Fragment {

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    //Controlli ListView

    ArrayList<MyFriends> friendsList = new ArrayList<>();
    ListView mListView;
    MyFriendsListAdapter adapter;



    FloatingActionButton BT_NewFriend;

    //CONTROL FOR FILTER
    EditText textFilter; //ET_FILTER_MyExpense
    ArrayList<MyFriends> filteredList = new ArrayList<>();


    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private static final String TAG = "TAG_Frag_MyFriends_Home";

    public Fragment_MyFriends_Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View my_view = inflater.inflate(R.layout.fragment__my_friends__home, container, false);

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
        mListView=my_view.findViewById(R.id.listView_MyFriend);
        BT_NewFriend = my_view.findViewById(R.id.BT_NewFriend);

    }

    private void setupFilter(View my_view) {
        //CONTROL FOR FILTER
        textFilter = my_view.findViewById(R.id.ET_FILTER_MyFriends);
        textFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterList(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void filterList(CharSequence charSequence) {

        filteredList.clear();

        Log.d(TAG , "inside Filter ");
        if (charSequence == null || charSequence.toString().isEmpty() ){
            Log.d(TAG , "Filter null  -> popolateList()");
            popolateList();
        }else {


            for (int k = 0; k < friendsList.size() ; k++ ){

                MyFriends friend = friendsList.get(k);

                Log.d(TAG , "charseq : " + charSequence);

                if (friend.getNameFriend().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT)) ||
                        friend.getMailFriend().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT)) ) {

                    if (!filteredList.contains(friend)) {
                        filteredList.add(friend);
                    }
                }
            } //end for

            adapter = new MyFriendsListAdapter(getContext(),
                    R.layout.adapter_my_friend, filteredList);


            mListView.setAdapter(adapter);

        } //end else
    }

    private void allOnClick() {
        BT_NewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_MyFriends_SearchNewFriend my_fragment = new Fragment_MyFriends_SearchNewFriend();
                //my_fragment.setMy_callbackFragment(this);
                my_frag_manager = getActivity().getSupportFragmentManager();
                my_frag_trans = my_frag_manager.beginTransaction();
                my_frag_trans.replace(R.id.Frame_Act_MyFriends , my_fragment);
                my_frag_trans.commit();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                MyFriends clickFriend = friendsList.get(position);
                openDetailExpanse(clickFriend); //funzione da riempire, dettagli amico
            }
        });
    }

    private void openDetailExpanse(MyFriends clickFriend) {

    }

    private void popolateList() {

        friendsList.clear();
        Log.d(TAG , "inside popolate ");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        /*
        Query friendsRef = db.collection("User Basic Info").document(userID)
                .collection("My Friends")
                .orderBy("numberOfLike", Query.Direction.DESCENDING);
         */


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
                                if (!userID.equals(oneFriend.getSecretId())) {

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


}//END FRAGMENT
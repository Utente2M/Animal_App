package it.uniba.dib.sms222315.Friends;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import it.uniba.dib.sms222315.R;


public class Fragment_addNewFriend extends Fragment {
    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    MyFriends receivedFriend;
    Button BT_back , BT_apply;
    TextView PersonName;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_Frag_addNewFriend";

    public Fragment_addNewFriend() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LOAD DATA FROM FRAG MY EXPENSE HOME
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedFriend = bundle.getParcelable("newFriend"); // Key
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragment_add_new_friend, container, false);

        allfind(my_view);

        PersonName.setText(receivedFriend.getNameFriend());

        setAllOnClick();



        // Inflate the layout for this fragment
        return my_view;
    }

    private void setAllOnClick() {
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_MyFriends_Home my_fragment = new Fragment_MyFriends_Home();
                //my_fragment.setMy_callbackFragment(this);
                my_frag_manager = getActivity().getSupportFragmentManager();
                my_frag_trans = my_frag_manager.beginTransaction();
                my_frag_trans.replace(R.id.Frame_Act_MyFriends , my_fragment);
                my_frag_trans.commit();
            }
        });

        BT_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriendIntoDB();
            }
        });
    }

    private void addFriendIntoDB() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();


        db.collection("User Basic Info").document(userID).
                collection("My Friends").document(receivedFriend.getSecretId())
                .set(receivedFriend)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(getContext() , "Congratulation ! \n" +
                                " You have new Friend " , Toast.LENGTH_SHORT).show();

                        Fragment_MyFriends_Home my_fragment = new Fragment_MyFriends_Home();
                        //my_fragment.setMy_callbackFragment(this);
                        my_frag_manager = getActivity().getSupportFragmentManager();
                        my_frag_trans = my_frag_manager.beginTransaction();
                        my_frag_trans.replace(R.id.Frame_Act_MyFriends , my_fragment);
                        my_frag_trans.commit();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void allfind(View my_view) {
        PersonName = my_view.findViewById(R.id.Frag_addNewFriend_name_person);
        BT_apply = my_view.findViewById(R.id.Frag_addNewFriend_BT_Conferme);
        BT_back=my_view.findViewById(R.id.Frag_addNewFriend_BT_Back);
    }
}
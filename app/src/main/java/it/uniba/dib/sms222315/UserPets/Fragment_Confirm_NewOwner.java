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
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import it.uniba.dib.sms222315.Friends.MyFriends;
import it.uniba.dib.sms222315.R;


public class Fragment_Confirm_NewOwner extends Fragment {

    Pets receivedPet;
    MyFriends receivedFriend;
    TextView TV_nameNewOwner , TV_namePet;
    Button BT_back , BT_apply;

    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_Frag_MyPet_Confirm_Owner";

    public Fragment_Confirm_NewOwner() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedPet = bundle.getParcelable("modPets"); // Key
            receivedFriend = bundle.getParcelable("newFriend");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment__conferm__new_owner, container, false);

        Log.d(TAG , "pet : " + receivedPet.getPrv_str_namePets());
        Log.d(TAG , " friend : " +receivedFriend.getNameFriend());

        allfind(my_view);
        setallText();
        setallOnClick();



        return my_view;
    }

    private void setallOnClick() {
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });
        BT_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIntoDB();
            }
        });
    }

    private void changeIntoDB() {
        DocumentReference petRefer = db.collection("Animal DB")
                .document(receivedPet.getPrv_doc_id());

        // Atomically add a new region to the "regions" array field.
        petRefer.update("prv_Str_responsabili", FieldValue.arrayUnion(receivedFriend.getSecretId()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                backHome();
            }
        });
    }

    private void setallText() {
        TV_nameNewOwner.setText(receivedFriend.getNameFriend());
        TV_namePet.setText(receivedPet.getPrv_str_namePets());
    }

    private void allfind(View my_view) {
        TV_nameNewOwner = my_view.findViewById(R.id.TV_MyPets_nameNewOner);
        TV_namePet = my_view.findViewById(R.id.TV_MyPets_namePet);
        BT_apply = my_view.findViewById(R.id.BT_MyPet_ConOwner_APPLY);
        BT_back = my_view.findViewById(R.id.BT_MyPet_ConOwner_BACK);
    }

   private void backHome(){
       Fragment_MyPets_Home my_fragment = new Fragment_MyPets_Home();
       my_frag_manager = getActivity().getSupportFragmentManager();
       my_frag_trans = my_frag_manager.beginTransaction();
       my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment);
       my_frag_trans.commit();
    }
}
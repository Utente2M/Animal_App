package it.uniba.dib.sms222315.UserProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import it.uniba.dib.sms222315.R;


public class Fragment_modifyUserInfo extends Fragment {

EditText dateBorn , phoneNmber;
AutoCompleteTextView address;

    //ISTANCE DB
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_Fragment_modifyUserInfo";

    public Fragment_modifyUserInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragment_modify_user_info, container, false);


        return my_view;
    }
}
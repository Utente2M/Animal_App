package it.uniba.dib.sms222315;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import it.uniba.dib.sms222315.UserExpense.Fragment_MyExpense_Home;

public class Activity_MyFriends extends AppCompatActivity {

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    private static final String TAG = "TAG_Act_MyFriends";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        Log.d(TAG , " ON CREATE ACT MYEXPENSE");


        firstFragment();
    }

    private void firstFragment() {

        Fragment_MyFriends_Home my_fragment = new Fragment_MyFriends_Home();
        //my_fragment.setMy_callbackFragment(this);
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        my_frag_trans.add(R.id.Frame_Act_MyFriends , my_fragment);
        my_frag_trans.commit();
    }
}
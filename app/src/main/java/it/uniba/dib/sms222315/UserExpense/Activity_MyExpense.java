package it.uniba.dib.sms222315.UserExpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserProfile.Fragment_UserProfile;

public class Activity_MyExpense extends AppCompatActivity implements Interf_MyExpense {


    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    private static final String TAG = "TAG_Act_MyExpense";
    private Interf_MyExpense myCallBackFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_expense);
        Log.d(TAG , " ON CREATE ACT MYEXPENSE");


        addFragment();

    }//end on create

    private void addFragment() {

        //primo fragment profile classico
        //con new scegliamo il fragment da istanziare

        Fragment_MyExpense_Home my_fragment = new Fragment_MyExpense_Home();
        my_fragment.setMy_callbackFragment(this);
        //
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();

        my_frag_trans.add(R.id.Frame_Act_MyExpense , my_fragment);
        my_frag_trans.commit();

    }//END ADD FRAGM


    @Override
    public void changeFragment() {

    }
}//END CLASS

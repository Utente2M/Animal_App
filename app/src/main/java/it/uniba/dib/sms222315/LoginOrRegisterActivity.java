package it.uniba.dib.sms222315;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class LoginOrRegisterActivity extends AppCompatActivity implements CallbackFragment {

    //inizialiamo i fragment
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;
    private static final String TAG = "TAG_Act_LogOrRegis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        //carichiamo i due frgment
        addFragment();


    }//END onCreate

    public void addFragment(){
        //con new scegliamo il fragment da istanziare

        Fragment_Login  my_fragment = new Fragment_Login();
        my_fragment.setMy_callbackFragment(this);
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();

        my_frag_trans.add(R.id.FragAutentic , my_fragment);
        my_frag_trans.commit();

    }//END addFrag

    public void replaceFragment(){
        //qui cambia il new rispetto a prima
        my_fragment = new Fragment_Register();
        my_frag_manager = getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        //si aggiunge il richiamo allo stack
        my_frag_trans.addToBackStack(null);
        //add diventa replace
        my_frag_trans.replace(R.id.FragAutentic , my_fragment);
        my_frag_trans.commit();

    }//END addFrag


    @Override
    public void changeFragment() {
        replaceFragment();
    }

    @Override
    public void sendData(String email) {
        Log.d(TAG , "Inside sendData" + email);

    }
}
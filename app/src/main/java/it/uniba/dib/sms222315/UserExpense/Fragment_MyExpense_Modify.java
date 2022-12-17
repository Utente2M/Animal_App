package it.uniba.dib.sms222315.UserExpense;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import it.uniba.dib.sms222315.R;


public class Fragment_MyExpense_Modify extends Fragment {

    //INIZIALIZZIAMO I CONTROLLI PER AGGIUNGERE SPESE
    Spinner SpinCategory; //spin_Category_MyExpense
    String sendModCategory;
    EditText ET_modValue; //ET_decimal_MyExpense
    EditText ET_modDescr; //ET_descr_MyExpense
    Button BT_modExp; //BT_create_MyExpense

    private static final String TAG = "TAG_Frag_MyExpense_MODIFY";
    MyExpense receivedExpense;





    public Fragment_MyExpense_Modify() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //LOAD DATA FROM FRAG MY EXPENSE HOME
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedExpense = bundle.getParcelable("modExpens"); // Key
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Log.d(TAG , "onCreateView ");
        View my_view = inflater.inflate(R.layout.fragment__my_expense__modify , container , false);

        //da eliminare a fine scrittura
        Log.d(TAG, "receivedExpense : " + receivedExpense.getPrv_Category_MyExpense());

        BT_modExp = my_view.findViewById(R.id.BT_MOD_MyExpense);
        BT_modExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "button conferm modify");
            }
        });




        return my_view;
    }
}
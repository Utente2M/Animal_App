package it.uniba.dib.sms222315.UserExpense;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserPets.Pets;

public class Fragment_MyExpense_Home extends Fragment implements AdapterView.OnItemSelectedListener {

    private Interf_MyExpense myCallBackFrag;
    ListView mListView;
    ArrayList<MyExpense> expensesList = new ArrayList<>();


    //INIZIALIZZIAMO I CONTROLLI PER AGGIUNGERE SPESE
    Spinner SpinCategory; //spin_Category_MyExpense
    String sendNewCategory;
    EditText ET_newValue; //ET_decimal_MyExpense
    EditText ET_newDescr; //ET_descr_MyExpense
    Button BT_createExp; //BT_create_MyExpense


    private static final String TAG = "TAG_Frag_MyExpense_Home";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Log.d(TAG , "onCreateView ");
        View my_view = inflater.inflate(R.layout.fragment__my_expense__home , container , false);

        //tutti i find e gli onclick
        mListView = (ListView) my_view.findViewById(R.id.listView_MyExpense);

        popolateList();
        ET_newValue.findViewById(R.id.ET_decimal_MyExpense);
        ET_newDescr.findViewById(R.id.ET_descr_MyExpense);
        //Spinner choose new category
        SpinCategory.findViewById(R.id.spin_Category_MyExpense);

        ArrayAdapter<CharSequence> adapter_spin = ArrayAdapter.createFromResource(getContext(), R.array.Specie_supportate, android.R.layout.simple_spinner_item);
        adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinCategory.setAdapter(adapter_spin);
        SpinCategory.setOnItemSelectedListener(this);


        /*
        Spinner SpinCategory; //spin_Category_MyExpense
    EditText newValue; //ET_decimal_MyExpense
    EditText newDescr; //ET_descr_MyExpense
         */


        BT_createExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sendValue = ET_newValue.getText().toString();
                String sendnewDescr = ET_newDescr.getText().toString();

                //TODO qui mi serve data e magari anche ora
                vedere tipo data




                sendNewExpenseToActivity ( sendNewCategory, sendValue, sendnewDescr , sendData );
            }
        });





        return my_view;
    }

    private void popolateList() {

        expensesList.clear();

        // caricare da DB

    }

    //potenziale errore
    public void setMy_callbackFragment (Interf_MyExpense my_callFrag ){
        this.myCallBackFrag = my_callFrag;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sendNewCategory = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}//END CLASS

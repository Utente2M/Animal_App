package it.uniba.dib.sms222315.UserExpense;

import static java.text.DateFormat.getDateTimeInstance;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import it.uniba.dib.sms222315.R;

public class Fragment_MyExpense_Home extends Fragment implements AdapterView.OnItemSelectedListener  {


    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    //Controlli ListView
    Interf_MyExpense myCallBackFrag;
    ArrayList<MyExpense> expensesList = new ArrayList<>();
    ListView mListView;
    MyExpenseListAdapter adapter;


    //INIZIALIZZIAMO I CONTROLLI PER AGGIUNGERE SPESE
    Spinner SpinCategory; //spin_Category_MyExpense
    String sendNewCategory;
    EditText ET_newValue; //ET_decimal_MyExpense
    EditText ET_newDescr; //ET_descr_MyExpense
    Button BT_createExp; //BT_create_MyExpense

    //Controlli per totale
    float fl_TotalExpense;
    TextView TV_totalExpense;

    //CONTROL FOR FILTER
    EditText textFilter; //ET_FILTER_MyExpense
    ArrayList<MyExpense> filterdList = new ArrayList<>();

    /*
    //DA spostare nella registrazione per la data
    int new_day,new_month,new_year;
     */


    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;


    private static final String TAG = "TAG_Frag_MyExpense_Home";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {





        // Inflate the layout for this fragment
        Log.d(TAG , "onCreateView ");
        View my_view = inflater.inflate(R.layout.fragment__my_expense__home , container , false);


        //tutti i find e gli onclick
        mListView = (ListView) my_view.findViewById(R.id.listView_MyExpense);

        //FIND FOR FUNCTION CREATE
        ET_newValue = my_view.findViewById(R.id.ET_decimal_MyExpense);
        ET_newDescr= my_view.findViewById(R.id.ET_descr_MyExpense);

        //Spinner choose new category
        SpinCategory = my_view.findViewById(R.id.spin_Category_MyExpense);

        ArrayAdapter<CharSequence> adapter_spin = ArrayAdapter.createFromResource(getContext(), R.array.CategorieExpanse, android.R.layout.simple_spinner_item);
        adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinCategory.setAdapter(adapter_spin);
        SpinCategory.setOnItemSelectedListener(this);

        //fUNCTION FOR TOTAL VIEV
        TV_totalExpense = my_view.findViewById(R.id.tV_myExp_total);


        BT_createExp = my_view.findViewById(R.id.BT_create_MyExpense);
        BT_createExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sendValue = ET_newValue.getText().toString();
                String sendnewDescr = ET_newDescr.getText().toString();

                if( sendValue.isEmpty()){
                    ET_newValue.setError("Value is required");
                }else {

                    sendNewExpenseToDB ( sendNewCategory, sendValue, sendnewDescr);
                }
            }
        });





        if (adapter ==null){
            expensesList.clear();
            filterdList.clear();
            popolateList();
            Log.d(TAG , "ok popolateList ");
        }




        //MODIFY ELEMENT FROM LIST
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                MyExpense clickExpense = expensesList.get(position);
                openDetailExpanse(clickExpense);

            }
        });



        //CONTROL FOR FILTER
        textFilter = my_view.findViewById(R.id.ET_FILTER_MyExpense);
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
        return my_view;
    }

    private void filterList(CharSequence charSequence) {



        filterdList.clear();


        Log.d(TAG , "inside Filter ");
        if (charSequence == null || charSequence.toString().isEmpty() ){
            Log.d(TAG , "Filter null  -> popolateList()");
            popolateList();
        }else {


           // String filterText = charSequence.toString();
            Log.d(TAG , "expList size : " +expensesList.size());
            for (int k = 0; k < expensesList.size() ; k++ ){

                MyExpense expense = expensesList.get(k);

                Log.d(TAG , "charseq : " + charSequence);


                if (expense.getPrv_valFloat_MyExpense().contains(charSequence) ||
                        expense.getPrv_Category_MyExpense().contains(charSequence) ||
                        expense.getPrv_Data_MyExpense().contains(charSequence) ||
                        expense.getPrv_Description_MyExpense().contains(charSequence)
                        ) {

                    if (!filterdList.contains(expense)) {
                        filterdList.add(expense);
                        Log.d(TAG, "TRY Filter : " + expense);
                    }
                }
            } //end for

            adapter = new MyExpenseListAdapter(getContext(),
                    R.layout.adapter_my_expense, filterdList);

            mListView.setAdapter(adapter);

        } //end else

    }//end function

    private void openDetailExpanse(MyExpense obj_modifyExpense) {

        my_fragment = new Fragment_MyExpense_Modify();
        my_frag_manager = getActivity().getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        Bundle bundle = new Bundle();
        //this is pass
        bundle.putParcelable("modExpens", obj_modifyExpense);
        my_fragment.setArguments(bundle);
        //si aggiunge il richiamo allo stack
        my_frag_trans.addToBackStack(null);
        //add diventa replace
        my_frag_trans.replace(R.id.Frame_Act_MyExpense , my_fragment );
        my_frag_trans.commit();
    }


    private void sendNewExpenseToDB(String sendNewCategory, String sendValue, String sendnewDescr) {
        Log.d(TAG, "Try create expense into DB");
        //get currunt date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("d,MM,yyyy,HH:mm:ss");
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        String formatData = format.format(calendar.getTime());
        Log.d(TAG , "Format Data : " +formatData);
        //formatData is the new ID document for single expanse

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);

        MyExpense NewExpense = new MyExpense(currentDate ,
                sendNewCategory , sendValue , sendnewDescr, formatData);


        //PROVA DI CREAZIONE SUBCOLLECTION

        db.collection("User Basic Info").document(userID).
                collection("My Expense").document()
                .set(NewExpense)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        popolateList();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /*
    private void showDataPickerDialog (){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity().getApplicationContext() ,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day_ofYear) {
        String Str_Date = "month/day/year"  + day_ofYear + "/" + month + "/" + year ;

        new_day=day_ofYear;
        new_month = month;
        new_year = year;

    }

     */

    private void popolateList() {

        Log.d(TAG , "inside popolate ");


        expensesList.clear();


        fl_TotalExpense = 0;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();


        // caricare da DB

        Query expenseRef = db.collection("User Basic Info").document(userID)
               .collection("My Expense")
                .orderBy("prv_CreatAt_Time", Query.Direction.DESCENDING);

        expenseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        MyExpense oneExpense = document.toObject(MyExpense.class);

                        String newValue = oneExpense.getPrv_valFloat_MyExpense();
                        float fl_newValue = Float.parseFloat(newValue);
                        expensesList.add(oneExpense);
                        fl_TotalExpense = fl_TotalExpense + fl_newValue;

                    }//end for

                    Log.d(TAG , "total float : " + Float.toString(fl_TotalExpense));

                    TV_totalExpense.setText( Float.toString(fl_TotalExpense) );


                    adapter = new MyExpenseListAdapter(getContext(),
                            R.layout.adapter_my_expense, expensesList);

                    mListView.setAdapter(adapter);


                }//end if
                else {
                    //nessuna spesa da mostrare
                }//fine else

            }
        });//END Listner
    }

    //potenziale errore
    public void setMy_callbackFragment (Interf_MyExpense my_callFrag ){
        this.myCallBackFrag = my_callFrag;
    }

    /// INIZIO OVERRIDE SPINNER
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sendNewCategory = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // FINE OVERRIDE SPINNER

}//END CLASS

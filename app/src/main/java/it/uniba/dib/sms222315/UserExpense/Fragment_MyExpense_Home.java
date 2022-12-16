package it.uniba.dib.sms222315.UserExpense;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserPets.MyPetsListAdapter;
import it.uniba.dib.sms222315.UserPets.Pets;

public class Fragment_MyExpense_Home extends Fragment implements AdapterView.OnItemSelectedListener  {

    Interf_MyExpense myCallBackFrag;
    ListView mListView;
    ArrayList<MyExpense> expensesList = new ArrayList<>();


    //INIZIALIZZIAMO I CONTROLLI PER AGGIUNGERE SPESE
    Spinner SpinCategory; //spin_Category_MyExpense
    String sendNewCategory;
    EditText ET_newValue; //ET_decimal_MyExpense
    EditText ET_newDescr; //ET_descr_MyExpense
    Button BT_createExp; //BT_create_MyExpense

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

        Log.d(TAG , "Try popolateList ");
        popolateList();
        Log.d(TAG , "ok popolateList ");


        ET_newValue = my_view.findViewById(R.id.ET_decimal_MyExpense);
        Log.d(TAG , "1 ");
        ET_newDescr= my_view.findViewById(R.id.ET_descr_MyExpense);
        Log.d(TAG , "2 ");

        //Spinner choose new category
        SpinCategory = my_view.findViewById(R.id.spin_Category_MyExpense);
        Log.d(TAG , "3");

        ArrayAdapter<CharSequence> adapter_spin = ArrayAdapter.createFromResource(getContext(), R.array.CategorieExpanse, android.R.layout.simple_spinner_item);
        adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinCategory.setAdapter(adapter_spin);
        SpinCategory.setOnItemSelectedListener(this);

        Log.d(TAG , "4 ");

        BT_createExp = my_view.findViewById(R.id.BT_create_MyExpense);
        BT_createExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sendValue = ET_newValue.getText().toString();
                String sendnewDescr = ET_newDescr.getText().toString();

                sendNewExpenseToActivity ( sendNewCategory, sendValue, sendnewDescr);
            }
        });

        return my_view;
    }

    private void sendNewExpenseToActivity(String sendNewCategory, String sendValue, String sendnewDescr) {

        Log.d(TAG, "Try create expense into DB");
        //get currunt date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);




        Log.d(TAG, "ok change in float");

        MyExpense NewExpense = new MyExpense(currentDate ,
                sendNewCategory , sendValue , sendnewDescr);


        //PROVA DI CREAZIONE SUBCOLLECTION

        db.collection("User Basic Info").document(userID).collection("My Expense")
                .add(NewExpense)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
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

        expensesList.clear();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);

        // caricare da DB

        CollectionReference expenseRef = db.collection("User Basic Info").document(userID).
                collection("My Expense");

        expenseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                        expensesList.add(document.toObject(MyExpense.class));


                    }//end for

                    Log.d(TAG , "end for");
                    MyExpenseListAdapter adapter = new MyExpenseListAdapter(getContext(),
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

package it.uniba.dib.sms222315.UserExpense;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import it.uniba.dib.sms222315.R;


public class Fragment_MyExpense_Modify extends Fragment implements AdapterView.OnItemSelectedListener {

    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //CONTROL FOR MODIFY EXPENSE
    Spinner SpinCategory;
    String sendModCategory;
    EditText ET_modValue;
    EditText ET_modDescr;
    Button BT_modExp;
    Button BT_delExp;


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

        //Spinner choose new category
        SpinCategory = my_view.findViewById(R.id.spin_MOD_MyExpense);
        ArrayAdapter<CharSequence> adapter_spin = ArrayAdapter.createFromResource(getContext(), R.array.CategorieExpanse, android.R.layout.simple_spinner_item);
        adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinCategory.setAdapter(adapter_spin);
        SpinCategory.setOnItemSelectedListener(this);

        ET_modValue = my_view.findViewById(R.id.ET_MOD_decimal_MyExpense);
        ET_modValue.setText(receivedExpense.getPrv_valFloat_MyExpense());

        ET_modDescr = my_view.findViewById(R.id.ET_MOD_descrMyExpense);
        ET_modDescr.setText(receivedExpense.getPrv_Description_MyExpense());



        BT_modExp = my_view.findViewById(R.id.BT_MOD_MyExpense);
        BT_modExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newValue = ET_modValue.getText().toString();
                String newDescr = ET_modDescr.getText().toString();

                if( newValue.isEmpty()){
                    ET_modValue.setError("Value is required");
                }else {

                    modifyExpenseIntoDB ( sendModCategory, newValue, newDescr);
                }
            }
        });//end LISTENER

        BT_delExp = my_view.findViewById(R.id.BT_DEL_MyExpanse);
        BT_delExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteIntoDB();
            }
        });//end LISTENER

        return my_view;
    }//END onCreateView

    private void deleteIntoDB() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        Query expenseRef = db.collection("User Basic Info").document(userID)
                .collection("My Expense")
                .whereEqualTo("prv_CreatAt_Time" , receivedExpense.getPrv_CreatAt_Time() );



       expenseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               for (QueryDocumentSnapshot document : task.getResult()) {
                   Log.d(TAG, document.getId() + " => " + document.getData());

                   db.collection("User Basic Info").document(userID)
                           .collection("My Expense").document(document.getId())
                           .delete()
                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                   getActivity().onBackPressed();
                               }
                           })
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {

                                   Log.w(TAG, "Error deleting document", e);
                                   Toast myToast = Toast.makeText(getContext(), "Error deleting expanse", Toast.LENGTH_LONG);
                                   myToast.show();
                               }
                           });



               }//end for
           }
       });

    }

    private void modifyExpenseIntoDB(String sendModCategory, String newValue, String newDescr) {
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

        //search correct document id

        Query expenseRef = db.collection("User Basic Info").document(userID)
                .collection("My Expense")
                .whereEqualTo("prv_CreatAt_Time" , receivedExpense.getPrv_CreatAt_Time() );

        expenseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());

                    db.collection("User Basic Info").document(userID).
                            collection("My Expense").document(document.getId())
                            .update(
                                    "prv_Category_MyExpense" , sendModCategory,
                                    "prv_Data_MyExpense" , currentDate ,
                                    "prv_Description_MyExpense" , newDescr ,
                                    "prv_valFloat_MyExpense" , newValue );

                }//end for
                getActivity().onBackPressed();

            }
        });//END LISTENER


    }//END FUNCTION


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sendModCategory = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
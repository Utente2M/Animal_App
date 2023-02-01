package it.uniba.dib.sms222315.Enti;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.Reporting.MyPostListAdapter;
import it.uniba.dib.sms222315.Reporting.Report;
import it.uniba.dib.sms222315.UserPets.MyPetsListAdapter;
import it.uniba.dib.sms222315.UserPets.Pets;
import it.uniba.dib.sms222315.UserProfile.User_Class;


public class Fragment_Enti_YourAssociations extends Fragment {

    //Control ListView
    ArrayList<Associations> originalList = new ArrayList<>();
    ListView mListView;
    MyAssociationAdapter adapter;
    //CONTROL FOR FILTER
    EditText textFilter;
    ArrayList<Associations> filteredList = new ArrayList<>();


    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_Fragment_Report_MyReport";

    public Fragment_Enti_YourAssociations() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment__enti__your_associations, container, false);
        // Inflate the layout for this fragment


        //tutti i find e gli onclick
        allFind(my_view);
        setupFilter(my_view);
        //allOnClick();

        if (adapter ==null){
            originalList.clear();
            //filteredList.clear();
            popolateList();
            Log.d(TAG , "ok popolateList ");
        }

        return my_view;
    }

    private void popolateList() {
        originalList.clear();
        Log.d(TAG , "inside popolate ");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        User_Class myDataUser = new User_Class();
        Log.d(TAG , " User id : "+myDataUser.getPrv_str_UID());

        CollectionReference entiRef = db.collection("Association");
        Query myGroup = entiRef.whereArrayContains("prv_Str_member" , userID);


        myGroup.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        Associations myGroup = document.toObject(Associations.class);
                        myGroup.setPrv_doc_id(document.getId());
                        originalList.add(myGroup);
                        Log.d(TAG , "uid doc : " + myGroup.getPrv_associationUID());

                    }//end for

                     adapter = new MyAssociationAdapter(getContext(),
                            R.layout.adapter_association, originalList);

                    mListView.setAdapter(adapter);


                }//end if
                else {
                    //nessun animale da mostrare
                }//fine else

            }//end on complete

        }); //end Listners
    }

    private void setupFilter(View my_view) {
        //CONTROL FOR FILTER
        textFilter = my_view.findViewById(R.id.ET_YourAssociations_myAssociations);
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
    }

    private void filterList(CharSequence charSequence) {

        filteredList.clear();

        Log.d(TAG , "inside Filter ");
        if (charSequence == null || charSequence.toString().isEmpty() ){
            Log.d(TAG , "Filter null  -> popolateList()");
            popolateList();
        }else {


            for (int k = 0; k < originalList.size() ; k++ ){

                Associations oneGroup = originalList.get(k);

                Log.d(TAG , "charseq : " + charSequence);

                if (oneGroup.getPrv_associationName().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT)) ||
                        oneGroup.getPrv_description().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT)) ||
                        oneGroup.getPrv_address().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT))) {

                    if (!filteredList.contains(oneGroup)) {
                        filteredList.add(oneGroup);
                    }
                }
            } //end for

            adapter = new MyAssociationAdapter(getContext(),
                    R.layout.adapter_association, filteredList);


            mListView.setAdapter(adapter);

        } //end else
    }

    private void allFind(View my_view) {
        mListView = my_view.findViewById(R.id.LV_YourAssociations_myAssociations);
    }
}
package it.uniba.dib.sms222315.Reporting;

import android.content.Context;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

import it.uniba.dib.sms222315.Friends.MyFriends;
import it.uniba.dib.sms222315.Friends.MyFriendsListAdapter;
import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserProfile.User_Class;


public class Fragment_Report_MyReport extends Fragment {

    //Control ListView
    ArrayList<Report> originalList = new ArrayList<>();
    ListView mListView;
    MyPostListAdapter adapter;
    //CONTROL FOR FILTER
    EditText textFilter;
    ArrayList<Report> filteredList = new ArrayList<>();


    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_Fragment_Report_MyReport";

    public Fragment_Report_MyReport() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        originalList.clear();
        filteredList.clear();
        popolateList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment__report__my_report, container, false);

        //tutti i find e gli onclick
        allFind(my_view);
        setupFilter(my_view);
        //allOnClick();

        if (adapter ==null){
            originalList.clear();
            filteredList.clear();
            popolateList();
            Log.d(TAG , "ok popolateList ");
        }

        return my_view;
    }



    private void allFind(View my_view) {
        mListView = my_view.findViewById(R.id.LV_MyRepor_myPost);
    }

    private void setupFilter(View my_view) {
        //CONTROL FOR FILTER
        textFilter = my_view.findViewById(R.id.ET_MyRepor_myPost);
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

    private void popolateList() {
        originalList.clear();
        Log.d(TAG , "inside popolate ");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        User_Class myDataUser = new User_Class();
        Log.d(TAG , " User id : "+myDataUser.getPrv_str_UID());

        Query postRef = db.collection("Post")
                .whereEqualTo("prv_authorID" , myDataUser.getPrv_str_UID() );
        postRef.orderBy("createAtTime", Query.Direction.DESCENDING);


        postRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Report onePost = document.toObject(Report.class);
                                originalList.add(onePost);


                            }//END FOR
                            adapter = new MyPostListAdapter(getContext(),
                                    R.layout.adapter_report, originalList);


                            mListView.setAdapter(adapter);
                        }else {

                        }
                    }
                });
    }//END POPOLATE

    private void filterList(CharSequence charSequence) {

        filteredList.clear();

        Log.d(TAG , "inside Filter ");
        if (charSequence == null || charSequence.toString().isEmpty() ){
            Log.d(TAG , "Filter null  -> popolateList()");
            popolateList();
        }else {


            for (int k = 0; k < originalList.size() ; k++ ){

                Report onePost = originalList.get(k);

                Log.d(TAG , "charseq : " + charSequence);

                if (onePost.getPrv_authorName().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT)) ||
                        onePost.getPrv_description().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT)) ||
                        onePost.getPrv_category().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT))) {

                    if (!filteredList.contains(onePost)) {
                        filteredList.add(onePost);
                    }
                }
            } //end for

            adapter = new MyPostListAdapter(getContext(),
                    R.layout.adapter_report, filteredList);


            mListView.setAdapter(adapter);

        } //end else
    }

}//END FRAGMENT
package it.uniba.dib.sms222315.UserExpense;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.dib.sms222315.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_MyExpense_Modify#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_MyExpense_Modify extends Fragment {


    private static final String TAG = "TAG_Frag_MyExpense_MODIFY";
    MyExpense receivedExpense;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_MyExpense_Modify() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_MyExpense_Modify.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_MyExpense_Modify newInstance(String param1, String param2) {
        Fragment_MyExpense_Modify fragment = new Fragment_MyExpense_Modify();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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


        Log.d(TAG, "receivedExpense : " + receivedExpense.getPrv_Category_MyExpense());

        return my_view;
    }
}
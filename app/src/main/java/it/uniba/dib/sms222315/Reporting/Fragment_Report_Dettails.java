package it.uniba.dib.sms222315.Reporting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserPets.Pets;

public class Fragment_Report_Dettails extends Fragment {

    private static final String TAG = "TAG_Fragment_Report_Dettails";
    Report receivedReport;

    public Fragment_Report_Dettails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LOAD DATA FROM FRAG MY EXPENSE HOME
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedReport = bundle.getParcelable("clickRepo"); // Key
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment__report__dettails, container, false);


        Log.d(TAG, " oggetto ricevuto : " +receivedReport.getPrv_secretDocID());
        Log.d(TAG, " oggetto ricevuto : " +receivedReport.getPrv_authorName());

        return my_view;
    }
}
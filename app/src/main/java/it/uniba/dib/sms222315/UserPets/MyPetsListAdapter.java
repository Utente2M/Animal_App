package it.uniba.dib.sms222315.UserPets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222315.TestListView.Person;


public class MyPetsListAdapter extends ArrayAdapter<Pets> {

    private static final String TAG = "TAG_MyPetsAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView razza;
        TextView sex;
    }


    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public MyPetsListAdapter(Context context, int resource, ArrayList<Pets> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // todo sono arrivato qua, vanno inserite le parti modificate


        return convertView;
    }
}

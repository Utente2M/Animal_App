package it.uniba.dib.sms222315.UserExpense;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;


public class MyExpenseListAdapter extends ArrayAdapter<MyExpense> {


    private static final String TAG = "TAG_MyExpenseAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView single_expanse;
        TextView CategoriaText;
        TextView descrizione;
        TextView Data;
        ImageView image; //forse rimane

    }


    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public MyExpenseListAdapter(Context context, int resource, ArrayList<MyExpense> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG , " in getView adapter");


        String CatText = getItem(position).getPrv_Category_MyExpense();
        String DataExp = getItem(position).getPrv_Data_MyExpense();
        String DescrExp = getItem(position).getPrv_Description_MyExpense();
        String SingExp = getItem(position).getPrv_valFloat_MyExpense();

        Log.d(TAG , "ok string");




        //Create the expense object with the information
        MyExpense ExpenseObj = new MyExpense(DataExp, CatText,
                SingExp, DescrExp );

        Log.d(TAG , " ok constructor");


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        MyExpenseListAdapter.ViewHolder holder;


        if(convertView == null){
            Log.d(TAG , " converView is NULL");
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new MyExpenseListAdapter.ViewHolder();
            Log.d(TAG , " inside null 1");

            holder.single_expanse = (TextView) convertView.findViewById(R.id.tV_ListV_myExpense_value);
            holder.CategoriaText = (TextView) convertView.findViewById(R.id.tV_ListV_myExpense_CategText);
            holder.Data = (TextView) convertView.findViewById(R.id.tV_ListV_myExpense_Data);
            holder.descrizione = (TextView) convertView.findViewById(R.id.tV_ListV_myExpense_Descri);
            holder.image = (ImageView) convertView.findViewById(R.id.image_Category_myExpense);

            Log.d(TAG , " inside null 2");

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            Log.d(TAG , " Convert view not null");
            holder = (MyExpenseListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        Log.d(TAG, "ok animation");


        holder.single_expanse.setText(ExpenseObj.getPrv_valFloat_MyExpense());
        holder.CategoriaText.setText(ExpenseObj.getPrv_Category_MyExpense());
        holder.Data.setText(ExpenseObj.getPrv_Data_MyExpense());
        holder.descrizione.setText("ExpenseObj.getPrv_Description_MyExpense()");

        Log.d(TAG , " ok holder set");

       //TODO QUA VANNO PASSATE LE STRINGHE DELLO SPINNER
        if (CatText.equals("Generale")){
            holder.image.setImageResource(R.drawable.icon_rabbit);

        }else if (CatText.equals("Cure")){
            holder.image.setImageResource(R.drawable.icon_petcare);

        }else if (CatText.equals("Cibo")){
            holder.image.setImageResource(R.drawable.icon_petfood);
        }else if (CatText.equals("Veterinario")) {
            holder.image.setImageResource(R.drawable.icon_veterinaria);
        }else if (CatText.equals("Toilettatura")){
            holder.image.setImageResource(R.drawable.icon_toilettatura);
        }else{
            Log.d(TAG , "Error Category");
        }

        Log.d(TAG , "ok if");


        return convertView;
    }
}//END CLASS

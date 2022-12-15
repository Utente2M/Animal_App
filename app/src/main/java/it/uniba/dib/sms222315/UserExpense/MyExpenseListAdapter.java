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


    private static final String TAG = "TAG_MyPetsAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView single_expanse;
        TextView CategoriaText;
        TextView Data;
        TextView Descrizione;
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

        // todo sono arrivato qua, vanno inserite le parti modificate
//get the pets information


        String CatText = getItem(position).getPrv_Category_MyExpense();
        String DataExp = getItem(position).getPrv_Data_MyExpense();
        String DescrExp = getItem(position).getPrv_Description_MyExpense();
        Float SingExp = getItem(position).getPrv_valFloat_MyExpense();
        // TODO QUA VA CARICATO DALLA CLASSE L'URI

        //Create the expense object with the information
        MyExpense ExpenseView = new MyExpense(DataExp, CatText,
                SingExp, DescrExp );


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        MyExpenseListAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new MyExpenseListAdapter.ViewHolder();


            holder.single_expanse = (TextView) convertView.findViewById(R.id.tV_ListV_myExpense_value);
            holder.CategoriaText = (TextView) convertView.findViewById(R.id.tV_ListV_myExpense_CategText);
            holder.Data = (TextView) convertView.findViewById(R.id.tV_ListV_myExpense_Data);
            holder.Descrizione =(TextView) convertView.findViewById(R.id.tV_ListV_myExpense_Descri);
            holder.image =(ImageView) convertView.findViewById(R.id.image_Category_myExpense);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (MyExpenseListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.single_expanse.setText(ExpenseView.getPrv_valFloat_MyExpense().toString());
        holder.CategoriaText.setText(ExpenseView.getPrv_Category_MyExpense());
        holder.Data.setText(ExpenseView.getPrv_Data_MyExpense());
        holder.Descrizione.setText(ExpenseView.getPrv_Description_MyExpense());


       //TODO QUA VANNO PASSATE LE STRINGHE DELLO SPINNER
        if (DescrExp.equals("Generale")){
            holder.image.setImageResource(R.drawable.icon_dog);

        }else if (DescrExp.equals("Cure")){
            holder.image.setImageResource(R.drawable.icon_petcare);

        }else if (DescrExp.equals("Cibo")){
            holder.image.setImageResource(R.drawable.icon_petfood);
        }else if (DescrExp.equals("Veterinario")) {
            holder.image.setImageResource(R.drawable.icon_veterinaria);
        }else if (DescrExp.equals("Toilettatura")){
            holder.image.setImageResource(R.drawable.icon_toilettatura);
        }else{
            Log.d(TAG , "Error Category");
        }


        return convertView;
    }
}//END CLASS

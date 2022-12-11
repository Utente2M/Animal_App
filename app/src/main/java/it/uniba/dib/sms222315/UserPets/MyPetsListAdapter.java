package it.uniba.dib.sms222315.UserPets;

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
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.TestListView.Person;
import it.uniba.dib.sms222315.TestListView.PersonListAdapter;


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
        TextView specie;
        ImageView image;
        //TODO DA IMPLEMENTARE CON IMMAGINI DA DB
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
//get the pets information
        String name = getItem(position).getPrv_str_namePets();
        String razza = getItem(position).getPrv_Razza();
        String sex = getItem(position).getPrv_sex();
        String specie = getItem(position).getPrv_specie();
        // TODO QUA VA CARICATO DALLA CLASSE L'URI

        //Create the pets object with the information
        Pets pets = new Pets(name,specie, sex , razza , "", "", "" );


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        MyPetsListAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new MyPetsListAdapter.ViewHolder();
            //cambiare id
            holder.name = (TextView) convertView.findViewById(R.id.tV_ListV_my_pets_nome);
            holder.razza = (TextView) convertView.findViewById(R.id.tV_ListV_my_pets_razza);
            holder.sex = (TextView) convertView.findViewById(R.id.tV_ListV_my_pets_razza);
            holder.specie =(TextView) convertView.findViewById(R.id.tV_ListV_my_pets_specie);
            holder.image =(ImageView) convertView.findViewById(R.id.image_MyPets);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (MyPetsListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(pets.getPrv_str_namePets());
        holder.razza.setText(pets.getPrv_Razza());
        holder.sex.setText(pets.getPrv_sex());

        //controllo immagine da caricare se immagine personale non presente
        //il caricamento da db va creato ancora

        holder.specie.setText(pets.getPrv_specie());
        if (specie.equals("Cane")){
            holder.image.setImageResource(R.drawable.icon_dog);

        }else if (specie.equals("Gatto")){
            holder.image.setImageResource(R.drawable.icon_cat);

        }else if (specie.equals("Coniglio")){
            holder.image.setImageResource(R.drawable.icon_rabbit);
        }else{
            Log.d(TAG , "specie not found");
        }


        return convertView;
    }
}

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

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;

public class VisiteAdapter extends ArrayAdapter<Visite> {


    private static final String TAG = "TAG_VisiteAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView namep;
        TextView data;
        TextView categoria;
        TextView description;
        ImageView imagev;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public VisiteAdapter(Context context, int resource, ArrayList<Visite> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        String name = getItem(position).getPrv_Name();
        String data = getItem(position).getPrv_Data();
        String str_categoria = getItem(position).getPrv_categoria();
        String str_description = getItem(position).getPrv_Description();


        //Create the pets object with the information
        Visite newVisit = new Visite(name, data , str_description, str_categoria);


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        VisiteAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new VisiteAdapter.ViewHolder();
            //cambiare id
            holder.namep = (TextView) convertView.findViewById(R.id.libretto_nome);
            holder.data = (TextView) convertView.findViewById(R.id.libretto_data);
            holder.categoria = (TextView) convertView.findViewById(R.id.libretto_categoria);
            holder.description =(TextView) convertView.findViewById(R.id.libretto_descrizione);
            holder.imagev =(ImageView) convertView.findViewById(R.id.image_MyLib);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (VisiteAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;





        holder.namep.setText(newVisit.getPrv_Name());
        holder.data.setText(newVisit.getPrv_Data());
        holder.categoria.setText(newVisit.getPrv_categoria());
        holder.description.setText(newVisit.getPrv_Description());

        String checkcategoria = newVisit.getPrv_categoria();

        Log.d(TAG, "checkcategoria :" + checkcategoria);
        if (checkcategoria.equals("General")){
            holder.imagev.setImageResource(R.drawable.icon_dog);

        }else if (checkcategoria.equals("Vaccino")){
            holder.imagev.setImageResource(R.drawable.icon_cat);

        }else if (checkcategoria.equals("Visita")){
            holder.imagev.setImageResource(R.drawable.icon_rabbit);
        }else if (checkcategoria.equals("Operazione")){
                holder.imagev.setImageResource(R.drawable.icon_rabbit);
            }else{
            Log.d(TAG , "specie not found");
        }


        return convertView;
    }


}

package it.uniba.dib.sms222315.MeetPets;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import java.io.InputStream;
import java.util.ArrayList;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.Reporting.MyPostListAdapter;
import it.uniba.dib.sms222315.UserPets.MyPetsListAdapter;
import it.uniba.dib.sms222315.UserPets.Pets;

public class meetAdapter extends ArrayAdapter<Pets> {

    private static final String TAG = "TAG_meetAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView specie;
        ImageView image;

    }


    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public meetAdapter(Context context, int resource, ArrayList<Pets> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


//get the pets information
        String name = getItem(position).getPrv_str_namePets();
        String specie = getItem(position).getPrv_specie();
        String linkimage = getItem(position).getLinkPhotoPets();


        //Create the pets object with the information
        Pets pets = new Pets(name,specie , linkimage );


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        meetAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new meetAdapter.ViewHolder();
            //cambiare id
            holder.name = (TextView) convertView.findViewById(R.id.TV_name_meetPet);
            holder.specie =(TextView) convertView.findViewById(R.id.TV_specie_meetPet);
            holder.image =(ImageView) convertView.findViewById(R.id.IV_meetPet);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (meetAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(pets.getPrv_str_namePets());
        holder.specie.setText(pets.getPrv_specie());


        Log.d(TAG, "image: " +pets.getLinkPhotoPets());
        if (pets.getLinkPhotoPets().isEmpty() ){
            Log.d(TAG, " no profile image");


            if (specie.equals("Cane")){
                holder.image.setImageResource(R.drawable.icon_dog);

            }else if (specie.equals("Gatto")){
                holder.image.setImageResource(R.drawable.icon_cat);

            }else if (specie.equals("Coniglio")){
                holder.image.setImageResource(R.drawable.icon_rabbit);
            }else{
                Log.d(TAG , "specie not found");
            }
        }
        else{
            new DownloadImageFromInternet((ImageView)
                    convertView.findViewById(R.id.IV_meetPet))
                    .execute(pets.getLinkPhotoPets());
        }
        return convertView;
    }


    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;

        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}

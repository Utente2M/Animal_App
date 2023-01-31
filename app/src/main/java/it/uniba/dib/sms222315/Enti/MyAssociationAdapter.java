package it.uniba.dib.sms222315.Enti;

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

public class MyAssociationAdapter extends ArrayAdapter<Associations> {

    private static final String TAG = "TAG_AssociationAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;


    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView address;
        TextView phone;
        ImageView image;

    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public MyAssociationAdapter(Context context, int resource,
                                ArrayList<Associations> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        String str_name = getItem(position).getPrv_associationName();
        String str_address = getItem(position).getPrv_address();
        String str_phone = getItem(position).getPrv_phone();
        String str_linkimage = getItem(position).getPrv_associationLogo();


        //Create the pets object with the information
        Associations group = new Associations(str_name,str_address,
                str_phone , str_linkimage );


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        MyAssociationAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new MyAssociationAdapter.ViewHolder();


            holder.name = (TextView) convertView.findViewById(R.id.Adap_Assoc_Name);
            holder.address = (TextView) convertView.findViewById(R.id.Adap_Assoc_street);
            holder.phone = (TextView) convertView.findViewById(R.id.Adap_Assoc_Phone);
            holder.image =(ImageView) convertView.findViewById(R.id.Adap_Assoc_image);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (MyAssociationAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(group.getPrv_associationName());
        holder.address.setText(group.getPrv_address());
        holder.phone.setText(group.getPrv_phone());

        if (group.getPrv_associationLogo()==(null) ){
            Log.d(TAG, " no profile image");
            holder.image.setImageResource(R.drawable.star);
        }
        else{
            new DownloadImageFromInternet((ImageView)
                    convertView.findViewById(R.id.Adap_Assoc_image))
                    .execute(group.getPrv_associationLogo());
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






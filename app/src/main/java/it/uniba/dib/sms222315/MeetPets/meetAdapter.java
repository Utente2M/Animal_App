package it.uniba.dib.sms222315.MeetPets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.Reporting.MyPostListAdapter;

public class meetAdapter extends BaseAdapter   {

    Context mContext;
    String namePet;
    String razzaPet;
    String imagePet;

    LayoutInflater inflater;

    public meetAdapter (Context context , String name, String razza, String image ){
        mContext = context;
        namePet = name;
        razzaPet = razza;
        imagePet = image;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if(inflater == null){
            inflater =(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView ==null){
            convertView = inflater.inflate(R.layout.grid_pets_meet , null);

        }

        ImageView myImageView = convertView.findViewById(R.id.IV_meetPet);
        TextView nameText = convertView.findViewById(R.id.TV_name_meetPet);
        TextView razzaText = convertView.findViewById(R.id.TV_razza_meetPet);


        nameText.setText(namePet[position]);
        //myImageView.set
        imagePet
        if (reportObj.getPrv_linkImg()==(null) ){
            Log.d(TAG, " no profile image");
            holder.image.setImageResource(R.drawable.star);
        }
        else{
            new MyPostListAdapter.DownloadImageFromInternet((ImageView)
                    convertView.findViewById(R.id.Adap_Repo_image))
                    .execute(reportObj.getPrv_linkImg());
        }



        return null;
    }
}

package it.uniba.dib.sms222315;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MenuBar_FullUser extends Fragment {



    public MenuBar_FullUser() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment_menu_bar__full_user, container, false);

        DrawerLayout drawerLayout = my_view.findViewById(R.id.drawerLayout);

        my_view.findViewById(R.id.ImageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });



        // Inflate the layout for this fragment
        return my_view;
    }
}
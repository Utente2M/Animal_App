package it.uniba.dib.sms222315;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class Fragment_Login extends Fragment {

    Button button_login , button_register ;
    EditText ET_username , ET_password ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragm_login, container, false);

        ET_username = my_view.findViewById(R.id.et_username_login);
        ET_password = my_view.findViewById(R.id.et_password_login);
        button_login = my_view.findViewById(R.id.btn_login);
        button_register = my_view.findViewById(R.id.btn_register);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });



        return my_view;
    }
}//end class

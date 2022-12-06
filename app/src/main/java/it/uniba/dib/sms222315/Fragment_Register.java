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

public class Fragment_Register extends Fragment {

    Button button_login , button_register ;
    EditText ET_username , ET_password , ET_email  ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //cambiamo il layout
        View my_view = inflater.inflate(R.layout.fragm_register, container, false);

        ET_username = my_view.findViewById(R.id.et_username_login);
        ET_password = my_view.findViewById(R.id.et_password_login);
        ET_email = my_view.findViewById(R.id.et_mail_login);

        button_register = my_view.findViewById(R.id.btn_register);



        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });



        return my_view;
    }
}//end class

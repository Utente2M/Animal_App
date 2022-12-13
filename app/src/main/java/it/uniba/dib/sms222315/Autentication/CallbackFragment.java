package it.uniba.dib.sms222315.Autentication;

import android.content.Context;
import android.view.View;

public interface CallbackFragment {



    void changeFragment();
    void startAutocompleteActivity ();
    void createUSerWithMailPassword(String email , String password);
    void logAccountWithMailePass (String email , String password);
    void addInformationToProfile (String name);

    void addBasicInfoToUser(String phone, String address, String dateBorn);
}

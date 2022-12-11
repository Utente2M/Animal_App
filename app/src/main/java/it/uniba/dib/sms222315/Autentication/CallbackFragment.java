package it.uniba.dib.sms222315.Autentication;

import android.content.Context;
import android.view.View;

public interface CallbackFragment {



    void changeFragment();
    void startAutocompleteActivity (View view, Context myContext);
    void createUSerWithMailPassword(String email , String password);
    void logAccountWithMailePass (String email , String password);
    void addInformationToProfile (String name);

}

package it.uniba.dib.sms222315;

public interface CallbackFragment {



    void changeFragment();
    void createUSerWithMailPassword(String email , String password);
    void logAccountWithMailePass (String email , String password);

}

package it.uniba.dib.sms222315.UserExpense;

public class MyExpense {

    //data type
    private String prv_Data_MyExpense;

    private String prv_Category_MyExpense;
    private float prv_valFloat_MyExpense;
    private String prv_Description_MyExpense;



    public MyExpense(String prv_data_myExpense, String prv_category_myExpense,
                     float prv_valFloat_myExpense, String prv_description_myExpense) {
        prv_Data_MyExpense = prv_data_myExpense;
        prv_Category_MyExpense = prv_category_myExpense;
        prv_valFloat_MyExpense = prv_valFloat_myExpense;
        prv_Description_MyExpense = prv_description_myExpense;
    }



    public MyExpense (){}


    public String getPrv_Data_MyExpense() {
        return prv_Data_MyExpense;
    }

    public String getPrv_Category_MyExpense() {
        return prv_Category_MyExpense;
    }

    public Float getPrv_valFloat_MyExpense() {
        return prv_valFloat_MyExpense;
    }

    public String getPrv_Description_MyExpense() {
        return prv_Description_MyExpense;
    }
}

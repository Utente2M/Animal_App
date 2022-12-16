package it.uniba.dib.sms222315.UserExpense;

public class MyExpense {

    //data type
    private String prv_Data_MyExpense;
    private String prv_Category_MyExpense;
    private String prv_valFloat_MyExpense;
    private String prv_Description_MyExpense;
    private String prv_CreatAt_Time;


    public MyExpense(String data_myExpense, String category_myExpense,
                     String valFloat_myExpense, String description_myExpense , String currentTime) {
        prv_Data_MyExpense = data_myExpense;
        prv_Category_MyExpense = category_myExpense;
        prv_valFloat_MyExpense = valFloat_myExpense;
        prv_Description_MyExpense = description_myExpense;
        prv_CreatAt_Time = currentTime;
    }


//costructor for expenseview
    public MyExpense(String data_myExpense, String category_myExpense,
                     String valFloat_myExpense, String description_myExpense) {
        prv_Data_MyExpense = data_myExpense;
        prv_Category_MyExpense = category_myExpense;
        prv_valFloat_MyExpense = valFloat_myExpense;
        prv_Description_MyExpense = description_myExpense;
    }



    public MyExpense (){}


    public String getPrv_Data_MyExpense() {
        return prv_Data_MyExpense;
    }

    public String getPrv_Category_MyExpense() {
        return prv_Category_MyExpense;
    }

    public String getPrv_valFloat_MyExpense() {
        return prv_valFloat_MyExpense;
    }


    public String getPrv_Description_MyExpense() {
        return prv_Description_MyExpense;
    }

    public String getPrv_CreatAt_Time() {
        return prv_CreatAt_Time;
    }
}

package com.example.smartfinance.Database;


public class PastExpense {
    private  String TransactionID;
    private  String TransactionAmount;
    private  String TransactionCategory;
    private String TransactionDate;
    private String TransactionNotes;

    public PastExpense(){

    }

    public PastExpense( String TransactionID,String TransactionAmount,String TransactionCategory, String TransactionDate, String TransactionNotes) {
        this.TransactionID = TransactionID;
        this.TransactionAmount = TransactionAmount;
        this.TransactionDate = TransactionDate;
        this.TransactionCategory = TransactionCategory;
        this.TransactionNotes = TransactionNotes;

    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }


    public String getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        TransactionAmount = transactionAmount;
    }

    public String getTransactionCategory() {
        return TransactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        TransactionCategory = transactionCategory;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getTransactionNotes() {
        return TransactionNotes;
    }

    public void setTransactionNotes(String transactionNotes) {
        TransactionNotes = transactionNotes;
    }
}

package com.example.smartfinance.Model;

public class TransactionData {

    private String transactionID;
    private double transactionAmount;
    private String transactionCategory;
    private String transactionDate;
    private String Notes;



    public TransactionData() {
    }

    public TransactionData(String transactionID, double transactionAmount, String transactionCategory, String transactionDate, String notes) {
        this.transactionID = transactionID;
        this.transactionAmount = transactionAmount;
        this.transactionCategory = transactionCategory;
        this.transactionDate = transactionDate;
        Notes = notes;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}

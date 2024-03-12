package org.example.model;

public class Transaction {
    public double amount;

    public String comment;

    public boolean request;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public String toString() {
        return "transaction: "+request + " comment: "+comment + " amount: "+amount;
    }
}

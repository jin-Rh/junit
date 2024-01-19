package com.example.unittesting5;

//For Testing with Junit5
public class Account {

    private String firstName;
    private String lastName;
    private double balance;
    private  int accountType;

    public static final int CURRENT = 1;
    public static final int SAVINGS = 2;
    private static double INTEREST_RATE = 0.02;

    public Account(String firstName, String lastName, double balance, int accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.accountType = accountType;
    }

    //branch argument is true if customer is performing a transaction at a branch
    //with a teller false if done on ATM
    public double deposit(double amount, boolean branch){
        balance += amount;
        return balance;
    }

    //branch argument is true if customer is performing a transaction at a branch
    //with a teller false if done on ATM
    public double withdraw(double amount, boolean branch){
        // if not branch and amount is greater than 1500 exception will be thrown
        if((amount > 1500.00) && (!branch)){
            throw new IllegalArgumentException();
        }
        balance-= amount;
        return balance;
    }

    public double calculateInterest(){
        if(getBalance() > 0){
            return getBalance() * INTEREST_RATE;
        }else {
            return 0;
        }

    }

    public double getBalance() {
        return balance;
    }

    public boolean isCurrentAccount(){
        return accountType == CURRENT;
    }


}

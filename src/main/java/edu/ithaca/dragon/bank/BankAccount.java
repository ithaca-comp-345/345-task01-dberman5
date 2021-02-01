package edu.ithaca.dragon.bank;

import java.math.BigDecimal;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid or if amount is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) /*&& isAmountValid(startingBalance*/){
            this.email = email;
            //this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }

        if(isAmountValid(startingBalance)){
            this.balance= startingBalance;
        }
        else{
            throw new IllegalArgumentException("Starting Balance: " + startingBalance + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance. If amount is negative, throws IllegalArgumentException
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(isAmountValid(amount)){
            if (amount <= balance){
                balance -= amount;
            }
            else if(amount > balance){
                throw new InsufficientFundsException("Not enough money");
            }
        }
        else{
            throw new IllegalArgumentException("Amount is invalid");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1 || email.indexOf('.')== -1){
            return false;
        }
        //invalid if string is empty
        else if (email.isEmpty()){
            return false;
        }
        //invalid if '-' is in address. This is low boundary
        else if (email.indexOf('-') != -1){
            return false;
        }
        //invalid if # of characters after last '.' is 2 or less
        else if (email.length() - email.indexOf('.') <= 2){
            return false;
        }
        //invalid if '#' is before '@'
        
        else if (email.indexOf('#') != -1){
            return false;
        }
        //invalid if '..' is found. This could be for any 2 symbols
        else if (email.contains("..") || email.contains("@.")){
            return false;
        }

        else if(email.charAt(email.length() -2) == '.'){
            return false;
        }

        else if(email.indexOf('.') != -1 && email.indexOf('@') > email.lastIndexOf('.')){
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isAmountValid (double amount){
        if(BigDecimal.valueOf(amount).scale() > 2){
            return false;
        }
        else if(amount< 0){
            return false;
        }
        return true;
    }
}

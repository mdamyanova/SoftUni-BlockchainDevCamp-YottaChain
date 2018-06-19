package com.yottachain.models.viewModels;

public class BalanceViewModel {

   private String address;
   private long balance;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBalance() {
        return this.balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
package com.faucet.models.viewModels;

public class TransactionViewModel {
    private double coins;
    private String address;

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

package com.yottachain.entities;

public class Address {

    private String addressId;
    private long amount;

    public Address(String address)
    {
        this.addressId = address;
    }

    public String getAddressId() {
        return addressId;
    }

    public long getAmount() {
        return amount;
    }
}
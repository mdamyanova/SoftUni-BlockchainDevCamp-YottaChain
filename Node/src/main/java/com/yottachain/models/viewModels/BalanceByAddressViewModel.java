package com.yottachain.models.viewModels;

public class BalanceByAddressViewModel {

    private long safeBalance;
    private long confirmedBalance;
    private long pendingBalance;

    public long getSafeBalance() {
        return this.safeBalance;
    }

    public void setSafeBalance(long safeBalance) {
        this.safeBalance = safeBalance;
    }

    public long getConfirmedBalance() {
        return this.confirmedBalance;
    }

    public void setConfirmedBalance(long confirmedBalance) {
        this.confirmedBalance = confirmedBalance;
    }

    public long getPendingBalance() {
        return this.pendingBalance;
    }

    public void setPendingBalance(long pendingBalance) {
        this.pendingBalance = pendingBalance;
    }
}
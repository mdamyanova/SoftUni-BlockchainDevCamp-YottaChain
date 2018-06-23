package com.yottachain.models.bindingModels;

public class MiningJobBindingModel {

    private int index;
    private long transactionsIncluded;
    private int difficulty;
    private long expectedReward;
    private String rewardAddress;
    private String blockDataHash;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getTransactionsIncluded() {
        return this.transactionsIncluded;
    }

    public void setTransactionsIncluded(long transactionsIncluded) {
        this.transactionsIncluded = transactionsIncluded;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public long getExpectedReward() {
        return this.expectedReward;
    }

    public void setExpectedReward(long expectedReward) {
        this.expectedReward = expectedReward;
    }

    public String getRewardAddress() {
        return this.rewardAddress;
    }

    public void setRewardAddress(String rewardAddress) {
        this.rewardAddress = rewardAddress;
    }

    public String getBlockDataHash() {
        return this.blockDataHash;
    }

    public void setBlockDataHash(String blockDataHash) {
        this.blockDataHash = blockDataHash;
    }
}
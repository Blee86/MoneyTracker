package com.developing.yosublee.moneytracker_dev.Tabs.Account;

/**
 * Created by yosublee on 6/26/15.
 */
public class AccountType {
    private long id;
    private String name;
    private boolean isScheduled;

    public AccountType() {}
    /**
     * Constructor for the Account type which doesn't need scheduled payment
     * @param name name of this account
     */
    public AccountType(String name) {
        this.name = name;
        isScheduled = false;

    }

    public String getName() {
        return name;
    }
    public long getId() {
        return this.id;
    }
    public boolean isScheduled() {
        return isScheduled;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScheduled(boolean scheduled) {
        this.isScheduled = scheduled;
    }

}

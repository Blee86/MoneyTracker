package com.developing.yosublee.moneytracker_dev.Tabs.Account;

/**
 * Created by yosublee on 6/25/15.
 */
public class AccountSource {
    private long id;
    private String name;
    private String typeName;
    private long typeId;
    private int balance;
    private long paymentDate;
    private long billingDate;
    private String from;
    private boolean scheduled;

    public AccountSource() {}

    public AccountSource(String name,
                         String type,
                         int balance) {
        this.name = name;
        this.typeName = type;
        this.balance = balance;
        this.paymentDate = 0;
        this.billingDate = 0;
        this.from = null;
        this.scheduled = false;
    }

    public AccountSource(String name,
                         String type,
                         int balance,
                         long paymentDate,
                         long billingDate,
                         String from) {
        this.name = name;
        this.typeName = type;
        this.balance = balance;
        this.scheduled = true;
        this.paymentDate = paymentDate;
        this.billingDate = billingDate;
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getBalance() {
        return balance;
    }

    public long getPaymentDateInUnixTime() {
        return paymentDate;
    }

    public long getBillingDateInUnixTime() {
        return billingDate;
    }

    public String getPaidFrom() {
        return from;
    }

    public long getId() {
        return this.id;
    }

    public long getTypeId() {
        return this.typeId;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setTypeId(long id) {
        this.typeId = id;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setId(long id) {
        this.id = id;
    }
    /**
     * (Card Only) Set the payment source, where it's paid from.
     * @param from target source
     */
    public void setFrom(String from) {
        this.from = from;
    }
    /**
     * (Card Only) Payment Date in Unix time.
     * @param paymentDate new Payment date in unix time
     */
    public void setPaymentDate(long paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * (Card Only) Billing Date in Unix Time.
     * @param billingDate new Billing date in Unix time
     */
    public void setBillingDate(long billingDate) {
        this.billingDate = billingDate;
    }

    public void setPaidFrom(String name) {
        this.from = name;
    }
    public boolean isScheduled() {
        return scheduled;
    }
}

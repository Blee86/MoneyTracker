package com.developing.yosublee.moneytracker_dev.DBAadapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.developing.yosublee.moneytracker_dev.DBAadapter.*;
import com.developing.yosublee.moneytracker_dev.Tabs.Account.AccountSource;
import com.developing.yosublee.moneytracker_dev.Tabs.Account.AccountType;

import java.util.ArrayList;

/**
 * Created by yosublee on 6/25/15.
 */
public class AccountManager extends DBAadapter {
    private static final String TAG = "AccountManager";

    // Tables
    public static final String TABLE_ACCOUNT = "account";
    public static final String TABLE_ACCOUNTTYPE = "accountTypes";

    // Keys (Columns)
    // Account Table
    public static final String KEY_ACCOUNT_ID = "id";
    public static final String KEY_ACCOUNT_NAME = "name";
    public static final String KEY_ACCOUNT_BALANCE = "balance";
    public static final String KEY_ACCOUNT_TYPE = "type";
    public static final String KEY_ACCOUNT_PAYMENTDATE = "paymentDate";
    public static final String KEY_ACCOUNT_PAYFROM = "payFrom";
    public static final String KEY_ACCOUNT_BILLINGDATE = "billingDate";

    // Card Table
    public static final String KEY_ACCOUNT_ACCOUNTTYPE_ID = "id";
    public static final String KEY_ACCOUNT_ACCOUNTTYPE_NAME = "name";
    public static final String KEY_ACCOUNT_ACCOUNTTYPE_SCHEDULEDSETUP = "scheduledSetUp";

    public static final String[] KEY_ACCOUNT_ALL = new String[]{
            KEY_ACCOUNT_ID,
            KEY_ACCOUNT_NAME,
            KEY_ACCOUNT_BALANCE,
            KEY_ACCOUNT_TYPE,
            KEY_ACCOUNT_PAYMENTDATE,
            KEY_ACCOUNT_PAYFROM,
            KEY_ACCOUNT_BILLINGDATE
    };

    public static final String[] KEY_ACCOUNT_ACCOUNTTYPE_ALL = new String[]{
            KEY_ACCOUNT_ACCOUNTTYPE_ID,
            KEY_ACCOUNT_ACCOUNTTYPE_NAME,
            KEY_ACCOUNT_ACCOUNTTYPE_SCHEDULEDSETUP
    };

    public AccountManager(Context context) {
        super(context);
    }

    public long insertAccount(AccountSource source) {
        long result = -1;

        try {
            this.open();

            ContentValues values = new ContentValues();
            values.put(KEY_ACCOUNT_ACCOUNTTYPE_NAME, source.getName());
            values.put(KEY_ACCOUNT_BALANCE, source.getBalance());
            values.put(KEY_ACCOUNT_TYPE, source.getTypeId());
            values.put(KEY_ACCOUNT_PAYMENTDATE, source.getPaymentDateInUnixTime());
            values.put(KEY_ACCOUNT_BILLINGDATE, source.getBillingDateInUnixTime());
            values.put(KEY_ACCOUNT_PAYFROM, source.getPaidFrom());

            result = db.insert(TABLE_ACCOUNT, null, values);
        } catch (Exception e) {
            Log.e(TAG, "InsertAccount Failed: " + e.toString());
        } finally {
            this.close();
            return result;
        }
    }

    /**
     * Get All accounts from DB
     * @return ArrayList of AccountSource
     */
    public ArrayList<AccountSource> getAllAccounts() {
        ArrayList<AccountSource> accountList = null;

        try {
            this.open();

            accountList = new ArrayList<AccountSource>();

            Cursor cursor = db.query(true, TABLE_ACCOUNT, KEY_ACCOUNT_ALL,
                    null, null, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                do {
                    if (cursor.getCount() > 0) {
                        AccountSource account = new AccountSource(cursor.getString(1),
                                cursor.getString(3),
                                Integer.parseInt(cursor.getString(2)),
                                Long.parseLong(cursor.getString(4)),
                                Long.parseLong(cursor.getString(6)),
                                cursor.getString(5));
                        account.setId(Long.parseLong(cursor.getString(0)));
                        accountList.add(account);

                        /*
                        Log.d(TAG,"0 = " + cursor.getString(0));
                        Log.d(TAG,"1 = " + cursor.getString(1));
                        Log.d(TAG,"2 = " + cursor.getString(2));
                        Log.d(TAG,"3 = " + cursor.getString(3));
                        Log.d(TAG,"4 = " + cursor.getString(4));
                        Log.d(TAG,"5 = " + cursor.getString(5));
                        Log.d(TAG,"6 = " + cursor.getString(6));

                        Log.d(TAG, "BALANCE = " + account.getBalance());
                        */
                    }
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getAllAccounts Failed: " + e.toString());
        } finally {
            this.close();
            return accountList;
        }
    }

    /**
     * Get all Account Types
     * @return ArrayList of AccountType
     */
    public ArrayList<AccountType> getAllAccountTypes() {
        ArrayList<AccountType> accountTypeList = null;

        try {
            this.open();

            accountTypeList = new ArrayList<AccountType>();

            Cursor cursor = db.query(true, TABLE_ACCOUNTTYPE, KEY_ACCOUNT_ACCOUNTTYPE_ALL,
                    null, null, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                do {
                    if (cursor.getCount() > 0) {
                        AccountType type = new AccountType(cursor.getString(1));
                        type.setId(Long.parseLong(cursor.getString(0)));

                        if (cursor.getString(2).toLowerCase().matches("yes")) {
                            type.setScheduled(true);
                        }
                        else {
                            type.setScheduled(false);
                        }

                        accountTypeList.add(type);
                    }
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getAllAccountTypes Failed: " + e.toString());
        } finally {
            this.close();
            return accountTypeList;
        }
    }

    /**
     * Get Account Type Id value by name
     * @param name name of Account type you searching for
     * @return id value of the account type
     */
    public AccountType getAccountTypeByName(String name) {
        AccountType type = null;
        try {
            this.open();

            Cursor cursor = db.query(TABLE_ACCOUNTTYPE, KEY_ACCOUNT_ACCOUNTTYPE_ALL,
                    KEY_ACCOUNT_ACCOUNTTYPE_NAME + "=?",
                    new String[] {name}, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                type = new AccountType();
                type.setId(Long.parseLong(cursor.getString(0)));
                type.setName(name);

                if (cursor.getString(2).toLowerCase().matches("yes")) {
                    type.setScheduled(true);
                }
                else {
                    type.setScheduled(false);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "GetTypeIdByName Failed: " + e.toString());
        } finally {
            this.close();
            return type;
        }

    }
}

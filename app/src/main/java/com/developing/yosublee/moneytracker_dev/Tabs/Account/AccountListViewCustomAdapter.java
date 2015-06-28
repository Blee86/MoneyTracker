package com.developing.yosublee.moneytracker_dev.Tabs.Account;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developing.yosublee.moneytracker_dev.R;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yosublee on 6/25/15.
 */
public class AccountListViewCustomAdapter extends BaseAdapter {
    private static final String TAG = "AccountListViewCustomAdapter";
    private Context mContext;
    private List<AccountSource> mAccountList;
    private ViewHolder holder;

    public AccountListViewCustomAdapter(Context context, List<AccountSource> list) {
        mContext = context;
        mAccountList = list;
    }


    @Override
    public int getCount() {
        return mAccountList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAccountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.custom_listview_account, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.custom_list_account_name);
            holder.balance = (TextView) convertView.findViewById(R.id.custom_list_account_balance);
            holder.type = (TextView) convertView.findViewById(R.id.custom_list_account_type);
            holder.paymentDate = (TextView) convertView.findViewById(R.id.custom_list_account_paymentDate);
            holder.billingDate = (TextView) convertView.findViewById(R.id.custom_list_account_billingDate);
            holder.paidFrom = (TextView) convertView.findViewById(R.id.custom_list_account_paidFrom);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set Elements
        if (mAccountList != null && mAccountList.size() > 0) {
            holder.name.setText(mAccountList.get(position).getName());
            holder.balance.setText("" + mAccountList.get(position).getBalance());
            holder.type.setText(mAccountList.get(position).getTypeName());
            holder.paymentDate.setText(String.valueOf(mAccountList.get(position).getPaymentDateInUnixTime()));
            holder.billingDate.setText(String.valueOf(mAccountList.get(position).getBillingDateInUnixTime()));
            holder.paidFrom.setText(mAccountList.get(position).getPaidFrom());
        }

        return convertView;
    }

    public static final Comparator<AccountSource> accountComparator =
            new Comparator<AccountSource>() {

                @Override
                public int compare(AccountSource source1, AccountSource source2) {
                    if (source1.getId() < source2.getId()) return 1;
                    else return -1;
                }
            };


    ////////////////////////////////////////////////////////////
    //  ViewHolder Pattern
    // - It helps smooth scroll with listView
    ////////////////////////////////////////////////////////////
    private class ViewHolder {
        public TextView name, balance, type, paymentDate, billingDate, paidFrom;
    }
}

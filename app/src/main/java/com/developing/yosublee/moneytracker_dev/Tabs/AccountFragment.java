package com.developing.yosublee.moneytracker_dev.Tabs;


import com.developing.yosublee.moneytracker_dev.CreateAccountActivity;
import com.developing.yosublee.moneytracker_dev.DBAadapter.AccountManager;
import com.developing.yosublee.moneytracker_dev.R;
import com.developing.yosublee.moneytracker_dev.Tabs.Account.AccountListViewCustomAdapter;
import com.developing.yosublee.moneytracker_dev.Tabs.Account.AccountSource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by yosublee on 6/23/15.
 */
public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";
    public Context context;

    // Layout Components
    ListView listView;
    Button btn_AccountManager;
    AccountListViewCustomAdapter listAdapter;

    // DB Objects
    AccountManager accountManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountManager = new AccountManager(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);

        // Initializing layout components
        listView = (ListView) view.findViewById(R.id.listView_accountList);
        btn_AccountManager = (Button) view.findViewById(R.id.btn_manageAccount);

        ArrayList<AccountSource> accountList = accountManager.getAllAccounts();
        listAdapter = new AccountListViewCustomAdapter(context, accountList);
        listView.setAdapter(listAdapter);
        listView.setEmptyView(view.findViewById(R.id.account_emptyListView));

        btn_AccountManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}


package com.developing.yosublee.moneytracker_dev;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.developing.yosublee.moneytracker_dev.DBAadapter.AccountManager;
import com.developing.yosublee.moneytracker_dev.Tabs.Account.AccountSource;
import com.developing.yosublee.moneytracker_dev.Tabs.Account.AccountType;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yosublee on 6/27/15.
 */
public class CreateAccountActivity extends Activity {
    private static final String TAG = "CreateAccountActivity";

    // Layout Components
    EditText name, balance;
    TableRow rowPaymentDate, rowFrom, rowBillingDate;
    Spinner type;
    Button btn_confirm, btn_cancel, btn_paymentDate, btn_paymentFrom, btn_billingDate,
            btn_manageAccountType;


    // Other objects to deal with DB
    AccountManager accountManager;
    Calendar dateTime = Calendar.getInstance();
    private long paymentDateTime, billingDateTime; // in Unix Time

    DatePickerDialog.OnDateSetListener paymentDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            btn_paymentDate.setText(String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth));
            paymentDateTime = dateTime.getTimeInMillis();
        }
    };

    DatePickerDialog.OnDateSetListener billingDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            btn_billingDate.setText(String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth));
            billingDateTime = dateTime.getTimeInMillis();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        accountManager = new AccountManager(getApplicationContext());
        // Initializa Layout Components
        name            = (EditText) findViewById(R.id.editText_accountName);
        balance         = (EditText) findViewById(R.id.editText_accountBalance);
        rowPaymentDate  = (TableRow) findViewById(R.id.row_paymentDate);
        rowFrom         = (TableRow) findViewById(R.id.row_accountFrom);
        rowBillingDate  = (TableRow) findViewById(R.id.row_billingDate);
        type            = (Spinner) findViewById(R.id.spinner_accountType);
        btn_confirm     = (Button) findViewById(R.id.btn_Confirm);
        btn_cancel      = (Button) findViewById(R.id.btn_Cancel);
        btn_paymentDate = (Button) findViewById(R.id.btn_accountPaymentDate);
        btn_paymentFrom = (Button) findViewById(R.id.btn_accountFrom);
        btn_billingDate = (Button) findViewById(R.id.btn_accountBilling);
        btn_manageAccountType = (Button) findViewById(R.id.btn_manage_AccountType);

        // Setup Account Type Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getAccountTypeNames(accountManager.getAllAccountTypes()));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(spinnerAdapter);

        // Setup Action Listeners
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), parent.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });

        // Calendar View to select Scheduled Payment & Billing Date
        btn_paymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateAccountActivity.this, paymentDatePicker, dateTime.get(Calendar.YEAR),
                        dateTime.get(Calendar.MONTH),
                        dateTime.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_billingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateAccountActivity.this, billingDatePicker, dateTime.get(Calendar.YEAR),
                        dateTime.get(Calendar.MONTH),
                        dateTime.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = -1;
                if (!name.getText().toString().matches("") &&
                        !balance.getText().toString().matches("")) {
                    // insert new Account
                    AccountSource source = new AccountSource();
                    AccountType accountType = accountManager.getAccountTypeByName(
                            type.getSelectedItem().toString());

                    source.setName(name.getText().toString());
                    source.setBalance(Integer.parseInt(balance.getText().toString()));
                    source.setTypeName(accountType.getName());
                    source.setTypeId(accountType.getId());

                    if (accountType.isScheduled()) {
                        source.setPaymentDate(paymentDateTime);
                        source.setBillingDate(billingDateTime);
                        //source.setPaidFrom();
                    }

                    result = accountManager.insertAccount(source);
                }

                if (result > 0) {
                    Toast.makeText(getApplicationContext(), "It worked I guess?!",
                            Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CANCEL");
                Toast.makeText(getApplicationContext(), "Cancel!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    private ArrayList<String> getAccountTypeNames(ArrayList<AccountType> list) {
        ArrayList<String> nameList = null;

        if (list.size() > 0) {
            nameList = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                nameList.add(list.get(i).getName());
            }
        }
        return nameList;
    }
}

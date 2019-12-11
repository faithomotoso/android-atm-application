package com.example.androidatmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidatmapplication.models.ATM;
import com.example.androidatmapplication.models.Bank;
import com.example.androidatmapplication.models.Customer;

public class Transactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                progressDialog.show();
                progressDialog.dismiss();
            }
        }, 1200);


        final Customer customer = Customer.getInstance();
        ATM atm = ATM.getInstance();
        Bank bank = Bank.init();
        TextView welcomeTextView = findViewById(R.id.welcome_textview);
        welcomeTextView.setText("Welcome, " + customer.getName());

        Button withdrawButton = findViewById(R.id.withdraw_buttom);
        Button checkBalanceButton = findViewById(R.id.check_balance_button);
        Button removeCardButton = findViewById(R.id.remove_card_button);

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent withdrawIntent = new Intent(Transactions.this, Withdraw.class);
                startActivity(withdrawIntent);
            }
        });

        checkBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent balanceIntent = new Intent(Transactions.this, Balance.class);
                startActivity(balanceIntent);
            }
        });

        removeCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(Transactions.this, MainActivity.class);
                Customer.clearInstance();
                ATM.clearInstance();
                startActivity(loginIntent);
                finish();

            }
        });
    }
}

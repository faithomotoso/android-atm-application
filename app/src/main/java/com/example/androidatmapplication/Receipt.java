package com.example.androidatmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidatmapplication.models.ATM;
import com.example.androidatmapplication.models.Transaction;

public class Receipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        Intent thisIntent = getIntent();
        Transaction transaction = (Transaction) thisIntent.getSerializableExtra("transaction");

        TextView dateTextView = findViewById(R.id.date_textview);
        TextView timeTextView = findViewById(R.id.time_textview);
        TextView locationTextView = findViewById(R.id.location_textview);
        TextView transactionTypeTextView = findViewById(R.id.transaction_type_textview);
        TextView accountTextView = findViewById(R.id.account_textview);
        TextView amountTextView = findViewById(R.id.amount_textview);
        TextView balanceTextView = findViewById(R.id.available_balance_textview);

        dateTextView.setText(transaction.getDate());
        timeTextView.setText(transaction.getTime());
        locationTextView.setText(transaction.getLocation());
        transactionTypeTextView.setText(transaction.getTransactionType());
        accountTextView.setText(transaction.getAccount());
        amountTextView.setText(transaction.getAmount());
        balanceTextView.setText(transaction.getAvailableBalance());

        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transactionHome = new Intent(Receipt.this, Transactions.class);
                startActivity(transactionHome);
            }
        });

        ATM atm = ATM.getInstance();
        atm.withdrawAmount = 0;

    }
}

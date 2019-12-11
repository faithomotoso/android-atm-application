package com.example.androidatmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.NumberFormat;
import android.icu.util.Currency;
import android.os.Bundle;
import android.widget.TextView;

import com.example.androidatmapplication.models.Customer;

public class Balance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);


        Customer customer = Customer.getInstance();

        TextView balanceTextView = findViewById(R.id.balance_amount_textview);

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setCurrency(Currency.getInstance("NGN"));

        balanceTextView.setText(formatter.format(customer.getAccountBalance()));
    }
}

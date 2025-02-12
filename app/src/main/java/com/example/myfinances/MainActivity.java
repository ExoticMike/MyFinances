package com.example.myfinances;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText accountNumber, initialBalance, currentBalance, interestRate, paymentAmount;
    private Button saveButton, clearButton;
    private RadioGroup radioGroup;
    private DatabaseHelper databaseHelper;
    private TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI Elements
        accountNumber = findViewById(R.id.editTextAccountNum);
        initialBalance = findViewById(R.id.editTextInitialBalance);
        currentBalance = findViewById(R.id.editTextCurBalance);
        interestRate = findViewById(R.id.editTextInterestRate);
        paymentAmount = findViewById(R.id.editTextPaymentAmount);
        radioGroup = findViewById(R.id.radioGroup);
        saveButton = findViewById(R.id.SaveButton);
        clearButton = findViewById(R.id.ClearButton);
        messageView = findViewById(R.id.textView);

        databaseHelper = new DatabaseHelper(this);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioCD) {
                interestRate.setVisibility(View.VISIBLE);
                paymentAmount.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioLoan) {
                interestRate.setVisibility(View.VISIBLE);
                paymentAmount.setVisibility(View.VISIBLE);
            } else {
                interestRate.setVisibility(View.GONE);
                paymentAmount.setVisibility(View.GONE);
            }
        });

        saveButton.setOnClickListener(v -> saveFinancialObject());
        clearButton.setOnClickListener(v -> clearFields());
    }

    private void saveFinancialObject() {
        String accNum = accountNumber.getText().toString().trim();
        if (accNum.isEmpty()) {
            messageView.setText("Please enter an account number.");
            return;
        }

        double initBal = parseDoubleSafe(initialBalance.getText().toString());
        double currBal = parseDoubleSafe(currentBalance.getText().toString());
        double interest = 0, payment = 0;
        String type = "";

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioCD) {
            interest = parseDoubleSafe(interestRate.getText().toString());
            type = "CD";
            databaseHelper.insertFinancialObject(new FinancialObject.CD(accNum, initBal, currBal, interest), type, interest, null);
        } else if (selectedId == R.id.radioLoan) {
            interest = parseDoubleSafe(interestRate.getText().toString());
            payment = parseDoubleSafe(paymentAmount.getText().toString());
            type = "Loan";
            databaseHelper.insertFinancialObject(new FinancialObject.Loan(accNum, initBal, currBal, payment, interest), type, interest, payment);
        } else {
            type = "Checking";
            databaseHelper.insertFinancialObject(new FinancialObject.CheckingAccount(accNum, initBal, currBal), type, null, null);
        }

        messageView.setText("Saved successfully!");
        clearFields();
    }

    private void clearFields() {
        accountNumber.setText("");
        initialBalance.setText("");
        currentBalance.setText("");
        interestRate.setText("");
        paymentAmount.setText("");
        messageView.setText("Fields cleared.");
    }
    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

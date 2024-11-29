package com.example.womensafetyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    private EditText inputField;
    private double firstNumber;
    private double secondNumber;
    private String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        inputField = findViewById(R.id.inputField);

        // Initialize buttons
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide = findViewById(R.id.btnDivide);
        Button btnModulus = findViewById(R.id.btnModulus);
        Button btnEqual = findViewById(R.id.btnEqual);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnZero = findViewById(R.id.btnZero);
        Button btnOne = findViewById(R.id.btnOne);
        Button btnTwo = findViewById(R.id.btnTwo);
        Button btnThree = findViewById(R.id.btnThree);
        Button btnFour = findViewById(R.id.btnFour);
        Button btnFive = findViewById(R.id.btnFive);
        Button btnSix = findViewById(R.id.btnSix);
        Button btnSeven = findViewById(R.id.btnSeven);
        Button btnEight = findViewById(R.id.btnEight);
        Button btnNine = findViewById(R.id.btnNine);
        Button btnDot = findViewById(R.id.btnDot);

        // Set OnClickListeners for number buttons
        btnZero.setOnClickListener(v -> appendToInputField("0"));
        btnOne.setOnClickListener(v -> appendToInputField("1"));
        btnTwo.setOnClickListener(v -> appendToInputField("2"));
        btnThree.setOnClickListener(v -> appendToInputField("3"));
        btnFour.setOnClickListener(v -> appendToInputField("4"));
        btnFive.setOnClickListener(v -> appendToInputField("5"));
        btnSix.setOnClickListener(v -> appendToInputField("6"));
        btnSeven.setOnClickListener(v -> appendToInputField("7"));
        btnEight.setOnClickListener(v -> appendToInputField("8"));
        btnNine.setOnClickListener(v -> appendToInputField("9"));
        btnDot.setOnClickListener(v -> appendToInputField("."));

        // Set OnClickListeners for operator buttons
        btnAdd.setOnClickListener(v -> handleOperatorClick("+"));
        btnSubtract.setOnClickListener(v -> handleOperatorClick("-"));
        btnMultiply.setOnClickListener(v -> handleOperatorClick("*"));
        btnDivide.setOnClickListener(v -> handleOperatorClick("/"));
        btnModulus.setOnClickListener(v -> handleOperatorClick("%"));

        // Set OnClickListener for equal button
        btnEqual.setOnClickListener(v -> calculateResult());

        // Set OnClickListener for clear button
        btnClear.setOnClickListener(v -> clearInputField());
    }

    // Appends a digit or dot to the input field
    private void appendToInputField(String text) {
        inputField.append(text);
    }

    // Handles operator button clicks and stores the operator
    private void handleOperatorClick(String operator) {
        firstNumber = Double.parseDouble(inputField.getText().toString());
        this.operator = operator;
        inputField.setText(""); // Clear the input field for the next number
    }

    // Clears the input field
    private void clearInputField() {
        inputField.setText("");
        firstNumber = 0;
        secondNumber = 0;
    }

    // Calculates the result based on the operator
    private void calculateResult() {
        if (operator != null && !inputField.getText().toString().isEmpty()) {
            secondNumber = Double.parseDouble(inputField.getText().toString());

            double result;
            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        inputField.setText("Error");
                        return;
                    }
                    break;
                case "%":
                    result = firstNumber % secondNumber;
                    break;
                default:
                    inputField.setText("Error");
                    return;
            }

            inputField.setText(String.valueOf(result));
            operator = null; // Reset the operator after calculation
        }
    }
}

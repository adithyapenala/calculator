package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private String currentOperator;
    private String firstOperand;
    private boolean isOperatorClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        firstOperand = "";
    }

    // Method to handle digit buttons (0-9)
    public void onDigitClick(View view) {
        Button button = (Button) view;
        String currentText = tvDisplay.getText().toString();

        // Handle decimal point
        if (button.getText().equals(".") && currentText.contains(".")) {
            return; // Prevent multiple decimal points
        }

        // Prevent appending multiple zeros at the start
        if (currentText.equals("0") && !button.getText().equals(".")) {
            currentText = "";
        }

        tvDisplay.setText(currentText + button.getText());
    }

    // Method to handle operator buttons (+, -, *, /)
    public void onOperatorClick(View view) {
        Button button = (Button) view;
        String currentText = tvDisplay.getText().toString();

        // Prevent operator if no operand is entered
        if (currentText.isEmpty()) {
            Toast.makeText(this, "Enter a number first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prevent consecutive operator clicks
        if (isOperatorClicked) {
            Toast.makeText(this, "Operator already selected", Toast.LENGTH_SHORT).show();
            return;
        }

        firstOperand = currentText;  // Store the first operand
        currentOperator = button.getText().toString();  // Store the operator
        isOperatorClicked = true;
    }

    // Method to handle the "=" button
    public void onEqualClick(View view) {
        String secondOperand = tvDisplay.getText().toString();

        // Check if an operator was selected
        if (currentOperator == null) {
            Toast.makeText(this, "Select an operator", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if second operand is entered
        if (secondOperand.isEmpty()) {
            Toast.makeText(this, "Enter a second number", Toast.LENGTH_SHORT).show();
            return;
        }

        double result = 0;

        try {
            // Perform the operation
            switch (currentOperator) {
                case "+":
                    result = Double.parseDouble(firstOperand) + Double.parseDouble(secondOperand);
                    break;
                case "-":
                    result = Double.parseDouble(firstOperand) - Double.parseDouble(secondOperand);
                    break;
                case "*":
                    result = Double.parseDouble(firstOperand) * Double.parseDouble(secondOperand);
                    break;
                case "/":
                    // Prevent division by zero
                    if (secondOperand.equals("0")) {
                        Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    result = Double.parseDouble(firstOperand) / Double.parseDouble(secondOperand);
                    break;
            }

            // Display the result
            tvDisplay.setText(String.valueOf(result));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
        }catch (ArithmeticException e) {
            Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error performing calculation", Toast.LENGTH_SHORT).show();
        }

        // Reset for the next operation
        firstOperand = "";
        currentOperator = null;
        isOperatorClicked = false;
    }


    // Method to handle the "C" (Clear) button
    public void onClearClick(View view) {
        tvDisplay.setText("0");  // Reset the display
        firstOperand = "";
        currentOperator = "";
        isOperatorClicked = false;
    }
}

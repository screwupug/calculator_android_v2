package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.calculator.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import calculations.Calculator;
import database.DBHelper;
import fragments.FinanceFragment;
import fragments.MeasuresFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private TextView inputField;

    private TextView resultField;
    private Calculator calculator = new Calculator();
    private ImageButton one, two, three, four, five, six, seven, eight, nine, zero,
            multiply, minus, clear, comma, division, delete, plus, percent, result,
            changeView, history, pi, factorial, leftBracket, rightBracket, root,
            exponent, measures, finance;

    private final String PI_NUMBER = "3.14159265";

    private ConstraintLayout extraButtons;


    private Fragment measuresFragment, financeFragment;
    private boolean isClicked;

    private boolean restoreState;

    private List<Character> operators = new ArrayList<>(Arrays.asList('+', '-', '×', '÷', '.'));

    DBHelper dbHelper;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View view) {
        setRestoreState();

        int id = view.getId();

        String resultFieldExpression = resultField.getText().toString();

        if (id == R.id.b_one) {
            addDigit("1");
        } else if (id == R.id.b_two) {
            addDigit("2");
        } else if (id == R.id.b_three) {
            addDigit("3");
        } else if (id == R.id.b_four) {
            addDigit("4");
        } else if (id == R.id.b_five) {
            addDigit("5");
        } else if (id == R.id.b_six) {
            addDigit("6");
        } else if (id == R.id.b_seven) {
            addDigit("7");
        } else if (id == R.id.b_eight) {
            addDigit("8");
        } else if (id == R.id.b_nine) {
            addDigit("9");
        } else if (id == R.id.b_zero) {
            addDigit("0");
        } else if (id == R.id.b_plus) {
            addOperator('+');
        } else if (id == R.id.b_minus) {
            addOperator('-');
        } else if (id == R.id.b_division) {
            addOperator('÷');
        } else if (id == R.id.b_multiply) {
            addOperator('×');
        } else if (id == R.id.b_clear) {
            inputField.setText("");
            resultField.setText("");
            checkSize();
        } else if (id == R.id.b_comma) {
            addComma();
        } else if (id == R.id.b_delete) {
            deleteOneChar();
        } else if (id == R.id.b_result) {
            calculate(resultFieldExpression);
        } else if (id == R.id.b_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.b_percent) {
            inputField.setText(calculator.calculatePercent(getExpressionFromInputField()));
        } else if (id == R.id.b_left_bracket) {
            addBrackets('(');
        } else if (id == R.id.b_right_bracket) {
            addBrackets(')');
        } else if (id == R.id.b_factoiral) {
            addFactorial();
        } else if (id == R.id.b_pi) {
            addPiNumber();
        } else if (id == R.id.b_exponent) {
            resultField.append("^");
            inputField.setText(null);
        } else if (id == R.id.b_change_view) {
            if (!isClicked) {
                extraButtons.setVisibility(View.VISIBLE);
                isClicked = true;
            } else {
                extraButtons.setVisibility(View.GONE);
                isClicked = false;
            }
        } else if (id == R.id.b_measures) {
            engageFragment(measuresFragment);
        } else if (id == R.id.b_finance) {
            engageFragment(financeFragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_main);
        initializeViewElements();
        setListener();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    // restores data in textViews
    @Override
    protected void onResume() {
        super.onResume();
        if (!receiveHistory()) {
            getDataFromDB();
        }
    }

    // stores data from textViews in DB
    @Override
    protected void onPause() {
        dbHelper.insertDataInMain(inputField.getText().toString(), resultField.getText().toString());
        dbHelper.close();
        super.onPause();
    }

    private void getDataFromDB() {
        if (inputField.length() > 0 || resultField.length() > 0) {
            dbHelper.deleteMainBase();
            return;
        }
        Cursor cursor = dbHelper.getDataFromMain();
        // if DB is empty - no actions
        if (cursor.getCount() == 0) {
            return;
        }
        while (cursor.moveToNext()) {
            inputField.setText(cursor.getString(0));
            restoreResultField(cursor.getString(1));
        }
        checkSize();
        dbHelper.deleteMainBase();
        dbHelper.close();
    }

    private void addDigit(String digit) {
        if (inputField.length() < 19) {
            inputField.append(digit);
            digitOrderForInputField();
            digitOrderForResultField();
            checkSize();
        }
    }

    private void addOperator(char operator) {
        String expression = getExpressionFromResultField();
        int size = expression.length();
        if (size == 0) {
            return;
        }
        char lastSymbol = expression.charAt(size - 1);
        if (operators.contains(lastSymbol)) {
            if (lastSymbol != operator && lastSymbol != '.') {
                // remove last symbol in resultField and append colored operator
                deleteOneChar();
                resultField.append(changeColor(String.valueOf(operator)));
            }
            return;
        }
        resultField.append(changeColor(String.valueOf(operator)));
        inputField.setText(" ");
    }

    private void addBrackets(char bracket) {
        resultField.append(changeColor(String.valueOf(bracket)));
        inputField.setText(" ");
    }

    private void addFactorial() {
        String expression = getExpressionFromInputField();
        if (expression.length() == 0) {
            resultField.append("0");
        }
        resultField.append(changeColor(String.valueOf('!')));
        inputField.setText(null);
    }

    private void addPiNumber() {

        inputField.setText(PI_NUMBER);
        resultField.append(String.valueOf('π'));
        checkSize();
    }

    // changes color of operators
    private SpannableString changeColor(String operator) {
        SpannableString spannableString = new SpannableString(String.valueOf(operator));
        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(getResources().getColor(R.color.inputFieldTextColor));
        spannableString.setSpan(foregroundSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    // restore colors in resultField
    private void restoreResultField(String input) {
        if (input == null) return;
        char[] line = input.toCharArray();
        for (Character element : line) {
            if (Character.isDigit(element) || element == '.') {
                resultField.append(String.valueOf(element));
            } else {
                resultField.append(changeColor(String.valueOf(element)));
            }
        }
    }

    // change textSize if needed
    private void checkSize() {
        String expression = getExpressionFromInputField();
        String result = getExpressionFromResultField();
        if (expression.length() < 6) {
            inputField.setTextSize(100);
        }
        if (expression.length() >= 6) {
            inputField.setTextSize(65);
        }
        if (expression.length() >= 9) {
            inputField.setTextSize(50);
        }
        if (expression.length() >= 12) {
            inputField.setTextSize(40);
        }
        if (expression.length() > 15) {
            inputField.setTextSize(35);
            inputField.setMovementMethod(new ScrollingMovementMethod());
        }
        if (result.length() >= 12) {
            resultField.setTextSize(35);
        }
    }

    // add orders (разряды)
    private void digitOrderForInputField() {
        String expression = getExpressionFromInputField();
        double digit = Double.parseDouble(expression);
        if (digit % 1 == 0) {
            expression = String.format(Locale.US, "%,.0f", digit).replaceAll(",", " ");
        }
        inputField.setText(expression);
    }

    // add digit to resultField
    private void digitOrderForResultField() {
        char expression = inputField.getText().toString().charAt(inputField.length() - 1);
        resultField.append(String.valueOf(expression));
    }

    // method that returns expression from inputField without spaces
    private String getExpressionFromInputField() {
        return inputField.getText().toString().replaceAll(" ", "");
    }

    // method that returns expression from inputField without spaces
    private String getExpressionFromResultField() {
        return resultField.getText().toString();
    }

    // method that remove one char from textViews
    private void deleteOneChar() {
        int inputFieldSize = inputField.length();
        int resultFieldSize = resultField.length();
        if (inputFieldSize != 0) {
            inputField.setText(inputField.getText().subSequence(0, inputFieldSize - 1));
        }
        if (resultFieldSize != 0) {
            resultField.setText(resultField.getText().subSequence(0, resultFieldSize - 1));
        }
    }


    // Method that doesn't allow to insert more than 1 comma in 1 number
    private void addComma() {
        String expression = getExpressionFromInputField();
        if (expression.length() == 0) return;
        if (expression.contains(".")) return;
        inputField.append(".");
        resultField.append(".");
    }

    // calculates expression
    private void calculate(String expression) {
        String result = calculator.start(expression);
        inputField.setText(result);
        checkSize();
        if (result.equals("Ошибка") || result.equals("На ноль делить нельзя!")) {
            restoreState = true;
        }
        if (expression.length() != 0) {
            dbHelper.insertDataInExpressions(expression, result);
            dbHelper.close();
        }
    }

    private void setRestoreState() {
        if (restoreState) {
            inputField.setText(null);
            resultField.setText(null);
            checkSize();
            restoreState = false;
        }
    }

    // get data from HistoryActivity if exists
    private boolean receiveHistory() {
        Intent history = getIntent();
        if (history.hasExtra(Intent.EXTRA_TEXT)) {
            String result = history.getStringExtra(Intent.EXTRA_TEXT);
            inputField.setText(result);
            resultField.setText(result.replaceAll(" ", ""));
            checkSize();
            history.removeExtra(Intent.EXTRA_TEXT);
            return true;
        }
        return false;
    }

    private void engageFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.rl_main_frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void initializeViewElements() {
        inputField = findViewById(R.id.tv_input_field);
        resultField = findViewById(R.id.tv_result_field);
        one = findViewById(R.id.b_one);
        two = findViewById(R.id.b_two);
        three = findViewById(R.id.b_three);
        four = findViewById(R.id.b_four);
        five = findViewById(R.id.b_five);
        six = findViewById(R.id.b_six);
        seven = findViewById(R.id.b_seven);
        eight = findViewById(R.id.b_eight);
        nine = findViewById(R.id.b_nine);
        zero = findViewById(R.id.b_zero);
        plus = findViewById(R.id.b_plus);
        minus = findViewById(R.id.b_minus);
        division = findViewById(R.id.b_division);
        multiply = findViewById(R.id.b_multiply);
        percent = findViewById(R.id.b_percent);
        clear = findViewById(R.id.b_clear);
        delete = findViewById(R.id.b_delete);
        comma = findViewById(R.id.b_comma);
        result = findViewById(R.id.b_result);
        history = findViewById(R.id.b_history);
        changeView = findViewById(R.id.b_change_view);
        extraButtons = findViewById(R.id.extra_buttons);
        pi = findViewById(R.id.b_pi);
        factorial = findViewById(R.id.b_factoiral);
        leftBracket = findViewById(R.id.b_left_bracket);
        rightBracket = findViewById(R.id.b_right_bracket);
        root = findViewById(R.id.b_root);
        exponent = findViewById(R.id.b_exponent);
        measures = findViewById(R.id.b_measures);
        finance = findViewById(R.id.b_finance);
        measuresFragment = new MeasuresFragment();
        financeFragment = new FinanceFragment();
    }

    private void setListener() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        division.setOnClickListener(this);
        multiply.setOnClickListener(this);
        percent.setOnClickListener(this);
        clear.setOnClickListener(this);
        delete.setOnClickListener(this);
        result.setOnClickListener(this);
        comma.setOnClickListener(this);
        history.setOnClickListener(this);
        changeView.setOnClickListener(this);
        pi.setOnClickListener(this);
        factorial.setOnClickListener(this);
        leftBracket.setOnClickListener(this);
        rightBracket.setOnClickListener(this);
        root.setOnClickListener(this);
        exponent.setOnClickListener(this);
        measures.setOnClickListener(this);
        finance.setOnClickListener(this);
    }
}
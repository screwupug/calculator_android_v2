package fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.calculator.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import currencies.Calculations;
import currencies.Controller;
import currencies.Currencies;
import currencies.Currency;
import currencies.JsonReader;


public class CurrencyFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Toolbar toolbar;
    private Controller controller;
    private List<Currencies> items;

    private TextView currencyToChange, topCurrencyField, bottomCurrencyField, cbField;
    private ImageButton one, two, three, four, five, six, seven, eight, nine, zero, comma,
    clearAll, delete;
    private boolean topCurrencyFieldChosen, bottomCurrencyFieldChosen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_currency, container, false);
        viewElementsInitialization();
        initializeCurrenciesController();
        toolBarInitialize();
        setListener();
        return view;
    }

    private void toolBarInitialize() {
        toolbar = view.findViewById(R.id.currency_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitFragment();
            }
        });
    }

    private void viewElementsInitialization() {
        cbField = view.findViewById(R.id.tv_cb_text);
        currencyToChange = view.findViewById(R.id.currency_to_change);
        topCurrencyField = view.findViewById(R.id.tv_rub);
        bottomCurrencyField = view.findViewById(R.id.tv_bottom_currency);
        one = view.findViewById(R.id.b_one);
        two = view.findViewById(R.id.b_two);
        three = view.findViewById(R.id.b_three);
        four = view.findViewById(R.id.b_four);
        five = view.findViewById(R.id.b_five);
        six = view.findViewById(R.id.b_six);
        seven = view.findViewById(R.id.b_seven);
        eight = view.findViewById(R.id.b_eight);
        nine = view.findViewById(R.id.b_nine);
        zero = view.findViewById(R.id.b_zero);
        comma = view.findViewById(R.id.b_comma);
        clearAll = view.findViewById(R.id.b_clear);
        delete = view.findViewById(R.id.b_delete);
        items = new ArrayList<>(Arrays.asList(Currencies.values()));
    }

    private void initializeCurrenciesController() {
        controller = new Controller(new JsonReader(), new Calculations());
        controller.setCurrencyFrom(new Currency("RUB"));
        if (controller.setCurrencyTo(new Currency(Currencies.USD.toString()))) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date();
            cbField.append(String.format(" (обновлено %s)", format.format(date)));
        }
    }

    private void setListener() {
        currencyToChange.setOnClickListener(this);
        topCurrencyField.setOnClickListener(this);
        bottomCurrencyField.setOnClickListener(this);
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
        comma.setOnClickListener(this);
        clearAll.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        int id = view.getId();
        if (id == R.id.currency_to_change) {
            showButtonSheet();
        }
        if (id == R.id.tv_bottom_currency) {
            changeFieldToBottomCurrency();
        }
        if (id == R.id.tv_rub) {
            changeFieldToRub();
        }
        if (id == R.id.b_one) {
            writeInField("1");
        }
        if (id == R.id.b_two) {
            writeInField("2");
        }
        if (id == R.id.b_three) {
            writeInField("3");
        }
        if (id == R.id.b_four) {
            writeInField("4");
        }
        if (id == R.id.b_five) {
            writeInField("5");
        }
        if (id == R.id.b_six) {
            writeInField("6");
        }
        if (id == R.id.b_seven) {
            writeInField("7");
        }
        if (id == R.id.b_eight) {
            writeInField("8");
        }
        if (id == R.id.b_nine) {
            writeInField("9");
        }
        if (id == R.id.b_zero) {
            writeInField("0");
        }
        if (id == R.id.b_delete) {
            deleteOneChar();
        }
        if (id == R.id.b_clear) {
            topCurrencyField.setText(null);
            bottomCurrencyField.setText(null);
        }
        if (id == R.id.b_comma) {
            addComma();
        }
    }

    private void changeFieldToRub() {
        topCurrencyFieldChosen = true;
        bottomCurrencyFieldChosen = false;
        if (topCurrencyField.length() == 0) {
            topCurrencyField.setHintTextColor(getResources().getColor(R.color.resultFieldTextColor));
        } else {
            topCurrencyField.setTextColor(getResources().getColor(R.color.resultFieldTextColor));
        }
        bottomCurrencyField.setTextColor(getResources().getColor(R.color.inputFieldTextColor));
        bottomCurrencyField.setHintTextColor(getResources().getColor(R.color.inputFieldTextColor));
    }

    private void changeFieldToBottomCurrency() {
        topCurrencyFieldChosen = false;
        bottomCurrencyFieldChosen = true;
        if (bottomCurrencyField.length() == 0) {
            bottomCurrencyField.setHintTextColor(getResources().getColor(R.color.resultFieldTextColor));
        } else {
            bottomCurrencyField.setTextColor(getResources().getColor(R.color.resultFieldTextColor));
        }
        topCurrencyField.setTextColor(getResources().getColor(R.color.inputFieldTextColor));
        topCurrencyField.setHintTextColor(getResources().getColor(R.color.inputFieldTextColor));
    }

    private void writeInField(String input) {
        String result;
        if (topCurrencyFieldChosen) {
            if (topCurrencyField.length() <= 15) {
                topCurrencyField.append(input);
                controller.upDateCurrencyFromAmount(topCurrencyField.getText().toString());
                result = controller.currencyFromFieldUpdated();
                bottomCurrencyField.setText(result);
            }
        }
        if (bottomCurrencyFieldChosen) {
            if (bottomCurrencyField.length() <= 15) {
                bottomCurrencyField.append(input);
                controller.upDateCurrencyToAmount(bottomCurrencyField.getText().toString());
                result = controller.currencyToFieldUpdated();
                topCurrencyField.setText(result);
            }
        }
    }

    private void addComma() {
        String expression;
        if (topCurrencyFieldChosen) {
            expression = topCurrencyField.getText().toString();
            if (expression.length() == 0) return;
            if (expression.contains(".")) return;
            topCurrencyField.append(".");
        }
        if (bottomCurrencyFieldChosen) {
            expression = bottomCurrencyField.getText().toString();
            if (expression.length() == 0) return;
            if (expression.contains(".")) return;
            bottomCurrencyField.append(".");
        }
    }


    private void deleteOneChar() {
        String result;
        int rubFieldSize = topCurrencyField.length();
        int bottomCurrencyFieldFieldSize = bottomCurrencyField.length();
        if (topCurrencyFieldChosen) {
            String topCurrencyFieldAmount;
            if (rubFieldSize != 0) {
                topCurrencyField.setText(topCurrencyField.getText()
                        .subSequence(0, rubFieldSize - 1));
                topCurrencyFieldAmount = topCurrencyField.getText().toString();
                if (topCurrencyFieldAmount.length() != 0) {
                    controller.upDateCurrencyFromAmount(topCurrencyFieldAmount);
                    result = controller.currencyFromFieldUpdated();
                    bottomCurrencyField.setText(result);
                } else {
                    bottomCurrencyField.setText(null);
                }
            }
        }
        if (bottomCurrencyFieldChosen) {
            String bottomCurrencyFieldAmount;
            if (bottomCurrencyFieldFieldSize != 0) {
                bottomCurrencyField.setText(bottomCurrencyField.getText()
                        .subSequence(0, bottomCurrencyFieldFieldSize - 1));
                bottomCurrencyFieldAmount = bottomCurrencyField.getText().toString();
                if (bottomCurrencyFieldAmount.length() != 0) {
                    controller.upDateCurrencyToAmount(bottomCurrencyFieldAmount);
                    result = controller.currencyToFieldUpdated();
                    topCurrencyField.setText(result);
                } else {
                    topCurrencyField.setText(null);
                }

            }
        }
    }

    private void exitFragment() {
        getActivity().getSupportFragmentManager().popBackStack();

    }

    private void showButtonSheet() {
        ChooseBottomSheetFragment fragment = new ChooseBottomSheetFragment(items, new onClickItem() {
            @Override
            public void onClick(String line) {
                currencyToChange.setText(line);
                controller.setCurrencyTo(new Currency(line));
                topCurrencyField.setText(null);
                bottomCurrencyField.setText(null);
            }
        });
        fragment.show(getActivity().getSupportFragmentManager(), fragment.getTag());
    }
}


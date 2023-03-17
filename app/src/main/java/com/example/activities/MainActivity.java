package com.example.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import fragments.CalculatorFragment;
import com.example.calculator.R;

import adapters.MyPagerAdapter;
import fragments.FinanceFragment;
import fragments.MeasuresFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager2 pager;

    Fragment calculator, measures, finance;
    ImageButton calculatorFragment, measuresFragment, financeFragment, history;
    private boolean measuresButtonClicked, calculatorButtonClicked, financeButtonClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeElements();
        setListener();
        changeState();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.b_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }
        if (id == R.id.b_measures) {
            pager.setCurrentItem(1);
            measuresButtonClicked = true;
            calculatorButtonClicked = false;
            financeButtonClick = false;
            whichButtonIsClicked();
        }
        if (id == R.id.b_finance) {
            pager.setCurrentItem(2);
            measuresButtonClicked = false;
            calculatorButtonClicked = false;
            financeButtonClick = true;
            whichButtonIsClicked();
        }
        if (id == R.id.b_calculator) {
            measuresButtonClicked = false;
            calculatorButtonClicked = true;
            financeButtonClick = false;
            whichButtonIsClicked();
            pager.setCurrentItem(0);
        }
    }

    private void initializeElements() {
        pager = findViewById(R.id.vp_pager);
        MyPagerAdapter adapter = new MyPagerAdapter(this);
        pager.setAdapter(adapter);
        calculatorFragment = findViewById(R.id.b_calculator);
        measuresFragment = findViewById(R.id.b_measures);
        financeFragment = findViewById(R.id.b_finance);
        history = findViewById(R.id.b_history);
        calculator = new CalculatorFragment();
        measures = new MeasuresFragment();
        finance = new FinanceFragment();
        adapter.addFragment(calculator);
        adapter.addFragment(measures);
        adapter.addFragment(finance);
    }

    private void setListener() {
        calculatorFragment.setOnClickListener(this);
        measuresFragment.setOnClickListener(this);
        financeFragment.setOnClickListener(this);
        history.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void whichButtonIsClicked() {
        if (calculatorButtonClicked) {
            measuresFragment.setBackground(
                    AppCompatResources.getDrawable(this, R.drawable.measure));
            financeFragment.setBackground(
                    AppCompatResources.getDrawable(this, R.drawable.finance));
            calculatorFragment.setBackground(
                    AppCompatResources.getDrawable(this, R.drawable.calculator));
        }
        if (measuresButtonClicked) {
            measuresFragment.setBackground(
                    AppCompatResources.getDrawable(this, R.drawable.button_measure_pressed));
            financeFragment.setBackground(
                    AppCompatResources.getDrawable(this, R.drawable.finance));
            calculatorFragment.setBackground(
                    AppCompatResources.getDrawable(this, R.drawable.calc_button_unpressed));
        }
        if (financeButtonClick) {
            measuresFragment.setBackground(
                    AppCompatResources.getDrawable(this, R.drawable.measure));
            financeFragment.setBackground(
                    AppCompatResources.getDrawable(this, R.drawable.button_finace_pressed));
            calculatorFragment.setBackground(
                    AppCompatResources.getDrawable(this, R.drawable.calc_button_unpressed));
        }
    }

    private void changeState() {
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        measuresButtonClicked = false;
                        calculatorButtonClicked = true;
                        financeButtonClick = false;
                        whichButtonIsClicked();
                        break;
                    case 1:
                        measuresButtonClicked = true;
                        calculatorButtonClicked = false;
                        financeButtonClick = false;
                        whichButtonIsClicked();
                        break;
                    case 2:
                        measuresButtonClicked = false;
                        calculatorButtonClicked = false;
                        financeButtonClick = true;
                        whichButtonIsClicked();
                        break;
                }
            }
        });
    }
}
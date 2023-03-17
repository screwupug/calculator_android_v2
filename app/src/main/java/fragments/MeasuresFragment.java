package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.calculator.R;


public class MeasuresFragment extends Fragment implements View.OnClickListener {

    View  view;

    ImageButton calc, finance;
    Fragment financeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_measures, container, false);
//        initializeViewElements();
//        setListener();
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.b_calculator) {
            getActivity().onBackPressed();
        }
        if (id == R.id.b_finance) {
            engageFragment();
        }
    }

    private void initializeViewElements() {
        calc = view.findViewById(R.id.b_calculator);
        finance = view.findViewById(R.id.b_finance);
        financeFragment = new FinanceFragment();
    }

    private void setListener() {
        calc.setOnClickListener(this);
        finance.setOnClickListener(this);
    }

    private void engageFragment() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rl_measures_fragment, financeFragment)
                .commit();
    }


}
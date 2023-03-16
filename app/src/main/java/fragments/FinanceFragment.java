package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.calculator.R;

public class FinanceFragment extends Fragment implements View.OnClickListener {

    View view;
    ImageButton calc, measures;
    Fragment measuresFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_finance, container, false);
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
        if (id == R.id.b_measures) {
            engageFragment();
        }
    }

    private void initializeViewElements() {
        calc = view.findViewById(R.id.b_calculator);
        measures = view.findViewById(R.id.b_measures);
        measuresFragment = new MeasuresFragment();
    }

    private void setListener() {
        calc.setOnClickListener(this);
        measures.setOnClickListener(this);
    }

    private void engageFragment() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rl_finance_frame, measuresFragment)
                .commit();
    }
}
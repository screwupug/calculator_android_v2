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
    ImageButton currency;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_finance, container, false);
        initializeViewElements();
        setListener();
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ib_currency) {
            engageFragment(new CurrencyFragment());
        }

    }

    private void initializeViewElements() {
        currency = view.findViewById(R.id.ib_currency);
    }

    private void setListener() {
        currency.setOnClickListener(this);
    }

    private void engageFragment(Fragment fragment) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rl_main_frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}
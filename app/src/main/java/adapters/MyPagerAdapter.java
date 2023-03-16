package adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.calculator.CalculatorFragment;

import java.util.ArrayList;
import java.util.List;

import fragments.FinanceFragment;
import fragments.MeasuresFragment;

public class MyPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

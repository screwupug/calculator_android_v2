package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.calculator.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DeleteDialogFragment extends BottomSheetDialogFragment {
    public interface ConfirmationListener {
        void confirmationButtonClicked(DeleteDialogFragment fragment);
        void cancelButtonClicked(DeleteDialogFragment fragment);
    }

    ConfirmationListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ConfirmationListener) context;
        } catch (ClassCastException e) {
            System.out.println(getActivity().toString() + "must implement listener");
        }
    }

    @Override
    public int getTheme() {
        return R.style.DeleteDialogFragmentTheme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.delete_diaolog, container, false);
        ImageButton cancel = v.findViewById(R.id.ib_cancel);
        ImageButton delete = v.findViewById(R.id.ib_delete_history);
        cancel.setOnClickListener(view -> listener.cancelButtonClicked(this));
        delete.setOnClickListener(view -> listener.confirmationButtonClicked(this));
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}

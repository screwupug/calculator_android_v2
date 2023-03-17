package fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculator.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import currencies.Currencies;


public class ChooseBottomSheetFragment extends BottomSheetDialogFragment {

    private onClickItem listener;

    private List<Currencies> items;

    public ChooseBottomSheetFragment(List<Currencies> items, onClickItem listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getTheme() {
        return R.style.DeleteDialogFragmentTheme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycle_view_dialog, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.rv_choose);
        ImageButton cancel = v.findViewById(R.id.ib_cancel_choose);
        cancel.setOnClickListener(view -> dismiss());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ChooseRecyclerViewAdapter adapter = new ChooseRecyclerViewAdapter(items, new onClickItem() {
            @Override
            public void onClick(String line) {
                listener.onClick(line);
                dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public class ChooseRecyclerViewAdapter extends RecyclerView.Adapter<ChooseRecyclerViewAdapter.ChooseRecyclerViewHolder> {
        private List<Currencies> items;
        private onClickItem listener;

        public ChooseRecyclerViewAdapter(List<Currencies> items, onClickItem listener) {
            this.items = items;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ChooseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            int layOutIdForDialogListItem = R.layout.choose_diaolog_list_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(layOutIdForDialogListItem, parent, false);
            ChooseRecyclerViewAdapter.ChooseRecyclerViewHolder viewHolder
                    = new ChooseRecyclerViewAdapter.ChooseRecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChooseRecyclerViewHolder holder, int position) {
            holder.item.setText(items.get(position).toString());
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ChooseRecyclerViewHolder extends RecyclerView.ViewHolder {
            private TextView item;

            public ChooseRecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                item = itemView.findViewById(R.id.tv_choose_item);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(item.getText().toString());
                    }
                });
            }
        }
    }


}


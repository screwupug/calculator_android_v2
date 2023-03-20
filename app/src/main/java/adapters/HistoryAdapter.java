package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.activities.MainActivity;
import com.example.calculator.R;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final Context context;
    private final List<String> expressions;
    private final List<String> results;


    public HistoryAdapter(Context context, List<String> expressions, List<String> results) {
        this.context = context;
        this.expressions = expressions;
        this.results = results;
    }

    // creates ViewHolders
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layOutIdForHistoryListItem = R.layout.history_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layOutIdForHistoryListItem, parent, false);
        HistoryViewHolder viewHolder = new HistoryViewHolder(view);
        return viewHolder;
    }

    // refresh ViewHolders
    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        insertDataInColors(position, holder);
        holder.listItemResultView.setText(String.valueOf(results.get(position)));
        checkSize(holder);
    }

    // parses line and appends symbols
    private void insertDataInColors(int position, HistoryViewHolder holder) {
        char[] line = expressions.get(position).toCharArray();
        for (Character element : line) {
            if (Character.isDigit(element) || element == '.' || element == 'E') {
                holder.listItemExpressionView.append(String.valueOf(element));
            } else {
                holder.listItemExpressionView.append(changeColor(String.valueOf(element)));
            }
        }
    }

    // changes color of operators
    private SpannableString changeColor(String operator) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ").append(operator).append(" ");
        SpannableString spannableString = new SpannableString(builder);
        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.rgb(115, 217, 188));
        spannableString.setSpan(foregroundSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    // changes textSize depending on length
    private void checkSize(HistoryViewHolder holder) {
        String expression = holder.listItemExpressionView.getText().toString();

        if (expression.length() >= 20) {
            holder.listItemResultView.setTextSize(30);
            holder.listItemExpressionView.setTextSize(20);
        }
    }


    @Override
    public int getItemCount() {
        return expressions.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView listItemExpressionView;
        TextView listItemResultView;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            listItemExpressionView = itemView.findViewById(R.id.tv_history_expression);
            listItemResultView = itemView.findViewById(R.id.tv_history_result);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, listItemResultView.getText().toString());
                    context.startActivity(intent);
                    context.stopService(intent);

                }
            });
        }
    }
}

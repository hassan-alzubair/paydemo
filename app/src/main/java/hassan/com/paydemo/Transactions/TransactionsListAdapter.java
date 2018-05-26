package hassan.com.paydemo.Transactions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/17/2018.
 */

public class TransactionsListAdapter extends RecyclerView.Adapter<TransactionsListAdapter.TransactionViewHolder> {

    private final Context context;
    private final List<TransactionModel> transactionModels;

    public TransactionsListAdapter(Context context, List<TransactionModel> transactionModels) {
        this.context = context;
        this.transactionModels = transactionModels;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        holder.txtDate.setText(transactionModels.get(position).getCreatedAt());
        holder.txtFrom.setText(transactionModels.get(position).getAccountFrom());
        holder.txtTo.setText(transactionModels.get(position).getAccountTo());
        holder.txtAmount.setText(transactionModels.get(position).getAmount());
        holder.txtRefId.setText(String.format("%s#", transactionModels.get(position).getTransactionRefId()));
        if (transactionModels.get(position).getType() == TransactionType.INCOMMING) {
            holder.imgType.setImageDrawable(context.getResources().getDrawable(R.drawable.incomming));
            holder.imgType.setColorFilter(context.getResources().getColor(android.R.color.holo_green_light));
        } else {
            holder.imgType.setImageDrawable(context.getResources().getDrawable(R.drawable.outgoing));
            holder.imgType.setColorFilter(context.getResources().getColor(android.R.color.holo_red_light));
        }
    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate;
        TextView txtFrom;
        TextView txtAmount;
        TextView txtRefId;
        ImageView imgType;
        TextView txtTo;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.row_transaction_date);
            txtFrom = itemView.findViewById(R.id.transaction_row_account_from);
            txtAmount = itemView.findViewById(R.id.transaction_row_amount);
            txtRefId = itemView.findViewById(R.id.transaction_row_ref_id);
            imgType = itemView.findViewById(R.id.transaction_type);
            txtTo = itemView.findViewById(R.id.transaction_row_account_to);
        }
    }
}

package hassan.com.paydemo.Transactions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import hassan.com.paydemo.Base.BaseActivity;
import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/17/2018.
 */

public class TransactionsActivity extends BaseActivity implements TransactionView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TransactionPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_transactions);

        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        getSupportActionBar().setTitle("التحويلات السابقة");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = findViewById(R.id.swipe_transactions);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));

        recyclerView = findViewById(R.id.recycler_transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new TransactionsPresenterImpl(this, this, new TransactionInteractorImpl());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getTransactionList();
            }
        });
        presenter.getTransactionList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            NavUtils.navigateUpFromSameTask(this);
        else
            return super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String err) {
        new AlertDialog.Builder(this)
                .setMessage(err)
                .setCancelable(true)
                .setNeutralButton("موافق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void setList(List<TransactionModel> transactionModels) {
        TransactionsListAdapter adapter = new TransactionsListAdapter(this, transactionModels);
        recyclerView.setAdapter(adapter);
    }
}

package bnsantos.com.analytics.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import bnsantos.com.analytics.example.AnalyticsHelper;
import bnsantos.com.analytics.example.Constants;
import bnsantos.com.analytics.example.OperationType;
import bnsantos.com.analytics.example.R;
import bnsantos.com.analytics.example.StorageUtils;

public class MainActivity extends Activity {
    private AnalyticsHelper mHelper;
    private TextView mTitle;
    private ArrayAdapter<String> mHistoryAdapter;
    private ListView mHistoryListView;
    private List<String> mLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new AnalyticsHelper(this);
        mLogs = StorageUtils.getLog(this);

        initViews();
        initListeners();
        initAdapter();
        addItemIntoAdapter(OperationType.LOGGED);
    }

    private void initViews() {
        mTitle = (TextView) findViewById(R.id.loggedAs);
        mTitle.setText(getString(R.string.logged_as, Constants.getCurrentUser()));
        mHistoryListView = (ListView) findViewById(R.id.historyListView);
    }

    private void initListeners() {
        findViewById(R.id.projectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProject();
            }
        });
        findViewById(R.id.noteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
        findViewById(R.id.commentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
        findViewById(R.id.clearButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoryAdapter.clear();
                mHistoryAdapter.notifyDataSetChanged();
                StorageUtils.clear(MainActivity.this);
            }
        });
        findViewById(R.id.shareButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLogs();
            }
        });
    }

    private void initAdapter() {
        mHistoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLogs);
        mHistoryListView.setAdapter(mHistoryAdapter);
    }

    private void addProject() {
        addItemIntoAdapter(OperationType.PROJECT);
    }

    private void addNote() {
        addItemIntoAdapter(OperationType.NOTE);
    }

    private void addComment() {
        addItemIntoAdapter(OperationType.COMMENT);
    }

    private void addItemIntoAdapter(OperationType operationType) {
        String operation = Constants.formatDate(Calendar.getInstance().getTime()) + ";" + Constants.getCurrentUser() + ";" + operationType.name();
        StorageUtils.addLog(this, operation);
        mHistoryAdapter.add(operation);
        mHistoryAdapter.notifyDataSetChanged();

        showToast(operationType);
        mHelper.trackEvent(operationType);
    }

    private void showToast(OperationType operationType) {
        Toast.makeText(this, getString(R.string.successfully_added, operationType.name()), Toast.LENGTH_SHORT).show();
    }

    private void shareLogs() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, StorageUtils.getRawLog(this));
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHelper.onResume(MainActivity.class.getSimpleName());
    }
}

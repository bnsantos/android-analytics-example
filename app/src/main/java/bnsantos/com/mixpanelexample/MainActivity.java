package bnsantos.com.mixpanelexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.ArrayList;
import java.util.Calendar;

import bnsantos.com.mixpanelexample.R;

public class MainActivity extends Activity {
    private MixpanelAPI mMixpanelAPI;
    private TextView mTitle;
    private ArrayAdapter<CharSequence> mHistoryAdapter;
    private ListView mHistoryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMixpanelAPI = MixpanelAPI.getInstance(this, Constants.getMixpanelToken());

        initViews();
        initListeners();
        initAdapter();
    }

    private void initViews(){
        mTitle = (TextView) findViewById(R.id.loggedAs);
        mTitle.setText(getString(R.string.logged_as, Constants.getCurrentUser()));
        mHistoryListView = (ListView) findViewById(R.id.historyListView);
    }

    private void initListeners(){
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
    }

    private void initAdapter(){
        mHistoryAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, new ArrayList<CharSequence>());
        mHistoryListView.setAdapter(mHistoryAdapter);
    }

    private void addProject(){
        addItemIntoAdapter(OperationType.PROJECT);
    }
    private void addNote(){
        addItemIntoAdapter(OperationType.NOTE);
    }
    private void addComment(){
        addItemIntoAdapter(OperationType.COMMENT);
    }

    private void addItemIntoAdapter(OperationType operationType){
        String operation = Constants.formatDate(Calendar.getInstance().getTime()) + ";" + Constants.getCurrentUser() + ";" + operationType.name();

        //TODO persist it
        mHistoryAdapter.add(operation);
        mHistoryAdapter.notifyDataSetChanged();

        showToast(operationType);
        sendMixPanelEvent();
    }

    private void showToast(OperationType operationType){
        Toast.makeText(this, getString(R.string.successfully_added, operationType.name()), Toast.LENGTH_SHORT).show();
    }

    private void sendMixPanelEvent(){
        //TODO
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMixpanelAPI.flush();
    }

    private enum OperationType{
        PROJECT, NOTE, COMMENT
    }
}

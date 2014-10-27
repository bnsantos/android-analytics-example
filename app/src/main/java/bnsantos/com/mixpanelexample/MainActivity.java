package bnsantos.com.mixpanelexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ly.count.android.api.Countly;

public class MainActivity extends Activity {
    private MixpanelAPI mMixpanelAPI;
    private TextView mTitle;
    private ArrayAdapter<String> mHistoryAdapter;
    private ListView mHistoryListView;
    private List<String> mLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMixPanel();
        initCountly();

        mLogs = StorageUtils.getLog(this);

        initViews();
        initListeners();
        initAdapter();
        addItemIntoAdapter(OperationType.LOGGED);
    }

    private void setMixPanel() {
        mMixpanelAPI = MixpanelAPI.getInstance(this, Constants.getMixpanelToken());
        mMixpanelAPI.identify(Constants.getCurrentUserId());
        mMixpanelAPI.getPeople().set("last_login", Calendar.getInstance().getTime());
        mMixpanelAPI.getPeople().set("name", Constants.getCurrentUser());
        mMixpanelAPI.getPeople().set("gender", "female");

        mMixpanelAPI.alias(Constants.getCurrentUserId(), Constants.getCurrentUser());
        JSONObject props = new JSONObject();
        try {
            props.put("User Type", "Crazy woman");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mMixpanelAPI.registerSuperProperties(props);
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
        sendMixPanelEvent(operationType);
        sendFlurryEvent(operationType);
        logCountlyEvent(operationType);
    }

    private void showToast(OperationType operationType) {
        Toast.makeText(this, getString(R.string.successfully_added, operationType.name()), Toast.LENGTH_SHORT).show();
    }

    private void sendMixPanelEvent(OperationType operationType) {
        mMixpanelAPI.getPeople().increment(operationType.name(), 1);

        JSONObject props = new JSONObject();
        try {
            props.put("Gender", "Female");
            props.put("Plan", "Premium");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMixpanelAPI.track(operationType.name(), props);
    }

    private void sendFlurryEvent(OperationType operationType) {
        Map<String, String> eventParams = new HashMap<String, String>();

        eventParams.put("User", Constants.getCurrentUser());
        eventParams.put("Plan", "Premium");

        FlurryAgent.logEvent(operationType.name(), eventParams);
    }

    private void setFlurryUser() {
        FlurryAgent.setGender(com.flurry.android.Constants.FEMALE);
        FlurryAgent.setUserId(Constants.getCurrentUserId());
    }

    private void shareLogs() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, StorageUtils.getRawLog(this));
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    private void initCountly() {
        Countly.sharedInstance().init(this, Constants.getCountlyServer(), Constants.getCountlyKey(), Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    private void logCountlyEvent(OperationType operationType) {
        Map<String, String> eventParams = new HashMap<String, String>();
        eventParams.put("User", Constants.getCurrentUser());
        eventParams.put("UserId", Constants.getCurrentUserId());
        eventParams.put("Plan", "Premium");
        eventParams.put("Gender", "Female");
        eventParams.put("Device", "android");
        eventParams.put("Language", this.getResources().getConfiguration().locale.getLanguage());
        Countly.sharedInstance().recordEvent(operationType.name(), eventParams, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, Constants.getFlurryToken());
        setFlurryUser();
        Countly.sharedInstance().onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMixpanelAPI.flush();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
        Countly.sharedInstance().onStop();
    }

    private enum OperationType {
        PROJECT, NOTE, COMMENT, LOGGED
    }
}

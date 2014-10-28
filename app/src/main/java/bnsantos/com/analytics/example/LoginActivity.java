package bnsantos.com.analytics.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class LoginActivity extends FragmentActivity {
    private Spinner mLoginSpinner;
    private String mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginSpinner = (Spinner) findViewById(R.id.userSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLoginSpinner.setAdapter(adapter);

        mCurrentUser = adapter.getItem(0).toString();

        mLoginSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentUser = (String) parent.getItemAtPosition(position);
                Constants.setCurrentUserId(position);
                Constants.setCurrentUser(mCurrentUser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.actionMixPanel:
                showMixPanelConfigDialog();
                return true;
            case R.id.actionFlurry:
                showFlurryConfigDialog();
                return true;
            case R.id.actionContlyKey:
                showCountlyKeyConfigDialog();
                return true;
            case R.id.actionCountlyServer:
                showCountlyServerConfigDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void login(){
        Toast.makeText(this, getString(R.string.login_message, Constants.getCurrentUser()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMixPanelConfigDialog(){
        AnalyticsSettingsDialog analyticsSettingsDialog = new AnalyticsSettingsDialog();
        analyticsSettingsDialog.setMode(AnalyticsSettingsDialog.SettingsMode.MIX_PANEL);
        analyticsSettingsDialog.setTitle(R.string.dialog_mix_panel_title);
        analyticsSettingsDialog.show(getSupportFragmentManager(), "MixPanelConfig");
    }

    private void showFlurryConfigDialog() {
        AnalyticsSettingsDialog analyticsSettingsDialog = new AnalyticsSettingsDialog();
        analyticsSettingsDialog.setMode(AnalyticsSettingsDialog.SettingsMode.FLURRY);
        analyticsSettingsDialog.setTitle(R.string.dialog_flurry_title);
        analyticsSettingsDialog.show(getSupportFragmentManager(), "FlurryConfig");
    }

    private void showCountlyServerConfigDialog() {
        AnalyticsSettingsDialog analyticsSettingsDialog = new AnalyticsSettingsDialog();
        analyticsSettingsDialog.setMode(AnalyticsSettingsDialog.SettingsMode.COUNTLY_SERVER);
        analyticsSettingsDialog.setTitle(R.string.dialog_countly_server_title);
        analyticsSettingsDialog.show(getSupportFragmentManager(), "CountlyServer");
    }

    private void showCountlyKeyConfigDialog() {
        AnalyticsSettingsDialog analyticsSettingsDialog = new AnalyticsSettingsDialog();
        analyticsSettingsDialog.setMode(AnalyticsSettingsDialog.SettingsMode.COUNTLY_KEY);
        analyticsSettingsDialog.setTitle(R.string.dialog_countly_key_title);
        analyticsSettingsDialog.show(getSupportFragmentManager(), "CountlyKey");
    }
}

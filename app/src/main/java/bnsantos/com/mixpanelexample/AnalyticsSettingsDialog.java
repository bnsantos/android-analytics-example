package bnsantos.com.mixpanelexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bruno on 22/10/14.
 */
public class AnalyticsSettingsDialog extends DialogFragment {
    private EditText mToken;
    private TextView mTitleTextView;
    private int mTitle;
    private SettingsMode mMode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_mixpanel_settings, null);

        mToken = (EditText) view.findViewById(R.id.dialogToken);
        mTitleTextView = (TextView) view.findViewById(R.id.dialogTitle);
        mTitleTextView.setText(mTitle);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (validToken()) {
                            setToken();
                            dismiss();
                        } else {
                            showError();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    private void setToken() {
        switch (mMode) {
            case MIX_PANEL:
                Constants.setMixpanelToken(mToken.getText().toString());
                break;
            case FLURRY:
                Constants.setFlurryToken(mToken.getText().toString());
                break;
            case COUNTLY_KEY:
                Constants.setCountlyKey(mToken.getText().toString());
                break;
            case COUNTLY_SERVER:
                Constants.setCountlyServer(mToken.getText().toString());
        }
    }

    private boolean validToken() {
        return mToken.getText().length() > 0;
    }

    private void showError() {
        Toast.makeText(getActivity(), R.string.token_empty, Toast.LENGTH_SHORT).show();
    }

    public void setTitle(int mTitle) {
        this.mTitle = mTitle;
    }

    public void setMode(SettingsMode mMode) {
        this.mMode = mMode;
    }

    public enum SettingsMode {
        MIX_PANEL, FLURRY, COUNTLY_SERVER, COUNTLY_KEY
    }
}

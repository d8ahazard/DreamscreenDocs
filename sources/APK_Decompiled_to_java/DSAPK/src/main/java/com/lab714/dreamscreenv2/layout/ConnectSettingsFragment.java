package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;

public class ConnectSettingsFragment extends Fragment {
    private static final String tag = "ConnectSettingsFrag";
    /* access modifiers changed from: private */
    public ConnectSettingsFragmentListener connectSettingsFragmentListener;
    private boolean firmwareUpdateNeeded;
    /* access modifiers changed from: private */
    public FragmentManager fragmentManager;

    public interface ConnectSettingsFragmentListener {
        boolean firmwareUpdateNeeded();

        boolean isEmailReceived();

        boolean isHueLifxSettingsReceived();

        void readEmailAddress();

        void readHueLifxSettings();
    }

    public static ConnectSettingsFragment newInstance() {
        return new ConnectSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.firmwareUpdateNeeded = this.connectSettingsFragmentListener.firmwareUpdateNeeded();
        boolean hueLifxSettingsReceived = this.connectSettingsFragmentListener.isHueLifxSettingsReceived();
        boolean emailReceived = this.connectSettingsFragmentListener.isEmailReceived();
        Handler handler = new Handler();
        if (!hueLifxSettingsReceived) {
            Log.i(tag, "hueLifxSettings not received, attempting read again");
            if (this.connectSettingsFragmentListener != null) {
                this.connectSettingsFragmentListener.readHueLifxSettings();
            }
        }
        if (!emailReceived) {
            Log.i(tag, "email not received, attempting read again");
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (ConnectSettingsFragment.this.connectSettingsFragmentListener != null) {
                        ConnectSettingsFragment.this.connectSettingsFragmentListener.readEmailAddress();
                    }
                }
            }, 100);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_connect, container, false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        ((TextView) view.findViewById(R.id.lightText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.lightText2)).setTypeface(font);
        ((TextView) view.findViewById(R.id.integrationsText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.integrationsText2)).setTypeface(font);
        ((TextView) view.findViewById(R.id.remoteText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.remoteText2)).setTypeface(font);
        ((TextView) view.findViewById(R.id.firmware_update_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.help_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.help_desc_label)).setTypeface(font);
        TextView firmwareUpdateText2 = (TextView) view.findViewById(R.id.firmware_update_desc_label);
        firmwareUpdateText2.setTypeface(font);
        this.fragmentManager = getFragmentManager();
        ((LinearLayout) view.findViewById(R.id.lightSettingsLayout)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ConnectSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, HueLifxSettingsFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        ((LinearLayout) view.findViewById(R.id.integrationSettingsLayout)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ConnectSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, IntegrationSettingsFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        ((LinearLayout) view.findViewById(R.id.remoteSettingsLayout)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ConnectSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, RemoteSettingsFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        LinearLayout firmwareUpdateLayout = (LinearLayout) view.findViewById(R.id.firmwareUpdate_Layout);
        firmwareUpdateLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ConnectSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, FirmwareUpdateFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        ((LinearLayout) view.findViewById(R.id.help_Layout)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ConnectSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, HelpFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        if (this.firmwareUpdateNeeded) {
            firmwareUpdateText2.setText(getString(R.string.FirmwareUpdateNeeded));
            firmwareUpdateLayout.setBackgroundColor(1090453504);
        } else {
            firmwareUpdateText2.setText(getString(R.string.FirmwareUpdateNotNeeded));
            firmwareUpdateLayout.setBackgroundColor(0);
        }
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConnectSettingsFragmentListener) {
            this.connectSettingsFragmentListener = (ConnectSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement ConnectSettingsFragmentListener");
    }

    public void onDetach() {
        super.onDetach();
        this.connectSettingsFragmentListener = null;
    }
}

package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;

public class SideKickSettingsFragment extends Fragment {
    private static final String tag = "SideKickSettingsFrag";
    private LinearLayout colorSettingsButton;
    private LinearLayout firmwareUpdateButton;
    private boolean firmwareUpdateNeeded;
    private TextView firmwareUpdateText2;
    /* access modifiers changed from: private */
    public FragmentManager fragmentManager;
    private LinearLayout helpSettingsButton;
    private LinearLayout sectorSettingsButton;
    private SideKickSettingsFragmentListener sideKickSettingsFragmentListener;

    public interface SideKickSettingsFragmentListener {
        boolean firmwareUpdateNeeded();
    }

    public static SideKickSettingsFragment newInstance() {
        return new SideKickSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.firmwareUpdateNeeded = this.sideKickSettingsFragmentListener.firmwareUpdateNeeded();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_sidekick, container, false);
        this.firmwareUpdateText2 = (TextView) view.findViewById(R.id.firmware_update_desc_label);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.firmwareUpdateText2.setTypeface(font);
        ((TextView) view.findViewById(R.id.color_settings_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.color_settings_desc_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.sector_settings_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.sector_settings_desc_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.firmware_update_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.help_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.help_desc_label)).setTypeface(font);
        this.fragmentManager = getFragmentManager();
        this.colorSettingsButton = (LinearLayout) view.findViewById(R.id.colorSettings_Layout);
        this.colorSettingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SideKickSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, ColorSettingsFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        this.sectorSettingsButton = (LinearLayout) view.findViewById(R.id.sectorSettings_Layout);
        this.sectorSettingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SideKickSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, SectorSettingsFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        this.firmwareUpdateButton = (LinearLayout) view.findViewById(R.id.firmwareUpdate_Layout);
        this.firmwareUpdateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SideKickSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, FirmwareUpdateFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        this.helpSettingsButton = (LinearLayout) view.findViewById(R.id.help_Layout);
        this.helpSettingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SideKickSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, HelpFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        if (this.firmwareUpdateNeeded) {
            this.firmwareUpdateText2.setText(getContext().getResources().getString(R.string.FirmwareUpdateNeeded));
            this.firmwareUpdateButton.setBackgroundColor(1090453504);
        } else {
            this.firmwareUpdateText2.setText(getContext().getResources().getString(R.string.FirmwareUpdateNotNeeded));
            this.firmwareUpdateButton.setBackgroundColor(0);
        }
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SideKickSettingsFragmentListener) {
            this.sideKickSettingsFragmentListener = (SideKickSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement SideKickSettingsFragmentListener");
    }

    public void onDetach() {
        super.onDetach();
        this.sideKickSettingsFragmentListener = null;
    }
}

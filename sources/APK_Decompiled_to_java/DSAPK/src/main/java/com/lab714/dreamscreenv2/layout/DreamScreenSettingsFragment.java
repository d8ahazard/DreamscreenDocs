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

public class DreamScreenSettingsFragment extends Fragment {
    private static final String tag = "DreamScreenSettingsFrag";
    private LinearLayout audioSettingsButton;
    private DreamScreenSettingsFragmentListener dreamScreenSettingsFragmentListener;
    private LinearLayout firmwareUpdateButton;
    private boolean firmwareUpdateNeeded;
    private TextView firmwareUpdateText2;
    /* access modifiers changed from: private */
    public FragmentManager fragmentManager;
    private LinearLayout helpSettingsButton;
    private LinearLayout installationSettingsButton;
    private boolean picFirmwareIsValid;
    private LinearLayout systemSettingsButton;
    private LinearLayout videoSettingsButton;

    public interface DreamScreenSettingsFragmentListener {
        boolean firmwareUpdateNeeded();

        boolean picFirmwareValid();
    }

    public static DreamScreenSettingsFragment newInstance() {
        return new DreamScreenSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.firmwareUpdateNeeded = this.dreamScreenSettingsFragmentListener.firmwareUpdateNeeded();
        this.picFirmwareIsValid = this.dreamScreenSettingsFragmentListener.picFirmwareValid();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_dreamscreen, container, false);
        this.firmwareUpdateText2 = (TextView) view.findViewById(R.id.firmware_update_desc_label);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.firmwareUpdateText2.setTypeface(font);
        ((TextView) view.findViewById(R.id.video_settings_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.video_settings_desc_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.audio_settings_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.audio_settings_desc_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.system_settings_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.system_settings_desc_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.installation_settings_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.installation_settings_desc_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.firmware_update_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.help_label)).setTypeface(font);
        ((TextView) view.findViewById(R.id.help_desc_label)).setTypeface(font);
        this.fragmentManager = getFragmentManager();
        this.videoSettingsButton = (LinearLayout) view.findViewById(R.id.videoSettings_Layout);
        this.videoSettingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DreamScreenSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, VideoSettingsFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        this.audioSettingsButton = (LinearLayout) view.findViewById(R.id.audioSettings_Layout);
        this.audioSettingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DreamScreenSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, AudioSettingsFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        this.systemSettingsButton = (LinearLayout) view.findViewById(R.id.systemSettings_Layout);
        this.systemSettingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DreamScreenSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, AdvancedSettingsFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        this.installationSettingsButton = (LinearLayout) view.findViewById(R.id.installationSettings_Layout);
        this.installationSettingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DreamScreenSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, InstallationSettingsFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        this.firmwareUpdateButton = (LinearLayout) view.findViewById(R.id.firmwareUpdate_Layout);
        this.firmwareUpdateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DreamScreenSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, FirmwareUpdateFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        this.helpSettingsButton = (LinearLayout) view.findViewById(R.id.help_Layout);
        this.helpSettingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DreamScreenSettingsFragment.this.fragmentManager.beginTransaction().replace(R.id.frameLayout, HelpFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        if (!this.picFirmwareIsValid) {
            this.firmwareUpdateText2.setText(getContext().getResources().getString(R.string.Error));
            this.firmwareUpdateButton.setBackgroundColor(0);
        } else if (this.firmwareUpdateNeeded) {
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
        if (context instanceof DreamScreenSettingsFragmentListener) {
            this.dreamScreenSettingsFragmentListener = (DreamScreenSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement Listener");
    }

    public void onDetach() {
        super.onDetach();
        this.dreamScreenSettingsFragmentListener = null;
    }
}

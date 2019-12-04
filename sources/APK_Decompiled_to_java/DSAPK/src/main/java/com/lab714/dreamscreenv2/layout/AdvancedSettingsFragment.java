package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;
import com.lab714.dreamscreenv2.devices.DreamScreen;
import com.lab714.dreamscreenv2.devices.DreamScreen4K;
import com.lab714.dreamscreenv2.devices.DreamScreenSolo;
import com.lab714.dreamscreenv2.devices.Light;
import java.util.Iterator;
import java.util.LinkedList;

public class AdvancedSettingsFragment extends Fragment {
    private static final long packetSendDelay = 30;
    private static final String tag = "AdvancedSettingsFrag";
    /* access modifiers changed from: private */
    public AdvancedSettingsFragmentListener advancedSettingsFragmentListener;
    private SeekBar bottomBrightnessSeekbar;
    private ImageView bottomGlow;
    /* access modifiers changed from: private */
    public int cecPassthroughEnable = 1;
    /* access modifiers changed from: private */
    public LinearLayout cecPassthroughLayout;
    private ImageView cecPassthroughToggle;
    /* access modifiers changed from: private */
    public int cecPowerEnable = 0;
    /* access modifiers changed from: private */
    public LinearLayout cecPowerEnableLayout;
    private ImageView cecPowerEnableToggle;
    /* access modifiers changed from: private */
    public int cecSwitchingEnable = 1;
    /* access modifiers changed from: private */
    public LinearLayout cecSwitchingLayout;
    private ImageView cecSwitchingToggle;
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public TextView currentWidgetText;
    /* access modifiers changed from: private */
    public int frameDelay = 0;
    /* access modifiers changed from: private */
    public LinearLayout frameDelayLayout;
    private SeekBar frameDelaySeekbar;
    /* access modifiers changed from: private */
    public TextView frameDelayTextCurrent;
    /* access modifiers changed from: private */
    public int hdrToneRemapping = 0;
    /* access modifiers changed from: private */
    public LinearLayout hdrToneRemappingLayout;
    private ImageView hdrToneRemappingToggle;
    /* access modifiers changed from: private */
    public final LayoutParams helpLayoutParams = new LayoutParams(-1, -2);
    /* access modifiers changed from: private */
    public TextView helpTextView;
    /* access modifiers changed from: private */
    public int hpdEnable = 1;
    /* access modifiers changed from: private */
    public LinearLayout hpdEnablingLayout;
    private ImageView hpdEnablingToggle;
    private VerticalSeekBar leftBrightnessSeekbar;
    private ImageView leftGlow;
    /* access modifiers changed from: private */
    public long packetSentAtTime = SystemClock.elapsedRealtime();
    private int productId;
    private VerticalSeekBar rightBrightnessSeekbar;
    private ImageView rightGlow;
    /* access modifiers changed from: private */
    public LinearLayout rootLayout;
    boolean shouldScrollToWidgetSettings = false;
    private SeekBar topBrightnessSeekbar;
    private ImageView topGlow;
    /* access modifiers changed from: private */
    public int usbPowerEnable = 0;
    /* access modifiers changed from: private */
    public LinearLayout usbPowerEnableLayout;
    private ImageView usbPowerEnableToggle;
    /* access modifiers changed from: private */
    public LinearLayout widgetDeviceLayout;
    /* access modifiers changed from: private */
    public LinearLayout widgetLayout;
    private ImageButton zoneBottomButton;
    private ImageButton zoneLeftButton;
    private ImageButton zoneRightButton;
    private ImageButton zoneTopButton;
    private byte zones = 0;
    /* access modifiers changed from: private */
    public byte[] zonesBrightness = {-1, -1, -1, -1};
    /* access modifiers changed from: private */
    public LinearLayout zonesLayout;

    public interface AdvancedSettingsFragmentListener {
        int getCecPassthroughEnable();

        int getCecPowerEnable();

        int getCecSwitchingEnable();

        Light getCurrentLight();

        int getFrameDelay();

        LinkedList<LinkedList<Light>> getGroups();

        int getHdrToneRemapping();

        int getHpdEnable();

        int getProductId();

        int getUsbPowerEnable();

        byte getZones();

        byte[] getZonesBrightness();

        void setCecPassthroughEnable(int i);

        void setCecPowerEnable(int i);

        void setCecSwitchingEnable(int i);

        void setFrameDelay(int i);

        void setHdrToneRemapping(int i);

        void setHpdEnable(int i);

        void setUsbPowerEnable(int i);

        void setZones(byte b);

        void setZonesBrightness(byte[] bArr);
    }

    public static AdvancedSettingsFragment newInstance() {
        return new AdvancedSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.productId = this.advancedSettingsFragmentListener.getProductId();
        this.zones = this.advancedSettingsFragmentListener.getZones();
        this.zonesBrightness = this.advancedSettingsFragmentListener.getZonesBrightness();
        this.cecPassthroughEnable = this.advancedSettingsFragmentListener.getCecPassthroughEnable();
        this.cecSwitchingEnable = this.advancedSettingsFragmentListener.getCecSwitchingEnable();
        this.hpdEnable = this.advancedSettingsFragmentListener.getHpdEnable();
        this.usbPowerEnable = this.advancedSettingsFragmentListener.getUsbPowerEnable();
        this.cecPowerEnable = this.advancedSettingsFragmentListener.getCecPowerEnable();
        this.frameDelay = this.advancedSettingsFragmentListener.getFrameDelay();
        this.hdrToneRemapping = this.advancedSettingsFragmentListener.getHdrToneRemapping();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advanced_settings, container, false);
        this.frameDelayTextCurrent = (TextView) view.findViewById(R.id.frameDelayTextCurrent);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.frameDelayTextCurrent.setTypeface(font);
        ((TextView) view.findViewById(R.id.headerText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.zonesText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.cecPassthroughText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.cecSwitchingText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.hpdEnablingText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.usbPowerEnableText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.cecPowerEnableText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.frameDelayText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.hdrToneRemappingText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.widgetText)).setTypeface(font);
        this.topGlow = (ImageView) view.findViewById(R.id.top_glow);
        this.leftGlow = (ImageView) view.findViewById(R.id.left_glow);
        this.rightGlow = (ImageView) view.findViewById(R.id.right_glow);
        this.bottomGlow = (ImageView) view.findViewById(R.id.bottom_glow);
        this.zoneTopButton = (ImageButton) view.findViewById(R.id.zone_top_button);
        this.zoneLeftButton = (ImageButton) view.findViewById(R.id.zone_left_button);
        this.zoneRightButton = (ImageButton) view.findViewById(R.id.zone_right_button);
        this.zoneBottomButton = (ImageButton) view.findViewById(R.id.zone_bottom_button);
        this.topBrightnessSeekbar = (SeekBar) view.findViewById(R.id.topBrightnessSeekbar);
        this.bottomBrightnessSeekbar = (SeekBar) view.findViewById(R.id.bottomBrightnessSeekbar);
        this.leftBrightnessSeekbar = (VerticalSeekBar) view.findViewById(R.id.leftBrightnessSeekbar);
        this.rightBrightnessSeekbar = (VerticalSeekBar) view.findViewById(R.id.rightBrightnessSeekbar);
        this.rootLayout = (LinearLayout) view.findViewById(R.id.rootLayout);
        this.zonesLayout = (LinearLayout) view.findViewById(R.id.zonesLayout);
        this.cecPassthroughLayout = (LinearLayout) view.findViewById(R.id.cecPassthroughLayout);
        this.cecSwitchingLayout = (LinearLayout) view.findViewById(R.id.cecSwitchingLayout);
        this.hpdEnablingLayout = (LinearLayout) view.findViewById(R.id.hpdEnablingLayout);
        this.usbPowerEnableLayout = (LinearLayout) view.findViewById(R.id.usbPowerEnableLayout);
        this.cecPowerEnableLayout = (LinearLayout) view.findViewById(R.id.cecPowerEnableLayout);
        this.frameDelayLayout = (LinearLayout) view.findViewById(R.id.frameDelayLayout);
        this.hdrToneRemappingLayout = (LinearLayout) view.findViewById(R.id.hdrToneRemappingLayout);
        this.widgetLayout = (LinearLayout) view.findViewById(R.id.widgetLayout);
        if (this.advancedSettingsFragmentListener != null) {
            Light currentLight = this.advancedSettingsFragmentListener.getCurrentLight();
            if (currentLight instanceof DreamScreen4K) {
                byte[] picVersionNumber = ((DreamScreen) currentLight).getPicVersionNumber();
                if (picVersionNumber[0] > 5 || (picVersionNumber[0] == 5 && picVersionNumber[1] >= 5)) {
                    this.hdrToneRemappingLayout.setVisibility(0);
                    view.findViewById(R.id.hdrToneRemappingDivider).setVisibility(0);
                }
            } else if (currentLight instanceof DreamScreenSolo) {
                byte[] picVersionNumber2 = ((DreamScreen) currentLight).getPicVersionNumber();
                if (picVersionNumber2[0] > 6 || (picVersionNumber2[0] == 6 && picVersionNumber2[1] >= 1)) {
                    this.hdrToneRemappingLayout.setVisibility(0);
                    view.findViewById(R.id.hdrToneRemappingDivider).setVisibility(0);
                }
            }
        }
        this.widgetDeviceLayout = (LinearLayout) view.findViewById(R.id.widgetDeviceLayout);
        if (this.productId == 7) {
            Log.i(tag, "Hiding features for Solo");
            view.findViewById(R.id.cecPassthroughSpacer).setVisibility(8);
            this.cecPassthroughLayout.setVisibility(8);
            view.findViewById(R.id.cecSwitchingSpacer).setVisibility(8);
            this.cecSwitchingLayout.setVisibility(8);
            view.findViewById(R.id.hpdEnablingSpacer).setVisibility(8);
            this.hpdEnablingLayout.setVisibility(8);
            view.findViewById(R.id.cecPowerEnableSpacer).setVisibility(8);
            this.cecPowerEnableLayout.setVisibility(8);
        }
        this.cecPassthroughToggle = (ImageView) view.findViewById(R.id.cecPassthroughToggle);
        this.cecSwitchingToggle = (ImageView) view.findViewById(R.id.cecSwitchingToggle);
        this.hpdEnablingToggle = (ImageView) view.findViewById(R.id.hpdEnablingToggle);
        this.usbPowerEnableToggle = (ImageView) view.findViewById(R.id.usbPowerEnableToggle);
        this.cecPowerEnableToggle = (ImageView) view.findViewById(R.id.cecPowerEnableToggle);
        this.frameDelaySeekbar = (SeekBar) view.findViewById(R.id.frameDelaySeekbar);
        this.hdrToneRemappingToggle = (ImageView) view.findViewById(R.id.hdrToneRemappingToggle);
        redrawFragment();
        this.zoneTopButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.zoneTapped(3);
            }
        });
        this.zoneLeftButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.zoneTapped(2);
            }
        });
        this.zoneRightButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.zoneTapped(1);
            }
        });
        this.zoneBottomButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.zoneTapped(0);
            }
        });
        this.topBrightnessSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AdvancedSettingsFragment.this.zonesBrightness[0] = (byte) progress;
                if (SystemClock.elapsedRealtime() - AdvancedSettingsFragment.this.packetSentAtTime > AdvancedSettingsFragment.packetSendDelay) {
                    AdvancedSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setZonesBrightness(AdvancedSettingsFragment.this.zonesBrightness);
                    AdvancedSettingsFragment.this.redrawFragment();
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.leftBrightnessSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AdvancedSettingsFragment.this.zonesBrightness[1] = (byte) progress;
                if (SystemClock.elapsedRealtime() - AdvancedSettingsFragment.this.packetSentAtTime > AdvancedSettingsFragment.packetSendDelay) {
                    AdvancedSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setZonesBrightness(AdvancedSettingsFragment.this.zonesBrightness);
                    AdvancedSettingsFragment.this.redrawFragment();
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.rightBrightnessSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AdvancedSettingsFragment.this.zonesBrightness[2] = (byte) progress;
                if (SystemClock.elapsedRealtime() - AdvancedSettingsFragment.this.packetSentAtTime > AdvancedSettingsFragment.packetSendDelay) {
                    AdvancedSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setZonesBrightness(AdvancedSettingsFragment.this.zonesBrightness);
                    AdvancedSettingsFragment.this.redrawFragment();
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.bottomBrightnessSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AdvancedSettingsFragment.this.zonesBrightness[3] = (byte) progress;
                if (SystemClock.elapsedRealtime() - AdvancedSettingsFragment.this.packetSentAtTime > AdvancedSettingsFragment.packetSendDelay) {
                    AdvancedSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setZonesBrightness(AdvancedSettingsFragment.this.zonesBrightness);
                    AdvancedSettingsFragment.this.redrawFragment();
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.leftBrightnessSeekbar.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case 1:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            }
        });
        this.rightBrightnessSeekbar.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case 1:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            }
        });
        this.helpTextView = new TextView(getActivity());
        this.helpTextView.setText("");
        this.helpTextView.setTypeface(font);
        this.helpTextView.setTextSize(12.0f);
        this.helpTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
        this.helpTextView.setPadding(pxToDp(5), 0, pxToDp(5), pxToDp(10));
        view.findViewById(R.id.zonesHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.rootLayout.removeView(AdvancedSettingsFragment.this.helpTextView);
                if (!AdvancedSettingsFragment.this.helpTextView.getText().equals(AdvancedSettingsFragment.this.context.getResources().getString(R.string.zonesHelpText)) || !AdvancedSettingsFragment.this.helpTextView.isShown()) {
                    AdvancedSettingsFragment.this.helpTextView.setText(AdvancedSettingsFragment.this.context.getResources().getString(R.string.zonesHelpText));
                    AdvancedSettingsFragment.this.rootLayout.addView(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.rootLayout.indexOfChild(AdvancedSettingsFragment.this.zonesLayout) + 1, AdvancedSettingsFragment.this.helpLayoutParams);
                    AdvancedSettingsFragment.this.rootLayout.requestChildFocus(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.helpTextView);
                }
            }
        });
        view.findViewById(R.id.cecPassthroughHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.rootLayout.removeView(AdvancedSettingsFragment.this.helpTextView);
                if (!AdvancedSettingsFragment.this.helpTextView.getText().equals(AdvancedSettingsFragment.this.context.getResources().getString(R.string.cecPassthroughHelpText)) || !AdvancedSettingsFragment.this.helpTextView.isShown()) {
                    AdvancedSettingsFragment.this.helpTextView.setText(AdvancedSettingsFragment.this.context.getResources().getString(R.string.cecPassthroughHelpText));
                    AdvancedSettingsFragment.this.rootLayout.addView(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.rootLayout.indexOfChild(AdvancedSettingsFragment.this.cecPassthroughLayout) + 1, AdvancedSettingsFragment.this.helpLayoutParams);
                    AdvancedSettingsFragment.this.rootLayout.requestChildFocus(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.helpTextView);
                }
            }
        });
        view.findViewById(R.id.cecSwitchingHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.rootLayout.removeView(AdvancedSettingsFragment.this.helpTextView);
                if (!AdvancedSettingsFragment.this.helpTextView.getText().equals(AdvancedSettingsFragment.this.context.getResources().getString(R.string.cecSwitchingHelpText)) || !AdvancedSettingsFragment.this.helpTextView.isShown()) {
                    AdvancedSettingsFragment.this.helpTextView.setText(AdvancedSettingsFragment.this.context.getResources().getString(R.string.cecSwitchingHelpText));
                    AdvancedSettingsFragment.this.rootLayout.addView(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.rootLayout.indexOfChild(AdvancedSettingsFragment.this.cecSwitchingLayout) + 1, AdvancedSettingsFragment.this.helpLayoutParams);
                    AdvancedSettingsFragment.this.rootLayout.requestChildFocus(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.helpTextView);
                }
            }
        });
        view.findViewById(R.id.hpdEnablingHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.rootLayout.removeView(AdvancedSettingsFragment.this.helpTextView);
                if (!AdvancedSettingsFragment.this.helpTextView.getText().equals(AdvancedSettingsFragment.this.context.getResources().getString(R.string.hpdEnablingHelpText)) || !AdvancedSettingsFragment.this.helpTextView.isShown()) {
                    AdvancedSettingsFragment.this.helpTextView.setText(AdvancedSettingsFragment.this.context.getResources().getString(R.string.hpdEnablingHelpText));
                    AdvancedSettingsFragment.this.rootLayout.addView(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.rootLayout.indexOfChild(AdvancedSettingsFragment.this.hpdEnablingLayout) + 1, AdvancedSettingsFragment.this.helpLayoutParams);
                    AdvancedSettingsFragment.this.rootLayout.requestChildFocus(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.helpTextView);
                }
            }
        });
        view.findViewById(R.id.usbPowerEnableHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.rootLayout.removeView(AdvancedSettingsFragment.this.helpTextView);
                if (!AdvancedSettingsFragment.this.helpTextView.getText().equals(AdvancedSettingsFragment.this.context.getResources().getString(R.string.usbPowerEnableHelpText)) || !AdvancedSettingsFragment.this.helpTextView.isShown()) {
                    AdvancedSettingsFragment.this.helpTextView.setText(AdvancedSettingsFragment.this.context.getResources().getString(R.string.usbPowerEnableHelpText));
                    AdvancedSettingsFragment.this.rootLayout.addView(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.rootLayout.indexOfChild(AdvancedSettingsFragment.this.usbPowerEnableLayout) + 1, AdvancedSettingsFragment.this.helpLayoutParams);
                    AdvancedSettingsFragment.this.rootLayout.requestChildFocus(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.helpTextView);
                }
            }
        });
        view.findViewById(R.id.cecPowerEnableHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.rootLayout.removeView(AdvancedSettingsFragment.this.helpTextView);
                if (!AdvancedSettingsFragment.this.helpTextView.getText().equals(AdvancedSettingsFragment.this.context.getResources().getString(R.string.cecPowerEnableHelpText)) || !AdvancedSettingsFragment.this.helpTextView.isShown()) {
                    AdvancedSettingsFragment.this.helpTextView.setText(AdvancedSettingsFragment.this.context.getResources().getString(R.string.cecPowerEnableHelpText));
                    AdvancedSettingsFragment.this.rootLayout.addView(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.rootLayout.indexOfChild(AdvancedSettingsFragment.this.cecPowerEnableLayout) + 1, AdvancedSettingsFragment.this.helpLayoutParams);
                    AdvancedSettingsFragment.this.rootLayout.requestChildFocus(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.helpTextView);
                }
            }
        });
        view.findViewById(R.id.frameDelayHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.rootLayout.removeView(AdvancedSettingsFragment.this.helpTextView);
                if (!AdvancedSettingsFragment.this.helpTextView.getText().equals(AdvancedSettingsFragment.this.getContext().getResources().getString(R.string.frameDelayHelpText)) || !AdvancedSettingsFragment.this.helpTextView.isShown()) {
                    AdvancedSettingsFragment.this.helpTextView.setText(AdvancedSettingsFragment.this.getContext().getResources().getString(R.string.frameDelayHelpText));
                    AdvancedSettingsFragment.this.rootLayout.addView(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.rootLayout.indexOfChild(AdvancedSettingsFragment.this.frameDelayLayout) + 1, AdvancedSettingsFragment.this.helpLayoutParams);
                    AdvancedSettingsFragment.this.rootLayout.requestChildFocus(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.helpTextView);
                }
            }
        });
        view.findViewById(R.id.hdrToneRemappingHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.rootLayout.removeView(AdvancedSettingsFragment.this.helpTextView);
                if (!AdvancedSettingsFragment.this.helpTextView.getText().equals(AdvancedSettingsFragment.this.context.getResources().getString(R.string.hdrToneRemappingHelpText)) || !AdvancedSettingsFragment.this.helpTextView.isShown()) {
                    AdvancedSettingsFragment.this.helpTextView.setText(AdvancedSettingsFragment.this.context.getResources().getString(R.string.hdrToneRemappingHelpText));
                    AdvancedSettingsFragment.this.rootLayout.addView(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.rootLayout.indexOfChild(AdvancedSettingsFragment.this.hdrToneRemappingLayout) + 1, AdvancedSettingsFragment.this.helpLayoutParams);
                    AdvancedSettingsFragment.this.rootLayout.requestChildFocus(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.helpTextView);
                }
            }
        });
        view.findViewById(R.id.widgetHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AdvancedSettingsFragment.this.rootLayout.removeView(AdvancedSettingsFragment.this.helpTextView);
                if (!AdvancedSettingsFragment.this.helpTextView.getText().equals(AdvancedSettingsFragment.this.getContext().getResources().getString(R.string.widgetHelpText)) || !AdvancedSettingsFragment.this.helpTextView.isShown()) {
                    AdvancedSettingsFragment.this.helpTextView.setText(AdvancedSettingsFragment.this.getContext().getResources().getString(R.string.widgetHelpText));
                    AdvancedSettingsFragment.this.rootLayout.addView(AdvancedSettingsFragment.this.helpTextView, AdvancedSettingsFragment.this.rootLayout.indexOfChild(AdvancedSettingsFragment.this.widgetLayout) + 1, AdvancedSettingsFragment.this.helpLayoutParams);
                    AdvancedSettingsFragment.this.rootLayout.requestChildFocus(AdvancedSettingsFragment.this.widgetDeviceLayout, AdvancedSettingsFragment.this.widgetDeviceLayout);
                }
            }
        });
        this.cecPassthroughToggle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AdvancedSettingsFragment.this.cecPassthroughEnable == 0) {
                    AdvancedSettingsFragment.this.cecPassthroughEnable = 1;
                } else {
                    AdvancedSettingsFragment.this.cecPassthroughEnable = 0;
                }
                AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setCecPassthroughEnable(AdvancedSettingsFragment.this.cecPassthroughEnable);
                AdvancedSettingsFragment.this.redrawFragment();
            }
        });
        this.cecSwitchingToggle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AdvancedSettingsFragment.this.cecSwitchingEnable == 0) {
                    AdvancedSettingsFragment.this.cecSwitchingEnable = 1;
                } else {
                    AdvancedSettingsFragment.this.cecSwitchingEnable = 0;
                }
                AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setCecSwitchingEnable(AdvancedSettingsFragment.this.cecSwitchingEnable);
                AdvancedSettingsFragment.this.redrawFragment();
            }
        });
        this.hpdEnablingToggle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AdvancedSettingsFragment.this.hpdEnable == 0) {
                    AdvancedSettingsFragment.this.hpdEnable = 1;
                } else {
                    AdvancedSettingsFragment.this.hpdEnable = 0;
                }
                AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setHpdEnable(AdvancedSettingsFragment.this.hpdEnable);
                AdvancedSettingsFragment.this.redrawFragment();
            }
        });
        this.usbPowerEnableToggle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AdvancedSettingsFragment.this.usbPowerEnable == 0) {
                    AdvancedSettingsFragment.this.usbPowerEnable = 1;
                } else {
                    AdvancedSettingsFragment.this.usbPowerEnable = 0;
                }
                AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setUsbPowerEnable(AdvancedSettingsFragment.this.usbPowerEnable);
                AdvancedSettingsFragment.this.redrawFragment();
            }
        });
        this.cecPowerEnableToggle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AdvancedSettingsFragment.this.cecPowerEnable == 0) {
                    AdvancedSettingsFragment.this.cecPowerEnable = 1;
                } else {
                    AdvancedSettingsFragment.this.cecPowerEnable = 0;
                }
                AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setCecPowerEnable(AdvancedSettingsFragment.this.cecPowerEnable);
                AdvancedSettingsFragment.this.redrawFragment();
            }
        });
        this.frameDelaySeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AdvancedSettingsFragment.this.frameDelay = progress;
                if (SystemClock.elapsedRealtime() - AdvancedSettingsFragment.this.packetSentAtTime > AdvancedSettingsFragment.packetSendDelay || progress == 0) {
                    AdvancedSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setFrameDelay(progress);
                    AdvancedSettingsFragment.this.frameDelayTextCurrent.setText("" + progress);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.hdrToneRemappingToggle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AdvancedSettingsFragment.this.hdrToneRemapping == 0) {
                    AdvancedSettingsFragment.this.hdrToneRemapping = 1;
                } else {
                    AdvancedSettingsFragment.this.hdrToneRemapping = 0;
                }
                AdvancedSettingsFragment.this.advancedSettingsFragmentListener.setHdrToneRemapping(AdvancedSettingsFragment.this.hdrToneRemapping);
                AdvancedSettingsFragment.this.redrawFragment();
            }
        });
        LinkedList<LinkedList<Light>> groups = null;
        if (this.advancedSettingsFragmentListener != null) {
            groups = this.advancedSettingsFragmentListener.getGroups();
        }
        if (groups != null && !groups.isEmpty()) {
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            String ipAddress = ControlWidget.getPreferenceString(getContext(), "ip");
            Iterator it = groups.iterator();
            while (it.hasNext()) {
                Iterator it2 = ((LinkedList) it.next()).iterator();
                while (it2.hasNext()) {
                    final Light light = (Light) it2.next();
                    if (light instanceof DreamScreen) {
                        final TextView lightText = new TextView(getContext());
                        lightText.setLayoutParams(layoutParams);
                        lightText.setTypeface(font);
                        lightText.setTextSize(15.0f);
                        lightText.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryLightTextColor));
                        lightText.setGravity(16);
                        lightText.setText(light.getName());
                        lightText.setCompoundDrawablePadding(pxToDp(8));
                        lightText.setPadding(pxToDp(12), pxToDp(6), pxToDp(12), pxToDp(6));
                        if (light.getProductId() == 1) {
                            lightText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dreamscreen_icon, 0, 0, 0);
                        } else if (light.getProductId() == 2) {
                            lightText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dreamscreen4k_icon, 0, 0, 0);
                        } else if (light.getProductId() == 7) {
                            lightText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dreamscreensolo_icon, 0, 0, 0);
                        } else {
                            Log.i(tag, "productId not handled");
                        }
                        lightText.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                if (AdvancedSettingsFragment.this.currentWidgetText == lightText) {
                                    Log.i(AdvancedSettingsFragment.tag, light.getName() + "attributes removed from widget");
                                    AdvancedSettingsFragment.this.currentWidgetText.setBackgroundColor(0);
                                    AdvancedSettingsFragment.this.currentWidgetText = null;
                                    ControlWidget.deletePreferenceKey(AdvancedSettingsFragment.this.context, "ip");
                                    ControlWidget.deletePreferenceKey(AdvancedSettingsFragment.this.context, "productId");
                                } else {
                                    Log.i(AdvancedSettingsFragment.tag, light.getName() + "attributes saved for widget");
                                    if (AdvancedSettingsFragment.this.currentWidgetText != null) {
                                        AdvancedSettingsFragment.this.currentWidgetText.setBackgroundColor(0);
                                    }
                                    AdvancedSettingsFragment.this.currentWidgetText = lightText;
                                    AdvancedSettingsFragment.this.currentWidgetText.setBackgroundColor(ContextCompat.getColor(AdvancedSettingsFragment.this.getContext(), R.color.colorDrawerItemSelected));
                                    ControlWidget.setPreferenceString(AdvancedSettingsFragment.this.getContext(), "ip", light.getIp());
                                    ControlWidget.setPreferenceInt(AdvancedSettingsFragment.this.getContext(), "productId", light.getProductId());
                                }
                                ControlWidget.updateAllDreamScreenWidgets(AdvancedSettingsFragment.this.getContext());
                            }
                        });
                        if (ipAddress != null && ipAddress.equals(light.getIp())) {
                            this.currentWidgetText = lightText;
                            this.currentWidgetText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorDrawerItemSelected));
                        }
                        this.widgetDeviceLayout.addView(lightText);
                    }
                }
            }
        }
        if (this.shouldScrollToWidgetSettings) {
            this.shouldScrollToWidgetSettings = false;
            scrollToWidgetSettings();
        }
        return view;
    }

    /* access modifiers changed from: private */
    public void zoneTapped(int index) {
        this.zones = (byte) (this.zones ^ (1 << index));
        this.advancedSettingsFragmentListener.setZones(this.zones);
        redrawFragment();
    }

    /* access modifiers changed from: private */
    public void redrawFragment() {
        this.zoneTopButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.zoneLeftButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.zoneRightButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.zoneBottomButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.topGlow.setVisibility(4);
        this.leftGlow.setVisibility(4);
        this.rightGlow.setVisibility(4);
        this.bottomGlow.setVisibility(4);
        this.topBrightnessSeekbar.setVisibility(4);
        this.leftBrightnessSeekbar.setVisibility(4);
        this.rightBrightnessSeekbar.setVisibility(4);
        this.bottomBrightnessSeekbar.setVisibility(4);
        if (((this.zones >> 3) & 1) == 1) {
            this.zoneTopButton.setColorFilter(null);
            this.topGlow.setVisibility(0);
            this.topGlow.setColorFilter(Color.parseColor("#" + String.format("%02X", new Object[]{Integer.valueOf((this.zonesBrightness[0] ^ -1) & 255)}) + "000000"));
            this.topBrightnessSeekbar.setVisibility(0);
            this.topBrightnessSeekbar.setProgress(this.zonesBrightness[0] & 255);
        }
        if (((this.zones >> 2) & 1) == 1) {
            this.zoneLeftButton.setColorFilter(null);
            this.leftGlow.setVisibility(0);
            this.leftGlow.setColorFilter(Color.parseColor("#" + String.format("%02X", new Object[]{Integer.valueOf((this.zonesBrightness[1] ^ -1) & 255)}) + "000000"));
            this.leftBrightnessSeekbar.setVisibility(0);
            this.leftBrightnessSeekbar.setProgress(this.zonesBrightness[1] & 255);
        }
        if (((this.zones >> 1) & 1) == 1) {
            this.zoneRightButton.setColorFilter(null);
            this.rightGlow.setVisibility(0);
            this.rightGlow.setColorFilter(Color.parseColor("#" + String.format("%02X", new Object[]{Integer.valueOf((this.zonesBrightness[2] ^ -1) & 255)}) + "000000"));
            this.rightBrightnessSeekbar.setVisibility(0);
            this.rightBrightnessSeekbar.setProgress(this.zonesBrightness[2] & 255);
        }
        if ((this.zones & 1) == 1) {
            this.zoneBottomButton.setColorFilter(null);
            this.bottomGlow.setVisibility(0);
            this.bottomGlow.setColorFilter(Color.parseColor("#" + String.format("%02X", new Object[]{Integer.valueOf((this.zonesBrightness[3] ^ -1) & 255)}) + "000000"));
            this.bottomBrightnessSeekbar.setVisibility(0);
            this.bottomBrightnessSeekbar.setProgress(this.zonesBrightness[3] & 255);
        }
        if (this.cecPassthroughEnable == 0) {
            this.cecPassthroughToggle.setImageResource(R.drawable.toggle2_off);
        } else {
            this.cecPassthroughToggle.setImageResource(R.drawable.toggle2_on);
        }
        if (this.cecSwitchingEnable == 0) {
            this.cecSwitchingToggle.setImageResource(R.drawable.toggle2_off);
        } else {
            this.cecSwitchingToggle.setImageResource(R.drawable.toggle2_on);
        }
        if (this.hpdEnable == 0) {
            this.hpdEnablingToggle.setImageResource(R.drawable.toggle2_off);
        } else {
            this.hpdEnablingToggle.setImageResource(R.drawable.toggle2_on);
        }
        if (this.usbPowerEnable == 0) {
            this.usbPowerEnableToggle.setImageResource(R.drawable.toggle2_off);
        } else {
            this.usbPowerEnableToggle.setImageResource(R.drawable.toggle2_on);
        }
        if (this.cecPowerEnable == 0) {
            this.cecPowerEnableToggle.setImageResource(R.drawable.toggle2_off);
        } else {
            this.cecPowerEnableToggle.setImageResource(R.drawable.toggle2_on);
        }
        this.frameDelaySeekbar.setProgress(this.frameDelay);
        this.frameDelayTextCurrent.setText("" + this.frameDelay);
        if (this.hdrToneRemapping == 0) {
            this.hdrToneRemappingToggle.setImageResource(R.drawable.toggle2_off);
        } else {
            this.hdrToneRemappingToggle.setImageResource(R.drawable.toggle2_on);
        }
    }

    public void setFlagToScrollToWidgetSettings(boolean shouldScrollToWidgetSettings2) {
        this.shouldScrollToWidgetSettings = shouldScrollToWidgetSettings2;
    }

    public void scrollToWidgetSettings() {
        Log.i(tag, "scrollToWidgetSettings");
        this.rootLayout.removeView(this.helpTextView);
        if (!this.helpTextView.getText().equals(getContext().getResources().getString(R.string.widgetHelpText)) || !this.helpTextView.isShown()) {
            this.helpTextView.setText(getContext().getResources().getString(R.string.widgetHelpText));
            this.rootLayout.addView(this.helpTextView, this.rootLayout.indexOfChild(this.widgetLayout) + 1, this.helpLayoutParams);
            this.rootLayout.requestChildFocus(this.widgetDeviceLayout, this.widgetDeviceLayout);
        }
    }

    private int pxToDp(int px) {
        return (int) TypedValue.applyDimension(1, (float) px, this.context.getResources().getDisplayMetrics());
    }

    public void onAttach(Context context2) {
        super.onAttach(context2);
        if (context2 instanceof AdvancedSettingsFragmentListener) {
            this.advancedSettingsFragmentListener = (AdvancedSettingsFragmentListener) context2;
            this.context = context2;
            return;
        }
        throw new RuntimeException(context2.toString() + " must implement SystemSettingsFragmentListener");
    }

    public void onDetach() {
        super.onDetach();
        this.advancedSettingsFragmentListener = null;
    }
}

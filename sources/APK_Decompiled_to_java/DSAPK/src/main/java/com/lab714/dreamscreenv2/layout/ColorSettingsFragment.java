package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;

public class ColorSettingsFragment extends Fragment {
    private static final long packetSendDelay = 30;
    private static final String tag = "ColorSettingsFrag";
    private SeekBar blueSaturationSeekBar;
    /* access modifiers changed from: private */
    public TextView blueSaturationText;
    /* access modifiers changed from: private */
    public ColorSettingsFragmentListener colorSettingsFragmentListener;
    private SeekBar greenSaturationSeekBar;
    /* access modifiers changed from: private */
    public TextView greenSaturationText;
    /* access modifiers changed from: private */
    public final LayoutParams helpLayoutParams = new LayoutParams(-1, -2);
    /* access modifiers changed from: private */
    public TextView helpTextView;
    /* access modifiers changed from: private */
    public long packetSentAtTime = SystemClock.elapsedRealtime();
    private SeekBar redSaturationSeekBar;
    /* access modifiers changed from: private */
    public TextView redSaturationText;
    /* access modifiers changed from: private */
    public LinearLayout rootLayout;
    /* access modifiers changed from: private */
    public byte[] saturation = {-1, -1, -1};
    private ImageView saturationHelp;
    /* access modifiers changed from: private */
    public LinearLayout saturationLayout;

    public interface ColorSettingsFragmentListener {
        byte[] getSaturation();

        void setSaturation(byte[] bArr);
    }

    public static ColorSettingsFragment newInstance() {
        return new ColorSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.saturation = this.colorSettingsFragmentListener.getSaturation();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_settings, container, false);
        this.redSaturationText = (TextView) view.findViewById(R.id.redSaturationText);
        this.greenSaturationText = (TextView) view.findViewById(R.id.greenSaturationText);
        this.blueSaturationText = (TextView) view.findViewById(R.id.blueSaturationText);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.redSaturationText.setTypeface(font);
        this.greenSaturationText.setTypeface(font);
        this.blueSaturationText.setTypeface(font);
        ((TextView) view.findViewById(R.id.headerText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.saturationText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.redText2)).setTypeface(font);
        ((TextView) view.findViewById(R.id.greenText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.blueText)).setTypeface(font);
        this.redSaturationSeekBar = (SeekBar) view.findViewById(R.id.SeekBar_Red);
        this.greenSaturationSeekBar = (SeekBar) view.findViewById(R.id.SeekBar_Green);
        this.blueSaturationSeekBar = (SeekBar) view.findViewById(R.id.SeekBar_blue);
        this.redSaturationSeekBar.setProgress(this.saturation[0] & 255);
        this.greenSaturationSeekBar.setProgress(this.saturation[1] & 255);
        this.blueSaturationSeekBar.setProgress(this.saturation[2] & 255);
        this.redSaturationText.setText(String.valueOf(Math.round((((float) this.redSaturationSeekBar.getProgress()) / 255.0f) * 100.0f)) + "%");
        this.greenSaturationText.setText(String.valueOf(Math.round((((float) this.greenSaturationSeekBar.getProgress()) / 255.0f) * 100.0f)) + "%");
        this.blueSaturationText.setText(String.valueOf(Math.round((((float) this.blueSaturationSeekBar.getProgress()) / 255.0f) * 100.0f)) + "%");
        this.rootLayout = (LinearLayout) view.findViewById(R.id.rootLayout);
        this.saturationLayout = (LinearLayout) view.findViewById(R.id.saturationLayout);
        this.saturationHelp = (ImageView) view.findViewById(R.id.saturationHelp);
        this.redSaturationSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getProgress() < 64) {
                    seekBar.setProgress(64);
                } else if (ColorSettingsFragment.this.saturation[0] != ((byte) progress) && SystemClock.elapsedRealtime() - ColorSettingsFragment.this.packetSentAtTime > ColorSettingsFragment.packetSendDelay) {
                    ColorSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    ColorSettingsFragment.this.saturation[0] = (byte) progress;
                    ColorSettingsFragment.this.colorSettingsFragmentListener.setSaturation(ColorSettingsFragment.this.saturation);
                    ColorSettingsFragment.this.redSaturationText.setText(String.valueOf(Math.round((((float) progress) / 255.0f) * 100.0f)) + "%");
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.greenSaturationSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getProgress() < 64) {
                    seekBar.setProgress(64);
                } else if (ColorSettingsFragment.this.saturation[1] != ((byte) progress) && SystemClock.elapsedRealtime() - ColorSettingsFragment.this.packetSentAtTime > ColorSettingsFragment.packetSendDelay) {
                    ColorSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    ColorSettingsFragment.this.saturation[1] = (byte) progress;
                    ColorSettingsFragment.this.colorSettingsFragmentListener.setSaturation(ColorSettingsFragment.this.saturation);
                    ColorSettingsFragment.this.greenSaturationText.setText(String.valueOf(Math.round((((float) progress) / 255.0f) * 100.0f)) + "%");
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.blueSaturationSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getProgress() < 64) {
                    seekBar.setProgress(64);
                } else if (ColorSettingsFragment.this.saturation[2] != ((byte) progress) && SystemClock.elapsedRealtime() - ColorSettingsFragment.this.packetSentAtTime > ColorSettingsFragment.packetSendDelay) {
                    ColorSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    ColorSettingsFragment.this.saturation[2] = (byte) progress;
                    ColorSettingsFragment.this.colorSettingsFragmentListener.setSaturation(ColorSettingsFragment.this.saturation);
                    ColorSettingsFragment.this.blueSaturationText.setText(String.valueOf(Math.round((((float) progress) / 255.0f) * 100.0f)) + "%");
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.helpTextView = new TextView(getActivity());
        this.helpTextView.setText("");
        this.helpTextView.setTypeface(font);
        this.helpTextView.setTextSize(12.0f);
        this.helpTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
        this.helpTextView.setPadding(pxToDp(5), 0, pxToDp(5), pxToDp(10));
        this.saturationHelp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ColorSettingsFragment.this.rootLayout.removeView(ColorSettingsFragment.this.helpTextView);
                if (!ColorSettingsFragment.this.helpTextView.getText().equals(ColorSettingsFragment.this.getContext().getResources().getString(R.string.saturationHelpTextSideKick)) || !ColorSettingsFragment.this.helpTextView.isShown()) {
                    ColorSettingsFragment.this.helpTextView.setText(ColorSettingsFragment.this.getContext().getResources().getString(R.string.saturationHelpTextSideKick));
                    ColorSettingsFragment.this.rootLayout.addView(ColorSettingsFragment.this.helpTextView, ColorSettingsFragment.this.rootLayout.indexOfChild(ColorSettingsFragment.this.saturationLayout) + 1, ColorSettingsFragment.this.helpLayoutParams);
                    ColorSettingsFragment.this.rootLayout.requestChildFocus(ColorSettingsFragment.this.helpTextView, ColorSettingsFragment.this.helpTextView);
                }
            }
        });
        return view;
    }

    private int pxToDp(int px) {
        return (int) TypedValue.applyDimension(1, (float) px, getContext().getResources().getDisplayMetrics());
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ColorSettingsFragmentListener) {
            this.colorSettingsFragmentListener = (ColorSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement Listener");
    }

    public void onDetach() {
        super.onDetach();
        this.colorSettingsFragmentListener = null;
    }
}

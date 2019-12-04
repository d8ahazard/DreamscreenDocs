package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.internal.view.SupportMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;

public class AudioSettingsFragment extends Fragment {
    private static final String tag = "AudioSettingsFrag";
    /* access modifiers changed from: private */
    public AudioSettingsFragmentListener audioSettingsFragmentListener;
    private SeekBar bassBrightness;
    private ImageButton bassMarker;
    private TextView bassValueText;
    private SeekBar middleBrightness;
    private ImageButton middleMarker;
    private TextView middleValueText;
    /* access modifiers changed from: private */
    public byte[] musicModeColors = {2, 1, 0};
    /* access modifiers changed from: private */
    public int musicModeType = 0;
    /* access modifiers changed from: private */
    public byte[] musicModeWeights = {100, 100, 100};
    /* access modifiers changed from: private */
    public int oldMusicModeType = -1;
    private SeekBar trebleBrightness;
    private ImageButton trebleMarker;
    private TextView trebleValueText;
    private ImageButton visualizer1Button;
    private ImageButton visualizer2Button;
    private ImageButton visualizer3Button;
    private ImageButton visualizer4Button;

    public interface AudioSettingsFragmentListener {
        byte[] getMusicModeColors();

        int getMusicModeType();

        byte[] getMusicModeWeights();

        void setMusicModeColors(byte[] bArr);

        void setMusicModeType(int i);

        void setMusicModeWeight(byte[] bArr);
    }

    public static AudioSettingsFragment newInstance() {
        return new AudioSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.musicModeType = this.audioSettingsFragmentListener.getMusicModeType();
        this.musicModeColors = this.audioSettingsFragmentListener.getMusicModeColors();
        this.musicModeWeights = this.audioSettingsFragmentListener.getMusicModeWeights();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio_settings, container, false);
        this.trebleValueText = (TextView) view.findViewById(R.id.trebleValueText);
        this.middleValueText = (TextView) view.findViewById(R.id.middleValueText);
        this.bassValueText = (TextView) view.findViewById(R.id.bassValueText);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        ((TextView) view.findViewById(R.id.headerText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.tmbText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.visualizersText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.trebleText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.middleText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.bassText)).setTypeface(font);
        this.trebleValueText.setTypeface(font);
        this.middleValueText.setTypeface(font);
        this.bassValueText.setTypeface(font);
        this.visualizer1Button = (ImageButton) view.findViewById(R.id.visual1_button);
        this.visualizer2Button = (ImageButton) view.findViewById(R.id.visual2_button);
        this.visualizer3Button = (ImageButton) view.findViewById(R.id.visual3_button);
        this.visualizer4Button = (ImageButton) view.findViewById(R.id.visual4_button);
        this.trebleMarker = (ImageButton) view.findViewById(R.id.trebleMarker);
        this.middleMarker = (ImageButton) view.findViewById(R.id.middleMarker);
        this.bassMarker = (ImageButton) view.findViewById(R.id.bassMarker);
        this.trebleBrightness = (SeekBar) view.findViewById(R.id.trebleBrightness);
        this.middleBrightness = (SeekBar) view.findViewById(R.id.middleBrightness);
        this.bassBrightness = (SeekBar) view.findViewById(R.id.bassBrightness);
        redrawFragment();
        this.visualizer1Button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AudioSettingsFragment.this.oldMusicModeType = AudioSettingsFragment.this.musicModeType;
                AudioSettingsFragment.this.musicModeType = 0;
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeType(AudioSettingsFragment.this.musicModeType);
                AudioSettingsFragment.this.redrawFragment();
            }
        });
        this.visualizer2Button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AudioSettingsFragment.this.oldMusicModeType = AudioSettingsFragment.this.musicModeType;
                AudioSettingsFragment.this.musicModeType = 1;
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeType(AudioSettingsFragment.this.musicModeType);
                AudioSettingsFragment.this.redrawFragment();
            }
        });
        this.visualizer3Button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AudioSettingsFragment.this.oldMusicModeType = AudioSettingsFragment.this.musicModeType;
                AudioSettingsFragment.this.musicModeType = 2;
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeType(AudioSettingsFragment.this.musicModeType);
                AudioSettingsFragment.this.redrawFragment();
            }
        });
        this.visualizer4Button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AudioSettingsFragment.this.oldMusicModeType = AudioSettingsFragment.this.musicModeType;
                AudioSettingsFragment.this.musicModeType = 3;
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeType(AudioSettingsFragment.this.musicModeType);
                AudioSettingsFragment.this.redrawFragment();
            }
        });
        this.trebleMarker.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                switch (AudioSettingsFragment.this.musicModeColors[0]) {
                    case 0:
                        AudioSettingsFragment.this.musicModeColors[0] = 1;
                        break;
                    case 1:
                        AudioSettingsFragment.this.musicModeColors[0] = 2;
                        break;
                    case 2:
                        AudioSettingsFragment.this.musicModeColors[0] = 0;
                        break;
                }
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeColors(AudioSettingsFragment.this.musicModeColors);
                AudioSettingsFragment.this.redrawFragment();
            }
        });
        this.middleMarker.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                switch (AudioSettingsFragment.this.musicModeColors[1]) {
                    case 0:
                        AudioSettingsFragment.this.musicModeColors[1] = 1;
                        break;
                    case 1:
                        AudioSettingsFragment.this.musicModeColors[1] = 2;
                        break;
                    case 2:
                        AudioSettingsFragment.this.musicModeColors[1] = 0;
                        break;
                }
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeColors(AudioSettingsFragment.this.musicModeColors);
                AudioSettingsFragment.this.redrawFragment();
            }
        });
        this.bassMarker.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                switch (AudioSettingsFragment.this.musicModeColors[2]) {
                    case 0:
                        AudioSettingsFragment.this.musicModeColors[2] = 1;
                        break;
                    case 1:
                        AudioSettingsFragment.this.musicModeColors[2] = 2;
                        break;
                    case 2:
                        AudioSettingsFragment.this.musicModeColors[2] = 0;
                        break;
                }
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeColors(AudioSettingsFragment.this.musicModeColors);
                AudioSettingsFragment.this.redrawFragment();
            }
        });
        this.trebleBrightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AudioSettingsFragment.this.musicModeWeights[0] = (byte) progress;
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeWeight(AudioSettingsFragment.this.musicModeWeights);
                AudioSettingsFragment.this.redrawFragment();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.middleBrightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AudioSettingsFragment.this.musicModeWeights[1] = (byte) progress;
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeWeight(AudioSettingsFragment.this.musicModeWeights);
                AudioSettingsFragment.this.redrawFragment();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.bassBrightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AudioSettingsFragment.this.musicModeWeights[2] = (byte) progress;
                AudioSettingsFragment.this.audioSettingsFragmentListener.setMusicModeWeight(AudioSettingsFragment.this.musicModeWeights);
                AudioSettingsFragment.this.redrawFragment();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return view;
    }

    /* access modifiers changed from: private */
    public void redrawFragment() {
        switch (this.oldMusicModeType) {
            case 0:
                this.visualizer1Button.setImageResource(R.drawable.visualizer1);
                break;
            case 1:
                this.visualizer2Button.setImageResource(R.drawable.visualizer2);
                break;
            case 2:
                this.visualizer3Button.setImageResource(R.drawable.visualizer3);
                break;
            case 3:
                this.visualizer4Button.setImageResource(R.drawable.visualizer4);
                break;
        }
        switch (this.musicModeType) {
            case 0:
                this.visualizer1Button.setImageResource(R.drawable.visualizer1_on);
                break;
            case 1:
                this.visualizer2Button.setImageResource(R.drawable.visualizer2_on);
                break;
            case 2:
                this.visualizer3Button.setImageResource(R.drawable.visualizer3_on);
                break;
            case 3:
                this.visualizer4Button.setImageResource(R.drawable.visualizer4_on);
                break;
        }
        switch (this.musicModeColors[0]) {
            case 0:
                this.trebleMarker.setColorFilter(SupportMenu.CATEGORY_MASK);
                break;
            case 1:
                this.trebleMarker.setColorFilter(-16711936);
                break;
            case 2:
                this.trebleMarker.setColorFilter(-16776961);
                break;
        }
        switch (this.musicModeColors[1]) {
            case 0:
                this.middleMarker.setColorFilter(SupportMenu.CATEGORY_MASK);
                break;
            case 1:
                this.middleMarker.setColorFilter(-16711936);
                break;
            case 2:
                this.middleMarker.setColorFilter(-16776961);
                break;
        }
        switch (this.musicModeColors[2]) {
            case 0:
                this.bassMarker.setColorFilter(SupportMenu.CATEGORY_MASK);
                break;
            case 1:
                this.bassMarker.setColorFilter(-16711936);
                break;
            case 2:
                this.bassMarker.setColorFilter(-16776961);
                break;
        }
        this.trebleBrightness.setProgress(this.musicModeWeights[0] & 255);
        this.middleBrightness.setProgress(this.musicModeWeights[1] & 255);
        this.bassBrightness.setProgress(this.musicModeWeights[2] & 255);
        this.trebleValueText.setText(String.valueOf(this.trebleBrightness.getProgress()) + "%");
        this.middleValueText.setText(String.valueOf(this.middleBrightness.getProgress()) + "%");
        this.bassValueText.setText(String.valueOf(this.bassBrightness.getProgress()) + "%");
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AudioSettingsFragmentListener) {
            this.audioSettingsFragmentListener = (AudioSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement Listener");
    }

    public void onDetach() {
        super.onDetach();
        this.audioSettingsFragmentListener = null;
    }
}

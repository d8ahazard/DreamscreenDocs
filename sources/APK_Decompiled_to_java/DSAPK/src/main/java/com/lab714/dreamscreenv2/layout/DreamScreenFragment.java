package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.lab714.dreamscreenv2.R;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class DreamScreenFragment extends Fragment {
    private static final int maxDisabledTaps = 5;
    private static final long packetSendDelay = 30;
    private static final String tag = "DreamScreenFrag";
    private AlertDialog alertDialog;
    private ImageButton ambientButton;
    /* access modifiers changed from: private */
    public int ambientLayoutType = 0;
    /* access modifiers changed from: private */
    public int ambientModeType = 0;
    /* access modifiers changed from: private */
    public int ambientShowType = 0;
    /* access modifiers changed from: private */
    public RelativeLayout ambientStartContainer;
    private ImageButton audioButton;
    private RelativeLayout audioContainer;
    /* access modifiers changed from: private */
    public ImageButton audioJackButton;
    private LinearLayout audioJackLayout;
    private SeekBar blueSeekBar;
    /* access modifiers changed from: private */
    public TextView blueValueText;
    /* access modifiers changed from: private */
    public int brightness = 0;
    private SeekBar brightnessSeekBar;
    /* access modifiers changed from: private */
    public ImageView colorWheel;
    private LinearLayout colorWheelContainer;
    /* access modifiers changed from: private */
    public ImageView colorWheelPicker;
    /* access modifiers changed from: private */
    public final int[] currentAmbientColor = {255, 255, 255};
    /* access modifiers changed from: private */
    public View currentContainer;
    /* access modifiers changed from: private */
    public int currentModeColor;
    /* access modifiers changed from: private */
    public DreamScreenFragmentListener dreamScreenFragmentListener;
    /* access modifiers changed from: private */
    public Animation fadeInAnimation;
    private Animation fadeOutAnimation;
    /* access modifiers changed from: private */
    public int featureDisabledCount = 0;
    private ImageButton fireButton;
    private ImageButton forestButton;
    private SeekBar greenSeekBar;
    /* access modifiers changed from: private */
    public TextView greenValueText;
    /* access modifiers changed from: private */
    public ImageButton hdmi1Button;
    private ImageButton hdmi1Edit;
    /* access modifiers changed from: private */
    public EditText hdmi1Text;
    /* access modifiers changed from: private */
    public ImageButton hdmi2Button;
    private ImageButton hdmi2Edit;
    /* access modifiers changed from: private */
    public EditText hdmi2Text;
    /* access modifiers changed from: private */
    public ImageButton hdmi3Button;
    private ImageButton hdmi3Edit;
    /* access modifiers changed from: private */
    public EditText hdmi3Text;
    private byte hdmiActiveChannels = 0;
    /* access modifiers changed from: private */
    public ImageButton hdmiAudioButton;
    private LinearLayout hdmiAudioLayout;
    /* access modifiers changed from: private */
    public int hdmiInput = -1;
    private byte[] hdmiInputName1;
    private byte[] hdmiInputName2;
    private byte[] hdmiInputName3;
    /* access modifiers changed from: private */
    public LinearLayout hdmiLayout;
    /* access modifiers changed from: private */
    public Animation hdmiSwitchingAnimation;
    private ImageButton holidayButton;
    private ImageButton julyButton;
    /* access modifiers changed from: private */
    public int mode = 0;
    /* access modifiers changed from: private */
    public int musicModeSource = 0;
    /* access modifiers changed from: private */
    public View newContainer;
    private ImageButton oceanButton;
    /* access modifiers changed from: private */
    public ImageView offContainer;
    private ImageButton oldShowButton;
    /* access modifiers changed from: private */
    public long packetSentAtTime = SystemClock.elapsedRealtime();
    private ImageButton popButton;
    private ImageButton powerButton;
    private ImageButton prideButton;
    /* access modifiers changed from: private */
    public int productId = 0;
    private ImageButton randomButton;
    private SeekBar redSeekBar;
    /* access modifiers changed from: private */
    public TextView redValueText;
    private LinearLayout showContainer;
    /* access modifiers changed from: private */
    public Animation sleepModeBreathingAnimation;
    private LinearLayout slidersContainer;
    /* access modifiers changed from: private */
    public ImageView slidersPicker;
    private ImageButton twinkleButton;
    private ImageButton userEdittingLockButton;
    /* access modifiers changed from: private */
    public boolean userEdittingUnlocked = false;
    private ImageButton videoButton;
    private ImageView videoContainer;
    private View view;
    /* access modifiers changed from: private */
    public ImageView whitesWheel;
    private LinearLayout whitesWheelContainer;
    /* access modifiers changed from: private */
    public ImageView whitesWheelPicker;

    public interface DreamScreenFragmentListener {
        boolean currentLightGroupedWithConnect();

        boolean currentLightGroupedWithDreamScreen();

        byte[] getAmbientColor();

        int getAmbientModeType();

        int getAmbientShowType();

        int getBrightness();

        byte getHdmiActiveChannels();

        int getHdmiInput();

        byte[] getHdmiInputName1();

        byte[] getHdmiInputName2();

        byte[] getHdmiInputName3();

        int getLightType();

        int getMode();

        int getMusicModeSource();

        LinkedList<String> getNewDevices();

        int getProductId();

        void openDrawer();

        void readHdmiActiveChannels();

        void readHdmiInput();

        void setAmbientColor(byte[] bArr);

        void setAmbientColorConstant(byte[] bArr);

        void setAmbientModeType(int i);

        void setAmbientShowType(int i);

        void setBrightness(int i);

        void setBrightnessConstant(int i);

        void setHdmiInput(int i);

        void setHdmiInputName1(byte[] bArr);

        void setHdmiInputName2(byte[] bArr);

        void setHdmiInputName3(byte[] bArr);

        void setMenuIcon();

        void setMode(int i);

        void setMusicModeSource(int i);
    }

    public static DreamScreenFragment newInstance() {
        return new DreamScreenFragment();
    }

    public void onResume() {
        super.onResume();
        if (this.slidersContainer.isShown()) {
            this.ambientLayoutType = 0;
            redrawFragment();
        }
        if (this.productId == 4 && this.dreamScreenFragmentListener != null) {
            if (this.dreamScreenFragmentListener.getLightType() == 0) {
                this.brightnessSeekBar.setAlpha(0.3f);
            } else {
                this.brightnessSeekBar.setAlpha(1.0f);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(tag, "onCreate");
        this.productId = this.dreamScreenFragmentListener.getProductId();
        this.mode = this.dreamScreenFragmentListener.getMode();
        if (this.mode < 0 || this.mode > 3) {
            this.mode = 0;
        }
        this.brightness = this.dreamScreenFragmentListener.getBrightness();
        byte[] currentAmbientBytes = this.dreamScreenFragmentListener.getAmbientColor();
        this.currentAmbientColor[0] = currentAmbientBytes[0] & 255;
        this.currentAmbientColor[1] = currentAmbientBytes[1] & 255;
        this.currentAmbientColor[2] = currentAmbientBytes[2] & 255;
        if (this.productId == 1 || this.productId == 2) {
            this.hdmiInputName1 = this.dreamScreenFragmentListener.getHdmiInputName1();
            this.hdmiInputName2 = this.dreamScreenFragmentListener.getHdmiInputName2();
            this.hdmiInputName3 = this.dreamScreenFragmentListener.getHdmiInputName3();
            this.hdmiInput = this.dreamScreenFragmentListener.getHdmiInput();
            this.hdmiActiveChannels = this.dreamScreenFragmentListener.getHdmiActiveChannels();
            this.musicModeSource = this.dreamScreenFragmentListener.getMusicModeSource();
        } else if (this.productId == 7) {
            this.musicModeSource = this.dreamScreenFragmentListener.getMusicModeSource();
        }
        this.ambientModeType = this.dreamScreenFragmentListener.getAmbientModeType();
        this.ambientShowType = this.dreamScreenFragmentListener.getAmbientShowType();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(tag, "onCreateView");
        if (this.view != null) {
            Log.i(tag, "DreamScreenFragment reusing view");
            this.dreamScreenFragmentListener.setMenuIcon();
            this.view.requestFocus();
            return this.view;
        }
        this.view = inflater.inflate(R.layout.fragment_dreamscreen, container, false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.redValueText = (TextView) this.view.findViewById(R.id.redValueText2);
        this.greenValueText = (TextView) this.view.findViewById(R.id.greenValueText2);
        this.blueValueText = (TextView) this.view.findViewById(R.id.blueValueText2);
        ((TextView) this.view.findViewById(R.id.power_label)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.video_label)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.audio_label)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.ambient_label)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.redText2)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.greenText2)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.blueText2)).setTypeface(font);
        this.redValueText.setTypeface(font);
        this.greenValueText.setTypeface(font);
        this.blueValueText.setTypeface(font);
        this.hdmiLayout = (LinearLayout) this.view.findViewById(R.id.hdmiLayout);
        this.offContainer = (ImageView) this.view.findViewById(R.id.offContainer);
        this.videoContainer = (ImageView) this.view.findViewById(R.id.videoContainer);
        this.audioContainer = (RelativeLayout) this.view.findViewById(R.id.audioContainer);
        this.ambientStartContainer = (RelativeLayout) this.view.findViewById(R.id.ambientStartContainer);
        this.showContainer = (LinearLayout) this.view.findViewById(R.id.showContainer);
        this.colorWheelContainer = (LinearLayout) this.view.findViewById(R.id.colorWheelContainer);
        this.whitesWheelContainer = (LinearLayout) this.view.findViewById(R.id.whitesWheelContainer);
        this.slidersContainer = (LinearLayout) this.view.findViewById(R.id.slidersContainer2);
        this.brightnessSeekBar = (SeekBar) this.view.findViewById(R.id.SeekBar_Main_Brightness);
        this.powerButton = (ImageButton) this.view.findViewById(R.id.power_button);
        this.videoButton = (ImageButton) this.view.findViewById(R.id.video_button);
        this.audioButton = (ImageButton) this.view.findViewById(R.id.audio_button);
        this.ambientButton = (ImageButton) this.view.findViewById(R.id.ambient_button);
        this.hdmi1Button = (ImageButton) this.view.findViewById(R.id.hdmi1Button);
        this.hdmi2Button = (ImageButton) this.view.findViewById(R.id.hdmi2Button);
        this.hdmi3Button = (ImageButton) this.view.findViewById(R.id.hdmi3Button);
        this.audioJackButton = (ImageButton) this.view.findViewById(R.id.audioJackButton);
        this.hdmiAudioButton = (ImageButton) this.view.findViewById(R.id.hdmiAudioButton);
        this.hdmiAudioLayout = (LinearLayout) this.view.findViewById(R.id.hdmiAudioLayout);
        this.audioJackLayout = (LinearLayout) this.view.findViewById(R.id.audioJackLayout);
        this.hdmi1Text = (EditText) this.view.findViewById(R.id.hdmi1Text);
        this.hdmi2Text = (EditText) this.view.findViewById(R.id.hdmi2Text);
        this.hdmi3Text = (EditText) this.view.findViewById(R.id.hdmi3Text);
        this.hdmi1Text.setTypeface(font);
        this.hdmi2Text.setTypeface(font);
        this.hdmi3Text.setTypeface(font);
        ((TextView) this.view.findViewById(R.id.audioJackText)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.hdmiAudioText)).setTypeface(font);
        if (this.productId == 1 || this.productId == 2) {
            try {
                this.hdmi1Text.setText(new String(this.hdmiInputName1, "UTF-8").trim());
                this.hdmi2Text.setText(new String(this.hdmiInputName2, "UTF-8").trim());
                this.hdmi3Text.setText(new String(this.hdmiInputName3, "UTF-8").trim());
            } catch (UnsupportedEncodingException e) {
                this.hdmi1Text.setText("HDMI 1");
                this.hdmi2Text.setText("HDMI 2");
                this.hdmi3Text.setText("HDMI 3");
            }
        }
        this.userEdittingLockButton = (ImageButton) this.view.findViewById(R.id.userEdittingLockButton);
        this.hdmi1Edit = (ImageButton) this.view.findViewById(R.id.hdmi1Edit);
        this.hdmi2Edit = (ImageButton) this.view.findViewById(R.id.hdmi2Edit);
        this.hdmi3Edit = (ImageButton) this.view.findViewById(R.id.hdmi3Edit);
        ((TextView) this.view.findViewById(R.id.scenesText)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.colorWheelText)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.whitesText)).setTypeface(font);
        ((TextView) this.view.findViewById(R.id.slidersText)).setTypeface(font);
        this.randomButton = (ImageButton) this.view.findViewById(R.id.random_button);
        this.fireButton = (ImageButton) this.view.findViewById(R.id.fire_button);
        this.twinkleButton = (ImageButton) this.view.findViewById(R.id.twinkle_button);
        this.oceanButton = (ImageButton) this.view.findViewById(R.id.ocean_button);
        this.prideButton = (ImageButton) this.view.findViewById(R.id.rainbow_button);
        this.julyButton = (ImageButton) this.view.findViewById(R.id.july_button);
        this.holidayButton = (ImageButton) this.view.findViewById(R.id.holiday_button);
        this.popButton = (ImageButton) this.view.findViewById(R.id.pop_button);
        this.forestButton = (ImageButton) this.view.findViewById(R.id.forest_button);
        this.colorWheel = (ImageView) this.view.findViewById(R.id.colorWheel);
        this.colorWheelPicker = (ImageView) this.view.findViewById(R.id.picker_current);
        this.whitesWheel = (ImageView) this.view.findViewById(R.id.whitesWheel);
        this.whitesWheelPicker = (ImageView) this.view.findViewById(R.id.whitesCurrent);
        this.redSeekBar = (SeekBar) this.view.findViewById(R.id.redSeekBar2);
        this.greenSeekBar = (SeekBar) this.view.findViewById(R.id.greenSeekBar2);
        this.blueSeekBar = (SeekBar) this.view.findViewById(R.id.blueSeekBar2);
        this.slidersPicker = (ImageView) this.view.findViewById(R.id.picker_current_sliders2);
        this.fadeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_anim);
        Animation animation = this.fadeOutAnimation;
        AnonymousClass1 r0 = new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                DreamScreenFragment.this.currentContainer.setVisibility(8);
                DreamScreenFragment.this.currentContainer.setAlpha(1.0f);
                DreamScreenFragment.this.newContainer.setVisibility(0);
                DreamScreenFragment.this.newContainer.startAnimation(DreamScreenFragment.this.fadeInAnimation);
                DreamScreenFragment.this.currentContainer = DreamScreenFragment.this.newContainer;
            }

            public void onAnimationRepeat(Animation animation) {
            }
        };
        animation.setAnimationListener(r0);
        this.fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_anim);
        Animation animation2 = this.fadeInAnimation;
        AnonymousClass2 r02 = new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (DreamScreenFragment.this.currentContainer == DreamScreenFragment.this.offContainer) {
                    DreamScreenFragment.this.offContainer.startAnimation(DreamScreenFragment.this.sleepModeBreathingAnimation);
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }
        };
        animation2.setAnimationListener(r02);
        this.sleepModeBreathingAnimation = new AlphaAnimation(1.0f, 0.4f);
        this.sleepModeBreathingAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        this.sleepModeBreathingAnimation.setDuration(1500);
        this.sleepModeBreathingAnimation.setRepeatMode(2);
        this.sleepModeBreathingAnimation.setRepeatCount(-1);
        this.brightnessSeekBar.setProgress(this.brightness);
        this.offContainer.setVisibility(8);
        this.userEdittingLockButton.setVisibility(4);
        this.videoContainer.setVisibility(8);
        this.audioContainer.setVisibility(8);
        this.ambientStartContainer.setVisibility(8);
        this.showContainer.setVisibility(8);
        this.colorWheelContainer.setVisibility(8);
        this.whitesWheelContainer.setVisibility(8);
        this.slidersContainer.setVisibility(8);
        switch (this.mode) {
            case 0:
                this.offContainer.setVisibility(0);
                this.currentContainer = this.offContainer;
                this.offContainer.startAnimation(this.sleepModeBreathingAnimation);
                break;
            case 1:
                this.videoContainer.setVisibility(0);
                this.currentContainer = this.videoContainer;
                break;
            case 2:
                this.audioContainer.setVisibility(0);
                this.currentContainer = this.audioContainer;
                break;
            case 3:
                switch (this.ambientLayoutType) {
                    case 0:
                        this.ambientStartContainer.setVisibility(0);
                        this.currentContainer = this.ambientStartContainer;
                        break;
                    case 1:
                        this.showContainer.setVisibility(0);
                        this.currentContainer = this.showContainer;
                        break;
                    case 2:
                        this.slidersContainer.setVisibility(0);
                        this.currentContainer = this.slidersContainer;
                        break;
                    case 3:
                        this.colorWheelContainer.setVisibility(0);
                        this.currentContainer = this.colorWheelContainer;
                        break;
                    case 4:
                        this.whitesWheelContainer.setVisibility(0);
                        this.currentContainer = this.whitesWheelContainer;
                        break;
                }
            default:
                this.offContainer.setVisibility(0);
                this.currentContainer = this.offContainer;
                this.offContainer.startAnimation(this.sleepModeBreathingAnimation);
                break;
        }
        redrawFragment();
        SeekBar seekBar = this.brightnessSeekBar;
        AnonymousClass3 r03 = new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    DreamScreenFragment.this.brightness = progress;
                    if (SystemClock.elapsedRealtime() - DreamScreenFragment.this.packetSentAtTime > DreamScreenFragment.packetSendDelay) {
                        DreamScreenFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                        DreamScreenFragment.this.dreamScreenFragmentListener.setBrightnessConstant(DreamScreenFragment.this.brightness);
                    }
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                DreamScreenFragment.this.dreamScreenFragmentListener.setBrightness(DreamScreenFragment.this.brightness);
            }
        };
        seekBar.setOnSeekBarChangeListener(r03);
        ImageButton imageButton = this.powerButton;
        AnonymousClass4 r04 = new OnClickListener() {
            public void onClick(View view) {
                if (DreamScreenFragment.this.mode != 0) {
                    DreamScreenFragment.this.mode = 0;
                    DreamScreenFragment.this.dreamScreenFragmentListener.setMode(DreamScreenFragment.this.mode);
                    DreamScreenFragment.this.userEdittingUnlocked = false;
                    DreamScreenFragment.this.redrawFragment();
                }
            }
        };
        imageButton.setOnClickListener(r04);
        ImageButton imageButton2 = this.videoButton;
        AnonymousClass5 r05 = new OnClickListener() {
            public void onClick(View view) {
                if (DreamScreenFragment.this.productId == 3 || DreamScreenFragment.this.productId == 4) {
                    if (!DreamScreenFragment.this.dreamScreenFragmentListener.currentLightGroupedWithDreamScreen()) {
                        
                        /*  JADX ERROR: Method code generation error
                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0022: INVOKE  (wrap: com.lab714.dreamscreenv2.layout.DreamScreenFragment
                              0x0020: IGET  (r0v9 com.lab714.dreamscreenv2.layout.DreamScreenFragment) = (r4v0 'this' com.lab714.dreamscreenv2.layout.DreamScreenFragment$5 A[THIS]) com.lab714.dreamscreenv2.layout.DreamScreenFragment.5.this$0 com.lab714.dreamscreenv2.layout.DreamScreenFragment) com.lab714.dreamscreenv2.layout.DreamScreenFragment.access$1108(com.lab714.dreamscreenv2.layout.DreamScreenFragment):int type: STATIC in method: com.lab714.dreamscreenv2.layout.DreamScreenFragment.5.onClick(android.view.View):void, dex: classes.dex
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:245)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:138)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:138)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:316)
                            	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:262)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:225)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:661)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:595)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:353)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:316)
                            	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:262)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:225)
                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
                            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:76)
                            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:32)
                            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:20)
                            	at jadx.core.ProcessClass.process(ProcessClass.java:36)
                            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
                            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
                            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
                            Caused by: org.objenesis.ObjenesisException: java.lang.ClassNotFoundException: sun.reflect.ReflectionFactory
                            	at org.objenesis.instantiator.sun.SunReflectionFactoryHelper.getReflectionFactoryClass(SunReflectionFactoryHelper.java:57)
                            	at org.objenesis.instantiator.sun.SunReflectionFactoryHelper.newConstructorForSerialization(SunReflectionFactoryHelper.java:37)
                            	at org.objenesis.instantiator.sun.SunReflectionFactoryInstantiator.<init>(SunReflectionFactoryInstantiator.java:41)
                            	at org.objenesis.strategy.StdInstantiatorStrategy.newInstantiatorOf(StdInstantiatorStrategy.java:68)
                            	at org.objenesis.ObjenesisBase.getInstantiatorOf(ObjenesisBase.java:94)
                            	at org.objenesis.ObjenesisBase.newInstance(ObjenesisBase.java:73)
                            	at com.rits.cloning.ObjenesisInstantiationStrategy.newInstance(ObjenesisInstantiationStrategy.java:17)
                            	at com.rits.cloning.Cloner.newInstance(Cloner.java:300)
                            	at com.rits.cloning.Cloner.cloneObject(Cloner.java:461)
                            	at com.rits.cloning.Cloner.cloneInternal(Cloner.java:456)
                            	at com.rits.cloning.Cloner.deepClone(Cloner.java:326)
                            	at jadx.core.dex.nodes.InsnNode.copy(InsnNode.java:352)
                            	at jadx.core.dex.nodes.InsnNode.copyCommonParams(InsnNode.java:333)
                            	at jadx.core.dex.instructions.IndexInsnNode.copy(IndexInsnNode.java:24)
                            	at jadx.core.dex.instructions.IndexInsnNode.copy(IndexInsnNode.java:9)
                            	at jadx.core.codegen.InsnGen.inlineMethod(InsnGen.java:880)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:669)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
                            	... 49 more
                            Caused by: java.lang.ClassNotFoundException: sun.reflect.ReflectionFactory
                            	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(Unknown Source)
                            	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(Unknown Source)
                            	at java.base/java.lang.ClassLoader.loadClass(Unknown Source)
                            	at java.base/java.lang.Class.forName0(Native Method)
                            	at java.base/java.lang.Class.forName(Unknown Source)
                            	at org.objenesis.instantiator.sun.SunReflectionFactoryHelper.getReflectionFactoryClass(SunReflectionFactoryHelper.java:54)
                            	... 67 more
                            */
                        /*
                            this = this;
                            r3 = 1
                            r2 = 0
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            int r0 = r0.productId
                            r1 = 3
                            if (r0 == r1) goto L_0x0014
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            int r0 = r0.productId
                            r1 = 4
                            if (r0 != r1) goto L_0x0050
                        L_0x0014:
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment$DreamScreenFragmentListener r0 = r0.dreamScreenFragmentListener
                            boolean r0 = r0.currentLightGroupedWithDreamScreen()
                            if (r0 != 0) goto L_0x0039
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            
                            // error: 0x0022: INVOKE  (r0 I:com.lab714.dreamscreenv2.layout.DreamScreenFragment) com.lab714.dreamscreenv2.layout.DreamScreenFragment.access$1108(com.lab714.dreamscreenv2.layout.DreamScreenFragment):int type: STATIC
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            int r0 = r0.featureDisabledCount
                            r1 = 5
                            if (r0 < r1) goto L_0x0038
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            r0.showFeatureDisabledDialog()
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            r0.featureDisabledCount = r2
                        L_0x0038:
                            return
                        L_0x0039:
                            java.lang.String r0 = "DreamScreenFrag"
                            java.lang.String r1 = "Sidekick/connect grouped with DS but dont let user set this sidekick to video mode"
                            android.util.Log.i(r0, r1)
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            android.content.Context r0 = r0.getContext()
                            java.lang.String r1 = "Select Video Mode and Music Mode under the Group."
                            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r2)
                            r0.show()
                            goto L_0x0038
                        L_0x0050:
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            int r0 = r0.mode
                            if (r0 == r3) goto L_0x0038
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            r0.mode = r3
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment$DreamScreenFragmentListener r0 = r0.dreamScreenFragmentListener
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r1 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            int r1 = r1.mode
                            r0.setMode(r1)
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            r0.userEdittingUnlocked = r2
                            com.lab714.dreamscreenv2.layout.DreamScreenFragment r0 = com.lab714.dreamscreenv2.layout.DreamScreenFragment.this
                            r0.redrawFragment()
                            goto L_0x0038
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.layout.DreamScreenFragment.AnonymousClass5.onClick(android.view.View):void");
                    }
                };
                imageButton2.setOnClickListener(r05);
                ImageButton imageButton3 = this.audioButton;
                AnonymousClass6 r06 = new OnClickListener() {
                    public void onClick(View view) {
                        if (DreamScreenFragment.this.productId == 3 || DreamScreenFragment.this.productId == 4) {
                            if (!DreamScreenFragment.this.dreamScreenFragmentListener.currentLightGroupedWithDreamScreen()) {
                                

                            }
                        };
                        imageButton3.setOnClickListener(r06);
                        ImageButton imageButton4 = this.ambientButton;
                        AnonymousClass7 r07 = new OnClickListener() {
                            public void onClick(View view) {
                                if (!DreamScreenFragment.this.ambientStartContainer.isShown()) {
                                    DreamScreenFragment.this.mode = 3;
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setMode(DreamScreenFragment.this.mode);
                                    DreamScreenFragment.this.ambientLayoutType = 0;
                                    DreamScreenFragment.this.userEdittingUnlocked = false;
                                    DreamScreenFragment.this.redrawFragment();
                                }
                            }
                        };
                        imageButton4.setOnClickListener(r07);
                        ImageView imageView = this.colorWheel;
                        AnonymousClass8 r08 = new OnTouchListener() {
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == 1) {
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColor(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                } else {
                                    Matrix inverse = new Matrix();
                                    DreamScreenFragment.this.colorWheel.getImageMatrix().invert(inverse);
                                    float[] touchPoint = {event.getX(), event.getY()};
                                    inverse.mapPoints(touchPoint);
                                    int xCoord = (int) touchPoint[0];
                                    int yCoord = (int) touchPoint[1];
                                    Bitmap bitmap = ((BitmapDrawable) DreamScreenFragment.this.colorWheel.getDrawable()).getBitmap();
                                    if (xCoord > 0 && xCoord < bitmap.getWidth() && yCoord > 0 && yCoord < bitmap.getHeight()) {
                                        int pixel = bitmap.getPixel(xCoord, yCoord);
                                        if (pixel != 0 && (!(DreamScreenFragment.this.currentAmbientColor[0] == Color.red(pixel) && DreamScreenFragment.this.currentAmbientColor[1] == Color.green(pixel) && DreamScreenFragment.this.currentAmbientColor[2] == Color.blue(pixel)) && SystemClock.elapsedRealtime() - DreamScreenFragment.this.packetSentAtTime > DreamScreenFragment.packetSendDelay)) {
                                            DreamScreenFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                                            DreamScreenFragment.this.currentAmbientColor[0] = Color.red(pixel);
                                            DreamScreenFragment.this.currentAmbientColor[1] = Color.green(pixel);
                                            DreamScreenFragment.this.currentAmbientColor[2] = Color.blue(pixel);
                                            DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColorConstant(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                            DreamScreenFragment.this.colorWheelPicker.setColorFilter(Color.rgb(DreamScreenFragment.this.currentAmbientColor[0], DreamScreenFragment.this.currentAmbientColor[1], DreamScreenFragment.this.currentAmbientColor[2]));
                                        }
                                    }
                                }
                                return true;
                            }
                        };
                        imageView.setOnTouchListener(r08);
                        ImageView imageView2 = this.whitesWheel;
                        AnonymousClass9 r09 = new OnTouchListener() {
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == 1) {
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColor(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                } else {
                                    Matrix inverse = new Matrix();
                                    DreamScreenFragment.this.whitesWheel.getImageMatrix().invert(inverse);
                                    float[] touchPoint = {event.getX(), event.getY()};
                                    inverse.mapPoints(touchPoint);
                                    int xCoord = (int) touchPoint[0];
                                    int yCoord = (int) touchPoint[1];
                                    Bitmap bitmap = ((BitmapDrawable) DreamScreenFragment.this.whitesWheel.getDrawable()).getBitmap();
                                    if (xCoord > 0 && xCoord < bitmap.getWidth() && yCoord > 0 && yCoord < bitmap.getHeight()) {
                                        int pixel = bitmap.getPixel(xCoord, yCoord);
                                        if (pixel != 0 && (!(DreamScreenFragment.this.currentAmbientColor[0] == Color.red(pixel) && DreamScreenFragment.this.currentAmbientColor[1] == Color.green(pixel) && DreamScreenFragment.this.currentAmbientColor[2] == Color.blue(pixel)) && SystemClock.elapsedRealtime() - DreamScreenFragment.this.packetSentAtTime > DreamScreenFragment.packetSendDelay)) {
                                            DreamScreenFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                                            DreamScreenFragment.this.currentAmbientColor[0] = Color.red(pixel);
                                            DreamScreenFragment.this.currentAmbientColor[1] = Color.green(pixel);
                                            DreamScreenFragment.this.currentAmbientColor[2] = Color.blue(pixel);
                                            DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColorConstant(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                            DreamScreenFragment.this.whitesWheelPicker.setColorFilter(Color.rgb(DreamScreenFragment.this.currentAmbientColor[0], DreamScreenFragment.this.currentAmbientColor[1], DreamScreenFragment.this.currentAmbientColor[2]));
                                        }
                                    }
                                }
                                return true;
                            }
                        };
                        imageView2.setOnTouchListener(r09);
                        SeekBar seekBar2 = this.redSeekBar;
                        AnonymousClass10 r010 = new OnSeekBarChangeListener() {
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser && DreamScreenFragment.this.currentAmbientColor[0] != progress && SystemClock.elapsedRealtime() - DreamScreenFragment.this.packetSentAtTime > DreamScreenFragment.packetSendDelay) {
                                    DreamScreenFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                                    DreamScreenFragment.this.currentAmbientColor[0] = progress;
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColorConstant(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                    DreamScreenFragment.this.slidersPicker.setColorFilter(Color.rgb(DreamScreenFragment.this.currentAmbientColor[0], DreamScreenFragment.this.currentAmbientColor[1], DreamScreenFragment.this.currentAmbientColor[2]));
                                    DreamScreenFragment.this.redValueText.setText(String.valueOf(DreamScreenFragment.this.currentAmbientColor[0]));
                                }
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColor(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                DreamScreenFragment.this.redValueText.setText(String.valueOf(DreamScreenFragment.this.currentAmbientColor[0]));
                            }
                        };
                        seekBar2.setOnSeekBarChangeListener(r010);
                        SeekBar seekBar3 = this.greenSeekBar;
                        AnonymousClass11 r011 = new OnSeekBarChangeListener() {
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser && DreamScreenFragment.this.currentAmbientColor[1] != progress && SystemClock.elapsedRealtime() - DreamScreenFragment.this.packetSentAtTime > DreamScreenFragment.packetSendDelay) {
                                    DreamScreenFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                                    DreamScreenFragment.this.currentAmbientColor[1] = progress;
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColorConstant(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                    DreamScreenFragment.this.slidersPicker.setColorFilter(Color.rgb(DreamScreenFragment.this.currentAmbientColor[0], DreamScreenFragment.this.currentAmbientColor[1], DreamScreenFragment.this.currentAmbientColor[2]));
                                    DreamScreenFragment.this.greenValueText.setText(String.valueOf(DreamScreenFragment.this.currentAmbientColor[1]));
                                }
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColor(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                DreamScreenFragment.this.greenValueText.setText(String.valueOf(DreamScreenFragment.this.currentAmbientColor[1]));
                            }
                        };
                        seekBar3.setOnSeekBarChangeListener(r011);
                        SeekBar seekBar4 = this.blueSeekBar;
                        AnonymousClass12 r012 = new OnSeekBarChangeListener() {
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser && DreamScreenFragment.this.currentAmbientColor[2] != progress && SystemClock.elapsedRealtime() - DreamScreenFragment.this.packetSentAtTime > DreamScreenFragment.packetSendDelay) {
                                    DreamScreenFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                                    DreamScreenFragment.this.currentAmbientColor[2] = progress;
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColorConstant(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                    DreamScreenFragment.this.slidersPicker.setColorFilter(Color.rgb(DreamScreenFragment.this.currentAmbientColor[0], DreamScreenFragment.this.currentAmbientColor[1], DreamScreenFragment.this.currentAmbientColor[2]));
                                    DreamScreenFragment.this.blueValueText.setText(String.valueOf(DreamScreenFragment.this.currentAmbientColor[2]));
                                }
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientColor(new byte[]{(byte) DreamScreenFragment.this.currentAmbientColor[0], (byte) DreamScreenFragment.this.currentAmbientColor[1], (byte) DreamScreenFragment.this.currentAmbientColor[2]});
                                DreamScreenFragment.this.blueValueText.setText(String.valueOf(DreamScreenFragment.this.currentAmbientColor[2]));
                            }
                        };
                        seekBar4.setOnSeekBarChangeListener(r012);
                        View findViewById = this.view.findViewById(R.id.scenesButton);
                        AnonymousClass13 r013 = new OnClickListener() {
                            public void onClick(View view) {
                                DreamScreenFragment.this.ambientModeType = 1;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientModeType(DreamScreenFragment.this.ambientModeType);
                                DreamScreenFragment.this.ambientLayoutType = 1;
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        findViewById.setOnClickListener(r013);
                        View findViewById2 = this.view.findViewById(R.id.colorWheelButton);
                        AnonymousClass14 r014 = new OnClickListener() {
                            public void onClick(View view) {
                                DreamScreenFragment.this.ambientModeType = 0;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientModeType(DreamScreenFragment.this.ambientModeType);
                                DreamScreenFragment.this.ambientLayoutType = 3;
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        findViewById2.setOnClickListener(r014);
                        View findViewById3 = this.view.findViewById(R.id.whitesButton);
                        AnonymousClass15 r015 = new OnClickListener() {
                            public void onClick(View view) {
                                DreamScreenFragment.this.ambientModeType = 0;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientModeType(DreamScreenFragment.this.ambientModeType);
                                DreamScreenFragment.this.ambientLayoutType = 4;
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        findViewById3.setOnClickListener(r015);
                        View findViewById4 = this.view.findViewById(R.id.slidersButton);
                        AnonymousClass16 r016 = new OnClickListener() {
                            public void onClick(View view) {
                                DreamScreenFragment.this.ambientModeType = 0;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientModeType(DreamScreenFragment.this.ambientModeType);
                                DreamScreenFragment.this.ambientLayoutType = 2;
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        findViewById4.setOnClickListener(r016);
                        ImageButton imageButton5 = this.randomButton;
                        AnonymousClass17 r017 = new OnClickListener() {
                            public void onClick(View v) {
                                DreamScreenFragment.this.ambientShowType = 0;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientShowType(DreamScreenFragment.this.ambientShowType);
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        imageButton5.setOnClickListener(r017);
                        ImageButton imageButton6 = this.fireButton;
                        AnonymousClass18 r018 = new OnClickListener() {
                            public void onClick(View v) {
                                DreamScreenFragment.this.ambientShowType = 1;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientShowType(DreamScreenFragment.this.ambientShowType);
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        imageButton6.setOnClickListener(r018);
                        ImageButton imageButton7 = this.twinkleButton;
                        AnonymousClass19 r019 = new OnClickListener() {
                            public void onClick(View v) {
                                DreamScreenFragment.this.ambientShowType = 2;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientShowType(DreamScreenFragment.this.ambientShowType);
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        imageButton7.setOnClickListener(r019);
                        ImageButton imageButton8 = this.oceanButton;
                        AnonymousClass20 r020 = new OnClickListener() {
                            public void onClick(View v) {
                                DreamScreenFragment.this.ambientShowType = 3;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientShowType(DreamScreenFragment.this.ambientShowType);
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        imageButton8.setOnClickListener(r020);
                        ImageButton imageButton9 = this.prideButton;
                        AnonymousClass21 r021 = new OnClickListener() {
                            public void onClick(View v) {
                                DreamScreenFragment.this.ambientShowType = 4;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientShowType(DreamScreenFragment.this.ambientShowType);
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        imageButton9.setOnClickListener(r021);
                        ImageButton imageButton10 = this.julyButton;
                        AnonymousClass22 r022 = new OnClickListener() {
                            public void onClick(View v) {
                                DreamScreenFragment.this.ambientShowType = 5;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientShowType(DreamScreenFragment.this.ambientShowType);
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        imageButton10.setOnClickListener(r022);
                        ImageButton imageButton11 = this.holidayButton;
                        AnonymousClass23 r023 = new OnClickListener() {
                            public void onClick(View v) {
                                DreamScreenFragment.this.ambientShowType = 6;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientShowType(DreamScreenFragment.this.ambientShowType);
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        imageButton11.setOnClickListener(r023);
                        ImageButton imageButton12 = this.popButton;
                        AnonymousClass24 r024 = new OnClickListener() {
                            public void onClick(View v) {
                                DreamScreenFragment.this.ambientShowType = 7;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientShowType(DreamScreenFragment.this.ambientShowType);
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        imageButton12.setOnClickListener(r024);
                        ImageButton imageButton13 = this.forestButton;
                        AnonymousClass25 r025 = new OnClickListener() {
                            public void onClick(View v) {
                                DreamScreenFragment.this.ambientShowType = 8;
                                DreamScreenFragment.this.dreamScreenFragmentListener.setAmbientShowType(DreamScreenFragment.this.ambientShowType);
                                DreamScreenFragment.this.redrawFragment();
                            }
                        };
                        imageButton13.setOnClickListener(r025);
                        this.hdmiSwitchingAnimation = new AlphaAnimation(1.0f, 0.4f);
                        this.hdmiSwitchingAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                        this.hdmiSwitchingAnimation.setDuration(1500);
                        this.hdmiSwitchingAnimation.setRepeatMode(2);
                        this.hdmiSwitchingAnimation.setRepeatCount(1);
                        Animation animation3 = this.hdmiSwitchingAnimation;
                        AnonymousClass26 r026 = new AnimationListener() {
                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                if (DreamScreenFragment.this.dreamScreenFragmentListener != null) {
                                    DreamScreenFragment.this.dreamScreenFragmentListener.readHdmiActiveChannels();
                                }
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        if (DreamScreenFragment.this.dreamScreenFragmentListener != null) {
                                            DreamScreenFragment.this.dreamScreenFragmentListener.readHdmiInput();
                                        }
                                    }
                                }, 100);
                            }

                            public void onAnimationRepeat(Animation animation) {
                            }
                        };
                        animation3.setAnimationListener(r026);
                        ImageButton imageButton14 = this.hdmi1Button;
                        AnonymousClass27 r027 = new OnClickListener() {
                            public void onClick(View view) {
                                if (!DreamScreenFragment.this.hdmiSwitchingAnimation.hasStarted() || DreamScreenFragment.this.hdmiSwitchingAnimation.hasEnded()) {
                                    DreamScreenFragment.this.hdmiInput = 0;
                                    if (DreamScreenFragment.this.dreamScreenFragmentListener != null) {
                                        DreamScreenFragment.this.dreamScreenFragmentListener.setHdmiInput(DreamScreenFragment.this.hdmiInput);
                                    }
                                    DreamScreenFragment.this.updateHDMILayout();
                                    DreamScreenFragment.this.hdmi1Button.startAnimation(DreamScreenFragment.this.hdmiSwitchingAnimation);
                                    return;
                                }
                                Log.i(DreamScreenFragment.tag, "hdmi currently switching, canceling set again");
                            }
                        };
                        imageButton14.setOnClickListener(r027);
                        ImageButton imageButton15 = this.hdmi2Button;
                        AnonymousClass28 r028 = new OnClickListener() {
                            public void onClick(View view) {
                                if (!DreamScreenFragment.this.hdmiSwitchingAnimation.hasStarted() || DreamScreenFragment.this.hdmiSwitchingAnimation.hasEnded()) {
                                    DreamScreenFragment.this.hdmiInput = 1;
                                    if (DreamScreenFragment.this.dreamScreenFragmentListener != null) {
                                        DreamScreenFragment.this.dreamScreenFragmentListener.setHdmiInput(DreamScreenFragment.this.hdmiInput);
                                    }
                                    DreamScreenFragment.this.updateHDMILayout();
                                    DreamScreenFragment.this.hdmi2Button.startAnimation(DreamScreenFragment.this.hdmiSwitchingAnimation);
                                    return;
                                }
                                Log.i(DreamScreenFragment.tag, "hdmi currently switching, canceling set again");
                            }
                        };
                        imageButton15.setOnClickListener(r028);
                        ImageButton imageButton16 = this.hdmi3Button;
                        AnonymousClass29 r029 = new OnClickListener() {
                            public void onClick(View view) {
                                if (!DreamScreenFragment.this.hdmiSwitchingAnimation.hasStarted() || DreamScreenFragment.this.hdmiSwitchingAnimation.hasEnded()) {
                                    DreamScreenFragment.this.hdmiInput = 2;
                                    if (DreamScreenFragment.this.dreamScreenFragmentListener != null) {
                                        DreamScreenFragment.this.dreamScreenFragmentListener.setHdmiInput(DreamScreenFragment.this.hdmiInput);
                                    }
                                    DreamScreenFragment.this.updateHDMILayout();
                                    DreamScreenFragment.this.hdmi3Button.startAnimation(DreamScreenFragment.this.hdmiSwitchingAnimation);
                                    return;
                                }
                                Log.i(DreamScreenFragment.tag, "hdmi currently switching, canceling set again");
                            }
                        };
                        imageButton16.setOnClickListener(r029);
                        ImageButton imageButton17 = this.userEdittingLockButton;
                        AnonymousClass30 r030 = new OnClickListener() {
                            public void onClick(View view) {
                                DreamScreenFragment.this.userEdittingUnlocked = !DreamScreenFragment.this.userEdittingUnlocked;
                                DreamScreenFragment.this.updateHDMILayout();
                            }
                        };
                        imageButton17.setOnClickListener(r030);
                        LinearLayout linearLayout = this.hdmiLayout;
                        AnonymousClass31 r031 = new OnClickListener() {
                            public void onClick(View view) {
                                Log.i(DreamScreenFragment.tag, "hdmiLayout close keyboard");
                                ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmiLayout.getWindowToken(), 0);
                            }
                        };
                        linearLayout.setOnClickListener(r031);
                        EditText editText = this.hdmi1Text;
                        AnonymousClass32 r032 = new OnFocusChangeListener() {
                            public void onFocusChange(View view, boolean hasFocus) {
                                InputMethodManager inputMethodManager = (InputMethodManager) DreamScreenFragment.this.getContext().getSystemService("input_method");
                                if (hasFocus) {
                                    inputMethodManager.showSoftInput(DreamScreenFragment.this.hdmi1Text, 2);
                                    DreamScreenFragment.this.hdmi1Text.setSelection(DreamScreenFragment.this.hdmi1Text.getText().length());
                                    return;
                                }
                                String newSourceName = DreamScreenFragment.this.hdmi1Text.getText().toString();
                                if (newSourceName.length() == 0) {
                                    newSourceName = "HDMI 1";
                                    DreamScreenFragment.this.hdmi1Text.setText(newSourceName);
                                }
                                Log.i(DreamScreenFragment.tag, "user entered new hdmi1 name " + newSourceName);
                                try {
                                    byte[] paddedName = Arrays.copyOf(newSourceName.getBytes("UTF-8"), 16);
                                    for (int i = 0; i < 16; i++) {
                                        if (paddedName[i] == 0) {
                                            paddedName[i] = 32;
                                        }
                                    }
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setHdmiInputName1(paddedName);
                                } catch (UnsupportedEncodingException e) {
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setHdmiInputName1("HDMI 1          ".getBytes());
                                }
                            }
                        };
                        editText.setOnFocusChangeListener(r032);
                        EditText editText2 = this.hdmi1Text;
                        AnonymousClass33 r033 = new OnEditorActionListener() {
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                Log.i(DreamScreenFragment.tag, "onEditorAction");
                                switch (actionId) {
                                    case 0:
                                    case 5:
                                    case 6:
                                        DreamScreenFragment.this.hdmi1Text.clearFocus();
                                        ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmi1Text.getWindowToken(), 0);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        };
                        editText2.setOnEditorActionListener(r033);
                        EditText editText3 = this.hdmi1Text;
                        AnonymousClass34 r034 = new TextWatcher() {
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            public void afterTextChanged(Editable editable) {
                                boolean enterPressed = false;
                                while (editable.toString().contains("\n")) {
                                    enterPressed = true;
                                    int index = editable.toString().indexOf("\n");
                                    editable.delete(index, index + 1);
                                }
                                if (enterPressed) {
                                    DreamScreenFragment.this.hdmi1Text.clearFocus();
                                    ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmi1Text.getWindowToken(), 0);
                                }
                            }
                        };
                        editText3.addTextChangedListener(r034);
                        ImageButton imageButton18 = this.hdmi1Edit;
                        AnonymousClass35 r035 = new OnClickListener() {
                            public void onClick(View view) {
                                if (!DreamScreenFragment.this.userEdittingUnlocked) {
                                    return;
                                }
                                if (DreamScreenFragment.this.hdmi1Text.hasFocus()) {
                                    DreamScreenFragment.this.hdmi1Text.clearFocus();
                                    ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmi1Text.getWindowToken(), 0);
                                    return;
                                }
                                DreamScreenFragment.this.hdmi1Text.requestFocus();
                            }
                        };
                        imageButton18.setOnClickListener(r035);
                        EditText editText4 = this.hdmi2Text;
                        AnonymousClass36 r036 = new OnFocusChangeListener() {
                            public void onFocusChange(View view, boolean hasFocus) {
                                InputMethodManager inputMethodManager = (InputMethodManager) DreamScreenFragment.this.getContext().getSystemService("input_method");
                                if (hasFocus) {
                                    inputMethodManager.showSoftInput(DreamScreenFragment.this.hdmi2Text, 2);
                                    DreamScreenFragment.this.hdmi2Text.setSelection(DreamScreenFragment.this.hdmi2Text.getText().length());
                                    return;
                                }
                                String newSourceName = DreamScreenFragment.this.hdmi2Text.getText().toString();
                                if (newSourceName.length() == 0) {
                                    newSourceName = "HDMI 2";
                                    DreamScreenFragment.this.hdmi2Text.setText(newSourceName);
                                }
                                Log.i(DreamScreenFragment.tag, "user entered new hdmi2 name " + newSourceName);
                                try {
                                    byte[] paddedName = Arrays.copyOf(newSourceName.getBytes("UTF-8"), 16);
                                    for (int i = 0; i < 16; i++) {
                                        if (paddedName[i] == 0) {
                                            paddedName[i] = 32;
                                        }
                                    }
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setHdmiInputName2(paddedName);
                                } catch (UnsupportedEncodingException e) {
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setHdmiInputName2("HDMI 2          ".getBytes());
                                }
                            }
                        };
                        editText4.setOnFocusChangeListener(r036);
                        EditText editText5 = this.hdmi2Text;
                        AnonymousClass37 r037 = new OnEditorActionListener() {
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                switch (actionId) {
                                    case 0:
                                    case 5:
                                    case 6:
                                        DreamScreenFragment.this.hdmi2Text.clearFocus();
                                        ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmi2Text.getWindowToken(), 0);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        };
                        editText5.setOnEditorActionListener(r037);
                        EditText editText6 = this.hdmi2Text;
                        AnonymousClass38 r038 = new TextWatcher() {
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            public void afterTextChanged(Editable editable) {
                                boolean enterPressed = false;
                                while (editable.toString().contains("\n")) {
                                    enterPressed = true;
                                    int index = editable.toString().indexOf("\n");
                                    editable.delete(index, index + 1);
                                }
                                if (enterPressed) {
                                    DreamScreenFragment.this.hdmi2Text.clearFocus();
                                    ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmi2Text.getWindowToken(), 0);
                                }
                            }
                        };
                        editText6.addTextChangedListener(r038);
                        ImageButton imageButton19 = this.hdmi2Edit;
                        AnonymousClass39 r039 = new OnClickListener() {
                            public void onClick(View view) {
                                if (!DreamScreenFragment.this.userEdittingUnlocked) {
                                    return;
                                }
                                if (DreamScreenFragment.this.hdmi2Text.hasFocus()) {
                                    DreamScreenFragment.this.hdmi2Text.clearFocus();
                                    ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmi2Text.getWindowToken(), 0);
                                    return;
                                }
                                DreamScreenFragment.this.hdmi2Text.requestFocus();
                            }
                        };
                        imageButton19.setOnClickListener(r039);
                        EditText editText7 = this.hdmi3Text;
                        AnonymousClass40 r040 = new OnFocusChangeListener() {
                            public void onFocusChange(View view, boolean hasFocus) {
                                InputMethodManager inputMethodManager = (InputMethodManager) DreamScreenFragment.this.getContext().getSystemService("input_method");
                                if (hasFocus) {
                                    inputMethodManager.showSoftInput(DreamScreenFragment.this.hdmi3Text, 2);
                                    DreamScreenFragment.this.hdmi3Text.setSelection(DreamScreenFragment.this.hdmi3Text.getText().length());
                                    return;
                                }
                                String newSourceName = DreamScreenFragment.this.hdmi3Text.getText().toString();
                                if (newSourceName.length() == 0) {
                                    newSourceName = "HDMI 3";
                                    DreamScreenFragment.this.hdmi3Text.setText(newSourceName);
                                }
                                Log.i(DreamScreenFragment.tag, "user entered new hdmi3 name " + newSourceName);
                                try {
                                    byte[] paddedName = Arrays.copyOf(newSourceName.getBytes("UTF-8"), 16);
                                    for (int i = 0; i < 16; i++) {
                                        if (paddedName[i] == 0) {
                                            paddedName[i] = 32;
                                        }
                                    }
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setHdmiInputName3(paddedName);
                                } catch (UnsupportedEncodingException e) {
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setHdmiInputName3("HDMI 3          ".getBytes());
                                }
                            }
                        };
                        editText7.setOnFocusChangeListener(r040);
                        EditText editText8 = this.hdmi3Text;
                        AnonymousClass41 r041 = new OnEditorActionListener() {
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                switch (actionId) {
                                    case 0:
                                    case 5:
                                    case 6:
                                        DreamScreenFragment.this.hdmi3Text.clearFocus();
                                        ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmi3Text.getWindowToken(), 0);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        };
                        editText8.setOnEditorActionListener(r041);
                        EditText editText9 = this.hdmi3Text;
                        AnonymousClass42 r042 = new TextWatcher() {
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            public void afterTextChanged(Editable editable) {
                                boolean enterPressed = false;
                                while (editable.toString().contains("\n")) {
                                    enterPressed = true;
                                    int index = editable.toString().indexOf("\n");
                                    editable.delete(index, index + 1);
                                }
                                if (enterPressed) {
                                    DreamScreenFragment.this.hdmi3Text.clearFocus();
                                    ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmi3Text.getWindowToken(), 0);
                                }
                            }
                        };
                        editText9.addTextChangedListener(r042);
                        ImageButton imageButton20 = this.hdmi3Edit;
                        AnonymousClass43 r043 = new OnClickListener() {
                            public void onClick(View view) {
                                if (!DreamScreenFragment.this.userEdittingUnlocked) {
                                    return;
                                }
                                if (DreamScreenFragment.this.hdmi3Text.hasFocus()) {
                                    DreamScreenFragment.this.hdmi3Text.clearFocus();
                                    ((InputMethodManager) DreamScreenFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(DreamScreenFragment.this.hdmi3Text.getWindowToken(), 0);
                                    return;
                                }
                                DreamScreenFragment.this.hdmi3Text.requestFocus();
                            }
                        };
                        imageButton20.setOnClickListener(r043);
                        ImageButton imageButton21 = this.hdmiAudioButton;
                        AnonymousClass44 r044 = new OnClickListener() {
                            public void onClick(View view) {
                                if (DreamScreenFragment.this.musicModeSource != 0) {
                                    DreamScreenFragment.this.musicModeSource = 0;
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setMusicModeSource(DreamScreenFragment.this.musicModeSource);
                                    DreamScreenFragment.this.hdmiAudioButton.setColorFilter(DreamScreenFragment.this.currentModeColor);
                                    DreamScreenFragment.this.audioJackButton.setColorFilter(null);
                                }
                            }
                        };
                        imageButton21.setOnClickListener(r044);
                        ImageButton imageButton22 = this.audioJackButton;
                        AnonymousClass45 r045 = new OnClickListener() {
                            public void onClick(View view) {
                                if (DreamScreenFragment.this.musicModeSource != 1) {
                                    DreamScreenFragment.this.musicModeSource = 1;
                                    DreamScreenFragment.this.dreamScreenFragmentListener.setMusicModeSource(DreamScreenFragment.this.musicModeSource);
                                    DreamScreenFragment.this.audioJackButton.setColorFilter(DreamScreenFragment.this.currentModeColor);
                                    DreamScreenFragment.this.hdmiAudioButton.setColorFilter(null);
                                }
                            }
                        };
                        imageButton22.setOnClickListener(r045);
                        this.view.setFocusableInTouchMode(true);
                        this.view.requestFocus();
                        View view2 = this.view;
                        AnonymousClass46 r046 = new OnKeyListener() {
                            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                                if (keyCode != 4 || DreamScreenFragment.this.mode != 3 || DreamScreenFragment.this.ambientLayoutType == 0) {
                                    return false;
                                }
                                DreamScreenFragment.this.ambientLayoutType = 0;
                                DreamScreenFragment.this.redrawFragment();
                                return true;
                            }
                        };
                        view2.setOnKeyListener(r046);
                        this.dreamScreenFragmentListener.setMenuIcon();
                        LinkedList<String> newDevices = this.dreamScreenFragmentListener.getNewDevices();
                        if (!newDevices.isEmpty()) {
                            Log.i(tag, "displaying newDevices dialog");
                            View newDeviceView = getActivity().getLayoutInflater().inflate(R.layout.newdevices_confirmation_dialog, null);
                            Builder alertDialogBuilder = new Builder(getActivity());
                            alertDialogBuilder.setView(newDeviceView).setCancelable(false);
                            final AlertDialog newDeviceDialog = alertDialogBuilder.create();
                            newDeviceDialog.show();
                            ((TextView) newDeviceView.findViewById(R.id.header)).setTypeface(font);
                            ((TextView) newDeviceView.findViewById(R.id.subHeader)).setTypeface(font);
                            TextView okText = (TextView) newDeviceView.findViewById(R.id.addText);
                            okText.setTypeface(font);
                            AnonymousClass47 r047 = new OnClickListener() {
                                public void onClick(View view) {
                                    if (newDeviceDialog != null && newDeviceDialog.isShowing()) {
                                        newDeviceDialog.dismiss();
                                        if (DreamScreenFragment.this.dreamScreenFragmentListener != null) {
                                            DreamScreenFragment.this.dreamScreenFragmentListener.openDrawer();
                                        }
                                    }
                                }
                            };
                            okText.setOnClickListener(r047);
                            LinearLayout devicesLayout = (LinearLayout) newDeviceView.findViewById(R.id.devicesLayout);
                            devicesLayout.removeAllViews();
                            LayoutParams lp = new LayoutParams(-2, -2);
                            lp.setMargins(0, 0, 0, pxToDp(8));
                            Iterator it = newDevices.iterator();
                            while (it.hasNext()) {
                                String newDevice = (String) it.next();
                                TextView textView = new TextView(getActivity());
                                textView.setLayoutParams(lp);
                                textView.setTypeface(font);
                                textView.setText(newDevice);
                                textView.setTextSize(18.0f);
                                textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                                devicesLayout.addView(textView);
                            }
                        }
                        return this.view;
                    }

                    public void redrawFragment() {
                        Log.i(tag, "DreamScreenFragment redrawFragment");
                        switch (this.mode) {
                            case 0:
                                this.currentModeColor = ContextCompat.getColor(getContext(), R.color.mode0Color);
                                break;
                            case 1:
                                this.currentModeColor = ContextCompat.getColor(getContext(), R.color.mode1Color);
                                break;
                            case 2:
                                this.currentModeColor = ContextCompat.getColor(getContext(), R.color.mode2Color);
                                break;
                            case 3:
                                this.currentModeColor = ContextCompat.getColor(getContext(), R.color.mode3Color);
                                break;
                        }
                        this.powerButton.setColorFilter(null);
                        this.ambientButton.setColorFilter(null);
                        if (this.productId == 1 || this.productId == 2 || this.productId == 7 || this.dreamScreenFragmentListener.currentLightGroupedWithDreamScreen()) {
                            this.videoButton.setColorFilter(null);
                            ((TextView) this.view.findViewById(R.id.video_label)).setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
                            this.audioButton.setColorFilter(null);
                            ((TextView) this.view.findViewById(R.id.audio_label)).setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
                        } else {
                            this.videoButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                            ((TextView) this.view.findViewById(R.id.video_label)).setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                            this.audioButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                            ((TextView) this.view.findViewById(R.id.audio_label)).setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                        }
                        this.brightnessSeekBar.getProgressDrawable().setColorFilter(null);
                        if (this.productId == 4 && this.dreamScreenFragmentListener != null) {
                            if (this.dreamScreenFragmentListener.getLightType() == 0) {
                                this.brightnessSeekBar.setAlpha(0.3f);
                            } else {
                                this.brightnessSeekBar.setAlpha(1.0f);
                            }
                        }
                        updateHDMILayout();
                        this.userEdittingLockButton.setVisibility(4);
                        switch (this.mode) {
                            case 0:
                                this.powerButton.setColorFilter(this.currentModeColor);
                                this.newContainer = this.offContainer;
                                this.brightnessSeekBar.getProgressDrawable().setColorFilter(-8355712, Mode.LIGHTEN);
                                this.userEdittingLockButton.setVisibility(0);
                                break;
                            case 1:
                                this.videoButton.setColorFilter(this.currentModeColor);
                                this.newContainer = this.videoContainer;
                                break;
                            case 2:
                                this.audioButton.setColorFilter(this.currentModeColor);
                                this.newContainer = this.audioContainer;
                                if (this.productId != 3 && this.productId != 4) {
                                    switch (this.musicModeSource) {
                                        case 0:
                                            this.hdmiAudioButton.setColorFilter(this.currentModeColor);
                                            this.audioJackButton.setColorFilter(null);
                                            break;
                                        case 1:
                                            this.audioJackButton.setColorFilter(this.currentModeColor);
                                            this.hdmiAudioButton.setColorFilter(null);
                                            break;
                                        case 2:
                                            this.audioJackButton.setColorFilter(null);
                                            this.hdmiAudioButton.setColorFilter(null);
                                            break;
                                        case 3:
                                            this.audioJackButton.setColorFilter(null);
                                            this.hdmiAudioButton.setColorFilter(null);
                                            break;
                                    }
                                } else {
                                    this.hdmiAudioLayout.setVisibility(8);
                                    this.audioJackLayout.setVisibility(8);
                                    break;
                                }
                                break;
                            case 3:
                                this.ambientButton.setColorFilter(this.currentModeColor);
                                switch (this.ambientLayoutType) {
                                    case 0:
                                        this.newContainer = this.ambientStartContainer;
                                        break;
                                    case 1:
                                        this.newContainer = this.showContainer;
                                        if (this.oldShowButton != null) {
                                            this.oldShowButton.animate().scaleX(1.0f).setDuration(150).start();
                                            this.oldShowButton.animate().scaleY(1.0f).setDuration(150).start();
                                        }
                                        switch (this.ambientShowType) {
                                            case 0:
                                                this.oldShowButton = this.randomButton;
                                                break;
                                            case 1:
                                                this.oldShowButton = this.fireButton;
                                                break;
                                            case 2:
                                                this.oldShowButton = this.twinkleButton;
                                                break;
                                            case 3:
                                                this.oldShowButton = this.oceanButton;
                                                break;
                                            case 4:
                                                this.oldShowButton = this.prideButton;
                                                break;
                                            case 5:
                                                this.oldShowButton = this.julyButton;
                                                break;
                                            case 6:
                                                this.oldShowButton = this.holidayButton;
                                                break;
                                            case 7:
                                                this.oldShowButton = this.popButton;
                                                break;
                                            case 8:
                                                this.oldShowButton = this.forestButton;
                                                break;
                                            default:
                                                Log.i(tag, "ambient show type not valid, settings to 0");
                                                this.ambientShowType = 0;
                                                this.oldShowButton = this.randomButton;
                                                break;
                                        }
                                        this.oldShowButton.animate().scaleX(1.2f).setDuration(150).start();
                                        this.oldShowButton.animate().scaleY(1.2f).setDuration(150).start();
                                        break;
                                    case 2:
                                        this.newContainer = this.slidersContainer;
                                        this.redSeekBar.setProgress(this.currentAmbientColor[0]);
                                        this.greenSeekBar.setProgress(this.currentAmbientColor[1]);
                                        this.blueSeekBar.setProgress(this.currentAmbientColor[2]);
                                        this.redValueText.setText(String.valueOf(this.currentAmbientColor[0]));
                                        this.greenValueText.setText(String.valueOf(this.currentAmbientColor[1]));
                                        this.blueValueText.setText(String.valueOf(this.currentAmbientColor[2]));
                                        this.slidersPicker.setColorFilter(Color.rgb(this.currentAmbientColor[0], this.currentAmbientColor[1], this.currentAmbientColor[2]));
                                        break;
                                    case 3:
                                        this.newContainer = this.colorWheelContainer;
                                        this.colorWheelPicker.setColorFilter(Color.rgb(this.currentAmbientColor[0], this.currentAmbientColor[1], this.currentAmbientColor[2]));
                                        break;
                                    case 4:
                                        this.newContainer = this.whitesWheelContainer;
                                        this.whitesWheelPicker.setColorFilter(Color.rgb(this.currentAmbientColor[0], this.currentAmbientColor[1], this.currentAmbientColor[2]));
                                        break;
                                    default:
                                        this.newContainer = this.ambientStartContainer;
                                        break;
                                }
                        }
                        if (this.newContainer != this.currentContainer) {
                            this.currentContainer.startAnimation(this.fadeOutAnimation);
                        }
                    }

                    public void dataChanged() {
                        if (this.brightness != this.dreamScreenFragmentListener.getBrightness()) {
                            this.brightness = this.dreamScreenFragmentListener.getBrightness();
                            this.brightnessSeekBar.setProgress(this.brightness);
                        }
                        if (this.productId == 4 && this.dreamScreenFragmentListener != null) {
                            if (this.dreamScreenFragmentListener.getLightType() == 0) {
                                this.brightnessSeekBar.setAlpha(0.3f);
                            } else {
                                this.brightnessSeekBar.setAlpha(1.0f);
                            }
                        }
                        if (this.mode != this.dreamScreenFragmentListener.getMode()) {
                            this.mode = this.dreamScreenFragmentListener.getMode();
                            if (this.mode < 0 || this.mode > 3) {
                                this.mode = 0;
                            }
                            byte[] currentAmbientBytes = this.dreamScreenFragmentListener.getAmbientColor();
                            this.currentAmbientColor[0] = currentAmbientBytes[0] & 255;
                            this.currentAmbientColor[1] = currentAmbientBytes[1] & 255;
                            this.currentAmbientColor[2] = currentAmbientBytes[2] & 255;
                            if (this.productId == 1 || this.productId == 2) {
                                this.hdmiInputName1 = this.dreamScreenFragmentListener.getHdmiInputName1();
                                this.hdmiInputName2 = this.dreamScreenFragmentListener.getHdmiInputName2();
                                this.hdmiInputName3 = this.dreamScreenFragmentListener.getHdmiInputName3();
                                this.hdmiInput = this.dreamScreenFragmentListener.getHdmiInput();
                                this.hdmiActiveChannels = this.dreamScreenFragmentListener.getHdmiActiveChannels();
                                this.musicModeSource = this.dreamScreenFragmentListener.getMusicModeSource();
                            } else if (this.productId == 7) {
                                this.musicModeSource = this.dreamScreenFragmentListener.getMusicModeSource();
                            }
                            this.ambientModeType = this.dreamScreenFragmentListener.getAmbientModeType();
                            this.ambientShowType = this.dreamScreenFragmentListener.getAmbientShowType();
                            redrawFragment();
                            return;
                        }
                        if (this.productId != 1 && this.productId != 2) {
                            if (this.productId == 7 && this.musicModeSource != this.dreamScreenFragmentListener.getMusicModeSource()) {
                                this.musicModeSource = this.dreamScreenFragmentListener.getMusicModeSource();
                                if (this.audioContainer.isShown()) {
                                    switch (this.musicModeSource) {
                                        case 0:
                                            this.hdmiAudioButton.setColorFilter(this.currentModeColor);
                                            this.audioJackButton.setColorFilter(null);
                                            break;
                                        case 1:
                                            this.audioJackButton.setColorFilter(this.currentModeColor);
                                            this.hdmiAudioButton.setColorFilter(null);
                                            break;
                                    }
                                }
                            }
                        } else {
                            this.hdmiInputName1 = this.dreamScreenFragmentListener.getHdmiInputName1();
                            this.hdmiInputName2 = this.dreamScreenFragmentListener.getHdmiInputName2();
                            this.hdmiInputName3 = this.dreamScreenFragmentListener.getHdmiInputName3();
                            this.hdmiInput = this.dreamScreenFragmentListener.getHdmiInput();
                            this.hdmiActiveChannels = this.dreamScreenFragmentListener.getHdmiActiveChannels();
                            updateHDMILayout();
                            if (this.musicModeSource != this.dreamScreenFragmentListener.getMusicModeSource()) {
                                this.musicModeSource = this.dreamScreenFragmentListener.getMusicModeSource();
                                if (this.audioContainer.isShown()) {
                                    switch (this.musicModeSource) {
                                        case 0:
                                            this.hdmiAudioButton.setColorFilter(this.currentModeColor);
                                            this.audioJackButton.setColorFilter(null);
                                            break;
                                        case 1:
                                            this.audioJackButton.setColorFilter(this.currentModeColor);
                                            this.hdmiAudioButton.setColorFilter(null);
                                            break;
                                        case 2:
                                            this.audioJackButton.setColorFilter(null);
                                            this.hdmiAudioButton.setColorFilter(null);
                                            break;
                                        case 3:
                                            this.audioJackButton.setColorFilter(null);
                                            this.hdmiAudioButton.setColorFilter(null);
                                            break;
                                    }
                                }
                            }
                        }
                        this.ambientModeType = this.dreamScreenFragmentListener.getAmbientModeType();
                        this.ambientShowType = this.dreamScreenFragmentListener.getAmbientShowType();
                        byte[] currentAmbientBytes2 = this.dreamScreenFragmentListener.getAmbientColor();
                        if ((currentAmbientBytes2 != null && currentAmbientBytes2.length == 3 && this.currentAmbientColor[0] != (currentAmbientBytes2[0] & 255)) || this.currentAmbientColor[1] != (currentAmbientBytes2[1] & 255) || this.currentAmbientColor[2] != (currentAmbientBytes2[2] & 255)) {
                            this.currentAmbientColor[0] = currentAmbientBytes2[0] & 255;
                            this.currentAmbientColor[1] = currentAmbientBytes2[1] & 255;
                            this.currentAmbientColor[2] = currentAmbientBytes2[2] & 255;
                            if (this.slidersContainer.isShown()) {
                                this.redSeekBar.setProgress(this.currentAmbientColor[0]);
                                this.greenSeekBar.setProgress(this.currentAmbientColor[1]);
                                this.blueSeekBar.setProgress(this.currentAmbientColor[2]);
                                this.redValueText.setText(String.valueOf(this.currentAmbientColor[0]));
                                this.greenValueText.setText(String.valueOf(this.currentAmbientColor[1]));
                                this.blueValueText.setText(String.valueOf(this.currentAmbientColor[2]));
                                this.slidersPicker.setColorFilter(Color.rgb(this.currentAmbientColor[0], this.currentAmbientColor[1], this.currentAmbientColor[2]));
                            } else if (this.colorWheelContainer.isShown()) {
                                this.colorWheelPicker.setColorFilter(Color.rgb(this.currentAmbientColor[0], this.currentAmbientColor[1], this.currentAmbientColor[2]));
                            } else if (this.whitesWheelContainer.isShown()) {
                                this.whitesWheelPicker.setColorFilter(Color.rgb(this.currentAmbientColor[0], this.currentAmbientColor[1], this.currentAmbientColor[2]));
                            }
                        }
                    }

                    /* access modifiers changed from: private */
                    public void updateHDMILayout() {
                        Log.i(tag, "updateHDMILayout");
                        this.hdmiLayout.setVisibility(0);
                        if (this.productId == 3 || this.productId == 4 || this.productId == 7) {
                            this.hdmiLayout.setVisibility(8);
                            return;
                        }
                        this.hdmi1Button.setColorFilter(null);
                        this.hdmi2Button.setColorFilter(null);
                        this.hdmi3Button.setColorFilter(null);
                        this.hdmi1Text.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
                        this.hdmi2Text.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
                        this.hdmi3Text.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
                        switch (this.hdmiInput) {
                            case 0:
                                this.hdmi1Button.setColorFilter(this.currentModeColor);
                                break;
                            case 1:
                                this.hdmi2Button.setColorFilter(this.currentModeColor);
                                break;
                            case 2:
                                this.hdmi3Button.setColorFilter(this.currentModeColor);
                                break;
                        }
                        if ((this.hdmiActiveChannels & 1) == 0) {
                            this.hdmi1Button.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                            this.hdmi1Text.setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                        }
                        if (((this.hdmiActiveChannels >> 1) & 1) == 0) {
                            this.hdmi2Button.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                            this.hdmi2Text.setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                        }
                        if (((this.hdmiActiveChannels >> 2) & 1) == 0) {
                            this.hdmi3Button.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                            this.hdmi3Text.setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                        }
                        if (this.userEdittingUnlocked) {
                            this.userEdittingLockButton.setImageResource(R.drawable.ic_lock_open_white_36dp);
                            this.hdmi1Edit.setVisibility(0);
                            this.hdmi2Edit.setVisibility(0);
                            this.hdmi3Edit.setVisibility(0);
                            this.hdmi1Text.setFocusable(true);
                            this.hdmi1Text.setFocusableInTouchMode(true);
                            this.hdmi2Text.setFocusable(true);
                            this.hdmi2Text.setFocusableInTouchMode(true);
                            this.hdmi3Text.setFocusable(true);
                            this.hdmi3Text.setFocusableInTouchMode(true);
                            return;
                        }
                        this.userEdittingLockButton.setImageResource(R.drawable.ic_lock_outline_white_36dp);
                        this.hdmi1Edit.setVisibility(8);
                        this.hdmi2Edit.setVisibility(8);
                        this.hdmi3Edit.setVisibility(8);
                        this.hdmi1Text.setFocusable(false);
                        this.hdmi1Text.setFocusableInTouchMode(false);
                        this.hdmi2Text.setFocusable(false);
                        this.hdmi2Text.setFocusableInTouchMode(false);
                        this.hdmi3Text.setFocusable(false);
                        this.hdmi3Text.setFocusableInTouchMode(false);
                    }

                    /* access modifiers changed from: private */
                    public void showFeatureDisabledDialog() {
                        closeFeatureDisabledDialog();
                        View alertDialogView = getActivity().getLayoutInflater().inflate(R.layout.feature_disabled_dialog, null);
                        TextView headerText = (TextView) alertDialogView.findViewById(R.id.headerText);
                        TextView subText = (TextView) alertDialogView.findViewById(R.id.subText);
                        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
                        headerText.setTypeface(font);
                        subText.setTypeface(font);
                        if (this.productId == 3) {
                            subText.setText("Music Mode and Video Mode are only applicable when SideKick is grouped with a DreamScreen.");
                        } else if (this.productId == 4) {
                            subText.setText("Music Mode and Video Mode are only applicable when Connect is grouped with a DreamScreen.");
                        }
                        ((TextView) alertDialogView.findViewById(R.id.addText)).setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                DreamScreenFragment.this.closeFeatureDisabledDialog();
                            }
                        });
                        Builder alertDialogBuilder = new Builder(getActivity());
                        alertDialogBuilder.setView(alertDialogView).setCancelable(false);
                        this.alertDialog = alertDialogBuilder.create();
                        this.alertDialog.show();
                    }

                    /* access modifiers changed from: private */
                    public void closeFeatureDisabledDialog() {
                        if (this.alertDialog != null && this.alertDialog.isShowing()) {
                            this.alertDialog.dismiss();
                        }
                    }

                    private int pxToDp(int px) {
                        return (int) TypedValue.applyDimension(1, (float) px, getResources().getDisplayMetrics());
                    }

                    public void onAttach(Context context) {
                        super.onAttach(context);
                        if (context instanceof DreamScreenFragmentListener) {
                            this.dreamScreenFragmentListener = (DreamScreenFragmentListener) context;
                            return;
                        }
                        throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
                    }

                    public void onDetach() {
                        super.onDetach();
                        this.dreamScreenFragmentListener = null;
                    }
                }


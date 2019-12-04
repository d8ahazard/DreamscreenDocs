package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;

public class VideoSettingsFragment extends Fragment {
    private static final long packetSendDelay = 30;
    private static final String tag = "VideoSettingsFrag";
    private SeekBar blueSaturationSeekBar;
    /* access modifiers changed from: private */
    public TextView blueSaturationText;
    private TextView blueText;
    /* access modifiers changed from: private */
    public int colorBoost = 0;
    private ImageView colorBoostHelp;
    /* access modifiers changed from: private */
    public LinearLayout colorBoostLayout;
    /* access modifiers changed from: private */
    public TextView colorBoostText;
    /* access modifiers changed from: private */
    public TextView colorBoostTextCurrent;
    private ImageView colorBoostToggle;
    /* access modifiers changed from: private */
    public int fadeRate = 0;
    private ImageView fadeRateHelp;
    /* access modifiers changed from: private */
    public LinearLayout fadeRateLayout;
    /* access modifiers changed from: private */
    public TextView fadeRateValueText;
    private SeekBar fadeSeekBar;
    private TextView fadeText;
    private SeekBar greenSaturationSeekBar;
    /* access modifiers changed from: private */
    public TextView greenSaturationText;
    private TextView greenText;
    private TextView headerText;
    /* access modifiers changed from: private */
    public final LayoutParams helpLayoutParams = new LayoutParams(-1, -2);
    /* access modifiers changed from: private */
    public TextView helpTextView;
    /* access modifiers changed from: private */
    public TextView minLuminosityText;
    private TextView minText;
    /* access modifiers changed from: private */
    public byte[] minimumLuminosity = {47, 47, 47};
    private ImageView minimumLuminosityHelp;
    /* access modifiers changed from: private */
    public LinearLayout minimumLuminosityLayout;
    private SeekBar minimumLuminositySeekBar;
    /* access modifiers changed from: private */
    public long packetSentAtTime = SystemClock.elapsedRealtime();
    private SeekBar redSaturationSeekBar;
    /* access modifiers changed from: private */
    public TextView redSaturationText;
    private TextView redText;
    /* access modifiers changed from: private */
    public LinearLayout rootLayout;
    /* access modifiers changed from: private */
    public byte[] saturation = {-1, -1, -1};
    private ImageView saturationHelp;
    /* access modifiers changed from: private */
    public LinearLayout saturationLayout;
    private TextView saturationText;
    /* access modifiers changed from: private */
    public VideoSettingsFragmentListener videoSettingsFragmentListener;
    /* access modifiers changed from: private */
    public int widescreenEnable = 0;
    private ImageView widescreenHelp;
    /* access modifiers changed from: private */
    public LinearLayout widescreenLayout;
    private TextView widescreenText;
    /* access modifiers changed from: private */
    public ImageView widescreenToggle;

    public interface VideoSettingsFragmentListener {
        int getColorBoost();

        int getFadeRate();

        int getLetterboxingEnable();

        byte[] getMinimumLuminosity();

        byte[] getSaturation();

        void setColorBoost(int i);

        void setFadeRate(int i);

        void setLetterboxingEnable(int i);

        void setMinimumLuminosity(byte[] bArr);

        void setSaturation(byte[] bArr);
    }

    public static VideoSettingsFragment newInstance() {
        return new VideoSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.widescreenEnable = this.videoSettingsFragmentListener.getLetterboxingEnable();
        this.saturation = this.videoSettingsFragmentListener.getSaturation();
        this.fadeRate = this.videoSettingsFragmentListener.getFadeRate() - 4;
        this.minimumLuminosity = this.videoSettingsFragmentListener.getMinimumLuminosity();
        this.colorBoost = this.videoSettingsFragmentListener.getColorBoost();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_settings, container, false);
        this.headerText = (TextView) view.findViewById(R.id.headerText);
        this.saturationText = (TextView) view.findViewById(R.id.saturationText);
        this.redText = (TextView) view.findViewById(R.id.redText2);
        this.greenText = (TextView) view.findViewById(R.id.greenText);
        this.blueText = (TextView) view.findViewById(R.id.blueText);
        this.widescreenText = (TextView) view.findViewById(R.id.widescreenText);
        this.colorBoostText = (TextView) view.findViewById(R.id.colorBoostText);
        this.colorBoostTextCurrent = (TextView) view.findViewById(R.id.colorBoostTextCurrent);
        this.fadeText = (TextView) view.findViewById(R.id.fadeText);
        this.minText = (TextView) view.findViewById(R.id.minText);
        this.redSaturationText = (TextView) view.findViewById(R.id.redSaturationText);
        this.greenSaturationText = (TextView) view.findViewById(R.id.greenSaturationText);
        this.blueSaturationText = (TextView) view.findViewById(R.id.blueSaturationText);
        this.fadeRateValueText = (TextView) view.findViewById(R.id.fadeRateValueText);
        this.minLuminosityText = (TextView) view.findViewById(R.id.minLuminosityText);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.headerText.setTypeface(font);
        this.saturationText.setTypeface(font);
        this.redText.setTypeface(font);
        this.greenText.setTypeface(font);
        this.blueText.setTypeface(font);
        this.widescreenText.setTypeface(font);
        this.colorBoostText.setTypeface(font);
        this.colorBoostTextCurrent.setTypeface(font);
        this.fadeText.setTypeface(font);
        this.minText.setTypeface(font);
        this.redSaturationText.setTypeface(font);
        this.greenSaturationText.setTypeface(font);
        this.blueSaturationText.setTypeface(font);
        this.fadeRateValueText.setTypeface(font);
        this.minLuminosityText.setTypeface(font);
        this.redSaturationSeekBar = (SeekBar) view.findViewById(R.id.SeekBar_Red);
        this.greenSaturationSeekBar = (SeekBar) view.findViewById(R.id.SeekBar_Green);
        this.blueSaturationSeekBar = (SeekBar) view.findViewById(R.id.SeekBar_blue);
        this.widescreenToggle = (ImageView) view.findViewById(R.id.widescreenToggle);
        this.colorBoostToggle = (ImageView) view.findViewById(R.id.colorBoostToggle);
        this.fadeSeekBar = (SeekBar) view.findViewById(R.id.SeekBar_fade);
        this.minimumLuminositySeekBar = (SeekBar) view.findViewById(R.id.SeekBar_min);
        this.redSaturationSeekBar.setProgress(this.saturation[0] & 255);
        this.greenSaturationSeekBar.setProgress(this.saturation[1] & 255);
        this.blueSaturationSeekBar.setProgress(this.saturation[2] & 255);
        this.redSaturationText.setText(String.valueOf(Math.round((((float) this.redSaturationSeekBar.getProgress()) / 255.0f) * 100.0f)) + "%");
        this.greenSaturationText.setText(String.valueOf(Math.round((((float) this.greenSaturationSeekBar.getProgress()) / 255.0f) * 100.0f)) + "%");
        this.blueSaturationText.setText(String.valueOf(Math.round((((float) this.blueSaturationSeekBar.getProgress()) / 255.0f) * 100.0f)) + "%");
        if (this.widescreenEnable == 0) {
            this.widescreenToggle.setImageResource(R.drawable.toggle3_auto);
        } else if (this.widescreenEnable == 1) {
            this.widescreenToggle.setImageResource(R.drawable.toggle3_on);
        } else {
            this.widescreenToggle.setImageResource(R.drawable.toggle3_off);
        }
        switch (this.colorBoost) {
            case 0:
                this.colorBoostTextCurrent.setText(getContext().getResources().getString(R.string.Off));
                break;
            case 1:
                this.colorBoostTextCurrent.setText(getContext().getResources().getString(R.string.Low));
                break;
            case 2:
                this.colorBoostTextCurrent.setText(getContext().getResources().getString(R.string.Med));
                break;
            case 3:
                this.colorBoostTextCurrent.setText(getContext().getResources().getString(R.string.High));
                break;
            default:
                this.colorBoostText.setText(getContext().getResources().getString(R.string.Off));
                break;
        }
        this.fadeSeekBar.setProgress(this.fadeRate);
        int newPercentage = Math.round((((float) (this.fadeRate - 4)) / 46.0f) * 100.0f);
        if (newPercentage < 0) {
            newPercentage = 0;
        } else if (newPercentage > 100) {
            newPercentage = 100;
        }
        this.fadeRateValueText.setText(String.valueOf(newPercentage) + "%");
        this.minimumLuminositySeekBar.setProgress(this.minimumLuminosity[0] & 255);
        this.minLuminosityText.setText(String.valueOf(Math.round((((float) this.minimumLuminositySeekBar.getProgress()) / 47.0f) * 100.0f)) + "%");
        this.rootLayout = (LinearLayout) view.findViewById(R.id.rootLayout);
        this.saturationLayout = (LinearLayout) view.findViewById(R.id.saturationLayout);
        this.widescreenLayout = (LinearLayout) view.findViewById(R.id.widescreenLayout);
        this.colorBoostLayout = (LinearLayout) view.findViewById(R.id.colorBoostLayout);
        this.fadeRateLayout = (LinearLayout) view.findViewById(R.id.fadeRateLayout);
        this.minimumLuminosityLayout = (LinearLayout) view.findViewById(R.id.minimumLuminosityLayout);
        this.saturationHelp = (ImageView) view.findViewById(R.id.saturationHelp);
        this.widescreenHelp = (ImageView) view.findViewById(R.id.widescreenHelp);
        this.colorBoostHelp = (ImageView) view.findViewById(R.id.colorBoostHelp);
        this.fadeRateHelp = (ImageView) view.findViewById(R.id.fadeRateHelp);
        this.minimumLuminosityHelp = (ImageView) view.findViewById(R.id.minimumLuminosityHelp);
        this.redSaturationSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getProgress() < 64) {
                    seekBar.setProgress(64);
                    return;
                }
                VideoSettingsFragment.this.saturation[0] = (byte) progress;
                if (SystemClock.elapsedRealtime() - VideoSettingsFragment.this.packetSentAtTime > VideoSettingsFragment.packetSendDelay) {
                    VideoSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    VideoSettingsFragment.this.videoSettingsFragmentListener.setSaturation(VideoSettingsFragment.this.saturation);
                    VideoSettingsFragment.this.redSaturationText.setText(String.valueOf(Math.round((((float) progress) / 255.0f) * 100.0f)) + "%");
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
                    return;
                }
                VideoSettingsFragment.this.saturation[1] = (byte) progress;
                if (SystemClock.elapsedRealtime() - VideoSettingsFragment.this.packetSentAtTime > VideoSettingsFragment.packetSendDelay) {
                    VideoSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    VideoSettingsFragment.this.videoSettingsFragmentListener.setSaturation(VideoSettingsFragment.this.saturation);
                    VideoSettingsFragment.this.greenSaturationText.setText(String.valueOf(Math.round((((float) progress) / 255.0f) * 100.0f)) + "%");
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
                    return;
                }
                VideoSettingsFragment.this.saturation[2] = (byte) progress;
                if (SystemClock.elapsedRealtime() - VideoSettingsFragment.this.packetSentAtTime > VideoSettingsFragment.packetSendDelay) {
                    VideoSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    VideoSettingsFragment.this.videoSettingsFragmentListener.setSaturation(VideoSettingsFragment.this.saturation);
                    VideoSettingsFragment.this.blueSaturationText.setText(String.valueOf(Math.round((((float) progress) / 255.0f) * 100.0f)) + "%");
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.widescreenToggle.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == 0) {
                    if (((double) event.getX()) < ((double) view.getWidth()) * 0.33d) {
                        VideoSettingsFragment.this.widescreenEnable = 0;
                    } else if (((double) event.getX()) < ((double) view.getWidth()) * 0.66d) {
                        VideoSettingsFragment.this.widescreenEnable = 1;
                    } else {
                        VideoSettingsFragment.this.widescreenEnable = 2;
                    }
                    VideoSettingsFragment.this.videoSettingsFragmentListener.setLetterboxingEnable(VideoSettingsFragment.this.widescreenEnable);
                    if (VideoSettingsFragment.this.widescreenEnable == 0) {
                        VideoSettingsFragment.this.widescreenToggle.setImageResource(R.drawable.toggle3_auto);
                    } else if (VideoSettingsFragment.this.widescreenEnable == 1) {
                        VideoSettingsFragment.this.widescreenToggle.setImageResource(R.drawable.toggle3_on);
                    } else {
                        VideoSettingsFragment.this.widescreenToggle.setImageResource(R.drawable.toggle3_off);
                    }
                }
                return true;
            }
        });
        this.colorBoostToggle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0005: INVOKE  (wrap: com.lab714.dreamscreenv2.layout.VideoSettingsFragment
                      0x0003: IGET  (r0v0 com.lab714.dreamscreenv2.layout.VideoSettingsFragment) = (r3v0 'this' com.lab714.dreamscreenv2.layout.VideoSettingsFragment$5 A[THIS]) com.lab714.dreamscreenv2.layout.VideoSettingsFragment.5.this$0 com.lab714.dreamscreenv2.layout.VideoSettingsFragment) com.lab714.dreamscreenv2.layout.VideoSettingsFragment.access$808(com.lab714.dreamscreenv2.layout.VideoSettingsFragment):int type: STATIC in method: com.lab714.dreamscreenv2.layout.VideoSettingsFragment.5.onClick(android.view.View):void, dex: classes.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:245)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
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
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:773)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:713)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
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
                    	... 38 more
                    Caused by: java.lang.ClassNotFoundException: sun.reflect.ReflectionFactory
                    	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(Unknown Source)
                    	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(Unknown Source)
                    	at java.base/java.lang.ClassLoader.loadClass(Unknown Source)
                    	at java.base/java.lang.Class.forName0(Native Method)
                    	at java.base/java.lang.Class.forName(Unknown Source)
                    	at org.objenesis.instantiator.sun.SunReflectionFactoryHelper.getReflectionFactoryClass(SunReflectionFactoryHelper.java:54)
                    	... 56 more
                    */
                /*
                    this = this;
                    r2 = 2131492950(0x7f0c0056, float:1.8609366E38)
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    
                    // error: 0x0005: INVOKE  (r0 I:com.lab714.dreamscreenv2.layout.VideoSettingsFragment) com.lab714.dreamscreenv2.layout.VideoSettingsFragment.access$808(com.lab714.dreamscreenv2.layout.VideoSettingsFragment):int type: STATIC
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    int r0 = r0.colorBoost
                    r1 = 3
                    if (r0 > r1) goto L_0x0019
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    int r0 = r0.colorBoost
                    if (r0 >= 0) goto L_0x001f
                L_0x0019:
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    r1 = 0
                    r0.colorBoost = r1
                L_0x001f:
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    int r0 = r0.colorBoost
                    switch(r0) {
                        case 0: goto L_0x004f;
                        case 1: goto L_0x0067;
                        case 2: goto L_0x0082;
                        case 3: goto L_0x009d;
                        default: goto L_0x0028;
                    }
                L_0x0028:
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.widget.TextView r0 = r0.colorBoostText
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r1 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.content.Context r1 = r1.getContext()
                    android.content.res.Resources r1 = r1.getResources()
                    java.lang.String r1 = r1.getString(r2)
                    r0.setText(r1)
                L_0x003f:
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment$VideoSettingsFragmentListener r0 = r0.videoSettingsFragmentListener
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r1 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    int r1 = r1.colorBoost
                    r0.setColorBoost(r1)
                    return
                L_0x004f:
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.widget.TextView r0 = r0.colorBoostTextCurrent
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r1 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.content.Context r1 = r1.getContext()
                    android.content.res.Resources r1 = r1.getResources()
                    java.lang.String r1 = r1.getString(r2)
                    r0.setText(r1)
                    goto L_0x003f
                L_0x0067:
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.widget.TextView r0 = r0.colorBoostTextCurrent
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r1 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.content.Context r1 = r1.getContext()
                    android.content.res.Resources r1 = r1.getResources()
                    r2 = 2131492936(0x7f0c0048, float:1.8609338E38)
                    java.lang.String r1 = r1.getString(r2)
                    r0.setText(r1)
                    goto L_0x003f
                L_0x0082:
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.widget.TextView r0 = r0.colorBoostTextCurrent
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r1 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.content.Context r1 = r1.getContext()
                    android.content.res.Resources r1 = r1.getResources()
                    r2 = 2131492937(0x7f0c0049, float:1.860934E38)
                    java.lang.String r1 = r1.getString(r2)
                    r0.setText(r1)
                    goto L_0x003f
                L_0x009d:
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r0 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.widget.TextView r0 = r0.colorBoostTextCurrent
                    com.lab714.dreamscreenv2.layout.VideoSettingsFragment r1 = com.lab714.dreamscreenv2.layout.VideoSettingsFragment.this
                    android.content.Context r1 = r1.getContext()
                    android.content.res.Resources r1 = r1.getResources()
                    r2 = 2131492924(0x7f0c003c, float:1.8609314E38)
                    java.lang.String r1 = r1.getString(r2)
                    r0.setText(r1)
                    goto L_0x003f
                */
                throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.layout.VideoSettingsFragment.AnonymousClass5.onClick(android.view.View):void");
            }
        });
        this.fadeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                VideoSettingsFragment.this.fadeRate = progress + 4;
                if (SystemClock.elapsedRealtime() - VideoSettingsFragment.this.packetSentAtTime > VideoSettingsFragment.packetSendDelay || progress == 0) {
                    VideoSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    VideoSettingsFragment.this.videoSettingsFragmentListener.setFadeRate(VideoSettingsFragment.this.fadeRate);
                    VideoSettingsFragment.this.fadeRateValueText.setText(String.valueOf(Math.round((((float) (VideoSettingsFragment.this.fadeRate - 4)) / 46.0f) * 100.0f)) + "%");
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.minimumLuminositySeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                VideoSettingsFragment.this.minimumLuminosity[0] = (byte) progress;
                VideoSettingsFragment.this.minimumLuminosity[1] = (byte) progress;
                VideoSettingsFragment.this.minimumLuminosity[2] = (byte) progress;
                if (SystemClock.elapsedRealtime() - VideoSettingsFragment.this.packetSentAtTime > VideoSettingsFragment.packetSendDelay || progress == 0) {
                    VideoSettingsFragment.this.packetSentAtTime = SystemClock.elapsedRealtime();
                    VideoSettingsFragment.this.videoSettingsFragmentListener.setMinimumLuminosity(VideoSettingsFragment.this.minimumLuminosity);
                    VideoSettingsFragment.this.minLuminosityText.setText(String.valueOf(Math.round((((float) progress) / 47.0f) * 100.0f)) + "%");
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
                VideoSettingsFragment.this.rootLayout.removeView(VideoSettingsFragment.this.helpTextView);
                if (!VideoSettingsFragment.this.helpTextView.getText().equals(VideoSettingsFragment.this.getContext().getResources().getString(R.string.saturationHelpTextDreamScreen)) || !VideoSettingsFragment.this.helpTextView.isShown()) {
                    VideoSettingsFragment.this.helpTextView.setText(VideoSettingsFragment.this.getContext().getResources().getString(R.string.saturationHelpTextDreamScreen));
                    VideoSettingsFragment.this.rootLayout.addView(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.rootLayout.indexOfChild(VideoSettingsFragment.this.saturationLayout) + 1, VideoSettingsFragment.this.helpLayoutParams);
                    VideoSettingsFragment.this.rootLayout.requestChildFocus(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.helpTextView);
                }
            }
        });
        this.widescreenHelp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                VideoSettingsFragment.this.rootLayout.removeView(VideoSettingsFragment.this.helpTextView);
                if (!VideoSettingsFragment.this.helpTextView.getText().equals(VideoSettingsFragment.this.getContext().getResources().getString(R.string.widescreenHelpText)) || !VideoSettingsFragment.this.helpTextView.isShown()) {
                    VideoSettingsFragment.this.helpTextView.setText(VideoSettingsFragment.this.getContext().getResources().getString(R.string.widescreenHelpText));
                    VideoSettingsFragment.this.rootLayout.addView(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.rootLayout.indexOfChild(VideoSettingsFragment.this.widescreenLayout) + 1, VideoSettingsFragment.this.helpLayoutParams);
                    VideoSettingsFragment.this.rootLayout.requestChildFocus(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.helpTextView);
                }
            }
        });
        this.colorBoostHelp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                VideoSettingsFragment.this.rootLayout.removeView(VideoSettingsFragment.this.helpTextView);
                if (!VideoSettingsFragment.this.helpTextView.getText().equals(VideoSettingsFragment.this.getContext().getResources().getString(R.string.colorBoostHelpText)) || !VideoSettingsFragment.this.helpTextView.isShown()) {
                    VideoSettingsFragment.this.helpTextView.setText(VideoSettingsFragment.this.getContext().getResources().getString(R.string.colorBoostHelpText));
                    VideoSettingsFragment.this.rootLayout.addView(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.rootLayout.indexOfChild(VideoSettingsFragment.this.colorBoostLayout) + 1, VideoSettingsFragment.this.helpLayoutParams);
                    VideoSettingsFragment.this.rootLayout.requestChildFocus(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.helpTextView);
                }
            }
        });
        this.fadeRateHelp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                VideoSettingsFragment.this.rootLayout.removeView(VideoSettingsFragment.this.helpTextView);
                if (!VideoSettingsFragment.this.helpTextView.getText().equals(VideoSettingsFragment.this.getContext().getResources().getString(R.string.fadeRateHelpText)) || !VideoSettingsFragment.this.helpTextView.isShown()) {
                    VideoSettingsFragment.this.helpTextView.setText(VideoSettingsFragment.this.getContext().getResources().getString(R.string.fadeRateHelpText));
                    VideoSettingsFragment.this.rootLayout.addView(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.rootLayout.indexOfChild(VideoSettingsFragment.this.fadeRateLayout) + 1, VideoSettingsFragment.this.helpLayoutParams);
                    VideoSettingsFragment.this.rootLayout.requestChildFocus(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.helpTextView);
                }
            }
        });
        this.minimumLuminosityHelp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                VideoSettingsFragment.this.rootLayout.removeView(VideoSettingsFragment.this.helpTextView);
                if (!VideoSettingsFragment.this.helpTextView.getText().equals(VideoSettingsFragment.this.getContext().getResources().getString(R.string.minimumLuminosityHelpText)) || !VideoSettingsFragment.this.helpTextView.isShown()) {
                    VideoSettingsFragment.this.helpTextView.setText(VideoSettingsFragment.this.getContext().getResources().getString(R.string.minimumLuminosityHelpText));
                    VideoSettingsFragment.this.rootLayout.addView(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.rootLayout.indexOfChild(VideoSettingsFragment.this.minimumLuminosityLayout) + 1, VideoSettingsFragment.this.helpLayoutParams);
                    VideoSettingsFragment.this.rootLayout.requestChildFocus(VideoSettingsFragment.this.helpTextView, VideoSettingsFragment.this.helpTextView);
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
        if (context instanceof VideoSettingsFragmentListener) {
            this.videoSettingsFragmentListener = (VideoSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement Listener");
    }

    public void onDetach() {
        super.onDetach();
        this.videoSettingsFragmentListener = null;
    }
}

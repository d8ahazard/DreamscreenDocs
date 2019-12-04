package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.lab714.dreamscreenv2.R;
import com.lab714.dreamscreenv2.devices.Connect;
import com.philips.lighting.hue.sdk.wrapper.Persistence;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedEvent;
import com.philips.lighting.hue.sdk.wrapper.connection.ConnectionEvent;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery.Callback;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery.Option;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscoveryImpl;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscoveryResult;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeBuilder;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipResponse;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Group;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.GroupType;
import com.philips.lighting.hue.sdk.wrapper.utilities.InitSdk;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class HueLifxSettingsFragment extends Fragment {
    private static final String HUE_APP_NAME = "dreamscreen";
    private static final String HUE_DEVICE_NAME = "dreamscreen_device";
    private static final int LIFX_PORT = 56700;
    private static final String[] dropDownItems = {"None", "Hue", "LIFX"};
    private static final byte[] getLabelCommand = {36, 0, 0, 52, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 0, 0, 0};
    /* access modifiers changed from: private */
    public static final byte[] getVersionCommand = {36, 0, 0, 52, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 0, 0, 0};
    /* access modifiers changed from: private */
    public static final byte[] sectorData = {123, 61, 68, -50, 30, 86, -80, 18, 69, -91, 11, 106, -63, 26, 100, -73, 37, 61, 114, -114, 86, -105, 112, 37, 126, -79, 45, -51, -78, 34, -68, -59, 38, -65, 98, 45};
    private static final String tag = "HueLifxSettingsFrag";
    /* access modifiers changed from: private */
    public Bridge bridge;
    /* access modifiers changed from: private */
    public BridgeConnectionCallback bridgeConnectionCallback = new BridgeConnectionCallback() {
        public void onConnectionEvent(BridgeConnection bridgeConnection, ConnectionEvent connectionEvent) {
            switch (AnonymousClass33.$SwitchMap$com$philips$lighting$hue$sdk$wrapper$connection$ConnectionEvent[connectionEvent.ordinal()]) {
                case 1:
                    HueLifxSettingsFragment.this.handler.post(new Runnable() {
                        public void run() {
                            Log.i(HueLifxSettingsFragment.tag, "onAuthenticationRequired");
                            if (HueLifxSettingsFragment.this.currentBridgeEntry != null) {
                                HueLifxSettingsFragment.this.currentBridgeEntry.setChecked(false);
                            }
                            if (HueLifxSettingsFragment.this.discoverHueDialog != null && HueLifxSettingsFragment.this.discoverHueDialog.isShowing()) {
                                HueLifxSettingsFragment.this.discoverHueDialog.dismiss();
                            }
                            if (HueLifxSettingsFragment.this.promptPushLinkDialog == null || !HueLifxSettingsFragment.this.promptPushLinkDialog.isShowing()) {
                                HueLifxSettingsFragment.this.promptPushLinkDialog.show();
                            }
                        }
                    });
                    return;
                case 2:
                    Log.i(HueLifxSettingsFragment.tag, "LINK_BUTTON_NOT_PRESSED");
                    if (HueLifxSettingsFragment.this.progressBar != null) {
                        HueLifxSettingsFragment.this.progressBar.incrementProgressBy(1);
                        if (HueLifxSettingsFragment.this.progressBar.getProgress() >= 30) {
                            if (HueLifxSettingsFragment.this.bridge != null) {
                                HueLifxSettingsFragment.this.bridge.disconnect();
                                HueLifxSettingsFragment.this.bridge = null;
                            }
                            HueLifxSettingsFragment.this.handler.post(new Runnable() {
                                public void run() {
                                    if (HueLifxSettingsFragment.this.promptPushLinkDialog != null && HueLifxSettingsFragment.this.promptPushLinkDialog.isShowing()) {
                                        HueLifxSettingsFragment.this.promptPushLinkDialog.dismiss();
                                    }
                                    Toast.makeText(HueLifxSettingsFragment.this.getContext(), "Authentication Failed", 0).show();
                                    if (HueLifxSettingsFragment.this.currentBridgeEntry != null) {
                                        HueLifxSettingsFragment.this.currentBridgeEntry.setChecked(false);
                                        HueLifxSettingsFragment.this.currentBridgeEntry = null;
                                    }
                                }
                            });
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    Log.i(HueLifxSettingsFragment.tag, "unhandled connection event: " + connectionEvent);
                    return;
            }
        }

        public void onConnectionError(BridgeConnection bridgeConnection, List<HueError> list) {
            for (HueError error : list) {
                Log.e(HueLifxSettingsFragment.tag, "Connection error: " + error.toString());
            }
        }
    };
    /* access modifiers changed from: private */
    public BridgeDiscovery bridgeDiscovery;
    private Callback bridgeDiscoveryCallback = new Callback() {
        public void onFinished(final List<BridgeDiscoveryResult> results, ReturnCode returnCode) {
            Log.i(HueLifxSettingsFragment.tag, "bridgeDiscovery onFinished");
            HueLifxSettingsFragment.this.bridgeDiscovery = null;
            HueLifxSettingsFragment.this.handler.post(new Runnable() {
                public void run() {
                    int upper;
                    int lower;
                    final boolean bridgeSupportsEntertainment;
                    if (HueLifxSettingsFragment.this.discoverHueDialog != null && HueLifxSettingsFragment.this.discoverHueDialog.isShowing()) {
                        if (HueLifxSettingsFragment.this.promptPushLinkDialog == null || !HueLifxSettingsFragment.this.promptPushLinkDialog.isShowing()) {
                            HueLifxSettingsFragment.this.discoverHueDialog.dismiss();
                            for (final BridgeDiscoveryResult bridgeDiscoveryResult : results) {
                                String[] tokens = bridgeDiscoveryResult.getApiVersion().split("\\.");
                                try {
                                    upper = Integer.parseInt(tokens[0]);
                                    lower = Integer.parseInt(tokens[1]);
                                } catch (NumberFormatException e) {
                                    upper = -1;
                                    lower = -1;
                                }
                                if (upper < 1 || lower < 24) {
                                    bridgeSupportsEntertainment = false;
                                } else {
                                    bridgeSupportsEntertainment = true;
                                }
                                View bridgeEntry = HueLifxSettingsFragment.this.getActivity().getLayoutInflater().inflate(R.layout.hue_lifx_entry, null);
                                ((ImageView) bridgeEntry.findViewById(R.id.imageView)).setImageResource(R.drawable.hue_bridge_v2);
                                final CheckBox bridgeEntryCheckBox = (CheckBox) bridgeEntry.findViewById(R.id.checkBox);
                                bridgeEntryCheckBox.setTypeface(HueLifxSettingsFragment.this.font);
                                bridgeEntryCheckBox.setText("Bridge " + bridgeDiscoveryResult.getIp());
                                bridgeEntryCheckBox.setTag(bridgeDiscoveryResult);
                                bridgeEntryCheckBox.setOnClickListener(new OnClickListener() {
                                    public void onClick(View view) {
                                        if (!bridgeSupportsEntertainment) {
                                            HueLifxSettingsFragment.this.handler.post(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(HueLifxSettingsFragment.this.getContext(), "Please update the firmware of this bridge in order to continue.", 1).show();
                                                }
                                            });
                                        } else if (HueLifxSettingsFragment.this.currentBridgeEntry == bridgeEntryCheckBox) {
                                            HueLifxSettingsFragment.this.currentBridgeEntry.setChecked(true);
                                        } else {
                                            HueLifxSettingsFragment.this.currentBridgeEntry = bridgeEntryCheckBox;
                                            HueLifxSettingsFragment.this.bridge = new BridgeBuilder(HueLifxSettingsFragment.HUE_APP_NAME, HueLifxSettingsFragment.HUE_DEVICE_NAME).setIpAddress(bridgeDiscoveryResult.getIp()).setConnectionType(BridgeConnectionType.LOCAL).setBridgeConnectionCallback(HueLifxSettingsFragment.this.bridgeConnectionCallback).addBridgeStateUpdatedCallback(HueLifxSettingsFragment.this.bridgeStateUpdatedCallback).build();
                                            HueLifxSettingsFragment.this.bridge.connect();
                                            Log.i(HueLifxSettingsFragment.tag, "attempting to connect to bridge");
                                        }
                                    }
                                });
                                HueLifxSettingsFragment.this.bridgeLayout.addView(bridgeEntry);
                            }
                        }
                    }
                }
            });
        }
    };
    /* access modifiers changed from: private */
    public LinearLayout bridgeLayout;
    /* access modifiers changed from: private */
    public BridgeStateUpdatedCallback bridgeStateUpdatedCallback = new BridgeStateUpdatedCallback() {
        public void onBridgeStateUpdated(Bridge newBridge, BridgeStateUpdatedEvent bridgeStateUpdatedEvent) {
            Log.i(HueLifxSettingsFragment.tag, "Bridge state updated event: " + bridgeStateUpdatedEvent);
            switch (AnonymousClass33.$SwitchMap$com$philips$lighting$hue$sdk$wrapper$connection$BridgeStateUpdatedEvent[bridgeStateUpdatedEvent.ordinal()]) {
                case 1:
                    HueLifxSettingsFragment.this.bridge = newBridge;
                    Log.i(HueLifxSettingsFragment.tag, "bridgeIsConnected :" + HueLifxSettingsFragment.this.bridge.getName() + ":");
                    if (HueLifxSettingsFragment.this.promptPushLinkDialog != null && HueLifxSettingsFragment.this.promptPushLinkDialog.isShowing()) {
                        HueLifxSettingsFragment.this.promptPushLinkDialog.dismiss();
                    }
                    HueLifxSettingsFragment.this.generateHueUsernameClientKey();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public InetAddress broadcastIpAddress;
    private String broadcastIpString;
    private final ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{-16842912}, new int[]{16842912}}, new int[]{-12303292, -8355712});
    /* access modifiers changed from: private */
    public CheckBox currentBridgeEntry;
    /* access modifiers changed from: private */
    public TextView currentHelpText;
    /* access modifiers changed from: private */
    public View currentRadioButton = null;
    /* access modifiers changed from: private */
    public byte[] currentSectorAssignment = null;
    /* access modifiers changed from: private */
    public DatagramSocket datagramSocketListener;
    /* access modifiers changed from: private */
    public AlertDialog discoverHueDialog;
    /* access modifiers changed from: private */
    public AlertDialog discoverLifxDialog;
    /* access modifiers changed from: private */
    public Typeface font;
    /* access modifiers changed from: private */
    public LinearLayout groupLayout;
    private LinearLayout groupLayoutRoot;
    /* access modifiers changed from: private */
    public final Handler handler = new Handler();
    /* access modifiers changed from: private */
    public byte[] hueBridgeClientKey;
    /* access modifiers changed from: private */
    public String hueBridgeGroupName;
    /* access modifiers changed from: private */
    public int hueBridgeGroupNumber;
    /* access modifiers changed from: private */
    public String hueBridgeUsername;
    /* access modifiers changed from: private */
    public byte[] hueBulbIds;
    /* access modifiers changed from: private */
    public LinearLayout hueBulbLayout;
    private LinearLayout hueBulbLayoutRoot;
    private OnClickListener hueBulbTappedListener;
    /* access modifiers changed from: private */
    public TextView hueHelpText;
    private LinearLayout hueLayout;
    /* access modifiers changed from: private */
    public HueLifxBulb[] hueLifxBulbs = new HueLifxBulb[10];
    /* access modifiers changed from: private */
    public HueLifxSettingsFragmentListener hueLifxSettingsFragmentListener;
    private OnClickListener lifxBulbClickedListener;
    /* access modifiers changed from: private */
    public LinearLayout lifxBulbLayout;
    /* access modifiers changed from: private */
    public TextView lifxHelpText;
    private LinearLayout lifxLayout;
    /* access modifiers changed from: private */
    public InetAddress[] lightIpAddresses;
    /* access modifiers changed from: private */
    public String[] lightNames;
    /* access modifiers changed from: private */
    public byte[] lightSectorAssignments;
    /* access modifiers changed from: private */
    public int lightType;
    private int modeTextBlurredOut;
    private ImageView noLightsImageView;
    /* access modifiers changed from: private */
    public String phoneIpString;
    /* access modifiers changed from: private */
    public ProgressBar progressBar;
    /* access modifiers changed from: private */
    public AlertDialog promptPushLinkDialog;
    private OnClickListener radioButtonClickedListener;
    private LayoutParams radioButtonLP;
    private RadioGroup radioGroup;
    private LinearLayout searchLifxLayout;
    private int secondaryTextColor;
    private ImageView sector1;
    private ImageView sector10;
    private ImageView sector11;
    private ImageView sector12;
    private ImageView sector2;
    private ImageView sector3;
    private ImageView sector4;
    private ImageView sector5;
    private ImageView sector6;
    private ImageView sector7;
    private ImageView sector8;
    private ImageView sector9;
    /* access modifiers changed from: private */
    public TextView sectorHelpText;
    /* access modifiers changed from: private */
    public LinearLayout sectorLayout;
    /* access modifiers changed from: private */
    public int threadCount = 0;
    /* access modifiers changed from: private */
    public boolean udpListening = false;

    /* renamed from: com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment$33 reason: invalid class name */
    static /* synthetic */ class AnonymousClass33 {
        static final /* synthetic */ int[] $SwitchMap$com$philips$lighting$hue$sdk$wrapper$connection$BridgeStateUpdatedEvent = new int[BridgeStateUpdatedEvent.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$philips$lighting$hue$sdk$wrapper$connection$ConnectionEvent = new int[ConnectionEvent.values().length];

        static {
            try {
                $SwitchMap$com$philips$lighting$hue$sdk$wrapper$connection$BridgeStateUpdatedEvent[BridgeStateUpdatedEvent.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$philips$lighting$hue$sdk$wrapper$connection$ConnectionEvent[ConnectionEvent.NOT_AUTHENTICATED.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$philips$lighting$hue$sdk$wrapper$connection$ConnectionEvent[ConnectionEvent.LINK_BUTTON_NOT_PRESSED.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private class HueLifxBulb {
        public byte hueBulbId;
        public InetAddress ipAddress;
        public String name;

        public HueLifxBulb(String name2, InetAddress ipAddress2, byte hueBulbId2) {
            this.name = name2;
            this.ipAddress = ipAddress2;
            this.hueBulbId = hueBulbId2;
        }

        public HueLifxBulb() {
            reset();
        }

        public void reset() {
            this.name = "";
            try {
                this.ipAddress = InetAddress.getByName("0.0.0.0");
            } catch (UnknownHostException e) {
            }
            this.hueBulbId = 0;
        }
    }

    public interface HueLifxSettingsFragmentListener {
        String getBroadcastIpString();

        byte[] getHueBridgeClientKey();

        String getHueBridgeGroupName();

        int getHueBridgeGroupNumber();

        String getHueBridgeUsername();

        byte[] getHueBulbIds();

        InetAddress[] getLightIpAddresses();

        String[] getLightNames();

        byte[] getLightSectorAssignments();

        int getLightType();

        String getPhoneIpString();

        void setHueLifxSettings(int i, @Nullable InetAddress[] inetAddressArr, @Nullable byte[] bArr, @Nullable String str, @Nullable byte[] bArr2, @Nullable byte[] bArr3, int i2, @Nullable String[] strArr, @Nullable String str2);

        void setMode(int i);

        void setSectorData(byte[] bArr);
    }

    private class UDPBroadcast extends AsyncTask<byte[], Void, Void> {
        private UDPBroadcast() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            try {
                DatagramSocket s = new DatagramSocket();
                s.setBroadcast(true);
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, HueLifxSettingsFragment.this.broadcastIpAddress, HueLifxSettingsFragment.LIFX_PORT));
                s.close();
            } catch (Exception e) {
                Log.i(HueLifxSettingsFragment.tag, "sending error-" + e.toString());
            }
            return null;
        }
    }

    private class UDPUnicast extends AsyncTask<byte[], Void, Void> {
        private InetAddress unicastIpAddress;

        public UDPUnicast(InetAddress unicastIpAddress2) {
            this.unicastIpAddress = unicastIpAddress2;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            try {
                DatagramSocket s = new DatagramSocket();
                s.setBroadcast(true);
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, this.unicastIpAddress, HueLifxSettingsFragment.LIFX_PORT));
                s.close();
            } catch (Exception e) {
                Log.i(HueLifxSettingsFragment.tag, "sending error-" + e.toString());
            }
            return null;
        }
    }

    static {
        Log.i(tag, "load huesdk lib");
        System.loadLibrary("huesdk");
    }

    public static HueLifxSettingsFragment newInstance() {
        return new HueLifxSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.lightType = this.hueLifxSettingsFragmentListener.getLightType();
        this.lightIpAddresses = this.hueLifxSettingsFragmentListener.getLightIpAddresses();
        this.lightSectorAssignments = this.hueLifxSettingsFragmentListener.getLightSectorAssignments();
        this.hueBridgeUsername = this.hueLifxSettingsFragmentListener.getHueBridgeUsername();
        this.hueBulbIds = this.hueLifxSettingsFragmentListener.getHueBulbIds();
        this.hueBridgeClientKey = this.hueLifxSettingsFragmentListener.getHueBridgeClientKey();
        this.hueBridgeGroupNumber = this.hueLifxSettingsFragmentListener.getHueBridgeGroupNumber();
        this.hueBridgeGroupName = this.hueLifxSettingsFragmentListener.getHueBridgeGroupName();
        this.lightNames = this.hueLifxSettingsFragmentListener.getLightNames();
        this.phoneIpString = this.hueLifxSettingsFragmentListener.getPhoneIpString();
        this.broadcastIpString = this.hueLifxSettingsFragmentListener.getBroadcastIpString();
        try {
            this.broadcastIpAddress = InetAddress.getByName(this.broadcastIpString);
        } catch (UnknownHostException e) {
        }
        Log.i(tag, "broadcastIpAddress " + this.broadcastIpAddress.getHostAddress());
        this.secondaryTextColor = ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor);
        this.modeTextBlurredOut = ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut);
        for (byte b = 0; b < 10; b = (byte) (b + 1)) {
            this.hueLifxBulbs[b] = new HueLifxBulb();
        }
        InitSdk.setApplicationContext(getContext());
        Persistence.setStorageLocation(getContext().getFilesDir().getAbsolutePath(), "DreamScreen");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hue_lifx_settings, container, false);
        this.font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        ((TextView) view.findViewById(R.id.headerText)).setTypeface(this.font);
        this.noLightsImageView = (ImageView) view.findViewById(R.id.noLightsImageView);
        initHueLayout(view);
        initLifxLayout(view);
        initSectorLayout(view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, dropDownItems) {
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(HueLifxSettingsFragment.this.font);
                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView dropDownView = (TextView) super.getDropDownView(position, convertView, parent);
                dropDownView.setTypeface(HueLifxSettingsFragment.this.font);
                if (position == HueLifxSettingsFragment.this.lightType) {
                    dropDownView.setBackgroundColor(-8355712);
                } else {
                    dropDownView.setBackgroundColor(-11973551);
                }
                return dropDownView;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setDropDownVerticalOffset(pxToDp(42));
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.i(HueLifxSettingsFragment.tag, "onItemSelected " + position);
                if (HueLifxSettingsFragment.this.lightType != position) {
                    HueLifxSettingsFragment.this.lightType = position;
                    if (HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener != null) {
                        HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener.setHueLifxSettings(HueLifxSettingsFragment.this.lightType, null, null, null, null, null, -1, null, null);
                    }
                    HueLifxSettingsFragment.this.hueBridgeUsername = "";
                    for (byte b = 0; b < 16; b = (byte) (b + 1)) {
                        HueLifxSettingsFragment.this.hueBridgeClientKey[b] = 0;
                    }
                    HueLifxSettingsFragment.this.hueBridgeGroupNumber = 0;
                    HueLifxSettingsFragment.this.hueBridgeGroupName = "";
                    for (byte b2 = 0; b2 < 10; b2 = (byte) (b2 + 1)) {
                        HueLifxSettingsFragment.this.hueLifxBulbs[b2].reset();
                        HueLifxSettingsFragment.this.hueBulbIds[b2] = HueLifxSettingsFragment.this.hueLifxBulbs[b2].hueBulbId;
                        HueLifxSettingsFragment.this.lightIpAddresses[b2] = HueLifxSettingsFragment.this.hueLifxBulbs[b2].ipAddress;
                        HueLifxSettingsFragment.this.lightNames[b2] = HueLifxSettingsFragment.this.hueLifxBulbs[b2].name;
                        for (int i = 0; i < 15; i++) {
                            HueLifxSettingsFragment.this.lightSectorAssignments[(b2 * 15) + i] = Connect.DEFAULT_SECTOR_ASSIGNMENT[i];
                        }
                    }
                }
                if (HueLifxSettingsFragment.this.lightType == 0) {
                    HueLifxSettingsFragment.this.displayNoLights();
                } else if (HueLifxSettingsFragment.this.lightType == 1) {
                    HueLifxSettingsFragment.this.displayHueLayout();
                } else if (HueLifxSettingsFragment.this.lightType == 2) {
                    HueLifxSettingsFragment.this.displayLifxLayout();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner.setSelection(this.lightType);
        this.hueHelpText = (TextView) view.findViewById(R.id.hueHelpText);
        this.lifxHelpText = (TextView) view.findViewById(R.id.lifxHelpText);
        this.sectorHelpText = (TextView) view.findViewById(R.id.sectorHelpText);
        this.hueHelpText.setTypeface(this.font);
        this.lifxHelpText.setTypeface(this.font);
        this.sectorHelpText.setTypeface(this.font);
        view.findViewById(R.id.hueHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (HueLifxSettingsFragment.this.currentHelpText == HueLifxSettingsFragment.this.hueHelpText) {
                    HueLifxSettingsFragment.this.currentHelpText.setVisibility(8);
                    HueLifxSettingsFragment.this.currentHelpText = null;
                    return;
                }
                if (HueLifxSettingsFragment.this.currentHelpText != null) {
                    HueLifxSettingsFragment.this.currentHelpText.setVisibility(8);
                }
                HueLifxSettingsFragment.this.hueHelpText.setVisibility(0);
                HueLifxSettingsFragment.this.currentHelpText = HueLifxSettingsFragment.this.hueHelpText;
            }
        });
        view.findViewById(R.id.lifxHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (HueLifxSettingsFragment.this.currentHelpText == HueLifxSettingsFragment.this.lifxHelpText) {
                    HueLifxSettingsFragment.this.currentHelpText.setVisibility(8);
                    HueLifxSettingsFragment.this.currentHelpText = null;
                    return;
                }
                if (HueLifxSettingsFragment.this.currentHelpText != null) {
                    HueLifxSettingsFragment.this.currentHelpText.setVisibility(8);
                }
                HueLifxSettingsFragment.this.lifxHelpText.setVisibility(0);
                HueLifxSettingsFragment.this.currentHelpText = HueLifxSettingsFragment.this.lifxHelpText;
            }
        });
        view.findViewById(R.id.sectorHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (HueLifxSettingsFragment.this.currentHelpText == HueLifxSettingsFragment.this.sectorHelpText) {
                    HueLifxSettingsFragment.this.currentHelpText.setVisibility(8);
                    HueLifxSettingsFragment.this.currentHelpText = null;
                    return;
                }
                if (HueLifxSettingsFragment.this.currentHelpText != null) {
                    HueLifxSettingsFragment.this.currentHelpText.setVisibility(8);
                }
                HueLifxSettingsFragment.this.sectorHelpText.setVisibility(0);
                HueLifxSettingsFragment.this.currentHelpText = HueLifxSettingsFragment.this.sectorHelpText;
            }
        });
        return view;
    }

    /* access modifiers changed from: private */
    public void displayNoLights() {
        Log.i(tag, "displayNoLights");
        this.hueLayout.setVisibility(8);
        this.lifxLayout.setVisibility(8);
        this.sectorLayout.setVisibility(8);
        this.noLightsImageView.setVisibility(0);
    }

    private void initHueLayout(View view) {
        this.hueLayout = (LinearLayout) view.findViewById(R.id.hueLayout);
        this.bridgeLayout = (LinearLayout) view.findViewById(R.id.bridgeLayout);
        this.groupLayout = (LinearLayout) view.findViewById(R.id.hueGroupsLayout);
        this.hueBulbLayout = (LinearLayout) view.findViewById(R.id.hueBulbLayout);
        this.groupLayoutRoot = (LinearLayout) view.findViewById(R.id.hueGroupsLayoutRoot);
        this.hueBulbLayoutRoot = (LinearLayout) view.findViewById(R.id.hueBulbLayoutRoot);
        ((TextView) view.findViewById(R.id.hueText)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.bridgeText)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.hueGroupsText)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.hueBulbText)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.searchBridgesText)).setTypeface(this.font);
        view.findViewById(R.id.searchBridgesLayout).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.i(HueLifxSettingsFragment.tag, "searchBridgesLayout onClick");
                HueLifxSettingsFragment.this.searchHueBridges();
            }
        });
        this.hueBulbTappedListener = new OnClickListener() {
            public void onClick(View view) {
                Log.i(HueLifxSettingsFragment.tag, "hueBulbTappedListener");
                ((CheckBox) view).setChecked(true);
            }
        };
        View promptPushLinkView = getActivity().getLayoutInflater().inflate(R.layout.prompt_pushlink_dialog, null);
        ((TextView) promptPushLinkView.findViewById(R.id.subText)).setTypeface(this.font);
        this.progressBar = (ProgressBar) promptPushLinkView.findViewById(R.id.progressBar);
        this.progressBar.setMax(30);
        this.progressBar.setProgress(0);
        Builder alertDialogBuilder = new Builder(getActivity());
        alertDialogBuilder.setView(promptPushLinkView).setCancelable(false);
        this.promptPushLinkDialog = alertDialogBuilder.create();
    }

    /* access modifiers changed from: private */
    public void displayHueLayout() {
        Log.i(tag, "displayHueLayout");
        this.noLightsImageView.setVisibility(8);
        this.lifxLayout.setVisibility(8);
        this.hueLayout.setVisibility(0);
        this.currentBridgeEntry = null;
        this.bridgeLayout.removeAllViews();
        this.groupLayout.removeAllViews();
        this.hueBulbLayout.removeAllViews();
        int validHueBridgeIndex = -1;
        for (int b = 0; b < 10; b = (byte) (b + 1)) {
            if (!"0.0.0.0".equals(this.lightIpAddresses[b].getHostAddress())) {
                validHueBridgeIndex = b;
            }
        }
        int validHueBridgeIndexFinal = validHueBridgeIndex;
        if (validHueBridgeIndexFinal != -1) {
            View bridgeEntry = getActivity().getLayoutInflater().inflate(R.layout.hue_lifx_entry, null);
            ((ImageView) bridgeEntry.findViewById(R.id.imageView)).setImageResource(R.drawable.hue_bridge_v2);
            CheckBox bridgeEntryCheckBox = (CheckBox) bridgeEntry.findViewById(R.id.checkBox);
            bridgeEntryCheckBox.setTypeface(this.font);
            bridgeEntryCheckBox.setText("Bridge " + this.lightIpAddresses[validHueBridgeIndexFinal].getHostAddress());
            bridgeEntryCheckBox.setChecked(true);
            bridgeEntryCheckBox.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    HueLifxSettingsFragment.this.searchHueBridges();
                }
            });
            this.bridgeLayout.addView(bridgeEntry);
            if (this.hueBridgeGroupNumber != 0) {
                View groupEntry = getActivity().getLayoutInflater().inflate(R.layout.hue_lifx_entry, null);
                ((ImageView) groupEntry.findViewById(R.id.imageView)).setImageResource(R.drawable.hue_group);
                CheckBox groupCheckBox = (CheckBox) groupEntry.findViewById(R.id.checkBox);
                groupCheckBox.setTypeface(this.font);
                groupCheckBox.setText(this.hueBridgeGroupName);
                groupCheckBox.setChecked(true);
                groupCheckBox.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Log.i(HueLifxSettingsFragment.tag, "initial group tapped, but just invoking searchHueBridges instead");
                        HueLifxSettingsFragment.this.searchHueBridges();
                    }
                });
                this.groupLayout.addView(groupEntry);
                for (byte lightIndex = 0; lightIndex < 10; lightIndex = (byte) (lightIndex + 1)) {
                    if (this.hueBulbIds[lightIndex] != 0) {
                        HueLifxBulb hueBulb = new HueLifxBulb(this.lightNames[lightIndex], this.lightIpAddresses[lightIndex], this.hueBulbIds[lightIndex]);
                        this.hueLifxBulbs[lightIndex] = hueBulb;
                        View bulbEntry = getActivity().getLayoutInflater().inflate(R.layout.hue_lifx_entry, null);
                        ((ImageView) bulbEntry.findViewById(R.id.imageView)).setImageResource(R.drawable.hue_bulb);
                        CheckBox bulbEntryCheckBox = (CheckBox) bulbEntry.findViewById(R.id.checkBox);
                        bulbEntryCheckBox.setTypeface(this.font);
                        bulbEntryCheckBox.setText(hueBulb.name);
                        bulbEntryCheckBox.setChecked(true);
                        bulbEntryCheckBox.setOnClickListener(this.hueBulbTappedListener);
                        bulbEntryCheckBox.setTag(hueBulb);
                        this.hueBulbLayout.addView(bulbEntry);
                    }
                }
            }
            if (this.hueBulbLayout.getChildCount() > 0) {
                this.sectorLayout.setVisibility(0);
                updateRadioButtons();
                return;
            }
            this.sectorLayout.setVisibility(8);
            return;
        }
        this.groupLayoutRoot.setVisibility(8);
        this.hueBulbLayoutRoot.setVisibility(8);
        this.sectorLayout.setVisibility(8);
        searchHueBridges();
    }

    /* access modifiers changed from: private */
    public void searchHueBridges() {
        Log.i(tag, "searchHueBridges");
        if (this.discoverHueDialog == null || !this.discoverHueDialog.isShowing()) {
            if (this.discoverHueDialog == null) {
                View discoverHueView = getActivity().getLayoutInflater().inflate(R.layout.discover_hue_lifx_dialog, null);
                TextView subText = (TextView) discoverHueView.findViewById(R.id.subText);
                subText.setText(getString(R.string.DiscoveringHueBridges));
                subText.setTypeface(this.font);
                Builder alertDialogBuilder = new Builder(getActivity());
                alertDialogBuilder.setView(discoverHueView).setCancelable(false);
                this.discoverHueDialog = alertDialogBuilder.create();
            }
            this.discoverHueDialog.show();
            for (byte b = 0; b < 10; b = (byte) (b + 1)) {
                this.hueLifxBulbs[b].reset();
                this.lightIpAddresses[b] = this.hueLifxBulbs[b].ipAddress;
                this.lightNames[b] = this.hueLifxBulbs[b].name;
                this.hueBulbIds[b] = this.hueLifxBulbs[b].hueBulbId;
            }
            if (this.hueLifxSettingsFragmentListener != null) {
                this.hueLifxSettingsFragmentListener.setHueLifxSettings(-1, this.lightIpAddresses, null, null, this.hueBulbIds, null, -1, this.lightNames, null);
            }
            this.bridgeLayout.removeAllViews();
            this.groupLayout.removeAllViews();
            this.hueBulbLayout.removeAllViews();
            this.groupLayoutRoot.setVisibility(8);
            this.hueBulbLayoutRoot.setVisibility(8);
            this.sectorLayout.setVisibility(8);
            this.bridgeDiscovery = new BridgeDiscoveryImpl();
            this.bridgeDiscovery.search(Option.ALL, this.bridgeDiscoveryCallback);
            updateRadioButtons();
            return;
        }
        this.discoverHueDialog.dismiss();
    }

    /* access modifiers changed from: private */
    public void generateHueUsernameClientKey() {
        Log.i(tag, "generateHueUsernameClientKey");
        this.hueBridgeUsername = "";
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            this.hueBridgeClientKey[b] = 0;
        }
        if (this.bridge == null) {
            Log.i(tag, "generateHueUsernameClientKey but current bridge is null. canceling");
            return;
        }
        Log.i(tag, "userName " + this.bridge.getBridgeConnection(BridgeConnectionType.LOCAL).getConnectionOptions().getUserName());
        this.bridge.refreshUsername(new BridgeResponseCallback() {
            public void handleCallback(Bridge bridge, com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode returnCode, List<ClipResponse> list, List<HueError> list2) {
                Log.i(HueLifxSettingsFragment.tag, "refreshUsername handleCallback");
                for (ClipResponse clipResponse : list) {
                    if ("clientkey".equals(clipResponse.getResourceName())) {
                        HueLifxSettingsFragment.this.hueBridgeClientKey = HueLifxSettingsFragment.this.hexStringToByteArray(clipResponse.getStringValue());
                        HueLifxSettingsFragment.this.hueBridgeUsername = bridge.getBridgeConnection(BridgeConnectionType.LOCAL).getConnectionOptions().getUserName();
                        Log.i(HueLifxSettingsFragment.tag, "generateHueUsernameClientKey, onHTTPResponse, hueBridgeUsername :" + HueLifxSettingsFragment.this.hueBridgeUsername + ":  hueBridgeClientKey :" + clipResponse.getStringValue() + ":");
                        if (HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener != null) {
                            HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener.setHueLifxSettings(-1, null, null, HueLifxSettingsFragment.this.hueBridgeUsername, null, HueLifxSettingsFragment.this.hueBridgeClientKey, -1, null, null);
                        }
                        HueLifxSettingsFragment.this.handler.post(new Runnable() {
                            public void run() {
                                HueLifxSettingsFragment.this.currentBridgeEntry.setChecked(true);
                                HueLifxSettingsFragment.this.displayHueGroupsOfConnectedBridge();
                            }
                        });
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void displayHueGroupsOfConnectedBridge() {
        int groupNumber;
        Log.i(tag, "displayHueGroupsOfConnectedBridge");
        this.groupLayoutRoot.setVisibility(0);
        this.hueBulbLayoutRoot.setVisibility(8);
        this.groupLayout.removeAllViews();
        this.hueBulbLayout.removeAllViews();
        if (this.bridge == null) {
            Log.i(tag, "displayHueGroupsOfConnectedBridge but current bridge is null. start searchHueBridges");
            searchHueBridges();
            return;
        }
        for (final Group group : this.bridge.getBridgeState().getGroups()) {
            if (group.getGroupType() == GroupType.ENTERTAINMENT) {
                try {
                    groupNumber = Integer.parseInt(group.getIdentifier());
                } catch (NumberFormatException e) {
                    groupNumber = 0;
                }
                Log.i(tag, "displayHueGroupsOfConnectedBridge, " + group.getName() + ", group number " + groupNumber);
                View groupEntry = getActivity().getLayoutInflater().inflate(R.layout.hue_lifx_entry, null);
                ((ImageView) groupEntry.findViewById(R.id.imageView)).setImageResource(R.drawable.hue_group);
                CheckBox groupCheckBox = (CheckBox) groupEntry.findViewById(R.id.checkBox);
                groupCheckBox.setTypeface(this.font);
                groupCheckBox.setText(group.getName());
                groupCheckBox.setChecked(false);
                groupCheckBox.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        int newGroupNumber;
                        CheckBox groupCheckBox = (CheckBox) view;
                        try {
                            newGroupNumber = Integer.parseInt(group.getIdentifier());
                        } catch (NumberFormatException e) {
                            newGroupNumber = 0;
                        }
                        if (HueLifxSettingsFragment.this.hueBridgeGroupNumber != newGroupNumber || !HueLifxSettingsFragment.this.hueBulbLayout.isShown()) {
                            Log.i(HueLifxSettingsFragment.tag, "group tapped " + newGroupNumber);
                            for (byte b = 0; b < HueLifxSettingsFragment.this.groupLayout.getChildCount(); b = (byte) (b + 1)) {
                                CheckBox groupCheckBoxTemp = (CheckBox) HueLifxSettingsFragment.this.groupLayout.getChildAt(b).findViewById(R.id.checkBox);
                                if (groupCheckBoxTemp != groupCheckBox) {
                                    Log.i(HueLifxSettingsFragment.tag, "unchecking " + b);
                                    groupCheckBoxTemp.setChecked(false);
                                } else {
                                    Log.i(HueLifxSettingsFragment.tag, "not-unchecking " + b);
                                }
                            }
                            HueLifxSettingsFragment.this.hueBridgeGroupNumber = newGroupNumber;
                            HueLifxSettingsFragment.this.hueBridgeGroupName = group.getName().trim();
                            if (HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener != null) {
                                HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener.setMode(0);
                            }
                            HueLifxSettingsFragment.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    if (HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener != null) {
                                        HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener.setHueLifxSettings(-1, null, null, null, null, null, HueLifxSettingsFragment.this.hueBridgeGroupNumber, null, HueLifxSettingsFragment.this.hueBridgeGroupName);
                                    }
                                }
                            }, 2500);
                            HueLifxSettingsFragment.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    if (HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener != null) {
                                        HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener.setMode(3);
                                    }
                                }
                            }, 2600);
                            HueLifxSettingsFragment.this.displayHueBulbsForGroup(group);
                            return;
                        }
                        groupCheckBox.setChecked(true);
                    }
                });
                this.groupLayout.addView(groupEntry);
            }
        }
    }

    /* access modifiers changed from: private */
    public void displayHueBulbsForGroup(Group group) {
        byte tempBulbId;
        Log.i(tag, "displayHueBulbsForGroup, groupNumber " + this.hueBridgeGroupNumber);
        this.hueBulbLayoutRoot.setVisibility(0);
        this.hueBulbLayout.removeAllViews();
        if (this.bridge == null) {
            Log.i(tag, "displayHueBulbsForGroup but current bridge is null. start searchHueBridges");
            searchHueBridges();
            return;
        }
        for (byte b = 0; b < 10; b = (byte) (b + 1)) {
            this.hueLifxBulbs[b].reset();
            this.hueBulbIds[b] = this.hueLifxBulbs[b].hueBulbId;
            this.lightIpAddresses[b] = this.hueLifxBulbs[b].ipAddress;
            this.lightNames[b] = this.hueLifxBulbs[b].name;
            for (int i = 0; i < 15; i++) {
                this.lightSectorAssignments[(b * 15) + i] = Connect.DEFAULT_SECTOR_ASSIGNMENT[i];
            }
        }
        Log.i(tag, "displayHueBulbsForGroup, before, " + this.lightIpAddresses[0] + " " + this.lightIpAddresses[1] + " " + this.lightIpAddresses[2] + " " + this.lightIpAddresses[3]);
        InetAddress bridgeAddress = null;
        try {
            bridgeAddress = InetAddress.getByName(this.bridge.getBridgeConfiguration().getNetworkConfiguration().getIpAddress());
        } catch (UnknownHostException e) {
        }
        List<String> groupLightNames = group.getLightIds();
        Iterator it = this.bridge.getBridgeState().getLights().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            LightPoint light = (LightPoint) it.next();
            if (!groupLightNames.contains(light.getIdentifier())) {
                Log.i(tag, "light not part of group, ignore");
            } else {
                try {
                    tempBulbId = (byte) Integer.parseInt(light.getIdentifier());
                } catch (NumberFormatException e2) {
                    tempBulbId = 0;
                }
                Log.i(tag, "adding hue bulb, id " + tempBulbId);
                HueLifxBulb hueLifxBulb = new HueLifxBulb(light.getName(), bridgeAddress, tempBulbId);
                View bulbEntry = getActivity().getLayoutInflater().inflate(R.layout.hue_lifx_entry, null);
                ((ImageView) bulbEntry.findViewById(R.id.imageView)).setImageResource(R.drawable.hue_bulb);
                CheckBox bulbEntryCheckBox = (CheckBox) bulbEntry.findViewById(R.id.checkBox);
                bulbEntryCheckBox.setTypeface(this.font);
                bulbEntryCheckBox.setText(hueLifxBulb.name);
                bulbEntryCheckBox.setChecked(true);
                bulbEntryCheckBox.setOnClickListener(this.hueBulbTappedListener);
                bulbEntryCheckBox.setTag(hueLifxBulb);
                this.hueBulbLayout.addView(bulbEntry);
                Log.i(tag, "adding " + hueLifxBulb.ipAddress.getHostAddress());
                boolean added = false;
                byte b2 = 0;
                while (true) {
                    if (b2 >= 10) {
                        break;
                    } else if ("0.0.0.0".equals(this.hueLifxBulbs[b2].ipAddress.getHostAddress())) {
                        this.hueLifxBulbs[b2] = hueLifxBulb;
                        this.lightIpAddresses[b2] = this.hueLifxBulbs[b2].ipAddress;
                        this.lightNames[b2] = this.hueLifxBulbs[b2].name;
                        this.hueBulbIds[b2] = this.hueLifxBulbs[b2].hueBulbId;
                        added = true;
                        break;
                    } else {
                        b2 = (byte) (b2 + 1);
                    }
                }
                Log.i(tag, "added " + added);
                if (this.hueBulbLayout.getChildCount() >= 10) {
                    Log.i(tag, "Limiting bulb count to " + this.hueBulbLayout.getChildCount());
                    break;
                }
            }
        }
        Log.i(tag, "displayHueBulbsForGroup, after, " + this.lightIpAddresses[0] + " " + this.lightIpAddresses[1] + " " + this.lightIpAddresses[2] + " " + this.lightIpAddresses[3]);
        if (this.hueLifxSettingsFragmentListener != null) {
            this.hueLifxSettingsFragmentListener.setHueLifxSettings(-1, this.lightIpAddresses, this.lightSectorAssignments, null, this.hueBulbIds, null, -1, this.lightNames, null);
        }
        if (this.hueBulbLayout.getChildCount() > 0) {
            this.sectorLayout.setVisibility(0);
            updateRadioButtons();
            return;
        }
        this.sectorLayout.setVisibility(8);
    }

    private void initLifxLayout(View view) {
        this.lifxLayout = (LinearLayout) view.findViewById(R.id.lifxLayout);
        this.lifxBulbLayout = (LinearLayout) view.findViewById(R.id.lifxBulbLayout);
        this.searchLifxLayout = (LinearLayout) view.findViewById(R.id.searchLifxLayout);
        ((TextView) view.findViewById(R.id.lifxText)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.searchLifxText)).setTypeface(this.font);
        this.lifxBulbClickedListener = new OnClickListener() {
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                HueLifxBulb hueLifxBulb = (HueLifxBulb) checkBox.getTag();
                Log.i(HueLifxSettingsFragment.tag, "lifxBulbClickedListener, before, " + HueLifxSettingsFragment.this.lightIpAddresses[0] + " " + HueLifxSettingsFragment.this.lightIpAddresses[1] + " " + HueLifxSettingsFragment.this.lightIpAddresses[2] + " " + HueLifxSettingsFragment.this.lightIpAddresses[3]);
                if (checkBox.isChecked()) {
                    Log.i(HueLifxSettingsFragment.tag, "adding " + hueLifxBulb.ipAddress.getHostAddress());
                    boolean added = false;
                    byte b = 0;
                    while (true) {
                        if (b >= 10) {
                            break;
                        } else if ("0.0.0.0".equals(HueLifxSettingsFragment.this.hueLifxBulbs[b].ipAddress.getHostAddress())) {
                            HueLifxSettingsFragment.this.hueLifxBulbs[b] = hueLifxBulb;
                            HueLifxSettingsFragment.this.lightIpAddresses[b] = HueLifxSettingsFragment.this.hueLifxBulbs[b].ipAddress;
                            HueLifxSettingsFragment.this.lightNames[b] = HueLifxSettingsFragment.this.hueLifxBulbs[b].name;
                            added = true;
                            break;
                        } else {
                            b = (byte) (b + 1);
                        }
                    }
                    Log.i(HueLifxSettingsFragment.tag, "added " + added);
                    if (!added) {
                        checkBox.setChecked(false);
                    }
                } else {
                    Log.i(HueLifxSettingsFragment.tag, "attempting to remove " + hueLifxBulb.ipAddress.getHostAddress());
                    byte b2 = 0;
                    while (true) {
                        if (b2 >= 10) {
                            break;
                        } else if (HueLifxSettingsFragment.this.hueLifxBulbs[b2] == hueLifxBulb) {
                            Log.i(HueLifxSettingsFragment.tag, "resetting index " + b2);
                            HueLifxSettingsFragment.this.hueLifxBulbs[b2] = new HueLifxBulb();
                            HueLifxSettingsFragment.this.lightIpAddresses[b2] = HueLifxSettingsFragment.this.hueLifxBulbs[b2].ipAddress;
                            HueLifxSettingsFragment.this.lightNames[b2] = HueLifxSettingsFragment.this.hueLifxBulbs[b2].name;
                            for (int i = 0; i < 15; i++) {
                                HueLifxSettingsFragment.this.lightSectorAssignments[(b2 * 15) + i] = Connect.DEFAULT_SECTOR_ASSIGNMENT[i];
                            }
                        } else {
                            b2 = (byte) (b2 + 1);
                        }
                    }
                }
                Log.i(HueLifxSettingsFragment.tag, "lifxBulbClickedListener, after, " + HueLifxSettingsFragment.this.lightIpAddresses[0] + " " + HueLifxSettingsFragment.this.lightIpAddresses[1] + " " + HueLifxSettingsFragment.this.lightIpAddresses[2] + " " + HueLifxSettingsFragment.this.lightIpAddresses[3]);
                if (HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener != null) {
                    HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener.setHueLifxSettings(-1, HueLifxSettingsFragment.this.lightIpAddresses, HueLifxSettingsFragment.this.lightSectorAssignments, null, null, null, -1, HueLifxSettingsFragment.this.lightNames, null);
                }
                HueLifxSettingsFragment.this.updateRadioButtons();
            }
        };
        this.searchLifxLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.i(HueLifxSettingsFragment.tag, "startLifxSearchingListener onClick");
                if (HueLifxSettingsFragment.this.discoverLifxDialog == null || !HueLifxSettingsFragment.this.discoverLifxDialog.isShowing()) {
                    if (HueLifxSettingsFragment.this.discoverLifxDialog == null) {
                        View discoverLifxView = HueLifxSettingsFragment.this.getActivity().getLayoutInflater().inflate(R.layout.discover_hue_lifx_dialog, null);
                        TextView subText = (TextView) discoverLifxView.findViewById(R.id.subText);
                        subText.setText(HueLifxSettingsFragment.this.getString(R.string.DiscoveringLifxBulbs));
                        subText.setTypeface(HueLifxSettingsFragment.this.font);
                        Builder alertDialogBuilder = new Builder(HueLifxSettingsFragment.this.getActivity());
                        alertDialogBuilder.setView(discoverLifxView).setCancelable(false);
                        HueLifxSettingsFragment.this.discoverLifxDialog = alertDialogBuilder.create();
                    }
                    HueLifxSettingsFragment.this.discoverLifxDialog.show();
                    for (byte b = 0; b < 10; b = (byte) (b + 1)) {
                        HueLifxSettingsFragment.this.hueLifxBulbs[b].reset();
                        HueLifxSettingsFragment.this.lightIpAddresses[b] = HueLifxSettingsFragment.this.hueLifxBulbs[b].ipAddress;
                        HueLifxSettingsFragment.this.lightNames[b] = HueLifxSettingsFragment.this.hueLifxBulbs[b].name;
                    }
                    if (HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener != null) {
                        HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener.setHueLifxSettings(-1, HueLifxSettingsFragment.this.lightIpAddresses, null, null, null, null, -1, HueLifxSettingsFragment.this.lightNames, null);
                    }
                    HueLifxSettingsFragment.this.lifxBulbLayout.removeAllViews();
                    HueLifxSettingsFragment.this.sectorLayout.setVisibility(8);
                    HueLifxSettingsFragment.this.startBackgroundUdpListener();
                    HueLifxSettingsFragment.this.discoverAllLifxGetVersion();
                    HueLifxSettingsFragment.this.updateRadioButtons();
                    return;
                }
                HueLifxSettingsFragment.this.discoverLifxDialog.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void displayLifxLayout() {
        Log.i(tag, "displayLifxLayout");
        this.noLightsImageView.setVisibility(8);
        this.hueLayout.setVisibility(8);
        this.lifxLayout.setVisibility(0);
        this.lifxBulbLayout.removeAllViews();
        for (byte lightIndex = 0; lightIndex < 10; lightIndex = (byte) (lightIndex + 1)) {
            if (this.lightIpAddresses[lightIndex] != null && !"0.0.0.0".equals(this.lightIpAddresses[lightIndex].getHostAddress())) {
                HueLifxBulb lifxBulb = new HueLifxBulb(this.lightNames[lightIndex], this.lightIpAddresses[lightIndex], 0);
                this.hueLifxBulbs[lightIndex] = lifxBulb;
                View bulbEntry = getActivity().getLayoutInflater().inflate(R.layout.hue_lifx_entry, null);
                ((ImageView) bulbEntry.findViewById(R.id.imageView)).setImageResource(R.drawable.lifx_bulb);
                CheckBox bulbEntryCheckBox = (CheckBox) bulbEntry.findViewById(R.id.checkBox);
                bulbEntryCheckBox.setTypeface(this.font);
                bulbEntryCheckBox.setText(lifxBulb.name);
                bulbEntryCheckBox.setChecked(true);
                bulbEntryCheckBox.setOnClickListener(this.lifxBulbClickedListener);
                bulbEntryCheckBox.setTag(lifxBulb);
                this.lifxBulbLayout.addView(bulbEntry);
            }
        }
        if (this.lifxBulbLayout.getChildCount() > 0) {
            this.sectorLayout.setVisibility(0);
            updateRadioButtons();
            return;
        }
        this.sectorLayout.setVisibility(8);
        this.searchLifxLayout.callOnClick();
    }

    private void initSectorLayout(View view) {
        this.sectorLayout = (LinearLayout) view.findViewById(R.id.sectorLayout);
        this.radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        ((TextView) view.findViewById(R.id.sectorText)).setTypeface(this.font);
        this.radioButtonLP = new LayoutParams(-2, -2);
        this.radioButtonClickedListener = new OnClickListener() {
            public void onClick(View radioButton) {
                if (HueLifxSettingsFragment.this.currentRadioButton != radioButton) {
                    HueLifxSettingsFragment.this.currentRadioButton = radioButton;
                    byte b = 0;
                    while (true) {
                        if (b >= 10) {
                            break;
                        } else if (HueLifxSettingsFragment.this.hueLifxBulbs[b] == radioButton.getTag()) {
                            HueLifxSettingsFragment.this.currentSectorAssignment = Arrays.copyOfRange(HueLifxSettingsFragment.this.lightSectorAssignments, b * 15, (b * 15) + 15);
                            break;
                        } else {
                            b = (byte) (b + 1);
                        }
                    }
                    HueLifxSettingsFragment.this.updateSectorImage();
                }
            }
        };
        this.sector1 = (ImageView) view.findViewById(R.id.sector1);
        this.sector2 = (ImageView) view.findViewById(R.id.sector2);
        this.sector3 = (ImageView) view.findViewById(R.id.sector3);
        this.sector4 = (ImageView) view.findViewById(R.id.sector4);
        this.sector5 = (ImageView) view.findViewById(R.id.sector5);
        this.sector6 = (ImageView) view.findViewById(R.id.sector6);
        this.sector7 = (ImageView) view.findViewById(R.id.sector7);
        this.sector8 = (ImageView) view.findViewById(R.id.sector8);
        this.sector9 = (ImageView) view.findViewById(R.id.sector9);
        this.sector10 = (ImageView) view.findViewById(R.id.sector10);
        this.sector11 = (ImageView) view.findViewById(R.id.sector11);
        this.sector12 = (ImageView) view.findViewById(R.id.sector12);
        this.sector1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(1);
            }
        });
        this.sector2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(2);
            }
        });
        this.sector3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(3);
            }
        });
        this.sector4.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(4);
            }
        });
        this.sector5.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(5);
            }
        });
        this.sector6.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(6);
            }
        });
        this.sector7.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(7);
            }
        });
        this.sector8.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(8);
            }
        });
        this.sector9.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(9);
            }
        });
        this.sector10.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(10);
            }
        });
        this.sector11.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(11);
            }
        });
        this.sector12.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HueLifxSettingsFragment.this.sectorOnClick(12);
            }
        });
        this.currentSectorAssignment = null;
        updateRadioButtons();
    }

    /* access modifiers changed from: private */
    public void updateRadioButtons() {
        this.radioGroup.removeAllViews();
        AppCompatRadioButton appCompatRadioButton = null;
        for (byte b = 0; b < 10; b = (byte) (b + 1)) {
            if (!"0.0.0.0".equals(this.hueLifxBulbs[b].ipAddress.getHostAddress())) {
                AppCompatRadioButton radioButton = new AppCompatRadioButton(getContext());
                radioButton.setLayoutParams(this.radioButtonLP);
                radioButton.setTextSize(15.0f);
                radioButton.setTextColor(this.secondaryTextColor);
                radioButton.setText(this.hueLifxBulbs[b].name);
                radioButton.setTypeface(this.font);
                CompoundButtonCompat.setButtonTintList(radioButton, this.colorStateList);
                radioButton.setTag(this.hueLifxBulbs[b]);
                radioButton.setOnClickListener(this.radioButtonClickedListener);
                this.radioGroup.addView(radioButton);
                if (appCompatRadioButton == null) {
                    appCompatRadioButton = radioButton;
                }
            }
        }
        if (appCompatRadioButton != null) {
            appCompatRadioButton.performClick();
            return;
        }
        this.currentSectorAssignment = null;
        updateSectorImage();
    }

    /* access modifiers changed from: private */
    public void sectorOnClick(int sectorNumber) {
        byte[] bArr;
        Log.i(tag, "sectorOnClick " + sectorNumber);
        if (this.currentSectorAssignment == null) {
            Log.i(tag, "sectorOnClick but currentSectorAssignment is null, canceling");
            return;
        }
        boolean sectorRemoved = false;
        ByteArrayOutputStream newArray = new ByteArrayOutputStream();
        for (byte b : this.currentSectorAssignment) {
            if (b == 0) {
                break;
            }
            if (b != ((byte) sectorNumber)) {
                newArray.write(b);
            } else {
                sectorRemoved = true;
            }
        }
        if (!sectorRemoved) {
            newArray.write((byte) sectorNumber);
        }
        byte b2 = 0;
        while (true) {
            if (b2 >= 10) {
                break;
            } else if (this.hueLifxBulbs[b2] == this.currentRadioButton.getTag()) {
                byte[] newSectorAssignment = Arrays.copyOf(newArray.toByteArray(), 15);
                for (int i = 0; i < 15; i++) {
                    this.lightSectorAssignments[(b2 * 15) + i] = newSectorAssignment[i];
                }
                if (this.hueLifxSettingsFragmentListener != null) {
                    this.hueLifxSettingsFragmentListener.setHueLifxSettings(-1, null, this.lightSectorAssignments, null, null, null, -1, null, null);
                }
                this.currentSectorAssignment = newSectorAssignment;
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        if (HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener != null) {
                            HueLifxSettingsFragment.this.hueLifxSettingsFragmentListener.setSectorData(HueLifxSettingsFragment.sectorData);
                        }
                    }
                }, 50);
            } else {
                b2 = (byte) (b2 + 1);
            }
        }
        updateSectorImage();
    }

    /* access modifiers changed from: private */
    public void updateSectorImage() {
        this.sector1.setImageResource(R.drawable.sector_unchecked_background);
        this.sector2.setImageResource(R.drawable.sector_unchecked_background);
        this.sector3.setImageResource(R.drawable.sector_unchecked_background);
        this.sector4.setImageResource(R.drawable.sector_unchecked_background);
        this.sector5.setImageResource(R.drawable.sector_unchecked_background);
        this.sector6.setImageResource(R.drawable.sector_unchecked_background);
        this.sector7.setImageResource(R.drawable.sector_unchecked_background);
        this.sector8.setImageResource(R.drawable.sector_unchecked_background);
        this.sector9.setImageResource(R.drawable.sector_unchecked_background);
        this.sector10.setImageResource(R.drawable.sector_unchecked_background);
        this.sector11.setImageResource(R.drawable.sector_unchecked_background);
        this.sector12.setImageResource(R.drawable.sector_unchecked_background);
        this.sector1.setColorFilter(this.modeTextBlurredOut);
        this.sector2.setColorFilter(this.modeTextBlurredOut);
        this.sector3.setColorFilter(this.modeTextBlurredOut);
        this.sector4.setColorFilter(this.modeTextBlurredOut);
        this.sector5.setColorFilter(this.modeTextBlurredOut);
        this.sector6.setColorFilter(this.modeTextBlurredOut);
        this.sector7.setColorFilter(this.modeTextBlurredOut);
        this.sector8.setColorFilter(this.modeTextBlurredOut);
        this.sector9.setColorFilter(this.modeTextBlurredOut);
        this.sector10.setColorFilter(this.modeTextBlurredOut);
        this.sector11.setColorFilter(this.modeTextBlurredOut);
        this.sector12.setColorFilter(this.modeTextBlurredOut);
        if (this.currentSectorAssignment != null) {
            for (byte b : this.currentSectorAssignment) {
                switch (b) {
                    case 1:
                        this.sector1.setImageResource(R.drawable.sector_checked_background);
                        this.sector1.setColorFilter(null);
                        break;
                    case 2:
                        this.sector2.setImageResource(R.drawable.sector_checked_background);
                        this.sector2.setColorFilter(null);
                        break;
                    case 3:
                        this.sector3.setImageResource(R.drawable.sector_checked_background);
                        this.sector3.setColorFilter(null);
                        break;
                    case 4:
                        this.sector4.setImageResource(R.drawable.sector_checked_background);
                        this.sector4.setColorFilter(null);
                        break;
                    case 5:
                        this.sector5.setImageResource(R.drawable.sector_checked_background);
                        this.sector5.setColorFilter(null);
                        break;
                    case 6:
                        this.sector6.setImageResource(R.drawable.sector_checked_background);
                        this.sector6.setColorFilter(null);
                        break;
                    case 7:
                        this.sector7.setImageResource(R.drawable.sector_checked_background);
                        this.sector7.setColorFilter(null);
                        break;
                    case 8:
                        this.sector8.setImageResource(R.drawable.sector_checked_background);
                        this.sector8.setColorFilter(null);
                        break;
                    case 9:
                        this.sector9.setImageResource(R.drawable.sector_checked_background);
                        this.sector9.setColorFilter(null);
                        break;
                    case 10:
                        this.sector10.setImageResource(R.drawable.sector_checked_background);
                        this.sector10.setColorFilter(null);
                        break;
                    case 11:
                        this.sector11.setImageResource(R.drawable.sector_checked_background);
                        this.sector11.setColorFilter(null);
                        break;
                    case 12:
                        this.sector12.setImageResource(R.drawable.sector_checked_background);
                        this.sector12.setColorFilter(null);
                        break;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[(len / 2)];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /* access modifiers changed from: private */
    public void discoverAllLifxGetVersion() {
        new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (i < 3) {
                    try {
                        new UDPBroadcast().execute(new byte[][]{HueLifxSettingsFragment.getVersionCommand});
                        Log.i(HueLifxSettingsFragment.tag, "sent discovery, getVersion, udp broadcast");
                        Thread.sleep(1000);
                        i++;
                    } catch (Exception e) {
                        Log.i(HueLifxSettingsFragment.tag, "send discovery failed :" + e.toString());
                    }
                }
                HueLifxSettingsFragment.this.handler.post(new Runnable() {
                    public void run() {
                        HueLifxSettingsFragment.this.finishedSearchingForLifx();
                    }
                });
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void finishedSearchingForLifx() {
        Log.i(tag, "finishedSearchingForLifx");
        stopUdpListening();
        if (this.discoverLifxDialog != null && this.discoverLifxDialog.isShowing()) {
            this.discoverLifxDialog.dismiss();
        }
        if (this.hueLifxSettingsFragmentListener != null) {
            this.hueLifxSettingsFragmentListener.setHueLifxSettings(-1, null, null, null, null, null, -1, null, null);
        }
        if (this.lifxBulbLayout.getChildCount() > 0) {
            this.sectorLayout.setVisibility(0);
            updateRadioButtons();
            return;
        }
        this.sectorLayout.setVisibility(8);
    }

    private void stopUdpListening() {
        this.udpListening = false;
        if (this.datagramSocketListener != null) {
            this.datagramSocketListener.close();
        }
        this.datagramSocketListener = null;
    }

    /* access modifiers changed from: private */
    public void startBackgroundUdpListener() {
        if (!this.udpListening) {
            new Thread(new Runnable() {
                public void run() {
                    
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0004: INVOKE  (wrap: com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment
                          0x0002: IGET  (r6v0 com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment) = (r11v0 'this' com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment$32 A[THIS]) com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.32.this$0 com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment) com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.access$5108(com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment):int type: STATIC in method: com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.32.run():void, dex: classes.dex
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
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:625)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:353)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                        	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:88)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:682)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
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
                        	... 50 more
                        Caused by: java.lang.ClassNotFoundException: sun.reflect.ReflectionFactory
                        	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(Unknown Source)
                        	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(Unknown Source)
                        	at java.base/java.lang.ClassLoader.loadClass(Unknown Source)
                        	at java.base/java.lang.Class.forName0(Native Method)
                        	at java.base/java.lang.Class.forName(Unknown Source)
                        	at org.objenesis.instantiator.sun.SunReflectionFactoryHelper.getReflectionFactoryClass(SunReflectionFactoryHelper.java:54)
                        	... 68 more
                        */
                    /*
                        this = this;
                        r10 = 1
                        r9 = 0
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this
                        
                        // error: 0x0004: INVOKE  (r6 I:com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment) com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.access$5108(com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment):int type: STATIC
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this
                        int r5 = r6.threadCount
                        java.lang.String r6 = "HueLifxSettingsFrag"
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder
                        r7.<init>()
                        java.lang.String r8 = "new listening thread started : "
                        java.lang.StringBuilder r7 = r7.append(r8)
                        java.lang.StringBuilder r7 = r7.append(r5)
                        java.lang.String r7 = r7.toString()
                        android.util.Log.i(r6, r7)
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this
                        r6.udpListening = r10
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        java.net.DatagramSocket r7 = new java.net.DatagramSocket     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r8 = 0     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r7.<init>(r8)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r6.datagramSocketListener = r7     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        java.net.DatagramSocket r6 = r6.datagramSocketListener     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r7 = 1     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r6.setReuseAddress(r7)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        java.net.DatagramSocket r6 = r6.datagramSocketListener     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        java.net.InetSocketAddress r7 = new java.net.InetSocketAddress     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r8 = 56700(0xdd7c, float:7.9454E-41)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r7.<init>(r8)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r6.bind(r7)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                    L_0x0050:
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        boolean r6 = r6.udpListening     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        if (r6 == 0) goto L_0x0073     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r6 = 256(0x100, float:3.59E-43)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        byte[] r2 = new byte[r6]     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        java.net.DatagramPacket r0 = new java.net.DatagramPacket     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        int r6 = r2.length     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r0.<init>(r2, r6)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        java.net.DatagramSocket r6 = r6.datagramSocketListener     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r6.receive(r0)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        boolean r6 = r6.udpListening     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        if (r6 != 0) goto L_0x0098     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                    L_0x0073:
                        java.lang.String r6 = "HueLifxSettingsFrag"     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        java.lang.String r7 = "out of udpListening while loop"     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        android.util.Log.i(r6, r7)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this
                        r6.udpListening = r9
                        java.lang.String r6 = "HueLifxSettingsFrag"
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder
                        r7.<init>()
                        java.lang.String r8 = "UDPListener stopped : "
                        java.lang.StringBuilder r7 = r7.append(r8)
                        java.lang.StringBuilder r7 = r7.append(r5)
                        java.lang.String r7 = r7.toString()
                        android.util.Log.i(r6, r7)
                    L_0x0097:
                        return
                    L_0x0098:
                        java.net.InetAddress r4 = r0.getAddress()     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        java.lang.String r6 = r4.getHostAddress()     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r7 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        java.lang.String r7 = r7.phoneIpString     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        boolean r6 = r6.equals(r7)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        if (r6 != 0) goto L_0x0050     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        int r6 = r0.getLength()     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        byte[] r3 = java.util.Arrays.copyOf(r2, r6)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        android.os.Handler r6 = r6.handler     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment$32$1 r7 = new com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment$32$1     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r7.<init>(r3, r4)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        r6.post(r7)     // Catch:{ SocketException -> 0x00c3, Exception -> 0x00fe }
                        goto L_0x0050
                    L_0x00c3:
                        r1 = move-exception
                        java.lang.String r6 = "HueLifxSettingsFrag"
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x013a }
                        r7.<init>()     // Catch:{ all -> 0x013a }
                        java.lang.String r8 = "SocketException-"     // Catch:{ all -> 0x013a }
                        java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x013a }
                        java.lang.String r8 = r1.toString()     // Catch:{ all -> 0x013a }
                        java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x013a }
                        java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x013a }
                        android.util.Log.i(r6, r7)     // Catch:{ all -> 0x013a }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this
                        r6.udpListening = r9
                        java.lang.String r6 = "HueLifxSettingsFrag"
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder
                        r7.<init>()
                        java.lang.String r8 = "UDPListener stopped : "
                        java.lang.StringBuilder r7 = r7.append(r8)
                        java.lang.StringBuilder r7 = r7.append(r5)
                        java.lang.String r7 = r7.toString()
                        android.util.Log.i(r6, r7)
                        goto L_0x0097
                    L_0x00fe:
                        r1 = move-exception
                        java.lang.String r6 = "HueLifxSettingsFrag"
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x013a }
                        r7.<init>()     // Catch:{ all -> 0x013a }
                        java.lang.String r8 = "UDPListener receiver error-"     // Catch:{ all -> 0x013a }
                        java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x013a }
                        java.lang.String r8 = r1.toString()     // Catch:{ all -> 0x013a }
                        java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x013a }
                        java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x013a }
                        android.util.Log.i(r6, r7)     // Catch:{ all -> 0x013a }
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r6 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this
                        r6.udpListening = r9
                        java.lang.String r6 = "HueLifxSettingsFrag"
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder
                        r7.<init>()
                        java.lang.String r8 = "UDPListener stopped : "
                        java.lang.StringBuilder r7 = r7.append(r8)
                        java.lang.StringBuilder r7 = r7.append(r5)
                        java.lang.String r7 = r7.toString()
                        android.util.Log.i(r6, r7)
                        goto L_0x0097
                    L_0x013a:
                        r6 = move-exception
                        com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment r7 = com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.this
                        r7.udpListening = r9
                        java.lang.String r7 = "HueLifxSettingsFrag"
                        java.lang.StringBuilder r8 = new java.lang.StringBuilder
                        r8.<init>()
                        java.lang.String r9 = "UDPListener stopped : "
                        java.lang.StringBuilder r8 = r8.append(r9)
                        java.lang.StringBuilder r8 = r8.append(r5)
                        java.lang.String r8 = r8.toString()
                        android.util.Log.i(r7, r8)
                        throw r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.AnonymousClass32.run():void");
                }
            }).start();
        }
    }

    /* access modifiers changed from: private */
    public void handleReceivedPacket(byte[] received, InetAddress senderIp) {
        Log.i(tag, "handleReceivedPacket " + senderIp.getHostAddress());
        for (byte b = 0; b < 10; b = (byte) (b + 1)) {
            if (this.hueLifxBulbs[b].ipAddress.equals(senderIp)) {
                Log.i(tag, "bulb already added, canceling");
                return;
            }
        }
        int command = (received[32] & 255) + ((received[33] & 255) << 8);
        if (command == 33) {
            if (received.length != 48) {
                Log.i(tag, "received invalid StateVersion length " + received.length);
                return;
            }
            if (!Connect.VALID_LIFX_PRODUCTIDS.contains(Integer.valueOf((received[40] & 255) + ((received[41] & 255) << 8) + ((received[42] & 255) << 16) + ((received[43] & 255) << 24)))) {
                Log.i(tag, "received an invalid lifx productId, canceling");
                return;
            }
            Log.i(tag, "Received valid StateVersion, unicasting back GetLabel");
            new UDPUnicast(senderIp).execute(new byte[][]{getLabelCommand});
        } else if (command != 25) {
            Log.i(tag, "received unknown LIFX message");
        } else if (received.length != 68) {
            Log.i(tag, "received invalid StateLabel length " + received.length);
        } else {
            String lifxLabel = "";
            try {
                lifxLabel = new String(Arrays.copyOfRange(received, 36, 68), "UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
            Log.i(tag, "lifxLabel: " + lifxLabel);
            HueLifxBulb newBulb = new HueLifxBulb(lifxLabel, senderIp, 0);
            View bulbEntry = getActivity().getLayoutInflater().inflate(R.layout.hue_lifx_entry, null);
            ((ImageView) bulbEntry.findViewById(R.id.imageView)).setImageResource(R.drawable.lifx_bulb);
            CheckBox bulbEntryCheckBox = (CheckBox) bulbEntry.findViewById(R.id.checkBox);
            bulbEntryCheckBox.setTypeface(this.font);
            bulbEntryCheckBox.setText(newBulb.name);
            bulbEntryCheckBox.setTag(newBulb);
            bulbEntryCheckBox.setOnClickListener(this.lifxBulbClickedListener);
            bulbEntryCheckBox.setChecked(true);
            Log.i(tag, "discovered, adding " + newBulb.ipAddress.getHostAddress());
            boolean added = false;
            byte b2 = 0;
            while (true) {
                if (b2 >= 10) {
                    break;
                } else if ("0.0.0.0".equals(this.hueLifxBulbs[b2].ipAddress.getHostAddress())) {
                    this.hueLifxBulbs[b2] = newBulb;
                    this.lightIpAddresses[b2] = this.hueLifxBulbs[b2].ipAddress;
                    this.lightNames[b2] = this.hueLifxBulbs[b2].name;
                    added = true;
                    break;
                } else {
                    b2 = (byte) (b2 + 1);
                }
            }
            if (!added) {
                Log.i(tag, "light not able to be added, canceling");
            } else {
                this.lifxBulbLayout.addView(bulbEntry);
            }
        }
    }

    private int pxToDp(int px) {
        return (int) TypedValue.applyDimension(1, (float) px, getResources().getDisplayMetrics());
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HueLifxSettingsFragmentListener) {
            this.hueLifxSettingsFragmentListener = (HueLifxSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement HueLifxSettingsFragmentListener");
    }

    public void onDetach() {
        if (this.bridge != null) {
            this.bridge.disconnect();
            this.bridge = null;
        }
        stopUdpListening();
        super.onDetach();
        this.hueLifxSettingsFragmentListener = null;
    }
}

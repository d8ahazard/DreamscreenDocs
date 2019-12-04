package com.lab714.dreamscreenv2.layout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.lab714.dreamscreenv2.R;
import com.lab714.dreamscreenv2.devices.Connect;
import com.lab714.dreamscreenv2.devices.Light;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NewDeviceFragment extends Fragment {
    private static final int CONNECT_TO_WIFI_TIMEOUT = 30000;
    private static final int ESP_RESPONSE_TIMEOUT = 40000;
    private static final int dreamScreenPort = 8888;
    private static final int permissionRequestCode = 33333;
    private static final String tag = "NewDeviceFrag";
    /* access modifiers changed from: private */
    public InetAddress apUnicastIP;
    /* access modifiers changed from: private */
    public boolean canceling = false;
    /* access modifiers changed from: private */
    public RelativeLayout confirmationLayout;
    /* access modifiers changed from: private */
    public Runnable connectToWifiTimeoutRunnable;
    /* access modifiers changed from: private */
    public boolean connectedToDreamScreen = false;
    /* access modifiers changed from: private */
    public boolean connectingBack = false;
    /* access modifiers changed from: private */
    public TextView connectingText;
    /* access modifiers changed from: private */
    public ConnectivityManager connectivityManager;
    /* access modifiers changed from: private */
    public RelativeLayout espFailedLayout;
    /* access modifiers changed from: private */
    public Runnable espFirstResponseTimeoutRunnable;
    /* access modifiers changed from: private */
    public Runnable espSecondResponseTimeoutRunnable;
    /* access modifiers changed from: private */
    public boolean firstScanResultsArrived = false;
    /* access modifiers changed from: private */
    public Typeface font;
    private boolean gpsReceiverRegistered = false;
    private final BroadcastReceiver gpsSwitchStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                Log.i(NewDeviceFragment.tag, "gpsSwitchStateReceiver PROVIDERS_CHANGED, calling onStart");
                NewDeviceFragment.this.onStart();
            }
        }
    };
    /* access modifiers changed from: private */
    public Handler handler;
    /* access modifiers changed from: private */
    public AlertDialog locationDialog;
    private LocationManager locationManager;
    /* access modifiers changed from: private */
    public NewDeviceFragmentListener newDeviceFragmentListener;
    /* access modifiers changed from: private */
    public LinkedList<String> newDreamScreens;
    /* access modifiers changed from: private */
    public LinkedList<String> newDreamScreensOriginal;
    /* access modifiers changed from: private */
    public AlertDialog permissionsDialog;
    /* access modifiers changed from: private */
    public RelativeLayout pickDreamScreens;
    /* access modifiers changed from: private */
    public ScrollView pickWiFi;
    /* access modifiers changed from: private */
    public int previousNetworkId = -1;
    /* access modifiers changed from: private */
    public boolean requestPermissionsShown = false;
    /* access modifiers changed from: private */
    public TextView responseText;
    private List<ScanResult> scanResults;
    private boolean scanningInProgress = false;
    /* access modifiers changed from: private */
    public RelativeLayout searchingLayout;
    /* access modifiers changed from: private */
    public boolean secondScanArrived = true;
    /* access modifiers changed from: private */
    public TextView sendingText;
    private boolean splashStarted = false;
    /* access modifiers changed from: private */
    public boolean startingOver = false;
    /* access modifiers changed from: private */
    public String userPassword = "";
    byte[] userPasswordBytes;
    /* access modifiers changed from: private */
    public String userSSID = "";
    byte[] userSSIDBytes;
    /* access modifiers changed from: private */
    public View view;
    private boolean waitingForRestrictions = false;
    /* access modifiers changed from: private */
    public WifiManager wifiManager;
    private WifiReceiver wifiReceiver = null;

    private class AccessPointUDPUnicast extends AsyncTask<byte[], Void, Void> {
        private AccessPointUDPUnicast() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            int i = 0;
            try {
                Log.i(NewDeviceFragment.tag, "sending AP unicast to " + NewDeviceFragment.this.apUnicastIP.getHostAddress() + " port " + NewDeviceFragment.dreamScreenPort);
                DatagramSocket s = new DatagramSocket();
                if (VERSION.SDK_INT >= 22) {
                    Network[] allNetworks = NewDeviceFragment.this.connectivityManager.getAllNetworks();
                    int length = allNetworks.length;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        Network network = allNetworks[i];
                        if (NewDeviceFragment.this.connectivityManager.getNetworkInfo(network).getType() == 1) {
                            network.bindSocket(s);
                            break;
                        }
                        i++;
                    }
                }
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, NewDeviceFragment.this.apUnicastIP, NewDeviceFragment.dreamScreenPort));
                s.close();
            } catch (Exception e) {
                Log.i(NewDeviceFragment.tag, "sending AP unicast error-" + e.toString());
            }
            return null;
        }
    }

    public interface NewDeviceFragmentListener {
        void disableBackButton();

        void enableBackButton();

        void startNewDeviceFragment();

        void startSplashActivity();
    }

    class WifiReceiver extends BroadcastReceiver {
        WifiReceiver() {
        }

        public void onReceive(Context c, Intent intent) {
            String action = intent.getAction();
            if (!NewDeviceFragment.this.firstScanResultsArrived && action.equals("android.net.wifi.SCAN_RESULTS")) {
                Log.i(NewDeviceFragment.tag, "first SCAN_RESULTS_AVAILABLE_ACTION");
                NewDeviceFragment.this.firstScanResultsArrived = true;
                NewDeviceFragment.this.handleScanResults();
            } else if (!NewDeviceFragment.this.secondScanArrived && action.equals("android.net.wifi.SCAN_RESULTS")) {
                Log.i(NewDeviceFragment.tag, "second SCAN_RESULTS_AVAILABLE_ACTION");
                NewDeviceFragment.this.secondScanArrived = true;
                NewDeviceFragment.this.handleSecondScanResults();
            } else if (action.equals("android.net.wifi.STATE_CHANGE")) {
                Log.i(NewDeviceFragment.tag, "NETWORK_STATE_CHANGED_ACTION");
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                Log.i(NewDeviceFragment.tag, "networkinfo getstate :" + networkInfo.getState().toString());
                WifiInfo wifiInfo = NewDeviceFragment.this.wifiManager.getConnectionInfo();
                if (wifiInfo.getNetworkId() != -1) {
                    final String ssid = wifiInfo.getSSID();
                    Log.i(NewDeviceFragment.tag, "connected to :" + ssid + ":");
                    if ((!NewDeviceFragment.this.firstScanResultsArrived || !ssid.contains("DreamScreen")) && !ssid.contains("SideKick") && !ssid.contains(Connect.DEFAULT_NAME)) {
                        if (NewDeviceFragment.this.connectingBack && wifiInfo.getNetworkId() == NewDeviceFragment.this.previousNetworkId) {
                            Log.i(NewDeviceFragment.tag, "wifiReceiver connected to a different network, remove connectToWifiTimeoutRunnable");
                            NewDeviceFragment.this.handler.removeCallbacks(NewDeviceFragment.this.connectToWifiTimeoutRunnable);
                            NewDeviceFragment.this.connectingBack = false;
                            NewDeviceFragment.this.startSplashActivity();
                        } else if (NewDeviceFragment.this.startingOver && wifiInfo.getNetworkId() == NewDeviceFragment.this.previousNetworkId) {
                            Log.i(NewDeviceFragment.tag, "wifiReceiver connected to a different network, remove connectToWifiTimeoutRunnable");
                            NewDeviceFragment.this.handler.removeCallbacks(NewDeviceFragment.this.connectToWifiTimeoutRunnable);
                            NewDeviceFragment.this.startingOver = false;
                            if (NewDeviceFragment.this.newDeviceFragmentListener != null) {
                                NewDeviceFragment.this.newDeviceFragmentListener.startNewDeviceFragment();
                            }
                        } else if (!NewDeviceFragment.this.connectedToDreamScreen || ssid.contains("DreamScreen") || ssid.contains("SideKick") || ssid.contains(Connect.DEFAULT_NAME)) {
                            Log.i(NewDeviceFragment.tag, "wifiReceiver connected to a network, not handled");
                        } else {
                            Log.i(NewDeviceFragment.tag, "connectedToDreamScreen but current ssid says otherwise. connectToPreviousNetworkAndStartSplash");
                            NewDeviceFragment.this.connectToPreviousNetworkAndStartSplash();
                        }
                    } else if (!NewDeviceFragment.this.connectedToDreamScreen) {
                        NewDeviceFragment.this.connectedToDreamScreen = true;
                        Log.i(NewDeviceFragment.tag, "wifiReceiver connected to a different network, remove connectToWifiTimeoutRunnable");
                        NewDeviceFragment.this.handler.removeCallbacks(NewDeviceFragment.this.connectToWifiTimeoutRunnable);
                        NewDeviceFragment.this.handler.postDelayed(new Runnable() {
                            public void run() {
                                NewDeviceFragment.this.dreamScreenConnected(ssid);
                            }
                        }, 2000);
                    }
                } else if (NewDeviceFragment.this.connectedToDreamScreen && networkInfo.getState() == State.DISCONNECTED) {
                    Log.i(NewDeviceFragment.tag, "was connected to DS but AP disconnected, connectToNextDreamscreen?");
                    NewDeviceFragment.this.connectToNextDreamScreen();
                }
            }
        }
    }

    public static NewDeviceFragment newInstance() {
        return new NewDeviceFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.view != null) {
            return this.view;
        }
        this.view = inflater.inflate(R.layout.fragment_newdevice, container, false);
        this.searchingLayout = (RelativeLayout) this.view.findViewById(R.id.searchingLayout);
        this.pickDreamScreens = (RelativeLayout) this.view.findViewById(R.id.pickDreamScreens);
        this.pickWiFi = (ScrollView) this.view.findViewById(R.id.pickWiFi);
        this.confirmationLayout = (RelativeLayout) this.view.findViewById(R.id.confirmationLayout);
        this.espFailedLayout = (RelativeLayout) this.view.findViewById(R.id.espFailedLayout);
        this.connectingText = (TextView) this.view.findViewById(R.id.connectingText);
        this.sendingText = (TextView) this.view.findViewById(R.id.sendingText);
        this.responseText = (TextView) this.view.findViewById(R.id.responseText);
        this.font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.connectingText.setTypeface(this.font);
        this.sendingText.setTypeface(this.font);
        this.responseText.setTypeface(this.font);
        this.locationManager = (LocationManager) getActivity().getSystemService("location");
        this.wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService("wifi");
        try {
            this.apUnicastIP = InetAddress.getByName("192.168.4.1");
        } catch (Exception e) {
            Log.i(tag, e.toString());
        }
        start();
        return this.view;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(tag, "onRequestPermissionsResult");
        this.requestPermissionsShown = false;
        if (requestCode == permissionRequestCode) {
            onStart();
        }
    }

    public void onStart() {
        super.onStart();
        Log.i(tag, "onStart");
        if (this.waitingForRestrictions && !this.requestPermissionsShown) {
            Log.i(tag, "onStart waitingForRestrictions, calling start()");
            if (this.permissionsDialog != null && this.permissionsDialog.isShowing()) {
                this.permissionsDialog.dismiss();
            }
            if (this.locationDialog != null && this.locationDialog.isShowing()) {
                this.locationDialog.dismiss();
            }
            start();
        }
    }

    private void start() {
        Log.i(tag, "start");
        this.pickDreamScreens.setVisibility(8);
        this.pickWiFi.setVisibility(8);
        this.confirmationLayout.setVisibility(8);
        this.espFailedLayout.setVisibility(8);
        this.searchingLayout.setVisibility(0);
        this.connectingText.setText("");
        this.sendingText.setText("");
        this.responseText.setText("");
        this.userSSID = "";
        this.userPassword = "";
        this.handler = new Handler();
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            Log.i(tag, "ACCESS_COARSE_LOCATION not granted");
            this.waitingForRestrictions = true;
            View locationPermissionDialog = getActivity().getLayoutInflater().inflate(R.layout.location_permission_dialog, null);
            TextView continueText = (TextView) locationPermissionDialog.findViewById(R.id.continueText);
            TextView continueTextButton = (TextView) locationPermissionDialog.findViewById(R.id.continueTextButton);
            Builder builder = new Builder(getActivity());
            builder.setView(locationPermissionDialog).setCancelable(false);
            this.permissionsDialog = builder.create();
            this.permissionsDialog.show();
            Log.i(tag, "permissionsDialog.show()");
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.ACCESS_COARSE_LOCATION")) {
                continueText.setText(getContext().getResources().getString(R.string.locationSettings1));
                continueTextButton.setText(getContext().getResources().getString(R.string.locationSettings2));
                continueTextButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        NewDeviceFragment.this.permissionsDialog.dismiss();
                        if (NewDeviceFragment.this.getActivity() == null) {
                            Log.i(NewDeviceFragment.tag, "Open App Settings, location permission, getActivity null. return?");
                            return;
                        }
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", NewDeviceFragment.this.getActivity().getPackageName(), null));
                        intent.addFlags(268435456);
                        NewDeviceFragment.this.startActivity(intent);
                    }
                });
                return;
            }
            continueText.setText(getContext().getResources().getString(R.string.locationPermissions1));
            continueTextButton.setText(getContext().getResources().getString(R.string.locationPermissions2));
            continueTextButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NewDeviceFragment.this.permissionsDialog.dismiss();
                    if (NewDeviceFragment.this.newDeviceFragmentListener == null) {
                        Log.i(NewDeviceFragment.tag, "special case, cancelling");
                        return;
                    }
                    Log.i(NewDeviceFragment.tag, "ACCESS_COARSE_LOCATION start request");
                    NewDeviceFragment.this.requestPermissionsShown = true;
                    NewDeviceFragment.this.requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, NewDeviceFragment.permissionRequestCode);
                }
            });
        } else if (VERSION.SDK_INT < 23 || this.locationManager.isProviderEnabled("gps") || this.locationManager.isProviderEnabled("network")) {
            Log.i(tag, "ACCESS_COARSE_LOCATION granted, and gps enabled if >=6.0");
            this.waitingForRestrictions = false;
            if (this.gpsSwitchStateReceiver != null && this.gpsReceiverRegistered) {
                this.gpsReceiverRegistered = false;
                getActivity().unregisterReceiver(this.gpsSwitchStateReceiver);
            }
            String ssid = this.wifiManager.getConnectionInfo().getSSID();
            if (!this.connectedToDreamScreen || ssid.contains("DreamScreen") || ssid.contains("SideKick") || ssid.contains(Connect.DEFAULT_NAME)) {
                this.connectivityManager = (ConnectivityManager) getActivity().getSystemService("connectivity");
                if (VERSION.SDK_INT >= 23) {
                    this.connectivityManager.bindProcessToNetwork(null);
                }
                startScanning();
                return;
            }
            Log.i(tag, "start no longer connected to a dreamscreen");
            startSplashActivity();
        } else {
            Log.i(tag, ">6.0, gps is off");
            this.waitingForRestrictions = true;
            View locationPermissionDialog2 = getActivity().getLayoutInflater().inflate(R.layout.enable_gps_dialog, null);
            TextView continueGpsTextButton = (TextView) locationPermissionDialog2.findViewById(R.id.continueGpsTextButton);
            Builder builder2 = new Builder(getActivity());
            builder2.setView(locationPermissionDialog2).setCancelable(false);
            this.locationDialog = builder2.create();
            this.locationDialog.show();
            continueGpsTextButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NewDeviceFragment.this.locationDialog.dismiss();
                    Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                    intent.addFlags(268435456);
                    if (NewDeviceFragment.this.isAdded() && intent.resolveActivity(NewDeviceFragment.this.getContext().getPackageManager()) == null) {
                        Log.i(NewDeviceFragment.tag, "ACTION_LOCATION_SOURCE_SETTINGS cannot be handled, just opening settings");
                        intent = new Intent("android.settings.SETTINGS");
                        intent.addFlags(268435456);
                    }
                    NewDeviceFragment.this.startActivity(intent);
                }
            });
            getActivity().registerReceiver(this.gpsSwitchStateReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
            this.gpsReceiverRegistered = true;
        }
    }

    private void startScanning() {
        if (this.wifiManager != null && !this.scanningInProgress) {
            this.previousNetworkId = this.wifiManager.getConnectionInfo().getNetworkId();
            this.connectingText.setText(getContext().getResources().getString(R.string.connectingText1));
            this.sendingText.setText("");
            this.responseText.setText("");
            this.scanningInProgress = true;
            Log.i(tag, "startScanning");
            this.wifiReceiver = new WifiReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            intentFilter.addAction("android.net.wifi.STATE_CHANGE");
            getActivity().registerReceiver(this.wifiReceiver, intentFilter);
            this.wifiManager.startScan();
        }
    }

    /* access modifiers changed from: private */
    public void handleScanResults() {
        Log.i(tag, "handleScanResults");
        this.scanResults = this.wifiManager.getScanResults();
        this.newDreamScreens = new LinkedList<>();
        Iterator it = this.scanResults.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ScanResult scanResult = (ScanResult) it.next();
            if ((scanResult.SSID.contains("DreamScreen") || scanResult.SSID.contains("SideKick") || scanResult.SSID.contains(Connect.DEFAULT_NAME)) && !this.newDreamScreens.contains(scanResult.SSID)) {
                this.newDreamScreens.add(scanResult.SSID);
                if (this.newDreamScreens.size() >= 6) {
                    Log.i(tag, "stopping number of newDreamScreens at 6");
                    break;
                }
            }
        }
        if (this.newDreamScreens.isEmpty()) {
            Log.i(tag, "no dreamscreens found");
            this.handler.post(new Runnable() {
                public void run() {
                    NewDeviceFragment.this.connectingText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.connectingText2));
                    NewDeviceFragment.this.sendingText.setText("");
                    NewDeviceFragment.this.responseText.setText("");
                }
            });
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    NewDeviceFragment.this.connectToPreviousNetworkAndStartSplash();
                }
            }, 2000);
            return;
        }
        Log.i(tag, "dreamscreens found");
        pickDevices();
    }

    private void pickDevices() {
        this.pickDreamScreens.setVisibility(8);
        this.pickWiFi.setVisibility(8);
        this.confirmationLayout.setVisibility(8);
        this.searchingLayout.setVisibility(8);
        this.pickDreamScreens.setVisibility(0);
        LinearLayout deviceList = (LinearLayout) this.view.findViewById(R.id.deviceList);
        deviceList.removeAllViews();
        LayoutParams lp = new LayoutParams(-2, -2);
        Iterator it = this.newDreamScreens.iterator();
        while (it.hasNext()) {
            final String dreamScreen = (String) it.next();
            final CheckBox checkBox = (CheckBox) getActivity().getLayoutInflater().inflate(R.layout.newdevice_checkbox, null);
            checkBox.setLayoutParams(lp);
            checkBox.setTypeface(this.font);
            checkBox.setText(dreamScreen);
            checkBox.setChecked(true);
            checkBox.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        Log.i(NewDeviceFragment.tag, "adding " + dreamScreen + " to newDreamScreens");
                        if (!NewDeviceFragment.this.newDreamScreens.contains(dreamScreen)) {
                            NewDeviceFragment.this.newDreamScreens.add(dreamScreen);
                            return;
                        }
                        return;
                    }
                    Log.i(NewDeviceFragment.tag, "removing " + dreamScreen + " from newDreamScreens");
                    NewDeviceFragment.this.newDreamScreens.remove(dreamScreen);
                }
            });
            deviceList.addView(checkBox);
        }
        TextView firstContinue = (TextView) this.view.findViewById(R.id.firstContinue);
        firstContinue.setTypeface(this.font);
        firstContinue.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (NewDeviceFragment.this.canceling) {
                    Log.i(NewDeviceFragment.tag, "user is canceling, return");
                } else if (NewDeviceFragment.this.newDreamScreens.isEmpty()) {
                    Log.i(NewDeviceFragment.tag, "user hasnt selected any new dreamscreens, dont do anything");
                } else {
                    NewDeviceFragment.this.newDreamScreensOriginal = new LinkedList();
                    Iterator it = NewDeviceFragment.this.newDreamScreens.iterator();
                    while (it.hasNext()) {
                        NewDeviceFragment.this.newDreamScreensOriginal.add((String) it.next());
                    }
                    NewDeviceFragment.this.getUsersInfo();
                }
            }
        });
        final TextView firstCancel = (TextView) this.view.findViewById(R.id.firstCancel);
        firstCancel.setTypeface(this.font);
        firstCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NewDeviceFragment.this.canceling = true;
                firstCancel.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.Canceling));
                NewDeviceFragment.this.connectToPreviousNetworkAndStartSplash();
            }
        });
        ((TextView) this.view.findViewById(R.id.pickHeader)).setTypeface(this.font);
        ((TextView) this.view.findViewById(R.id.pickSubHeader)).setTypeface(this.font);
    }

    /* access modifiers changed from: private */
    public void getUsersInfo() {
        this.pickDreamScreens.setVisibility(8);
        this.espFailedLayout.setVisibility(8);
        this.searchingLayout.setVisibility(8);
        this.confirmationLayout.setVisibility(8);
        this.pickWiFi.setVisibility(0);
        final ArrayList<String> ssidList = new ArrayList<>();
        if (this.scanResults != null && !this.scanResults.isEmpty()) {
            for (ScanResult scanResult : this.scanResults) {
                if (!scanResult.SSID.contains("DreamScreen") && !scanResult.SSID.contains("SideKick") && !scanResult.SSID.contains(Connect.DEFAULT_NAME) && !ssidList.contains(scanResult.SSID) && scanResult.frequency < 4000 && !scanResult.SSID.isEmpty()) {
                    ssidList.add(scanResult.SSID);
                }
            }
        }
        final Spinner ssidSpinner = (Spinner) this.view.findViewById(R.id.ssidSpinner);
        ssidSpinner.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor), Mode.SRC_ATOP);
        final ArrayAdapter<String> ssidListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.newdevice_spinneritem, ssidList) {
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(NewDeviceFragment.this.font);
                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView dropDownView = (TextView) super.getDropDownView(position, convertView, parent);
                dropDownView.setTypeface(NewDeviceFragment.this.font);
                if (position == ssidSpinner.getSelectedItemPosition()) {
                    dropDownView.setBackgroundColor(-8355712);
                } else {
                    dropDownView.setBackgroundColor(-11513776);
                }
                return dropDownView;
            }
        };
        ssidListAdapter.setDropDownViewResource(R.layout.newdevice_spinneritem_dropdown);
        ssidSpinner.setAdapter(ssidListAdapter);
        ssidSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSSID = (String) ssidSpinner.getSelectedItem();
                if (selectedSSID != null && selectedSSID.length() > 31) {
                    Toast.makeText(NewDeviceFragment.this.getContext(), "WARNING: This SSID is too long", 1).show();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        int index = ssidList.indexOf(this.wifiManager.getConnectionInfo().getSSID());
        if (index != -1) {
            ssidSpinner.setSelection(index);
        }
        this.view.findViewById(R.id.addNetworkLayout).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.i(NewDeviceFragment.tag, "add a network tapped, opening alertdialog");
                View addNetworkDialogView = NewDeviceFragment.this.getActivity().getLayoutInflater().inflate(R.layout.add_network_dialog, null);
                final EditText networkEditText = (EditText) addNetworkDialogView.findViewById(R.id.networkEditText);
                TextView addText = (TextView) addNetworkDialogView.findViewById(R.id.addText);
                TextView cancelText = (TextView) addNetworkDialogView.findViewById(R.id.cancelText);
                networkEditText.setTypeface(NewDeviceFragment.this.font);
                addText.setTypeface(NewDeviceFragment.this.font);
                cancelText.setTypeface(NewDeviceFragment.this.font);
                Builder alertDialogBuilder = new Builder(NewDeviceFragment.this.getActivity());
                alertDialogBuilder.setView(addNetworkDialogView).setCancelable(false);
                final AlertDialog addNetworkDialog = alertDialogBuilder.create();
                addNetworkDialog.show();
                networkEditText.setOnEditorActionListener(new OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Log.i(NewDeviceFragment.tag, "onEditorAction");
                        switch (actionId) {
                            case 0:
                            case 5:
                            case 6:
                                ((InputMethodManager) NewDeviceFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(networkEditText.getWindowToken(), 0);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                networkEditText.addTextChangedListener(new TextWatcher() {
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
                            ((InputMethodManager) NewDeviceFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(networkEditText.getWindowToken(), 0);
                        }
                    }
                });
                addText.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        String newNetwork = networkEditText.getText().toString();
                        if (!newNetwork.isEmpty()) {
                            ((InputMethodManager) NewDeviceFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(networkEditText.getWindowToken(), 0);
                            ssidList.add(0, newNetwork);
                            ssidListAdapter.notifyDataSetChanged();
                            ssidSpinner.setSelection(0);
                            addNetworkDialog.dismiss();
                        }
                    }
                });
                cancelText.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ((InputMethodManager) NewDeviceFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(networkEditText.getWindowToken(), 0);
                        addNetworkDialog.dismiss();
                    }
                });
            }
        });
        ((TextView) this.view.findViewById(R.id.addNetworkText)).setTypeface(this.font);
        final EditText password = (EditText) this.view.findViewById(R.id.password);
        password.setText("");
        password.setTypeface(this.font);
        password.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) NewDeviceFragment.this.getActivity().getSystemService("input_method");
                if (hasFocus) {
                    inputMethodManager.showSoftInput(password, 2);
                    password.setSelection(password.getText().length());
                    return;
                }
                inputMethodManager.hideSoftInputFromWindow(NewDeviceFragment.this.pickWiFi.getWindowToken(), 0);
            }
        });
        ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.pickWiFi.getWindowToken(), 0);
        TextView secondContinue = (TextView) this.view.findViewById(R.id.secondContinue);
        secondContinue.setTypeface(this.font);
        secondContinue.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.i(NewDeviceFragment.tag, "secondContinue");
                ((InputMethodManager) NewDeviceFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(NewDeviceFragment.this.pickWiFi.getWindowToken(), 0);
                if (NewDeviceFragment.this.canceling) {
                    Log.i(NewDeviceFragment.tag, "user is canceling, return");
                    return;
                }
                NewDeviceFragment.this.userSSID = (String) ssidSpinner.getSelectedItem();
                if (NewDeviceFragment.this.userSSID != null) {
                    NewDeviceFragment.this.userPassword = password.getText().toString();
                    if (NewDeviceFragment.this.userSSID.isEmpty()) {
                        Toast.makeText(NewDeviceFragment.this.getContext(), "Invalid SSID", 0).show();
                    } else if (NewDeviceFragment.this.userPassword.length() <= 0 || NewDeviceFragment.this.userPassword.length() >= 8) {
                        Log.i(NewDeviceFragment.tag, "userSSID entered :" + NewDeviceFragment.this.userSSID + ":");
                        Log.i(NewDeviceFragment.tag, "userPassword entered :" + NewDeviceFragment.this.userPassword + ":");
                        NewDeviceFragment.this.pickDreamScreens.setVisibility(8);
                        NewDeviceFragment.this.pickWiFi.setVisibility(8);
                        NewDeviceFragment.this.confirmationLayout.setVisibility(8);
                        NewDeviceFragment.this.espFailedLayout.setVisibility(8);
                        NewDeviceFragment.this.searchingLayout.setVisibility(0);
                        if (NewDeviceFragment.this.newDeviceFragmentListener != null) {
                            NewDeviceFragment.this.newDeviceFragmentListener.disableBackButton();
                        }
                        NewDeviceFragment.this.connectToDreamScreen((String) NewDeviceFragment.this.newDreamScreens.getFirst());
                    } else {
                        Toast.makeText(NewDeviceFragment.this.getContext(), "Invalid Password Length", 0).show();
                    }
                }
            }
        });
        TextView secondCancel = (TextView) this.view.findViewById(R.id.secondCancel);
        secondCancel.setTypeface(this.font);
        secondCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.i(NewDeviceFragment.tag, "secondCancel, going back to pick dreamscreens, not canceling");
                ((InputMethodManager) NewDeviceFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(NewDeviceFragment.this.pickWiFi.getWindowToken(), 0);
                NewDeviceFragment.this.pickDreamScreens.setVisibility(8);
                NewDeviceFragment.this.pickWiFi.setVisibility(8);
                NewDeviceFragment.this.confirmationLayout.setVisibility(8);
                NewDeviceFragment.this.searchingLayout.setVisibility(8);
                NewDeviceFragment.this.pickDreamScreens.setVisibility(0);
            }
        });
        ((TextView) this.view.findViewById(R.id.pickSubHeader2)).setTypeface(this.font);
        ((TextView) this.view.findViewById(R.id.pickSubHeader3)).setTypeface(this.font);
        ((RelativeLayout) this.view.findViewById(R.id.relativeLayout)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((InputMethodManager) NewDeviceFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(NewDeviceFragment.this.pickWiFi.getWindowToken(), 0);
            }
        });
    }

    /* access modifiers changed from: private */
    public void connectToDreamScreen(final String ssid) {
        Log.i(tag, "connectToDreamScreen");
        String quotedSSID = "\"" + ssid + "\"";
        if (quotedSSID.equals(this.wifiManager.getConnectionInfo().getSSID())) {
            Log.i(tag, "remove connectToWifiTimeoutRunnable");
            this.handler.removeCallbacks(this.connectToWifiTimeoutRunnable);
            this.connectedToDreamScreen = true;
            dreamScreenConnected(quotedSSID);
            return;
        }
        this.connectedToDreamScreen = false;
        this.handler.post(new Runnable() {
            public void run() {
                NewDeviceFragment.this.connectingText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.connectingText3) + ssid);
                NewDeviceFragment.this.sendingText.setText("");
                NewDeviceFragment.this.responseText.setText("");
            }
        });
        Log.i(tag, "wifi enabled :" + this.wifiManager.isWifiEnabled() + "   pinged :" + this.wifiManager.pingSupplicant());
        int networkId = -1;
        List<WifiConfiguration> networks = this.wifiManager.getConfiguredNetworks();
        if (networks != null) {
            try {
                Iterator it = networks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
                    if (ssid.equals(wifiConfiguration.SSID.substring(1, wifiConfiguration.SSID.length() - 1))) {
                        networkId = wifiConfiguration.networkId;
                        break;
                    }
                    this.wifiManager.disableNetwork(wifiConfiguration.networkId);
                }
            } catch (NullPointerException e) {
                Log.i(tag, "npe during search");
            }
        } else {
            Log.e(tag, "wifiManager#getConfiguredNetworks is null");
        }
        if (networkId == -1) {
            Log.i(tag, "adding network");
            WifiConfiguration wifiConfiguration2 = new WifiConfiguration();
            wifiConfiguration2.SSID = "\"" + ssid + "\"";
            wifiConfiguration2.allowedKeyManagement.set(0);
            networkId = this.wifiManager.addNetwork(wifiConfiguration2);
            this.wifiManager.saveConfiguration();
        }
        Log.i(tag, "after editting, networkId " + networkId);
        this.handler.postDelayed(new Runnable() {
            public void run() {
                int networkId2 = -1;
                List<WifiConfiguration> networks = NewDeviceFragment.this.wifiManager.getConfiguredNetworks();
                if (networks != null) {
                    Iterator it = networks.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
                        if (ssid.equals(wifiConfiguration.SSID.substring(1, wifiConfiguration.SSID.length() - 1))) {
                            networkId2 = wifiConfiguration.networkId;
                            break;
                        }
                    }
                } else {
                    Log.e(NewDeviceFragment.tag, "wifiManager#getConfiguredNetworks is null");
                }
                Log.i(NewDeviceFragment.tag, "connecting to :" + ssid + ": id :" + networkId2);
                Log.i(NewDeviceFragment.tag, "enable network :" + NewDeviceFragment.this.wifiManager.enableNetwork(networkId2, true) + ": waiting to connect to dreamscreen");
                NewDeviceFragment.this.connectToWifiTimeoutRunnable = new Runnable() {
                    public void run() {
                        Log.i(NewDeviceFragment.tag, "connectToWifiTimeoutRunnable, enable network has timed out. connectToPreviousNetworkAndStartSplash");
                        NewDeviceFragment.this.connectToPreviousNetworkAndStartSplash();
                    }
                };
                NewDeviceFragment.this.handler.postDelayed(NewDeviceFragment.this.connectToWifiTimeoutRunnable, 30000);
            }
        }, 1000);
    }

    /* access modifiers changed from: private */
    public void dreamScreenConnected(String ssid) {
        if (!ssid.equals(this.wifiManager.getConnectionInfo().getSSID())) {
            Log.i(tag, "dreamscreenConnected() but current network says otherwise. connectToPreviousNetworkAndStartSplash");
            connectToPreviousNetworkAndStartSplash();
        } else if (!this.userSSID.isEmpty()) {
            final String s = ssid.substring(1, ssid.length() - 1);
            this.handler.post(new Runnable() {
                public void run() {
                    String newText;
                    try {
                        newText = NewDeviceFragment.this.getContext().getResources().getString(R.string.connectingText4) + s;
                    } catch (NullPointerException e) {
                        newText = "Connected to " + s;
                    }
                    NewDeviceFragment.this.connectingText.setText(newText);
                    NewDeviceFragment.this.sendingText.setText("");
                    NewDeviceFragment.this.responseText.setText("");
                }
            });
            boolean b = this.newDreamScreens.remove(s);
            Log.i(tag, "newDreamScreens.remove() " + s + "     " + b);
            if (!b) {
                Log.i(tag, "connected to DS when shouldnt be. connectToPreviousNetworkAndStartSplash");
                connectToPreviousNetworkAndStartSplash();
                return;
            }
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    Log.i(NewDeviceFragment.tag, "sending userSSID " + NewDeviceFragment.this.userSSID);
                    Log.i(NewDeviceFragment.tag, "sending userPassword " + NewDeviceFragment.this.userPassword);
                    NewDeviceFragment.this.sendingText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.sendingText1));
                    NewDeviceFragment.this.responseText.setText("");
                    try {
                        NewDeviceFragment.this.userSSIDBytes = NewDeviceFragment.this.userSSID.getBytes("UTF-8");
                        NewDeviceFragment.this.userPasswordBytes = NewDeviceFragment.this.userPassword.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        Log.i(NewDeviceFragment.tag, e.toString());
                    }
                    NewDeviceFragment.this.sendUDPUnicastWrite(3, 1, new byte[]{0});
                    NewDeviceFragment.this.handler.postDelayed(new Runnable() {
                        public void run() {
                            NewDeviceFragment.this.sendUDPUnicastWrite(1, 1, NewDeviceFragment.this.userSSIDBytes);
                        }
                    }, 30);
                    NewDeviceFragment.this.handler.postDelayed(new Runnable() {
                        public void run() {
                            NewDeviceFragment.this.sendUDPUnicastWrite(1, 1, NewDeviceFragment.this.userSSIDBytes);
                        }
                    }, 60);
                    NewDeviceFragment.this.handler.postDelayed(new Runnable() {
                        public void run() {
                            NewDeviceFragment.this.sendUDPUnicastWrite(1, 2, NewDeviceFragment.this.userPasswordBytes);
                        }
                    }, 90);
                    NewDeviceFragment.this.handler.postDelayed(new Runnable() {
                        public void run() {
                            NewDeviceFragment.this.sendingText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.sendingText2));
                            NewDeviceFragment.this.responseText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.responseTextWaiting));
                        }
                    }, 1000);
                    NewDeviceFragment.this.espFirstResponseTimeoutRunnable = new Runnable() {
                        public void run() {
                            Log.i(NewDeviceFragment.tag, "espFirstResponseTimeoutRunnable, getting a 0x010D from dreamscreen timed out, start second attempt");
                            NewDeviceFragment.this.sendingText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.sendingText3));
                            NewDeviceFragment.this.responseText.setText("");
                            NewDeviceFragment.this.sendUDPUnicastWrite(1, 1, NewDeviceFragment.this.userSSIDBytes);
                            NewDeviceFragment.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    NewDeviceFragment.this.sendUDPUnicastWrite(1, 1, NewDeviceFragment.this.userSSIDBytes);
                                }
                            }, 30);
                            NewDeviceFragment.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    NewDeviceFragment.this.sendUDPUnicastWrite(1, 2, NewDeviceFragment.this.userPasswordBytes);
                                }
                            }, 60);
                            NewDeviceFragment.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    NewDeviceFragment.this.sendingText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.sendingText4));
                                    NewDeviceFragment.this.responseText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.responseTextWaiting));
                                }
                            }, 1000);
                            NewDeviceFragment.this.espSecondResponseTimeoutRunnable = new Runnable() {
                                public void run() {
                                    Log.i(NewDeviceFragment.tag, "espSecondResponseTimeoutRunnable, getting a 0x010D from dreamscreen timed out, connectToNextDreamScreen");
                                    Toast.makeText(NewDeviceFragment.this.getContext(), "Device is not responding", 1).show();
                                    NewDeviceFragment.this.connectToNextDreamScreen();
                                }
                            };
                            NewDeviceFragment.this.handler.postDelayed(NewDeviceFragment.this.espSecondResponseTimeoutRunnable, 40000);
                        }
                    };
                    NewDeviceFragment.this.handler.postDelayed(NewDeviceFragment.this.espFirstResponseTimeoutRunnable, 40000);
                }
            }, 12000);
        }
    }

    public void espConnectedToWiFi(boolean didConnect) {
        if (this.espFirstResponseTimeoutRunnable != null) {
            this.handler.removeCallbacks(this.espFirstResponseTimeoutRunnable);
        }
        if (this.espSecondResponseTimeoutRunnable != null) {
            this.handler.removeCallbacks(this.espSecondResponseTimeoutRunnable);
        }
        if (didConnect) {
            Log.i(tag, "esp did connect to wifi");
            this.handler.post(new Runnable() {
                public void run() {
                    NewDeviceFragment.this.responseText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.responseTextSuccess));
                }
            });
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    NewDeviceFragment.this.connectToNextDreamScreen();
                }
            }, 2000);
            return;
        }
        Log.i(tag, "new credentials did not work, startEspFailed");
        startEspFailed();
    }

    /* access modifiers changed from: private */
    public void connectToNextDreamScreen() {
        Log.i(tag, "connectToNextDreamScreen");
        if (this.espFirstResponseTimeoutRunnable != null) {
            this.handler.removeCallbacks(this.espFirstResponseTimeoutRunnable);
        }
        if (this.espSecondResponseTimeoutRunnable != null) {
            this.handler.removeCallbacks(this.espSecondResponseTimeoutRunnable);
        }
        if (this.newDreamScreens.isEmpty()) {
            connectToPreviousNetworkAndStartSplash();
        } else {
            connectToDreamScreen((String) this.newDreamScreens.getFirst());
        }
    }

    /* access modifiers changed from: private */
    public void handleSecondScanResults() {
        Log.i(tag, "handleSecondScanResults");
        List<ScanResult> secondScanResults = this.wifiManager.getScanResults();
        LinkedList<String> secondDreamScreens = new LinkedList<>();
        for (ScanResult scanResult : secondScanResults) {
            if (scanResult.SSID.contains("DreamScreen") || scanResult.SSID.contains("SideKick") || scanResult.SSID.contains(Connect.DEFAULT_NAME)) {
                secondDreamScreens.add(scanResult.SSID);
            }
        }
        Iterator it = secondDreamScreens.iterator();
        while (it.hasNext()) {
            this.newDreamScreensOriginal.remove((String) it.next());
        }
        this.pickDreamScreens.setVisibility(8);
        this.pickWiFi.setVisibility(8);
        this.searchingLayout.setVisibility(8);
        this.pickDreamScreens.setVisibility(8);
        this.confirmationLayout.setVisibility(0);
        TextView connectedText = (TextView) this.view.findViewById(R.id.connectedText);
        connectedText.setTypeface(this.font);
        if (this.newDreamScreensOriginal.isEmpty()) {
            connectedText.setText(getContext().getResources().getString(R.string.NoNewDevicesAdded));
        } else {
            LinearLayout addedList = (LinearLayout) this.view.findViewById(R.id.addedList);
            addedList.removeAllViews();
            Iterator it2 = this.newDreamScreensOriginal.iterator();
            while (it2.hasNext()) {
                String dreamScreen = (String) it2.next();
                Log.i(tag, "adding to list :" + dreamScreen);
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new LayoutParams(-2, -2));
                textView.setTypeface(this.font);
                textView.setText(dreamScreen);
                textView.setTextSize(18.0f);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
                addedList.addView(textView);
            }
        }
        TextView okButton = (TextView) this.view.findViewById(R.id.okButton);
        okButton.setTypeface(this.font);
        okButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NewDeviceFragment.this.connectToPreviousNetworkAndStartSplash();
            }
        });
    }

    /* access modifiers changed from: private */
    public void connectToPreviousNetworkAndStartSplash() {
        Log.i(tag, "connectToPreviousNetworkAndStartSplash");
        this.connectedToDreamScreen = false;
        this.handler.post(new Runnable() {
            public void run() {
                NewDeviceFragment.this.pickDreamScreens.setVisibility(8);
                NewDeviceFragment.this.pickWiFi.setVisibility(8);
                NewDeviceFragment.this.confirmationLayout.setVisibility(8);
                NewDeviceFragment.this.espFailedLayout.setVisibility(8);
                NewDeviceFragment.this.searchingLayout.setVisibility(0);
                NewDeviceFragment.this.connectingText.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.connectingText5));
                NewDeviceFragment.this.sendingText.setText("");
                NewDeviceFragment.this.responseText.setText("");
            }
        });
        List<WifiConfiguration> networks = this.wifiManager.getConfiguredNetworks();
        if (networks != null) {
            Iterator it = networks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
                boolean enableNetwork = this.wifiManager.enableNetwork(wifiConfiguration.networkId, false);
                if (wifiConfiguration.SSID.contains("DreamScreen") || wifiConfiguration.SSID.contains("SideKick") || wifiConfiguration.SSID.contains(Connect.DEFAULT_NAME)) {
                    this.wifiManager.removeNetwork(wifiConfiguration.networkId);
                }
                if (this.userSSID.equals(wifiConfiguration.SSID)) {
                    Log.i(tag, "setting previousNetworkId to the same one the user selected as userSSID");
                    this.previousNetworkId = wifiConfiguration.networkId;
                    break;
                }
            }
        } else {
            Log.e(tag, "wifiManager#getConfiguredNetworks is null");
        }
        this.wifiManager.saveConfiguration();
        if (this.previousNetworkId == -1) {
            startSplashActivity();
        } else if (this.wifiManager.getConnectionInfo().getNetworkId() == this.previousNetworkId) {
            startSplashActivity();
        } else if (this.wifiReceiver != null) {
            this.connectingBack = true;
            this.wifiManager.enableNetwork(this.previousNetworkId, true);
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    NewDeviceFragment.this.startSplashActivity();
                }
            }, 30000);
        } else {
            startSplashActivity();
        }
    }

    private void startEspFailed() {
        Log.i(tag, "startEspFailed");
        if (!(this.wifiReceiver == null || getActivity() == null)) {
            getActivity().unregisterReceiver(this.wifiReceiver);
            this.wifiReceiver = null;
        }
        this.handler.removeCallbacksAndMessages(null);
        this.handler.post(new Runnable() {
            public void run() {
                NewDeviceFragment.this.pickDreamScreens.setVisibility(8);
                NewDeviceFragment.this.pickWiFi.setVisibility(8);
                NewDeviceFragment.this.confirmationLayout.setVisibility(8);
                NewDeviceFragment.this.searchingLayout.setVisibility(8);
                NewDeviceFragment.this.espFailedLayout.setVisibility(0);
                ((TextView) NewDeviceFragment.this.view.findViewById(R.id.errorHeader)).setTypeface(NewDeviceFragment.this.font);
                ((TextView) NewDeviceFragment.this.view.findViewById(R.id.errorSubHeader)).setTypeface(NewDeviceFragment.this.font);
                final TextView thirdCancel = (TextView) NewDeviceFragment.this.view.findViewById(R.id.thirdCancel);
                thirdCancel.setTypeface(NewDeviceFragment.this.font);
                thirdCancel.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        NewDeviceFragment.this.connectToPreviousNetworkAndStartSplash();
                        thirdCancel.setText(NewDeviceFragment.this.getContext().getResources().getString(R.string.Canceling));
                    }
                });
                TextView restartText = (TextView) NewDeviceFragment.this.view.findViewById(R.id.restartText);
                restartText.setTypeface(NewDeviceFragment.this.font);
                restartText.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (NewDeviceFragment.this.newDeviceFragmentListener != null) {
                            NewDeviceFragment.this.newDeviceFragmentListener.startNewDeviceFragment();
                        }
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendUDPUnicastWrite(byte command1, byte command2, byte[] payload) {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        response.write(252);
        response.write((byte) (payload.length + 5));
        response.write(-1);
        response.write(1);
        response.write(command1);
        response.write(command2);
        for (byte b : payload) {
            response.write(b);
        }
        response.write(Light.uartComm_calculate_crc8(response.toByteArray()));
        new AccessPointUDPUnicast().execute(new byte[][]{response.toByteArray()});
    }

    /* access modifiers changed from: private */
    public void startSplashActivity() {
        if (this.splashStarted) {
            Log.i(tag, "startSplashActivity splashStarted true, return");
            return;
        }
        Log.i(tag, "startSplashActivity");
        this.handler.removeCallbacksAndMessages(null);
        if (!(this.wifiReceiver == null || getActivity() == null)) {
            getActivity().unregisterReceiver(this.wifiReceiver);
            this.wifiReceiver = null;
        }
        if (this.newDeviceFragmentListener != null) {
            this.splashStarted = true;
            this.handler.post(new Runnable() {
                public void run() {
                    String newText;
                    try {
                        newText = NewDeviceFragment.this.getContext().getResources().getString(R.string.connectingText6);
                    } catch (NullPointerException e) {
                        newText = "A Few More Seconds...";
                    }
                    NewDeviceFragment.this.connectingText.setText(newText);
                    NewDeviceFragment.this.sendingText.setText("");
                    NewDeviceFragment.this.responseText.setText("");
                }
            });
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    if (NewDeviceFragment.this.newDeviceFragmentListener != null) {
                        NewDeviceFragment.this.newDeviceFragmentListener.enableBackButton();
                        NewDeviceFragment.this.newDeviceFragmentListener.startSplashActivity();
                        return;
                    }
                    Log.i(NewDeviceFragment.tag, "newdevicefragment listener null, did not start splash. potential for app to be hanging");
                }
            }, 5000);
            return;
        }
        Log.i(tag, "not starting splash activity, listener == null ");
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewDeviceFragmentListener) {
            this.newDeviceFragmentListener = (NewDeviceFragmentListener) context;
            Log.i(tag, "newDeviceFragment onAttach");
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    public void onDetach() {
        if (this.permissionsDialog != null && this.permissionsDialog.isShowing()) {
            this.permissionsDialog.dismiss();
        }
        if (this.locationDialog != null && this.locationDialog.isShowing()) {
            this.locationDialog.dismiss();
        }
        if (!(this.wifiReceiver == null || getActivity() == null)) {
            getActivity().unregisterReceiver(this.wifiReceiver);
            this.wifiReceiver = null;
        }
        if (this.gpsSwitchStateReceiver != null && this.gpsReceiverRegistered) {
            this.gpsReceiverRegistered = false;
            getActivity().unregisterReceiver(this.gpsSwitchStateReceiver);
        }
        super.onDetach();
        this.newDeviceFragmentListener = null;
        Log.i(tag, "newDeviceFragment onDetach");
    }
}

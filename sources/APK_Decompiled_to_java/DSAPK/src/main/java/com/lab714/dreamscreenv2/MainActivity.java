package com.lab714.dreamscreenv2;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.lab714.dreamscreenv2.devices.Connect;
import com.lab714.dreamscreenv2.devices.DreamScreen;
import com.lab714.dreamscreenv2.devices.DreamScreen4K;
import com.lab714.dreamscreenv2.devices.DreamScreenSolo;
import com.lab714.dreamscreenv2.devices.Light;
import com.lab714.dreamscreenv2.devices.SideKick;
import com.lab714.dreamscreenv2.devices.UDPListener;
import com.lab714.dreamscreenv2.devices.UDPListener.InterfaceListener;
import com.lab714.dreamscreenv2.layout.AdvancedSettingsFragment;
import com.lab714.dreamscreenv2.layout.AdvancedSettingsFragment.AdvancedSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.AudioSettingsFragment.AudioSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.ColorSettingsFragment.ColorSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.ConnectSettingsFragment;
import com.lab714.dreamscreenv2.layout.ConnectSettingsFragment.ConnectSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.ControlWidget;
import com.lab714.dreamscreenv2.layout.DreamScreenFragment;
import com.lab714.dreamscreenv2.layout.DreamScreenFragment.DreamScreenFragmentListener;
import com.lab714.dreamscreenv2.layout.DreamScreenSettingsFragment;
import com.lab714.dreamscreenv2.layout.DreamScreenSettingsFragment.DreamScreenSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment;
import com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.FirmwareUpdateFragmentListener;
import com.lab714.dreamscreenv2.layout.HelpFragment.HelpFragmentListener;
import com.lab714.dreamscreenv2.layout.HueLifxSettingsFragment.HueLifxSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.InstallationSettingsFragment.InstallationSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.IntegrationSettingsFragment.IntegrationSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.NewDeviceFragment;
import com.lab714.dreamscreenv2.layout.NewDeviceFragment.NewDeviceFragmentListener;
import com.lab714.dreamscreenv2.layout.RemoteSettingsFragment;
import com.lab714.dreamscreenv2.layout.RemoteSettingsFragment.RemoteSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.SectorSettingsFragment.SectorSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.SideKickSettingsFragment;
import com.lab714.dreamscreenv2.layout.SideKickSettingsFragment.SideKickSettingsFragmentListener;
import com.lab714.dreamscreenv2.layout.SplashFragment;
import com.lab714.dreamscreenv2.layout.SplashFragment.SplashFragmentListener;
import com.lab714.dreamscreenv2.layout.VideoSettingsFragment.VideoSettingsFragmentListener;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements SplashFragmentListener, DreamScreenFragmentListener, DreamScreenSettingsFragmentListener, SideKickSettingsFragmentListener, VideoSettingsFragmentListener, ColorSettingsFragmentListener, AudioSettingsFragmentListener, InstallationSettingsFragmentListener, NewDeviceFragmentListener, AdvancedSettingsFragmentListener, SectorSettingsFragmentListener, HelpFragmentListener, FirmwareUpdateFragmentListener, InterfaceListener, ConnectSettingsFragmentListener, HueLifxSettingsFragmentListener, IntegrationSettingsFragmentListener, RemoteSettingsFragmentListener {
    private static final String dsv2Prefs = "dsv2Prefs";
    private static final long pingInterval = 15000;
    private static final String tag = "MainActivity";
    private static final String tutorialShownKey = "tutorialShownKey";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private boolean backButtonEnabled = true;
    /* access modifiers changed from: private */
    public AlertDialog badDeviceDialog;
    private boolean badDeviceDialogShown = false;
    /* access modifiers changed from: private */
    public AlertDialog bootloadingDeviceDialog;
    private String broadcastIpString;
    /* access modifiers changed from: private */
    public boolean broadcastingToGroup = false;
    private int chevronIcon;
    /* access modifiers changed from: private */
    public Light currentLight = null;
    private float drawerGroupSize;
    /* access modifiers changed from: private */
    public DrawerLayout drawerLayout;
    /* access modifiers changed from: private */
    public LinearLayout drawerLinearLayout;
    private float drawerTextSize;
    private Typeface font;
    /* access modifiers changed from: private */
    public LinkedList<LinkedList<Light>> groups = new LinkedList<>();
    /* access modifiers changed from: private */
    public final Handler handler = new Handler();
    private String ipAddressString;
    /* access modifiers changed from: private */
    public Light lightForGrouping = null;
    /* access modifiers changed from: private */
    public Light lightToPing;
    private int menuIcon;
    private final LinkedList<String> newDevices = new LinkedList<>();
    private boolean newDevicesShown = false;
    private long pingSentAtTime = SystemClock.elapsedRealtime();
    private Runnable pingTimeoutRunnable;
    private boolean reattemptJumpToWidgetSettingsFlag = false;
    private ScrollView scrollView;
    private ImageButton settingsButton;
    private boolean toastShown = false;
    /* access modifiers changed from: private */
    public ImageView toolbarGroupIcon;
    /* access modifiers changed from: private */
    public TextView toolbarTitle;
    /* access modifiers changed from: private */
    public float toolbarTitleSize;
    /* access modifiers changed from: private */
    public UDPListener udpListener;
    /* access modifiers changed from: private */
    public boolean userEdittingUnlocked = false;
    private WifiManager wifiManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        boolean isSw600;
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        if (!getSharedPreferences(dsv2Prefs, 0).getBoolean(tutorialShownKey, false)) {
            Log.i(tag, "Starting TutorialActivity");
            Intent intent = new Intent(this, TutorialActivity.class);
            intent.setFlags(268451840);
            startActivity(intent);
            finish();
            return;
        }
        Log.i(tag, "tutorial has been shown");
        if (getResources().getDimension(R.dimen.isSw600) != 0.0f) {
            isSw600 = true;
            this.chevronIcon = R.drawable.ic_chevron_left_white_48dp;
            this.menuIcon = R.drawable.ic_menu_white_36dp;
            this.drawerTextSize = 17.0f;
            this.drawerGroupSize = 22.0f;
            this.toolbarTitleSize = 30.0f;
        } else {
            isSw600 = false;
            this.chevronIcon = R.drawable.ic_chevron_left_white_36dp;
            this.menuIcon = R.drawable.ic_menu_white_24dp;
            this.drawerTextSize = 15.0f;
            this.drawerGroupSize = 20.0f;
            this.toolbarTitleSize = 24.0f;
        }
        Log.i(tag, "isSw600 " + isSw600);
        this.font = Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        this.toolbarTitle.setTypeface(this.font);
        this.toolbarGroupIcon = (ImageView) toolbar.findViewById(R.id.toolbarGroupIcon);
        findViewById(R.id.rootView).setOnDragListener(new OnDragListener() {
            public boolean onDrag(View view, DragEvent dragEvent) {
                if (!MainActivity.this.userEdittingUnlocked) {
                    return false;
                }
                switch (dragEvent.getAction()) {
                    case 1:
                        if (MainActivity.this.lightForGrouping != null) {
                            return true;
                        }
                        return false;
                    case 3:
                        return MainActivity.this.setLightForGroupingUnassigned();
                    default:
                        return true;
                }
            }
        });
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerLayout.addDrawerListener(new DrawerListener() {
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            public void onDrawerOpened(View drawerView) {
                ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(drawerView.getWindowToken(), 0);
            }

            public void onDrawerClosed(View drawerView) {
                Log.i(MainActivity.tag, "onDrawerClosed");
                ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(drawerView.getWindowToken(), 0);
                if (MainActivity.this.userEdittingUnlocked) {
                    MainActivity.this.userEdittingUnlocked = false;
                    MainActivity.this.redrawDrawerLinearLayout();
                    MainActivity.this.highlightDrawerSelection();
                }
            }

            public void onDrawerStateChanged(int newState) {
            }
        });
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        this.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        this.actionBarDrawerToggle.setToolbarNavigationClickListener(new OnClickListener() {
            public void onClick(View v) {
                Fragment currentFragment = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                if (MainActivity.this.drawerLayout.isDrawerVisible((int) GravityCompat.START)) {
                    MainActivity.this.drawerLayout.closeDrawer((int) GravityCompat.START);
                } else if (currentFragment instanceof DreamScreenFragment) {
                    MainActivity.this.drawerLayout.openDrawer((int) GravityCompat.START);
                } else if (!(currentFragment instanceof FirmwareUpdateFragment) || !((FirmwareUpdateFragment) currentFragment).isUpdating()) {
                    MainActivity.this.getSupportFragmentManager().popBackStack();
                    Log.i(MainActivity.tag, "mainactivity popping backstack");
                }
            }
        });
        this.settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        this.settingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayout);
                if (currentFragment instanceof DreamScreenFragment) {
                    MainActivity.this.setBackArrowIcon();
                    for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                        fragmentManager.popBackStack();
                    }
                    if (MainActivity.this.currentLight instanceof DreamScreen) {
                        fragmentManager.beginTransaction().replace(R.id.frameLayout, DreamScreenSettingsFragment.newInstance()).addToBackStack(null).commit();
                    } else if (MainActivity.this.currentLight instanceof SideKick) {
                        fragmentManager.beginTransaction().replace(R.id.frameLayout, SideKickSettingsFragment.newInstance()).addToBackStack(null).commit();
                    } else if (MainActivity.this.currentLight instanceof Connect) {
                        fragmentManager.beginTransaction().replace(R.id.frameLayout, ConnectSettingsFragment.newInstance()).addToBackStack(null).commit();
                    }
                } else if ((currentFragment instanceof FirmwareUpdateFragment) && ((FirmwareUpdateFragment) currentFragment).isUpdating()) {
                } else {
                    if ((currentFragment instanceof DreamScreenSettingsFragment) || (currentFragment instanceof SideKickSettingsFragment) || (currentFragment instanceof ConnectSettingsFragment)) {
                        Log.i(MainActivity.tag, "Settings button tapped, ignoring");
                        return;
                    }
                    MainActivity.this.getSupportFragmentManager().popBackStack();
                    Log.i(MainActivity.tag, "mainactivity settings button popping backstack");
                }
            }
        });
        this.scrollView = (ScrollView) findViewById(R.id.drawerScrollView);
        this.drawerLinearLayout = (LinearLayout) findViewById(R.id.drawerLinearLayout);
        redrawDrawerLinearLayout();
        this.scrollView.setSmoothScrollingEnabled(true);
        this.drawerLinearLayout.setOnDragListener(new OnDragListener() {
            public boolean onDrag(View view, DragEvent dragEvent) {
                if (!MainActivity.this.userEdittingUnlocked) {
                    return false;
                }
                switch (dragEvent.getAction()) {
                    case 2:
                        MainActivity.this.checkIfScrollNeeded(view, dragEvent);
                        return true;
                    case 3:
                        return MainActivity.this.setLightForGroupingUnassigned();
                    default:
                        return true;
                }
            }
        });
        this.wifiManager = (WifiManager) getApplicationContext().getSystemService("wifi");
        initPhonesIp();
        initBroadcastIp();
        if (this.udpListener == null) {
            Log.i(tag, "Creating new udpListener");
            this.udpListener = new UDPListener(this.ipAddressString, this.broadcastIpString);
            this.udpListener.setListener(this);
        }
        startSplashActivity();
    }

    public void setMenuIcon() {
        Log.i(tag, "setMenuIcon");
        this.actionBarDrawerToggle.setHomeAsUpIndicator(this.menuIcon);
        if (this.broadcastingToGroup) {
            this.settingsButton.setVisibility(4);
        } else {
            this.settingsButton.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void setBackArrowIcon() {
        Log.i(tag, "setBackArrowIcon");
        this.actionBarDrawerToggle.setHomeAsUpIndicator(this.chevronIcon);
        this.settingsButton.setVisibility(4);
    }

    /* access modifiers changed from: private */
    public void hideToolbarButtons() {
        Log.i(tag, "hideToolbarButtons");
        this.actionBarDrawerToggle.setHomeAsUpIndicator((Drawable) null);
        this.settingsButton.setVisibility(4);
    }

    /* access modifiers changed from: private */
    public void checkIfScrollNeeded(View view, DragEvent dragEvent) {
        Point touchPoint = convertPoint(new Point((int) dragEvent.getX(), (int) dragEvent.getY()), view, this.scrollView);
        int range = (int) (0.05d * ((double) this.scrollView.getHeight()));
        if (touchPoint.y < range) {
            Log.i(tag, "checkIfScrollNeeded, scrolling up");
            this.scrollView.smoothScrollBy(0, -range);
        } else if (this.scrollView.getHeight() - touchPoint.y < range) {
            Log.i(tag, "checkIfScrollNeeded, scrolling down");
            this.scrollView.smoothScrollBy(0, range);
        } else {
            Log.i(tag, "checkIfScrollNeeded, not scrolling");
        }
    }

    public static Point convertPoint(Point fromPoint, View fromView, View toView) {
        int[] fromCoord = new int[2];
        int[] toCoord = new int[2];
        fromView.getLocationOnScreen(fromCoord);
        toView.getLocationOnScreen(toCoord);
        return new Point((fromCoord[0] - toCoord[0]) + fromPoint.x, (fromCoord[1] - toCoord[1]) + fromPoint.y);
    }

    /* access modifiers changed from: private */
    public void redrawDrawerLinearLayout() {
        this.drawerLinearLayout.removeAllViews();
        this.drawerLinearLayout.invalidate();
        boolean groupTextAdded = false;
        int secondaryTextColor = ContextCompat.getColor(getApplicationContext(), R.color.secondaryLightTextColor);
        Iterator it = this.groups.iterator();
        while (it.hasNext()) {
            LinkedList<Light> group = (LinkedList) it.next();
            final LinearLayout drawerGroup = new LinearLayout(this);
            if (!group.isEmpty()) {
                Log.i(tag, "redraw drawer group " + ((Light) group.get(0)).getGroupNumber());
            } else {
                Log.i(tag, "redraw drawer empty group");
            }
            if (!group.isEmpty() && ((Light) group.get(0)).getGroupNumber() != 0) {
                LinearLayout linearLayout = new LinearLayout(this);
                LayoutParams layoutParams = new LayoutParams(-1, -2);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setPadding(0, pxToDp(5), 0, pxToDp(5));
                linearLayout.setOrientation(1);
                this.drawerLinearLayout.addView(linearLayout);
                if (!groupTextAdded) {
                    groupTextAdded = true;
                    LinearLayout linearLayout2 = new LinearLayout(this);
                    LayoutParams layoutParams2 = new LayoutParams(-1, pxToDp(1));
                    linearLayout2.setLayoutParams(layoutParams2);
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.secondaryLightTextColor));
                    linearLayout.addView(linearLayout2);
                    TextView textView = new TextView(this);
                    LayoutParams layoutParams3 = new LayoutParams(-2, -2);
                    layoutParams3.setMargins(pxToDp(16), 0, 0, 0);
                    textView.setLayoutParams(layoutParams3);
                    textView.setPadding(0, pxToDp(3), 0, pxToDp(3));
                    textView.setText(getResources().getString(R.string.Groups));
                    textView.setTextSize(this.drawerGroupSize);
                    textView.setTextColor(secondaryTextColor);
                    textView.setTypeface(this.font);
                    linearLayout.addView(textView);
                }
                LinearLayout linearLayout3 = new LinearLayout(this);
                LayoutParams layoutParams4 = new LayoutParams(-1, pxToDp(1));
                linearLayout3.setLayoutParams(layoutParams4);
                linearLayout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.secondaryLightTextColor));
                linearLayout.addView(linearLayout3);
                LayoutParams layoutParams5 = new LayoutParams(-1, pxToDp(48));
                drawerGroup.setLayoutParams(layoutParams5);
                drawerGroup.setOrientation(0);
                drawerGroup.setPadding(pxToDp(16), 0, pxToDp(16), 0);
                drawerGroup.setGravity(16);
                drawerGroup.setTag(group);
                this.drawerLinearLayout.addView(drawerGroup);
                ImageView imageView = new ImageView(this);
                LayoutParams layoutParams6 = new LayoutParams(pxToDp(30), pxToDp(30));
                imageView.setLayoutParams(layoutParams6);
                imageView.setImageResource(R.drawable.drawer_app_groupoption2_128px);
                drawerGroup.addView(imageView);
                EditText groupName = (EditText) getLayoutInflater().inflate(R.layout.navigation_drawer_edittext, null);
                LayoutParams layoutParams7 = new LayoutParams(-2, -2, 1.0f);
                layoutParams7.setMargins(pxToDp(10), 0, 0, 0);
                groupName.setLayoutParams(layoutParams7);
                groupName.setText(((Light) group.get(0)).getGroupName().trim());
                groupName.setTextSize(this.drawerTextSize);
                groupName.setTextColor(secondaryTextColor);
                groupName.setSingleLine(false);
                groupName.setHorizontallyScrolling(false);
                groupName.setMaxLines(2);
                groupName.setImeOptions(6);
                groupName.setInputType(663697);
                groupName.setTypeface(this.font);
                groupName.setBackgroundColor(0);
                groupName.setPadding(0, pxToDp(1), 0, 0);
                groupName.setFilters(new InputFilter[]{new LengthFilter(16)});
                if (this.userEdittingUnlocked) {
                    groupName.setFocusable(true);
                    groupName.setFocusableInTouchMode(true);
                } else {
                    groupName.setFocusable(false);
                    groupName.setFocusableInTouchMode(false);
                }
                drawerGroup.addView(groupName);
                AnonymousClass6 r0 = new OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        return true;
                    }
                };
                groupName.setOnLongClickListener(r0);
                final EditText editText = groupName;
                final LinkedList linkedList = group;
                AnonymousClass7 r02 = new OnFocusChangeListener() {
                    public void onFocusChange(View view, boolean hasFocus) {
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService("input_method");
                        if (hasFocus) {
                            Log.i(MainActivity.tag, "groupName hasFocus");
                            inputMethodManager.showSoftInput(editText, 2);
                            editText.setSelection(editText.getText().length());
                            return;
                        }
                        Log.i(MainActivity.tag, "groupName lost focus");
                        String newGroupName = editText.getText().toString().trim();
                        if (newGroupName.length() == 0) {
                            newGroupName = "Group";
                            editText.setText(newGroupName);
                        }
                        Log.i(MainActivity.tag, "user entered new groupname " + newGroupName);
                        boolean currentLightInGroup = false;
                        Iterator it = linkedList.iterator();
                        while (it.hasNext()) {
                            Light light = (Light) it.next();
                            light.setGroupName(newGroupName, false);
                            if (light == MainActivity.this.currentLight) {
                                currentLightInGroup = true;
                            }
                        }
                        if (MainActivity.this.broadcastingToGroup && currentLightInGroup) {
                            MainActivity.this.setToolbarTitle();
                        }
                    }
                };
                groupName.setOnFocusChangeListener(r02);
                final EditText editText2 = groupName;
                AnonymousClass8 r03 = new OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        switch (actionId) {
                            case 0:
                            case 5:
                            case 6:
                                editText2.clearFocus();
                                ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText2.getWindowToken(), 0);
                                return true;
                            default:
                                return false;
                        }
                    }
                };
                groupName.setOnEditorActionListener(r03);
                final EditText editText3 = groupName;
                AnonymousClass9 r04 = new TextWatcher() {
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
                            editText3.clearFocus();
                            ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText3.getWindowToken(), 0);
                        }
                    }
                };
                groupName.addTextChangedListener(r04);
                final EditText editText4 = groupName;
                AnonymousClass10 r05 = new OnClickListener() {
                    public void onClick(View view) {
                        if (!editText4.hasFocus() || !MainActivity.this.userEdittingUnlocked) {
                            if (editText4.hasFocus()) {
                                editText4.clearFocus();
                            }
                            drawerGroup.performClick();
                        }
                    }
                };
                groupName.setOnClickListener(r05);
                final EditText editText5 = groupName;
                final LinkedList linkedList2 = group;
                OnDragListener drawerGroupDragListener = new OnDragListener() {
                    public boolean onDrag(View view, DragEvent dragEvent) {
                        if (!MainActivity.this.userEdittingUnlocked) {
                            return false;
                        }
                        switch (dragEvent.getAction()) {
                            case 2:
                                MainActivity.this.checkIfScrollNeeded(view, dragEvent);
                                return true;
                            case 3:
                                return MainActivity.this.setLightForGroupingToGroup(linkedList2);
                            case 5:
                                editText5.setFocusable(false);
                                editText5.setFocusableInTouchMode(false);
                                return true;
                            case 6:
                                editText5.setFocusable(true);
                                editText5.setFocusableInTouchMode(true);
                                return true;
                            default:
                                return true;
                        }
                    }
                };
                groupName.setOnDragListener(drawerGroupDragListener);
                if (this.userEdittingUnlocked) {
                    ImageView imageView2 = new ImageView(this);
                    LayoutParams layoutParams8 = new LayoutParams(pxToDp(36), pxToDp(36));
                    imageView2.setLayoutParams(layoutParams8);
                    imageView2.setPadding(pxToDp(6), pxToDp(6), pxToDp(6), pxToDp(6));
                    imageView2.setImageResource(R.drawable.ic_create_white_36dp);
                    imageView2.setScaleType(ScaleType.FIT_CENTER);
                    drawerGroup.addView(imageView2);
                    final EditText editText6 = groupName;
                    AnonymousClass12 r06 = new OnClickListener() {
                        public void onClick(View view) {
                            if (!MainActivity.this.userEdittingUnlocked) {
                                return;
                            }
                            if (editText6.hasFocus()) {
                                editText6.clearFocus();
                                ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText6.getWindowToken(), 0);
                                return;
                            }
                            editText6.requestFocus();
                        }
                    };
                    imageView2.setOnClickListener(r06);
                }
                final LinkedList linkedList3 = group;
                AnonymousClass13 r07 = new OnClickListener() {
                    public void onClick(View view) {
                        if (!MainActivity.this.userEdittingUnlocked) {
                            Fragment currentFragment = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                            if (!(currentFragment instanceof FirmwareUpdateFragment) || !((FirmwareUpdateFragment) currentFragment).isUpdating()) {
                                MainActivity.this.currentLight = (Light) linkedList3.get(0);
                                Iterator it = linkedList3.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    Light light = (Light) it.next();
                                    if (light instanceof DreamScreen) {
                                        MainActivity.this.currentLight = light;
                                        break;
                                    }
                                }
                                MainActivity.this.currentLight.setMode(MainActivity.this.currentLight.getMode(), true);
                                Iterator it2 = linkedList3.iterator();
                                while (it2.hasNext()) {
                                    ((Light) it2.next()).initMode(MainActivity.this.currentLight.getMode());
                                }
                                MainActivity.this.broadcastingToGroup = true;
                                MainActivity.this.redrawDrawerLinearLayout();
                                MainActivity.this.highlightDrawerSelection();
                                MainActivity.this.startDreamScreenFragment();
                                MainActivity.this.drawerLayout.closeDrawers();
                                return;
                            }
                            Log.i(MainActivity.tag, "dont do anything while firmware updating");
                        }
                    }
                };
                drawerGroup.setOnClickListener(r07);
                drawerGroup.setOnDragListener(drawerGroupDragListener);
            }
            int lightIndex = 0;
            Iterator it2 = group.iterator();
            while (it2.hasNext()) {
                Light light = (Light) it2.next();
                final LinearLayout drawerLight = new LinearLayout(this);
                drawerLight.setLayoutParams(new LayoutParams(-1, pxToDp(48)));
                drawerLight.setOrientation(0);
                if (light.getGroupNumber() == 0) {
                    drawerLight.setPadding(pxToDp(16), 0, pxToDp(16), 0);
                } else {
                    drawerLight.setPadding(pxToDp(55), 0, pxToDp(16), 0);
                }
                drawerLight.setGravity(16);
                drawerLight.setTag(light);
                this.drawerLinearLayout.addView(drawerLight);
                ImageView imageView3 = new ImageView(this);
                imageView3.setLayoutParams(new LayoutParams(pxToDp(30), pxToDp(30)));
                drawerLight.addView(imageView3);
                if (light.getProductId() == 1) {
                    imageView3.setImageResource(R.drawable.dreamscreen_icon);
                } else if (light.getProductId() == 2) {
                    imageView3.setImageResource(R.drawable.dreamscreen4k_icon);
                } else if (light.getProductId() == 7) {
                    imageView3.setImageResource(R.drawable.dreamscreensolo_icon);
                } else if (light.getProductId() == 3) {
                    imageView3.setImageResource(R.drawable.sidekick_icon);
                } else if (light.getProductId() == 4) {
                    imageView3.setImageResource(R.drawable.connect_icon);
                } else {
                    Log.i(tag, "redrawDrawer icon error");
                    imageView3.setImageResource(R.drawable.sidekick_icon);
                }
                EditText lightName = (EditText) getLayoutInflater().inflate(R.layout.navigation_drawer_edittext, null);
                LayoutParams layoutParams9 = new LayoutParams(-2, -2, 1.0f);
                layoutParams9.setMargins(pxToDp(10), 0, 0, 0);
                lightName.setLayoutParams(layoutParams9);
                lightName.setText(light.getName().trim());
                lightName.setTextSize(this.drawerTextSize);
                lightName.setTextColor(secondaryTextColor);
                lightName.setGravity(16);
                lightName.setSingleLine(false);
                lightName.setHorizontallyScrolling(false);
                lightName.setMaxLines(2);
                lightName.setImeOptions(6);
                lightName.setInputType(663697);
                lightName.setTypeface(this.font);
                lightName.setBackgroundColor(0);
                lightName.setPadding(0, pxToDp(1), 0, 0);
                lightName.setFilters(new InputFilter[]{new LengthFilter(16)});
                if (this.userEdittingUnlocked) {
                    lightName.setFocusable(true);
                    lightName.setFocusableInTouchMode(true);
                } else {
                    lightName.setFocusable(false);
                    lightName.setFocusableInTouchMode(false);
                }
                drawerLight.addView(lightName);
                final EditText editText7 = lightName;
                final Light light2 = light;
                AnonymousClass14 r08 = new OnFocusChangeListener() {
                    public void onFocusChange(View view, boolean hasFocus) {
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService("input_method");
                        if (hasFocus) {
                            Log.i(MainActivity.tag, editText7.getText().toString() + " has focus");
                            inputMethodManager.showSoftInput(editText7, 2);
                            editText7.setSelection(editText7.getText().length());
                            return;
                        }
                        Log.i(MainActivity.tag, editText7.getText().toString() + " lost focus");
                        String newName = editText7.getText().toString().trim();
                        if (newName.length() == 0) {
                            if (light2.getProductId() == 1) {
                                newName = "DreamScreen HD";
                            } else if (light2.getProductId() == 2) {
                                newName = "DreamScreen 4K";
                            } else if (light2.getProductId() == 7) {
                                newName = "DreamScreen Solo";
                            } else if (light2.getProductId() == 3) {
                                newName = "SideKick";
                            } else if (light2.getProductId() == 4) {
                                newName = Connect.DEFAULT_NAME;
                            } else {
                                Log.i(MainActivity.tag, "error, bad productId");
                                newName = "Light";
                            }
                            editText7.setText(newName);
                        }
                        Log.i(MainActivity.tag, "user entered new lightName " + newName);
                        light2.setName(newName);
                        if (MainActivity.this.currentLight == light2 && !MainActivity.this.broadcastingToGroup) {
                            Log.i(MainActivity.tag, "currentLight == light && !broadcastingToGroup; calling setToolbarTitle");
                            MainActivity.this.setToolbarTitle();
                        }
                    }
                };
                lightName.setOnFocusChangeListener(r08);
                final EditText editText8 = lightName;
                AnonymousClass15 r09 = new OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Log.i(MainActivity.tag, "lightname onEditorActionListener");
                        switch (actionId) {
                            case 0:
                            case 5:
                            case 6:
                                editText8.clearFocus();
                                ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText8.getWindowToken(), 0);
                                return true;
                            default:
                                return false;
                        }
                    }
                };
                lightName.setOnEditorActionListener(r09);
                final EditText editText9 = lightName;
                AnonymousClass16 r010 = new TextWatcher() {
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
                            editText9.clearFocus();
                            ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText9.getWindowToken(), 0);
                        }
                    }
                };
                lightName.addTextChangedListener(r010);
                final EditText editText10 = lightName;
                AnonymousClass17 r011 = new OnClickListener() {
                    public void onClick(View view) {
                        if (!editText10.hasFocus() || !MainActivity.this.userEdittingUnlocked) {
                            if (editText10.hasFocus()) {
                                editText10.clearFocus();
                            }
                            drawerLight.performClick();
                        }
                    }
                };
                lightName.setOnClickListener(r011);
                final EditText editText11 = lightName;
                AnonymousClass18 r012 = new OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        if (!editText11.hasFocus() || !MainActivity.this.userEdittingUnlocked) {
                            drawerLight.performLongClick();
                        }
                        return true;
                    }
                };
                lightName.setOnLongClickListener(r012);
                if (this.userEdittingUnlocked) {
                    ImageView imageView4 = new ImageView(this);
                    imageView4.setLayoutParams(new LayoutParams(pxToDp(36), pxToDp(36)));
                    imageView4.setPadding(pxToDp(6), pxToDp(6), pxToDp(6), pxToDp(6));
                    imageView4.setImageResource(R.drawable.ic_create_white_36dp);
                    imageView4.setScaleType(ScaleType.FIT_CENTER);
                    drawerLight.addView(imageView4);
                    imageView4.setFocusable(false);
                    imageView4.setFocusableInTouchMode(false);
                    drawerLight.setFocusable(false);
                    drawerLight.setFocusableInTouchMode(false);
                    final EditText editText12 = lightName;
                    AnonymousClass19 r013 = new OnClickListener() {
                        public void onClick(View view) {
                            if (!MainActivity.this.userEdittingUnlocked) {
                                return;
                            }
                            if (editText12.hasFocus()) {
                                Log.i(MainActivity.tag, "editbutton, lightname clear focus, hide soft input");
                                editText12.clearFocus();
                                ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText12.getWindowToken(), 0);
                                return;
                            }
                            Log.i(MainActivity.tag, "editbutton, lightname request focus");
                            editText12.requestFocus();
                        }
                    };
                    imageView4.setOnClickListener(r013);
                }
                final Light light3 = light;
                AnonymousClass20 r014 = new OnClickListener() {
                    public void onClick(View view) {
                        if (!MainActivity.this.userEdittingUnlocked) {
                            Fragment currentFragment = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                            if (!(currentFragment instanceof FirmwareUpdateFragment) || !((FirmwareUpdateFragment) currentFragment).isUpdating()) {
                                MainActivity.this.currentLight = light3;
                                MainActivity.this.broadcastingToGroup = false;
                                MainActivity.this.redrawDrawerLinearLayout();
                                MainActivity.this.highlightDrawerSelection();
                                MainActivity.this.startDreamScreenFragment();
                                MainActivity.this.drawerLayout.closeDrawers();
                                return;
                            }
                            Log.i(MainActivity.tag, "dont do anything while firmware updating");
                        }
                    }
                };
                drawerLight.setOnClickListener(r014);
                final Light light4 = light;
                AnonymousClass21 r015 = new OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        Fragment currentFragment = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                        if ((currentFragment instanceof FirmwareUpdateFragment) && ((FirmwareUpdateFragment) currentFragment).isUpdating()) {
                            Log.i(MainActivity.tag, "dont do anything while firmware updating");
                            return true;
                        } else if (!MainActivity.this.userEdittingUnlocked) {
                            return false;
                        } else {
                            MainActivity.this.lightForGrouping = light4;
                            ClipData clipData = ClipData.newPlainText("", "");
                            DragShadowBuilder dragShadow = new DragShadowBuilder(drawerLight);
                            if (VERSION.SDK_INT >= 24) {
                                view.startDragAndDrop(clipData, dragShadow, null, 0);
                            } else {
                                view.startDrag(clipData, dragShadow, null, 0);
                            }
                            ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(drawerLight.getWindowToken(), 0);
                            return true;
                        }
                    }
                };
                drawerLight.setOnLongClickListener(r015);
                final EditText editText13 = lightName;
                final LinkedList linkedList4 = group;
                OnDragListener drawerLightDragListener = new OnDragListener() {
                    public boolean onDrag(View view, DragEvent dragEvent) {
                        if (!MainActivity.this.userEdittingUnlocked) {
                            return false;
                        }
                        switch (dragEvent.getAction()) {
                            case 2:
                                MainActivity.this.checkIfScrollNeeded(view, dragEvent);
                                return true;
                            case 3:
                                return MainActivity.this.setLightForGroupingToGroup(linkedList4);
                            case 5:
                                editText13.setFocusable(false);
                                editText13.setFocusableInTouchMode(false);
                                return true;
                            case 6:
                                editText13.setFocusable(true);
                                editText13.setFocusableInTouchMode(true);
                                return true;
                            default:
                                return true;
                        }
                    }
                };
                drawerLight.setOnDragListener(drawerLightDragListener);
                lightName.setOnDragListener(drawerLightDragListener);
                lightIndex++;
            }
        }
        if (this.userEdittingUnlocked) {
            LinearLayout linearLayout4 = new LinearLayout(this);
            LayoutParams layoutParams10 = new LayoutParams(-1, pxToDp(1));
            layoutParams10.setMargins(0, pxToDp(5), 0, pxToDp(5));
            linearLayout4.setLayoutParams(layoutParams10);
            linearLayout4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.secondaryLightTextColor));
            this.drawerLinearLayout.addView(linearLayout4);
            LinearLayout drawerAddNewGroup = new LinearLayout(this);
            drawerAddNewGroup.setId(R.id.newGroup);
            LayoutParams layoutParams11 = new LayoutParams(-1, pxToDp(48));
            drawerAddNewGroup.setLayoutParams(layoutParams11);
            drawerAddNewGroup.setOrientation(0);
            drawerAddNewGroup.setPadding(pxToDp(16), 0, pxToDp(16), 0);
            drawerAddNewGroup.setGravity(16);
            this.drawerLinearLayout.addView(drawerAddNewGroup);
            ImageView imageView5 = new ImageView(this);
            imageView5.setLayoutParams(new LayoutParams(pxToDp(30), pxToDp(30)));
            imageView5.setImageResource(R.drawable.drawer_app_groupoption2_128px);
            drawerAddNewGroup.addView(imageView5);
            TextView addNewGroupText = new TextView(this);
            LayoutParams layoutParams12 = new LayoutParams(-2, -2);
            layoutParams12.setMargins(pxToDp(10), 0, 0, 0);
            addNewGroupText.setLayoutParams(layoutParams12);
            addNewGroupText.setText(getResources().getString(R.string.dragDropText));
            addNewGroupText.setTextSize(this.drawerTextSize);
            addNewGroupText.setTypeface(this.font);
            addNewGroupText.setTextColor(secondaryTextColor);
            drawerAddNewGroup.addView(addNewGroupText);
            AnonymousClass23 r016 = new OnDragListener() {
                public boolean onDrag(View view, DragEvent dragEvent) {
                    if (!MainActivity.this.userEdittingUnlocked) {
                        return false;
                    }
                    switch (dragEvent.getAction()) {
                        case 1:
                            return true;
                        case 2:
                            MainActivity.this.checkIfScrollNeeded(view, dragEvent);
                            return true;
                        case 3:
                            LinkedList<Light> groupToRemove = null;
                            boolean afterFirstGroup = false;
                            ArrayList<Integer> groupNumberList = new ArrayList<>();
                            Iterator it = MainActivity.this.groups.iterator();
                            while (it.hasNext()) {
                                LinkedList<Light> group = (LinkedList) it.next();
                                group.remove(MainActivity.this.lightForGrouping);
                                if (afterFirstGroup && group.isEmpty()) {
                                    groupToRemove = group;
                                } else if (!group.isEmpty()) {
                                    groupNumberList.add(Integer.valueOf(((Light) group.get(0)).getGroupNumber()));
                                }
                                afterFirstGroup = true;
                            }
                            if (!groupNumberList.contains(Integer.valueOf(0))) {
                                groupNumberList.add(Integer.valueOf(0));
                            }
                            if (groupToRemove != null) {
                                MainActivity.this.groups.remove(groupToRemove);
                            }
                            int emptyIndex = -1;
                            if (!groupNumberList.isEmpty()) {
                                Collections.sort(groupNumberList);
                                int i = 0;
                                while (true) {
                                    if (i < groupNumberList.size() - 1) {
                                        if (((Integer) groupNumberList.get(i)).intValue() + 1 != ((Integer) groupNumberList.get(i + 1)).intValue()) {
                                            emptyIndex = ((Integer) groupNumberList.get(i)).intValue() + 1;
                                        } else {
                                            i++;
                                        }
                                    }
                                }
                            }
                            if (emptyIndex == -1) {
                                emptyIndex = ((Integer) groupNumberList.get(groupNumberList.size() - 1)).intValue() + 1;
                            }
                            Log.i(MainActivity.tag, emptyIndex + " empty index     groupNumberList " + groupNumberList);
                            LinkedList<Light> newGroup = new LinkedList<>();
                            MainActivity.this.lightForGrouping.setGroupName("New Group", false);
                            MainActivity.this.lightForGrouping.setGroupNumber(emptyIndex, false);
                            newGroup.add(MainActivity.this.lightForGrouping);
                            MainActivity.this.groups.add(newGroup);
                            if (MainActivity.this.currentLight == MainActivity.this.lightForGrouping && MainActivity.this.broadcastingToGroup) {
                                MainActivity.this.setToolbarTitle();
                            }
                            MainActivity.this.redrawDrawerLinearLayout();
                            MainActivity.this.highlightDrawerSelection();
                            if ((MainActivity.this.currentLight instanceof SideKick) || (MainActivity.this.currentLight instanceof Connect)) {
                                Fragment currentFragment = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                                if (currentFragment instanceof DreamScreenFragment) {
                                    ((DreamScreenFragment) currentFragment).redrawFragment();
                                }
                            }
                            return true;
                        case 4:
                            return true;
                        case 5:
                            return true;
                        case 6:
                            return true;
                        default:
                            return true;
                    }
                }
            };
            drawerAddNewGroup.setOnDragListener(r016);
        }
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams13 = new RelativeLayout.LayoutParams(-1, -1);
        relativeLayout.setLayoutParams(layoutParams13);
        relativeLayout.setVerticalGravity(80);
        relativeLayout.setPadding(pxToDp(8), pxToDp(16), pxToDp(8), pxToDp(16));
        this.drawerLinearLayout.addView(relativeLayout);
        final RelativeLayout relativeLayout2 = relativeLayout;
        AnonymousClass24 r017 = new OnClickListener() {
            public void onClick(View view) {
                ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(relativeLayout2.getWindowToken(), 0);
            }
        };
        relativeLayout.setOnClickListener(r017);
        LinearLayout edittingLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams layoutParams14 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams14.addRule(20);
        edittingLayout.setLayoutParams(layoutParams14);
        edittingLayout.setOrientation(1);
        edittingLayout.setGravity(1);
        relativeLayout.addView(edittingLayout);
        ImageButton imageButton = new ImageButton(this);
        imageButton.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        if (this.userEdittingUnlocked) {
            imageButton.setImageResource(R.drawable.ic_lock_open_white_36dp);
        } else {
            imageButton.setImageResource(R.drawable.ic_lock_outline_white_36dp);
        }
        imageButton.setBackgroundColor(0);
        AnonymousClass25 r018 = new OnClickListener() {
            public void onClick(View view) {
                if (!MainActivity.this.groups.isEmpty()) {
                    Log.i(MainActivity.tag, "userEdittingLockButton");
                    MainActivity.this.userEdittingUnlocked = !MainActivity.this.userEdittingUnlocked;
                    MainActivity.this.redrawDrawerLinearLayout();
                    MainActivity.this.highlightDrawerSelection();
                    ((InputMethodManager) MainActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(MainActivity.this.drawerLinearLayout.getWindowToken(), 0);
                }
            }
        };
        imageButton.setOnClickListener(r018);
        edittingLayout.addView(imageButton);
        TextView edittingText = new TextView(this);
        edittingText.setLayoutParams(new ViewGroup.LayoutParams(pxToDp(75), -2));
        edittingText.setTextSize(this.drawerTextSize);
        edittingText.setTypeface(this.font);
        edittingText.setTextColor(secondaryTextColor);
        edittingText.setGravity(1);
        edittingText.setText("Edit");
        edittingLayout.addView(edittingText);
        LinearLayout addNewLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams layoutParams15 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams15.addRule(21);
        addNewLayout.setLayoutParams(layoutParams15);
        addNewLayout.setOrientation(1);
        addNewLayout.setGravity(1);
        relativeLayout.addView(addNewLayout);
        ImageButton addDeviceButton = new ImageButton(this);
        addDeviceButton.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        addDeviceButton.setImageResource(R.drawable.ic_add_circle_outline_white_36dp);
        addDeviceButton.setBackgroundColor(0);
        addDeviceButton.setAdjustViewBounds(true);
        AnonymousClass26 r019 = new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.drawerLayout.closeDrawers();
                Fragment currentFragment = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                if ((currentFragment instanceof NewDeviceFragment) || ((currentFragment instanceof FirmwareUpdateFragment) && ((FirmwareUpdateFragment) currentFragment).isUpdating())) {
                    Log.i(MainActivity.tag, "dont let user start newDeviceFragment if currently NewDeviceFragment or FirmwareUpdateFragment");
                    return;
                }
                MainActivity.this.groups = new LinkedList();
                MainActivity.this.currentLight = null;
                MainActivity.this.broadcastingToGroup = false;
                MainActivity.this.lightForGrouping = null;
                MainActivity.this.toolbarTitle.setText("");
                MainActivity.this.toolbarGroupIcon.setVisibility(8);
                MainActivity.this.redrawDrawerLinearLayout();
                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    fragmentManager.popBackStack();
                }
                fragmentManager.beginTransaction().replace(R.id.frameLayout, NewDeviceFragment.newInstance()).commitAllowingStateLoss();
                MainActivity.this.handler.postDelayed(new Runnable() {
                    public void run() {
                        MainActivity.this.hideToolbarButtons();
                    }
                }, 50);
            }
        };
        addDeviceButton.setOnClickListener(r019);
        addNewLayout.addView(addDeviceButton);
        TextView addNewText = new TextView(this);
        addNewText.setLayoutParams(new ViewGroup.LayoutParams(pxToDp(75), -2));
        addNewText.setTextSize(this.drawerTextSize);
        addNewText.setTypeface(this.font);
        addNewText.setTextColor(secondaryTextColor);
        addNewText.setGravity(17);
        addNewText.setText(getResources().getString(R.string.AddNew));
        addNewLayout.addView(addNewText);
    }

    /* access modifiers changed from: private */
    public void highlightDrawerSelection() {
        if (this.groups.isEmpty()) {
            Log.i(tag, "highlightDrawerSelection, groups empty, returning");
            return;
        }
        if (this.currentLight == null) {
            Iterator it = this.groups.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                LinkedList<Light> group = (LinkedList) it.next();
                if (!group.isEmpty() && ((Light) group.getFirst()).getGroupNumber() != 0) {
                    this.currentLight = (Light) group.getFirst();
                    this.broadcastingToGroup = true;
                    startDreamScreenFragment();
                    break;
                }
            }
            if (this.currentLight == null && !((LinkedList) this.groups.getFirst()).isEmpty()) {
                this.currentLight = (Light) ((LinkedList) this.groups.getFirst()).getFirst();
                this.broadcastingToGroup = false;
                startDreamScreenFragment();
            }
            if (this.currentLight == null) {
                Log.i(tag, "highlightDrawerSelection currentlight is null, not highlighting");
                return;
            } else if (this.reattemptJumpToWidgetSettingsFlag) {
                this.reattemptJumpToWidgetSettingsFlag = false;
                attemptToJumpToWidgetSettings();
            }
        }
        if (this.currentLight.getGroupNumber() == 0 && this.broadcastingToGroup) {
            Log.i(tag, "highlightDrawer, currentlight in unassigned was broadcasting to group, setting false ");
            this.broadcastingToGroup = false;
        }
        for (int i = 0; i < this.drawerLinearLayout.getChildCount(); i++) {
            ViewGroup drawerEntry = (ViewGroup) this.drawerLinearLayout.getChildAt(i);
            if (drawerEntry.getTag() != null) {
                if (drawerEntry.getTag() instanceof Light) {
                    if (drawerEntry.getTag() == this.currentLight && !this.broadcastingToGroup) {
                        drawerEntry.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDrawerItemSelected));
                    }
                } else if (((LinkedList) drawerEntry.getTag()).contains(this.currentLight) && this.broadcastingToGroup) {
                    drawerEntry.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDrawerItemSelected));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean setLightForGroupingUnassigned() {
        Log.i(tag, "setLightForGroupingUnassigned");
        if (this.groups.isEmpty()) {
            this.groups.add(new LinkedList());
        }
        if (this.lightForGrouping.getGroupNumber() == 0) {
            Log.i(tag, "already unassigned, dont do anything");
        } else {
            LinkedList<Light> groupToRemove = null;
            boolean afterFirstGroup = false;
            Iterator it = this.groups.iterator();
            while (it.hasNext()) {
                LinkedList<Light> group = (LinkedList) it.next();
                group.remove(this.lightForGrouping);
                if (afterFirstGroup && group.isEmpty()) {
                    groupToRemove = group;
                }
                afterFirstGroup = true;
            }
            if (groupToRemove != null) {
                Log.i(tag, "removed group ");
                this.groups.remove(groupToRemove);
            }
            this.lightForGrouping.setGroupName("unassigned", false);
            this.lightForGrouping.setGroupNumber(0, false);
            if (!((LinkedList) this.groups.get(0)).isEmpty() && ((Light) ((LinkedList) this.groups.get(0)).get(0)).getGroupNumber() != 0) {
                this.groups.addFirst(new LinkedList());
            }
            if (this.lightForGrouping instanceof DreamScreen) {
                ((LinkedList) this.groups.getFirst()).addFirst(this.lightForGrouping);
            } else {
                ((LinkedList) this.groups.getFirst()).add(this.lightForGrouping);
            }
            if (this.currentLight == this.lightForGrouping && this.broadcastingToGroup) {
                this.broadcastingToGroup = false;
                setToolbarTitle();
            }
            redrawDrawerLinearLayout();
            highlightDrawerSelection();
            if ((this.currentLight instanceof SideKick) || (this.currentLight instanceof Connect)) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                if (currentFragment instanceof DreamScreenFragment) {
                    ((DreamScreenFragment) currentFragment).redrawFragment();
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean setLightForGroupingToGroup(LinkedList<Light> group) {
        Log.i(tag, "setLightForGroupingToGroup");
        if (group.contains(this.lightForGrouping)) {
            Log.i(tag, "dropped into its own group, dont do anything");
        } else {
            if ((this.lightForGrouping instanceof DreamScreen) && ((Light) group.get(0)).getGroupNumber() != 0) {
                Iterator it = group.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((Light) it.next()) instanceof DreamScreen) {
                            Toast.makeText(this, "Warning, multiple DreamScreens in the same group may cause unexpected behavior.", 1).show();
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            Iterator it2 = this.groups.iterator();
            while (it2.hasNext()) {
                ((LinkedList) it2.next()).remove(this.lightForGrouping);
            }
            this.lightForGrouping.setGroupName(((Light) group.get(0)).getGroupName(), false);
            this.lightForGrouping.setGroupNumber(((Light) group.get(0)).getGroupNumber(), false);
            if (this.lightForGrouping instanceof DreamScreen) {
                group.addFirst(this.lightForGrouping);
            } else {
                group.add(this.lightForGrouping);
            }
            if (this.currentLight == this.lightForGrouping && this.broadcastingToGroup) {
                setToolbarTitle();
            }
            for (int i = 0; i < this.groups.size(); i++) {
                if (i != 0 && ((LinkedList) this.groups.get(i)).isEmpty()) {
                    this.groups.remove(i);
                    Log.i(tag, "removed group");
                }
            }
            redrawDrawerLinearLayout();
            highlightDrawerSelection();
            if ((this.currentLight instanceof SideKick) || (this.currentLight instanceof Connect)) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                if (currentFragment instanceof DreamScreenFragment) {
                    Log.i(tag, "redrawing currentLight sidekick dreamscreenfragment");
                    ((DreamScreenFragment) currentFragment).redrawFragment();
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void startDreamScreenFragment() {
        if (this.currentLight != null) {
            try {
                Log.i(tag, "startDreamScreenFragment");
                FragmentManager fragmentManager = getSupportFragmentManager();
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    fragmentManager.popBackStack();
                }
                fragmentManager.beginTransaction().replace(R.id.frameLayout, DreamScreenFragment.newInstance()).commitAllowingStateLoss();
                setToolbarTitle();
            } catch (IllegalStateException e) {
                Log.i(tag, "startSplashActivity, IllegalStateException " + e.toString());
                restartApp();
            }
        } else {
            Log.e(tag, "startDreamScreenFragment currentLight is null");
        }
    }

    /* access modifiers changed from: private */
    public void setToolbarTitle() {
        Log.i(tag, "set toolbar title");
        this.toolbarGroupIcon.animate().alpha(0.0f).setDuration(150).setListener(null);
        this.toolbarTitle.animate().alpha(0.0f).setDuration(150).setListener(new AnimatorListener() {
            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (MainActivity.this.broadcastingToGroup) {
                    MainActivity.this.toolbarTitle.setText(MainActivity.this.currentLight.getGroupName());
                } else {
                    MainActivity.this.toolbarTitle.setText(MainActivity.this.currentLight.getName());
                }
                if (MainActivity.this.toolbarTitle.getLineCount() == 1) {
                    MainActivity.this.toolbarTitle.setTextSize(MainActivity.this.toolbarTitleSize);
                } else if (MainActivity.this.toolbarTitle.getLineCount() > 1) {
                    MainActivity.this.toolbarTitle.setTextSize(MainActivity.this.toolbarTitleSize - 6.0f);
                }
                MainActivity.this.toolbarTitle.animate().alpha(1.0f).setDuration(150).setListener(null);
                if (MainActivity.this.broadcastingToGroup) {
                    MainActivity.this.toolbarGroupIcon.setVisibility(0);
                    MainActivity.this.toolbarGroupIcon.animate().alpha(1.0f).setDuration(150).setListener(null);
                    return;
                }
                MainActivity.this.toolbarGroupIcon.setVisibility(8);
            }

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Log.i(tag, "onResume");
        handlePing();
        if (ControlWidget.getPreferenceInt(getApplicationContext(), "redirect_widget_settings") == 1) {
            Log.i(tag, "redirect_widget_settings 1, redirect to widget settings");
            ControlWidget.setPreferenceInt(getApplicationContext(), "redirect_widget_settings", 0);
            attemptToJumpToWidgetSettings();
        }
    }

    private void attemptToJumpToWidgetSettings() {
        if (this.currentLight == null) {
            Log.i(tag, "attemptToJumpToWidgetSettings but currentLight == null, canceling. reattemptJumpToWidgetSettingsFlag true");
            this.reattemptJumpToWidgetSettingsFlag = true;
        } else if (!(this.currentLight instanceof DreamScreen)) {
            Log.i(tag, "attemptToJumpToWidgetSettings but currentLight not a dreamscreen, canceling. reattemptJumpToWidgetSettingsFlag false");
            this.reattemptJumpToWidgetSettingsFlag = false;
        } else {
            Log.i(tag, "attemptToJumpToWidgetSettings");
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayout);
            if (currentFragment == null) {
                Log.i(tag, "currentFrgment is null, error");
            } else if (((currentFragment instanceof FirmwareUpdateFragment) && ((FirmwareUpdateFragment) currentFragment).isUpdating()) || (currentFragment instanceof NewDeviceFragment)) {
            } else {
                if (currentFragment instanceof AdvancedSettingsFragment) {
                    ((AdvancedSettingsFragment) currentFragment).scrollToWidgetSettings();
                    return;
                }
                AdvancedSettingsFragment advancedSettingsFragment = AdvancedSettingsFragment.newInstance();
                advancedSettingsFragment.setFlagToScrollToWidgetSettings(true);
                fragmentManager.beginTransaction().replace(R.id.frameLayout, advancedSettingsFragment).addToBackStack(null).commitAllowingStateLoss();
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        MainActivity.this.setBackArrowIcon();
                    }
                }, 200);
            }
        }
    }

    private void handlePing() {
        Log.i(tag, "handlePing");
        if (this.currentLight != null) {
            if (this.currentLight instanceof DreamScreen) {
                if (((DreamScreen) this.currentLight).isDemo()) {
                    return;
                }
            } else if (this.currentLight instanceof SideKick) {
                if (((SideKick) this.currentLight).isDemo()) {
                    return;
                }
            } else if (!(this.currentLight instanceof Connect)) {
                Log.i(tag, "error, currentLight bad type");
                return;
            } else if (((Connect) this.currentLight).isDemo()) {
                return;
            }
            if (SystemClock.elapsedRealtime() - this.pingSentAtTime >= pingInterval) {
                this.pingSentAtTime = SystemClock.elapsedRealtime();
                Log.i(tag, "onResume, pinging currentLight");
                this.lightToPing = this.currentLight;
                if (VERSION.SDK_INT >= 23) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
                    Network[] allNetworks = connectivityManager.getAllNetworks();
                    int length = allNetworks.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        Network network = allNetworks[i];
                        if (connectivityManager.getNetworkInfo(network) != null && connectivityManager.getNetworkInfo(network).getType() == 1) {
                            connectivityManager.bindProcessToNetwork(network);
                            Log.i(tag, "android M bound to wifinetwork " + network.toString());
                            break;
                        }
                        i++;
                    }
                }
                initPhonesIp();
                initBroadcastIp();
                this.udpListener.setIpAddressString(this.ipAddressString);
                this.udpListener.setBroadcastIpString(this.broadcastIpString);
                this.udpListener.stopListening();
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        if (MainActivity.this.udpListener != null) {
                            MainActivity.this.udpListener.startListening();
                        }
                    }
                }, 100);
                this.lightToPing.sendPing();
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        if (MainActivity.this.lightToPing != null) {
                            MainActivity.this.lightToPing.sendPing();
                            MainActivity.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    if (MainActivity.this.lightToPing != null) {
                                        MainActivity.this.lightToPing.sendPing();
                                    }
                                }
                            }, 300);
                        }
                    }
                }, 300);
                if (this.pingTimeoutRunnable == null) {
                    this.pingTimeoutRunnable = new Runnable() {
                        public void run() {
                            if (MainActivity.this.lightToPing != null) {
                                Log.i(MainActivity.tag, "pingTimeoutRunnable, no response within 1s");
                                MainActivity.this.lightToPing = null;
                                if (MainActivity.this.bootloadingDeviceDialog == null || !MainActivity.this.bootloadingDeviceDialog.isShowing()) {
                                    Fragment currentFragment = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                                    if (!(currentFragment instanceof FirmwareUpdateFragment) || !((FirmwareUpdateFragment) currentFragment).isUpdating()) {
                                        MainActivity.this.restart();
                                    } else {
                                        Log.i(MainActivity.tag, "dont do anything while firmware updating");
                                    }
                                }
                            }
                        }
                    };
                }
                this.handler.postDelayed(this.pingTimeoutRunnable, 1000);
            }
        }
    }

    public void pingReceived(Light lightSender) {
        if (this.lightToPing != null && lightSender == this.lightToPing) {
            Log.i(tag, "pingReceived, remove pingTimeoutRunnable");
            this.handler.removeCallbacks(this.pingTimeoutRunnable);
            this.lightToPing = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (this.actionBarDrawerToggle != null) {
            this.actionBarDrawerToggle.syncState();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        if (this.udpListener != null) {
            this.udpListener.stopListening();
        }
        super.onDestroy();
    }

    @Nullable
    public Light getLightFromIp(InetAddress ipAddress) {
        Iterator it = this.groups.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((LinkedList) it.next()).iterator();
            while (true) {
                if (it2.hasNext()) {
                    Light light = (Light) it2.next();
                    if (ipAddress.getHostAddress().equals(light.getIp())) {
                        return light;
                    }
                }
            }
        }
        return null;
    }

    public void insertNewLight(Light newLight) {
        Log.i(tag, "insertNewLight");
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (!(currentFragment instanceof NewDeviceFragment)) {
            boolean updateDrawer = true;
            if ((currentFragment instanceof SplashFragment) && ((SplashFragment) currentFragment).isSearching) {
                updateDrawer = false;
            }
            if (this.groups.isEmpty()) {
                this.groups.add(new LinkedList());
            }
            Iterator it = this.groups.iterator();
            while (it.hasNext()) {
                Iterator it2 = ((LinkedList) it.next()).iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (((Light) it2.next()).getLightsUnicastIP().getHostAddress().equals(newLight.getLightsUnicastIP().getHostAddress())) {
                            Log.i(tag, "insertNewLight ip already added, cancelling");
                            return;
                        }
                    }
                }
            }
            boolean lightAdded = false;
            Iterator it3 = this.groups.iterator();
            while (it3.hasNext()) {
                LinkedList<Light> group = (LinkedList) it3.next();
                if (!lightAdded && !group.isEmpty() && ((Light) group.get(0)).getGroupNumber() == newLight.getGroupNumber()) {
                    if (newLight instanceof DreamScreen) {
                        group.addFirst(newLight);
                    } else {
                        group.add(newLight);
                    }
                    lightAdded = true;
                }
            }
            if (!lightAdded) {
                if (newLight.getGroupNumber() == 0) {
                    ((LinkedList) this.groups.get(0)).add(newLight);
                } else {
                    LinkedList<Light> newGroup = new LinkedList<>();
                    newGroup.add(newLight);
                    this.groups.add(newGroup);
                }
            }
            if (updateDrawer) {
                redrawDrawerLinearLayout();
                highlightDrawerSelection();
            }
        }
    }

    public void setLightToGroup(Light lightSender, int newGroupInt) {
        boolean lightSenderMoved = false;
        Iterator it = this.groups.iterator();
        while (it.hasNext()) {
            LinkedList<Light> group = (LinkedList) it.next();
            group.remove(lightSender);
            if (!group.isEmpty() && ((Light) group.getFirst()).getGroupNumber() == newGroupInt) {
                lightSender.initGroupNumber(newGroupInt);
                if (lightSender instanceof DreamScreen) {
                    group.addFirst(lightSender);
                } else {
                    group.add(lightSender);
                }
                lightSenderMoved = true;
            }
        }
        if (!lightSenderMoved) {
            lightSender.initGroupNumber(newGroupInt);
            if (newGroupInt == 0) {
                ((LinkedList) this.groups.getFirst()).add(lightSender);
            } else {
                LinkedList<Light> newGroup = new LinkedList<>();
                newGroup.add(lightSender);
                this.groups.add(newGroup);
            }
        }
        for (int i = 0; i < this.groups.size(); i++) {
            if (i != 0 && ((LinkedList) this.groups.get(i)).isEmpty()) {
                this.groups.remove(i);
            }
        }
    }

    public void updateLightsInGroup(int groupNumber, byte[] received) {
        Log.i(tag, "updateLightsInGroup groupNumber " + groupNumber);
        Iterator it = this.groups.iterator();
        while (it.hasNext()) {
            LinkedList<Light> group = (LinkedList) it.next();
            if (!group.isEmpty() && ((Light) group.getFirst()).getGroupNumber() == groupNumber) {
                Iterator it2 = group.iterator();
                while (it2.hasNext()) {
                    Light light = (Light) it2.next();
                    if (this.udpListener != null) {
                        this.udpListener.updateLightState(light, received);
                    }
                }
                return;
            }
        }
    }

    public void lightUpdatedRefreshUi(Light lightSender, boolean drawerChanged, boolean isGroupBroadcast) {
        if (drawerChanged) {
            Log.i(tag, "lightUpdatedRefreshUi, redrawDrawer, highlight, set title+groupicon");
            redrawDrawerLinearLayout();
            highlightDrawerSelection();
            setToolbarTitle();
        }
        if (this.currentLight == lightSender) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            if (currentFragment instanceof DreamScreenFragment) {
                ((DreamScreenFragment) currentFragment).dataChanged();
            }
        }
    }

    public void newDeviceReceived(String apName) {
        if (!this.newDevices.contains(apName)) {
            Log.i(tag, "newDeviceReceived :" + apName + ":");
            this.newDevices.add(apName);
        }
    }

    public void espConnectedToWiFi(boolean didConnect) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (currentFragment instanceof NewDeviceFragment) {
            ((NewDeviceFragment) currentFragment).espConnectedToWiFi(didConnect);
        }
    }

    public void bootloaderFlagsReceived(Light lightSender, byte[] bootloaderKey) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if ((currentFragment instanceof FirmwareUpdateFragment) && lightSender == this.currentLight) {
            ((FirmwareUpdateFragment) currentFragment).bootloaderFlagsReceived(((DreamScreen) this.currentLight).areFlagsInBootloaderMode(bootloaderKey));
        }
    }

    public void deviceInBootloaderModeReceived(final Light lightSender, final int picIsInBootloaderMode) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (currentFragment instanceof FirmwareUpdateFragment) {
            if (lightSender == this.currentLight) {
                ((FirmwareUpdateFragment) currentFragment).deviceInBootloaderModeReceived(picIsInBootloaderMode);
            }
        } else if (picIsInBootloaderMode != 1) {
        } else {
            if (this.bootloadingDeviceDialog == null || !this.bootloadingDeviceDialog.isShowing()) {
                Log.i(tag, "displaying bootloadingDeviceDialog");
                View bootloadingDeviceView = getLayoutInflater().inflate(R.layout.bootloading_device_found_dialog, null);
                Builder alertDialogBuilder = new Builder(this);
                alertDialogBuilder.setView(bootloadingDeviceView).setCancelable(false);
                this.bootloadingDeviceDialog = alertDialogBuilder.create();
                this.bootloadingDeviceDialog.show();
                ((TextView) bootloadingDeviceView.findViewById(R.id.header)).setTypeface(this.font);
                TextView subHeader = (TextView) bootloadingDeviceView.findViewById(R.id.subHeader);
                subHeader.setTypeface(this.font);
                subHeader.setText((getResources().getString(R.string.UpdatingDeviceFound1) + lightSender.getName()) + getResources().getString(R.string.UpdatingDeviceFound2));
                TextView okText = (TextView) bootloadingDeviceView.findViewById(R.id.addText);
                okText.setTypeface(this.font);
                okText.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (MainActivity.this.bootloadingDeviceDialog != null && MainActivity.this.bootloadingDeviceDialog.isShowing()) {
                            MainActivity.this.currentLight = lightSender;
                            MainActivity.this.broadcastingToGroup = false;
                            MainActivity.this.redrawDrawerLinearLayout();
                            MainActivity.this.highlightDrawerSelection();
                            MainActivity.this.setBackArrowIcon();
                            MainActivity.this.drawerLayout.closeDrawers();
                            MainActivity.this.setToolbarTitle();
                            final Fragment currentFragment = FirmwareUpdateFragment.newInstance();
                            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, currentFragment).commitAllowingStateLoss();
                            MainActivity.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    MainActivity.this.bootloadingDeviceDialog.dismiss();
                                    ((FirmwareUpdateFragment) currentFragment).resumePicUpdating(picIsInBootloaderMode);
                                }
                            }, 100);
                        }
                    }
                });
            }
        }
    }

    public void picVersionNumberReceived(Light lightSender, byte[] newPicVersionNumber) {
        if (lightSender == this.currentLight) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            if (currentFragment instanceof FirmwareUpdateFragment) {
                ((FirmwareUpdateFragment) currentFragment).picVersionNumberReceived(newPicVersionNumber);
            }
        }
    }

    public void diagnosticDataReceived(Light lightSender, byte[] diagnosticPayload) {
        if (lightSender == this.currentLight) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            if (currentFragment instanceof FirmwareUpdateFragment) {
                ((FirmwareUpdateFragment) currentFragment).diagnosticsReceived(diagnosticPayload);
            }
        }
    }

    public void deviceInBadBootStateReceived(Light lightSender) {
        if (!this.badDeviceDialogShown) {
            this.badDeviceDialogShown = true;
            if (this.badDeviceDialog == null || !this.badDeviceDialog.isShowing()) {
                View badDeviceView = getLayoutInflater().inflate(R.layout.bad_device_found_dialog, null);
                Builder alertDialogBuilder = new Builder(this);
                alertDialogBuilder.setView(badDeviceView).setCancelable(false);
                this.badDeviceDialog = alertDialogBuilder.create();
                this.badDeviceDialog.show();
                ((TextView) badDeviceView.findViewById(R.id.subHeader)).setText((getResources().getString(R.string.BadDeviceFound1) + lightSender.getName()) + getResources().getString(R.string.BadDeviceFound2));
                ((TextView) badDeviceView.findViewById(R.id.addText)).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        MainActivity.this.badDeviceDialog.dismiss();
                    }
                });
            }
        }
    }

    public void onIrCommandReceived(Light lightSender, int lastIrCommandReceived) {
        if (lightSender == this.currentLight) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            if (currentFragment instanceof RemoteSettingsFragment) {
                ((RemoteSettingsFragment) currentFragment).onIrCommandReceived(lastIrCommandReceived);
            }
        }
    }

    public void wifiNetworkChanged() {
        Log.i(tag, "wifiNetworkChanged");
        initPhonesIp();
        initBroadcastIp();
        this.udpListener.setIpAddressString(this.ipAddressString);
        this.udpListener.setBroadcastIpString(this.broadcastIpString);
        this.udpListener.stopListening();
        this.handler.postDelayed(new Runnable() {
            public void run() {
                if (MainActivity.this.udpListener != null) {
                    MainActivity.this.udpListener.startListening();
                }
            }
        }, 100);
    }

    public boolean finishedSearching() {
        this.badDeviceDialogShown = false;
        Iterator it = this.groups.iterator();
        while (it.hasNext()) {
            if (!((LinkedList) it.next()).isEmpty()) {
                redrawDrawerLinearLayout();
                highlightDrawerSelection();
                return true;
            }
        }
        return false;
    }

    public void startNewDeviceFragment() {
        startNewDeviceFragmentF();
    }

    private void startNewDeviceFragmentF() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (!(currentFragment instanceof FirmwareUpdateFragment) || !((FirmwareUpdateFragment) currentFragment).isUpdating()) {
            this.groups = new LinkedList<>();
            this.currentLight = null;
            this.broadcastingToGroup = false;
            this.lightForGrouping = null;
            this.toolbarTitle.setText("");
            this.toolbarGroupIcon.setVisibility(8);
            redrawDrawerLinearLayout();
            FragmentManager fragmentManager = getSupportFragmentManager();
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }
            fragmentManager.beginTransaction().replace(R.id.frameLayout, NewDeviceFragment.newInstance()).commitAllowingStateLoss();
            this.drawerLayout.closeDrawers();
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    MainActivity.this.hideToolbarButtons();
                }
            }, 50);
            return;
        }
        Log.i(tag, "dont do anything while firmware updating");
    }

    public void addLights(LinkedList<Light> lights) {
        this.groups = new LinkedList<>();
        this.groups.add(new LinkedList());
        Iterator it = lights.iterator();
        while (it.hasNext()) {
            Light light = (Light) it.next();
            boolean lightAdded = false;
            Iterator it2 = this.groups.iterator();
            while (it2.hasNext()) {
                LinkedList<Light> group = (LinkedList) it2.next();
                if (!lightAdded && !group.isEmpty() && ((Light) group.get(0)).getGroupNumber() == light.getGroupNumber()) {
                    group.add(light);
                    lightAdded = true;
                }
            }
            if (!lightAdded) {
                if (light.getGroupNumber() == 0) {
                    ((LinkedList) this.groups.get(0)).add(light);
                } else {
                    LinkedList<Light> newGroup = new LinkedList<>();
                    newGroup.add(light);
                    this.groups.add(newGroup);
                }
            }
        }
        redrawDrawerLinearLayout();
        highlightDrawerSelection();
    }

    public String getBroadcastIpString() {
        return this.broadcastIpString;
    }

    public String getDeviceIpString() {
        return this.ipAddressString;
    }

    public void setMode(int mode) {
        if (this.currentLight == null) {
            Log.i(tag, "setMode but currentLight null, canceling");
            return;
        }
        this.currentLight.setMode(mode, this.broadcastingToGroup);
        if (this.broadcastingToGroup) {
            Iterator it = this.groups.iterator();
            while (it.hasNext()) {
                LinkedList<Light> group = (LinkedList) it.next();
                if (!group.isEmpty() && ((Light) group.getFirst()).getGroupNumber() == this.currentLight.getGroupNumber()) {
                    Iterator it2 = group.iterator();
                    while (it2.hasNext()) {
                        ((Light) it2.next()).initMode(this.currentLight.getMode());
                    }
                    return;
                }
            }
        }
    }

    public void setBrightness(int brightness) {
        this.currentLight.setBrightness(brightness, this.broadcastingToGroup);
        if (this.broadcastingToGroup) {
            Iterator it = this.groups.iterator();
            while (it.hasNext()) {
                LinkedList<Light> group = (LinkedList) it.next();
                if (!group.isEmpty() && ((Light) group.getFirst()).getGroupNumber() == this.currentLight.getGroupNumber()) {
                    Iterator it2 = group.iterator();
                    while (it2.hasNext()) {
                        ((Light) it2.next()).initBrightness(this.currentLight.getBrightness());
                    }
                    return;
                }
            }
        }
    }

    public void setBrightnessConstant(int brightness) {
        if (this.broadcastingToGroup) {
            this.currentLight.setBrightness(brightness, this.broadcastingToGroup);
        } else {
            this.currentLight.setBrightnessConstantUnicast(brightness);
        }
    }

    public int getMode() {
        return this.currentLight.getMode();
    }

    public void setAmbientModeType(int ambientModeType) {
        this.currentLight.setAmbientModeType(ambientModeType, this.broadcastingToGroup);
    }

    public int getAmbientModeType() {
        return this.currentLight.getAmbientModeType();
    }

    public int getBrightness() {
        return this.currentLight.getBrightness();
    }

    public void setAmbientColor(byte[] ambientColor) {
        this.currentLight.setAmbientColor(ambientColor, this.broadcastingToGroup);
    }

    public void setAmbientColorConstant(byte[] ambientColor) {
        if (this.broadcastingToGroup) {
            this.currentLight.setAmbientColor(ambientColor, this.broadcastingToGroup);
        } else {
            this.currentLight.setAmbientColorConstantUnicast(ambientColor);
        }
    }

    public void setAmbientShowType(int ambientShowType) {
        this.currentLight.setAmbientShowType(ambientShowType, this.broadcastingToGroup);
    }

    public byte[] getAmbientColor() {
        return this.currentLight.getAmbientColor();
    }

    public int getAmbientShowType() {
        return this.currentLight.getAmbientShowType();
    }

    public void setHdmiInput(int hdmiInput) {
        if (this.currentLight == null) {
            Log.i(tag, "setMode but currentLight null, canceling");
        } else {
            ((DreamScreen) this.currentLight).setHdmiInput(hdmiInput, this.broadcastingToGroup);
        }
    }

    public void setMusicModeSource(int musicModeSource) {
        ((DreamScreen) this.currentLight).setMusicModeSource(musicModeSource, this.broadcastingToGroup);
    }

    public int getMusicModeSource() {
        return ((DreamScreen) this.currentLight).getMusicModeSource();
    }

    public void setHdmiInputName1(byte[] hdmiInputName1) {
        if (this.currentLight != null && (this.currentLight instanceof DreamScreen)) {
            ((DreamScreen) this.currentLight).setHdmiInputName1(hdmiInputName1, this.broadcastingToGroup);
        }
    }

    public void setHdmiInputName2(byte[] hdmiInputName2) {
        if (this.currentLight != null && (this.currentLight instanceof DreamScreen)) {
            ((DreamScreen) this.currentLight).setHdmiInputName2(hdmiInputName2, this.broadcastingToGroup);
        }
    }

    public void setHdmiInputName3(byte[] hdmiInputName3) {
        if (this.currentLight != null && (this.currentLight instanceof DreamScreen)) {
            ((DreamScreen) this.currentLight).setHdmiInputName3(hdmiInputName3, this.broadcastingToGroup);
        }
    }

    public byte[] getHdmiInputName1() {
        return ((DreamScreen) this.currentLight).getHdmiInputName1();
    }

    public byte[] getHdmiInputName2() {
        return ((DreamScreen) this.currentLight).getHdmiInputName2();
    }

    public byte[] getHdmiInputName3() {
        return ((DreamScreen) this.currentLight).getHdmiInputName3();
    }

    public int getHdmiInput() {
        return ((DreamScreen) this.currentLight).getHdmiInput();
    }

    public byte getHdmiActiveChannels() {
        return ((DreamScreen) this.currentLight).getHdmiActiveChannels();
    }

    public void readHdmiInput() {
        ((DreamScreen) this.currentLight).readHdmiInput();
    }

    public void readHdmiActiveChannels() {
        ((DreamScreen) this.currentLight).readHdmiActiveChannels();
    }

    public boolean currentLightGroupedWithDreamScreen() {
        if (this.currentLight == null) {
            return false;
        }
        if (this.currentLight instanceof DreamScreen) {
            return true;
        }
        if (this.currentLight.getGroupNumber() == 0) {
            return false;
        }
        Iterator it = this.groups.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            LinkedList<Light> group = (LinkedList) it.next();
            if (!group.isEmpty() && ((Light) group.getFirst()).getGroupNumber() == this.currentLight.getGroupNumber()) {
                Iterator it2 = group.iterator();
                while (it2.hasNext()) {
                    if (((Light) it2.next()) instanceof DreamScreen) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean currentLightGroupedWithConnect() {
        if (this.currentLight == null) {
            return false;
        }
        if (this.currentLight instanceof Connect) {
            return true;
        }
        if (this.currentLight.getGroupNumber() == 0) {
            return false;
        }
        Iterator it = this.groups.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            LinkedList<Light> group = (LinkedList) it.next();
            if (!group.isEmpty() && ((Light) group.getFirst()).getGroupNumber() == this.currentLight.getGroupNumber()) {
                Iterator it2 = group.iterator();
                while (it2.hasNext()) {
                    if (((Light) it2.next()) instanceof Connect) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public LinkedList<String> getNewDevices() {
        if (this.newDevices.isEmpty()) {
            return this.newDevices;
        }
        if (!this.newDevicesShown) {
            this.newDevicesShown = true;
        } else {
            while (!this.newDevices.isEmpty()) {
                this.newDevices.removeLast();
            }
        }
        return this.newDevices;
    }

    public void openDrawer() {
        this.drawerLayout.openDrawer((int) GravityCompat.START);
    }

    public boolean firmwareUpdateNeeded() {
        if (this.currentLight instanceof DreamScreen) {
            boolean espFirmwareUpdateNeeded = ((DreamScreen) this.currentLight).espFirmwareUpdateNeeded();
            boolean picFirmwareUpdateNeeded = ((DreamScreen) this.currentLight).picFirmwareUpdateNeeded();
            if (espFirmwareUpdateNeeded || picFirmwareUpdateNeeded) {
                return true;
            }
            return false;
        } else if (this.currentLight instanceof SideKick) {
            return ((SideKick) this.currentLight).espFirmwareUpdateNeeded();
        } else {
            if (this.currentLight instanceof Connect) {
                return ((Connect) this.currentLight).espFirmwareUpdateNeeded();
            }
            Log.i(tag, "firmwareUpdateNeeded failed");
            return false;
        }
    }

    public boolean picFirmwareValid() {
        if (this.currentLight instanceof DreamScreen) {
            return ((DreamScreen) this.currentLight).picFirmwareValid();
        }
        return true;
    }

    public void setSaturation(byte[] saturation) {
        this.currentLight.setSaturation(saturation, this.broadcastingToGroup);
    }

    public void setFadeRate(int fadeRate) {
        this.currentLight.setFadeRate(fadeRate, this.broadcastingToGroup);
    }

    public void setMinimumLuminosity(byte[] minimumLuminosity) {
        ((DreamScreen) this.currentLight).setMinimumLuminosity(minimumLuminosity, this.broadcastingToGroup);
    }

    public void setFrameDelay(int frameDelay) {
        ((DreamScreen) this.currentLight).setVideoFrameDelay(frameDelay, this.broadcastingToGroup);
    }

    public void setColorBoost(int colorBoost) {
        ((DreamScreen) this.currentLight).setColorBoost(colorBoost, this.broadcastingToGroup);
    }

    public LinkedList<LinkedList<Light>> getGroups() {
        return this.groups;
    }

    public int getProductId() {
        if (this.currentLight != null) {
            return this.currentLight.getProductId();
        }
        Log.e(tag, "getProductId, from DreamScreenFragment? currentLight == null so restartApp");
        restartApp();
        return 1;
    }

    private void restartApp() {
        Log.e(tag, "restartApp");
        try {
            Context c = getApplicationContext();
            if (c != null) {
                PackageManager pm = c.getPackageManager();
                if (pm != null) {
                    Intent mStartActivity = pm.getLaunchIntentForPackage(c.getPackageName());
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(67108864);
                        ((AlarmManager) c.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(1, System.currentTimeMillis() + 100, PendingIntent.getActivity(c, 696969, mStartActivity, 268435456));
                        System.exit(0);
                        return;
                    }
                    Log.e(tag, "Was not able to restart application, mStartActivity null");
                    return;
                }
                Log.e(tag, "Was not able to restart application, PM null");
                return;
            }
            Log.e(tag, "Was not able to restart application, Context null");
        } catch (Exception e) {
            Log.e(tag, "Was not able to restart application");
        }
    }

    public byte[] getSaturation() {
        return this.currentLight.getSaturation();
    }

    public int getFadeRate() {
        return this.currentLight.getFadeRate();
    }

    public byte[] getMinimumLuminosity() {
        return ((DreamScreen) this.currentLight).getMinimumLuminosity();
    }

    public int getFrameDelay() {
        return ((DreamScreen) this.currentLight).getVideoFrameDelay();
    }

    public int getColorBoost() {
        return ((DreamScreen) this.currentLight).getColorBoost();
    }

    public void setMusicModeType(int musicModeType) {
        ((DreamScreen) this.currentLight).setMusicModeType(musicModeType, this.broadcastingToGroup);
    }

    public void setMusicModeColors(byte[] musicModeColors) {
        ((DreamScreen) this.currentLight).setMusicModeColors(musicModeColors, this.broadcastingToGroup);
    }

    public void setMusicModeWeight(byte[] musicModeWeights) {
        ((DreamScreen) this.currentLight).setMusicModeWeights(musicModeWeights, this.broadcastingToGroup);
    }

    public int getMusicModeType() {
        if (this.currentLight != null) {
            return ((DreamScreen) this.currentLight).getMusicModeType();
        }
        Log.e(tag, "getMusicModeType, currentLight == null, recover by restartApp");
        restartApp();
        return 1;
    }

    public byte[] getMusicModeColors() {
        return ((DreamScreen) this.currentLight).getMusicModeColors();
    }

    public byte[] getMusicModeWeights() {
        return ((DreamScreen) this.currentLight).getMusicModeWeights();
    }

    public byte[] getFlexSetup() {
        if (this.currentLight != null) {
            return ((DreamScreen) this.currentLight).getFlexSetup();
        }
        Log.e(tag, "getFlexSetup, currentLight == null, recover by restartApp");
        restartApp();
        return new byte[0];
    }

    public void setFlexSetup(byte[] flexSetup) {
        ((DreamScreen) this.currentLight).setFlexSetup(flexSetup, this.broadcastingToGroup);
    }

    public int getSkuSetup() {
        return ((DreamScreen) this.currentLight).getSkuSetup();
    }

    public void setSkuSetup(int skuSetup) {
        ((DreamScreen) this.currentLight).setSkuSetup(skuSetup, this.broadcastingToGroup);
    }

    public void setLightForGroupingNull() {
        this.lightForGrouping = null;
    }

    public byte[] getSectorAssignment() {
        return ((SideKick) this.currentLight).getSectorAssignment();
    }

    public void setSectorData(byte[] sectorData) {
        if (this.currentLight instanceof SideKick) {
            ((SideKick) this.currentLight).setSectorData(sectorData, this.broadcastingToGroup);
        } else if (this.currentLight instanceof Connect) {
            ((Connect) this.currentLight).setSectorData(sectorData, this.broadcastingToGroup);
        }
    }

    public void setSectorAssignment(byte[] sectorAssignment) {
        ((SideKick) this.currentLight).setSectorAssignment(sectorAssignment, this.broadcastingToGroup);
    }

    public void setZones(byte zones) {
        ((DreamScreen) this.currentLight).setZones(zones, this.broadcastingToGroup);
    }

    public void setZonesBrightness(byte[] zonesBrightness) {
        ((DreamScreen) this.currentLight).setZonesBrightness(zonesBrightness, this.broadcastingToGroup);
    }

    public byte getZones() {
        if (this.currentLight != null) {
            return ((DreamScreen) this.currentLight).getZones();
        }
        Log.e(tag, "getZones, currentLight == null, recover by restartApp");
        restartApp();
        return 1;
    }

    public byte[] getZonesBrightness() {
        return ((DreamScreen) this.currentLight).getZonesBrightness();
    }

    public int getLetterboxingEnable() {
        if (this.currentLight != null) {
            return ((DreamScreen) this.currentLight).getLetterboxingEnable();
        }
        Log.e(tag, "getLetterboxingEnable, currentLight == null, recover by restartApp");
        restartApp();
        return 1;
    }

    public void setLetterboxingEnable(int letterboxingEnable) {
        ((DreamScreen) this.currentLight).setLetterboxingEnable(letterboxingEnable, this.broadcastingToGroup);
    }

    public int getCecPassthroughEnable() {
        return ((DreamScreen) this.currentLight).getCecPassthroughEnable();
    }

    public void setCecPassthroughEnable(int cecPassthroughEnable) {
        ((DreamScreen) this.currentLight).setCecPassthroughEnable(cecPassthroughEnable, this.broadcastingToGroup);
    }

    public int getCecSwitchingEnable() {
        return ((DreamScreen) this.currentLight).getCecSwitchingEnable();
    }

    public void setCecSwitchingEnable(int cecSwitchingEnable) {
        ((DreamScreen) this.currentLight).setCecSwitchingEnable(cecSwitchingEnable, this.broadcastingToGroup);
    }

    public int getHpdEnable() {
        return ((DreamScreen) this.currentLight).getHpdEnable();
    }

    public void setHpdEnable(int hpdEnable) {
        ((DreamScreen) this.currentLight).setHpdEnable(hpdEnable, this.broadcastingToGroup);
    }

    public int getUsbPowerEnable() {
        return ((DreamScreen) this.currentLight).getUsbPowerEnable();
    }

    public void setUsbPowerEnable(int usbPowerEnable) {
        ((DreamScreen) this.currentLight).setUsbPowerEnable(usbPowerEnable, this.broadcastingToGroup);
    }

    public int getCecPowerEnable() {
        return ((DreamScreen) this.currentLight).getCecPowerEnable();
    }

    public void setCecPowerEnable(int cecPowerEnable) {
        ((DreamScreen) this.currentLight).setCecPowerEnable(cecPowerEnable, this.broadcastingToGroup);
    }

    public int getHdrToneRemapping() {
        return ((DreamScreen) this.currentLight).getHdrToneRemapping();
    }

    public void setHdrToneRemapping(int hdrToneRemapping) {
        ((DreamScreen) this.currentLight).setHdrToneRemapping(hdrToneRemapping, this.broadcastingToGroup);
    }

    public Light getCurrentLight() {
        return this.currentLight;
    }

    public void doFactoryReset() {
        if (this.currentLight != null) {
            this.currentLight.doFactoryReset(false);
        }
        this.groups = new LinkedList<>();
        this.currentLight = null;
        this.broadcastingToGroup = false;
        this.lightForGrouping = null;
        redrawDrawerLinearLayout();
        this.toolbarTitle.setText("");
        this.toolbarGroupIcon.setVisibility(8);
        this.handler.postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.startSplashActivity();
            }
        }, 2000);
    }

    public void startSplashActivity() {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }
            fragmentManager.beginTransaction().replace(R.id.frameLayout, SplashFragment.newInstance()).commitAllowingStateLoss();
            hideToolbarButtons();
        } catch (IllegalStateException e) {
            Log.i(tag, "startSplashActivity, IllegalStateException " + e.toString());
            restartApp();
        }
    }

    public byte[] getEspFirmwareVersion() {
        if (this.currentLight instanceof DreamScreen) {
            return ((DreamScreen) this.currentLight).getEspFirmwareVersion();
        }
        if (this.currentLight instanceof SideKick) {
            return ((SideKick) this.currentLight).getEspFirmwareVersion();
        }
        if (this.currentLight instanceof Connect) {
            return ((Connect) this.currentLight).getEspFirmwareVersion();
        }
        Log.i(tag, "getEspFirmwareVersion failed");
        return new byte[]{0, 0};
    }

    public byte[] getPicFirmwareVersion() {
        return ((DreamScreen) this.currentLight).getPicVersionNumber();
    }

    public boolean espFirmwareUpdateNeeded() {
        if (this.currentLight instanceof DreamScreen) {
            return ((DreamScreen) this.currentLight).espFirmwareUpdateNeeded();
        }
        if (this.currentLight instanceof SideKick) {
            return ((SideKick) this.currentLight).espFirmwareUpdateNeeded();
        }
        if (this.currentLight instanceof Connect) {
            return ((Connect) this.currentLight).espFirmwareUpdateNeeded();
        }
        Log.i(tag, "espFirmwareUpdateNeeded failed");
        return false;
    }

    public boolean picFirmwareUpdateNeeded() {
        return ((DreamScreen) this.currentLight).picFirmwareUpdateNeeded();
    }

    public void restart() {
        Log.i(tag, "restart()");
        this.groups = new LinkedList<>();
        this.currentLight = null;
        this.broadcastingToGroup = false;
        this.lightForGrouping = null;
        redrawDrawerLinearLayout();
        this.toolbarTitle.setText("");
        this.toolbarGroupIcon.setVisibility(8);
        enableBackButton();
        this.badDeviceDialogShown = false;
        startSplashActivity();
    }

    public String getIp() {
        return this.currentLight.getIp();
    }

    public InetAddress getLightsUnicastIP() {
        return this.currentLight.getLightsUnicastIP();
    }

    public void enterBootloaderFlags() {
        ((DreamScreen) this.currentLight).enterBootloaderFlags();
    }

    public void clearBootloaderFlags() {
        ((DreamScreen) this.currentLight).clearBootloaderFlags();
    }

    public void readBootloaderFlags() {
        ((DreamScreen) this.currentLight).readBootloaderFlags();
    }

    public void resetPic() {
        ((DreamScreen) this.currentLight).resetPic();
    }

    public void readBootloaderMode() {
        ((DreamScreen) this.currentLight).readBootloaderMode();
    }

    public void readPicVersionNumber() {
        ((DreamScreen) this.currentLight).readPicVersionNumber();
    }

    public void resetEsp() {
        this.currentLight.resetEsp(false);
    }

    public void stopEsp32Drivers() {
        ((Connect) this.currentLight).stopEsp32Drivers();
    }

    public void readDiagnostics() {
        if (this.currentLight instanceof DreamScreen4K) {
            ((DreamScreen4K) this.currentLight).readDiagnostics();
        } else if (this.currentLight instanceof DreamScreenSolo) {
            ((DreamScreenSolo) this.currentLight).readDiagnostics();
        }
    }

    public int getLightType() {
        if (this.currentLight == null || !(this.currentLight instanceof Connect)) {
            return 0;
        }
        return ((Connect) this.currentLight).getLightType();
    }

    public InetAddress[] getLightIpAddresses() {
        return ((Connect) this.currentLight).getLightIpAddresses();
    }

    public byte[] getLightSectorAssignments() {
        return ((Connect) this.currentLight).getLightSectorAssignments();
    }

    public String getHueBridgeUsername() {
        return ((Connect) this.currentLight).getHueBridgeUsername();
    }

    public byte[] getHueBulbIds() {
        return ((Connect) this.currentLight).getHueBulbIds();
    }

    public byte[] getHueBridgeClientKey() {
        return ((Connect) this.currentLight).getHueBridgeClientKey();
    }

    public int getHueBridgeGroupNumber() {
        return ((Connect) this.currentLight).getHueBridgeGroupNumber();
    }

    public String getHueBridgeGroupName() {
        return ((Connect) this.currentLight).getHueBridgeGroupName();
    }

    public String[] getLightNames() {
        return ((Connect) this.currentLight).getLightNames();
    }

    public void setHueLifxSettings(int lightType, @Nullable InetAddress[] lightIpAddresses, @Nullable byte[] lightSectorAssignments, @Nullable String hueBridgeUsername, @Nullable byte[] hueBulbIds, @Nullable byte[] hueBridgeClientKey, int hueBridgeGroupNumber, @Nullable String[] lightNames, @Nullable String hueBridgeGroupName) {
        ((Connect) this.currentLight).setHueLifxSettings(lightType, lightIpAddresses, lightSectorAssignments, hueBridgeUsername, hueBulbIds, hueBridgeClientKey, hueBridgeGroupNumber, lightNames, hueBridgeGroupName);
    }

    public String getPhoneIpString() {
        return this.ipAddressString;
    }

    public int getIrEnabled() {
        return ((Connect) this.currentLight).getIrEnabled();
    }

    public int getIrLearningMode() {
        return ((Connect) this.currentLight).getIrLearningMode();
    }

    public byte[] getIrManifest() {
        return ((Connect) this.currentLight).getIrManifest();
    }

    public void setIrEnabled(int irEnabled) {
        ((Connect) this.currentLight).setIrEnabled(irEnabled, this.broadcastingToGroup);
    }

    public void setIrLearningMode(int irLearningMode) {
        ((Connect) this.currentLight).setIrLearningMode(irLearningMode, this.broadcastingToGroup);
    }

    public void setIrManifestEntry(byte[] irManifestEntry) {
        ((Connect) this.currentLight).setIrManifestEntry(irManifestEntry, this.broadcastingToGroup);
    }

    public String getEmailAddress() {
        return ((Connect) this.currentLight).getEmailAddress();
    }

    public String getThingName() {
        return ((Connect) this.currentLight).getThingName();
    }

    public void sendCertificates(String certificate, String privateKey) {
        ((Connect) this.currentLight).sendCertificates(certificate, privateKey);
    }

    public void setEmailAddress(String emailAddress) {
        ((Connect) this.currentLight).setEmailAddress(emailAddress, false);
    }

    public void setThingName(String thingName) {
        ((Connect) this.currentLight).setThingName(thingName, false);
    }

    public boolean isHueLifxSettingsReceived() {
        return ((Connect) this.currentLight).isHueLifxSettingsReceived();
    }

    public boolean isEmailReceived() {
        return ((Connect) this.currentLight).isEmailReceived();
    }

    public void readHueLifxSettings() {
        ((Connect) this.currentLight).readHueLifxSettings();
    }

    public void readEmailAddress() {
        ((Connect) this.currentLight).readEmailAddress();
    }

    public void startTutorial() {
        Log.i(tag, "HelpFragment invoked startTutorial");
        if (this.udpListener != null) {
            this.udpListener.stopListening();
        }
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.setFlags(268451840);
        startActivity(intent);
        finish();
    }

    private int pxToDp(int px) {
        return (int) TypedValue.applyDimension(1, (float) px, getResources().getDisplayMetrics());
    }

    private boolean initPhonesIp() {
        String oldIP = this.ipAddressString;
        int ip = this.wifiManager.getConnectionInfo().getIpAddress();
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ip = Integer.reverseBytes(ip);
        }
        try {
            this.ipAddressString = InetAddress.getByAddress(BigInteger.valueOf((long) ip).toByteArray()).getHostAddress();
        } catch (UnknownHostException e) {
            this.ipAddressString = null;
        }
        Log.i(tag, "phones ip:" + this.ipAddressString);
        if (this.ipAddressString == null || !this.ipAddressString.equals(oldIP)) {
            return true;
        }
        return false;
    }

    private boolean initBroadcastIp() {
        String oldIP = this.broadcastIpString;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
                if (!networkInterface.isLoopback()) {
                    Iterator it = networkInterface.getInterfaceAddresses().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        InterfaceAddress interfaceAddress = (InterfaceAddress) it.next();
                        if (interfaceAddress.getBroadcast() != null) {
                            this.broadcastIpString = interfaceAddress.getBroadcast().getHostAddress();
                            break;
                        }
                    }
                }
            }
        } catch (NullPointerException | SocketException e) {
            Log.i(tag, "initBroadcastIp, defaulting to 255.255.255.255, exception " + e.toString());
            try {
                this.broadcastIpString = InetAddress.getByName("255.255.255.255").getHostAddress();
            } catch (UnknownHostException e2) {
            }
        }
        Log.i(tag, "initBroadcastIp " + this.broadcastIpString);
        if (this.broadcastIpString == null || !this.broadcastIpString.equals(oldIP)) {
            return true;
        }
        return false;
    }

    public void onBackPressed() {
        if (this.backButtonEnabled) {
            super.onBackPressed();
        } else if (!this.toastShown) {
            this.toastShown = true;
            Toast.makeText(getApplicationContext(), "Please do not stop the app while it is working.", 0).show();
        }
    }

    public void disableBackButton() {
        Log.i(tag, "disableBackButton");
        this.backButtonEnabled = false;
    }

    public void enableBackButton() {
        Log.i(tag, "enableBackButton");
        this.backButtonEnabled = true;
    }
}

package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;
import com.lab714.dreamscreenv2.devices.Connect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class RemoteSettingsFragment extends Fragment {
    private static final String tag = "RemoteSettingsFrag";
    /* access modifiers changed from: private */
    public RelativeLayout addCustomCommandButtonLayout;
    private OnClickListener addCustomCommandClickListener;
    /* access modifiers changed from: private */
    public AlertDialog addCustomCommandDialog;
    /* access modifiers changed from: private */
    public ImageView check1;
    /* access modifiers changed from: private */
    public ImageView check2;
    /* access modifiers changed from: private */
    public ImageView check3;
    private int checkColor;
    /* access modifiers changed from: private */
    public Spinner commandSpinner;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> commandSpinnerAdapter;
    /* access modifiers changed from: private */
    public LinearLayout customCommandsLayout;
    /* access modifiers changed from: private */
    public Typeface font;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    private OnClickListener harmonyClickedListener;
    private View harmonyDivider;
    private LinearLayout harmonyLayout;
    /* access modifiers changed from: private */
    public final LayoutParams helpLayoutParams = new LayoutParams(-1, -2);
    /* access modifiers changed from: private */
    public TextView helpTextView;
    /* access modifiers changed from: private */
    public int irEnabled = 1;
    /* access modifiers changed from: private */
    public LinearLayout irEnabledLayout;
    private ImageView irEnabledToggle;
    /* access modifiers changed from: private */
    public int irLearningMode = 0;
    /* access modifiers changed from: private */
    public byte[] irManifest = new byte[0];
    /* access modifiers changed from: private */
    public HashMap<Byte, Integer> irManifestMap = new HashMap<>();
    /* access modifiers changed from: private */
    public LinearLayout manifestEntryLayout;
    /* access modifiers changed from: private */
    public HashMap<Integer, Integer> receivedIrCommands = new HashMap<>();
    /* access modifiers changed from: private */
    public RemoteSettingsFragmentListener remoteSettingsFragmentListener;
    /* access modifiers changed from: private */
    public LinearLayout rootLayout;
    /* access modifiers changed from: private */
    public int unselectedCheckColor;

    public interface RemoteSettingsFragmentListener {
        int getIrEnabled();

        int getIrLearningMode();

        byte[] getIrManifest();

        void setIrEnabled(int i);

        void setIrLearningMode(int i);

        void setIrManifestEntry(byte[] bArr);
    }

    public static RemoteSettingsFragment newInstance() {
        return new RemoteSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.irEnabled = this.remoteSettingsFragmentListener.getIrEnabled();
        this.irLearningMode = this.remoteSettingsFragmentListener.getIrLearningMode();
        this.irManifest = this.remoteSettingsFragmentListener.getIrManifest();
        this.unselectedCheckColor = ContextCompat.getColor(getContext(), R.color.unselectedCheck);
        this.checkColor = ContextCompat.getColor(getContext(), R.color.checkHighlighted);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remote_settings, container, false);
        this.font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        ((TextView) view.findViewById(R.id.headerText)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.irEnabledText)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.customCommandsText)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.addText)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.harmonyText1)).setTypeface(this.font);
        ((TextView) view.findViewById(R.id.harmonyText2)).setTypeface(this.font);
        this.rootLayout = (LinearLayout) view.findViewById(R.id.rootLayout);
        this.irEnabledLayout = (LinearLayout) view.findViewById(R.id.irEnabledLayout);
        this.customCommandsLayout = (LinearLayout) view.findViewById(R.id.customCommandsLayout);
        this.manifestEntryLayout = (LinearLayout) view.findViewById(R.id.manifestEntryLayout);
        this.harmonyLayout = (LinearLayout) view.findViewById(R.id.harmonyLayout);
        this.addCustomCommandButtonLayout = (RelativeLayout) view.findViewById(R.id.addCustomCommandButtonLayout);
        this.irEnabledToggle = (ImageView) view.findViewById(R.id.irEnabledToggle);
        this.harmonyDivider = view.findViewById(R.id.harmonyDivider);
        this.harmonyClickedListener = new OnClickListener() {
            public void onClick(View view) {
                Log.i(RemoteSettingsFragment.tag, "harmonyLayout tapped");
                Intent launchIntent = RemoteSettingsFragment.this.getActivity().getPackageManager().getLaunchIntentForPackage("com.logitech.harmonyhub");
                if (launchIntent != null) {
                    RemoteSettingsFragment.this.startActivity(launchIntent);
                }
            }
        };
        this.addCustomCommandClickListener = new OnClickListener() {
            public void onClick(View view) {
                if (RemoteSettingsFragment.this.addCustomCommandDialog == null || !RemoteSettingsFragment.this.addCustomCommandDialog.isShowing()) {
                    RemoteSettingsFragment.this.irLearningMode = 1;
                    if (RemoteSettingsFragment.this.remoteSettingsFragmentListener != null) {
                        RemoteSettingsFragment.this.remoteSettingsFragmentListener.setIrLearningMode(RemoteSettingsFragment.this.irLearningMode);
                    }
                    RemoteSettingsFragment.this.handler.postDelayed(new Runnable() {
                        public void run() {
                            if (RemoteSettingsFragment.this.remoteSettingsFragmentListener != null) {
                                RemoteSettingsFragment.this.remoteSettingsFragmentListener.setIrLearningMode(RemoteSettingsFragment.this.irLearningMode);
                            }
                        }
                    }, 100);
                    View addCustomCommandView = RemoteSettingsFragment.this.getActivity().getLayoutInflater().inflate(R.layout.add_custom_command_dialog, null);
                    ((TextView) addCustomCommandView.findViewById(R.id.text1)).setTypeface(RemoteSettingsFragment.this.font);
                    ((TextView) addCustomCommandView.findViewById(R.id.text2)).setTypeface(RemoteSettingsFragment.this.font);
                    ArrayList<String> unusedCommands = new ArrayList<>();
                    for (int i = 1; i < Connect.IR_COMMANDS.size(); i++) {
                        if (!RemoteSettingsFragment.this.irManifestMap.containsKey(Byte.valueOf((byte) i))) {
                            unusedCommands.add(Connect.IR_COMMANDS.get(i));
                        }
                    }
                    RemoteSettingsFragment.this.commandSpinnerAdapter = new ArrayAdapter<String>(RemoteSettingsFragment.this.getActivity(), 17367049, unusedCommands) {
                        @NonNull
                        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            ((TextView) v).setTypeface(RemoteSettingsFragment.this.font);
                            v.setTag(((TextView) v).getText());
                            return v;
                        }

                        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                            TextView dropDownView = (TextView) super.getDropDownView(position, convertView, parent);
                            dropDownView.setTypeface(RemoteSettingsFragment.this.font);
                            if (position == RemoteSettingsFragment.this.commandSpinner.getSelectedItemPosition()) {
                                dropDownView.setBackgroundColor(1073741824);
                            } else {
                                dropDownView.setBackgroundColor(536870912);
                            }
                            return dropDownView;
                        }
                    };
                    RemoteSettingsFragment.this.commandSpinner = (Spinner) addCustomCommandView.findViewById(R.id.commandSpinner);
                    RemoteSettingsFragment.this.commandSpinner.setAdapter(RemoteSettingsFragment.this.commandSpinnerAdapter);
                    RemoteSettingsFragment.this.commandSpinner.setBackgroundColor(536870912);
                    RemoteSettingsFragment.this.receivedIrCommands.clear();
                    RemoteSettingsFragment.this.check1 = (ImageView) addCustomCommandView.findViewById(R.id.check1);
                    RemoteSettingsFragment.this.check2 = (ImageView) addCustomCommandView.findViewById(R.id.check2);
                    RemoteSettingsFragment.this.check3 = (ImageView) addCustomCommandView.findViewById(R.id.check3);
                    RemoteSettingsFragment.this.check1.setColorFilter(RemoteSettingsFragment.this.unselectedCheckColor);
                    RemoteSettingsFragment.this.check2.setColorFilter(RemoteSettingsFragment.this.unselectedCheckColor);
                    RemoteSettingsFragment.this.check3.setColorFilter(RemoteSettingsFragment.this.unselectedCheckColor);
                    TextView resetText = (TextView) addCustomCommandView.findViewById(R.id.resetText);
                    resetText.setTypeface(RemoteSettingsFragment.this.font);
                    resetText.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            RemoteSettingsFragment.this.receivedIrCommands.clear();
                            RemoteSettingsFragment.this.check1.setColorFilter(RemoteSettingsFragment.this.unselectedCheckColor);
                            RemoteSettingsFragment.this.check2.setColorFilter(RemoteSettingsFragment.this.unselectedCheckColor);
                            RemoteSettingsFragment.this.check3.setColorFilter(RemoteSettingsFragment.this.unselectedCheckColor);
                        }
                    });
                    TextView cancelText = (TextView) addCustomCommandView.findViewById(R.id.cancelText);
                    cancelText.setTypeface(RemoteSettingsFragment.this.font);
                    cancelText.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (RemoteSettingsFragment.this.addCustomCommandDialog != null && RemoteSettingsFragment.this.addCustomCommandDialog.isShowing()) {
                                RemoteSettingsFragment.this.addCustomCommandDialog.dismiss();
                            }
                            RemoteSettingsFragment.this.irLearningMode = 0;
                            if (RemoteSettingsFragment.this.remoteSettingsFragmentListener != null) {
                                RemoteSettingsFragment.this.remoteSettingsFragmentListener.setIrLearningMode(RemoteSettingsFragment.this.irLearningMode);
                            }
                            RemoteSettingsFragment.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    if (RemoteSettingsFragment.this.remoteSettingsFragmentListener != null) {
                                        RemoteSettingsFragment.this.remoteSettingsFragmentListener.setIrLearningMode(RemoteSettingsFragment.this.irLearningMode);
                                    }
                                }
                            }, 100);
                        }
                    });
                    RemoteSettingsFragment.this.addCustomCommandDialog = new Builder(RemoteSettingsFragment.this.getContext()).setView(addCustomCommandView).setCancelable(false).create();
                    RemoteSettingsFragment.this.addCustomCommandDialog.show();
                }
            }
        };
        if (this.irEnabled == 0) {
            displayIrAsEnabled(false);
        } else {
            displayIrAsEnabled(true);
        }
        this.irEnabledToggle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (RemoteSettingsFragment.this.irEnabled == 0) {
                    RemoteSettingsFragment.this.irEnabled = 1;
                    RemoteSettingsFragment.this.displayIrAsEnabled(true);
                } else {
                    RemoteSettingsFragment.this.irEnabled = 0;
                    RemoteSettingsFragment.this.displayIrAsEnabled(false);
                    RemoteSettingsFragment.this.rootLayout.removeView(RemoteSettingsFragment.this.helpTextView);
                }
                if (RemoteSettingsFragment.this.remoteSettingsFragmentListener != null) {
                    RemoteSettingsFragment.this.remoteSettingsFragmentListener.setIrEnabled(RemoteSettingsFragment.this.irEnabled);
                }
            }
        });
        for (byte recordIndex = 0; recordIndex < 8; recordIndex = (byte) (recordIndex + 1)) {
            addIrManifestEntryToLayout(recordIndex);
        }
        Log.i(tag, "irManifestMap" + this.irManifestMap.toString());
        if (firstAvailableManifestRecordIndex() == -1) {
            this.addCustomCommandButtonLayout.setVisibility(4);
        }
        this.helpTextView = new TextView(getActivity());
        this.helpTextView.setText("");
        this.helpTextView.setTypeface(this.font);
        this.helpTextView.setTextSize(12.0f);
        this.helpTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
        this.helpTextView.setPadding(pxToDp(5), 0, pxToDp(5), pxToDp(10));
        view.findViewById(R.id.irEnabledHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RemoteSettingsFragment.this.rootLayout.removeView(RemoteSettingsFragment.this.helpTextView);
                if (!RemoteSettingsFragment.this.helpTextView.getText().equals(RemoteSettingsFragment.this.getString(R.string.irEnabledHelpText)) || !RemoteSettingsFragment.this.helpTextView.isShown()) {
                    RemoteSettingsFragment.this.helpTextView.setText(RemoteSettingsFragment.this.getString(R.string.irEnabledHelpText));
                    RemoteSettingsFragment.this.rootLayout.addView(RemoteSettingsFragment.this.helpTextView, RemoteSettingsFragment.this.rootLayout.indexOfChild(RemoteSettingsFragment.this.irEnabledLayout) + 1, RemoteSettingsFragment.this.helpLayoutParams);
                    RemoteSettingsFragment.this.rootLayout.requestChildFocus(RemoteSettingsFragment.this.helpTextView, RemoteSettingsFragment.this.helpTextView);
                }
            }
        });
        view.findViewById(R.id.customCommandsHelp).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (RemoteSettingsFragment.this.irEnabled != 0) {
                    RemoteSettingsFragment.this.rootLayout.removeView(RemoteSettingsFragment.this.helpTextView);
                    if (!RemoteSettingsFragment.this.helpTextView.getText().equals(RemoteSettingsFragment.this.getString(R.string.customCommandsHelpText)) || !RemoteSettingsFragment.this.helpTextView.isShown()) {
                        RemoteSettingsFragment.this.helpTextView.setText(RemoteSettingsFragment.this.getString(R.string.customCommandsHelpText));
                        RemoteSettingsFragment.this.rootLayout.addView(RemoteSettingsFragment.this.helpTextView, RemoteSettingsFragment.this.rootLayout.indexOfChild(RemoteSettingsFragment.this.customCommandsLayout) + 1, RemoteSettingsFragment.this.helpLayoutParams);
                        RemoteSettingsFragment.this.rootLayout.requestChildFocus(RemoteSettingsFragment.this.helpTextView, RemoteSettingsFragment.this.helpTextView);
                    }
                }
            }
        });
        return view;
    }

    /* access modifiers changed from: 0000 */
    public void addIrManifestEntryToLayout(byte recordIndex) {
        int entryIndex = recordIndex * 5;
        final byte commandId = this.irManifest[entryIndex];
        if (commandId != 0 && commandId <= 12) {
            this.irManifestMap.put(Byte.valueOf(commandId), Integer.valueOf(((this.irManifest[entryIndex + 1] & 255) << 24) + ((this.irManifest[entryIndex + 2] & 255) << 16) + ((this.irManifest[entryIndex + 3] & 255) << 8) + (this.irManifest[entryIndex + 4] & 255)));
            final View irManifestEntry = getActivity().getLayoutInflater().inflate(R.layout.ir_manifest_entry, null);
            TextView commandText = (TextView) irManifestEntry.findViewById(R.id.text3);
            commandText.setTypeface(this.font);
            commandText.setText((CharSequence) Connect.IR_COMMANDS.get(commandId));
            ImageView deleteImageView = (ImageView) irManifestEntry.findViewById(R.id.deleteImageView);
            deleteImageView.setTag(Byte.valueOf(recordIndex));
            deleteImageView.setOnClickListener(new OnClickListener() {
                public void onClick(View deleteImageView) {
                    byte recordIndex = ((Byte) deleteImageView.getTag()).byteValue();
                    RemoteSettingsFragment.this.irManifestMap.remove(Byte.valueOf(commandId));
                    for (int i = 0; i < 5; i++) {
                        RemoteSettingsFragment.this.irManifest[(recordIndex * 5) + i] = 0;
                    }
                    byte[] irManifestEntryArray = new byte[6];
                    irManifestEntryArray[0] = recordIndex;
                    System.arraycopy(RemoteSettingsFragment.this.irManifest, recordIndex * 5, irManifestEntryArray, 1, 5);
                    if (RemoteSettingsFragment.this.remoteSettingsFragmentListener != null) {
                        RemoteSettingsFragment.this.remoteSettingsFragmentListener.setIrManifestEntry(irManifestEntryArray);
                    }
                    RemoteSettingsFragment.this.manifestEntryLayout.removeView(irManifestEntry);
                    if (RemoteSettingsFragment.this.firstAvailableManifestRecordIndex() != -1) {
                        RemoteSettingsFragment.this.addCustomCommandButtonLayout.setVisibility(0);
                    }
                }
            });
            this.manifestEntryLayout.addView(irManifestEntry);
        }
    }

    /* access modifiers changed from: private */
    public int firstAvailableManifestRecordIndex() {
        for (byte recordIndex = 0; recordIndex < 8; recordIndex = (byte) (recordIndex + 1)) {
            if (this.irManifest[recordIndex * 5] == 0) {
                return recordIndex;
            }
        }
        return -1;
    }

    public void onIrCommandReceived(int lastIrCommandReceived) {
        Log.i(tag, "onIrCommandReceived " + lastIrCommandReceived);
        if (this.irLearningMode != 1 || this.addCustomCommandDialog == null || !this.addCustomCommandDialog.isShowing()) {
            return;
        }
        if (Connect.DS_REMOTE_COMMAND_VALUES.contains(Integer.valueOf(lastIrCommandReceived)) || this.irManifestMap.containsValue(Integer.valueOf(lastIrCommandReceived))) {
            Log.i(tag, "ir command value already used, ignoring");
            return;
        }
        if (this.receivedIrCommands.isEmpty()) {
            this.receivedIrCommands.put(Integer.valueOf(lastIrCommandReceived), Integer.valueOf(1));
        } else if (this.receivedIrCommands.containsKey(Integer.valueOf(lastIrCommandReceived))) {
            this.receivedIrCommands.put(Integer.valueOf(lastIrCommandReceived), Integer.valueOf(((Integer) this.receivedIrCommands.get(Integer.valueOf(lastIrCommandReceived))).intValue() + 1));
        } else {
            Log.i(tag, "received secondary ir command, unneeded, canceling");
            return;
        }
        int highestCount = 0;
        int leadingIrCommand = 0;
        for (Entry<Integer, Integer> entry : this.receivedIrCommands.entrySet()) {
            if (((Integer) entry.getValue()).intValue() > highestCount) {
                highestCount = ((Integer) entry.getValue()).intValue();
                leadingIrCommand = ((Integer) entry.getKey()).intValue();
            }
        }
        if (highestCount == 1) {
            this.check1.setColorFilter(this.checkColor);
        } else if (highestCount == 2) {
            this.check2.setColorFilter(this.checkColor);
        } else {
            this.check3.setColorFilter(this.checkColor);
            this.irLearningMode = 0;
            if (this.remoteSettingsFragmentListener != null) {
                this.remoteSettingsFragmentListener.setIrLearningMode(this.irLearningMode);
            }
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    if (RemoteSettingsFragment.this.remoteSettingsFragmentListener != null) {
                        RemoteSettingsFragment.this.remoteSettingsFragmentListener.setIrLearningMode(RemoteSettingsFragment.this.irLearningMode);
                    }
                }
            }, 100);
            int recordIndex = firstAvailableManifestRecordIndex();
            if (recordIndex == -1) {
                Log.i(tag, "received new irCommand 3 times but no available recordIndex in irManifest, error");
                this.addCustomCommandButtonLayout.setVisibility(4);
                return;
            }
            byte commandId = (byte) Connect.IR_COMMANDS.indexOf((String) this.commandSpinner.getSelectedView().getTag());
            if (commandId < 0 || commandId >= Connect.IR_COMMANDS.size()) {
                commandId = 1;
            }
            int entryIndex = recordIndex * 5;
            this.irManifest[entryIndex] = commandId;
            this.irManifest[entryIndex + 1] = (byte) (leadingIrCommand >> 24);
            this.irManifest[entryIndex + 2] = (byte) (leadingIrCommand >> 16);
            this.irManifest[entryIndex + 3] = (byte) (leadingIrCommand >> 8);
            this.irManifest[entryIndex + 4] = (byte) leadingIrCommand;
            final byte[] irManifestEntryArray = {(byte) recordIndex, commandId, (byte) (leadingIrCommand >> 24), (byte) (leadingIrCommand >> 16), (byte) (leadingIrCommand >> 8), (byte) leadingIrCommand};
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    if (RemoteSettingsFragment.this.remoteSettingsFragmentListener != null) {
                        RemoteSettingsFragment.this.remoteSettingsFragmentListener.setIrManifestEntry(irManifestEntryArray);
                    }
                }
            }, 200);
            addIrManifestEntryToLayout((byte) recordIndex);
            if (firstAvailableManifestRecordIndex() == -1) {
                this.addCustomCommandButtonLayout.setVisibility(4);
            }
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    RemoteSettingsFragment.this.addCustomCommandDialog.dismiss();
                }
            }, 500);
        }
    }

    /* access modifiers changed from: private */
    public void displayIrAsEnabled(boolean enabled) {
        if (enabled) {
            this.irEnabledToggle.setImageResource(R.drawable.toggle2_on);
            this.addCustomCommandButtonLayout.setOnClickListener(this.addCustomCommandClickListener);
            this.harmonyLayout.setOnClickListener(this.harmonyClickedListener);
            this.customCommandsLayout.setAlpha(1.0f);
            this.manifestEntryLayout.setAlpha(1.0f);
            this.harmonyLayout.setAlpha(1.0f);
            this.harmonyDivider.setAlpha(1.0f);
            return;
        }
        this.irEnabledToggle.setImageResource(R.drawable.toggle2_off);
        this.addCustomCommandButtonLayout.setOnClickListener(null);
        this.harmonyLayout.setOnClickListener(null);
        this.customCommandsLayout.setAlpha(0.3f);
        this.manifestEntryLayout.setAlpha(0.3f);
        this.harmonyLayout.setAlpha(0.3f);
        this.harmonyDivider.setAlpha(0.3f);
    }

    private int pxToDp(int px) {
        return (int) TypedValue.applyDimension(1, (float) px, getContext().getResources().getDisplayMetrics());
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RemoteSettingsFragmentListener) {
            this.remoteSettingsFragmentListener = (RemoteSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement RemoteSettingsFragmentListener");
    }

    public void onDetach() {
        if (this.addCustomCommandDialog != null && this.addCustomCommandDialog.isShowing()) {
            this.addCustomCommandDialog.dismiss();
        }
        super.onDetach();
        this.remoteSettingsFragmentListener = null;
    }
}

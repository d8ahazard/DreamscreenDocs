package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.lab714.dreamscreenv2.R;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class InstallationSettingsFragment extends Fragment implements OnItemSelectedListener {
    private static final byte[] classic = {8, 16, 48, 48, 7, 7};
    private static final int horizontalMax = 80;
    private static final int horizontalMin = 10;
    private static final String[] items = {"Classic - 32\" - 45\"", "Mega - 45\" - 65\"", "Xtreme - 65\" - 80\"", "Flex / DIY"};
    private static final String tag = "InstallationSettingsFrag";
    private static final int verticalMax = 45;
    private static final int verticalMin = 5;
    /* access modifiers changed from: private */
    public ImageView bottomLeft;
    private ImageView bottomLeftRight;
    private TextView bottomLeftText;
    private ImageView bottomLeftUp;
    private LinearLayout bottomLine1;
    private LinearLayout bottomLine2;
    /* access modifiers changed from: private */
    public ImageView bottomRight;
    private ImageView bottomRightLeft;
    private TextView bottomRightText;
    private ImageView bottomRightUp;
    /* access modifiers changed from: private */
    public TextView channel1Button;
    /* access modifiers changed from: private */
    public ArrayList<ImageView> channel1Transitions = new ArrayList<>();
    private TextView channel2Button;
    /* access modifiers changed from: private */
    public ArrayList<ImageView> channel2Transitions = new ArrayList<>();
    private ImageButton clearButton;
    private TextView clearText;
    private OnDragListener cornerDragListener;
    private OnTouchListener cornerTouchListener;
    /* access modifiers changed from: private */
    public int customMode = 0;
    private OnDragListener edittextDragPreventer;
    /* access modifiers changed from: private */
    public byte[] flexSetup = ((byte[]) classic.clone());
    /* access modifiers changed from: private */
    public Typeface font;
    private TextView headerText;
    private EditText horizontalCount;
    private RelativeLayout imageLayout;
    private ImageView imageView;
    /* access modifiers changed from: private */
    public InstallationSettingsFragmentListener installationSettingsFragmentListener;
    private LinearLayout leftLine1;
    private LinearLayout leftLine2;
    private ImageButton lockButton;
    private TextView lockText;
    private LinearLayout rightLine1;
    private LinearLayout rightLine2;
    private LinearLayout rootLinearLayout;
    /* access modifiers changed from: private */
    public int skuSetup = 0;
    /* access modifiers changed from: private */
    public ImageView topLeft;
    private ImageView topLeftDown;
    private ImageView topLeftRight;
    private TextView topLeftText;
    private LinearLayout topLine1;
    private LinearLayout topLine2;
    /* access modifiers changed from: private */
    public ImageView topRight;
    private ImageView topRightDown;
    private ImageView topRightLeft;
    private TextView topRightText;
    /* access modifiers changed from: private */
    public ArrayList<ImageView> transitions = new ArrayList<>();
    private RelativeLayout tvLayout;
    /* access modifiers changed from: private */
    public boolean userEditting = false;
    private EditText verticalCount;

    public interface InstallationSettingsFragmentListener {
        byte[] getFlexSetup();

        int getSkuSetup();

        void setFlexSetup(byte[] bArr);

        void setLightForGroupingNull();

        void setSkuSetup(int i);
    }

    public static InstallationSettingsFragment newInstance() {
        return new InstallationSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.flexSetup = this.installationSettingsFragmentListener.getFlexSetup();
        this.skuSetup = this.installationSettingsFragmentListener.getSkuSetup();
        this.installationSettingsFragmentListener.setLightForGroupingNull();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_installation_settings, container, false);
        this.headerText = (TextView) view.findViewById(R.id.headerText);
        this.topLeftText = (TextView) view.findViewById(R.id.topLeftText);
        this.bottomLeftText = (TextView) view.findViewById(R.id.bottomLeftText);
        this.topRightText = (TextView) view.findViewById(R.id.topRightText);
        this.bottomRightText = (TextView) view.findViewById(R.id.bottomRightText);
        this.font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.headerText.setTypeface(this.font);
        this.topLeftText.setTypeface(this.font);
        this.bottomLeftText.setTypeface(this.font);
        this.topRightText.setTypeface(this.font);
        this.bottomRightText.setTypeface(this.font);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, items) {
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(InstallationSettingsFragment.this.font);
                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView dropDownView = (TextView) super.getDropDownView(position, convertView, parent);
                dropDownView.setTypeface(InstallationSettingsFragment.this.font);
                if (position == InstallationSettingsFragment.this.skuSetup) {
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
        spinner.setOnItemSelectedListener(this);
        spinner.setDropDownVerticalOffset(pxToDp(42));
        this.imageLayout = (RelativeLayout) view.findViewById(R.id.imageLayout);
        this.imageView = (ImageView) view.findViewById(R.id.deleteImageView);
        this.tvLayout = (RelativeLayout) view.findViewById(R.id.tvLayout);
        this.rootLinearLayout = (LinearLayout) view.findViewById(R.id.rootLinearLayout);
        this.verticalCount = (EditText) view.findViewById(R.id.verticalCount);
        this.horizontalCount = (EditText) view.findViewById(R.id.horizontalCount);
        this.channel1Button = (TextView) view.findViewById(R.id.channel1Button);
        this.channel2Button = (TextView) view.findViewById(R.id.channel2Button);
        this.lockText = (TextView) view.findViewById(R.id.lockText);
        this.clearText = (TextView) view.findViewById(R.id.clearText);
        this.lockButton = (ImageButton) view.findViewById(R.id.lockButton);
        this.clearButton = (ImageButton) view.findViewById(R.id.clearButton);
        this.topLeft = (ImageView) view.findViewById(R.id.topLeft);
        this.topRight = (ImageView) view.findViewById(R.id.topRight);
        this.bottomLeft = (ImageView) view.findViewById(R.id.bottomLeft);
        this.bottomRight = (ImageView) view.findViewById(R.id.bottomRight);
        this.topLeftRight = (ImageView) view.findViewById(R.id.topLeftRight);
        this.topLeftDown = (ImageView) view.findViewById(R.id.topLeftDown);
        this.topRightDown = (ImageView) view.findViewById(R.id.topRightDown);
        this.topRightLeft = (ImageView) view.findViewById(R.id.topRightLeft);
        this.bottomLeftUp = (ImageView) view.findViewById(R.id.bottomLeftUp);
        this.bottomLeftRight = (ImageView) view.findViewById(R.id.bottomLeftRight);
        this.bottomRightLeft = (ImageView) view.findViewById(R.id.bottomRightLeft);
        this.bottomRightUp = (ImageView) view.findViewById(R.id.bottomRightUp);
        this.leftLine1 = (LinearLayout) view.findViewById(R.id.leftLine1);
        this.topLine1 = (LinearLayout) view.findViewById(R.id.topLine1);
        this.rightLine1 = (LinearLayout) view.findViewById(R.id.rightLine1);
        this.bottomLine1 = (LinearLayout) view.findViewById(R.id.bottomLine1);
        this.leftLine2 = (LinearLayout) view.findViewById(R.id.leftLine2);
        this.topLine2 = (LinearLayout) view.findViewById(R.id.topLine2);
        this.rightLine2 = (LinearLayout) view.findViewById(R.id.rightLine2);
        this.bottomLine2 = (LinearLayout) view.findViewById(R.id.bottomLine2);
        spinner.setSelection(this.skuSetup);
        this.channel1Button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (InstallationSettingsFragment.this.userEditting) {
                    InstallationSettingsFragment.this.customMode = 1;
                    InstallationSettingsFragment.this.redrawTV();
                }
            }
        });
        this.channel2Button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (InstallationSettingsFragment.this.userEditting) {
                    InstallationSettingsFragment.this.customMode = 2;
                    InstallationSettingsFragment.this.redrawTV();
                }
            }
        });
        this.lockButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                InstallationSettingsFragment.this.userEditting = !InstallationSettingsFragment.this.userEditting;
                InstallationSettingsFragment.this.customMode = 0;
                InstallationSettingsFragment.this.redrawTV();
                if (InstallationSettingsFragment.this.userEditting) {
                    InstallationSettingsFragment.this.channel1Button.performClick();
                    return;
                }
                InstallationSettingsFragment.this.transitionsToFlexSetup();
                InstallationSettingsFragment.this.installationSettingsFragmentListener.setFlexSetup(InstallationSettingsFragment.this.flexSetup);
            }
        });
        this.clearButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (InstallationSettingsFragment.this.userEditting) {
                    if (InstallationSettingsFragment.this.customMode == 1) {
                        InstallationSettingsFragment.this.channel1Transitions = new ArrayList();
                    } else if (InstallationSettingsFragment.this.customMode == 2) {
                        InstallationSettingsFragment.this.channel2Transitions = new ArrayList();
                    }
                    InstallationSettingsFragment.this.redrawTV();
                }
            }
        });
        this.verticalCount.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InstallationSettingsFragment.this.checkVerticalCount();
                }
            }
        });
        this.verticalCount.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 6) {
                    InstallationSettingsFragment.this.checkVerticalCount();
                }
                return false;
            }
        });
        this.horizontalCount.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InstallationSettingsFragment.this.checkHorizontalCount();
                }
            }
        });
        this.horizontalCount.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 6) {
                    InstallationSettingsFragment.this.checkHorizontalCount();
                }
                return false;
            }
        });
        this.rootLinearLayout.requestFocus();
        this.cornerTouchListener = new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!InstallationSettingsFragment.this.userEditting || motionEvent.getAction() != 0 || InstallationSettingsFragment.this.customMode == 0) {
                    return false;
                }
                DragShadowBuilder dragShadow = new DragShadowBuilder(view);
                if (VERSION.SDK_INT >= 24) {
                    view.startDragAndDrop(null, dragShadow, null, 0);
                } else {
                    view.startDrag(null, dragShadow, null, 0);
                }
                if (InstallationSettingsFragment.this.customMode == 1) {
                    InstallationSettingsFragment.this.channel1Transitions = new ArrayList();
                    return true;
                } else if (InstallationSettingsFragment.this.customMode != 2) {
                    return true;
                } else {
                    InstallationSettingsFragment.this.channel2Transitions = new ArrayList();
                    return true;
                }
            }
        };
        this.cornerDragListener = new OnDragListener() {
            public boolean onDrag(View view, DragEvent dragEvent) {
                if (InstallationSettingsFragment.this.customMode == 0) {
                    return false;
                }
                switch (dragEvent.getAction()) {
                    case 5:
                        if (InstallationSettingsFragment.this.customMode == 1) {
                            InstallationSettingsFragment.this.transitions = InstallationSettingsFragment.this.channel1Transitions;
                        } else if (InstallationSettingsFragment.this.customMode == 2) {
                            InstallationSettingsFragment.this.transitions = InstallationSettingsFragment.this.channel2Transitions;
                        }
                        if (InstallationSettingsFragment.this.transitions.size() <= 0) {
                            InstallationSettingsFragment.this.transitions.add((ImageView) view);
                        } else if (InstallationSettingsFragment.this.transitions.size() >= 2 && InstallationSettingsFragment.this.transitions.get(InstallationSettingsFragment.this.transitions.size() - 1) == view) {
                            InstallationSettingsFragment.this.transitions.remove(InstallationSettingsFragment.this.transitions.size() - 1);
                        } else if ((!InstallationSettingsFragment.this.transitions.contains(view) || InstallationSettingsFragment.this.transitions.size() > 4 || view == InstallationSettingsFragment.this.transitions.get(0)) && !(InstallationSettingsFragment.this.transitions.size() == 2 && view == InstallationSettingsFragment.this.transitions.get(0))) {
                            ImageView lastTransition = (ImageView) InstallationSettingsFragment.this.transitions.get(InstallationSettingsFragment.this.transitions.size() - 1);
                            ImageView oppositeCorner = null;
                            if (lastTransition == InstallationSettingsFragment.this.topLeft) {
                                oppositeCorner = InstallationSettingsFragment.this.bottomRight;
                            } else if (lastTransition == InstallationSettingsFragment.this.topRight) {
                                oppositeCorner = InstallationSettingsFragment.this.bottomLeft;
                            } else if (lastTransition == InstallationSettingsFragment.this.bottomLeft) {
                                oppositeCorner = InstallationSettingsFragment.this.topRight;
                            } else if (lastTransition == InstallationSettingsFragment.this.bottomRight) {
                                oppositeCorner = InstallationSettingsFragment.this.topLeft;
                            }
                            if (!(view == oppositeCorner || view == lastTransition || InstallationSettingsFragment.this.transitions.size() > 4)) {
                                InstallationSettingsFragment.this.transitions.add((ImageView) view);
                            }
                        }
                        InstallationSettingsFragment.this.redrawTV();
                        return true;
                    default:
                        return true;
                }
            }
        };
        this.topLeft.setOnTouchListener(this.cornerTouchListener);
        this.topRight.setOnTouchListener(this.cornerTouchListener);
        this.bottomLeft.setOnTouchListener(this.cornerTouchListener);
        this.bottomRight.setOnTouchListener(this.cornerTouchListener);
        this.topLeft.setOnDragListener(this.cornerDragListener);
        this.topRight.setOnDragListener(this.cornerDragListener);
        this.bottomLeft.setOnDragListener(this.cornerDragListener);
        this.bottomRight.setOnDragListener(this.cornerDragListener);
        this.edittextDragPreventer = new OnDragListener() {
            public boolean onDrag(View view, DragEvent dragEvent) {
                return false;
            }
        };
        this.horizontalCount.setOnDragListener(this.edittextDragPreventer);
        this.verticalCount.setOnDragListener(this.edittextDragPreventer);
        return view;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0:
                this.skuSetup = 0;
                this.installationSettingsFragmentListener.setSkuSetup(this.skuSetup);
                redrawFragment();
                return;
            case 1:
                this.skuSetup = 1;
                this.installationSettingsFragmentListener.setSkuSetup(this.skuSetup);
                redrawFragment();
                return;
            case 2:
                this.skuSetup = 2;
                this.installationSettingsFragmentListener.setSkuSetup(this.skuSetup);
                redrawFragment();
                return;
            case 3:
                this.skuSetup = 3;
                this.installationSettingsFragmentListener.setSkuSetup(this.skuSetup);
                this.customMode = 0;
                this.userEditting = false;
                flexSetupToTransitions();
                redrawFragment();
                this.verticalCount.setText(String.valueOf(this.flexSetup[0]));
                this.horizontalCount.setText(String.valueOf(this.flexSetup[1]));
                return;
            default:
                return;
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /* access modifiers changed from: private */
    public void checkVerticalCount() {
        int input;
        try {
            input = Integer.parseInt(this.verticalCount.getText().toString());
        } catch (Exception e) {
            input = 0;
        }
        if (input < 5) {
            this.verticalCount.setText("5");
        } else if (input > 45) {
            this.verticalCount.setText("45");
        }
    }

    /* access modifiers changed from: private */
    public void checkHorizontalCount() {
        int input;
        try {
            input = Integer.parseInt(this.horizontalCount.getText().toString());
        } catch (Exception e) {
            input = 0;
        }
        if (input < 10) {
            this.horizontalCount.setText("10");
        } else if (input > 80) {
            this.horizontalCount.setText("80");
        }
    }

    private void redrawFragment() {
        this.imageLayout.setVisibility(8);
        this.tvLayout.setVisibility(8);
        switch (this.skuSetup) {
            case 0:
                this.imageLayout.setVisibility(0);
                this.imageView.setImageResource(R.drawable.classic_setup);
                return;
            case 1:
                this.imageLayout.setVisibility(0);
                this.imageView.setImageResource(R.drawable.mega_setup);
                return;
            case 2:
                this.imageLayout.setVisibility(0);
                this.imageView.setImageResource(R.drawable.xtreme_setup);
                return;
            case 3:
                redrawTV();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void redrawTV() {
        this.tvLayout.setVisibility(0);
        this.topLeftText.setText("");
        this.bottomLeftText.setText("");
        this.topRightText.setText("");
        this.bottomRightText.setText("");
        this.horizontalCount.setTextColor(-1);
        this.verticalCount.setTextColor(-1);
        this.channel1Button.setTextColor(-1);
        this.channel1Button.setBackgroundResource(R.drawable.channel_unselected);
        this.channel2Button.setTextColor(-1);
        this.channel2Button.setBackgroundResource(R.drawable.channel_unselected);
        this.clearButton.setColorFilter(null);
        this.clearText.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryLightTextColor));
        switch (this.customMode) {
            case 0:
                if (!this.userEditting) {
                    this.lockButton.setImageResource(R.drawable.ic_lock_outline_white_36dp);
                    this.lockText.setText("Edit");
                    this.horizontalCount.setFocusable(false);
                    this.horizontalCount.setFocusableInTouchMode(false);
                    this.verticalCount.setFocusable(false);
                    this.verticalCount.setFocusableInTouchMode(false);
                    this.horizontalCount.setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                    this.verticalCount.setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                    this.channel1Button.setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                    this.channel1Button.setBackgroundResource(R.drawable.channel_disabled);
                    this.channel2Button.setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                    this.channel2Button.setBackgroundResource(R.drawable.channel_disabled);
                    this.clearButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                    this.clearText.setTextColor(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
                    break;
                } else {
                    this.lockButton.setImageResource(R.drawable.ic_lock_open_white_36dp);
                    this.lockText.setText("Save");
                    this.horizontalCount.setFocusable(true);
                    this.horizontalCount.setFocusableInTouchMode(true);
                    this.verticalCount.setFocusable(true);
                    this.verticalCount.setFocusableInTouchMode(true);
                    break;
                }
            case 1:
                this.channel1Button.setTextColor(ContextCompat.getColor(getContext(), R.color.channel1Start));
                this.channel1Button.setBackgroundResource(R.drawable.channel1_selected);
                break;
            case 2:
                this.channel2Button.setTextColor(ContextCompat.getColor(getContext(), R.color.channel2Start));
                this.channel2Button.setBackgroundResource(R.drawable.channel2_selected);
                break;
        }
        this.topLeft.setColorFilter(null);
        this.topRight.setColorFilter(null);
        this.bottomLeft.setColorFilter(null);
        this.bottomRight.setColorFilter(null);
        this.topLeft.setVisibility(0);
        this.topRight.setVisibility(0);
        this.bottomRight.setVisibility(0);
        this.bottomLeft.setVisibility(0);
        this.topLeftRight.setVisibility(4);
        this.topLeftRight.setColorFilter(null);
        this.topLeftDown.setVisibility(4);
        this.topLeftDown.setColorFilter(null);
        this.topRightLeft.setVisibility(4);
        this.topRightLeft.setColorFilter(null);
        this.topRightDown.setVisibility(4);
        this.topRightDown.setColorFilter(null);
        this.bottomLeftUp.setVisibility(4);
        this.bottomLeftUp.setColorFilter(null);
        this.bottomLeftRight.setVisibility(4);
        this.bottomLeftRight.setColorFilter(null);
        this.bottomRightLeft.setVisibility(4);
        this.bottomRightLeft.setColorFilter(null);
        this.bottomRightUp.setVisibility(4);
        this.bottomRightUp.setColorFilter(null);
        this.leftLine1.setVisibility(4);
        this.topLine1.setVisibility(4);
        this.rightLine1.setVisibility(4);
        this.bottomLine1.setVisibility(4);
        this.leftLine2.setVisibility(4);
        this.topLine2.setVisibility(4);
        this.rightLine2.setVisibility(4);
        this.bottomLine2.setVisibility(4);
        ImageView startPoint = null;
        ImageView startPoint2 = null;
        if (this.customMode == 1 || this.customMode == 0) {
            int color = ContextCompat.getColor(getContext(), R.color.channel1);
            int colorStart = ContextCompat.getColor(getContext(), R.color.channel1Start);
            if (this.customMode == 1) {
                Iterator it = this.channel1Transitions.iterator();
                while (it.hasNext()) {
                    ((ImageView) it.next()).setColorFilter(color, Mode.SRC_IN);
                }
            }
            for (int i = 1; i < this.channel1Transitions.size(); i++) {
                ImageView point = (ImageView) this.channel1Transitions.get(i - 1);
                ImageView point2 = (ImageView) this.channel1Transitions.get(i);
                if (point == this.topLeft && point2 == this.bottomLeft) {
                    this.leftLine1.setVisibility(0);
                    if (i == 1) {
                        startPoint2 = this.topLeftDown;
                        this.topLeftDown.setVisibility(0);
                        this.topLeftDown.setColorFilter(colorStart, Mode.SRC_IN);
                        this.topLeftText.setText("1");
                    }
                } else if (point == this.bottomLeft && point2 == this.topLeft) {
                    this.leftLine1.setVisibility(0);
                    if (i == 1) {
                        startPoint2 = this.bottomLeftUp;
                        this.bottomLeftUp.setVisibility(0);
                        this.bottomLeftUp.setColorFilter(colorStart, Mode.SRC_IN);
                        this.bottomLeftText.setText("1");
                    }
                } else if (point == this.topLeft && point2 == this.topRight) {
                    this.topLine1.setVisibility(0);
                    if (i == 1) {
                        startPoint2 = this.topLeftRight;
                        this.topLeftRight.setVisibility(0);
                        this.topLeftRight.setColorFilter(colorStart, Mode.SRC_IN);
                        this.topLeftText.setText("1");
                    }
                } else if (point == this.topRight && point2 == this.topLeft) {
                    this.topLine1.setVisibility(0);
                    if (i == 1) {
                        startPoint2 = this.topRightLeft;
                        this.topRightLeft.setVisibility(0);
                        this.topRightLeft.setColorFilter(colorStart, Mode.SRC_IN);
                        this.topRightText.setText("1");
                    }
                } else if (point == this.topRight && point2 == this.bottomRight) {
                    this.rightLine1.setVisibility(0);
                    if (i == 1) {
                        startPoint2 = this.topRightDown;
                        this.topRightDown.setVisibility(0);
                        this.topRightDown.setColorFilter(colorStart, Mode.SRC_IN);
                        this.topRightText.setText("1");
                    }
                } else if (point == this.bottomRight && point2 == this.topRight) {
                    this.rightLine1.setVisibility(0);
                    if (i == 1) {
                        startPoint2 = this.bottomRightUp;
                        this.bottomRightUp.setVisibility(0);
                        this.bottomRightUp.setColorFilter(colorStart, Mode.SRC_IN);
                        this.bottomRightText.setText("1");
                    }
                } else if (point == this.bottomLeft && point2 == this.bottomRight) {
                    this.bottomLine1.setVisibility(0);
                    if (i == 1) {
                        startPoint2 = this.bottomLeftRight;
                        this.bottomLeftRight.setVisibility(0);
                        this.bottomLeftRight.setColorFilter(colorStart, Mode.SRC_IN);
                        this.bottomLeftText.setText("1");
                    }
                } else if (point == this.bottomRight && point2 == this.bottomLeft) {
                    this.bottomLine1.setVisibility(0);
                    if (i == 1) {
                        startPoint2 = this.bottomRightLeft;
                        this.bottomRightLeft.setVisibility(0);
                        this.bottomRightLeft.setColorFilter(colorStart, Mode.SRC_IN);
                        this.bottomRightText.setText("1");
                    }
                }
            }
        }
        if (this.customMode == 2 || this.customMode == 0) {
            int color2 = ContextCompat.getColor(getContext(), R.color.channel2);
            int colorStart2 = ContextCompat.getColor(getContext(), R.color.channel2Start);
            int colorMixed = ContextCompat.getColor(getContext(), R.color.channelsMixed);
            if (this.customMode == 2) {
                Iterator it2 = this.channel2Transitions.iterator();
                while (it2.hasNext()) {
                    ImageView imageView2 = (ImageView) it2.next();
                    if (imageView2.getColorFilter() == null) {
                        imageView2.setColorFilter(color2, Mode.SRC_IN);
                    } else {
                        imageView2.setColorFilter(colorMixed, Mode.SRC_IN);
                    }
                }
            }
            for (int i2 = 1; i2 < this.channel2Transitions.size(); i2++) {
                ImageView point3 = (ImageView) this.channel2Transitions.get(i2 - 1);
                ImageView point22 = (ImageView) this.channel2Transitions.get(i2);
                if (point3 == this.topLeft && point22 == this.bottomLeft) {
                    this.leftLine2.setVisibility(0);
                    if (i2 == 1) {
                        startPoint = this.topLeftDown;
                        if (this.topLeftText.getText().length() == 0) {
                            this.topLeftText.setText("2");
                        } else {
                            this.topLeftText.setText("1 2");
                        }
                        if (this.topLeftDown.isShown()) {
                            this.topLeftDown.setColorFilter(colorMixed, Mode.SRC_IN);
                        } else {
                            this.topLeftDown.setVisibility(0);
                            this.topLeftDown.setColorFilter(colorStart2, Mode.SRC_IN);
                        }
                    }
                } else if (point3 == this.bottomLeft && point22 == this.topLeft) {
                    this.leftLine2.setVisibility(0);
                    if (i2 == 1) {
                        startPoint = this.bottomLeftUp;
                        if (this.bottomLeftText.getText().length() == 0) {
                            this.bottomLeftText.setText("2");
                        } else {
                            this.bottomLeftText.setText("1 2");
                        }
                        if (this.bottomLeftUp.isShown()) {
                            this.bottomLeftUp.setColorFilter(colorMixed, Mode.SRC_IN);
                        } else {
                            this.bottomLeftUp.setVisibility(0);
                            this.bottomLeftUp.setColorFilter(colorStart2, Mode.SRC_IN);
                        }
                    }
                } else if (point3 == this.topLeft && point22 == this.topRight) {
                    this.topLine2.setVisibility(0);
                    if (i2 == 1) {
                        startPoint = this.topLeftRight;
                        if (this.topLeftText.getText().length() == 0) {
                            this.topLeftText.setText("2");
                        } else {
                            this.topLeftText.setText("1 2");
                        }
                        if (this.topLeftRight.isShown()) {
                            this.topLeftRight.setColorFilter(colorMixed, Mode.SRC_IN);
                        } else {
                            this.topLeftRight.setVisibility(0);
                            this.topLeftRight.setColorFilter(colorStart2, Mode.SRC_IN);
                        }
                    }
                } else if (point3 == this.topRight && point22 == this.topLeft) {
                    this.topLine2.setVisibility(0);
                    if (i2 == 1) {
                        startPoint = this.topRightLeft;
                        if (this.topRightText.getText().length() == 0) {
                            this.topRightText.setText("2");
                        } else {
                            this.topRightText.setText("1 2");
                        }
                        if (this.topRightLeft.isShown()) {
                            this.topRightLeft.setColorFilter(colorMixed, Mode.SRC_IN);
                        } else {
                            this.topRightLeft.setVisibility(0);
                            this.topRightLeft.setColorFilter(colorStart2, Mode.SRC_IN);
                        }
                    }
                } else if (point3 == this.topRight && point22 == this.bottomRight) {
                    this.rightLine2.setVisibility(0);
                    if (i2 == 1) {
                        startPoint = this.topRightDown;
                        if (this.topRightText.getText().length() == 0) {
                            this.topRightText.setText("2");
                        } else {
                            this.topRightText.setText("1 2");
                        }
                        if (this.topRightDown.isShown()) {
                            this.topRightDown.setColorFilter(colorMixed, Mode.SRC_IN);
                        } else {
                            this.topRightDown.setVisibility(0);
                            this.topRightDown.setColorFilter(colorStart2, Mode.SRC_IN);
                        }
                    }
                } else if (point3 == this.bottomRight && point22 == this.topRight) {
                    this.rightLine2.setVisibility(0);
                    if (i2 == 1) {
                        startPoint = this.bottomRightUp;
                        if (this.bottomRightText.getText().length() == 0) {
                            this.bottomRightText.setText("2");
                        } else {
                            this.bottomRightText.setText("1 2");
                        }
                        if (this.bottomRightUp.isShown()) {
                            this.bottomRightUp.setColorFilter(colorMixed, Mode.SRC_IN);
                        } else {
                            this.bottomRightUp.setVisibility(0);
                            this.bottomRightUp.setColorFilter(colorStart2, Mode.SRC_IN);
                        }
                    }
                } else if (point3 == this.bottomLeft && point22 == this.bottomRight) {
                    this.bottomLine2.setVisibility(0);
                    if (i2 == 1) {
                        startPoint = this.bottomLeftRight;
                        if (this.bottomLeftText.getText().length() == 0) {
                            this.bottomLeftText.setText("2");
                        } else {
                            this.bottomLeftText.setText("1 2");
                        }
                        if (this.bottomLeftRight.isShown()) {
                            this.bottomLeftRight.setColorFilter(colorMixed, Mode.SRC_IN);
                        } else {
                            this.bottomLeftRight.setVisibility(0);
                            this.bottomLeftRight.setColorFilter(colorStart2, Mode.SRC_IN);
                        }
                    }
                } else if (point3 == this.bottomRight && point22 == this.bottomLeft) {
                    this.bottomLine2.setVisibility(0);
                    if (i2 == 1) {
                        startPoint = this.bottomRightLeft;
                        if (this.bottomRightText.getText().length() == 0) {
                            this.bottomRightText.setText("2");
                        } else {
                            this.bottomRightText.setText("1 2");
                        }
                        if (this.bottomRightLeft.isShown()) {
                            this.bottomRightLeft.setColorFilter(colorMixed, Mode.SRC_IN);
                        } else {
                            this.bottomRightLeft.setVisibility(0);
                            this.bottomRightLeft.setColorFilter(colorStart2, Mode.SRC_IN);
                        }
                    }
                }
            }
        }
        if (startPoint != null) {
            startPoint.bringToFront();
        }
        if (startPoint2 != null) {
            startPoint2.bringToFront();
        }
        if (this.customMode == 0) {
            this.topLeft.setVisibility(4);
            this.topRight.setVisibility(4);
            this.bottomLeft.setVisibility(4);
            this.bottomRight.setVisibility(4);
        }
        this.topLeftText.bringToFront();
        this.bottomLeftText.bringToFront();
        this.topRightText.bringToFront();
        this.bottomRightText.bringToFront();
    }

    private void flexSetupToTransitions() {
        this.channel1Transitions = new ArrayList<>();
        this.channel2Transitions = new ArrayList<>();
        byte b = this.flexSetup[0] & 255;
        byte b2 = this.flexSetup[1] & 255;
        int channel2Count = this.flexSetup[3] & 255;
        int channel2Orientation = this.flexSetup[5] & 255;
        setTransitions(this.channel1Transitions, b, b2, this.flexSetup[2] & 255, this.flexSetup[4] & 255);
        setTransitions(this.channel2Transitions, b, b2, channel2Count, channel2Orientation);
    }

    private void setTransitions(ArrayList<ImageView> transitions2, int verticalLedCount, int horizontalLedCount, int channelCount, int channelOrientation) {
        int channelCount2;
        int channelCount3;
        int channelCount4;
        int channelCount5;
        int channelCount6;
        int channelCount7;
        int channelCount8;
        int channelCount9;
        int channelCount10;
        int channelCount11;
        int channelCount12;
        int channelCount13;
        int channelCount14;
        int channelCount15;
        int channelCount16;
        int channelCount17;
        int channelCount18;
        int channelCount19;
        int channelCount20;
        int channelCount21;
        int channelCount22;
        int channelCount23;
        int channelCount24;
        int channelCount25;
        if (channelOrientation == 1) {
            if (channelCount >= verticalLedCount) {
                channelCount23 = channelCount - verticalLedCount;
                transitions2.add(this.bottomRight);
                transitions2.add(this.topRight);
            } else {
                channelCount23 = 0;
            }
            if (channelCount23 >= horizontalLedCount) {
                channelCount24 = channelCount23 - horizontalLedCount;
                transitions2.add(this.topLeft);
            } else {
                channelCount24 = 0;
            }
            if (channelCount24 >= verticalLedCount) {
                channelCount25 = channelCount24 - verticalLedCount;
                transitions2.add(this.bottomLeft);
            } else {
                channelCount25 = 0;
            }
            if (channelCount25 >= horizontalLedCount) {
                transitions2.add(this.bottomRight);
            }
        } else if (channelOrientation == 2) {
            if (channelCount >= horizontalLedCount) {
                channelCount20 = channelCount - horizontalLedCount;
                transitions2.add(this.topRight);
                transitions2.add(this.topLeft);
            } else {
                channelCount20 = 0;
            }
            if (channelCount20 >= verticalLedCount) {
                channelCount21 = channelCount20 - verticalLedCount;
                transitions2.add(this.bottomLeft);
            } else {
                channelCount21 = 0;
            }
            if (channelCount21 >= horizontalLedCount) {
                channelCount22 = channelCount21 - horizontalLedCount;
                transitions2.add(this.bottomRight);
            } else {
                channelCount22 = 0;
            }
            if (channelCount22 >= verticalLedCount) {
                transitions2.add(this.topRight);
            }
        } else if (channelOrientation == 3) {
            if (channelCount >= verticalLedCount) {
                channelCount17 = channelCount - verticalLedCount;
                transitions2.add(this.topLeft);
                transitions2.add(this.bottomLeft);
            } else {
                channelCount17 = 0;
            }
            if (channelCount17 >= horizontalLedCount) {
                channelCount18 = channelCount17 - horizontalLedCount;
                transitions2.add(this.bottomRight);
            } else {
                channelCount18 = 0;
            }
            if (channelCount18 >= verticalLedCount) {
                channelCount19 = channelCount18 - verticalLedCount;
                transitions2.add(this.topRight);
            } else {
                channelCount19 = 0;
            }
            if (channelCount19 >= horizontalLedCount) {
                transitions2.add(this.topLeft);
            }
        } else if (channelOrientation == 4) {
            if (channelCount >= horizontalLedCount) {
                channelCount14 = channelCount - horizontalLedCount;
                transitions2.add(this.bottomLeft);
                transitions2.add(this.bottomRight);
            } else {
                channelCount14 = 0;
            }
            if (channelCount14 >= verticalLedCount) {
                channelCount15 = channelCount14 - verticalLedCount;
                transitions2.add(this.topRight);
            } else {
                channelCount15 = 0;
            }
            if (channelCount15 >= horizontalLedCount) {
                channelCount16 = channelCount15 - horizontalLedCount;
                transitions2.add(this.topLeft);
            } else {
                channelCount16 = 0;
            }
            if (channelCount16 >= verticalLedCount) {
                transitions2.add(this.bottomLeft);
            }
        } else if (channelOrientation == 5) {
            if (channelCount >= verticalLedCount) {
                channelCount11 = channelCount - verticalLedCount;
                transitions2.add(this.topRight);
                transitions2.add(this.bottomRight);
            } else {
                channelCount11 = 0;
            }
            if (channelCount11 >= horizontalLedCount) {
                channelCount12 = channelCount11 - horizontalLedCount;
                transitions2.add(this.bottomLeft);
            } else {
                channelCount12 = 0;
            }
            if (channelCount12 >= verticalLedCount) {
                channelCount13 = channelCount12 - verticalLedCount;
                transitions2.add(this.topLeft);
            } else {
                channelCount13 = 0;
            }
            if (channelCount13 >= horizontalLedCount) {
                transitions2.add(this.topRight);
            }
        } else if (channelOrientation == 6) {
            if (channelCount >= horizontalLedCount) {
                channelCount8 = channelCount - horizontalLedCount;
                transitions2.add(this.topLeft);
                transitions2.add(this.topRight);
            } else {
                channelCount8 = 0;
            }
            if (channelCount8 >= verticalLedCount) {
                channelCount9 = channelCount8 - verticalLedCount;
                transitions2.add(this.bottomRight);
            } else {
                channelCount9 = 0;
            }
            if (channelCount9 >= horizontalLedCount) {
                channelCount10 = channelCount9 - horizontalLedCount;
                transitions2.add(this.bottomLeft);
            } else {
                channelCount10 = 0;
            }
            if (channelCount10 >= verticalLedCount) {
                transitions2.add(this.topLeft);
            }
        } else if (channelOrientation == 7) {
            if (channelCount >= verticalLedCount) {
                channelCount5 = channelCount - verticalLedCount;
                transitions2.add(this.bottomLeft);
                transitions2.add(this.topLeft);
            } else {
                channelCount5 = 0;
            }
            if (channelCount5 >= horizontalLedCount) {
                channelCount6 = channelCount5 - horizontalLedCount;
                transitions2.add(this.topRight);
            } else {
                channelCount6 = 0;
            }
            if (channelCount6 >= verticalLedCount) {
                channelCount7 = channelCount6 - verticalLedCount;
                transitions2.add(this.bottomRight);
            } else {
                channelCount7 = 0;
            }
            if (channelCount7 >= horizontalLedCount) {
                transitions2.add(this.bottomLeft);
            }
        } else if (channelOrientation == 8) {
            if (channelCount >= horizontalLedCount) {
                channelCount2 = channelCount - horizontalLedCount;
                transitions2.add(this.bottomRight);
                transitions2.add(this.bottomLeft);
            } else {
                channelCount2 = 0;
            }
            if (channelCount2 >= verticalLedCount) {
                channelCount3 = channelCount2 - verticalLedCount;
                transitions2.add(this.topLeft);
            } else {
                channelCount3 = 0;
            }
            if (channelCount3 >= horizontalLedCount) {
                channelCount4 = channelCount3 - horizontalLedCount;
                transitions2.add(this.topRight);
            } else {
                channelCount4 = 0;
            }
            if (channelCount4 >= verticalLedCount) {
                transitions2.add(this.bottomRight);
            }
        }
    }

    /* access modifiers changed from: private */
    public void transitionsToFlexSetup() {
        ByteArrayOutputStream flexSetupOutput = new ByteArrayOutputStream();
        int verticalLedCount = Integer.parseInt(this.verticalCount.getText().toString());
        int horizontalLedCount = Integer.parseInt(this.horizontalCount.getText().toString());
        if (verticalLedCount < 5) {
            verticalLedCount = 5;
            this.verticalCount.setText("5");
        } else if (verticalLedCount > 45) {
            verticalLedCount = 45;
            this.verticalCount.setText("45");
        }
        if (horizontalLedCount < 10) {
            horizontalLedCount = 10;
            this.horizontalCount.setText("10");
        } else if (horizontalLedCount > 80) {
            horizontalLedCount = 80;
            this.horizontalCount.setText("80");
        }
        flexSetupOutput.write((byte) verticalLedCount);
        flexSetupOutput.write((byte) horizontalLedCount);
        flexSetupOutput.write((byte) getChannelLedCount(this.channel1Transitions, verticalLedCount, horizontalLedCount));
        flexSetupOutput.write((byte) getChannelLedCount(this.channel2Transitions, verticalLedCount, horizontalLedCount));
        flexSetupOutput.write((byte) getChannelOrientation(this.channel1Transitions));
        flexSetupOutput.write((byte) getChannelOrientation(this.channel2Transitions));
        this.flexSetup = flexSetupOutput.toByteArray();
    }

    private int getChannelLedCount(ArrayList<ImageView> transitions2, int verticalLedCount, int horizontalLedCount) {
        int channelLedCount = 0;
        if (transitions2.size() > 1) {
            for (int i = 1; i < transitions2.size(); i++) {
                ImageView point = (ImageView) transitions2.get(i);
                ImageView point2 = (ImageView) transitions2.get(i - 1);
                if ((point == this.topLeft && point2 == this.bottomLeft) || (point2 == this.topLeft && point == this.bottomLeft)) {
                    channelLedCount += verticalLedCount;
                } else if ((point == this.topLeft && point2 == this.topRight) || (point2 == this.topLeft && point == this.topRight)) {
                    channelLedCount += horizontalLedCount;
                } else if ((point == this.topRight && point2 == this.bottomRight) || (point2 == this.topRight && point == this.bottomRight)) {
                    channelLedCount += verticalLedCount;
                } else if ((point == this.bottomRight && point2 == this.bottomLeft) || (point2 == this.bottomRight && point == this.bottomLeft)) {
                    channelLedCount += horizontalLedCount;
                }
            }
        }
        return channelLedCount;
    }

    private int getChannelOrientation(ArrayList<ImageView> transitions2) {
        if (transitions2.size() <= 1) {
            return 0;
        }
        ImageView start = (ImageView) transitions2.get(0);
        ImageView next = (ImageView) transitions2.get(1);
        if (start == this.bottomRight && next == this.topRight) {
            return 1;
        }
        if (start == this.topRight && next == this.topLeft) {
            return 2;
        }
        if (start == this.topLeft && next == this.bottomLeft) {
            return 3;
        }
        if (start == this.bottomLeft && next == this.bottomRight) {
            return 4;
        }
        if (start == this.topRight && next == this.bottomRight) {
            return 5;
        }
        if (start == this.topLeft && next == this.topRight) {
            return 6;
        }
        if (start == this.bottomLeft && next == this.topLeft) {
            return 7;
        }
        if (start == this.bottomRight && next == this.bottomLeft) {
            return 8;
        }
        return 0;
    }

    private int pxToDp(int px) {
        return (int) TypedValue.applyDimension(1, (float) px, getResources().getDisplayMetrics());
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InstallationSettingsFragmentListener) {
            this.installationSettingsFragmentListener = (InstallationSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement Listener");
    }

    public void onDetach() {
        super.onDetach();
        this.installationSettingsFragmentListener = null;
    }
}

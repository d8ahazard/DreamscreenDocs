package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class SectorSettingsFragment extends Fragment {
    private static final byte[] sectorData = {123, 61, 68, -50, 30, 86, -80, 18, 69, -91, 11, 106, -63, 26, 100, -73, 37, 61, 114, -114, 86, -105, 112, 37, 126, -79, 45, -51, -78, 34, -68, -59, 38, -65, 98, 45};
    private static final String tag = "SectorSettingsFrag";
    private TextView headerText;
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
    private byte[] sectorAssignment;
    private SectorSettingsFragmentListener sectorSettingsFragmentListener;
    private TextView sectorText;

    public interface SectorSettingsFragmentListener {
        byte[] getSectorAssignment();

        void setSectorAssignment(byte[] bArr);

        void setSectorData(byte[] bArr);
    }

    public static SectorSettingsFragment newInstance() {
        return new SectorSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sectorAssignment = this.sectorSettingsFragmentListener.getSectorAssignment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sector_settings, container, false);
        this.headerText = (TextView) view.findViewById(R.id.headerText);
        this.sectorText = (TextView) view.findViewById(R.id.sectorText);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.headerText.setTypeface(font);
        this.sectorText.setTypeface(font);
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
                SectorSettingsFragment.this.sectorOnClick(1);
            }
        });
        this.sector2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(2);
            }
        });
        this.sector3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(3);
            }
        });
        this.sector4.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(4);
            }
        });
        this.sector5.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(5);
            }
        });
        this.sector6.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(6);
            }
        });
        this.sector7.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(7);
            }
        });
        this.sector8.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(8);
            }
        });
        this.sector9.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(9);
            }
        });
        this.sector10.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(10);
            }
        });
        this.sector11.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(11);
            }
        });
        this.sector12.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SectorSettingsFragment.this.sectorOnClick(12);
            }
        });
        redrawFragment();
        return view;
    }

    /* access modifiers changed from: private */
    public void sectorOnClick(int sectorNumber) {
        byte[] bArr;
        Log.i(tag, "sector" + sectorNumber);
        boolean sectorRemoved = false;
        ByteArrayOutputStream newArray = new ByteArrayOutputStream();
        for (byte b : this.sectorAssignment) {
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
        this.sectorAssignment = Arrays.copyOf(newArray.toByteArray(), 15);
        this.sectorSettingsFragmentListener.setSectorAssignment(this.sectorAssignment);
        this.sectorSettingsFragmentListener.setSectorData(sectorData);
        redrawFragment();
    }

    private void redrawFragment() {
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
        this.sector1.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector2.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector3.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector4.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector5.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector6.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector7.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector8.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector9.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector10.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector11.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        this.sector12.setColorFilter(ContextCompat.getColor(getContext(), R.color.modeTextBlurredOut));
        for (byte b : this.sectorAssignment) {
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

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SectorSettingsFragmentListener) {
            this.sectorSettingsFragmentListener = (SectorSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement Listener");
    }

    public void onDetach() {
        super.onDetach();
        this.sectorSettingsFragmentListener = null;
    }
}

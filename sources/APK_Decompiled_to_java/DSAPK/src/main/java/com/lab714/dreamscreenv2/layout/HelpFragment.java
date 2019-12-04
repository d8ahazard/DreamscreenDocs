package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;

public class HelpFragment extends Fragment {
    private static final String tag = "HelpFrag";
    private LinearLayout appTutorialLayout;
    private TextView appTutorialText;
    private TextView appTutorialText2;
    private LinearLayout faqLayout;
    private TextView faqText;
    private TextView faqText2;
    /* access modifiers changed from: private */
    public HelpFragmentListener helpFragmentListener;
    private LinearLayout setupLayout;
    private TextView setupText;
    private TextView setupText2;

    public interface HelpFragmentListener {
        void startTutorial();
    }

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        this.faqText = (TextView) view.findViewById(R.id.faqText);
        this.faqText2 = (TextView) view.findViewById(R.id.faqText2);
        this.setupText = (TextView) view.findViewById(R.id.setupText);
        this.setupText2 = (TextView) view.findViewById(R.id.setupText2);
        this.appTutorialText = (TextView) view.findViewById(R.id.appTutorialText);
        this.appTutorialText2 = (TextView) view.findViewById(R.id.appTutorialText2);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.faqText.setTypeface(font);
        this.faqText2.setTypeface(font);
        this.setupText.setTypeface(font);
        this.setupText2.setTypeface(font);
        this.appTutorialText.setTypeface(font);
        this.appTutorialText2.setTypeface(font);
        this.faqLayout = (LinearLayout) view.findViewById(R.id.faqLayout);
        this.faqLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HelpFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.dreamscreentv.com/faq/")));
            }
        });
        this.setupLayout = (LinearLayout) view.findViewById(R.id.setupLayout);
        this.setupLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HelpFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.dreamscreentv.com/setup/")));
            }
        });
        this.appTutorialLayout = (LinearLayout) view.findViewById(R.id.appTutorialLayout);
        this.appTutorialLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (HelpFragment.this.helpFragmentListener != null) {
                    HelpFragment.this.helpFragmentListener.startTutorial();
                }
            }
        });
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HelpFragmentListener) {
            this.helpFragmentListener = (HelpFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement SystemSettingsFragmentListener");
    }

    public void onDetach() {
        super.onDetach();
        this.helpFragmentListener = null;
    }
}

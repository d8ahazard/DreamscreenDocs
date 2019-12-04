package com.lab714.dreamscreenv2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {
    private static final String dsv2Prefs = "dsv2Prefs";
    private static final int pageCount = 14;
    private static final String tag = "TutorialActivity";
    private static final String tutorialShownKey = "tutorialShownKey";
    /* access modifiers changed from: private */
    public int currentPage = 0;
    private TutorialFragmentPagerAdapter fragmentPagerAdapter;
    /* access modifiers changed from: private */
    public ImageButton learnMoreButton;
    /* access modifiers changed from: private */
    public TextView nextText;
    private OnPageChangeListener onPageChangeListener;
    private TextView skipText;
    /* access modifiers changed from: private */
    public ViewPager viewPager;

    public static class TutorialFragment extends Fragment {
        int pageNumber;

        static TutorialFragment newInstance(int num) {
            TutorialFragment tutorialFragment = new TutorialFragment();
            Bundle args = new Bundle();
            args.putInt("pageNumber", num);
            tutorialFragment.setArguments(args);
            return tutorialFragment;
        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.pageNumber = getArguments() != null ? getArguments().getInt("pageNumber") : 1;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Log.i(TutorialActivity.tag, "displaying pageNumber " + this.pageNumber);
            View view = inflater.inflate(R.layout.activity_tutorial_picture, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            switch (this.pageNumber) {
                case 0:
                    imageView.setImageResource(R.drawable.tutorial_page1);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.tutorial_page2);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.tutorial_page3);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.tutorial_page4);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.tutorial_page5);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.tutorial_page6);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.tutorial_page7);
                    break;
                case 7:
                    imageView.setImageResource(R.drawable.tutorial_page8);
                    break;
                case 8:
                    imageView.setImageResource(R.drawable.tutorial_page9);
                    break;
                case 9:
                    imageView.setImageResource(R.drawable.tutorial_page10);
                    break;
                case 10:
                    imageView.setImageResource(R.drawable.tutorial_page11);
                    break;
                case 11:
                    imageView.setImageResource(R.drawable.tutorial_page12);
                    break;
                case 12:
                    imageView.setImageResource(R.drawable.tutorial_page13);
                    break;
                case 13:
                    imageView.setImageResource(R.drawable.tutorial_page14);
                    break;
                default:
                    Log.i(TutorialActivity.tag, "error, bad pageNumber");
                    break;
            }
            return view;
        }
    }

    public static class TutorialFragmentPagerAdapter extends FragmentPagerAdapter {
        public TutorialFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public int getCount() {
            return 14;
        }

        public Fragment getItem(int position) {
            Log.i(TutorialActivity.tag, "getItem " + position);
            return TutorialFragment.newInstance(position);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_tutorial);
        Log.i(tag, "onCreate");
        Typeface font = Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf");
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.fragmentPagerAdapter = new TutorialFragmentPagerAdapter(getSupportFragmentManager());
        this.viewPager.setAdapter(this.fragmentPagerAdapter);
        this.learnMoreButton = (ImageButton) findViewById(R.id.learnMoreButton);
        this.learnMoreButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TutorialActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.dreamscreentv.com/setup/")));
            }
        });
        this.skipText = (TextView) findViewById(R.id.skipText);
        this.skipText.setTypeface(font);
        this.skipText.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TutorialActivity.this.startMainActivity();
            }
        });
        this.nextText = (TextView) findViewById(R.id.nextText);
        this.nextText.setTypeface(font);
        this.nextText.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TutorialActivity.this.currentPage == 13) {
                    Log.i(TutorialActivity.tag, "tutorial done, start mainActivity");
                    TutorialActivity.this.startMainActivity();
                    return;
                }
                TutorialActivity.this.viewPager.setCurrentItem(TutorialActivity.this.currentPage + 1);
            }
        });
        this.onPageChangeListener = new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                Log.i(TutorialActivity.tag, "onPageSelected " + position);
                TutorialActivity.this.currentPage = position;
                if (TutorialActivity.this.currentPage == 13) {
                    TutorialActivity.this.nextText.setText(TutorialActivity.this.getResources().getString(R.string.DONE));
                    TutorialActivity.this.learnMoreButton.setVisibility(0);
                    return;
                }
                TutorialActivity.this.nextText.setText(TutorialActivity.this.getResources().getString(R.string.NEXT));
                TutorialActivity.this.learnMoreButton.setVisibility(8);
            }

            public void onPageScrollStateChanged(int state) {
            }
        };
        this.viewPager.addOnPageChangeListener(this.onPageChangeListener);
    }

    /* access modifiers changed from: private */
    @SuppressLint({"ApplySharedPref"})
    public void startMainActivity() {
        Log.i(tag, "startMainActivity");
        Editor editor = getSharedPreferences(dsv2Prefs, 0).edit();
        editor.putBoolean(tutorialShownKey, true);
        editor.commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(268451840);
        startActivity(intent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Log.i(tag, "onDestroy");
        if (!(this.viewPager == null || this.onPageChangeListener == null)) {
            this.viewPager.removeOnPageChangeListener(this.onPageChangeListener);
        }
        this.viewPager = null;
        this.onPageChangeListener = null;
        super.onDestroy();
    }
}

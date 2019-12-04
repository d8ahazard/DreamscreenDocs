package androidx.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.DecorView;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.core.widget.TextViewCompat;
import android.text.TextUtils.TruncateAt;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Locale;

@DecorView
public class PagerTitleStrip extends ViewGroup {
    private static final int[] ATTRS = {16842804, 16842901, 16842904, 16842927};
    private static final float SIDE_ALPHA = 0.6f;
    private static final int[] TEXT_ATTRS = {16843660};
    private static final int TEXT_SPACING = 16;
    TextView mCurrText;
    private int mGravity;
    private int mLastKnownCurrentPage;
    float mLastKnownPositionOffset;
    TextView mNextText;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener;
    ViewPager mPager;
    TextView mPrevText;
    private int mScaledTextSpacing;
    int mTextColor;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;

    private class PageListener extends DataSetObserver implements OnPageChangeListener, OnAdapterChangeListener {
        private int mScrollState;

        PageListener() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset > 0.5f) {
                position++;
            }
            androidx.viewpager.widget.PagerTitleStrip.this.updateTextPositions(position, positionOffset, false);
        }

        public void onPageSelected(int position) {
            float offset = 0.0f;
            if (this.mScrollState == 0) {
                androidx.viewpager.widget.PagerTitleStrip.this.updateText(androidx.viewpager.widget.PagerTitleStrip.this.mPager.getCurrentItem(), androidx.viewpager.widget.PagerTitleStrip.this.mPager.getAdapter());
                if (androidx.viewpager.widget.PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                    offset = androidx.viewpager.widget.PagerTitleStrip.this.mLastKnownPositionOffset;
                }
                androidx.viewpager.widget.PagerTitleStrip.this.updateTextPositions(androidx.viewpager.widget.PagerTitleStrip.this.mPager.getCurrentItem(), offset, true);
            }
        }

        public void onPageScrollStateChanged(int state) {
            this.mScrollState = state;
        }

        public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter) {
            androidx.viewpager.widget.PagerTitleStrip.this.updateAdapter(oldAdapter, newAdapter);
        }

        public void onChanged() {
            float offset = 0.0f;
            androidx.viewpager.widget.PagerTitleStrip.this.updateText(androidx.viewpager.widget.PagerTitleStrip.this.mPager.getCurrentItem(), androidx.viewpager.widget.PagerTitleStrip.this.mPager.getAdapter());
            if (androidx.viewpager.widget.PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                offset = androidx.viewpager.widget.PagerTitleStrip.this.mLastKnownPositionOffset;
            }
            androidx.viewpager.widget.PagerTitleStrip.this.updateTextPositions(androidx.viewpager.widget.PagerTitleStrip.this.mPager.getCurrentItem(), offset, true);
        }
    }

    private static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        private Locale mLocale;

        SingleLineAllCapsTransform(Context context) {
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        public CharSequence getTransformation(CharSequence source, View view) {
            CharSequence source2 = super.getTransformation(source, view);
            if (source2 != null) {
                return source2.toString().toUpperCase(this.mLocale);
            }
            return null;
        }
    }

    private static void setSingleLineAllCaps(TextView text) {
        text.setTransformationMethod(new SingleLineAllCapsTransform(text.getContext()));
    }

    public PagerTitleStrip(@NonNull Context context) {
        this(context, null);
    }

    public PagerTitleStrip(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mLastKnownCurrentPage = -1;
        this.mLastKnownPositionOffset = -1.0f;
        this.mPageListener = new PageListener();
        TextView textView = new TextView(context);
        this.mPrevText = textView;
        addView(textView);
        TextView textView2 = new TextView(context);
        this.mCurrText = textView2;
        addView(textView2);
        TextView textView3 = new TextView(context);
        this.mNextText = textView3;
        addView(textView3);
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        int textAppearance = a.getResourceId(0, 0);
        if (textAppearance != 0) {
            TextViewCompat.setTextAppearance(this.mPrevText, textAppearance);
            TextViewCompat.setTextAppearance(this.mCurrText, textAppearance);
            TextViewCompat.setTextAppearance(this.mNextText, textAppearance);
        }
        int textSize = a.getDimensionPixelSize(1, 0);
        if (textSize != 0) {
            setTextSize(0, (float) textSize);
        }
        if (a.hasValue(2)) {
            int textColor = a.getColor(2, 0);
            this.mPrevText.setTextColor(textColor);
            this.mCurrText.setTextColor(textColor);
            this.mNextText.setTextColor(textColor);
        }
        this.mGravity = a.getInteger(3, 80);
        a.recycle();
        this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(SIDE_ALPHA);
        this.mPrevText.setEllipsize(TruncateAt.END);
        this.mCurrText.setEllipsize(TruncateAt.END);
        this.mNextText.setEllipsize(TruncateAt.END);
        boolean allCaps = false;
        if (textAppearance != 0) {
            TypedArray ta = context.obtainStyledAttributes(textAppearance, TEXT_ATTRS);
            allCaps = ta.getBoolean(0, false);
            ta.recycle();
        }
        if (allCaps) {
            setSingleLineAllCaps(this.mPrevText);
            setSingleLineAllCaps(this.mCurrText);
            setSingleLineAllCaps(this.mNextText);
        } else {
            this.mPrevText.setSingleLine();
            this.mCurrText.setSingleLine();
            this.mNextText.setSingleLine();
        }
        this.mScaledTextSpacing = (int) (16.0f * context.getResources().getDisplayMetrics().density);
    }

    public void setTextSpacing(int spacingPixels) {
        this.mScaledTextSpacing = spacingPixels;
        requestLayout();
    }

    public int getTextSpacing() {
        return this.mScaledTextSpacing;
    }

    public void setNonPrimaryAlpha(@FloatRange(from = 0.0d, to = 1.0d) float alpha) {
        this.mNonPrimaryAlpha = ((int) (255.0f * alpha)) & 255;
        int transparentColor = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        this.mPrevText.setTextColor(transparentColor);
        this.mNextText.setTextColor(transparentColor);
    }

    public void setTextColor(@ColorInt int color) {
        this.mTextColor = color;
        this.mCurrText.setTextColor(color);
        int transparentColor = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        this.mPrevText.setTextColor(transparentColor);
        this.mNextText.setTextColor(transparentColor);
    }

    public void setTextSize(int unit, float size) {
        this.mPrevText.setTextSize(unit, size);
        this.mCurrText.setTextSize(unit, size);
        this.mNextText.setTextSize(unit, size);
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (!(parent instanceof ViewPager)) {
            throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
        }
        ViewPager pager = (ViewPager) parent;
        PagerAdapter adapter = pager.getAdapter();
        pager.setInternalPageChangeListener(this.mPageListener);
        pager.addOnAdapterChangeListener(this.mPageListener);
        this.mPager = pager;
        updateAdapter(this.mWatchingAdapter != null ? (PagerAdapter) this.mWatchingAdapter.get() : null, adapter);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPager != null) {
            updateAdapter(this.mPager.getAdapter(), null);
            this.mPager.setInternalPageChangeListener(null);
            this.mPager.removeOnAdapterChangeListener(this.mPageListener);
            this.mPager = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateText(int currentItem, PagerAdapter adapter) {
        int itemCount;
        if (adapter != null) {
            itemCount = adapter.getCount();
        } else {
            itemCount = 0;
        }
        this.mUpdatingText = true;
        CharSequence text = null;
        if (currentItem >= 1 && adapter != null) {
            text = adapter.getPageTitle(currentItem - 1);
        }
        this.mPrevText.setText(text);
        this.mCurrText.setText((adapter == null || currentItem >= itemCount) ? null : adapter.getPageTitle(currentItem));
        CharSequence text2 = null;
        if (currentItem + 1 < itemCount && adapter != null) {
            text2 = adapter.getPageTitle(currentItem + 1);
        }
        this.mNextText.setText(text2);
        int childWidthSpec = MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) * 0.8f)), Integer.MIN_VALUE);
        int childHeightSpec = MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE);
        this.mPrevText.measure(childWidthSpec, childHeightSpec);
        this.mCurrText.measure(childWidthSpec, childHeightSpec);
        this.mNextText.measure(childWidthSpec, childHeightSpec);
        this.mLastKnownCurrentPage = currentItem;
        if (!this.mUpdatingPositions) {
            updateTextPositions(currentItem, this.mLastKnownPositionOffset, false);
        }
        this.mUpdatingText = false;
    }

    public void requestLayout() {
        if (!this.mUpdatingText) {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateAdapter(PagerAdapter oldAdapter, PagerAdapter newAdapter) {
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (newAdapter != null) {
            newAdapter.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference<>(newAdapter);
        }
        if (this.mPager != null) {
            this.mLastKnownCurrentPage = -1;
            this.mLastKnownPositionOffset = -1.0f;
            updateText(this.mPager.getCurrentItem(), newAdapter);
            requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateTextPositions(int position, float positionOffset, boolean force) {
        int prevTop;
        int currTop;
        int nextTop;
        if (position != this.mLastKnownCurrentPage) {
            updateText(position, this.mPager.getAdapter());
        } else if (!force && positionOffset == this.mLastKnownPositionOffset) {
            return;
        }
        this.mUpdatingPositions = true;
        int prevWidth = this.mPrevText.getMeasuredWidth();
        int currWidth = this.mCurrText.getMeasuredWidth();
        int nextWidth = this.mNextText.getMeasuredWidth();
        int halfCurrWidth = currWidth / 2;
        int stripWidth = getWidth();
        int stripHeight = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int textPaddedRight = paddingRight + halfCurrWidth;
        int contentWidth = (stripWidth - (paddingLeft + halfCurrWidth)) - textPaddedRight;
        float currOffset = positionOffset + 0.5f;
        if (currOffset > 1.0f) {
            currOffset -= 1.0f;
        }
        int currLeft = ((stripWidth - textPaddedRight) - ((int) (((float) contentWidth) * currOffset))) - (currWidth / 2);
        int currRight = currLeft + currWidth;
        int prevBaseline = this.mPrevText.getBaseline();
        int currBaseline = this.mCurrText.getBaseline();
        int nextBaseline = this.mNextText.getBaseline();
        int maxBaseline = Math.max(Math.max(prevBaseline, currBaseline), nextBaseline);
        int prevTopOffset = maxBaseline - prevBaseline;
        int currTopOffset = maxBaseline - currBaseline;
        int nextTopOffset = maxBaseline - nextBaseline;
        int alignedNextHeight = nextTopOffset + this.mNextText.getMeasuredHeight();
        int maxTextHeight = Math.max(Math.max(prevTopOffset + this.mPrevText.getMeasuredHeight(), currTopOffset + this.mCurrText.getMeasuredHeight()), alignedNextHeight);
        switch (this.mGravity & 112) {
            case 16:
                int centeredTop = (((stripHeight - paddingTop) - paddingBottom) - maxTextHeight) / 2;
                prevTop = centeredTop + prevTopOffset;
                currTop = centeredTop + currTopOffset;
                nextTop = centeredTop + nextTopOffset;
                break;
            case 80:
                int bottomGravTop = (stripHeight - paddingBottom) - maxTextHeight;
                prevTop = bottomGravTop + prevTopOffset;
                currTop = bottomGravTop + currTopOffset;
                nextTop = bottomGravTop + nextTopOffset;
                break;
            default:
                prevTop = paddingTop + prevTopOffset;
                currTop = paddingTop + currTopOffset;
                nextTop = paddingTop + nextTopOffset;
                break;
        }
        this.mCurrText.layout(currLeft, currTop, currRight, this.mCurrText.getMeasuredHeight() + currTop);
        int prevLeft = Math.min(paddingLeft, (currLeft - this.mScaledTextSpacing) - prevWidth);
        this.mPrevText.layout(prevLeft, prevTop, prevLeft + prevWidth, this.mPrevText.getMeasuredHeight() + prevTop);
        int nextLeft = Math.max((stripWidth - paddingRight) - nextWidth, this.mScaledTextSpacing + currRight);
        this.mNextText.layout(nextLeft, nextTop, nextLeft + nextWidth, this.mNextText.getMeasuredHeight() + nextTop);
        this.mLastKnownPositionOffset = positionOffset;
        this.mUpdatingPositions = false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height;
        if (MeasureSpec.getMode(widthMeasureSpec) != 1073741824) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        int heightPadding = getPaddingTop() + getPaddingBottom();
        int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding, -2);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, (int) (((float) widthSize) * 0.2f), -2);
        this.mPrevText.measure(childWidthSpec, childHeightSpec);
        this.mCurrText.measure(childWidthSpec, childHeightSpec);
        this.mNextText.measure(childWidthSpec, childHeightSpec);
        if (MeasureSpec.getMode(heightMeasureSpec) == 1073741824) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = Math.max(getMinHeight(), this.mCurrText.getMeasuredHeight() + heightPadding);
        }
        setMeasuredDimension(widthSize, View.resolveSizeAndState(height, heightMeasureSpec, this.mCurrText.getMeasuredState() << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        float offset = 0.0f;
        if (this.mPager != null) {
            if (this.mLastKnownPositionOffset >= 0.0f) {
                offset = this.mLastKnownPositionOffset;
            }
            updateTextPositions(this.mLastKnownCurrentPage, offset, true);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getMinHeight() {
        Drawable bg = getBackground();
        if (bg != null) {
            return bg.getIntrinsicHeight();
        }
        return 0;
    }
}

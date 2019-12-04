package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import android.support.design.R;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView.ItemView;
import androidx.appcompat.widget.TooltipCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class BottomNavigationItemView extends FrameLayout implements ItemView {
    private static final int[] CHECKED_STATE_SET = {16842912};
    public static final int INVALID_ITEM_POSITION = -1;
    private final int mDefaultMargin;
    private ImageView mIcon;
    private ColorStateList mIconTint;
    private MenuItemImpl mItemData;
    private int mItemPosition;
    private final TextView mLargeLabel;
    private final float mScaleDownFactor;
    private final float mScaleUpFactor;
    private final int mShiftAmount;
    private boolean mShiftingMode;
    private final TextView mSmallLabel;

    public BottomNavigationItemView(@NonNull Context context) {
        this(context, null);
    }

    public BottomNavigationItemView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mItemPosition = -1;
        Resources res = getResources();
        int inactiveLabelSize = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_text_size);
        int activeLabelSize = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_text_size);
        this.mDefaultMargin = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_margin);
        this.mShiftAmount = inactiveLabelSize - activeLabelSize;
        this.mScaleUpFactor = (((float) activeLabelSize) * 1.0f) / ((float) inactiveLabelSize);
        this.mScaleDownFactor = (((float) inactiveLabelSize) * 1.0f) / ((float) activeLabelSize);
        LayoutInflater.from(context).inflate(R.layout.design_bottom_navigation_item, this, true);
        setBackgroundResource(R.drawable.design_bottom_navigation_item_background);
        this.mIcon = (ImageView) findViewById(R.id.icon);
        this.mSmallLabel = (TextView) findViewById(R.id.smallLabel);
        this.mLargeLabel = (TextView) findViewById(R.id.largeLabel);
    }

    public void initialize(MenuItemImpl itemData, int menuType) {
        this.mItemData = itemData;
        setCheckable(itemData.isCheckable());
        setChecked(itemData.isChecked());
        setEnabled(itemData.isEnabled());
        setIcon(itemData.getIcon());
        setTitle(itemData.getTitle());
        setId(itemData.getItemId());
        setContentDescription(itemData.getContentDescription());
        TooltipCompat.setTooltipText(this, itemData.getTooltipText());
    }

    public void setItemPosition(int position) {
        this.mItemPosition = position;
    }

    public int getItemPosition() {
        return this.mItemPosition;
    }

    public void setShiftingMode(boolean enabled) {
        this.mShiftingMode = enabled;
    }

    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public void setTitle(CharSequence title) {
        this.mSmallLabel.setText(title);
        this.mLargeLabel.setText(title);
    }

    public void setCheckable(boolean checkable) {
        refreshDrawableState();
    }

    public void setChecked(boolean checked) {
        this.mLargeLabel.setPivotX((float) (this.mLargeLabel.getWidth() / 2));
        this.mLargeLabel.setPivotY((float) this.mLargeLabel.getBaseline());
        this.mSmallLabel.setPivotX((float) (this.mSmallLabel.getWidth() / 2));
        this.mSmallLabel.setPivotY((float) this.mSmallLabel.getBaseline());
        if (this.mShiftingMode) {
            if (checked) {
                LayoutParams iconParams = (LayoutParams) this.mIcon.getLayoutParams();
                iconParams.gravity = 49;
                iconParams.topMargin = this.mDefaultMargin;
                this.mIcon.setLayoutParams(iconParams);
                this.mLargeLabel.setVisibility(0);
                this.mLargeLabel.setScaleX(1.0f);
                this.mLargeLabel.setScaleY(1.0f);
            } else {
                LayoutParams iconParams2 = (LayoutParams) this.mIcon.getLayoutParams();
                iconParams2.gravity = 17;
                iconParams2.topMargin = this.mDefaultMargin;
                this.mIcon.setLayoutParams(iconParams2);
                this.mLargeLabel.setVisibility(4);
                this.mLargeLabel.setScaleX(0.5f);
                this.mLargeLabel.setScaleY(0.5f);
            }
            this.mSmallLabel.setVisibility(4);
        } else if (checked) {
            LayoutParams iconParams3 = (LayoutParams) this.mIcon.getLayoutParams();
            iconParams3.gravity = 49;
            iconParams3.topMargin = this.mDefaultMargin + this.mShiftAmount;
            this.mIcon.setLayoutParams(iconParams3);
            this.mLargeLabel.setVisibility(0);
            this.mSmallLabel.setVisibility(4);
            this.mLargeLabel.setScaleX(1.0f);
            this.mLargeLabel.setScaleY(1.0f);
            this.mSmallLabel.setScaleX(this.mScaleUpFactor);
            this.mSmallLabel.setScaleY(this.mScaleUpFactor);
        } else {
            LayoutParams iconParams4 = (LayoutParams) this.mIcon.getLayoutParams();
            iconParams4.gravity = 49;
            iconParams4.topMargin = this.mDefaultMargin;
            this.mIcon.setLayoutParams(iconParams4);
            this.mLargeLabel.setVisibility(4);
            this.mSmallLabel.setVisibility(0);
            this.mLargeLabel.setScaleX(this.mScaleDownFactor);
            this.mLargeLabel.setScaleY(this.mScaleDownFactor);
            this.mSmallLabel.setScaleX(1.0f);
            this.mSmallLabel.setScaleY(1.0f);
        }
        refreshDrawableState();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.mSmallLabel.setEnabled(enabled);
        this.mLargeLabel.setEnabled(enabled);
        this.mIcon.setEnabled(enabled);
        if (enabled) {
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND));
        } else {
            ViewCompat.setPointerIcon(this, null);
        }
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (this.mItemData != null && this.mItemData.isCheckable() && this.mItemData.isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    public void setIcon(Drawable icon) {
        if (icon != null) {
            ConstantState state = icon.getConstantState();
            if (state != null) {
                icon = state.newDrawable();
            }
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon, this.mIconTint);
        }
        this.mIcon.setImageDrawable(icon);
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return true;
    }

    public void setIconTintList(ColorStateList tint) {
        this.mIconTint = tint;
        if (this.mItemData != null) {
            setIcon(this.mItemData.getIcon());
        }
    }

    public void setTextColor(ColorStateList color) {
        this.mSmallLabel.setTextColor(color);
        this.mLargeLabel.setTextColor(color);
    }

    public void setItemBackground(int background) {
        Drawable backgroundDrawable;
        if (background == 0) {
            backgroundDrawable = null;
        } else {
            backgroundDrawable = ContextCompat.getDrawable(getContext(), background);
        }
        ViewCompat.setBackground(this, backgroundDrawable);
    }
}

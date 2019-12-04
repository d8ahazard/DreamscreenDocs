package androidx.core.widget;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.annotation.StyleRes;
import androidx.core.os.BuildCompat;
import android.util.Log;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    static final TextViewCompatBaseImpl IMPL;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoSizeTextType {
    }

    @RequiresApi(16)
    static class TextViewCompatApi16Impl extends TextViewCompatBaseImpl {
        TextViewCompatApi16Impl() {
        }

        public int getMaxLines(TextView textView) {
            return textView.getMaxLines();
        }

        public int getMinLines(TextView textView) {
            return textView.getMinLines();
        }
    }

    @RequiresApi(17)
    static class TextViewCompatApi17Impl extends TextViewCompatApi16Impl {
        TextViewCompatApi17Impl() {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            Drawable drawable;
            boolean rtl = true;
            if (textView.getLayoutDirection() != 1) {
                rtl = false;
            }
            if (rtl) {
                drawable = end;
            } else {
                drawable = start;
            }
            if (!rtl) {
                start = end;
            }
            textView.setCompoundDrawables(drawable, top, start, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            Drawable drawable;
            boolean rtl = true;
            if (textView.getLayoutDirection() != 1) {
                rtl = false;
            }
            if (rtl) {
                drawable = end;
            } else {
                drawable = start;
            }
            if (!rtl) {
                start = end;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, top, start, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
            int i;
            boolean rtl = true;
            if (textView.getLayoutDirection() != 1) {
                rtl = false;
            }
            if (rtl) {
                i = end;
            } else {
                i = start;
            }
            if (!rtl) {
                start = end;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(i, top, start, bottom);
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            boolean rtl = true;
            if (textView.getLayoutDirection() != 1) {
                rtl = false;
            }
            Drawable[] compounds = textView.getCompoundDrawables();
            if (rtl) {
                Drawable start = compounds[2];
                Drawable end = compounds[0];
                compounds[0] = start;
                compounds[2] = end;
            }
            return compounds;
        }
    }

    @RequiresApi(18)
    static class TextViewCompatApi18Impl extends TextViewCompatApi17Impl {
        TextViewCompatApi18Impl() {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            textView.setCompoundDrawablesRelative(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }
    }

    @RequiresApi(23)
    static class TextViewCompatApi23Impl extends TextViewCompatApi18Impl {
        TextViewCompatApi23Impl() {
        }

        public void setTextAppearance(@NonNull TextView textView, @StyleRes int resId) {
            textView.setTextAppearance(resId);
        }
    }

    @RequiresApi(27)
    static class TextViewCompatApi27Impl extends TextViewCompatApi23Impl {
        TextViewCompatApi27Impl() {
        }

        public void setAutoSizeTextTypeWithDefaults(TextView textView, int autoSizeTextType) {
            textView.setAutoSizeTextTypeWithDefaults(autoSizeTextType);
        }

        public void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
        }

        public void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
        }

        public int getAutoSizeTextType(TextView textView) {
            return textView.getAutoSizeTextType();
        }

        public int getAutoSizeStepGranularity(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        public int getAutoSizeMinTextSize(TextView textView) {
            return textView.getAutoSizeMinTextSize();
        }

        public int getAutoSizeMaxTextSize(TextView textView) {
            return textView.getAutoSizeMaxTextSize();
        }

        public int[] getAutoSizeTextAvailableSizes(TextView textView) {
            return textView.getAutoSizeTextAvailableSizes();
        }
    }

    static class TextViewCompatBaseImpl {
        private static final int LINES = 1;
        private static final String LOG_TAG = "TextViewCompatBase";
        private static Field sMaxModeField;
        private static boolean sMaxModeFieldFetched;
        private static Field sMaximumField;
        private static boolean sMaximumFieldFetched;
        private static Field sMinModeField;
        private static boolean sMinModeFieldFetched;
        private static Field sMinimumField;
        private static boolean sMinimumFieldFetched;

        TextViewCompatBaseImpl() {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            textView.setCompoundDrawables(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            textView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
            textView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }

        private static Field retrieveField(String fieldName) {
            Field field = null;
            try {
                field = TextView.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                Log.e(LOG_TAG, "Could not retrieve " + fieldName + " field.");
                return field;
            }
        }

        private static int retrieveIntFromField(Field field, TextView textView) {
            try {
                return field.getInt(textView);
            } catch (IllegalAccessException e) {
                Log.d(LOG_TAG, "Could not retrieve value of " + field.getName() + " field.");
                return -1;
            }
        }

        public int getMaxLines(TextView textView) {
            if (!sMaxModeFieldFetched) {
                sMaxModeField = retrieveField("mMaxMode");
                sMaxModeFieldFetched = true;
            }
            if (sMaxModeField != null && retrieveIntFromField(sMaxModeField, textView) == 1) {
                if (!sMaximumFieldFetched) {
                    sMaximumField = retrieveField("mMaximum");
                    sMaximumFieldFetched = true;
                }
                if (sMaximumField != null) {
                    return retrieveIntFromField(sMaximumField, textView);
                }
            }
            return -1;
        }

        public int getMinLines(TextView textView) {
            if (!sMinModeFieldFetched) {
                sMinModeField = retrieveField("mMinMode");
                sMinModeFieldFetched = true;
            }
            if (sMinModeField != null && retrieveIntFromField(sMinModeField, textView) == 1) {
                if (!sMinimumFieldFetched) {
                    sMinimumField = retrieveField("mMinimum");
                    sMinimumFieldFetched = true;
                }
                if (sMinimumField != null) {
                    return retrieveIntFromField(sMinimumField, textView);
                }
            }
            return -1;
        }

        public void setTextAppearance(TextView textView, @StyleRes int resId) {
            textView.setTextAppearance(textView.getContext(), resId);
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawables();
        }

        public void setAutoSizeTextTypeWithDefaults(TextView textView, int autoSizeTextType) {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeWithDefaults(autoSizeTextType);
            }
        }

        public void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
            }
        }

        public void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
            }
        }

        public int getAutoSizeTextType(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeTextType();
            }
            return 0;
        }

        public int getAutoSizeStepGranularity(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeStepGranularity();
            }
            return -1;
        }

        public int getAutoSizeMinTextSize(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMinTextSize();
            }
            return -1;
        }

        public int getAutoSizeMaxTextSize(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMaxTextSize();
            }
            return -1;
        }

        public int[] getAutoSizeTextAvailableSizes(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeTextAvailableSizes();
            }
            return new int[0];
        }
    }

    private TextViewCompat() {
    }

    static {
        if (BuildCompat.isAtLeastOMR1()) {
            IMPL = new TextViewCompatApi27Impl();
        } else if (VERSION.SDK_INT >= 23) {
            IMPL = new TextViewCompatApi23Impl();
        } else if (VERSION.SDK_INT >= 18) {
            IMPL = new TextViewCompatApi18Impl();
        } else if (VERSION.SDK_INT >= 17) {
            IMPL = new TextViewCompatApi17Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new TextViewCompatApi16Impl();
        } else {
            IMPL = new TextViewCompatBaseImpl();
        }
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        IMPL.setCompoundDrawablesRelative(textView, start, top, end, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, start, top, end, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, start, top, end, bottom);
    }

    public static int getMaxLines(@NonNull TextView textView) {
        return IMPL.getMaxLines(textView);
    }

    public static int getMinLines(@NonNull TextView textView) {
        return IMPL.getMinLines(textView);
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int resId) {
        IMPL.setTextAppearance(textView, resId);
    }

    @NonNull
    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        return IMPL.getCompoundDrawablesRelative(textView);
    }

    public static void setAutoSizeTextTypeWithDefaults(@NonNull TextView textView, int autoSizeTextType) {
        IMPL.setAutoSizeTextTypeWithDefaults(textView, autoSizeTextType);
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(@NonNull TextView textView, int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
        IMPL.setAutoSizeTextTypeUniformWithConfiguration(textView, autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull TextView textView, @NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
        IMPL.setAutoSizeTextTypeUniformWithPresetSizes(textView, presetSizes, unit);
    }

    public static int getAutoSizeTextType(@NonNull TextView textView) {
        return IMPL.getAutoSizeTextType(textView);
    }

    public static int getAutoSizeStepGranularity(@NonNull TextView textView) {
        return IMPL.getAutoSizeStepGranularity(textView);
    }

    public static int getAutoSizeMinTextSize(@NonNull TextView textView) {
        return IMPL.getAutoSizeMinTextSize(textView);
    }

    public static int getAutoSizeMaxTextSize(@NonNull TextView textView) {
        return IMPL.getAutoSizeMaxTextSize(textView);
    }

    @NonNull
    public static int[] getAutoSizeTextAvailableSizes(@NonNull TextView textView) {
        return IMPL.getAutoSizeTextAvailableSizes(textView);
    }
}

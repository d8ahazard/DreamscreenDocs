package com.lab714.dreamscreenv2.devices;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.DragEvent;

public class EditTextNoDrag extends AppCompatEditText {
    public EditTextNoDrag(Context context) {
        super(context);
    }

    public EditTextNoDrag(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextNoDrag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onDragEvent(DragEvent event) {
        return false;
    }
}

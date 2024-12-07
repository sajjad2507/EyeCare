package com.example.eyecare.ui.utils.services;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public final class OverlayView extends View {

    public int background;
    public int color;
    public Paint paint = new Paint();

    public OverlayView(Context context) {
        this(context, null);
    }

    public OverlayView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public OverlayView(Context context, AttributeSet attr, int defStyleAttr) {
        super(context, attr, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(this.background);
        this.paint.setColor(this.color);
        canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.paint);
    }

    public void setDimColor(int color) {
        this.color = color;
        invalidate();
    }

    public void setFilterColor(int background) {
        this.background = background;
        invalidate();
    }
}

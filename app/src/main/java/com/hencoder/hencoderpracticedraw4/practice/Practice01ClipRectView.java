package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice01ClipRectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    public Practice01ClipRectView(Context context) {
        super(context);
    }

    public Practice01ClipRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice01ClipRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Rect rect;

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = (getWidth() - bitmap.getWidth()) / 2;
        int top = (getHeight() - bitmap.getHeight()) / 2;

        int x = getWidth() / 2;
        int y = getHeight() / 2;

        if (rect == null) {
            rect = new Rect(x - 100, y - 100, x + 100, y + 100);
        }

        canvas.save();
        canvas.clipRect(rect);
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();
    }
}

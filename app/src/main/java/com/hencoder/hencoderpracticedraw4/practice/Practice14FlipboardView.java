package com.hencoder.hencoderpracticedraw4.practice;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice14FlipboardView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degree;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 180);

    public Practice14FlipboardView(Context context) {
        super(context);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

        animator.setDuration(2500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int l = centerX - bitmapWidth / 2;
        int t = centerY - bitmapHeight / 2;

        // 1.绘制上半部分，保持图片一半
        canvas.save();
        canvas.clipRect(0, 0, getWidth(), centerY);
        canvas.drawBitmap(bitmap, l, t, paint);
        canvas.restore();

        canvas.save();

        /*
        //2.下半部分绘制
        if (degree<90){
            //90以内 需要遮挡上半部的图片旋转，所以，只需要绘制 h/2 以下的部分
            canvas.clipRect(0, centerY, getWidth(), getHeight());
        }else {
            //90 - 180，之前的上半部分旋转到下半部分，这时需要遮挡下半部分
            canvas.clipRect(0, 0, getWidth(), centerY);
        }
        */

        camera.save();
        camera.rotateX(degree);
        canvas.translate(centerX, centerY);// step 2
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);// step 1
        camera.restore();

        //最后确定绘制的区域（下半部分），这样可以确保所有展示的部分都在下半区域，不需要进行角度的判断（隐藏上半部分和隐藏下半部分）
        canvas.clipRect(0, centerY, getWidth(), getHeight());
        canvas.drawBitmap(bitmap, l, t, paint);//中心点绘制
        canvas.restore();
    }
}

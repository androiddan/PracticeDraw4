package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);
    Camera camera = new Camera();

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final Matrix matrix;

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int hw = bitmap.getWidth() / 2;
        int hh = bitmap.getHeight() / 2;

        int centerX1 = point1.x + hw;
        int centerY1 = point1.y + hh;

        int centerX2 = point2.x + hw;
        int centerY2 = point2.y + hh;


        canvas.save();
        camera.save();
        camera.rotateX(30);
        canvas.translate(centerX1, centerY1);//2.在移动到原先位置
        //将camera投影到canvas上
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.translate(-centerX1, -centerY1);//1.先移动到原点 -> canvas的方法需要倒着写
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        camera.save();
        camera.rotateY(30);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX2, -centerY2); //pre表示该动作会拆入到‘队列’之前
        matrix.postTranslate(centerX2, centerY2); // post表示在队列之后插入执行
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}

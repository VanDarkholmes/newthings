package com.example.machenike.ok13.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.machenike.ok13.util.ImageUtil;


/**
 * Created by byy on 2018/6/27.
 */

public class MyView extends View {



    private int coinImageCount = 1;

    public void setCoinImageCount(int coinImageCount) {
        //每调用一次这个方法，我就重新绘制一些图片
        this.coinImageCount = coinImageCount;
        //每调用一次这个方法就会重新绘制
        postInvalidate();
    }

    public int getCoinImageCount() {
        return coinImageCount;
    }

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }







    @Override
    protected void onDraw(Canvas canvas) {


        //canvas 画布
        Paint paint = new Paint();

        //Bitmap bitmap, float left, float top, Paint paint
        for (int i = 0;i<coinImageCount;i++){
            canvas.drawBitmap(ImageUtil.coinImg,100+i*100,100,paint);
        }


    }
}

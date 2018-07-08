package com.example.machenike.ok13.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

/**
 * Created by byy on 2018/6/21.
 */

public class ImageUtil {
    //裁剪图片的类
    //Bitmap
    private  static Bitmap uiMain;
    public static Bitmap topStar;
    public static Bitmap selectBg;
    public static Bitmap answerBg;
    public static Bitmap topBgImg;

    public static Bitmap backBtnImg;
    public static Bitmap coinImg;
    public static Bitmap deleteImg;
    public static Bitmap hintImg;
    public static Bitmap questionType;




    public static void initImg(Activity a) throws IOException {
        //decodeStream 把图片文件转化为bitmap对象

        uiMain = BitmapFactory
                .decodeStream(a.getAssets().open("ui_main.png"));
        topStar = getImagePiece(1020,1939,108,108);
        //已经选择的关卡
        selectBg = getImagePiece(975,338,74,76);
        //要回答的关卡
        answerBg = getImagePiece(1602,155,66,63);

        topBgImg = getImagePiece(975,893,640,96);


        backBtnImg = getImagePiece(98,1972,96,72);
        coinImg = getImagePiece(1602,219,44,33);
        deleteImg = getImagePiece(1503,990,92,123);
        hintImg = getImagePiece(1579,1371,92,123);
        questionType = getImagePiece(1657,787,310,58);


    }

    public static Bitmap getImagePiece(int x,int y,int width,int height){
        Bitmap newBitmap = Bitmap.createBitmap(uiMain,x,y,width,height);
        return newBitmap;
    }

}

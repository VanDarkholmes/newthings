package com.example.machenike.ok13.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by byy on 2018/6/21.
 */

public class Globals {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static SQLiteDatabase db;
    public static float GRID_WIDTH;
    public static float GRID_SEP=5;

    public  static void init (Activity a){
        SCREEN_WIDTH = a.getWindowManager().getDefaultDisplay().getWidth();
        SCREEN_HEIGHT = a.getWindowManager().getDefaultDisplay().getHeight();

        //加载本地数据。初始化数据库
        DBUtils dbUtils = new DBUtils(a,"crazy",null,1);
        //只有执行这个方法，才会调用onCreate()
        db = dbUtils.getReadableDatabase();

        GRID_WIDTH = SCREEN_WIDTH/11;

        SharedPreferences sharedPreferences = a.getSharedPreferences("game_data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        int number = sharedPreferences.getInt("stageNum",0);
        if (number == 0){
            //表示第一次进入游戏
            //关卡数应该是1；
            editor.putInt("stageNum",1);
            //默认的金币数
            editor.putInt("coinCount",50);

            editor.commit();
        }
        try {
            ImageUtil.initImg(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


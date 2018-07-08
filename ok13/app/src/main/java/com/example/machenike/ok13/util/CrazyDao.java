package com.example.machenike.ok13.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by byy on 2018/6/21.
 */

//从数据库加载数据
public class CrazyDao {

    //从数据库中加载数据，
    public static List<Map<String,Object>> getStageNum(int defaultStage){
        String sql = "select id from crazy";
        SQLiteDatabase sqLiteDatabase = Globals.db;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst();
        List<Map<String,Object>>maps = new ArrayList<>();
        while(!cursor.isAfterLast()){
            Map<String,Object>map = new HashMap<>();
            map.put("stageNum",cursor.getInt(0));
            if (cursor.getInt(0)<=defaultStage){
                map.put("overFlag",true);
            }else{
                map.put("overFlag",false);
            }
            maps.add(map);
            cursor.moveToNext();
        }
        cursor.close();
        return  maps;
    }
    //根据关卡数找到对应的关卡数据
    public static Map<String,Object> queryDataByStage(int stageNum) {


        String sql = "select question_img,question_type,answer,select_txt from crazy where id=?";
        //执行sql
        SQLiteDatabase sqLiteDatabase = Globals.db;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{stageNum+""});
        cursor.moveToFirst();
        Map<String,Object>map = new HashMap<>();
        map.put("questionImg",cursor.getString(0));
        map.put("questionType",cursor.getString(1));
        map.put("answer",cursor.getString(2));
        map.put("selectTxt",cursor.getString(3));
        return  map;
    }
}

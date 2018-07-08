package com.example.machenike.ok13.util;

/**
 * Created by byy on 2018/6/21.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/***
 * 这个类作用就是创建应用数据库
 */
public class DBUtils extends SQLiteOpenHelper{
    private Random random = new Random();
    private Context context;
    /***
     * SQLiteOpenHelper是一个抽象类，要实现两个抽象方法和一个构造方法
     * name:数据库的名称
     * CursorFactory：游标创建的工厂 null,
     * version:数据库的版本
     */
    public DBUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //当创建这个SQLiteOpenHelper这个对象的时候，这个方法会被调用一次，而且永远只调用一次
        //表就可以在这里建立
        String sql = "create table crazy(" +
                " id integer primary key autoincrement ," +
                " question_img text," +
                " question_type text," +
                " answer text," +
                " select_txt text)";
        sqLiteDatabase.execSQL(sql);
        //还要初始化数据
        try {
            initData(sqLiteDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //将本地数据保存到数据库
    private void initData(SQLiteDatabase sqLiteDatabase) throws IOException {
        //本地数据都存在/assets/问题答案、/assets/问题图片
        //以问题答案为主，每一个txt文件都表示一个题目
        //每一个txt文件中第一行是 answer
        //第二行是 question_type
        //question_img:是一个路径，是问题答案文件的名称+png
        String sql = "insert into crazy (question_img,question_type,answer,select_txt) values (?,?,?,?)";
        String[] pathes = this.context.getAssets().list("questionTxt");
        for(String path : pathes){
            InputStream inputStream = context.getAssets().open("questionTxt/"+path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"GBK"));
            String line = null;
            int index = 0;
            String questionImage = null;
            String questionType = null;
            String answer = null;
            String selectTxt = null;
            while((line = bufferedReader.readLine())!=null){
                index++;
                if (index == 1){
                    //答案没有经过特殊处理， 每一道题答案字段在数据库中存出是 正确答案+随机字符串
                    answer = line;
                }else if(index == 2){
                    questionType = line;
                }
                questionImage = "questionImg/"
                        +path.substring(0,path.lastIndexOf("."))+".png";
                selectTxt = getSelectTxt(answer);
            }
            //执行SQL
            sqLiteDatabase.execSQL(sql,new Object[]{questionImage,questionType,answer,selectTxt});
            bufferedReader.close();
        }
    }

    public String getSelectTxt(String answer){
        StringBuffer stringBuffer = new StringBuffer(answer);
        while(stringBuffer.length()<24){
            stringBuffer.append(getRandomChar());
        }
        return stringBuffer.toString();
    }



    //随机返回一个汉字
    private char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;
        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }

        return str.charAt(0);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //当前后版本不一致的时候就会调用这个方法，
        //在这里执行更新数据库的代码
        //String sql = alter
    }
}

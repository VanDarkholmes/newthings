package com.example.machenike.ok13;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machenike.ok13.adpter.SelectAdapter;
import com.example.machenike.ok13.util.CrazyDao;
import com.example.machenike.ok13.util.Globals;
import com.example.machenike.ok13.util.ImageUtil;
import com.example.machenike.ok13.view.SelectView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class Game2Activity extends AppCompatActivity {

    private LinearLayout gameTopBg;
    private Button gameTopBack;
    private TextView coinImg;
    private TextView coinCount;
    private Button deleteImg;
    private Button hintImg;
    private TextView questionTypeImg;
    private TextView questionImg;
    private GridView selectGrid;
    private List<View> allViews;
    private Context context;
   int k=0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);



        gameTopBg = findViewById(R.id.game_top_bg);
        gameTopBack = findViewById(R.id.game_top_back);
        TextView gameTopStar = findViewById(R.id.game_top_star);
        coinImg = findViewById(R.id.coin_img);
        coinCount = findViewById(R.id.coin_count);
        deleteImg = findViewById(R.id.delete_img);
        hintImg = findViewById(R.id.hint_img);
        questionTypeImg = findViewById(R.id.question_type);
        questionImg = findViewById(R.id.question_img);
        selectGrid = findViewById(R.id.select_grid);
        final SelectView myView = findViewById(R.id.myView);


        gameTopBg.setBackground(new BitmapDrawable(ImageUtil.topBgImg));
        gameTopBack.setBackground(new BitmapDrawable(ImageUtil.backBtnImg));
        gameTopStar.setBackground(new BitmapDrawable(ImageUtil.topStar));
        coinImg.setBackground(new BitmapDrawable(ImageUtil.coinImg));
        deleteImg.setBackground(new BitmapDrawable(ImageUtil.deleteImg));
        hintImg.setBackground(new BitmapDrawable(ImageUtil.hintImg));
        questionTypeImg.setBackground(new BitmapDrawable(ImageUtil.questionType));




        int stageNum = getIntent().getIntExtra("selectStage",1);
        gameTopStar.setText(stageNum+"");

        int coinCountNum = getSharedPreferences("game_data",MODE_PRIVATE).getInt("coin",50);
        coinCount.setText(coinCountNum+"");


        Map<String,Object> map= CrazyDao.queryDataByStage(stageNum);//数据库找到对应的信息。

        questionTypeImg.setText(map.get("questionType").toString());

        String path = map.get("questionImg").toString();
        try {
            Bitmap bt = BitmapFactory.decodeStream(getAssets().open(path));
            questionImg.setBackground(new BitmapDrawable(bt));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String selectTxt = map.get("selectTxt").toString();
        final List<String> list = new ArrayList<>();
        for(int i = 0;i<selectTxt.length();i++){
            list.add(selectTxt.charAt(i)+"");
        }

        Collections.shuffle(list);

        allViews = new ArrayList<>();
        for(String s:list){
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.select,null);

            linearLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Globals.SCREEN_HEIGHT/11));
            TextView  textView = linearLayout.findViewById(R.id.select_txt);
            textView.setText(s);
            textView.setBackground(new BitmapDrawable(ImageUtil.selectBg));
            allViews.add(linearLayout);
        }


        SelectAdapter selectAdapter = new SelectAdapter(allViews);
        selectGrid.setAdapter(selectAdapter);



        myView.initAnwser(map.get("answer").toString());//传进去的是正确答案

        selectGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {//答题框里的按钮们被点击
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = (LinearLayout) allViews.get(i);//获取到点击的按钮
                TextView textView = (TextView) linearLayout.getChildAt(0);//获取按钮中的字
                String userAnswer = textView.getText().toString();//获取按钮中的字
                myView.addUserAnswer(userAnswer,i);//把字放到答题框里去，看SelectView里的方法
                String answer = myView.answer();
                String[] now = myView.back();
                Log.i("aaaa","aaaaaaaaaaaa!!!!!!!!!!!!"+now.length+k);
                if(k<answer.length()){
                    linearLayout.setVisibility(View.INVISIBLE);
                }
             k=k+1;
            }
        });


        deleteImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myView.deleteUserAnswer();
            }
        });
        gameTopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Game2Activity.this,StageActivity.class);
                startActivity(intent1);
            }
        });
        hintImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = myView.answer();
                String[] now = myView.back();//得到实时的用户输入的字。
                int p = answer.length();
                String answer2[] = new String[p];
                for(int i=0;i<answer.length();i++){
                    answer2[i] = answer.charAt(i)+"";
                }


                for(int k= 0;k<answer.length();k++){
                    if(now[k]!=null){

                    }else{
                        for (int j=0;j<24;j++){
                            if (list.get(j).toString().equals(answer2[k])){
                                SharedPreferences sharedPreferences = getSharedPreferences("game_data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                int flag = sharedPreferences.getInt("coin",50)-30;
                                if(flag<0){
                                    Toast.makeText(Game2Activity.this, "金币不足！", Toast.LENGTH_SHORT).show();
                                }else{
                                    myView.addUserAnswer(answer2[k],j);
                                    LinearLayout linearLayout = (LinearLayout) allViews.get(j);
                                    linearLayout.setVisibility(View.INVISIBLE);
                                    Log.i("aaaa","aaaaaaaaaaaa!!!!!!!!!!!!111111111111111");

                                    editor.putInt("coin",sharedPreferences.getInt("coin",50)-30);
                                    editor.commit();
                                }
                                int coinCountNum = getSharedPreferences("game_data",MODE_PRIVATE).getInt("coin",50);
                                coinCount.setText(coinCountNum+"");
                                break;
                            }
                        }
                        break;
                    }
                }
               // myView.addUserAnswer(answer2,i);//把字放到答题框里去，看SelectView里的方法
                Log.i("aaaa","aaaaaaaaaaaa!!!!!!!!!!!!"+list.get(2).toString());



            }
        });

    }

    public List<View> getAllViews() {
        return allViews;
    }
}

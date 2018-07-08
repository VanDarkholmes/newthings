package com.example.machenike.ok13;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.machenike.ok13.util.CrazyDao;
import com.example.machenike.ok13.util.Globals;
import com.example.machenike.ok13.util.ImageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class StageActivity extends AppCompatActivity {
    private GridView stageGrid;
    private TextView topStar;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        Globals.init(this);

        //res文件夹下的内容都需要进行编译的。
        //assets文件夹中的内容也会被打包到APP中去
        stageGrid = findViewById(R.id.grid);

        topStar = findViewById(R.id.top_star);
        topStar.setBackground(new BitmapDrawable(ImageUtil.topStar));

        SharedPreferences sharedPreferences = getSharedPreferences("game_data",MODE_PRIVATE);
        final int stageNum = sharedPreferences.getInt("stageNum",0);
        topStar.setText(stageNum+"");

        final List<Map<String,Object>> datas = CrazyDao.getStageNum(stageNum);

        StageAdapter stageAdapter = new StageAdapter(this,datas);
        stageGrid.setAdapter(stageAdapter);


        stageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i<=stageNum-1){
                Map<String,Object>map = datas.get(i);
                Intent intent = new Intent(StageActivity.this,Game2Activity.class);
                intent.putExtra("selectStage",(int)map.get("stageNum"));
                SharedPreferences sharedPreferences = getSharedPreferences("game_data",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("nowStage",(int)map.get("stageNum"));
                    editor.commit();
                  //Log.i("aaa","aaa"+(int)map.get("stageNum"));
                startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("game_data",MODE_PRIVATE);
        int stageNum = sharedPreferences.getInt("stageNum",0);
        topStar.setText(stageNum+"");
        final List<Map<String,Object>> datas = CrazyDao.getStageNum(stageNum);
        StageAdapter stageAdapter = new StageAdapter(this,datas);
        stageGrid.setAdapter(stageAdapter);
    }
}

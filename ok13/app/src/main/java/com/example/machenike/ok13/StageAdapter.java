package com.example.machenike.ok13;

import
        android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.machenike.ok13.util.Globals;
import com.example.machenike.ok13.util.ImageUtil;

import java.util.List;
import java.util.Map;



/**
 * Created by byy on 2018/6/21.
 */

public class StageAdapter extends BaseAdapter {

    //数据源，上下文
    private Context context;
    private List<Map<String,Object>> maps;


    public StageAdapter(Context context, List<Map<String, Object>> maps) {
        this.context = context;
        this.maps = maps;
    }

    public StageAdapter() {
    }




    @Override
    public int getCount() {
        return maps.size();
    }

    @Override
    public Object getItem(int i) {
        return maps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        View tempView = null;
        if (view == null){
            tempView  = LayoutInflater.
                    from(context).inflate(R.layout.stage,viewGroup,false);
            tempView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    Globals.SCREEN_HEIGHT/10));
        }else{
            tempView = view;
        }
        Map<String,Object>map = maps.get(i);
        //map里应该存储   key='stageNum' value=1
        //还应该存贮，当前关卡是否已经玩过，
        //key='overFlag' value=true/false
        TextView stageView = tempView.findViewById(R.id.stage_num);
        stageView.setText(map.get("stageNum").toString());
        if ((Boolean) map.get("overFlag")){
            //可以玩的，背景颜色是白的
            //tempView.setBackgroundColor(Color.WHITE);
            tempView.setBackground(new BitmapDrawable(ImageUtil.selectBg));
        }else{
            //不可以玩，背景颜色是黑的
            //tempView.setBackgroundColor(Color.BLACK);
            tempView.setBackground(new BitmapDrawable(ImageUtil.answerBg));
        }
        return tempView;
    }
}

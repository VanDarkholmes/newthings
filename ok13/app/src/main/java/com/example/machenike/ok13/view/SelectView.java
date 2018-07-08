package com.example.machenike.ok13.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.machenike.ok13.Game2Activity;
import com.example.machenike.ok13.util.Globals;
import com.example.machenike.ok13.util.ImageUtil;

import javax.microedition.khronos.opengles.GL;


/**
 * Created by byy on 2018/6/27.
 */

public class SelectView extends View {

    //正确答案
    private String answer;
    private Game2Activity ga;
    private int answerIndex[];

    //存放用户数据的答案


    private String[] allUserAnswer;
    private float left;


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;

    }

    public SelectView(Context context) {
        super(context);
    }

    //解析xml文件 执行这个方法
    public SelectView(Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);

        ga = (Game2Activity)context;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //注册OnTouchLinstener方法,监听三种状态，按下，移动，抬起
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    //当前的动作是向下按
                    //Toast.makeText(ga,"触摸了我",Toast.LENGTH_SHORT).show();
                    if (motionEvent.getX()>left){
                        int temp = (int)(motionEvent.getX() - left);
                        int result = temp/((int)(Globals.GRID_WIDTH+Globals.GRID_SEP));
                        if (result<answer.length() && allUserAnswer[result] != null){
                            //从用户答案列表中删除对应的数据
                            allUserAnswer[result] = null;

                            //拿到当前的活动
                            //在拿到我点击的视图
                            ga.getAllViews().get(answerIndex[result]).setVisibility(VISIBLE);//设置返回的按钮可见
                            answerIndex[result] = -1;

                            postInvalidate();
                        }
                    }
                }

                return true;
            }
        });
    }
    public SelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void initAnwser(String answer){//answer是正确答案
        this.answer = answer;
        this.allUserAnswer = new String[answer.length()]; //用于存放用户选择的答案
        this.answerIndex = new int[answer.length()];//用于存放用户点击的答案在gridView里的编号
        postInvalidate();//重新绘制
    }
    //用户点击gridview,相当前的视图添加答案

   public String answer(){
        return answer;
   }
    public  void deleteUserAnswer(){
//        Log.i("啊啊啊啊啊","1");
        for (int i = 0;i<allUserAnswer.length;i++){
            if (allUserAnswer[i+1]==null){
                //1.拿到最后一个字置空
                allUserAnswer[i]=null;
                ga.getAllViews().get(answerIndex[i]).setVisibility(VISIBLE);
                postInvalidate();
                break;
            }
            if(allUserAnswer[allUserAnswer.length-1]!=null){
                allUserAnswer[allUserAnswer.length-1]=null;
                ga.getAllViews().get(answerIndex[allUserAnswer.length-1]).setVisibility(VISIBLE);
                postInvalidate();
                break;
            }
        }

    }


    public void addUserAnswer(String answer,int index){
        for (int i = 0;i<allUserAnswer.length;i++){
            if (allUserAnswer[i]==null){
                allUserAnswer[i] = answer;
                answerIndex[i] = index;//将用户选择的答案的gridView编号传到数组进行储存。
                //添加答案的时候，要需要把对应答案的索引值出入进来
                break;
            }
        }

        //还要判断用户输入的数据是否正确
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<allUserAnswer.length;i++){
            sb.append(allUserAnswer[i]);
        }
        if (sb.length() == this.answer.length() && sb.toString().equals(this.answer)){
            //表示答对
            SharedPreferences sharedPreferences = ga.getSharedPreferences("game_data",Context.MODE_PRIVATE);

            int nowStage = sharedPreferences.getInt("nowStage",1);
            int StageNum = sharedPreferences.getInt("stageNum",1);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Log.i("nowStage!!!!!!","aaaaaaaaaa"+nowStage);
           if(nowStage==StageNum){
            editor.putInt("stageNum",nowStage+1); //控制已经进行完成的关卡数。更新关卡
           }
           int f =nowStage+1;
            Log.i("nowStage!!!!!!","aaaaaaaaaafffffff!!!!!!!!!!!!!!!"+f);
            editor.putInt("coin",sharedPreferences.getInt("coin",50)+30);   //完成就加30金币。更新金币
            editor.putInt("nowStage",f);
            //关卡数和金币数都是用sharedPreferences进行实时储存更新的。

            editor.commit();//提交金币数和关卡数。
            Toast.makeText(ga,"答题正确，下一关",Toast.LENGTH_SHORT).show();
            //跳转到下一关
            Intent intent = new Intent(ga,Game2Activity.class);
            intent.putExtra("selectStage",f);//关卡数+1和stageNum其实是一个东西
            //开始跳转
            ga.startActivity(intent);
            ga.finish();//表示关闭当前的Activity
        }
        //重新绘制
        postInvalidate();
    }

    public String[] back(){
        return allUserAnswer;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(26);
        paint.setColor(Color.WHITE);
        //要根据answer去绘制界面
        if (answer!=null){
            //绘制输入框图片

            float temp = answer.length() * Globals.GRID_WIDTH /2;
            left = Globals.SCREEN_WIDTH /2.0f - temp;
            for(int i = 0;i<answer.length();i++){
                //绘制答题背景
                canvas.drawBitmap(ImageUtil.answerBg,
                        left +i*(Globals.GRID_WIDTH+Globals.GRID_SEP),
                        10,
                        paint);
                //绘制答题答案
                if (allUserAnswer[i] != null){
                    canvas.drawText(allUserAnswer[i]
                            , left +i*(Globals.GRID_WIDTH+Globals.GRID_SEP)+(Globals.GRID_WIDTH/2-13)
                            ,Globals.GRID_WIDTH/2+13
                            ,paint);
                }
            }
        }
    }
}

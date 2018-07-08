package com.example.machenike.ok13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machenike.ok13.util.Globals;
import com.example.machenike.ok13.view.MyView;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Globals.init(this);
        final MyView myView = findViewById(R.id.myView2);
        Button addButton = findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.setCoinImageCount(myView.getCoinImageCount()+1);
            }
        });
    }
}

package com.behivordemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.behivordemo.R;
import com.behivordemo.customview.ClearCircleView;
import com.behivordemo.customview.SignInView;
import com.behivordemo.material.MaterialAimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16/10/10.
 */

public class CustomShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customshow);
        final SignInView signInView = (SignInView) findViewById(R.id.sign_in_view);
        List<String> viewData = new ArrayList<>();
        viewData.add("周一");
        viewData.add("周二");
        viewData.add("周三");
        viewData.add("周四");
        viewData.add("周五");
        viewData.add("周六");
        viewData.add("周日");
        signInView.setSignInData(viewData);

        findViewById(R.id.tv_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInView.signInEvent();
            }
        });

        MaterialAimUtils materialAimUtils = new MaterialAimUtils
                .Builder().animDuration(600)
                .materialAimType(MaterialAimUtils.MaterialAimType.SLIDE)
                .slideGrivaty(Gravity.RIGHT)
                .build();
        materialAimUtils.setEixtMaterial(this);
        materialAimUtils.setEnterMaterial(this);

        final ClearCircleView clearCircleView = (ClearCircleView) findViewById(R.id.cc_clear);

        clearCircleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                clearCircleView.startWave();
            }
        }, 500);
    }
}

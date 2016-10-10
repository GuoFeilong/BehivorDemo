package com.behivordemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.behivordemo.R;
import com.behivordemo.customview.SignInView;

/**
 * Created by user on 16/10/10.
 */

public class CustomShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customshow);
        final SignInView signInView = (SignInView) findViewById(R.id.sign_in_view);
        findViewById(R.id.tv_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInView.signInEvent();
            }
        });
    }
}

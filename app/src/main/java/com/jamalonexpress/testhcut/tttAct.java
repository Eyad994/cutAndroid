package com.jamalonexpress.testhcut;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import androidx.appcompat.app.AppCompatActivity;

public class tttAct extends AppCompatActivity {

    String arrayName[] = {"Facebook", "Twitter",
    "Youtube", "Linkedin", "Pinterest"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttt);

        CircleMenu circleMenu = findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.RED,R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_disabled)
                .addSubMenu(Color.BLUE, R.drawable.facebook)
                .addSubMenu(Color.RED, R.drawable.pinterest)
                .addSubMenu(Color.BLUE, R.drawable.linkedin)
                .addSubMenu(Color.BLUE, R.drawable.twitter)
                .addSubMenu(Color.BLACK, R.drawable.vk)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {
                        Toast.makeText(tttAct.this, "Selected"+ arrayName[i], Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

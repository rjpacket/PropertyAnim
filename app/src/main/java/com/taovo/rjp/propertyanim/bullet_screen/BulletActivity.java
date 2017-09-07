package com.taovo.rjp.propertyanim.bullet_screen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.taovo.rjp.propertyanim.R;

import java.util.ArrayList;

public class BulletActivity extends Activity {

    private BulletScreenView bulletScreenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bullet);

        bulletScreenView = (BulletScreenView) findViewById(R.id.bullet_screen_view);

    }

    public void add(View view) {
        ArrayList<Bullet> bullets = new ArrayList<>();
        bullets.add(new Bullet("haokanhoakannak"));
        bullets.add(new Bullet("gartaytryzhghjdfgsgdfggggggdfggg"));
        bullets.add(new Bullet("125364747"));
        bullets.add(new Bullet("gggggggggggggggggggggggggggg"));
        bullets.add(new Bullet("666666666"));
        bullets.add(new Bullet("666"));
        bullets.add(new Bullet("999999999999"));
        bulletScreenView.addBullet(bullets);
    }
}

package com.taovo.rjp.propertyanim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void number(View view){
        startActivity(new Intent(this, ScrollNumberActivity.class));
    }

    public void openBook(View view){
        startActivity(new Intent(this, ChangePageActivity.class));
    }
}

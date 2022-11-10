package com.example.demoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.demoapplication.Activities.ListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callList(View view) {
        Intent i = new Intent(MainActivity.this, ListActivity.class);
        i.putExtra("PAGE", "Call");
        startActivity(i);
    }

    public void buyList(View view) {
        Intent i = new Intent(MainActivity.this, ListActivity.class);
        i.putExtra("PAGE", "Buy");
        startActivity(i);
    }

    public void sellList(View view) {
        Intent i = new Intent(MainActivity.this, ListActivity.class);
        i.putExtra("PAGE", "Sell");
        startActivity(i);
    }
}
package com.example.dell5559.offstage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HomePage extends AppCompatActivity {

    ImageView event,chat,participant,income,expense,todo;
    ImageButton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        event = (ImageView) findViewById(R.id.eventslist);
        chat = (ImageView) findViewById(R.id.chatt);
        participant = (ImageView) findViewById(R.id.participants);
        expense = (ImageView) findViewById(R.id.expensess);
        todo = (ImageView) findViewById(R.id.todolist);
        profile = (ImageButton)findViewById(R.id.imagebtn);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,EventsList.class));
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,GroupsActivity.class));
            }
        });
        participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,ParticipantsActivity.class));
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,ExpenseTrackerActivity.class));
            }
        });
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,todo_MainActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,ProfileActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this,R.style.MyDialogTheme)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        HomePage.super.onBackPressed();
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        finish();
                        System.exit(1);
                    }
                }).create().show();
    }
}

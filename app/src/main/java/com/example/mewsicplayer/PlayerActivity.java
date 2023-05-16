package com.example.mewsicplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    Bundle songExtraData; // in order to get the values from previous activity
    ImageView prev,play, next;
    int position;
    SeekBar mSeekBarTime;
    static MediaPlayer mMediaPlayer; // if not static then two or more than two songs will be played at the same time
    TextView songName;
    ArrayList<File> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // casting views

        prev = findViewById(R.id.previous);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        mSeekBarTime = findViewById(R.id.mSeekBarTime);
        songName  = findViewById(R.id.songName);

        // when the activity launches mediaplayer should be on stop

        if (mMediaPlayer!=null) {
            mMediaPlayer.stop();
        }

        // getting values from previous activity
        Intent intent = getIntent();
        songExtraData = intent.getExtras();

        musicList = (ArrayList)songExtraData.getParcelableArrayList("songsList");
        position = songExtraData.getInt("position", 0);

        // creating a new method that initializes the media player on launch of activity

        initializeMusicPlayer(position);

        // play button

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position < musicList.size() -1) {
                    position++;
                } else {
                    position = 0;
                }
                initializeMusicPlayer(position);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position<=0) {
                    position = musicList.size();
                } else {
                    position++;
                }

                initializeMusicPlayer(position);
            }
        });
    }
    

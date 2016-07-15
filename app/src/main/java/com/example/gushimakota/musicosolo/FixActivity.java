package com.example.gushimakota.musicosolo;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;

public class FixActivity extends AppCompatActivity {

    private String aPart;
    private String bPart;
    private String cPart;

    private String filePath;
    private int finalState;
    private MediaPlayer player;
    private String track[] = {"","",""};
    private boolean playing;
    private boolean init;

    private TextView textOr;
    private TextView textEstimate;
    private Button play;
    private Button bad;
    private Button good;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix);
        Intent intent = getIntent();
        aPart = intent.getStringExtra("Apart");
        bPart = intent.getStringExtra("Bpart");
        cPart = intent.getStringExtra("Cpart");
        playing = false;
        init = false;
        textEstimate = (TextView)findViewById(R.id.textPleaseEstimate);
        textOr = (TextView)findViewById(R.id.textOr);
        play = (Button)findViewById(R.id.play_for_checking);
        play.setVisibility(View.INVISIBLE);
        bad = (Button)findViewById(R.id.bad);
        good = (Button)findViewById(R.id.good);

        jointTrack();
        setButtonActions();

        finalState = 0;
    }

    private void jointTrack() {

        try {

            FileInputStream fisA = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/Musico/" + aPart + ".mp3");
            FileInputStream fisB = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/Musico/" + bPart + ".mp3");
            FileInputStream fisC = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/Musico/" + cPart + ".mp3");
            SequenceInputStream sisAB = new SequenceInputStream(fisA, fisB);
            SequenceInputStream sisABC = new SequenceInputStream(sisAB, fisC);

            filePath = Environment.getExternalStorageDirectory().getPath() + "/Musico/final.mp3";

            FileOutputStream fos = new FileOutputStream(new File(filePath));

            int temp;

            try {
                while ((temp = sisABC.read()) != -1) {
//                                if (temp != 0){
                    fos.write(temp);
//                                }

                }
                play.setVisibility(View.VISIBLE);
            } catch (IOException e4) {
                // TODO Auto-generated catch block
                e4.printStackTrace();
            }
        } catch (FileNotFoundException e5) {
            // TODO Auto-generated catch block
            e5.printStackTrace();
        }
    }

    private void setButtonActions(){
        player = new MediaPlayer();
        //再生ボタンの挙動
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!init){
                    try {
                        player.setDataSource(filePath);
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    init = true;
                }
                if (!playing){

                    textEstimate.setVisibility(View.VISIBLE);
                    textOr.setVisibility(View.VISIBLE);
                    bad.setVisibility(View.VISIBLE);
                    good.setVisibility(View.VISIBLE);
                    player.start();
                    playing  = true;
                    play.setText("停止");
                }else{
                    player.pause();
                    playing  = false;
                    play.setText("再生する");
                }
            }
        });
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmSendingAllClear();
            }
        });
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmSendingOK();
            }
        });
    }

    private void confirmSendingAllClear(){
        // 確認ダイアログの生成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle("このトラックは不適です");
        alertDlg.setMessage("本当にやり直しにしますか？");
        alertDlg.setPositiveButton(
                "はい",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK ボタンクリック処理
                        disagreeToTrack();
                    }
                });
        alertDlg.setNegativeButton(
                "キャンセル",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel ボタンクリック処理
                        return;
                    }
                });

        // 表示
        alertDlg.create().show();
    }

    private void confirmSendingOK(){
        // 確認ダイアログの生成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle("このトラックは適切です");
        alertDlg.setMessage("本当に採用にしますか？");
        alertDlg.setPositiveButton(
                "はい",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK ボタンクリック処理
                        agreeToTrack();
                    }
                });
        alertDlg.setNegativeButton(
                "キャンセル",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel ボタンクリック処理
                        return;
                    }
                });

        // 表示
        alertDlg.create().show();
    }

    private void disagreeToTrack(){
        Intent restart = new Intent(FixActivity.this,EditAllPartActivity.class);
        finish();
        startActivity(restart);
    }

    private void agreeToTrack(){
        ParseObject object = new ParseObject("Solo");
        object.put("Name",getString(R.string.username));
        object.put("A",aPart);
        object.put("B",bPart);
        object.put("C", cPart);
        object.saveInBackground();
        Intent end = new Intent(FixActivity.this,com.example.gushimakota.musicosolo.ThankUActivity.class);
        finish();
        startActivity(end);
    }

}
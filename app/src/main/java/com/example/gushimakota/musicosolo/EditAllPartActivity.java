package com.example.gushimakota.musicosolo;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class EditAllPartActivity extends AppCompatActivity implements EditFragment.EditListener{

    private int state;
    private LinearLayout l1;
    private EditFragment fragment;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        state = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apart);
        l1 = (LinearLayout)findViewById(R.id.container_apart);
        intent = new Intent(EditAllPartActivity.this,com.example.gushimakota.musicosolo.FixActivity.class);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment = EditFragment.newInstance("A");
        transaction.add(R.id.container_apart, fragment, "fragmentA");
        transaction.commit();
    }

    // interface内のメソッドを実装します。
    @Override
    public void onClickButton(String idea) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        switch (state){
            case 0:
                state=1;
                intent.putExtra("Apart", idea);
                fragment = EditFragment.newInstance("B");
                transaction.add(R.id.container_apart, fragment, "fragmentB");
                break;
            case 1:
                state=2;
                intent.putExtra("Bpart", idea);
                fragment = EditFragment.newInstance("C");
                transaction.add(R.id.container_apart, fragment, "fragmentC");
                break;
            case 2:
                intent.putExtra("Cpart", idea);
                state=0;
                finish();
                startActivity(intent);
                break;
        }
        transaction.commit();
    }
}
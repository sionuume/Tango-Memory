package com.icaynia.tangoii;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class WordToHiragana extends AppCompatActivity {
    private int count = 0;
    private int errorcount = 0;
    private TextView wordvu;
    private TextView hiraganavu;
    private TextView errorcountvu;
    private EditText input;
    private Button input_submit;
    private wordManager mWordManager;
    private Random oRandom;
    private ArrayList<word> words;
    private word mword;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_to_hiragana);
        this.init();

        game();
    }

    private void init() {
        mWordManager = new wordManager(this);
        wordvu = (TextView) findViewById(R.id.wordvu);
        hiraganavu = (TextView) findViewById(R.id.hiraganavu);
        input = (EditText) findViewById(R.id.input);
        input_submit = (Button) findViewById(R.id.input_submit);
        errorcountvu = (TextView) findViewById(R.id.errorcount);
        oRandom = new Random();
        words = mWordManager.getWordAll();
        mHandler = new Handler();



    }

    private void game() {
        int randint;
        errorcountvu.setText("Life = " + (3-errorcount));
        while (true) {
            randint = rand(words.size()-1);
            mword = words.get(randint);
            if (isKanji(mword.word)) {
                break;
            }
        }
        final int r_id = mword.id;
        wordvu.setText(mword.word);
        mWordManager.addCount(r_id);

        Log.e("count", "showcount = "+mword.showcount);
        input_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.getText().toString().equals(mword.hiragana)) {
                    hiraganavu.setText("");
                    mWordManager.addPassCount(r_id);
                    errorcount = 0;
                    input.setText("");
                    game();
                } else {
                    errorcount++;
                    errorcountvu.setText("Life = " + (3-errorcount));
                    if (errorcount >= 3) {
                        errorcount = 0;
                        hiraganavu.setText(mword.word+" = "+mword.hiragana);
                        input.setText("");
                        game();
                    }
                }
            }
        });

    }

    private int rand(int max) {
        int temp = oRandom.nextInt(max);
        return temp;
    }

    public static boolean isKanji(String str)
    {
        for(int i = 0 ; i < str.length() ; i++)
        {
            char ch = str.charAt(i);
            Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
            if(
                    Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(unicodeBlock) ||
                    Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A.equals(unicodeBlock) ||
                    Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B.equals(unicodeBlock) ||
                    Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS.equals(unicodeBlock) ||
                    Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT.equals(unicodeBlock)
            ) return true;
        }
        return false;
    }

    private class customThread extends Thread {

        public customThread() {
        }

        @Override
        public void run() {
            super.run();
            try {
                customThread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    game();
                }
            });

        }

    }


}

package ctc.kopo.pchu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import ctc.kopo.pchu.R;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final ImageView eyeChange = (ImageView) findViewById(R.id.splasheyes);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                eyeChange.setImageResource(R.drawable.splash2);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        eyeChange.setImageResource(R.drawable.splash3);
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                                SplashActivity.this.startActivity(mainIntent);
                                SplashActivity.this.finish();
                            }
                        }, SPLASH_DISPLAY_LENGTH);
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
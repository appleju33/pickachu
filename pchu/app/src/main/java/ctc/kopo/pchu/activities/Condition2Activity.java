package ctc.kopo.pchu.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import ctc.kopo.pchu.R;

/**
 * Created by Polytech on 2017-08-01.
 */

public class Condition2Activity extends AppCompatActivity{

    String mood = null;
    String color = null;
    String skinrgb = null;
    boolean index1=true;boolean index2=true;boolean index3=true;boolean index4=true;boolean index5=true;boolean index6=true;
    boolean index7=true;boolean index8=true;boolean index9=true;boolean index10=true;boolean index11=true;boolean index12=true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condition2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0); // 그림자 없애기
        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);      //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        actionBar.setCustomView(mCustomView);

        // 액션바에 백그라운드 색상을 아래처럼 입힐 수 있습니다.
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(255, 255, 255, 255)));

        //설명서로 가는 버튼
        ImageButton info = (ImageButton) mCustomView.findViewById(R.id.settingsBtn);
        info.setVisibility(View.INVISIBLE);

        final ImageButton mood1 = (ImageButton) findViewById(R.id.mood1);
        final ImageButton bitch2 = (ImageButton) findViewById(R.id.bitch2);
        final ImageButton bye3 = (ImageButton) findViewById(R.id.bye3);
        final ImageButton sick4 = (ImageButton) findViewById(R.id.sick4);
        final ImageButton kiss5 = (ImageButton) findViewById(R.id.kiss5);
        final ImageButton party6 = (ImageButton) findViewById(R.id.party6);
        final ImageButton smart7 = (ImageButton) findViewById(R.id.smart7);
        final ImageButton boss8 = (ImageButton) findViewById(R.id.boss8);
        final ImageButton vitamin9 = (ImageButton) findViewById(R.id.vitamin9);
        final ImageButton natural10 = (ImageButton) findViewById(R.id.natural10);
        final ImageButton strong11 = (ImageButton) findViewById(R.id.strong11);
        final ImageButton mola12 = (ImageButton) findViewById(R.id.mola12);
        ImageButton next = (ImageButton) findViewById(R.id.next) ;

        Intent intent = getIntent();
        color= intent.getExtras().getString("color");
        skinrgb= intent.getExtras().getString("RGB");
        Toast.makeText(this, skinrgb, Toast.LENGTH_LONG).show();

        isCheckedBtn();
        mood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index1) {
                    mood = "분위기";
                    mood1.setImageResource(R.drawable.mood1_click);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = false;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    mood1.setImageResource(R.drawable.mood1);
                    index1 = true;
                }
                isCheckedBtn();
            }
        });

        bitch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index2) {
                    mood = "강렬";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2_click);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=false;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    bitch2.setImageResource(R.drawable.bitch2);
                    index2 = true;
                }
                isCheckedBtn();
            }
        });

        bye3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index3) {
                    mood = "우울";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3_click);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=true;index3=false;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    bye3.setImageResource(R.drawable.bye3);
                    index3 = true;
                }
                isCheckedBtn();
            }
        });

        sick4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index4) {
                    mood = "창백";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4_click);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=true;index3=true;index4=false;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    sick4.setImageResource(R.drawable.sick4);
                    index4 = true;
                }
                isCheckedBtn();
            }
        });

        kiss5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index5) {
                    mood = "데이트";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5_click);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=true;index3=true;index4=true;index5=false;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    kiss5.setImageResource(R.drawable.kiss5);
                    index5 = true;
                }
                isCheckedBtn();
            }
        });

        party6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index6) {
                    mood = "파티";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6_click);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=false;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    party6.setImageResource(R.drawable.party6);
                    index6 = true;
                }
                isCheckedBtn();
            }
        });
        smart7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index7) {
                    mood = "스마트";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7_click);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=false;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    smart7.setImageResource(R.drawable.smart7);
                    index7 = true;
                }
                isCheckedBtn();
            }
        });

        boss8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index8) {
                    mood = "청순";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8_click);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=false;index9=true;index10=true;index11=true;index12=true;
                }else{
                    boss8.setImageResource(R.drawable.boss8);
                    index8 = true;
                }
                isCheckedBtn();
            }
        });
        vitamin9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index9) {
                    mood = "상큼";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9_click);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=false;index10=true;index11=true;index12=true;
                }else{
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    index9 = true;
                }
                isCheckedBtn();
            }
        });
        natural10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index10) {
                    mood = "자연스러움";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10_click);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=false;index11=true;index12=true;
                }else{
                    natural10.setImageResource(R.drawable.natural10);
                    index10 = true;
                }
                isCheckedBtn();
            }
        });
        strong11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index11) {
                    mood = "쎈 언니";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11_click);
                    mola12.setImageResource(R.drawable.mola12);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=false;index12=true;
                }else{
                    strong11.setImageResource(R.drawable.strong11);
                    index11 = true;
                }
                isCheckedBtn();
            }
        });
        mola12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index12) {
                    mood = "아무거나";
                    mood1.setImageResource(R.drawable.mood1);
                    bitch2.setImageResource(R.drawable.bitch2);
                    bye3.setImageResource(R.drawable.bye3);
                    sick4.setImageResource(R.drawable.sick4);
                    kiss5.setImageResource(R.drawable.kiss5);
                    party6.setImageResource(R.drawable.party6);
                    smart7.setImageResource(R.drawable.smart7);
                    boss8.setImageResource(R.drawable.boss8);
                    vitamin9.setImageResource(R.drawable.vitamin9);
                    natural10.setImageResource(R.drawable.natural10);
                    strong11.setImageResource(R.drawable.strong11);
                    mola12.setImageResource(R.drawable.mola12_click);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=false;
                }else{
                    mola12.setImageResource(R.drawable.mola12);
                    index12 = true;
                }
                isCheckedBtn();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                intent.putExtra("RGB", skinrgb);
                intent.putExtra("mood",mood);
                intent.putExtra("color",color);
                startActivity(intent);
            }
        });
    }
    public void isCheckedBtn(){
        ImageButton next = (ImageButton) findViewById(R.id.next) ;
        if(index1 && index2 && index3 && index4 && index5 && index6 && index7 && index8 && index9 && index10 && index11 && index12){
            //next.setBackgroundColor(Color.parseColor("#8C8C8C"));
            next.setImageResource(R.drawable.condition2_un);
            next.setEnabled(false);
        }else{
            //next.setBackgroundColor(Color.parseColor("#A90008"));
            next.setImageResource(R.drawable.condition2);
            next.setEnabled(true);
        }
    }
}

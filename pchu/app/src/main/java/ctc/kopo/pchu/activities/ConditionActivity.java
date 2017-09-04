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
 * Created by Polytech on 2017-09-04.
 */

public class ConditionActivity extends AppCompatActivity {

    String mood = null;
    String color = null;
    String skinrgb = null;

    ImageButton con1;
    ImageButton con2;
    ImageButton con3;
    ImageButton con4;
    ImageButton con5;
    ImageButton con6;
    ImageButton con7;
    ImageButton con8;
    ImageButton con9;
    ImageButton con10;

    int[] con1ImgClicked = {R.drawable.theme1_1_color,R.drawable.theme1_2_color,R.drawable.theme1_3_color,R.drawable.theme1_4_color,R.drawable.theme1_5_color};
    int[] con1ImgUnclicked = {R.drawable.theme1_1_grey,R.drawable.theme1_2_grey,R.drawable.theme1_3_grey,R.drawable.theme1_4_grey,R.drawable.theme1_5_grey};
    int[] con2ImgClicked = {R.drawable.theme2_1_color,R.drawable.theme2_2_color,R.drawable.theme2_3_color,R.drawable.theme2_4_color,R.drawable.theme2_5_color};
    int[] con2ImgUnclicked = {R.drawable.theme2_1_grey,R.drawable.theme2_2_grey,R.drawable.theme2_3_grey,R.drawable.theme2_4_grey,R.drawable.theme2_5_grey};
    int[] con3ImgClicked = {R.drawable.theme3_1_color,R.drawable.theme3_2_color,R.drawable.theme3_3_color,R.drawable.theme3_4_color,R.drawable.theme3_5_color};
    int[] con3ImgUnclicked = {R.drawable.theme3_1_grey,R.drawable.theme3_2_grey,R.drawable.theme3_3_grey,R.drawable.theme3_4_grey,R.drawable.theme3_5_grey};
    int[] con4ImgClicked = {R.drawable.theme4_1_color,R.drawable.theme4_2_color,R.drawable.theme4_3_color,R.drawable.theme4_4_color,R.drawable.theme4_5_color};
    int[] con4ImgUnclicked = {R.drawable.theme4_1_grey,R.drawable.theme4_2_grey,R.drawable.theme4_3_grey,R.drawable.theme4_4_grey,R.drawable.theme4_5_grey};
    int[] con5ImgClicked = {R.drawable.theme5_1_color,R.drawable.theme5_2_color,R.drawable.theme5_3_color,R.drawable.theme5_4_color,R.drawable.theme5_5_color};
    int[] con5ImgUnclicked = {R.drawable.theme5_1_grey,R.drawable.theme5_2_grey,R.drawable.theme5_3_grey,R.drawable.theme5_4_grey,R.drawable.theme5_5_grey};
    int[] con6ImgClicked = {R.drawable.theme6_1_color,R.drawable.theme6_2_color,R.drawable.theme6_3_color,R.drawable.theme6_4_color,R.drawable.theme6_5_color};
    int[] con6ImgUnclicked = {R.drawable.theme6_1_grey,R.drawable.theme6_2_grey,R.drawable.theme6_3_grey,R.drawable.theme6_4_grey,R.drawable.theme6_5_grey};
    int[] con7ImgClicked = {R.drawable.theme7_1_color,R.drawable.theme7_2_color,R.drawable.theme7_3_color,R.drawable.theme7_4_color,R.drawable.theme7_5_color};
    int[] con7ImgUnclicked = {R.drawable.theme7_1_grey,R.drawable.theme7_2_grey,R.drawable.theme7_3_grey,R.drawable.theme7_4_grey,R.drawable.theme7_5_grey};
    int[] con8ImgClicked = {R.drawable.theme8_1_color,R.drawable.theme8_2_color,R.drawable.theme8_3_color,R.drawable.theme8_4_color,R.drawable.theme8_5_color};
    int[] con8ImgUnclicked = {R.drawable.theme8_1_grey,R.drawable.theme8_2_grey,R.drawable.theme8_3_grey,R.drawable.theme8_4_grey,R.drawable.theme8_5_grey};
    int[] con9ImgClicked = {R.drawable.theme9_1_color,R.drawable.theme9_2_color,R.drawable.theme9_3_color};
    int[] con9ImgUnclicked = {R.drawable.theme9_1_grey,R.drawable.theme9_2_grey,R.drawable.theme9_3_grey};
    int[] con10ImgClicked = {R.drawable.theme10_1_color,R.drawable.theme10_2_color,R.drawable.theme10_3_color};
    int[] con10ImgUnclicked = {R.drawable.theme10_1_grey,R.drawable.theme10_2_grey,R.drawable.theme10_3_grey};
    int[] nums = new int[10];

    boolean index1=true;boolean index2=true;boolean index3=true;boolean index4=true;boolean index5=true;boolean index6=true;
    boolean index7=true;boolean index8=true;boolean index9=true;boolean index10=true;boolean index11=true;boolean index12=true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condition_1);

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

        con1 = (ImageButton) findViewById(R.id.con1_btn);
        con2 = (ImageButton) findViewById(R.id.con2_btn);
        con3 = (ImageButton) findViewById(R.id.con3_btn);
        con4 = (ImageButton) findViewById(R.id.con4_btn);
        con5 = (ImageButton) findViewById(R.id.con5_btn);
        con6 = (ImageButton) findViewById(R.id.con6_btn);
        con7 = (ImageButton) findViewById(R.id.con7_btn);
        con8 = (ImageButton) findViewById(R.id.con8_btn);
        con9 = (ImageButton) findViewById(R.id.con9_btn);
        con10 = (ImageButton) findViewById(R.id.con10_btn);
        ImageButton next = (ImageButton) findViewById(R.id.next) ;

        Intent intent = getIntent();
        skinrgb= intent.getExtras().getString("RGB");
        Toast.makeText(this, skinrgb, Toast.LENGTH_LONG).show();

        initImgButton();

        isCheckedBtn();
        con1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index1) {
                    mood = "theme1";
                    con1.setImageResource(con1ImgClicked[nums[0]]);
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index1 = false;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    index1 = true;
                }
                isCheckedBtn();
            }
        });

        con2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index2) {
                    mood = "theme2";
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    con2.setImageResource(con2ImgClicked[nums[1]]);
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index1 = true;index2=false;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    index2 = true;
                }
                isCheckedBtn();
            }
        });

        con3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index3) {
                    mood = "theme3";
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    con3.setImageResource(con3ImgClicked[nums[2]]);
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index1 = true;index2=true;index3=false;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    index3 = true;
                }
                isCheckedBtn();
            }
        });

        con4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index4) {
                    mood = "theme4";
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    con4.setImageResource(con4ImgClicked[nums[3]]);
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index1 = true;index2=true;index3=true;index4=false;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    index4 = true;
                }
                isCheckedBtn();
            }
        });

        con5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index5) {
                    mood = "theme5";
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    con5.setImageResource(con5ImgClicked[nums[4]]);
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index1 = true;index2=true;index3=true;index4=true;index5=false;index6=true;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    index5 = true;
                }
                isCheckedBtn();
            }
        });

        con6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index6) {
                    mood = "theme6";
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    con6.setImageResource(con6ImgClicked[nums[5]]);
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=false;
                    index7=true;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    index6 = true;
                }
                isCheckedBtn();
            }
        });
        con7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index7) {
                    mood = "theme7";
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    con7.setImageResource(con7ImgClicked[nums[6]]);
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=false;index8=true;index9=true;index10=true;index11=true;index12=true;
                }else{
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    index7 = true;
                }
                isCheckedBtn();
            }
        });

        con8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index8) {
                    mood = "theme8";
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    con8.setImageResource(con8ImgClicked[nums[7]]);
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=false;index9=true;index10=true;index11=true;index12=true;
                }else{
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    index8 = true;
                }
                isCheckedBtn();
            }
        });
        con9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index9) {
                    mood = "theme9";
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    con9.setImageResource(con9ImgClicked[nums[8]]);
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=false;index10=true;index11=true;index12=true;
                }else{
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    index9 = true;
                }
                isCheckedBtn();
            }
        });
        con10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index10) {
                    mood = "theme10";
                    con1.setImageResource(con1ImgUnclicked[nums[0]]);
                    con2.setImageResource(con2ImgUnclicked[nums[1]]);
                    con3.setImageResource(con3ImgUnclicked[nums[2]]);
                    con4.setImageResource(con4ImgUnclicked[nums[3]]);
                    con5.setImageResource(con5ImgUnclicked[nums[4]]);
                    con6.setImageResource(con6ImgUnclicked[nums[5]]);
                    con7.setImageResource(con7ImgUnclicked[nums[6]]);
                    con8.setImageResource(con8ImgUnclicked[nums[7]]);
                    con9.setImageResource(con9ImgUnclicked[nums[8]]);
                    con10.setImageResource(con10ImgClicked[nums[9]]);
                    index1 = true;index2=true;index3=true;index4=true;index5=true;index6=true;
                    index7=true;index8=true;index9=true;index10=false;index11=true;index12=true;
                }else{
                    con10.setImageResource(con10ImgUnclicked[nums[9]]);
                    index10 = true;
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
    public void initImgButton(){
        for(int i = 0;i<8;i++){
            nums[i] = (int)(Math.random()*5);
            //Log.d("숫자확인",String.valueOf(nums[i]));
        }
        nums[8] = (int)(Math.random()*3);
        nums[9] = (int)(Math.random()*3);

        con1.setImageResource(con1ImgUnclicked[nums[0]]);
        con2.setImageResource(con2ImgUnclicked[nums[1]]);
        con3.setImageResource(con3ImgUnclicked[nums[2]]);
        con4.setImageResource(con4ImgUnclicked[nums[3]]);
        con5.setImageResource(con5ImgUnclicked[nums[4]]);
        con6.setImageResource(con6ImgUnclicked[nums[5]]);
        con7.setImageResource(con7ImgUnclicked[nums[6]]);
        con8.setImageResource(con8ImgUnclicked[nums[7]]);
        con9.setImageResource(con9ImgUnclicked[nums[8]]);
        con10.setImageResource(con10ImgUnclicked[nums[9]]);
    }
}

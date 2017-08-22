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

public class Condition1Activity extends AppCompatActivity {
    String color = null;
    String skinrgb = null;
    boolean index1 = true;
    boolean index2 = true;
    boolean index4 = true;
    boolean index5 = true;
    boolean index6 = true;
    boolean index7 = true;
    boolean index8 = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condition1);

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

        final ImageButton pink1 = (ImageButton) findViewById(R.id.pink1) ;
        final ImageButton coral2 = (ImageButton) findViewById(R.id.coral2) ;
        final ImageButton nude4 = (ImageButton) findViewById(R.id.nude4) ;
        final ImageButton red5 = (ImageButton) findViewById(R.id.red5) ;
        final ImageButton know6 = (ImageButton) findViewById(R.id.know6) ;
        final ImageButton violet7 = (ImageButton) findViewById(R.id.violet7) ;
        final ImageButton orange8 = (ImageButton) findViewById(R.id.orange8) ;
        ImageButton next = (ImageButton) findViewById(R.id.next) ;

        Intent intent = getIntent();
        skinrgb= intent.getExtras().getString("RGB");

        Toast.makeText(this, skinrgb, Toast.LENGTH_LONG).show();

        isCheckedBtn();

        pink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index1) {
                    color = "Pink Shade";
                    pink1.setImageResource(R.drawable.pink_click);
                    coral2.setImageResource(R.drawable.coral2);
                    nude4.setImageResource(R.drawable.nude4);
                    red5.setImageResource(R.drawable.red5);
                    know6.setImageResource(R.drawable.know6);
                    violet7.setImageResource(R.drawable.violet7);
                    orange8.setImageResource(R.drawable.org8);
                    index1 = false;index2=true;index4=true;index5=true;index6=true;index7=true;index8=true;
                }else{
                    pink1.setImageResource(R.drawable.pink1);
                    index1 = true;
                }
                isCheckedBtn();
            }
        });

        coral2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index2) {
                    color = "Peach & Coral";
                    coral2.setImageResource(R.drawable.coral_click);
                    pink1.setImageResource(R.drawable.pink1);
                    nude4.setImageResource(R.drawable.nude4);
                    red5.setImageResource(R.drawable.red5);
                    know6.setImageResource(R.drawable.know6);
                    violet7.setImageResource(R.drawable.violet7);
                    orange8.setImageResource(R.drawable.org8);
                    index2 = false;index1=true;index4=true;index5=true;index6=true;index7=true;index8=true;
                }else{
                    coral2.setImageResource(R.drawable.coral2);
                    index2 = true;
                }
                isCheckedBtn();
            }
        });

/*        crazy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = list_data.size();
                if(ib3 == 0) {
                    list_data.add("crazy");
                    crazy3.setImageResource(R.drawable.crazy_click);
                    ib3++;
                } else if(ib3 == 1) {
                    for(int i =0; i<num;i++){
                        if(list_data.get(i)=="crazy") {
                            list_data.remove(i);
                            break;
                        }
                    }
                    crazy3.setImageResource(R.drawable.crazy3);
                    ib3--;
                }
            }
        });*/

        nude4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index4) {
                    color = "Nude Beige";
                    nude4.setImageResource(R.drawable.nude_click);
                    pink1.setImageResource(R.drawable.pink1);
                    coral2.setImageResource(R.drawable.coral2);
                    red5.setImageResource(R.drawable.red5);
                    know6.setImageResource(R.drawable.know6);
                    violet7.setImageResource(R.drawable.violet7);
                    orange8.setImageResource(R.drawable.org8);
                    index4 = false;index2=true;index1=true;index5=true;index6=true;index7=true;index8=true;
                }else{
                    nude4.setImageResource(R.drawable.nude4);
                    index4 = true;
                }
                isCheckedBtn();
            }
        });

        red5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index5) {
                    color = "Call me Red";
                    red5.setImageResource(R.drawable.red_click);
                    pink1.setImageResource(R.drawable.pink1);
                    coral2.setImageResource(R.drawable.coral2);
                    nude4.setImageResource(R.drawable.nude4);
                    know6.setImageResource(R.drawable.know6);
                    violet7.setImageResource(R.drawable.violet7);
                    orange8.setImageResource(R.drawable.org8);
                    index5 = false;index2=true;index4=true;index1=true;index6=true;index7=true;index8=true;
                }else{
                    red5.setImageResource(R.drawable.red5);
                    index5 = true;
                }
                isCheckedBtn();
            }
        });
        know6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index6) {
                    color = "I'dont know";
                    know6.setImageResource(R.drawable.know_click);
                    red5.setImageResource(R.drawable.red5);
                    pink1.setImageResource(R.drawable.pink1);
                    coral2.setImageResource(R.drawable.coral2);
                    nude4.setImageResource(R.drawable.nude4);
                    violet7.setImageResource(R.drawable.violet7);
                    orange8.setImageResource(R.drawable.org8);
                    index5 = true;index2=true;index4=true;index1=true;index6=false;index7=true;index8=true;
                }else{
                    know6.setImageResource(R.drawable.know6);
                    index6 = true;
                }
                isCheckedBtn();
            }
        });

        violet7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index7) {
                    color = "Violet";
                    violet7.setImageResource(R.drawable.violet_click);
                    know6.setImageResource(R.drawable.know6);
                    pink1.setImageResource(R.drawable.pink1);
                    coral2.setImageResource(R.drawable.coral2);
                    nude4.setImageResource(R.drawable.nude4);
                    red5.setImageResource(R.drawable.red5);
                    orange8.setImageResource(R.drawable.org8);
                    index7 = false;index2=true;index4=true;index5=true;index6=true;index1=true;index8=true;
                }else{
                    violet7.setImageResource(R.drawable.violet7);
                    index7 = true;
                }
                isCheckedBtn();
            }
        });

        orange8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index8) {
                    color = "Orange";
                    orange8.setImageResource(R.drawable.org_click);
                    pink1.setImageResource(R.drawable.pink1);
                    coral2.setImageResource(R.drawable.coral2);
                    nude4.setImageResource(R.drawable.nude4);
                    red5.setImageResource(R.drawable.red5);
                    know6.setImageResource(R.drawable.know6);
                    violet7.setImageResource(R.drawable.violet7);
                    index8 = false;index2=true;index4=true;index5=true;index6=true;index7=true;index1=true;
                }else{
                    orange8.setImageResource(R.drawable.org8);
                    index8 = true;
                }
                isCheckedBtn();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Condition2Activity.class);
                intent.putExtra("RGB", skinrgb);
                intent.putExtra("color",color);
                startActivity(intent);
            }
        });
    }

    public void isCheckedBtn(){
        ImageButton next = (ImageButton) findViewById(R.id.next) ;
        if(index1 && index2 && index4 && index5 && index6 && index7 && index8){
            //next.setBackgroundColor(Color.parseColor("#8C8C8C"));
            next.setImageResource(R.drawable.condition1_un);
            next.setEnabled(false);
        }else{
            //next.setBackgroundColor(Color.parseColor("#A90008"));
            next.setImageResource(R.drawable.condition1);
            next.setEnabled(true);
        }
    }
}

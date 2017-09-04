package ctc.kopo.pchu.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ctc.kopo.pchu.R;

/**
 * Created by Polytech on 2017-08-09.
 */

public class AnalysisActivity extends AppCompatActivity{
    String standardRGB;
    int personalColorRow = 0;
    int personalColorColumn = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis);

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
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                startActivity(intent);
            }
        });

        String skinrgb = null;
        Intent intent = getIntent();
        skinrgb= intent.getExtras().getString("RGB");

        getRGB(skinrgb);
        setCondition();

        ImageButton next = (ImageButton) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ConditionActivity.class);
                intent.putExtra("RGB", standardRGB);
                startActivity(intent);
            }
        });
    }

    void getRGB(String skinrgb){
        float shortestSkin = 99999999;
        String rgb = null;
        
        int color = (int) Long.parseLong(skinrgb.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        try {
            InputStream is = getAssets().open("skinLevel.csv");
            //CSVReader reader = new CSVReader(new FileReader("skinLevel.csv"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            int j = 0;
            while ((line = reader.readLine()) != null) {
                // do something with "line"
                String curLine[] =line.split(",");
                for (int i = 0; i < curLine.length; i++) {
                    int indexColor = (int) Long.parseLong(curLine[i].replace("#", ""), 16);
                    int indexR = (indexColor >> 16) & 0xFF;
                    int indexG = (indexColor >> 8) & 0xFF;
                    int indexB = (indexColor >> 0) & 0xFF;
                    float distance = getdistance(r, g, b, indexR, indexG, indexB);
                    if (shortestSkin > distance) {
                        rgb = curLine[i];
                        shortestSkin = distance;
                        personalColorColumn = i;
                        personalColorRow = j;
                    }
                }
                j++;
            }
        } catch (IOException ex) {
            // handle exception
        }
        LinearLayout RowContainer = (LinearLayout) findViewById(R.id.skincontainer);
        LinearLayout Colcontainer = (LinearLayout) RowContainer.getChildAt(personalColorRow);
        ImageView imgv = (ImageView)Colcontainer.getChildAt(personalColorColumn);
        //imgv.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        imgv.setImageResource(R.drawable.star);
    }

    static float getdistance(int r, int g, int b, int c1r, int c1g, int c1b){
        float distance;
        distance = (float) (Math.sqrt(Math.pow(Math.abs(r-c1r),2)+Math.pow(Math.abs(g-c1g),2)+Math.pow(Math.abs(b-c1b),2)));
        return distance;
    }
    public static void startWithColorPalette(Context context, String a) {
        final boolean isActivity = context instanceof Activity;
//        final Rect startBounds = new Rect();

        final Intent intent = new Intent(context, AnalysisActivity.class);
        intent.putExtra("RGB", a);

        if (!isActivity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);

        if (isActivity) {
            ((Activity) context).overridePendingTransition(0, 0);
        }
    }

    void setCondition(){
        int[] coolarr = {R.drawable.cool1,R.drawable.cool2,R.drawable.cool3,R.drawable.cool4,R.drawable.cool5,R.drawable.cool6
                ,R.drawable.cool7,R.drawable.cool8,R.drawable.cool9,R.drawable.cool10,R.drawable.cool11,R.drawable.cool12};
        int[] neuarr = {R.drawable.neutral1,R.drawable.neutral2,R.drawable.neutral3,R.drawable.neutral4,R.drawable.neutral5,R.drawable.neutral6
                ,R.drawable.neutral7,R.drawable.neutral8,R.drawable.neutral9,R.drawable.neutral10,R.drawable.neutral11,R.drawable.neutral12};
        int[] warmarr = {R.drawable.warm1,R.drawable.warm2,R.drawable.warm3,R.drawable.warm4,R.drawable.warm5,R.drawable.warm6
                ,R.drawable.warm7,R.drawable.warm8,R.drawable.warm9,R.drawable.warm10,R.drawable.warm11,R.drawable.warm12};

        ImageView skin = (ImageView) findViewById(R.id.skin_color);
        ImageView lip = (ImageView) findViewById(R.id.lip_color);
        ImageView condi = (ImageView) findViewById(R.id.condi_txt);

        if(personalColorRow<2 && personalColorColumn<2){
            lip.setImageResource(R.drawable.lip_cool1);
            condi.setImageResource(R.drawable.txt_cool1);
            standardRGB = "skin1";
            if(personalColorRow==0 && personalColorColumn==0){
                skin.setImageResource(coolarr[0]);
            }else if(personalColorRow==0 && personalColorColumn==1){
                skin.setImageResource(coolarr[1]);
            }else if(personalColorRow==1 && personalColorColumn==0){
                skin.setImageResource(coolarr[2]);
            }else if(personalColorRow==1 && personalColorColumn==1){
                skin.setImageResource(coolarr[3]);
            }
        }else if(personalColorRow<2 && personalColorColumn<4){
            lip.setImageResource(R.drawable.lip_cool2);
            condi.setImageResource(R.drawable.txt_cool2);
            standardRGB = "skin4";
            if(personalColorRow==0 && personalColorColumn==2){
                skin.setImageResource(coolarr[4]);
            }else if(personalColorRow==0 && personalColorColumn==3){
                skin.setImageResource(coolarr[5]);
            }else if(personalColorRow==1 && personalColorColumn==2){
                skin.setImageResource(coolarr[6]);
            }else if(personalColorRow==1 && personalColorColumn==3){
                skin.setImageResource(coolarr[7]);
            }
        }else if(personalColorRow<2 && personalColorColumn<6){
            lip.setImageResource(R.drawable.lip_cool3);
            condi.setImageResource(R.drawable.txt_cool3);
            standardRGB = "skin7";
            if(personalColorRow==0 && personalColorColumn==4){
                skin.setImageResource(coolarr[8]);
            }else if(personalColorRow==0 && personalColorColumn==5){
                skin.setImageResource(coolarr[9]);
            }else if(personalColorRow==1 && personalColorColumn==4){
                skin.setImageResource(coolarr[10]);
            }else if(personalColorRow==1 && personalColorColumn==5){
                skin.setImageResource(coolarr[11]);
            }
        }else if(personalColorRow<4 && personalColorColumn<2){
            lip.setImageResource(R.drawable.lip_neu1);
            condi.setImageResource(R.drawable.txt_neu1);
            standardRGB = "skin2";
            if(personalColorRow==2 && personalColorColumn==0){
                skin.setImageResource(neuarr[0]);
            }else if(personalColorRow==2 && personalColorColumn==1){
                skin.setImageResource(neuarr[1]);
            }else if(personalColorRow==3 && personalColorColumn==0){
                skin.setImageResource(neuarr[2]);
            }else if(personalColorRow==3 && personalColorColumn==1){
                skin.setImageResource(neuarr[3]);
            }
        }else if(personalColorRow<4 && personalColorColumn<4){
            lip.setImageResource(R.drawable.lip_neu2);
            condi.setImageResource(R.drawable.txt_neu2);
            standardRGB = "skin5";
            if(personalColorRow==2 && personalColorColumn==2){
                skin.setImageResource(neuarr[4]);
            }else if(personalColorRow==2 && personalColorColumn==3){
                skin.setImageResource(neuarr[5]);
            }else if(personalColorRow==3 && personalColorColumn==2){
                skin.setImageResource(neuarr[6]);
            }else if(personalColorRow==3 && personalColorColumn==3){
                skin.setImageResource(neuarr[7]);
            }
        }else if(personalColorRow<4 && personalColorColumn<6){
            lip.setImageResource(R.drawable.lip_neu3);
            condi.setImageResource(R.drawable.txt_neu3);
            standardRGB = "skin8";
            if(personalColorRow==2 && personalColorColumn==4){
                skin.setImageResource(neuarr[8]);
            }else if(personalColorRow==2 && personalColorColumn==5){
                skin.setImageResource(neuarr[9]);
            }else if(personalColorRow==3 && personalColorColumn==4){
                skin.setImageResource(neuarr[10]);
            }else if(personalColorRow==3 && personalColorColumn==5){
                skin.setImageResource(neuarr[11]);
            }
        }else if(personalColorRow<6 && personalColorColumn<2){
            lip.setImageResource(R.drawable.lip_warm1);
            condi.setImageResource(R.drawable.txt_warm1);
            standardRGB = "skin3";
            if(personalColorRow==4 && personalColorColumn==0){
                skin.setImageResource(warmarr[0]);
            }else if(personalColorRow==4 && personalColorColumn==1){
                skin.setImageResource(warmarr[1]);
            }else if(personalColorRow==5 && personalColorColumn==0){
                skin.setImageResource(warmarr[2]);
            }else if(personalColorRow==5 && personalColorColumn==1){
                skin.setImageResource(warmarr[3]);
            }
        }else if(personalColorRow<6 && personalColorColumn<4){
            lip.setImageResource(R.drawable.lip_warm2);
            condi.setImageResource(R.drawable.txt_warm2);
            standardRGB = "skin6";
            if(personalColorRow==4 && personalColorColumn==2){
                skin.setImageResource(warmarr[4]);
            }else if(personalColorRow==4 && personalColorColumn==3){
                skin.setImageResource(warmarr[5]);
            }else if(personalColorRow==5 && personalColorColumn==2){
                skin.setImageResource(warmarr[6]);
            }else if(personalColorRow==5 && personalColorColumn==3){
                skin.setImageResource(warmarr[7]);
            }
        }else if(personalColorRow<6 && personalColorColumn<6){
            lip.setImageResource(R.drawable.lip_warm3);
            condi.setImageResource(R.drawable.txt_warm3);
            standardRGB = "skin9";
            if(personalColorRow==4 && personalColorColumn==4){
                skin.setImageResource(warmarr[8]);
            }else if(personalColorRow==4 && personalColorColumn==5){
                skin.setImageResource(warmarr[9]);
            }else if(personalColorRow==5 && personalColorColumn==4){
                skin.setImageResource(warmarr[10]);
            }else if(personalColorRow==5 && personalColorColumn==5){
                skin.setImageResource(warmarr[11]);
            }
        }
    }
}

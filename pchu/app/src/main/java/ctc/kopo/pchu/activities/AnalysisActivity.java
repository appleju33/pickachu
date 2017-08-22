package ctc.kopo.pchu.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis);

        String skinrgb = null;
        Intent intent = getIntent();
        skinrgb= intent.getExtras().getString("RGB");

        standardRGB = getRGB(skinrgb);

        ImageButton next = (ImageButton) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Condition1Activity.class);
                intent.putExtra("RGB", standardRGB);
                startActivity(intent);
            }
        });
    }


    String getRGB(String skinrgb){
        int personalColorRow = 0;
        int personalColorColumn = 0;
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
        imgv.setImageResource(R.drawable.page_not);

        return rgb;
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
}

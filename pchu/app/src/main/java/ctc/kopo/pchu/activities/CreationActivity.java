package ctc.kopo.pchu.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.data.ColorItem;
import ctc.kopo.pchu.data.ColorItems;
import ctc.kopo.pchu.data.Palette;
import ctc.kopo.pchu.data.Palettes;

/**
 * Created by Polytech on 2017-08-01.
 */

public class CreationActivity extends AppCompatActivity{

    private List<ColorItem> savedColorsItems;

    protected void onCreate(Bundle savedInstanceState) { // 액티비티 생성시
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation);

        //오늘 날짜로 피부색이름 지정
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        String getTime = sdf.format(date);

        //화이트밸런스를 위한 하얀 종이 rgb의 hex값
        Intent intent = getIntent();
        String whitebalance = intent.getExtras().getString("whitebalance");
        String skinName = intent.getExtras().getString("skinname");
        //intent.putExtra("whitebalance",whitebalance);
        Toast.makeText(this, whitebalance, Toast.LENGTH_LONG).show();

        //피부색 저장
        savedColorsItems = ColorItems.getSavedColorItems(this);

        //내일 이거 계산 메소드 만들기
//        String a = savedColorsItems.get(0).getHexString();
//        int inputColor = Color.parseColor(whitebalance);
//        savedColorsItems.get(0).setColor(inputColor);

        Palette newPalette = make("["+skinName+"] "+getTime);

        SkinDB task = new SkinDB();

        task.execute(savedColorsItems.get(0).getHexString().replace("#",""),savedColorsItems.get(1).getHexString().replace("#",""),savedColorsItems.get(2).getHexString().replace("#",""),savedColorsItems.get(3).getHexString().replace("#",""),savedColorsItems.get(4).getHexString().replace("#",""));


        //저장된 칼라
        if (Palettes.saveColorPalette(getApplicationContext(), newPalette)) {
            for(ColorItem colorItem : savedColorsItems ) {
                ColorItems.deleteColorItem(this, colorItem);
            }
            finish();
        }

    }
    public Palette make(String name) {
        return new Palette(name, savedColorsItems);
    }


    class SkinDB extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://iamhpd7.cafe24.com/PickChu/insertSkin.jsp?pick1="+strings[0]+"&pick2="+strings[1]+"&pick3="+strings[2]+"&pick4="+strings[3]+"&pick5="+strings[4]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "pick1="+strings[0]+"&pick2="+strings[1]+"&pick3="+strings[2]+"pick4="+strings[3]+"&pick5="+strings[4];
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
}


package ctc.kopo.pchu.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.views.JsonItem;

/**
 * Created by Polytech on 2017-08-01.
 */

public class ResultActivity extends AppCompatActivity{

    String color = null;
    String mood  = null;
    String colorType = null;
    String moodType = null;
    String skinrgb = null;
    double distance = 0;

    Button tv_color1;
    Button tv_color2;
    Button tv_color3;
    Button tv_color4;
    TextView colortxt;
    TextView moodtxt;
    ImageView re_iv;
    ImageView re_iv2;
    ImageView re_iv3;

    String setid[] = {"","",""};
    String setr[] = {"","",""};
    String setg[] = {"","",""};
    String setb[] = {"","",""};

    String rgb[] = new String[3];
    String condrgb[] = new String[3];

    private int cnt = 0;

    private ArrayList<JsonItem> Pitem = new ArrayList<JsonItem>() ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result2);

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
        
        //intent 데이터 받아오기
        Intent intent = getIntent();
        color= intent.getExtras().getString("color");
        mood= intent.getExtras().getString("mood");
        skinrgb = intent.getExtras().getString("RGB");

        colorType = changeColor(color);
        moodType = changeMood(mood);

        colortxt = (TextView) findViewById(R.id.selectedcolor);
        moodtxt = (TextView) findViewById(R.id.selectedmood);
        tv_color1 = (Button) findViewById(R.id.tv_color1);
        tv_color2 = (Button) findViewById(R.id.tv_color2);
        tv_color3 = (Button) findViewById(R.id.tv_color3);
        tv_color4 = (Button) findViewById(R.id.tv_color4);
        re_iv = (ImageView) findViewById(R.id.result_iv);
        re_iv2 = (ImageView) findViewById(R.id.result_iv2);
        re_iv3 = (ImageView) findViewById(R.id.result_iv3);

        Toast.makeText(this, skinrgb, Toast.LENGTH_LONG).show();

        colortxt.setText(color+"");
        moodtxt.setText(mood+"");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        xmlAsyncTask xml = new xmlAsyncTask();
//        xml.execute();

        getData("http://iamhpd7.cafe24.com/PickChu/productPic.php");

//        피드백 선택 부분, 잠시 보류
        tv_color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ResultActivity.this)
                        .setMessage("이 상품으로 선택 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                CustomTask task = new CustomTask();
                                task.execute("1", colorType, moodType, Pitem.get(0).getColorR(), Pitem.get(1).getColorR(), Pitem.get(2).getColorR(), Pitem.get(0).getColorG(), Pitem.get(1).getColorG(), Pitem.get(2).getColorG(), Pitem.get(0).getColorB(), Pitem.get(1).getColorB(), Pitem.get(2).getColorB() , "1");
                                Toast toast = Toast.makeText(getApplication(),"선택 완료!",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,300);
                                toast.show();
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();
            }
        });

        tv_color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ResultActivity.this)
                        .setMessage("이 상품으로 선택 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                CustomTask task = new CustomTask();
                                task.execute("1", colorType, moodType, Pitem.get(1).getColorR(), Pitem.get(0).getColorR(), Pitem.get(2).getColorR(), Pitem.get(1).getColorG(), Pitem.get(0).getColorG(), Pitem.get(2).getColorG(), Pitem.get(1).getColorB(), Pitem.get(0).getColorB(), Pitem.get(2).getColorB() , "1");
                                Toast toast = Toast.makeText(getApplication(),"선택 완료!",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,300);
                                toast.show();
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();
            }
        });

        tv_color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ResultActivity.this)
                        .setMessage("이 상품으로 선택 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                CustomTask task = new CustomTask();
                                task.execute("1", colorType, moodType, Pitem.get(2).getColorR(), Pitem.get(1).getColorR(), Pitem.get(0).getColorR(), Pitem.get(2).getColorG(), Pitem.get(1).getColorG(), Pitem.get(0).getColorG(), Pitem.get(2).getColorB(), Pitem.get(1).getColorB(), Pitem.get(0).getColorB() , "1");
                                Toast toast = Toast.makeText(getApplication(),"선택 완료!",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,300);
                                toast.show();
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();
            }
        });

        tv_color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ResultActivity.this)
                        .setMessage("다른 상품을 추천 받으시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                if (cnt < 2) {
                                    CustomTask task = new CustomTask();
                                    task.execute("1", colorType, moodType, Pitem.get(2).getColorR(), Pitem.get(1).getColorR(), Pitem.get(0).getColorR(), Pitem.get(2).getColorG(), Pitem.get(1).getColorG(), Pitem.get(0).getColorG(), Pitem.get(2).getColorB(), Pitem.get(1).getColorB(), Pitem.get(0).getColorB(), "0");
                                    Pitem.remove(0);
                                    Pitem.remove(1);
                                    Pitem.remove(2);

                                    Bitmap bitmap []= {loadBitmap(Pitem.get(0).getImg()+".jpg"), loadBitmap(Pitem.get(1).getImg()+".jpg"), loadBitmap(Pitem.get(2).getImg()+".jpg")};
                                    re_iv.setImageBitmap(bitmap[0]);
                                    re_iv2.setImageBitmap(bitmap[1]);
                                    re_iv3.setImageBitmap(bitmap[2]);

                                    tv_color1.setText("name: "+Pitem.get(0).getItemname()+"\n price: "+Pitem.get(0).getPrice()+"\n img: "+Pitem.get(0).getImg()+"\n hex: "+Pitem.get(0).getHex()+"\n dis: "+Pitem.get(0).getDistance());
                                    tv_color1.setBackgroundColor(Color.rgb(Integer.parseInt(Pitem.get(0).getColorR()),Integer.parseInt(Pitem.get(0).getColorG()),Integer.parseInt(Pitem.get(0).getColorB())));
                                    tv_color2.setText("name: "+Pitem.get(1).getItemname()+"\n price: "+Pitem.get(1).getPrice()+"\n img: "+Pitem.get(1).getImg()+"\n hex: "+Pitem.get(1).getHex()+"\n dis: "+Pitem.get(1).getDistance());
                                    tv_color2.setBackgroundColor(Color.rgb(Integer.parseInt(Pitem.get(1).getColorR()),Integer.parseInt(Pitem.get(1).getColorG()),Integer.parseInt(Pitem.get(1).getColorB())));
                                    tv_color3.setText("name: "+Pitem.get(2).getItemname()+"\n price: "+Pitem.get(2).getPrice()+"\n img: "+Pitem.get(2).getImg()+"\n hex: "+Pitem.get(2).getHex()+"\n dis: "+Pitem.get(2).getDistance());
                                    tv_color3.setBackgroundColor(Color.rgb(Integer.parseInt(Pitem.get(2).getColorR()),Integer.parseInt(Pitem.get(2).getColorG()),Integer.parseInt(Pitem.get(2).getColorB())));

                                    cnt++;
                                }else{
                                    Toast toast = Toast.makeText(getApplication(),"다른 상품 추천받기 한도를 초과하셨습니다.",Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();
            }
        });
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Integer, String> {
            @Override
            protected String doInBackground(String... urls) {
                StringBuilder jsonHtml = new StringBuilder();
                try {
                    // 연결 url 설정
                    URL url = new URL(urls[0]);
                    // 커넥션 객체 생성
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 연결되었으면.
                    if (conn != null) {
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);
                        // 연결되었음 코드가 리턴되면.
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader br =
                                    new BufferedReader(
                                            new InputStreamReader(conn.getInputStream(), "UTF-8"));
                            for ( ; ; ) {
                                // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                                String line = br.readLine();
                                if (line == null) break;
                                // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                                jsonHtml.append(line + "\n");
                            }
                            br.close();
                        }
                        conn.disconnect();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return jsonHtml.toString();
            }

            @Override
            protected void onPostExecute(String result) {
                // System.out.println("result = " + result);
                showList(result);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    protected void showList(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            // json 배열의 이름을 검색
            JSONArray snsDatas = jsonObj.getJSONArray("result");

            for (int i = 0; i < snsDatas.length(); i++) {
                JsonItem jsonItem = new JsonItem();

                JSONObject c = snsDatas.getJSONObject(i);
                jsonItem.setColorR(c.getString("r"));
                jsonItem.setColorG(c.getString("g"));
                jsonItem.setColorB(c.getString("b"));
                jsonItem.setBrand(c.getString("brand"));
                jsonItem.setItemname(c.getString("itemname"));
                jsonItem.setColorname(c.getString("colorname"));
                jsonItem.setPrice(c.getString("price"));
                jsonItem.setImg(c.getString("img"));
                jsonItem.setHex(c.getString("hex"));
                distance = getdistance(Integer.parseInt(c.getString("r")),Integer.parseInt(c.getString("g")),Integer.parseInt(c.getString("b")),
                        Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2]),
                        Integer.parseInt(condrgb[0]),Integer.parseInt(condrgb[1]),Integer.parseInt(condrgb[2]));
                jsonItem.setDistance(distance);

                if(Pitem.size() > 8) {
                    for (int j = 0; j < 9; j++) {
                        if(Pitem.get(j).getDistance() > distance){
                            Pitem.remove(j);
                            Pitem.add(jsonItem);
                            break;
                        }
                    }
                }else {
                    Pitem.add(jsonItem);
                }
            }

            Collections.sort(Pitem, new Comparator<JsonItem>() {
                @Override
                public int compare(JsonItem o1, JsonItem o2) {
                    return (o1.getDistance() < o2.getDistance()?-1:(o1.getDistance()>o2.getDistance())?1:0);
                }
            });

            Bitmap bitmap []= {loadBitmap(Pitem.get(0).getImg()+".jpg"), loadBitmap(Pitem.get(1).getImg()+".jpg"), loadBitmap(Pitem.get(2).getImg()+".jpg")};
            re_iv.setImageBitmap(bitmap[0]);
            re_iv2.setImageBitmap(bitmap[1]);
            re_iv3.setImageBitmap(bitmap[2]);

            tv_color1.setText("name: "+Pitem.get(0).getItemname()+"\n price: "+Pitem.get(0).getPrice()+"\n img: "+Pitem.get(0).getImg()+"\n hex: "+Pitem.get(0).getHex()+"\n dis: "+Pitem.get(0).getDistance());
            tv_color1.setBackgroundColor(Color.rgb(Integer.parseInt(Pitem.get(0).getColorR()),Integer.parseInt(Pitem.get(0).getColorG()),Integer.parseInt(Pitem.get(0).getColorB())));
            tv_color2.setText("name: "+Pitem.get(1).getItemname()+"\n price: "+Pitem.get(1).getPrice()+"\n img: "+Pitem.get(1).getImg()+"\n hex: "+Pitem.get(1).getHex()+"\n dis: "+Pitem.get(1).getDistance());
            tv_color2.setBackgroundColor(Color.rgb(Integer.parseInt(Pitem.get(1).getColorR()),Integer.parseInt(Pitem.get(1).getColorG()),Integer.parseInt(Pitem.get(1).getColorB())));
            tv_color3.setText("name: "+Pitem.get(2).getItemname()+"\n price: "+Pitem.get(2).getPrice()+"\n img: "+Pitem.get(2).getImg()+"\n hex: "+Pitem.get(2).getHex()+"\n dis: "+Pitem.get(2).getDistance());
            tv_color3.setBackgroundColor(Color.rgb(Integer.parseInt(Pitem.get(2).getColorR()),Integer.parseInt(Pitem.get(2).getColorG()),Integer.parseInt(Pitem.get(2).getColorB())));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://iamhpd7.cafe24.com/PickChu/insertDB.jsp?sc="+strings[0]+"&rmd1="+strings[1]+"&rmd2="+strings[2]+"&r1="+strings[3]+"&r2="+strings[4]+"&r3="+strings[5]+"&g1="+strings[6]+"&g2="+strings[7]+"&g3="+strings[8]+"&b1="+strings[9]+"&b2="+strings[10]+"&b3="+strings[11]+"&fb="+strings[12]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "sc="+strings[0]+"&rmd1="+strings[1]+"&rmd2="+strings[2]+"&r1="+strings[3]+"&r2="+strings[4]+"&r3="+strings[5]+"&g1="+strings[6]+"&g2="+strings[7]+"&g3="+strings[8]+"&b1="+strings[9]+"&b2="+strings[10]+"&b3="+strings[11]+"&fb="+strings[12];
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



    public String changeColor(String color){

        if(color.equals("nude Beige")){
            rgb = new String[] {"236", "208", "181"};
            return "0000001";
        }else if(color.equals("Peach & Coral")){
            rgb= new String[]{"236", "104", "91"};
            return "0000010";
        }else if(color.equals("Pink Shade")){
            rgb= new String[]{"209", "48", "118"};
            return "0000100";
        }else if(color.equals("Call me Red")){
            rgb= new String[]{"255", "0", "0"};
            return "0001000";
        }else if(color.equals("Orange")){
            rgb= new String[]{"254", "89", "0"};
            return "0010000";
        }else if(color.equals("Violet")){
            rgb= new String[]{"68", "0", "153"};
            return "0100000";
        }else{
            rgb[0] = Integer.toString((int) (Math.random()*255));
            rgb[1] = Integer.toString((int) (Math.random()*255));
            rgb[2] = Integer.toString((int) (Math.random()*255));
            return "1000000";
        }
    }


    public String inputColor(String color){

        if(color.equals("Call me Red")){
            return "red";
        }else if(color.equals("Peach & Coral")){
            return "coral";
        }else if(color.equals("Pink Shade")){
            return "pink";
        }else if(color.equals("nude Beige")){
            return "beige";
        }else if(color.equals("Violet")){
            return "purple";
        }else if(color.equals("Orange")){
            return "orange";
        }else{
            return "-";
        }
    }

    public String changeMood(String mood){

        if(mood.equals("분위기")){
            condrgb= new String[]{"237", "127", "143"};
            return "000000000001";
        }else if(mood.equals("강렬")){
            condrgb= new String[]{"166", "48", "93"};
            return "000000000010";
        }else if(mood.equals("우울")){
            condrgb= new String[]{"168", "48", "75"};
            return "000000000100";
        }else if(mood.equals("창백")){
            condrgb= new String[]{"221", "144", "142"};
            return "000000001000";
        }else if(mood.equals("데이트")){
            condrgb= new String[]{"248", "140", "158"};
            return "000000010000";
        }else if(mood.equals("파티")){
            condrgb= new String[]{"245", "0", "136"};
            return "000000100000";
        }else if(mood.equals("스마트")){
            condrgb= new String[]{"243", "123", "122"};
            return "000001000000";
        }else if(mood.equals("청순")){
            condrgb= new String[]{"229", "148", "182"};
            return "000010000000";
        }else if(mood.equals("상큼")){
            condrgb= new String[]{"255", "61", "0"};
            return "000100000000";
        }else if(mood.equals("자연스러움")){
            condrgb= new String[]{"137", "144", "150"};
            return "001000000000";
        }else if(mood.equals("쎈 언니")){
            condrgb= new String[]{"148", "0", "64"};
            return "010000000000";
        }else{
            condrgb[0] = Integer.toString((int) (Math.random()*255));
            condrgb[1] = Integer.toString((int) (Math.random()*255));
            condrgb[2] = Integer.toString((int) (Math.random()*255));
            return "100000000000";
        }
    }

    static float getdistance(int r, int g, int b, int c1r, int c1g, int c1b, int c2r, int c2g, int c2b){
        float distance;
        distance = (float) (Math.sqrt(Math.pow(Math.abs(r-c1r),2)+Math.pow(Math.abs(g-c1g),2)+Math.pow(Math.abs(b-c1b),2)) +
                Math.sqrt(Math.pow(Math.abs(r-c2r),2)+Math.pow(Math.abs(g-c2g),2)+Math.pow(Math.abs(b-c2b),2)));
        return distance;
    }

    public Bitmap loadBitmap(String urlStr) {
        Bitmap bitmap = null;
        AssetManager mngr = getResources().getAssets();
        try{
            InputStream is = mngr.open(urlStr);
            bitmap = BitmapFactory.decodeStream(is);

        }catch(Exception e){

        }

        return bitmap;
    }
}

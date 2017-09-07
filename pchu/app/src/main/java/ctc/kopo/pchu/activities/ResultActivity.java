package ctc.kopo.pchu.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    //String color = null;
    int theme  = 0;
    String colorType = null;
//    String moodType = null;
    int skin = 0;
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

    int rgb[] = new int[3];
    String condrgb[] = new String[3];

    String[][] recommColor = {{"#d29696","#fa96a5","#ffd2e6","#fa3c2d","#ff6496","#dc8c96","#963c64","#c8000a","#f50087"},
                            {"#dc968c","#fa8282","#fabebe","#f54b2d","#f0648c","#dca0a0","#911e37","#d71919","#cd5f69"},
                            {"#d7967d","#fa6e6e","#f5beaa","#ff4619","#f05064","#dc8278","#a41e23","#e63201","#fa695a"},
                            {"#d2a0a0","#eb96a5","#ffbed2","#f55537","#f06ea0","#dc7882","#a34869","#ab0026","#cd6e96"},
                            {"#dca096","#eb8282","#faaaaa","#eb5537","#e65f82","#dc8c8c","#a54150","#c31928","#e1a091"},
                            {"#d7a087","#eb6e6e","#f5aa96","#f57d5f","#eb646e","#dc786e","#b44b4b","#be2301","#fa8c82"},
                            {"#d2afaf","#dc96a5","#ffaabe","#eb4632","#d22869","#dc646e","#550f41","#a0003c","#fab4dc"},
                            {"#dcb4aa","#dc8282","#fa9696","#ff733c","#e14173","#dc7878","#821e32","#a51928","#b43232"},
                            {"#d7aa91","#dc6e6e","#f59682","#ff6423","#f04169","#dc6e64","#6e1428","#a51401","#aa6964"}};

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
        //color= intent.getExtras().getString("color");
        theme= Integer.parseInt(intent.getExtras().getString("mood").replace("theme",""))-1;
        skin = Integer.parseInt(intent.getExtras().getString("RGB").replace("skin",""))-1;

//        colorType = changeColor(color);
//        moodType = changeMood(mood);
        if(theme==9){
            colorType = "#ffffff";
        }else {
            colorType = recommColor[skin][theme];
        }
        //Toast.makeText(this, colorType, Toast.LENGTH_LONG).show();

        changeColor(colorType);


        colortxt = (TextView) findViewById(R.id.selectedcolor);
        moodtxt = (TextView) findViewById(R.id.selectedmood);
        tv_color1 = (Button) findViewById(R.id.tv_color1);
        tv_color2 = (Button) findViewById(R.id.tv_color2);
        tv_color3 = (Button) findViewById(R.id.tv_color3);
        tv_color4 = (Button) findViewById(R.id.tv_color4);
        re_iv = (ImageView) findViewById(R.id.result_iv);
        re_iv2 = (ImageView) findViewById(R.id.result_iv2);
        re_iv3 = (ImageView) findViewById(R.id.result_iv3);

        //Toast.makeText(this, skinrgb, Toast.LENGTH_LONG).show();

//        colortxt.setText(color+"");
//        moodtxt.setText(mood+"");

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
                                task.execute(String.valueOf(skin+1), String.valueOf(theme+1), Pitem.get(0).getColorR(), Pitem.get(1).getColorR(), Pitem.get(2).getColorR(), Pitem.get(0).getColorG(), Pitem.get(1).getColorG(), Pitem.get(2).getColorG(), Pitem.get(0).getColorB(), Pitem.get(1).getColorB(), Pitem.get(2).getColorB() , "1");
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
                                task.execute(String.valueOf(skin+1), String.valueOf(theme+1), Pitem.get(1).getColorR(), Pitem.get(0).getColorR(), Pitem.get(2).getColorR(), Pitem.get(1).getColorG(), Pitem.get(0).getColorG(), Pitem.get(2).getColorG(), Pitem.get(1).getColorB(), Pitem.get(0).getColorB(), Pitem.get(2).getColorB() , "1");
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
                                task.execute(String.valueOf(skin+1), String.valueOf(theme+1), Pitem.get(2).getColorR(), Pitem.get(1).getColorR(), Pitem.get(0).getColorR(), Pitem.get(2).getColorG(), Pitem.get(1).getColorG(), Pitem.get(0).getColorG(), Pitem.get(2).getColorB(), Pitem.get(1).getColorB(), Pitem.get(0).getColorB() , "1");
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
                                    task.execute(String.valueOf(skin+1), String.valueOf(theme+1), Pitem.get(2).getColorR(), Pitem.get(1).getColorR(), Pitem.get(0).getColorR(), Pitem.get(2).getColorG(), Pitem.get(1).getColorG(), Pitem.get(0).getColorG(), Pitem.get(2).getColorB(), Pitem.get(1).getColorB(), Pitem.get(0).getColorB(), "0");
                                    Pitem.remove(0);
                                    Pitem.remove(1);
                                    Pitem.remove(2);

                                    //Bitmap bitmap []= {loadBitmap(Pitem.get(0).getImg()+".jpg"), loadBitmap(Pitem.get(1).getImg()+".jpg"), loadBitmap(Pitem.get(2).getImg()+".jpg")};
                                    try {
                                        Bitmap bitmap[] = {new back().execute(imgUrl + Pitem.get(0).getImg() + ".jpg").get(), new back().execute(imgUrl + Pitem.get(1).getImg() + ".jpg").get(), new back().execute(imgUrl + Pitem.get(2).getImg() + ".jpg").get()};
                                        re_iv.setImageBitmap(bitmap[0]);
                                        re_iv2.setImageBitmap(bitmap[1]);
                                        re_iv3.setImageBitmap(bitmap[2]);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

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
                        rgb[0],rgb[1],rgb[2]);
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

            //Bitmap bitmap []= {loadBitmap(Pitem.get(0).getImg()+".jpg"), loadBitmap(Pitem.get(1).getImg()+".jpg"), loadBitmap(Pitem.get(2).getImg()+".jpg")};

            //Toast.makeText(this, imgUrl + Pitem.get(0).getImg() + ".jpg", Toast.LENGTH_LONG).show();
            Bitmap bitmap[] = {new back().execute(imgUrl + Pitem.get(0).getImg() + ".jpg").get(), new back().execute(imgUrl + Pitem.get(1).getImg() + ".jpg").get(), new back().execute(imgUrl + Pitem.get(2).getImg() + ".jpg").get()};
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
        } catch (Exception alle){
            Log.d("에러발생",alle.getMessage());
        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://iamhpd7.cafe24.com/PickChu/insertDB.jsp?sc="+strings[0]+"&rmd="+strings[1]+"&r1="+strings[2]+"&r2="+strings[3]+"&r3="+strings[4]+"&g1="+strings[5]+"&g2="+strings[6]+"&g3="+strings[7]+"&b1="+strings[8]+"&b2="+strings[9]+"&b3="+strings[10]+"&fb="+strings[11]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "sc="+strings[0]+"&rmd="+strings[1]+"&r1="+strings[2]+"&r2="+strings[3]+"&r3="+strings[4]+"&g1="+strings[5]+"&g2="+strings[6]+"&g3="+strings[7]+"&b1="+strings[8]+"&b2="+strings[9]+"&b3="+strings[10]+"&fb="+strings[11];
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

    String imgUrl = "http://iamhpd7.cafe24.com/PickChu/img/";
    Bitmap bmImg;

    private class back extends AsyncTask<String, Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                //이미지 용량을 줄이기
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 2; // (1/2)로 줄임

                bmImg = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }

    }

    /*public Bitmap loadBitmap(String urlStr) {
        Bitmap bitmap = null;
        AssetManager mngr = getResources().getAssets();
        try{
            InputStream is = mngr.open(urlStr);
            bitmap = BitmapFactory.decodeStream(is);

        }catch(Exception e){

        }

        return bitmap;
    }*/

    static float getdistance(int r, int g, int b, int cr, int cg, int cb){
        float distance;
        distance = (float) (Math.sqrt(Math.pow(Math.abs(r-cr),2)+Math.pow(Math.abs(g-cg),2)+Math.pow(Math.abs(b-cb),2)));
        return distance;
    }
    public void changeColor(String hexcolor){

        hexcolor = hexcolor.replace("#","");
        if(hexcolor.equals("ffffff")) {
            rgb = new int[]{(int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)};
        }else {
            rgb = new int[]{Integer.parseInt(hexcolor.substring(0, 2), 16), Integer.parseInt(hexcolor.substring(2, 4), 16), Integer.parseInt(hexcolor.substring(4, 6), 16)};
        }
    }
}

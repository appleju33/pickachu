package ctc.kopo.pchu.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ctc.kopo.pchu.R;

/**
 * Created by Polytech on 2017-09-07.
 */

public class SelectedCustomDialog extends DialogFragment {
    public SelectedCustomDialog(){
    }

    String img;
    String brand;
    String product;
    String detail;
    String price;
    String hex;
    String rgbstr;
    String[] rgb;
    String skin;
    String theme;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.clickitempop, container);

        img = getArguments().getString("img");
        brand = getArguments().getString("brand");
        product = getArguments().getString("product");
        detail = getArguments().getString("detail");
        price = getArguments().getString("price");
        hex = getArguments().getString("hex");
        rgbstr = getArguments().getString("rgb");
        skin = getArguments().getString("skinType");
        theme = getArguments().getString("themeChoice");

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(rgbstr);
        rgb = new String[3];
        int i = 0;
        while(matcher.find()){
            //System.out.println(matcher.group(0));
            rgb[i] = matcher.group(0);
            i++;
        }

        ImageView sample = (ImageView) view.findViewById(R.id.itemimg);
        TextView brd = (TextView) view.findViewById(R.id.brand);
        TextView pdt = (TextView) view.findViewById(R.id.product);
        TextView dtl = (TextView) view.findViewById(R.id.detail);
        TextView prc = (TextView) view.findViewById(R.id.price);
        View bar = view.findViewById(R.id.hexbar);
        View cir = view.findViewById(R.id.hexcircle);

        brd.setText(brand);
        pdt.setText(product);
        dtl.setText(detail);
        prc.setText(price);
        bar.setBackgroundColor(Color.parseColor(hex));
        cir.setBackgroundColor(Color.parseColor(hex));

        try{
            Bitmap bitmap = new back().execute(imgUrl +img+".jpg").get();
            sample.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        final ImageButton likeBtn = (ImageButton) view.findViewById(R.id.like);
        final ImageButton sosoBtn = (ImageButton) view.findViewById(R.id.soso);
        final ImageButton youtubeBtn = (ImageButton) view.findViewById(R.id.youtubebtn);
        youtubeBtn.setEnabled(false);

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeBtn.setEnabled(false);
                sosoBtn.setEnabled(false);
                likeBtn.setImageResource(R.drawable.likebtn_un);
                sosoBtn.setImageResource(R.drawable.sosobtn_un);
                youtubeBtn.setEnabled(true);
                youtubeBtn.setImageResource(R.drawable.youtubebtn);
                CustomTask task = new CustomTask();
                task.execute(skin, theme, rgb[0], rgb[1], rgb[2], "1");
            }
        });

        sosoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeBtn.setEnabled(false);
                sosoBtn.setEnabled(false);
                likeBtn.setImageResource(R.drawable.likebtn_un);
                sosoBtn.setImageResource(R.drawable.sosobtn_un);
                youtubeBtn.setEnabled(true);
                youtubeBtn.setImageResource(R.drawable.youtubebtn);
                CustomTask task = new CustomTask();
                task.execute(skin, theme, rgb[0], rgb[1], rgb[2], "0");
            }
        });

        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;

        int dialogWidth = 1000; // specify a value here
        int dialogHeight = 750; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

        // ... other stuff you want to do in your onStart() method
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                //URL url = new URL("http://iamhpd7.cafe24.com/PickChu/insertDB.jsp?sc="+strings[0]+"&rmd="+strings[1]+"&r1="+strings[2]+"&r2="+strings[3]+"&r3="+strings[4]+"&g1="+strings[5]+"&g2="+strings[6]+"&g3="+strings[7]+"&b1="+strings[8]+"&b2="+strings[9]+"&b3="+strings[10]+"&fb="+strings[11]);
                URL url = new URL("http://iamhpd7.cafe24.com/PickChu/insertDB.jsp?sc="+strings[0]+"&rmd="+strings[1]+"&r="+strings[2]+"&g="+strings[3]+"&b="+strings[4]+"&fb="+strings[5]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //sendMsg = "sc="+strings[0]+"&rmd="+strings[1]+"&r1="+strings[2]+"&r2="+strings[3]+"&r3="+strings[4]+"&g1="+strings[5]+"&g2="+strings[6]+"&g3="+strings[7]+"&b1="+strings[8]+"&b2="+strings[9]+"&b3="+strings[10]+"&fb="+strings[11];
                sendMsg = "sc="+strings[0]+"&rmd="+strings[1]+"&r="+strings[2]+"&g="+strings[3]+"&b="+strings[4]+"&fb="+strings[5];
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

    private class back extends AsyncTask<String, Integer,Bitmap> {

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
}


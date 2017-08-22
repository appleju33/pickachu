package ctc.kopo.pchu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.adapter.ViewPagerAdapter;
import ctc.kopo.pchu.views.PaletteListPage;

/**
 * Created by koposw25 on 2017-08-04.
 */

public class PopupActivity extends AppCompatActivity {
    private PaletteListPage mPaletteListPage;
    TextView txtText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup);

        ViewPager pager= (ViewPager)findViewById(R.id.pager);
        ViewPagerAdapter adapter= new ViewPagerAdapter(this);
        //ViewPager에 Adapter 설정
        pager.setAdapter(adapter);

        final LinearLayout pager_layout = (LinearLayout) findViewById(R.id.pager_layout);
        final int[] mPrevPosition = {0};
        pager_layout.getChildAt(0).setBackgroundResource(R.drawable.page_select);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                pager_layout.getChildAt(mPrevPosition[0]).setBackgroundResource(R.drawable.page_not);	//이전 페이지에 해당하는 페이지 표시 이미지 변경
                pager_layout.getChildAt(position).setBackgroundResource(R.drawable.page_select);		//현재 페이지에 해당하는 페이지 표시 이미지 변경
                mPrevPosition[0] = position;
            }
        });
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        finish();
        return;
    }
}
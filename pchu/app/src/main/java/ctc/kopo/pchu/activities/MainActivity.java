package ctc.kopo.pchu.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.data.ColorItem;
import ctc.kopo.pchu.data.ColorItems;
import ctc.kopo.pchu.views.PaletteListPage;

public class MainActivity extends AppCompatActivity implements PaletteListPage.Listener {

    //피부색 리스트 & 설명서 페이지
    private PaletteListPage mPaletteListPage;

    //뒤로가기 연속 두번 눌렀을 때 종료하기 위한 시간체크
    private long lastTimeBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //레이아웃 내부의 뷰를 다루기 위함 (PaletteListPage를 불러옴)
        ViewGroup view = (ViewGroup) findViewById(R.id.container);
        mPaletteListPage = new PaletteListPage(this);
        mPaletteListPage.setListener(this);
        view.addView(mPaletteListPage);

        //ColorItems 초기화
        initColorItems();
        //actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );


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

        //카메라 권한 체크
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this,"카메라권한있음",Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this,"카메라 권한이 없습니다.",Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                //Toast.makeText(this,"피부색을 측정하려면 카메라 권한이 필요합니다.",Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

        //카메라로 갑시다
        ImageButton next = (ImageButton) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WhitePickerActivity.class);
                startActivity(intent);
            }
        });
    }


    //카메라 권한 주세요
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "카메라 권한이 거부되었습니다.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    //ColorItems 초기화
    public void initColorItems() {
        List<ColorItem> savedColorsItems = ColorItems.getSavedColorItems(this);
        for (ColorItem colorItem : savedColorsItems) {
            ColorItems.deleteColorItem(this, colorItem);
        }
    }

    //특정 시간 내에 뒤로가기를 한 번 더 누르면 종료
    public void onBackPressed() {
        // 1970년 1월 1일부터 지금까지 경과한 시간을 밀리초 단위로 반환하는 메서드
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
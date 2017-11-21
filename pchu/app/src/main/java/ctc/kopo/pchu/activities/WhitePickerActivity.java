package ctc.kopo.pchu.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.List;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.adapter.WhiteDialogAdapter;
import ctc.kopo.pchu.data.ColorItem;
import ctc.kopo.pchu.data.ColorItems;
import ctc.kopo.pchu.utils.Cameras;
import ctc.kopo.pchu.views.CameraColorPickerPreview;


/**
 * Created by Polytech on 2017-08-01.
 */

public class WhitePickerActivity extends AppCompatActivity implements CameraColorPickerPreview.OnColorSelectedListener, View.OnClickListener {
    Intent intent;
    ImageView tv = null;
    /**
     * A tag used in the logs.
     */
    protected static final String TAG = WhitePickerActivity.class.getSimpleName();

    /**
     * A boolean for knowing the orientation of the activity.
     */
    protected boolean mIsPortrait;

    /**
     * 저장 메시지가 나올 때 걸리는 시간
     */
    protected static final long DURATION_CONFIRM_SAVE_MESSAGE = 400;

    // 후면카메라 시작 메소드
    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return c;
    }

    /**
     * An instance of the {@link android.hardware.Camera} used for displaying the preview.
     */
    protected Camera mCamera;

    /**
     * A simple {@link android.widget.FrameLayout} that contains the preview.
     */
    protected FrameLayout mPreviewContainer;

    /**
     * The {@link CameraColorPickerPreview} used for the preview.
     */
    protected CameraColorPickerPreview mCameraPreview;

    /**
     * A reference to the {@link ColorPickerActivity.CameraAsyncTask} that gets the {@link android.hardware.Camera}.
     */
    protected CameraAsyncTask mCameraAsyncTask;

    /**
     * The color selected by the user.
     * <p/>
     * The user "selects" a color by pointing a color with the camera.
     */
    protected int mSelectedColor;

    /**
     * The last picked color.
     * <p/>
     * The user "picks" a color by clicking the preview.
     */
    protected int mLastPickedColor;

    /**
     * A simple {@link android.view.View} used for animating the color being picked.
     */
    protected View mPickedColorPreviewAnimated;

    /**
     * A simple {@link android.view.View} used for showing the selected color.
     */
    protected View mPointerRing;


    /**
     * the intent {@link android.content.Intent#getAction action} that led to this activity.
     */
    protected String action = null;

    // 중복 클릭 방지 시간 설정
    private static final long MIN_CLICK_INTERVAL=500;

    private long mLastClickTime;

    /**
     * <a href="http://www.openintents.org/action/org-openintents-action-pick-color/">
     * see openintents.org</a>
     */
    public static final String OI_COLOR_PICKER = "org.openintents.action.PICK_COLOR";
    public static final String OI_COLOR_DATA = "org.openintents.extra.COLOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 생성시
        super.onCreate(savedInstanceState);
        setContentView(R.layout.white_paper_picker);

        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);

        intent = new Intent(getApplicationContext(),ColorPickerActivity.class);

        //초기 뷰들 정의
        initViews();

        Intent intent = getIntent();
        if (intent != null)
            action = intent.getAction();

        showDialog(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Setup the camera asynchronously.
        mCameraAsyncTask = new CameraAsyncTask();
        mCameraAsyncTask.execute();


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        final String [] items = new String[] {"백열등", "형광등", "자연광"};
        //final int [] icons = new int[] {R.drawable.inclamp,R.drawable.flulamp,R.drawable.sun};
        WhiteDialogAdapter adapter = new WhiteDialogAdapter();
        adapter.addItem(R.drawable.inclamp,items[0]);
        adapter.addItem(R.drawable.flulamp,items[1]);
        adapter.addItem(R.drawable.sun,items[2]);

        AlertDialog.Builder builder = new AlertDialog.Builder(WhitePickerActivity.this);
        //builder.setTitle("조명 선택");
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });


        builder.setAdapter(adapter,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //Toast.makeText(WhitePickerActivity.this, items[which], Toast.LENGTH_SHORT).show();
                intent.putExtra("lamp",items[which]);
                dialog.dismiss(); // 누르면 바로 닫히는 형태
            }
        });
//
//        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//                Toast.makeText(WhitePickerActivity.this, items[which], Toast.LENGTH_SHORT).show();
//                dialog.dismiss(); // 누르면 바로 닫히는 형태
//            }
//        });
        return builder.create();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Cancel the Camera AsyncTask.
        mCameraAsyncTask.cancel(true);

        // Release the camera.
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }

        // Remove the camera preview
        if (mCameraPreview != null) {
            mPreviewContainer.removeView(mCameraPreview);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onColorSelected(int color) {
        mSelectedColor = color;
        mPointerRing.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP); // 사진 포인터 바깥 원에 색을 지정
    }

    @Override
    // 카메라뷰 클릭시 색을 저장하는 곳
    public void onClick(View v) {
        long currentClickTime= SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;
        String rgb = null;
        if(elapsedTime<=MIN_CLICK_INTERVAL){
            return;
        }
        if (v == mCameraPreview) {
            animatePickedColor(mSelectedColor);
            if (OI_COLOR_PICKER.equals(action)) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(OI_COLOR_DATA, mLastPickedColor);
                setResult(RESULT_OK, returnIntent);
                finish();
                return;
            }
            //Coloritem에 정의되어있는 형식으로 ColorItems의 gson으로 저장
            //ColorItems.saveColorItem(this, new ColorItem(mLastPickedColor));
            //ColorItem(mLastPickedColor)
            //rgb = ColorItem.makeHexString(mLastPickedColor);
            rgb = ColorItem.makeHexString(mLastPickedColor);
            //Toast.makeText(this, rgb, Toast.LENGTH_LONG).show();
            intent.putExtra("whitebalance",rgb);

            AlertDialog.Builder alertdialog = new AlertDialog.Builder(this,R.style.AlertTheme);
            // 메세지
            alertdialog.setMessage("피부색 측정으로 넘어갑니다.");
            alertdialog.setCancelable(false);
            // 확인 버튼 리스너
            alertdialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(intent);
                    finish();
                }
            });
            // 취소 버튼 리스너
            alertdialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //finish();
                }
            });
            AlertDialog alert = alertdialog.create();

            alert.show();

        }
    }
    /**
     * Animate the color being picked.
     *
     * @param pickedColor the color being picked.
     */
    protected void animatePickedColor(int pickedColor) {
        mLastPickedColor = pickedColor;
    }

    /**
     * Initialize the views used in this activity.
     * <p/>
     * Internally find the view by their ids and set the click listeners.
     */
    //전체 뷰를 초기화
    protected void initViews() {
        mIsPortrait = getResources().getBoolean(ctc.kopo.pchu.R.bool.is_portrait);
        mPreviewContainer = (FrameLayout) findViewById(ctc.kopo.pchu.R.id.activity_color_picker_preview_container);
        mPickedColorPreviewAnimated = findViewById(ctc.kopo.pchu.R.id.activity_color_picker_animated_preview);
        mPointerRing = findViewById(ctc.kopo.pchu.R.id.activity_color_picker_pointer_ring);
        tv = (ImageView)findViewById(R.id.img_view);
        tv.setImageResource(R.drawable.paper);
    }

    /**
     * Async task used to configure and start the camera preview.
     */
    private class CameraAsyncTask extends AsyncTask<Void, Void, Camera> {

        /**
         * The {@link android.view.ViewGroup.LayoutParams} used for adding the preview to its container.
         */
        protected FrameLayout.LayoutParams mPreviewParams;

        @Override
        protected Camera doInBackground(Void... params) {
            Camera camera = getCameraInstance();
            if (camera == null) {
                WhitePickerActivity.this.finish();
            } else {
                //configure Camera parameters
                Camera.Parameters cameraParameters = camera.getParameters();

                //get optimal camera preview size according to the layout used to display it
                Camera.Size bestSize = Cameras.getBestPreviewSize(
                        cameraParameters.getSupportedPreviewSizes()
                        , mPreviewContainer.getWidth()
                        , mPreviewContainer.getHeight()
                        , mIsPortrait);
                //set optimal camera preview
                cameraParameters.setPreviewSize(bestSize.width, bestSize.height);
                camera.setParameters(cameraParameters);

                //set camera orientation to match with current device orientation
                Cameras.setCameraDisplayOrientation(WhitePickerActivity.this, camera);

                //get proportional dimension for the layout used to display preview according to the preview size used
                int[] adaptedDimension = Cameras.getProportionalDimension(
                        bestSize
                        , mPreviewContainer.getWidth()
                        , mPreviewContainer.getHeight()
                        , mIsPortrait);

                //set up params for the layout used to display the preview
                mPreviewParams = new FrameLayout.LayoutParams(adaptedDimension[0], adaptedDimension[1]);
                mPreviewParams.gravity = Gravity.CENTER;
            }
            return camera;
        }

        @Override
        protected void onPostExecute(Camera camera) {
            super.onPostExecute(camera);

            // Check if the task is cancelled before trying to use the camera.
            if (!isCancelled()) {
                mCamera = camera;
                if (mCamera == null) {
                    WhitePickerActivity.this.finish();
                } else {
                    //set up camera preview
                    mCameraPreview = new CameraColorPickerPreview(WhitePickerActivity.this, mCamera);
                    mCameraPreview.setOnColorSelectedListener(WhitePickerActivity.this);
                    mCameraPreview.setOnClickListener(WhitePickerActivity.this);

                    //add camera preview
                    mPreviewContainer.addView(mCameraPreview, 0, mPreviewParams);
                }
            }
        }

        @Override
        protected void onCancelled(Camera camera) {
            super.onCancelled(camera);
            if (camera != null) {
                camera.release();
            }
        }
    }
    public void onBackPressed() {
        initColorItems();
        finish();
    }
    public void initColorItems(){
        List<ColorItem> savedColorsItems = ColorItems.getSavedColorItems(this);
        for(ColorItem colorItem : savedColorsItems ) {
            ColorItems.deleteColorItem(this, colorItem);
        }
    }
}
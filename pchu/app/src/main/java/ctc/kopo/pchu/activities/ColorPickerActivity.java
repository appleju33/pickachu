package ctc.kopo.pchu.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.data.ColorItem;
import ctc.kopo.pchu.data.ColorItems;
import ctc.kopo.pchu.utils.Cameras;
import ctc.kopo.pchu.views.CameraColorPickerPreview;


/**
 * Created by Polytech on 2017-08-01.
 */

public class ColorPickerActivity extends AppCompatActivity implements CameraColorPickerPreview.OnColorSelectedListener, View.OnClickListener {
    ImageView tv = null;
    public int count = -1;
    Intent intent2;
    private FloatingActionButton mFab;
    ImageView iv;
    /**
     * A tag used in the logs.
     */
    protected static final String TAG = ColorPickerActivity.class.getSimpleName();
    /**
     * The name of the property that animates the 'picked color'
     * Used by {@link ColorPickerActivity #m PickedColorProgressAnimator}.
     */
    protected static final String PICKED_COLOR_PROGRESS_PROPERTY_NAME = "pickedColorProgress";

    /**
     * The name of the property that animates the 'save completed'.
     * Used by {@link ColorPickerActivity #m SaveCompletedProgressAnimator}.
     */
    protected static final String SAVE_COMPLETED_PROGRESS_PROPERTY_NAME = "saveCompletedProgress";

    /**
     * A boolean for knowing the orientation of the activity.
     */
    protected boolean mIsPortrait;

    /**
     * 저장 메시지가 나올 때 걸리는 시간
     */
    protected static final long DURATION_CONFIRM_SAVE_MESSAGE = 400;

    /**
     * 저장 메시지가 유지되는 시간
     */
    protected static final long DELAY_HIDE_CONFIRM_SAVE_MESSAGE = 1400;

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
     * A simple {@link android.view.View} used for showing the picked color.
     */
    LinearLayout preview_layout;

    /**
     * A simple {@link android.view.View} used for animating the color being picked.
     */
    protected View mPickedColorPreviewAnimated;

    /**
     * An {@link android.animation.ObjectAnimator} used for animating the color being picked.
     */
    protected ObjectAnimator mPickedColorProgressAnimator;

    /**
     * The delta for the translation on the x-axis of the mPickedColorPreviewAnimated.
     */
    protected float mTranslationDeltaX;

    /**
     * The delta for the translation on the y-axis of the mPickedColorPreviewAnimated.
     */
    protected float mTranslationDeltaY;

    /**
     * A simple {@link android.view.View} used for showing the selected color.
     */
    protected View mPointerRing;

    /**
     * A float representing the progress of the "save completed" state.
     */
    protected float mSaveCompletedProgress;

    /**
     * An {@link android.animation.ObjectAnimator} used for animating the "save completed" state.
     */
    protected ObjectAnimator mSaveCompletedProgressAnimator;

    /**
     * A simple {@link android.widget.TextView} that confirms the user that the color has been saved successfully.
     */
    protected LinearLayout mConfirmSaveMessage;

    /**
     * An {@link android.view.animation.Interpolator} used for showing the mConfirmSaveMessage.
     */
    //애니메이션을 점점 빨라지거나 느려지게
    protected Interpolator mConfirmSaveMessageInterpolator;

    /**
     * A {@link java.lang.Runnable} that hide the confirm save message.
     * <p/>
     * This runnable is posted with some delayed on mConfirmSaveMessage each time a color is successfully saved.
     */
    protected Runnable mHideConfirmSaveMessage;

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
        setContentView(ctc.kopo.pchu.R.layout.activity_color_picker);

        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);

        intent2 = new Intent(getApplicationContext(),ColorPickerActivity2.class);

        preview_layout = (LinearLayout) findViewById(R.id.activity_color_picker_bottom_bar);
        iv = (ImageView) findViewById(R.id.color_msg);
        mFab = (FloatingActionButton) findViewById(R.id.activity_main_fab);

        //현재 포커스되어 있는 색을 ring에 뿌려주는 애니메이션 정의
        initPickedColorProgressAnimator();
        //저장완료 애니메이션 정의
        initSaveCompletedProgressAnimator();
        //초기 뷰들 정의
        initViews();
        //찍은 색을 아래에 찍어주는 애니메이션 위치 정의
        initTranslationDeltas();

        Intent intent = getIntent();
        if (intent != null)
            action = intent.getAction();

        String whitebalance = intent.getExtras().getString("whitebalance");
        intent2.putExtra("whitebalance",whitebalance);

        //mFab.setTranslationY(200);
        mFab.hide();
        mFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(count==0){
                    tv.setImageResource(R.drawable.pick1);
                }else if(count==1){
                    tv.setImageResource(R.drawable.pick2);
                }
                rePick();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Setup the camera asynchronously.
        mCameraAsyncTask = new CameraAsyncTask();
        mCameraAsyncTask.execute();
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
        // Remove any pending mHideConfirmSaveMessage.
        mConfirmSaveMessage.removeCallbacks(mHideConfirmSaveMessage);
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
        if(elapsedTime<=MIN_CLICK_INTERVAL){
            return;
        }
        tv = (ImageView) findViewById(R.id.img_view);
        tv.setImageResource(R.drawable.pick2);
        iv.setImageResource(R.drawable.color_saved);
        if (v == mCameraPreview) {
            animatePickedColor(mSelectedColor);
            if (OI_COLOR_PICKER.equals(action)) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(OI_COLOR_DATA, mLastPickedColor);
                //Toast.makeText(getApplicationContext(), "색 정보 : " + mLastPickedColor, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, returnIntent);
                finish();
                return;
            }
            //Coloritem에 정의되어있는 형식으로 ColorItems의 gson으로 저장
            ColorItems.saveColorItem(this, new ColorItem(mLastPickedColor));

            //Toast.makeText(getApplicationContext(), "색 정보 : " + mLastPickedColor, Toast.LENGTH_SHORT).show();
            setSaveCompleted(true);
        }
        count++;
        transani();
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
        mConfirmSaveMessage = (LinearLayout) findViewById(ctc.kopo.pchu.R.id.activity_color_picker_confirm_save_message); // --> 카메라 컬러 세이브 메시지

        //저장 버튼 클릭시 메시지 띄우는 곳
        mHideConfirmSaveMessage = new Runnable() {
            @Override
            public void run() {
                mConfirmSaveMessage.animate()
                        .translationY(-mConfirmSaveMessage.getMeasuredHeight())
                        .setDuration(DURATION_CONFIRM_SAVE_MESSAGE)
                        .start();
            }
        };
        positionConfirmSaveMessage();
        mConfirmSaveMessageInterpolator = new DecelerateInterpolator();

        //mLastPickedColor = ColorItems.getLastPickedColor(this);
        //applyPreviewColor(mLastPickedColor);
    }

    /**
     * Position mConfirmSaveMessage.
     * <p/>
     * Set the translationY of mConfirmSaveMessage to - mConfirmSaveMessage.getMeasuredHeight() so that it is correctly placed before the first animation.
     */
    //저장되었습니다 메시지 위치
    protected void positionConfirmSaveMessage() {
        ViewTreeObserver vto = mConfirmSaveMessage.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    ViewTreeObserver vto = mConfirmSaveMessage.getViewTreeObserver();
                    vto.removeOnPreDrawListener(this);
                    mConfirmSaveMessage.setTranslationY(-mConfirmSaveMessage.getMeasuredHeight());
                    return true;
                }
            });
        }
    }

    /**
     * Initialize the deltas used for the translation of the preview of the picked color.
     */
    //하단의 저장된 색으로 날아가는 애니메이션 위치 설정
    @SuppressLint("NewApi")
    protected void initTranslationDeltas() {
        ViewTreeObserver vto = mPointerRing.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver vto = mPointerRing.getViewTreeObserver();
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                        vto.removeGlobalOnLayoutListener(this);
                    } else {
                        vto.removeOnGlobalLayoutListener(this);
                    }

                    Rect pointerRingRect = new Rect();
                    Rect colorPreviewAnimatedRect = new Rect();
                    mPickedColorPreviewAnimated.getGlobalVisibleRect(pointerRingRect);
                    preview_layout.getChildAt(1).getGlobalVisibleRect(colorPreviewAnimatedRect);
                    mTranslationDeltaX = colorPreviewAnimatedRect.left - pointerRingRect.left;
                    mTranslationDeltaY = colorPreviewAnimatedRect.top - pointerRingRect.top;

                }
            });
        }
    }
    protected void transani(){
        Rect pointerRingRect = new Rect();
        Rect colorPreviewAnimatedRect = new Rect();
        mPickedColorPreviewAnimated.getGlobalVisibleRect(pointerRingRect);
        preview_layout.getChildAt(count).getGlobalVisibleRect(colorPreviewAnimatedRect);
        mTranslationDeltaX = colorPreviewAnimatedRect.left + 50 - pointerRingRect.left;
        mTranslationDeltaY = colorPreviewAnimatedRect.top - pointerRingRect.top - 20;
    }

    /**
     * Initialize the animator used for the progress of the picked color.
     */
    protected void initPickedColorProgressAnimator() {
        mPickedColorProgressAnimator = ObjectAnimator.ofFloat(this, PICKED_COLOR_PROGRESS_PROPERTY_NAME, 0f, 1f);
        mPickedColorProgressAnimator.setDuration(400);
        mPickedColorProgressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                    mPickedColorPreviewAnimated.setVisibility(View.VISIBLE);
                    mPickedColorPreviewAnimated.getBackground().setColorFilter(mSelectedColor, PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //방금 찍은 색정보를 가져와서 색칠 함
                ColorItems.saveLastPickedColor(ColorPickerActivity.this, mLastPickedColor);
                applyPreviewColor(mLastPickedColor);
                mPickedColorPreviewAnimated.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                    mPickedColorPreviewAnimated.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * Initialize the animator used for the progress of the "save completed" state.
     */
    protected void initSaveCompletedProgressAnimator() {
        mSaveCompletedProgressAnimator = ObjectAnimator.ofFloat(this, SAVE_COMPLETED_PROGRESS_PROPERTY_NAME, 1f, 0f);
    }

    /**
     * Apply the preview color.
     * <p/>
     * Display the preview color and its human representation.
     *
     * @param previewColor the preview color to apply.
     */
    protected void applyPreviewColor(int previewColor) {
        setSaveCompleted(false);
//        float positionOffset = (float) 0.05;
//        positionOffset *= 1;
        //mFab.setTranslationY((((LinearLayout) mFab.getParent()).getHeight() - mFab.getTop()) * positionOffset);
        mFab.show();
        animateFab(mFab,300);
        // picked된 칼라 추출하여 아래에 찍어줌
        if(count==0) {
            intent2.putExtra("inhand",mLastPickedColor);
            preview_layout.getChildAt(count).getBackground().setColorFilter(previewColor, PorterDuff.Mode.SRC_ATOP);
        }else if (count==1){
            intent2.putExtra("outhand",mLastPickedColor);
            preview_layout.getChildAt(count).getBackground().setColorFilter(previewColor, PorterDuff.Mode.SRC_ATOP);

            AlertDialog.Builder alertdialog = new AlertDialog.Builder(this,R.style.AlertTheme);
            // 메세지
            alertdialog.setMessage("얼굴 촬영으로 넘어갑니다.");
            alertdialog.setCancelable(false);
            // 확인 버튼 리스너
            alertdialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(intent2);
                    finish();
                }
            });
            // 취소 버튼 리스너
            alertdialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    rePick();
                    // 찍은 colorItems 초기화하고 메인으로
//                    initColorItems();
//                    finish();
                    //Toast.makeText(getApplicationContext(), "'취소'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
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
        if (mPickedColorProgressAnimator.isRunning()) {
            mPickedColorProgressAnimator.cancel();
        }
        mPickedColorProgressAnimator.start();
    }

    /**
     * Set the "save completed" state.
     * <p/>
     * True means that the save is completed. The preview color should not be saved again.
     *
     * @param isSaveCompleted the "save completed" state.
     */
    protected void setSaveCompleted(boolean isSaveCompleted) {
        //mSaveButton.setEnabled(!isSaveCompleted);
        mSaveCompletedProgressAnimator.cancel();
        mSaveCompletedProgressAnimator.setFloatValues(mSaveCompletedProgress, isSaveCompleted ? 0f : 1f);
        mSaveCompletedProgressAnimator.start();
        if (isSaveCompleted) {
            mConfirmSaveMessage.setVisibility(View.VISIBLE);
            mConfirmSaveMessage.animate().translationY(0).setDuration(DURATION_CONFIRM_SAVE_MESSAGE)
                    .setInterpolator(mConfirmSaveMessageInterpolator).start();
            mConfirmSaveMessage.removeCallbacks(mHideConfirmSaveMessage);
            mConfirmSaveMessage.postDelayed(mHideConfirmSaveMessage, DELAY_HIDE_CONFIRM_SAVE_MESSAGE);
        }
    }
    protected void setPickedColorProgress(float progress) { // 애니메이션 각도 소스
        final float fastOppositeProgress = (float) Math.pow(progress, 0.3f);
        final float translationX = mTranslationDeltaX * progress;
        final float translationY = (float) (mTranslationDeltaY * Math.pow(progress, 2f));

        mPickedColorPreviewAnimated.setTranslationX(translationX);
        mPickedColorPreviewAnimated.setTranslationY(translationY);
        mPickedColorPreviewAnimated.setScaleX(fastOppositeProgress);
        mPickedColorPreviewAnimated.setScaleY(fastOppositeProgress);
    }

    /*protected void setSaveCompleted(boolean isSaveCompleted) {
            mConfirmSaveMessage.animate().translationY(0).setDuration(DURATION_CONFIRM_SAVE_MESSAGE)
                    .setInterpolator(mConfirmSaveMessageInterpolator).start();
            mConfirmSaveMessage.removeCallbacks(mHideConfirmSaveMessage);
            mConfirmSaveMessage.postDelayed(mHideConfirmSaveMessage, DELAY_HIDE_CONFIRM_SAVE_MESSAGE);
    }*/

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
                ColorPickerActivity.this.finish();
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
                Cameras.setCameraDisplayOrientation(ColorPickerActivity.this, camera);

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
                    ColorPickerActivity.this.finish();
                } else {
                    //set up camera preview
                    mCameraPreview = new CameraColorPickerPreview(ColorPickerActivity.this, mCamera);
                    mCameraPreview.setOnColorSelectedListener(ColorPickerActivity.this);
                    mCameraPreview.setOnClickListener(ColorPickerActivity.this);

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
    public void rePick(){
        iv.setImageResource(R.drawable.color_cancel);
        preview_layout.getChildAt(count).getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        count--;
        List<ColorItem> savedColorsItems = ColorItems.getSavedColorItems(getApplicationContext());
        for(ColorItem colorItem : savedColorsItems) {
            ColorItems.deleteColorItem(getApplicationContext(), colorItem); // 메인 Activity가 onCreate()되면 자동으로 initColorItems()를 호출하고 저장된 칼라값을 모두 삭제한다.
            break;
        }
        mConfirmSaveMessage.setVisibility(View.VISIBLE);
        mConfirmSaveMessage.animate().translationY(0).setDuration(DURATION_CONFIRM_SAVE_MESSAGE)
                .setInterpolator(mConfirmSaveMessageInterpolator).start();
        mConfirmSaveMessage.removeCallbacks(mHideConfirmSaveMessage);
        mConfirmSaveMessage.postDelayed(mHideConfirmSaveMessage, DELAY_HIDE_CONFIRM_SAVE_MESSAGE);
        if(count == -1) {
            //mFab.setTranslationY(200);
            mFab.hide();
        }
    }
    private void animateFab(final FloatingActionButton fab, int delay) {
        fab.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Play a subtle animation
                final long duration = 300;

                final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(fab, View.SCALE_X, 1f, 1.2f, 1f);
                scaleXAnimator.setDuration(duration);
                scaleXAnimator.setRepeatCount(1);

                final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(fab, View.SCALE_Y, 1f, 1.2f, 1f);
                scaleYAnimator.setDuration(duration);
                scaleYAnimator.setRepeatCount(1);

                scaleXAnimator.start();
                scaleYAnimator.start();

                final AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(scaleXAnimator).with(scaleYAnimator);
                animatorSet.start();
            }
        }, delay);
    }
}
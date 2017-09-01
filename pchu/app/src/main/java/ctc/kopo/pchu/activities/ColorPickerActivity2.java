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
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.data.ColorItem;
import ctc.kopo.pchu.data.ColorItems;
import ctc.kopo.pchu.utils.Cameras;
import ctc.kopo.pchu.views.CameraColorPickerPreview;

/**
 * Created by Polytech on 2017-08-01.
 */

public class ColorPickerActivity2 extends AppCompatActivity implements CameraColorPickerPreview.OnColorSelectedListener, View.OnClickListener {
    ImageView tv = null;
    public int count = 1;
    private FloatingActionButton mFab;
    ImageView iv;
    Intent intentc;
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

    // 전면카메라 시작 메소드
    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
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
    protected View mPickedColorPreview1;
    protected View mPickedColorPreview2;
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

    /**
     * <a href="http://www.openintents.org/action/org-openintents-action-pick-color/">
     * see openintents.org</a>
     */
    public static final String OI_COLOR_PICKER = "org.openintents.action.PICK_COLOR";
    public static final String OI_COLOR_DATA = "org.openintents.extra.COLOR";

    // 중복 클릭 방지 시간 설정
    private static final long MIN_CLICK_INTERVAL=500;

    private long mLastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 생성시
        super.onCreate(savedInstanceState);
        setContentView(ctc.kopo.pchu.R.layout.activity_color_picker);

        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        tv = (ImageView) findViewById(R.id.img_view);
        tv.setImageResource(R.drawable.pick3);

        preview_layout = (LinearLayout) findViewById(R.id.activity_color_picker_bottom_bar);
        iv = (ImageView) findViewById(R.id.color_msg);
        mFab = (FloatingActionButton) findViewById(R.id.activity_main_fab);

        initPickedColorProgressAnimator();
        initSaveCompletedProgressAnimator();
        initViews();
        initTranslationDeltas();

        Intent intent = getIntent();
        if (intent != null)
            action = intent.getAction();

        intentc = new Intent(getApplicationContext(),  CreationActivity.class);

        String whitebalance = intent.getExtras().getString("whitebalance");
        intentc.putExtra("whitebalance",whitebalance);

        //mFab.setTranslationY(200);
        mFab.hide();
        mFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(count==2){
                    tv.setImageResource(R.drawable.pick3);
                }else if(count==3){
                    tv.setImageResource(R.drawable.pick4);
                }else if(count==4){
                    tv.setImageResource(R.drawable.pick5);
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
    // 카메라뷰 터치시 색을 저장하는 곳
    public void onClick(View v) {
        long currentClickTime= SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;
        if(elapsedTime<=MIN_CLICK_INTERVAL){
            return;
        }
        tv = (ImageView) findViewById(R.id.img_view);
        if(count==1) {
            tv.setImageResource(R.drawable.pick4);
        }else if(count==2){
            tv.setImageResource(R.drawable.pick5);
        }
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
        mPickedColorPreview1 = findViewById(ctc.kopo.pchu.R.id.activity_color_picker_color_preview);
        mPickedColorPreview2 = findViewById(ctc.kopo.pchu.R.id.activity_color_picker_color_preview2);
        mPickedColorPreviewAnimated = findViewById(ctc.kopo.pchu.R.id.activity_color_picker_animated_preview);
        mPointerRing = findViewById(ctc.kopo.pchu.R.id.activity_color_picker_pointer_ring);
        mConfirmSaveMessage = (LinearLayout) findViewById(ctc.kopo.pchu.R.id.activity_color_picker_confirm_save_message); // --> 카메라 컬러 세이브 메시지

        Intent iniintent = getIntent();
        int inhand = iniintent.getExtras().getInt("inhand");
        int outhand = iniintent.getExtras().getInt("outhand");
        mPickedColorPreview1.getBackground().setColorFilter(inhand, PorterDuff.Mode.SRC_ATOP);
        mPickedColorPreview2.getBackground().setColorFilter(outhand, PorterDuff.Mode.SRC_ATOP);

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
    //하단의 저장된 색으로 날아가는 애니메이션 설정
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

                    final Rect pointerRingRect = new Rect();
                    final Rect colorPreviewAnimatedRect = new Rect();
                    mPickedColorPreviewAnimated.getGlobalVisibleRect(pointerRingRect);
                    preview_layout.getChildAt(2).getGlobalVisibleRect(colorPreviewAnimatedRect);
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
                ColorItems.saveLastPickedColor(ColorPickerActivity2.this, mLastPickedColor);
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
        float positionOffset = (float) 0.05;
        positionOffset *= 1;
        //mFab.setTranslationY((((LinearLayout) mFab.getParent()).getHeight() - mFab.getTop()) * positionOffset);
        mFab.show();
        animateFab(mFab,300);
        preview_layout.getChildAt(count).getBackground().setColorFilter(previewColor, PorterDuff.Mode.SRC_ATOP);
        // picked된 칼라 추출하여 아래에 찍어줌
        if (count==4){
            AlertDialog.Builder editdialog = new AlertDialog.Builder(this,R.style.AlertTheme);


            editdialog.setCancelable(false);
            editdialog.setMessage("피부톤 이름을 정해주세요.");
            final EditText name = new EditText(this);
            name.setTextColor(Color.parseColor("#000000"));
            name.setSingleLine(true);
            name.setGravity(0x11);
            name.setBackgroundResource(R.drawable.underline);

            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(4);
            name.setFilters(FilterArray);

            editdialog.setView(name);

            editdialog.setPositiveButton(getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String skinName = name.getText().toString();
                            intentc.putExtra("skinname",skinName);
                            startActivity(intentc);
                            finish();
                        }
                    });
            editdialog.setNegativeButton(getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            rePick();
//                            initColorItems();
//                            finish();
                        }
                    });
            AlertDialog alert = editdialog.create();
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
                ColorPickerActivity2.this.finish();
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
                Cameras.setCameraDisplayOrientation(ColorPickerActivity2.this, camera);

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
                    ColorPickerActivity2.this.finish();
                } else {
                    //set up camera preview
                    mCameraPreview = new CameraColorPickerPreview(ColorPickerActivity2.this, mCamera);
                    mCameraPreview.setOnColorSelectedListener(ColorPickerActivity2.this);
                    mCameraPreview.setOnClickListener(ColorPickerActivity2.this);

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
        if(count == 1) {
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
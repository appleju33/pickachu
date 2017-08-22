package ctc.kopo.pchu.views;

/**
 * Created by Polytech on 2017-08-01.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import ctc.kopo.pchu.data.ColorItem;
import ctc.kopo.pchu.data.Palette;

/**
 * A simple {@link View} that can display a {@link Palette}.
 * <p/>
 * All {@link ColorItem}s of the {@link Palette} are represented horizontally with a rectangle
 * of their respective color.
 */
public class PaletteView extends View {

    /**
     * The {@link Palette} being displayed.
     */
    private Palette mPalette;

    /**
     * A {@link RectF} used for drawing the {@link ColorItem}s.
     */
    private RectF mRect;

    /**
     * A {@link RectF} for computing the bounds of the drawing area.
     */
    private RectF mBounds;

    /**
     * A {@link Paint} for drawing the {@link ColorItem}s.
     */
    private Paint myPaint;
    private Paint mColorPaint;

    public PaletteView(Context context) {
        super(context);
        init();
    }

    public PaletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaletteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaletteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * Set the {@link Palette} that will be displayed.
     *
     * @param palette the {@link Palette} to display.
     */
    public void setPalette(Palette palette) {
        mPalette = palette;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int left = getPaddingLeft();
        final int top = getPaddingTop();
        final int right = getMeasuredWidth() - getPaddingRight();
        final int bottom = getMeasuredHeight() - getPaddingBottom();

        // Compute the drawing area.
        // The drawing area corresponds to the size of the palette view minus its padding.
        mBounds.set(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        String testcolor;
        int first;
        int secStart;
        int secEnd;
        int ThirdStart;
        int ThirdEnd;
        if (mPalette == null) {
            // If there is no palette to draw, simply draw a white rect.
            //mColorPaint.setColor(Color.WHITE);
            //canvas.drawRect(mBounds, mColorPaint);
        } else {
            // Draw a rec for each color items in the palette.
            final List<ColorItem> colorItems = mPalette.getColors();
            final float colorWidth = mBounds.width() / colorItems.size();
            //mRect.set(mBounds.left, mBounds.top, mBounds.left + colorWidth, mBounds.bottom);
            mRect.set(mBounds.left, mBounds.top, mBounds.right, mBounds.bottom);

            for (ColorItem colorItem : colorItems) {
//                mColorPaint.setColor(colorItem.getColor());
//                canvas.drawRect(mRect, mColorPaint);
//                mRect.left = mRect.right;
//                mRect.right += colorWidth;
                testcolor = colorItem.getRgbString();
                first = testcolor.indexOf(",");
                secStart  = first+1;
                secEnd = testcolor.indexOf(",",secStart);
                ThirdStart  = secEnd+1;
                ThirdEnd = testcolor.indexOf(")");
                red += Integer.parseInt(colorItem.getRgbString().substring(4, first));
                green += Integer.parseInt(colorItem.getRgbString().substring(secStart,secEnd));
                blue += Integer.parseInt(colorItem.getRgbString().substring(ThirdStart,ThirdEnd));

                count++;
            }
            red /= count;
            green /= count;
            blue /= count;
            String hexColor = rgbToHex(red, green, blue);
            //Toast.makeText(getContext(),hexColor, Toast.LENGTH_LONG).show();
            myPaint.setColor(Color.parseColor(hexColor));
            canvas.drawRect(mRect, myPaint);
        }
    }
    public static String rgbToHex(int r, int g, int b)
    {
        int Hex = r << 16 ^ g << 8 ^ b;
        return "#" + lpad(Integer.toHexString(Hex));
    }
    public static String lpad(String lpadstr){
        for(int i=lpadstr.length();i<6;i++){
            lpadstr="0"+lpadstr;
        }
        return lpadstr;
    }

    /**
     * Initialize the palette view.
     * <p/>
     * Must be called once in each constructor.
     */
    private void init() {
        mBounds = new RectF();
        mRect = new RectF();

        myPaint = new Paint();
        myPaint.setStyle(Paint.Style.FILL);
//        mColorPaint = new Paint();
//        mColorPaint.setStyle(Paint.Style.FILL);
    }
}
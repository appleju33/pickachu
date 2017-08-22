package ctc.kopo.pchu.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * {@link RecPaletteView} which will always be rendered as square.
 */
public class RecPaletteView extends PaletteView {
    /**
     * {@link RecPaletteView} which will always be rendered as square.
     *
     * @param context holding context.
     */
    public RecPaletteView(Context context) {
        super(context);
    }

    /**
     * {@link RecPaletteView} which will always be rendered as square.
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public RecPaletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@link RecPaletteView} which will always be rendered as square.
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public RecPaletteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        int measureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(measureSpec, measureSpec);
    }
}

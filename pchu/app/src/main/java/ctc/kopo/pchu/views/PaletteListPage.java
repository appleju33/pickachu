package ctc.kopo.pchu.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.activities.AnalysisActivity;
import ctc.kopo.pchu.activities.PopupActivity;
import ctc.kopo.pchu.data.ColorItem;
import ctc.kopo.pchu.data.Palette;
import ctc.kopo.pchu.data.Palettes;
import ctc.kopo.pchu.wrappers.PaletteListWrapper;

import static ctc.kopo.pchu.views.PaletteView.rgbToHex;

/**
 * Created by Polytech on 2017-08-01.
 */

public class PaletteListPage extends FrameLayout implements PaletteListWrapper.PaletteListWrapperListener {

    /**
     * An {@link Palettes.OnPaletteChangeListener} that will be notified when the palettes of the user change.
     */
    private Palettes.OnPaletteChangeListener mOnPaletteChangeListener;

    /**
     * Listener used to catch internal event in order to avoid {@link PaletteListPage} exposing
     * this onClick callback.
     */
    //private View.OnClickListener internalListener;

    /**
     * Listener used to catch view events.
     */
    private Listener listener;

    public PaletteListPage(Context context) {
        super(context);
        init(context);
    }

    public PaletteListPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PaletteListPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaletteListPage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Listener used to catch view events.
     *
     * @param listener listener used to catch view events.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //showToast("on Move");
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            // 삭제되는 아이템의 포지션을 가져온다
            final int position = viewHolder.getAdapterPosition();
            final List<Palette> savedPalette = Palettes.getSavedColorPalettes(getContext());
            final long delPalId = savedPalette.get(position).getId();
            for(Palette pal : savedPalette){
                if(pal.getId()==delPalId){
                    Palettes.deleteColorPalette(getContext(), pal);
                }
            }
        }
        private Paint p = new Paint();
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            Bitmap icon = null;
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;

                if(dX > 0){
                } else {
                    p.setColor(Color.parseColor("#BFBFBF"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background,p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,p);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    /**
     * Initialize this palette list page.
     * <p/>
     * Must be called once in each constructor.
     *
     * @param context the {@link Context} from the constructor.
     */
    private void init(final Context context) {
        final View view = View.inflate(context, R.layout.view_palette_list_page, this);
        //initInternalListener();

        //final View emptyView = view.findViewById(R.id.view_palette_list_empty_view);
        //final FrameLayout emptyView = (FrameLayout) findViewById(R.id.view_palette_list_empty_view);
        //emptyView.setOnClickListener(internalListener);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.view_palette_list_page_list_view);
        final FlavorPaletteListWrapper wrapper = FlavorPaletteListWrapper.create(recyclerView, this);
        final PaletteListWrapper.Adapter adapter = wrapper.installRecyclerView();

        final ImageButton menualBtn = (ImageButton) findViewById(R.id.menual);

        menualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PopupActivity.class);
                context.startActivity(intent);
            }
        });

        // setup swipe to remove item
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        final FrameLayout wink = (FrameLayout) findViewById(R.id.winkimg);
        final int[] thisWink = {0};

        Handler handler;
        handler = new Handler(){
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                int[] winks = {R.drawable.main1,R.drawable.main2,R.drawable.main3,R.drawable.main4,R.drawable.main5,R.drawable.main6};
                final ImageView eyeChange = (ImageView) findViewById(R.id.eyes);

                eyeChange.setImageResource(winks[thisWink[0]]);
                thisWink[0] = thisWink[0] +1;
                if(thisWink[0]==6){
                    thisWink[0]=0;
                }
                this.sendEmptyMessageDelayed(0, 1000);
            }
        };
        handler.sendEmptyMessage(0);

        /*
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        final ViewPagerAdapter menualadapter = new ViewPagerAdapter(getContext());
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

        //ViewPager에 Adapter 설정
        pager.setAdapter(menualadapter);
        */

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
//                pager_layout.setVisibility(adapter.getItemCount() == 0 ? VISIBLE : GONE);
//                pager.setVisibility(adapter.getItemCount() == 0 ? VISIBLE : GONE);
                menualBtn.setVisibility(adapter.getItemCount() == 0 ? VISIBLE : GONE);
            }
        });
        adapter.setPalettes(Palettes.getSavedColorPalettes(context));
                mOnPaletteChangeListener = new Palettes.OnPaletteChangeListener() {
                    @Override
                    public void onColorPaletteChanged(List<Palette> palettes) {
                adapter.setPalettes(palettes);
                //Log.d(TAG, "제대로 나오는거");
            }
        };
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Register the OnPaletteChangeListener to be notified when the user create a new palette
        // Or when a user delete a palette.
        Palettes.registerListener(getContext(), mOnPaletteChangeListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        // Unregister the OnPaletteChangeListener.
        // We do not need to be notified anymore.
        Palettes.unregisterListener(getContext(), mOnPaletteChangeListener);
        super.onDetachedFromWindow();
    }

    @Override
    // 리스트의 팔레트 클릭 시에 Recomm1Activity로 색깔을 넘긴다.
    public void onPaletteClicked(@NonNull Palette palette, @NonNull View palettePreview) {
        //PaletteDetailActivity.startWithColorPalette(getContext(), palette, palettePreview);
        String myColor;
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
        final List<ColorItem> colorItems = palette.getColors();
        for (ColorItem colorItem : colorItems) {
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

        //Condition1Activity.startWithColorPalette(getContext(), hexColor);
        AnalysisActivity.startWithColorPalette(getContext(), hexColor);
        //Toast.makeText(getContext(),hexColor, Toast.LENGTH_LONG).show();

    }
    /**
     * Listener used to catch view events.
     */
    public interface Listener {
        /**
         * Called when the user requested emphasis on the palette creation action.
         * <p/>
         * Currently, when the user touch the empty view.
         */
        //void onEmphasisOnPaletteCreationRequested();
    }
/*

    */
/**
     * Initialize internal listener.
     *//*

    private void initInternalListener() {
        internalListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    //listener.onEmphasisOnPaletteCreationRequested();
                }
            }
        };
    }
*/

}

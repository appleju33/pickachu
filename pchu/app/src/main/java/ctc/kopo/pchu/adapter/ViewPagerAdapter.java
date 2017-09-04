package ctc.kopo.pchu.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ctc.kopo.pchu.R;

/**
 * Created by Polytech on 2017-08-01.
 */

public class ViewPagerAdapter extends PagerAdapter{
    Context context;
    int[] imageId = {R.drawable.menual1, R.drawable.menual2, R.drawable.menual3, R.drawable.menual4, R.drawable.menual5, R.drawable.menual6, R.drawable.menual7, R.drawable.menual8};

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.main_viewpagerimg, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.pagerimg);
        imageView.setImageResource(imageId[position]);
        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub

        return view == ((View)object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}

package ctc.kopo.pchu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.views.ResultItem;

/**
 * Created by Polytech on 2017-08-01.
 */

public class ResultListViewAdapter extends BaseAdapter {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ResultItem> listViewItemList = new ArrayList<ResultItem>() ;

    // ListViewAdapter의 생성자
    public ResultListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "resultitem" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.resultitem, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.itemimg) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.brand) ;
        TextView prodTextView = (TextView) convertView.findViewById(R.id.product) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.detail) ;
        TextView priceTextView = (TextView) convertView.findViewById(R.id.price) ;
        View barView = (View) convertView.findViewById(R.id.hexbar) ;
        View cirView = (View) convertView.findViewById(R.id.hexcircle) ;


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ResultItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageBitmap(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        prodTextView.setText(listViewItem.getProduct());
        descTextView.setText(listViewItem.getDesc());
        priceTextView.setText(listViewItem.getPrice());
        barView.setBackgroundColor(Color.parseColor(listViewItem.getBar()));
        cirView.setBackgroundColor(Color.parseColor(listViewItem.getBar()));

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Bitmap icon, String title,String product, String desc, String price,String bar) {
        ResultItem item = new ResultItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setProduct(product);
        item.setDesc(desc);
        item.setPrice(price);
        item.setBar(bar);

        listViewItemList.add(item);
    }
}

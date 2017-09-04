package ctc.kopo.pchu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ctc.kopo.pchu.R;
import ctc.kopo.pchu.views.DialogItem;

/**
 * Created by Polytech on 2017-09-04.
 */

public class WhiteDialogAdapter extends BaseAdapter {
    private ArrayList<DialogItem> imgViewItemList = new ArrayList<DialogItem>();

    public WhiteDialogAdapter(){
    }

    @Override
    public int getCount() {
        return imgViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "resultitem" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.whitedialog, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.dia_img) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.dia_txt) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DialogItem listViewItem = imgViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageResource(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());

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
        return imgViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(int icon, String title) {
        DialogItem item = new DialogItem();

        item.setIcon(icon);
        item.setTitle(title);

        imgViewItemList.add(item);
    }
}

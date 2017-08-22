package ctc.kopo.pchu.views;

import android.graphics.drawable.Drawable;

/**
 * Created by Polytech on 2017-08-01.
 */

public class ResultItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private String priceStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setPrice(String price) {
        priceStr = price ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public String getPrice() {
        return this.priceStr ;
    }
}

package ctc.kopo.pchu.views;

import android.graphics.Bitmap;

/**
 * Created by Polytech on 2017-08-01.
 */

public class ResultItem {
    private Bitmap iconBitmap ;
    private String titleStr ;
    private String productStr ;
    private String descStr ;
    private String priceStr ;
    private String hexbarStr;

    public void setIcon(Bitmap icon) {
        iconBitmap = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setProduct(String product) {
        productStr = product ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setPrice(String price) {
        priceStr = price ;
    }
    public void setBar(String bar) {
        hexbarStr = bar ;
    }

    public Bitmap getIcon() {
        return this.iconBitmap ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getProduct() {
        return this.productStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public String getPrice() {
        return this.priceStr ;
    }
    public String getBar() {
        return this.hexbarStr ;
    }
}

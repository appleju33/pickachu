package ctc.kopo.pchu.views;

import android.graphics.drawable.Drawable;

/**
 * Created by koposw22 on 2017-08-05.
 */

public class JsonItem {

    private String colorR;
    private String colorG;
    private String colorB;
    private String brand;
    private String itemname;
    private String colorname;
    private String price;
    private String img;
    private String hex;
    private double distance;

    public void setColorR(String red) {
        colorR = red;
    }
    public void setColorG(String green) {
        colorG = green ;
    }
    public void setColorB(String blue) {
        colorB = blue ;
    }
    public void setBrand(String brandstr) {
        brand = brandstr ;
    }
    public void setItemname(String item) {
        itemname = item ;
    }
    public void setColorname(String Cname) {
        colorname = Cname ;
    }
    public void setPrice(String priceStr) {
        price = priceStr ;
    }
    public void setImg(String imgStr) {
        img = imgStr ;
    }
    public void setHex(String hexStr) {
        hex = hexStr ;
    }
    public void setDistance(double Distance) {
        distance = Distance ;
    }

    public String getColorR() {
        return this.colorR ;
    }
    public String getColorG() {
        return this.colorG ;
    }

    public String getColorB() {
        return this.colorB ;
    }

    public String getBrand() {
        return this.brand ;
    }

    public String getItemname() {
        return this.itemname ;
    }

    public String getColorname() {
        return this.colorname ;
    }

    public String getPrice() {
        return this.price ;
    }
    public String getImg() {
        return this.img ;
    }
    public String getHex() {
        return this.hex ;
    }
    public double getDistance() {
        return this.distance ;
    }


}

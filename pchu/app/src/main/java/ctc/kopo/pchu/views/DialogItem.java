package ctc.kopo.pchu.views;

/**
 * Created by Polytech on 2017-09-04.
 */

public class DialogItem {
    private int lampIcon ;
    private String titleStr ;

    public void setIcon(int icon) {
        lampIcon = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }

    public int getIcon() {
        return this.lampIcon ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
}

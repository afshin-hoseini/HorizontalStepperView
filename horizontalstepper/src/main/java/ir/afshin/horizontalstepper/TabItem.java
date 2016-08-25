package ir.afshin.horizontalstepper;

/**
 * Created by afshinhoseini on 8/25/16.
 */
public class TabItem {

    public int iconDrawable = 0;
    public String label = "";
    TabItemView tabItemView = null;

// ____________________________________________________________________

    public TabItem(String label, int iconDrawable) {

        this.label = label;
        this.iconDrawable = iconDrawable;
    }
// ____________________________________________________________________
}

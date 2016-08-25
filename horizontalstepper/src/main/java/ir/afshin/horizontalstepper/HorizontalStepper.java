package ir.afshin.horizontalstepper;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by afshinhoseini on 8/25/16.
 */
public class HorizontalStepper extends HorizontalScrollView {

    private LinearLayout stepTabsContainer = null;
    private ArrayList<TabItem> tabItems = new ArrayList<>();
    private TabItem finishingPart = null;
    private boolean didInitAdjusted = false;
    private boolean didLayouted = false;
    private final int MIN_STEP_WIDTH_DP = 55;
    private int minStepWidth_px = 0;
    private int currentSelectedStepIndex = -1;
    private TabItem currentTabItem = null;
    private StepSelectionChanged stepSelectionChangedListener = null;

// ____________________________________________________________________

    public HorizontalStepper(Context context) {
        super(context);
        init();
    }

    public HorizontalStepper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalStepper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public HorizontalStepper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

// ____________________________________________________________________

    private void init() {

        stepTabsContainer = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        stepTabsContainer.setLayoutParams(layoutParams);
        stepTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        stepTabsContainer.setGravity(Gravity.CENTER_VERTICAL);
        addView(stepTabsContainer);

        minStepWidth_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MIN_STEP_WIDTH_DP, getResources().getDisplayMetrics());

    }

// ____________________________________________________________________

    public void setStepSelectionChangedListener(StepSelectionChanged listener) {

        this.stepSelectionChangedListener = listener;
    }

// ____________________________________________________________________

    public TabItem nextStep() {

        int fillSize = (finishingPart == null ? 1 : tabItems.contains(finishingPart) ? 2 : 1);
        currentSelectedStepIndex = tabItems.indexOf(currentTabItem);

        if(tabItems.size() >= fillSize && currentSelectedStepIndex < tabItems.size()-fillSize) {

            currentSelectedStepIndex++;
            currentTabItem = tabItems.get(currentSelectedStepIndex);
            currentTabItem.tabItemView.select();
        }

        if(stepSelectionChangedListener != null)
            stepSelectionChangedListener.selectedStep(currentTabItem);

        return currentTabItem;
    }

// ____________________________________________________________________

    public TabItem previousStep() {

        int fillSize = (finishingPart == null ? 1 : tabItems.contains(finishingPart) ? 2 : 1);
        currentSelectedStepIndex = tabItems.indexOf(currentTabItem);

        if(tabItems.size() >= fillSize && currentSelectedStepIndex <= tabItems.size()-fillSize && currentSelectedStepIndex > 0) {


            tabItems.get(currentSelectedStepIndex).tabItemView.deselect();
            currentSelectedStepIndex--;

            if(currentSelectedStepIndex >= 0)
                currentTabItem = tabItems.get(currentSelectedStepIndex);
        }

        if(stepSelectionChangedListener != null)
            stepSelectionChangedListener.selectedStep(currentTabItem);

        return currentTabItem;
    }

// ____________________________________________________________________

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        didLayouted = true;
        if(!didInitAdjusted) {
            didInitAdjusted = true;
            adjustStepTabs();
        }
    }


// ____________________________________________________________________

    public void addTab(TabItem tabItem) {

        tabItem.tabItemView = new TabItemView(getContext(), stepTabsContainer, false);
        tabItem.tabItemView.resize(minStepWidth_px);
        tabItem.tabItemView.setIcon(tabItem.iconDrawable);
        tabItems.add(tabItem);
        stepTabsContainer.addView(tabItem.tabItemView.getTabView());

        adjustStepTabs();
    }

// ____________________________________________________________________

    private void adjustStepTabs() {

        if(!didLayouted || tabItems == null)
            return;

        if(finishingPart != null) {
            tabItems.remove(finishingPart);
            stepTabsContainer.removeView(finishingPart.tabItemView.getTabView());
        }

        int wholeWidth = getWidth();
        int remainingWidth = wholeWidth - (minStepWidth_px * tabItems.size());

        if(remainingWidth > 0) {

            if(finishingPart == null) {
                finishingPart = new TabItem("", 0);
                finishingPart.tabItemView = new TabItemView(getContext(), stepTabsContainer, true);
            }

            if(tabItems.size() == 0)
                finishingPart.tabItemView.resize(remainingWidth);
            else {


                int iconHolderWidth = (int) getContext().getResources().getDimension(R.dimen.iconHolderWidthHeight);//TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 54, getResources().getDisplayMetrics());
                int stepBarWidth = minStepWidth_px - iconHolderWidth;
                remainingWidth += stepBarWidth;

                finishingPart.tabItemView.resize(remainingWidth / 2);
                tabItems.get(0).tabItemView.resize(iconHolderWidth + remainingWidth / 2);
            }

            tabItems.add(finishingPart);

            stepTabsContainer.addView(finishingPart.tabItemView.getTabView());

            requestLayout();
        }

        if(currentTabItem == null) {


            int fillSize = (finishingPart == null ? 1 : tabItems.contains(finishingPart) ? 2 : 1);
            if(tabItems.size() >= fillSize) {

                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        currentTabItem = tabItems.get(0);
                        currentTabItem.tabItemView.select();

                        if(stepSelectionChangedListener != null)
                            stepSelectionChangedListener.selectedStep(currentTabItem);
                    }
                },200);

            }

        }

    }

// ____________________________________________________________________

    public interface StepSelectionChanged {

        void selectedStep(TabItem tabItem);
    }

// ____________________________________________________________________
}

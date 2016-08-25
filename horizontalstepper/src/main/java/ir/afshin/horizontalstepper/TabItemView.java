package ir.afshin.horizontalstepper;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by afshinhoseini on 8/24/16.
 */
class TabItemView {

    private View tabView = null;
    private Context context = null;
    private int width = 200;
    private ImageView img_icon = null;
    private View bar = null;
    private boolean isFinishingPart = false;
    private StepTabBkg imgIconBkg = null;
    private StepTabBkg barBkg = null;
    boolean isSelected = false;


// ____________________________________________________________________

    public TabItemView(Context context, ViewGroup parent, boolean isFinishingPart) {

        this.context = context;
        this.isFinishingPart = isFinishingPart;
        createView(parent);
    }

// ____________________________________________________________________

    public void resize(int width) {

        this.width = width;
        tabView.getLayoutParams().width = width;
    }

// ____________________________________________________________________

    private void createView(ViewGroup parent) {

        this.tabView = LayoutInflater.from(context).inflate(R.layout.tab_item_view, null, false);
        tabView.setOnClickListener(onTabClicked);

        tabView.measure(0, 0);

        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(width, tabView.getMeasuredHeight());
        tabView.setLayoutParams(layoutParams);

        img_icon = (ImageView) tabView.findViewById(R.id.imgIcon);
        bar = tabView.findViewById(R.id.bar);




        //Setting icon image drawable
        Drawable imgIconSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.tab_icon_selected_bkg);
        imgIconSelectedDrawable.mutate();
        imgIconSelectedDrawable.setColorFilter(ContextCompat.getColor(context, R.color.selectedColor), PorterDuff.Mode.SRC_OVER);

        Drawable imgIconUnselectedDrawable = ContextCompat.getDrawable(context, R.drawable.tab_icon_selected_bkg);
        imgIconUnselectedDrawable.mutate();
        imgIconUnselectedDrawable.setColorFilter(ContextCompat.getColor(context, R.color.unselectedColor), PorterDuff.Mode.SRC_OVER);

        imgIconBkg = new StepTabBkg(context, imgIconSelectedDrawable, imgIconUnselectedDrawable);
        img_icon.setBackground(imgIconBkg);

        //Setting bar view drawable
        Drawable barSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.tab_bar_selected_bkg);
        barSelectedDrawable.mutate();
        barSelectedDrawable.setColorFilter(ContextCompat.getColor(context, R.color.selectedColor), PorterDuff.Mode.SRC_OVER);

        Drawable barUnselectedDrawable = ContextCompat.getDrawable(context, R.drawable.tab_bar_selected_bkg);
        barUnselectedDrawable.mutate();
        barUnselectedDrawable.setColorFilter(ContextCompat.getColor(context, R.color.unselectedColor), PorterDuff.Mode.SRC_OVER);

        barBkg = new StepTabBkg(context, barSelectedDrawable, barUnselectedDrawable);
        bar.setBackground(barBkg);




        if (isFinishingPart)
            img_icon.setVisibility(View.GONE);

//        if(parent != null)
//            parent.addView(parent);
    }

// ____________________________________________________________________

    public void setIcon(int drawableRes) {

        img_icon.setImageResource(drawableRes);
    }

// ____________________________________________________________________

    float getIconHorizontalCenter() {

        return (getTabView().getLeft() + img_icon.getLeft()) + (img_icon.getWidth()/2);
    }

// ____________________________________________________________________

    public View getTabView() {

        return tabView;
    }

// ____________________________________________________________________

    void select() {

        if(isSelected)
            return;

        barBkg.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(isSelected)
                    imgIconBkg.setIsSelect(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        barBkg.setIsSelect(true);
        isSelected = true;
    }

// ____________________________________________________________________

    void deselect() {


        if(!isSelected)
            return;

        imgIconBkg.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(!isSelected)
                    barBkg.setIsSelect(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imgIconBkg.setIsSelect(false);
        isSelected = false;
    }

// ____________________________________________________________________

    View.OnClickListener onTabClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

//            if(isSelected) {
//
//                deselect();
//
//            }
//            else
//                select();
        }
    };

// ____________________________________________________________________
}

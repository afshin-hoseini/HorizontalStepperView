package ir.afshin.horizontalstepper;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by afshinhoseini on 8/26/16.
 */
public class CustomizedViewPager extends ViewPager {

// ____________________________________________________________________
    public CustomizedViewPager(Context context) {
        super(context);
        init();
    }

    public CustomizedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

// ____________________________________________________________________

    private void init() {

        setMyScroller();
    }

// ____________________________________________________________________

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

// ____________________________________________________________________

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

// ____________________________________________________________________

    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// ____________________________________________________________________

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }


// ____________________________________________________________________
}

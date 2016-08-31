package ir.afshin.horizontalstepper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.Animation;

/**
 * Created by afshinhoseini on 8/25/16.
 */
public class StepTabBkg extends Drawable implements Drawable.Callback {

    private Drawable unselectedDrawable = null;
    private Drawable selectedDrawable = null;
    private Context context = null;
    private ScaleAnimDrawable scaleAnimDrawable = null;
    private Animation.AnimationListener animationListener = null;

// ____________________________________________________________________

    public StepTabBkg(Context context, Drawable selectedDrawable, Drawable unselectedDrawable) {

        this.context = context;
        this.unselectedDrawable = unselectedDrawable;
        this.selectedDrawable = selectedDrawable;

        scaleAnimDrawable = new ScaleAnimDrawable(context, selectedDrawable);
        scaleAnimDrawable.setCallback(this);
    }

// ____________________________________________________________________


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        unselectedDrawable.setBounds(bounds);
        scaleAnimDrawable.setBounds(bounds);
    }

// ____________________________________________________________________
    @Override
    public void draw(Canvas canvas) {

        unselectedDrawable.draw(canvas);
        scaleAnimDrawable.draw(canvas);
    }

// ____________________________________________________________________

    @Override
    public void setAlpha(int i) {

    }

// ____________________________________________________________________

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

// ____________________________________________________________________

    @Override
    public int getOpacity() {
        return 0;
    }

// ____________________________________________________________________

    public void setIsSelect(boolean selected) {

        if(selected)
            scaleAnimDrawable.expand(Gravity.START, ScaleAnimDrawable.AnimSide.BothSide);
        else
            scaleAnimDrawable.shrink(Gravity.START, ScaleAnimDrawable.AnimSide.BothSide);
    }
// ____________________________________________________________________
     public void setAnimationListener(Animation.AnimationListener animationListener) {

         scaleAnimDrawable.animationListener = animationListener;
     }

// ____________________________________________________________________
    @Override
    public void invalidateDrawable(Drawable drawable) {


        if(getCallback() != null){
            getCallback().invalidateDrawable(this);
        }
    }

    @Override
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l) {

    }

    @Override
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {

    }

// ____________________________________________________________________
}

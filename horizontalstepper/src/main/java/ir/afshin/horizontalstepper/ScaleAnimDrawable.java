package ir.afshin.horizontalstepper;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by afshinhoseini on 8/24/16.
 */
public class ScaleAnimDrawable extends Drawable {

    public enum AnimSide {

        BothSide, width, height
    }

    Context context = null;
    Drawable drawable = null;
    AlphaAnimation alphaAnimation = null;
    long duration = 150;
    boolean isAnimating = false;
    float pivotX = 0;
    float pivotY = 0;
    AnimSide animSide = AnimSide.BothSide;
    int animGravity = 0;
    float finishScale = 0;

    Animation.AnimationListener animationListener = null;

    Transformation transformation = new Transformation();

// ____________________________________________________________________

    public ScaleAnimDrawable(Context context, Drawable drawable) {

        this.drawable = drawable;
        this.context = context;
    }

// ____________________________________________________________________

    private void calculatePivotes() {

        if(animGravity == Gravity.LEFT) {

            pivotX = 0;
            pivotY = getBounds().bottom / 2;
        }
        else if(animGravity == Gravity.RIGHT) {

            pivotX = getBounds().width();
            pivotY = getBounds().bottom / 2;
        }
    }

// ____________________________________________________________________

    @Override
    public void draw(Canvas canvas) {

        int saveCount = canvas.save();

        calculatePivotes();


        long animTime = System.currentTimeMillis();

        float scale = 0;

        if(alphaAnimation != null) {

            alphaAnimation.getTransformation(animTime, transformation);
            scale = transformation.getAlpha();
        }

        float scaleX = animSide == AnimSide.height ? 1 : scale;
        float scaleY = animSide == AnimSide.width ? 1 : scale;
        canvas.scale(scaleX, scaleY, pivotX, pivotY);

        drawable.draw(canvas);

        canvas.restoreToCount(saveCount);

        if(isAnimating) {

            if(scale == finishScale) {

                isAnimating = false;
                if(animationListener != null)
                    animationListener.onAnimationEnd(alphaAnimation);
            }
            else {

                invalidateSelf();
            }

        }
    }

// ____________________________________________________________________

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        drawable.setBounds(bounds);
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
        return 255;
    }

// ____________________________________________________________________

    public void expand(int gravity, AnimSide animSide) {

        startAnimation(gravity, animSide, 0,1);
    }

// ____________________________________________________________________

    public void shrink(int gravity, AnimSide animSide) {

        startAnimation(gravity, animSide, 1,0);
    }

// ____________________________________________________________________

    public void startAnimation(int gravity, AnimSide animSide, float from, float to) {

        gravity = Gravity.getAbsoluteGravity(gravity, getLayoutDirection());
        this.animSide = animSide;
        this.animGravity = gravity;

        alphaAnimation = new AlphaAnimation(from, to);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);

        isAnimating = true;
        finishScale = to;
        invalidateSelf();

        if(animationListener != null)
            animationListener.onAnimationStart(alphaAnimation);
    }

// ____________________________________________________________________

    public int getLayoutDirection() {

        Configuration config = context.getResources().getConfiguration();

        return config.getLayoutDirection();
    }


// ____________________________________________________________________
}

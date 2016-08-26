package ir.afshin.horizontalstepper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by afshinhoseini on 8/25/16.
 */
public class TitledHorizontalStepper extends LinearLayout {

    private View contentView = null;
    private HorizontalStepper horizontalStepper = null;
    private TextView txt_title = null;
    private FrameLayout titleFrame = null;
    private TranslateAnimation txtTitle_translateAnimation =  null;
    private float pendingTitlePosFrom = 0;
    private float pendingTitlePosTo = 0;
    private TabItem currentTabItem = null;

    private int txtTitleColor = 0;
    private int titleFrameColor = 0;
    private int backgroundColor = 0;

// ____________________________________________________________________

    public TitledHorizontalStepper(Context context) {
        super(context);
        init();
    }

    public TitledHorizontalStepper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitledHorizontalStepper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public TitledHorizontalStepper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

// ____________________________________________________________________

    private void init() {

        int[] attrs = {R.attr.selectedStepTitleColor, R.attr.stepsContainerBackground, R.attr.titleFrameBackground};
        TypedArray ta = getContext().obtainStyledAttributes(R.style.stepper, attrs);

        txtTitleColor = ta.getColor(0, 0);
        backgroundColor = ta.getColor(1,0);
        titleFrameColor = ta.getColor(2,0);

        ta.recycle();

        contentView  = LayoutInflater.from(getContext()).inflate(R.layout.titled_horizontal_stepper, this, false);
        addView(contentView);

        horizontalStepper = (HorizontalStepper) contentView.findViewById(R.id.horizontalStepper);
        txt_title = (TextView) contentView.findViewById(R.id.stepTitle);
        titleFrame = (FrameLayout) contentView.findViewById(R.id.titleFrame);

        horizontalStepper.setStepSelectionChangedListener(stepSelectionChangedListener);

        setBackgroundColor(backgroundColor);
        txt_title.setTextColor(txtTitleColor);
        titleFrame.setBackgroundColor(titleFrameColor);

    }
// ____________________________________________________________________

    public HorizontalStepper getStepper() {

        return horizontalStepper;
    }

// ____________________________________________________________________

    private void animateTitleTo(final TabItem tabItem) {

        Log.e("ANIMATION", "TAB ITEM");


        if(txtTitle_translateAnimation != null) {

            Transformation transformation = new Transformation();
            txtTitle_translateAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), transformation);

            float[] matrix = new float[9];
            transformation.getMatrix().getValues(matrix);
            float xTrans = matrix[Matrix.MTRANS_X];

            txt_title.setX(pendingTitlePosFrom + xTrans);

            txtTitle_translateAnimation.setAnimationListener(null);
            txtTitle_translateAnimation.cancel();

        }


        Paint titlePaint = new Paint();
        titlePaint.setTypeface(txt_title.getTypeface());
        titlePaint.setTextSize(txt_title.getTextSize());
        float nextTitleWidth = titlePaint.measureText(tabItem.label);

        float wholeWidth = titleFrame.getWidth();

        float tabCenterHorizontalPos = tabItem.tabItemView.getIconHorizontalCenter();
        float nextTitle_xPosition = tabCenterHorizontalPos - (nextTitleWidth/2);

        if(nextTitle_xPosition < 0)
            nextTitle_xPosition = 0;

        if(nextTitle_xPosition + nextTitleWidth > getLeft()+wholeWidth)
            nextTitle_xPosition -= (nextTitle_xPosition + nextTitleWidth) - (getLeft()+wholeWidth);

        //txt_title.setX(nextTitle_xPosition);
        txt_title.setText(tabItem.label);
        txt_title.setVisibility(VISIBLE);

        pendingTitlePosFrom = txt_title.getX();
        pendingTitlePosTo = nextTitle_xPosition;

        txtTitle_translateAnimation = new TranslateAnimation(0, nextTitle_xPosition - txt_title.getX(), 0, 0);
        txtTitle_translateAnimation.setDuration(300);
        txtTitle_translateAnimation.setFillEnabled(true);
        txtTitle_translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                txtTitle_translateAnimation = null;
                txt_title.setX(pendingTitlePosTo);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        txt_title.startAnimation(txtTitle_translateAnimation);


    }

// ____________________________________________________________________

    public void nextStep() {

        currentTabItem = horizontalStepper.nextStep();
    }

// ____________________________________________________________________

    public void previousStep() {

        currentTabItem = horizontalStepper.previousStep();
    }

// ____________________________________________________________________

    HorizontalStepper.StepSelectionChanged stepSelectionChangedListener = new HorizontalStepper.StepSelectionChanged() {
        @Override
        public void selectedStep(TabItem tabItem) {


            if(currentTabItem != tabItem) {

                currentTabItem = tabItem;
                animateTitleTo(tabItem);
            }
        }
    };

// ____________________________________________________________________
}

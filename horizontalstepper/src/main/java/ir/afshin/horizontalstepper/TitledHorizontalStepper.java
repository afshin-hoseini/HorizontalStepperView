package ir.afshin.horizontalstepper;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
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

        contentView  = LayoutInflater.from(getContext()).inflate(R.layout.titled_horizontal_stepper, this, false);
        addView(contentView);

        horizontalStepper = (HorizontalStepper) contentView.findViewById(R.id.horizontalStepper);
        txt_title = (TextView) contentView.findViewById(R.id.stepTitle);
        titleFrame = (FrameLayout) contentView.findViewById(R.id.titleFrame);

        horizontalStepper.setStepSelectionChangedListener(stepSelectionChangedListener);

    }
// ____________________________________________________________________

    public HorizontalStepper getStepper() {

        return horizontalStepper;
    }

// ____________________________________________________________________

    private void animateTitleTo(final TabItem tabItem) {

        Paint titlePaint = new Paint();
        titlePaint.setTypeface(txt_title.getTypeface());
        titlePaint.setTextSize(txt_title.getTextSize());
        float nextTitleWidth = titlePaint.measureText(tabItem.label);

        float wholeWidth = titleFrame.getWidth();

        float tabCenterHorizontalPos = tabItem.tabItemView.getIconHorizontalCenter();
        final float nextTitle_xPosition = tabCenterHorizontalPos - (nextTitleWidth/2);

        //txt_title.setX(nextTitle_xPosition);
        txt_title.setText(tabItem.label);

        if(txtTitle_translateAnimation != null)
            txtTitle_translateAnimation.cancel();

        txtTitle_translateAnimation = new TranslateAnimation(0, nextTitle_xPosition - txt_title.getX(), 0, 0);
        txtTitle_translateAnimation.setDuration(300);
        txtTitle_translateAnimation.setFillEnabled(true);
        txtTitle_translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                txt_title.setX(nextTitle_xPosition);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        txt_title.startAnimation(txtTitle_translateAnimation);


    }

// ____________________________________________________________________

    public void nextStep() {

        TabItem currentTabItem = horizontalStepper.nextStep();
    }

// ____________________________________________________________________

    public void previousStep() {

        TabItem currentTabItem = horizontalStepper.previousStep();
    }

// ____________________________________________________________________

    HorizontalStepper.StepSelectionChanged stepSelectionChangedListener = new HorizontalStepper.StepSelectionChanged() {
        @Override
        public void selectedStep(TabItem tabItem) {

            animateTitleTo(tabItem);
        }
    };

// ____________________________________________________________________
}

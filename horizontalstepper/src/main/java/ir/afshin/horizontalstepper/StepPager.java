package ir.afshin.horizontalstepper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by afshinhoseini on 8/26/16.
 */
public class StepPager extends RelativeLayout {

    private View buttonsPanel = null;
    private TextView btnPrev = null;
    private TextView btnNext = null;
    private TitledHorizontalStepper stepper = null;
    private CustomizedViewPager viewPager = null;
    private TabItem[] steps = null;
    private Adapter adapter = null;
    private boolean isRtl = false;
    private Handler handler = new Handler();
    private int currentStep = 0;

// ____________________________________________________________________

//region constructors
    public StepPager(Context context) {
        super(context);
    }

    public StepPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StepPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public StepPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
//endregion

// ____________________________________________________________________

    public void init(TabItem[] steps, Adapter adapter) {


        Configuration configuration = getContext().getResources().getConfiguration();
        isRtl = configuration.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;

        ViewGroup contentView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.step_pager, this, false);

        ArrayList<View> children = new ArrayList<>();
        for(int i = 0; i < contentView.getChildCount(); i++)
            children.add(contentView.getChildAt(i));

        for(View child : children) {

            contentView.removeView(child);
            addView(child);
        }

        buttonsPanel = findViewById(R.id.buttonPanel);
        btnNext = (TextView) findViewById(R.id.btnNext);
        btnPrev = (TextView) findViewById(R.id.btnPrev);
        stepper = (TitledHorizontalStepper) findViewById(R.id.stepper);
        viewPager = (CustomizedViewPager) findViewById(R.id.viewPager);

        this.steps = steps;
        this.adapter = adapter;
        if(steps != null) {

            for(TabItem step : steps)
                stepper.getStepper().addTab(step);
        }

        viewPager.setAdapter(pagerAdapter);

        if(isRtl)
            viewPager.setCurrentItem(StepPager.this.steps.length - 1,false);

        btnNext.setOnClickListener(onBtnNextClicked);
        btnPrev.setOnClickListener(onBtnPreviousClicked);

    }

// ____________________________________________________________________

    private int indexOf(TabItem step) {

        for(int i=0; i<steps.length; i++)
            if(steps[i] == step)
                return  i;

        return -1;
    }

// ____________________________________________________________________

    OnClickListener onBtnNextClicked = new OnClickListener() {
        @Override
        public void onClick(View v) {

            boolean isCurrentPageValid = adapter.isValid(steps[currentStep], currentStep);

            if(currentStep == steps.length -1 && isCurrentPageValid){

                adapter.finished();
            }
            else if(isCurrentPageValid) {

                stepper.nextStep();
                currentStep ++;

                if(isRtl)
                    viewPager.setCurrentItem(steps.length - 1 - currentStep);

                adapter.enteredStep(steps[currentStep], currentStep);
            }
        }
    };

// ____________________________________________________________________

    OnClickListener onBtnPreviousClicked = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if(currentStep > 0) {

                stepper.previousStep();
                currentStep --;

                if(isRtl)
                    viewPager.setCurrentItem(steps.length - 1 - currentStep);
                adapter.enteredStep(steps[currentStep], currentStep);
            }
        }
    };
// ____________________________________________________________________

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {

            return steps == null ? 0 : steps.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if(isRtl)
                position = steps.length - 1 - position;

            View view = adapter.getViewFor(container, steps[position], position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            if(isRtl)
                position = steps.length - 1 - position;

            adapter.destroyView((View) object, steps[position], position);
        }
    };

// ____________________________________________________________________

    public interface Adapter {

        void destroyView(View stepView, TabItem step, int stepNo);
        void enteredStep(TabItem step, int stepNo);
        boolean isValid(TabItem step, int stepNo);
        View getViewFor(ViewGroup parent, TabItem step, int stepNo);
        void finished();
    }

// ____________________________________________________________________
}

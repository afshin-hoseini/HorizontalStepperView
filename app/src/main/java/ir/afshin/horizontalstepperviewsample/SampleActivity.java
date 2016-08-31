package ir.afshin.horizontalstepperviewsample;

import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import java.util.Locale;

import ir.afshin.horizontalstepper.HorizontalStepper;
import ir.afshin.horizontalstepper.TabItem;
import ir.afshin.horizontalstepper.TitledHorizontalStepper;

public class SampleActivity extends AppCompatActivity {

    TitledHorizontalStepper titledHorizontalStepper = null;
    HorizontalStepper horizontalStepper = null;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //makeRtl();

        setContentView(R.layout.activity_sample);

        titledHorizontalStepper = (TitledHorizontalStepper) findViewById(R.id.stepperView);
        horizontalStepper = titledHorizontalStepper.getStepper();
        horizontalStepper.addTab(new TabItem("Item1 witr", R.mipmap.ic_launcher));
        horizontalStepper.addTab(new TabItem("Item2", R.mipmap.ic_launcher));
        horizontalStepper.addTab(new TabItem("Item3 88736s", R.mipmap.ic_launcher));
        horizontalStepper.addTab(new TabItem("Item4", R.mipmap.ic_launcher));
        horizontalStepper.addTab(new TabItem("Item5 with long data", R.mipmap.ic_launcher));

    }

    protected void makeRtl() {

        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("fa"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    public void btnNextClicked(View v) {

        titledHorizontalStepper.nextStep();
    }

    public void btnPrevClicked(View v) {

        titledHorizontalStepper.previousStep();
        ViewPager viewPager = null;

    }
}

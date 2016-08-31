package ir.afshin.horizontalstepperviewsample;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import ir.afshin.horizontalstepper.StepPager;
import ir.afshin.horizontalstepper.TabItem;

/**
 * Created by afshinhoseini on 8/26/16.
 */
public class PagerSample extends Activity {

    StepPager stepPager = null;


// ____________________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //makeRtl();

        setContentView(R.layout.activity_pager);

        TabItem[] steps = {

            new TabItem("Item1werty", R.mipmap.ic_launcher),
            new TabItem("Item", R.mipmap.ic_launcher),
            new TabItem("Item3655", R.mipmap.ic_launcher),
            new TabItem("Ite", R.mipmap.ic_launcher),
            new TabItem("Item58866", R.mipmap.ic_launcher)

        };

        stepPager = (StepPager) findViewById(R.id.stepPager);
        stepPager.init(steps, new StepPager.Adapter() {

            @Override
            public  void destroyView(View stepView, TabItem step, int stepNo) {


            }

            @Override
            public void enteredStep(TabItem step, int stepNo) {

            }

            @Override
            public boolean isValid(TabItem step, int stepNo) {
                return true;
            }

            @Override
            public View getViewFor(ViewGroup parent, TabItem step, int stepNo) {

                Button btn = new Button(PagerSample.this);
                btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                btn.setText("STEP: " + stepNo);
                btn.setTextColor(0xff000000);

                Log.e("GET_VIEW", stepNo + "");
                return btn;
            }

            @Override
            public void finished() {

            }
        });

    }

// ____________________________________________________________________

    protected void makeRtl() {

        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("fa"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        Locale locale = new Locale("fa");
        Locale.setDefault(locale);

        Resources resources = getResources();

        configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

    }
// ____________________________________________________________________


}

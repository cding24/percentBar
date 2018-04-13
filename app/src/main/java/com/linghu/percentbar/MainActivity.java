package com.linghu.percentbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * created by linghu on 2017/5/4
 *  这是一个比例进度控件
 */
public class MainActivity extends AppCompatActivity {
    private PercentBar phyPercentBar;
    private PercentBar chemPercentBar;

    private int phyRate;
    private int chemRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phyPercentBar = (PercentBar) findViewById(R.id.phy_percentBar);
        chemPercentBar = (PercentBar) findViewById(R.id.chem_percentBar);


        phyRate = 80;
        chemRate = 30;
        if (phyRate >= 0) {
            phyPercentBar.setPercent(phyRate);
        } else {
            phyPercentBar.setPercent(0);
        }
        if (chemRate >= 0) {
            chemPercentBar.setPercent(chemRate);
        } else {
            chemPercentBar.setPercent(0);
        }
    }

}

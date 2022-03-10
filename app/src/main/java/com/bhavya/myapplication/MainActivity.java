package com.bhavya.myapplication;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private CustomSeekBar seekbar;

    private double totalSpan = 100;
    private double redSpan = 0;
    private double greenSpan = 0;
    private double yellowSpan = 0;
    private double darkGreySpan;

    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekbar = findViewById(R.id.seekBar0);
        initDataToSeekbar(70.0);
        seekbar.setEnabled(false);
    }

    private void initDataToSeekbar(double a) {
        seekbar.setProgress((int)a);
        progressItemList = new ArrayList<ProgressItem>();
        if(a<=(100.0/3))
        {
            redSpan=a;
        }
        else if(a<=(200.0/3))
        {
            yellowSpan=a-100.0/3;
            redSpan=100.0/3;
        }
        else if(a<=(300.0/3))
        {
            redSpan=100.0/3;
            yellowSpan=100.0/3;
            greenSpan=a-200.0/3;
        }
        darkGreySpan=totalSpan-(redSpan+yellowSpan+greenSpan);
        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (float) ((redSpan / totalSpan) * 100);
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);
        // yellow span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (float) (yellowSpan / totalSpan) * 100;
        mProgressItem.color = R.color.yellow;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (float) ((greenSpan) / totalSpan) * 100;
        mProgressItem.color = R.color.green;
        progressItemList.add(mProgressItem);
        // greyspan
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (float) (darkGreySpan / totalSpan) * 100;
        mProgressItem.color = R.color.grey;
        progressItemList.add(mProgressItem);

        seekbar.initData(progressItemList);
        seekbar.invalidate();
    }
}
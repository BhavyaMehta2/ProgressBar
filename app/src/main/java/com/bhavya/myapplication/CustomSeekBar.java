package com.bhavya.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.ArrayList;

public class CustomSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    private ArrayList<ProgressItem> mProgressItemsList;
    private float[] corners = new float[]{
        0, 0,        // Top left radius in px
                0, 0,        // Top right radius in px
                0, 0,          // Bottom right radius in px
                0, 0           // Bottom left radius in px
    };
    int padding = 64;

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initData(ArrayList<ProgressItem> progressItemsList) {
        this.mProgressItemsList = progressItemsList;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, 150);
    }

    private void setCornerRadius(int i, int progress)
    {
        int progressBarHeight = getHeight();
        if(i == 0 && progress<=100/3) {
            corners = new float[]{
                    progressBarHeight, progressBarHeight,        // Top left radius in px
                    progressBarHeight, progressBarHeight,         // Top right radius in px
                    0, 0,          // Bottom right radius in px
                    0, 0           // Bottom left radius in px
            };
        }
        else if (i==0)
        {
            corners = new float[]{
                    progressBarHeight, progressBarHeight,        // Top left radius in px
                    0, 0,         // Top right radius in px
                    0, 0,          // Bottom right radius in px
                    0, 0           // Bottom left radius in px
            };
        }
        else if((i == 1 && progress<=200/3)||(i == 2 && progress<=300/3)) {
            corners = new float[]{
                    0, 0,        // Top left radius in px
                    progressBarHeight, progressBarHeight,        // Top right radius in px
                    0, 0,          // Bottom right radius in px
                    0, 0           // Bottom left radius in px
            };
        }
        else if(i == mProgressItemsList.size() - 1&&progress==0) {
            {
                corners = new float[]{
                        progressBarHeight, progressBarHeight,        // Top left radius in px
                        progressBarHeight, progressBarHeight,         // Top right radius in px
                        0, 0,          // Bottom right radius in px
                        0, 0           // Bottom left radius in px
                };
            }
        }
        else if(i == mProgressItemsList.size() - 1) {
            {
                corners = new float[]{
                        0, 0,        // Top left radius in px
                        progressBarHeight, progressBarHeight,         // Top right radius in px
                        0, 0,          // Bottom right radius in px
                        0, 0           // Bottom left radius in px
                };
            }
        }
        else {
            corners = new float[]{
                    0, 0,        // Top left radius in px
                    0, 0,        // Top right radius in px
                    0, 0,          // Bottom right radius in px
                    0, 0           // Bottom left radius in px
            };
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int thumb_x = (int) (( (double)this.getProgress()/this.getMax() ) * ((double)this.getWidth()-padding))+padding/2;
        float middle = (float) (this.getHeight())-100;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        canvas.drawText(""+this.getProgress(), thumb_x, middle+10, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.progress_seek_bar_arrow_down);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, false);

        int color=0;

        if(getProgress()<=100/3)
            color=R.color.red;
        else if(getProgress()<=200/3)
            color=R.color.yellow;
        else if(getProgress()<=100)
            color=R.color.green;

        Paint filter=new Paint();
        ColorFilter filterBMP = new PorterDuffColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN);
        filter.setColorFilter(filterBMP);

       canvas.drawBitmap(resizedBitmap, thumb_x-resizedBitmap.getWidth()/2.0f, middle+resizedBitmap.getHeight()/2.0f,filter);
        if (mProgressItemsList.size() > 0) {
            int lastProgressX = padding/2;
            int progressBarWidth = getWidth()-padding;
            int progressBarHeight = getHeight();
            int thumboffset = getThumbOffset();
            int progressItemWidth, progressItemRight;
            for (int i = 0; i < mProgressItemsList.size(); i++) {
                ProgressItem progressItem = mProgressItemsList.get(i);
                Paint progressPaint = new Paint();
                progressPaint.setColor(getResources().getColor(
                        progressItem.color));

                progressItemWidth = (int) (progressItem.progressItemPercentage
                        * progressBarWidth / 100);

                progressItemRight = lastProgressX + progressItemWidth;

                setCornerRadius(i, getProgress());

                // for last item give right to progress item to the width
                if (i == mProgressItemsList.size() - 1
                        && progressItemRight != progressBarWidth) {
                    progressItemRight = progressBarWidth+padding/2;
                }

                RectF progressRect = new RectF();
                progressRect.set(lastProgressX, thumboffset / 2.0f+100,
                        progressItemRight, progressBarHeight - thumboffset / 2.0f);

                final Path path = new Path();
                path.addRoundRect(progressRect, corners, Path.Direction.CW);
                canvas.drawPath(path, progressPaint);
                lastProgressX = progressItemRight;
            }

        }

    }

}

package com.algowire.chanelview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int currentHeight = 0;
    int firstChildHeight = 0;
    int defaultChildHeight = 0;
    int numberOfChild = 6;
    int[] colors = new int[numberOfChild];
    LinearLayout mainContainer;
    ScrollView mainScrollView;
    int currentScrollPosition;
    int finalScrollPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < colors.length; i++) {
            Random random = new Random();
            colors[i] = 100000 + random.nextInt(900000);
        }

        Display display = getWindowManager().getDefaultDisplay();
        currentHeight = display.getHeight();

        firstChildHeight = (currentHeight * 50) / 100;
        defaultChildHeight = currentHeight / numberOfChild;

        mainContainer = (LinearLayout) findViewById(R.id.mailLayout);
        mainScrollView = (ScrollView) findViewById(R.id.mailScrollLayout);
        for (int i = 0; i < numberOfChild; i++) {
            LinearLayout childLayout;
            if (i == 0) {
                childLayout = new LinearLayout(this);
                childLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, firstChildHeight));
                childLayout.setBackgroundColor(Color.parseColor("#" + colors[i]));
                childLayout.setTag(i);
                childLayout.setOnClickListener(this);
                mainContainer.addView(childLayout);
            } else {
                childLayout = new LinearLayout(this);
                childLayout.setOnClickListener(this);
                childLayout.setTag(i);
                childLayout.setBackgroundColor(Color.parseColor("#" + colors[i]));
                childLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, defaultChildHeight));
                mainContainer.addView(childLayout);
            }
        }
    }

    @Override
    public void onClick(final View v) {

        currentScrollPosition = mainScrollView.getScrollY();
        finalScrollPosition = v.getTop();

        ValueAnimator scrollAnimator = ValueAnimator.ofInt(currentScrollPosition, finalScrollPosition);
        scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int amount = (int) animation.getAnimatedValue();
                mainScrollView.scrollTo(0, amount);
            }
        });
        scrollAnimator.setDuration(500);

        ValueAnimator heightAnimator = ValueAnimator.ofInt(v.getHeight(), firstChildHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                v.getLayoutParams().height = height;
                v.requestLayout();
            }
        });
        heightAnimator.setDuration(500);

        heightAnimator.start();
        scrollAnimator.start();

        scrollAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}

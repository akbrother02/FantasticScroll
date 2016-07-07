package com.algowire.chanelview;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GestureDetector.OnGestureListener {

    int currentHeight = 0;
    int firstChildHeight = 0;
    int defaultChildHeight = 0;
    int numberOfChild = 6;
    int[] colors = new int[numberOfChild];
    LinearLayout mainContainer;
    ScrollView mainScrollView;
    GestureDetectorCompat detector;
    int currentScrollPosition;
    int finalScrollPosition;
    View currentView;
    View precedingView;
    View followingView;
    int currentActive = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detector = new GestureDetectorCompat(this, this);

        for (int i = 0; i < colors.length; i++) {
            Random random = new Random();
            colors[i] = 100000 + random.nextInt(900000);
        }

        int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six};

        Display display = getWindowManager().getDefaultDisplay();
        currentHeight = display.getHeight();

        firstChildHeight = (currentHeight * 50) / 100;
        defaultChildHeight = currentHeight / numberOfChild;

        mainContainer = (LinearLayout) findViewById(R.id.mailLayout);
        mainScrollView = (ScrollView) findViewById(R.id.mailScrollLayout);
        mainScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        for (int i = 0; i < numberOfChild; i++) {
            LinearLayout childLayout;
            if (i == 0) {
                childLayout = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.child_layout, null);
                childLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, firstChildHeight));
                //childLayout.setBackgroundColor(Color.parseColor("#" + colors[i]));
                ImageView imageView = (ImageView) childLayout.findViewById(R.id.image);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setImageResource(images[i]);
                childLayout.setOnClickListener(this);
                childLayout.setTag(i);
                mainContainer.addView(childLayout);
            } else {
                childLayout = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.child_layout, null);
                childLayout.setTag(i);
                childLayout.setOnClickListener(this);
                ImageView imageView = (ImageView) childLayout.findViewById(R.id.image);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setImageResource(images[i]);
                //childLayout.setBackgroundColor(Color.parseColor("#" + colors[i]));
                childLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, defaultChildHeight));
                mainContainer.addView(childLayout);
            }
        }

        setPrecedingView(null);
        setCurrentView(mainContainer.getChildAt(0));
        setFollowingView(mainContainer.getChildAt(1));
        currentActive = 0;
    }

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View currentView) {
        this.currentView = currentView;
        System.out.println(mainContainer.indexOfChild(currentView));
    }

    public View getPrecedingView() {
        return precedingView;
    }

    public void setPrecedingView(View precedingView) {
        this.precedingView = precedingView;
        System.out.println(mainContainer.indexOfChild(precedingView));
    }

    public View getFollowingView() {
        return followingView;
    }

    public void setFollowingView(View followingView) {
        this.followingView = followingView;
        System.out.println(mainContainer.indexOfChild(followingView));
        System.out.println("/// Clean ///");
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

        scrollAnimator.start();
        heightAnimator.start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return this.detector.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        final int SWIPE_MIN_DISTANCE = 50;
        final int SWIPE_THRESHOLD_VELOCITY = 50;

        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            //Toast.makeText(this, "Bottom to Top", Toast.LENGTH_SHORT).show();
            if (getFollowingView() != null) {
                downToUpScroll(getCurrentView(), getFollowingView());
            }
            //From Bottom to Top
            return true;
        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            //Toast.makeText(this, "Top to Bottom", Toast.LENGTH_SHORT).show();
            if (getPrecedingView() != null) {
                upToDownScroll(getPrecedingView(), getCurrentView());
            }
            //From Top to Bottom
            return true;
        }

        return true;
    }

    public void upToDownScroll(final View precedingView, final View currentView) {

        if (mainContainer.indexOfChild(currentView) == 0) {
            //do-nothing
        } else {
            int currentScrollPosition = mainScrollView.getScrollY();
            int toScrollPosition = precedingView.getTop();

            ValueAnimator scrollAnimator = ValueAnimator.ofInt(currentScrollPosition, toScrollPosition);
            scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int amount = (int) animation.getAnimatedValue();
                    mainScrollView.scrollTo(0, amount);
                }
            });
            scrollAnimator.setDuration(400);

            ValueAnimator heightAnimator = ValueAnimator.ofInt(currentView.getLayoutParams().height, defaultChildHeight);
            heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int height = (int) animation.getAnimatedValue();
                    currentView.getLayoutParams().height = height;
                    currentView.requestLayout();
                }
            });
            heightAnimator.setDuration(400);

            scrollAnimator.start();
            heightAnimator.start();

            View temp = currentView;
            setCurrentView(precedingView);
            setPrecedingView(mainContainer.getChildAt(mainContainer.indexOfChild(precedingView) - 1));
            setFollowingView(temp);
        }
    }

    public void downToUpScroll(View currentView, final View followingView) {
        int currentScrollPosition = mainScrollView.getScrollY();
        int toScrollPosition = followingView.getTop();

        ValueAnimator scrollAnimator = ValueAnimator.ofInt(currentScrollPosition, toScrollPosition);
        scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int amount = (int) animation.getAnimatedValue();
                mainScrollView.scrollTo(0, amount);
            }
        });
        scrollAnimator.setDuration(400);

        ValueAnimator heightAnimator = ValueAnimator.ofInt(followingView.getHeight(), firstChildHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                followingView.getLayoutParams().height = height;
                followingView.requestLayout();
            }
        });
        heightAnimator.setDuration(400);

        scrollAnimator.start();
        heightAnimator.start();

        View temp = currentView;
        setPrecedingView(currentView);
        setCurrentView(followingView);
        setFollowingView(mainContainer.getChildAt(mainContainer.indexOfChild(followingView) + 1));
    }
}

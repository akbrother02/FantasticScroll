package developer.shivam.mylibrary;

/**
 *    FantasticScroll v1.0
 */

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Random;

import static android.widget.LinearLayout.VERTICAL;

public class FantasticScroller extends ScrollView {

    private int NUM_OF_CHILD = 5;
    private Context context = null;
    private int focusedChildHeight = 600;
    private int defaultChildHeight = 300;
    String[] childColors;

    public FantasticScroller(Context context) {
        super(context);
        init(context, null);
    }

    public FantasticScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        childColors = new String[getNumberOfChild()];
        for (int i = 0; i < getNumberOfChild(); i++) {
            Random random = new Random();
            int color = 100000 + random.nextInt(900000);
            childColors[i] = "#" + color;
        }

        makeFantasticScrollView();
    }

    public void makeFantasticScrollView() {
        /**
         * ScrollView is a parent layout in which a single linearLayout is
         *  added with vertical orientation which further contains children
         */
        LinearLayout mainContainer = new LinearLayout(context);
        mainContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mainContainer.setOrientation(VERTICAL);
        for (int i = 0; i < getNumberOfChild(); i++) {
            if (i == 0) {
                LinearLayout childLinearLayout = new LinearLayout(context);
                childLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        getFocusedChildHeight()));
                childLinearLayout.setBackgroundColor(Color.parseColor(childColors[i]));
                mainContainer.addView(childLinearLayout);
            } else {
                LinearLayout childLinearLayout = new LinearLayout(context);
                childLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        getDefaultChildHeight()));
                childLinearLayout.setBackgroundColor(Color.parseColor(childColors[i]));
                mainContainer.addView(childLinearLayout);
            }
        }

        addView(mainContainer);
    }

    public void setNumberOfChild(int size) {
        this.NUM_OF_CHILD = size;
    }

    public int getNumberOfChild() {
        return NUM_OF_CHILD;
    }

    public void setFocusedChildHeight(int focusedChildHeight) {
        this.focusedChildHeight = focusedChildHeight;
    }

    public int getFocusedChildHeight() {
        return focusedChildHeight;
    }

    public int getDefaultChildHeight() {
        return defaultChildHeight;
    }

    public void setDefaultChildHeight(int defaultChildHeight) {
        this.defaultChildHeight = defaultChildHeight;
    }


}

package developer.shivam.mylibrary;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Random;

public class FantasticScroller extends LinearLayout {

    private int NUM_OF_CHILD = 5;
    private Context context = null;
    private int focusedChildHeight;
    private int defaultChildHeight;
    private AttributeSet attributeSet;
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
        this.attributeSet = attrs;

        childColors = new String[getNumberOfChild()];
        for (int i = 0; i < getNumberOfChild(); i++) {
            Random random = new Random();
            int color = 100000 + random.nextInt(900000);
            childColors[i] = "#" + color;
        }

        addChild();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        focusedChildHeight = (b * 60)/100;
        defaultChildHeight = b / 5;
        super.onLayout(changed, l, t, r, b);
    }

    public void addChild() {
        LinearLayout mainContainer = new LinearLayout(context);
        for (int i = 0; i < getNumberOfChild(); i++) {
            if (i == 0) {
                LinearLayout childLinearLayout = new LinearLayout(context);
                childLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        focusedChildHeight));
                childLinearLayout.setBackgroundColor(Color.parseColor(childColors[i]));
                mainContainer.addView(childLinearLayout);
            } else {
                LinearLayout childLinearLayout = new LinearLayout(context);
                childLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        defaultChildHeight));
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
}

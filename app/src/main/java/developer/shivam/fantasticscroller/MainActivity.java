package developer.shivam.fantasticscroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import developer.shivam.mylibrary.FantasticScroller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FantasticScroller scroller = (FantasticScroller) findViewById(R.id.fantasticScroller);
        scroller.setFocusedChildHeight(600);
        scroller.setDefaultChildHeight(300);
        scroller.setNumberOfChild(5);
    }
}

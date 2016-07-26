package developer.shivam.fantasticscroller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FantasticView extends ListView {

    public FantasticView(Context context) {
        super(context);
    }

    public FantasticView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public ListAdapter getAdapter() {
        return null;
    }

    @Override
    public void setSelection(int i) {

    }


}

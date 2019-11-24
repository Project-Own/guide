package com.example.guide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * A NestedScrollView that implements the new-and-improved NestedScrollingParent2
 * interface and that defines its own customized nested scrolling behavior. View
 * source code for the NestedScrollView2 class here: j.mp/NestedScrollView2
 */
public class CustomNestedScrollView2 extends NestedScrollView2 {

    public CustomNestedScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed, int type) {
        final RecyclerView rv = (RecyclerView) target;
        if ((dy < 0 && isRvScrolledToTop(rv)) || (dy > 0 && !isNsvScrolledToBottom(this))) {
            scrollBy(0, dy);
            consumed[1] = dy;
            return;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    // Note that we no longer need to override onNestedPreFling() here; the
    // new-and-improved nested scrolling APIs give us the nested flinging
    // behavior we want already by default!

    private static boolean isNsvScrolledToBottom(NestedScrollView nsv) {
        return !nsv.canScrollVertically(1);
    }

    private static boolean isRvScrolledToTop(RecyclerView rv) {
        final StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) rv.getLayoutManager();

        int[] firstVisibleItem = null;
        firstVisibleItem = lm.findFirstVisibleItemPositions(firstVisibleItem);
        return firstVisibleItem[0] == 0
                && lm.findViewByPosition(0).getTop() == 0;
    }
}

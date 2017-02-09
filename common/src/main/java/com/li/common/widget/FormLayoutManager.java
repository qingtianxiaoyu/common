package com.li.common.widget;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by liweifa on 2016/12/5.
 */

public class FormLayoutManager extends RecyclerView.LayoutManager {
    private final String TAG = "FormLayoutManager";
    private int mAvailableY = 0;
    private final static int LAYOUT_START = -1;

    private final static int LAYOUT_END = 1;
    private int layoutDirection = 1;
    private int LAYOUT_STATE;
    private int LAYOUT_VERTICAL = -1;
    private int LAYOUT_HORIZONTAL = 1;

    private int mColumnCount = 1;


    private int[] columnWidth;
    private int[] rowHeight;


    public FormLayoutManager(int columnCount) {
        mColumnCount = columnCount;

    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {

        LAYOUT_STATE = LAYOUT_HORIZONTAL;
        if (getChildCount() == 0) {
            return 0;
        }
        final int layoutDirection = dx > 0 ? LAYOUT_END : LAYOUT_START;
//        int lastColumn = getItemCount() < mColumnCount ? getItemCount() - 1 : mColumnCount - 1;
        if (getDecoratedRight(getChildAt(mColumnCount - 1)) < getWidth()) {
            return 0;
        }
        int consumed;
        if (layoutDirection == LAYOUT_START) {
            consumed = Math.min(-getDecoratedLeft(getChildAt(0)), -dx);

        } else {
            consumed = Math.max(getWidth() - getDecoratedRight(getChildAt(mColumnCount - 1)), -dx);
        }
        offsetChildrenHorizontal(consumed);
        return -consumed;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        LAYOUT_STATE = LAYOUT_VERTICAL;
        return scrollBy(dy, recycler, state);
    }

    int scrollBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        if (getChildCount() == 0) {
            return 0;
        }

        layoutDirection = dy > 0 ? LAYOUT_END : LAYOUT_START;
        if (LAYOUT_STATE == LAYOUT_VERTICAL) {
            Log.d(TAG, "dy" + dy);
            Log.d(TAG, "AvailableY" + mAvailableY);
            if (layoutDirection == LAYOUT_END) {
                if (getPosition(getChildAt(getChildCount() - 1)) == getItemCount() - 1 && getDecoratedBottom(getChildAt(getChildCount() - 1)) < getHeight()) {
                    return 0;
                }
                mAvailableY = getHeight() - getDecoratedBottom(getChildAt(getChildCount() - 1)) + Math.abs(dy);
                fill(recycler, state);
                int consumed = mAvailableY < 0 ? -dy : -dy + mAvailableY;
                offsetChildrenVertical(consumed);
                return -consumed;

            } else {
                mAvailableY = getDecoratedTop(getChildAt(0)) + Math.abs(dy);
                fill(recycler, state);
                int consumed = mAvailableY < 0 ? -dy : -dy - mAvailableY;
                offsetChildrenVertical(consumed);
                return -consumed;
            }
        }
        return 0;

    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        if (getChildCount() == 0) {
            mAvailableY = getHeight();
            mColumnCount = mColumnCount < getItemCount() ? mColumnCount : getItemCount();
            columnWidth = new int[mColumnCount];
            int mRowCount = getItemCount() % mColumnCount == 0 ? getItemCount() / mColumnCount : getItemCount() / mColumnCount + 1;
            rowHeight = new int[mRowCount];
            for (int i = 0; i < columnWidth.length; i++) {
                View view = recycler.getViewForPosition(i);
                measureChildWithMargins(view, 0, 0);
                columnWidth[i] = getDecoratedMeasuredWidth(view);
            }
            for (int j = 0; j < rowHeight.length; j++) {
                View view = recycler.getViewForPosition(j * mColumnCount);
                measureChildWithMargins(view, 0, 0);
                rowHeight[j] = getDecoratedMeasuredHeight(view);
            }
        }

        fill(recycler, state);

    }


    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return 0;
        }
        if (getChildCount() > 0) {
            recycleByLayoutState(recycler, state);
        }
        while ((mAvailableY > 0) && hasMore(state)) {
            if (layoutDirection == LAYOUT_END) {
                int position;
                if (getChildCount() == 0) {
                    position = 0;
                } else {
                    position = getPosition(getChildAt(getChildCount() - 1)) + 1;

                }
                /**
                 *
                 return mLayoutManager.getDecoratedMeasuredHeight(view) + params.topMargin
                 + params.bottomMargin;
                 */
                View view = recycler.getViewForPosition(position);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                        view.getLayoutParams();
                int column = position % mColumnCount;
                int row = position / mColumnCount;
                int top;
                int left;
                if (column == 0) {
                    if (getChildCount() == 0) {
                        top = 0;
                        left = 0;
                    } else {
                        top = getDecoratedBottom(getChildAt(getChildCount() - 1));
                        left = getDecoratedLeft(getChildAt(0));

                    }

                } else {
                    if (getChildCount() == 0) {
                        top = 0;
                        left = 0;
                    } else {
                        top = getDecoratedTop(getChildAt(getChildCount() - 1));
                        left = getDecoratedRight(getChildAt(getChildCount() - 1));

                    }
                }
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int bottom = top + rowHeight[row];
                int right = left + columnWidth[column];

                layoutDecoratedWithMargins(view, left, top, right, bottom);

                if (column == mColumnCount - 1) {
                    mAvailableY = mAvailableY - view.getHeight();
                }
            } else {
                int position = getPosition(getChildAt(0)) - 1;
                if (position < 0) {
                    break;
                }
                int column = position % mColumnCount;
                int row = position / mColumnCount;
                /**
                 *        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                 view.getLayoutParams();
                 return mLayoutManager.getDecoratedMeasuredHeight(view) + params.topMargin
                 + params.bottomMargin;
                 */
                View view = recycler.getViewForPosition(position);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                        view.getLayoutParams();
                int top;
                int left;
                int bottom;
                int right;
                if (column == mColumnCount - 1) {
                    if (getChildCount() == 0) {
                        bottom = 0;
                        right = 0;
                    } else {
                        bottom = getDecoratedTop(getChildAt(0));
                        right = getDecoratedRight(getChildAt(mColumnCount - 1));

                    }

                } else {
                    if (getChildCount() == 0) {
                        bottom = 0;
                        right = 0;
                    } else {
                        bottom = getDecoratedBottom(getChildAt(0));
                        right = getDecoratedLeft(getChildAt(0));

                    }
                }
                addView(view, 0);
                measureChildWithMargins(view, 0, 0);
                top = bottom - rowHeight[row];
                left = right - columnWidth[column];
                layoutDecoratedWithMargins(view, left, top, right, bottom);

                if (column == 0) {
                    mAvailableY = mAvailableY - view.getHeight();
                }
            }

        }

        return mAvailableY;

    }

    private boolean hasMore(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return true;
        }
        if (layoutDirection == LAYOUT_END) {
            return getPosition(getChildAt(getChildCount() - 1)) >= 0 && getPosition(getChildAt(getChildCount() - 1)) + 1 < state.getItemCount();
        } else {
            return getPosition(getChildAt(0)) > 0;
        }
    }


    private void recycleByLayoutState(RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int childCount = getChildCount();
        ArrayList<View> views = new ArrayList();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (getDecoratedTop(child) >= getHeight()) {
                views.add(child);
            } else if (getDecoratedBottom(child) <= 0) {
                views.add(child);
            }
        }
        recycleChildren(recycler, views);

    }


    private void recycleChildren(RecyclerView.Recycler recycler, ArrayList<View> views) {
        if (views == null && views.size() == 0) {
            return;
        }
        Log.d(TAG, "回收" + views.size());
        for (View view : views) {

            removeAndRecycleView(view, recycler);
        }
    }


    ;
}



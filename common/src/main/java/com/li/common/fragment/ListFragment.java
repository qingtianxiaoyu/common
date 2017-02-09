package com.li.common.fragment;

import android.view.View;
import android.widget.ListView;

/**
 * Created by liweifa on 2016/10/20.
 */

public class ListFragment extends android.support.v4.app.ListFragment {

    OnItemOnClickListener onItemOnClickListener;
    int type = 0;


    public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.onItemOnClickListener = onItemOnClickListener;
    }


    public void setType(int type) {
        this.type = type;
    }

    public ListFragment() {
        super();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(onItemOnClickListener!=null){
        onItemOnClickListener.onItemOnClick(position, type);
        }
    }

    public interface OnItemOnClickListener {
        void onItemOnClick(int position, int type);
    }
}

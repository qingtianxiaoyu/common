package com.li.common.widget;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;
import java.util.Map;

/**
 * Created by liweifa on 2016/11/29.
 */

public class SimpleTableAdapter extends RecyclerView.Adapter<SimpleTableAdapter.SimpleTableViewHolder> implements View.OnClickListener {
    Context mContext;
    @LayoutRes
    int[] mLayoutId;
    private int[] mTo;
    private String[] mFrom;
    List<? extends Map<String, ?>> mData;
    LayoutInflater mInflater;
    int mTotalColumn = -1;
    OnItemOnClickListener mOnItemOnClickListener;


    public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.mOnItemOnClickListener = onItemOnClickListener;
    }

    public SimpleTableAdapter(Context context, int totalColumn, @LayoutRes int[] layoutId, String[] from, @IdRes int[] to, List<? extends Map<String, ?>> data) {
        mContext = context;
        mLayoutId = layoutId;
        mTo = to;
        mFrom = from;
        mData = data;
        mTotalColumn = totalColumn;
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getItemViewType(int position) {
        if (mTotalColumn > 0) {
            return position % mTotalColumn;
        }
        return super.getItemViewType(position);
    }

    @Override
    public SimpleTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType >= mLayoutId.length) {
            return new SimpleTableViewHolder(mInflater.inflate(mLayoutId[mLayoutId.length - 1], parent, false), this);
        } else {
            return new SimpleTableViewHolder(mInflater.inflate(mLayoutId[viewType], parent, false), this);
        }
    }

    @Override
    public void onBindViewHolder(SimpleTableViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (mOnItemOnClickListener != null) {
            mOnItemOnClickListener.onItemOnClick(position, view);
        }

    }

    public class SimpleTableViewHolder extends RecyclerView.ViewHolder {
        View.OnClickListener onClickListener;

        public SimpleTableViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;
        }

        public void setData(Map<String, ?> map) {

            final String[] from = mFrom;
            final int[] to = mTo;
            final int count = to.length;
            for (int i = 0; i < count; i++) {
                final View v = itemView.findViewById(to[i]);
                if (v != null) {
                    final Object data = map.get(from[i]);
                    String text = data == null ? "" : data.toString();
                    if (text == null) {
                        text = "";
                    }


                    if (v instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                        } else if (v instanceof TextView) {
                            // Note: keep the instanceof TextView check at the bottom of these
                            // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                            setViewText((TextView) v, text);
                        } else {
                            throw new IllegalStateException(v.getClass().getName() +
                                    " should be bound to a Boolean, not a " +
                                    (data == null ? "<unknown type>" : data.getClass()));
                        }
                    } else if (v instanceof TextView) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        setViewText((TextView) v, text);
                    } else if (v instanceof ImageView) {
                        if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);
                        } else {
                            setViewImage((ImageView) v, text);
                        }
                    } else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                " view that can be bounds by this SimpleAdapter");
                    }
                }
            }


            itemView.setTag(getLayoutPosition());
            if (onClickListener != null) {
                itemView.setOnClickListener(onClickListener);

            }
        }

        /**
         * Called by bindView() to set the image for an ImageView but only if
         * there is no existing ViewBinder or if the existing ViewBinder cannot
         * handle binding to an ImageView.
         * <p>
         * This method is called instead of {@link #setViewImage(ImageView, String)}
         * if the supplied data is an int or Integer.
         *
         * @param v     ImageView to receive an image
         * @param value the value retrieved from the data set
         * @see #setViewImage(ImageView, String)
         */
        public void setViewImage(ImageView v, int value) {
            v.setImageResource(value);
        }

        /**
         * Called by bindView() to set the image for an ImageView but only if
         * there is no existing ViewBinder or if the existing ViewBinder cannot
         * handle binding to an ImageView.
         * <p>
         * By default, the value will be treated as an image resource. If the
         * value cannot be used as an image resource, the value is used as an
         * image Uri.
         * <p>
         * This method is called instead of {@link #setViewImage(ImageView, int)}
         * if the supplied data is not an int or Integer.
         *
         * @param v     ImageView to receive an image
         * @param value the value retrieved from the data set
         * @see #setViewImage(ImageView, int)
         */
        public void setViewImage(ImageView v, String value) {
            try {
                v.setImageResource(Integer.parseInt(value));
            } catch (NumberFormatException nfe) {
                v.setImageURI(Uri.parse(value));
            }
        }

        /**
         * Called by bindView() to set the text for a TextView but only if
         * there is no existing ViewBinder or if the existing ViewBinder cannot
         * handle binding to a TextView.
         *
         * @param v    TextView to receive text
         * @param text the text to be set for the TextView
         */
        public void setViewText(TextView v, String text) {
            v.setText(text);
        }


        public String getText() {

            return "";
        }
    }

}




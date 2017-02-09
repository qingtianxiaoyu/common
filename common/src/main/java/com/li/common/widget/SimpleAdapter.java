package com.li.common.widget;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liweifa on 2016/11/24.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> implements View.OnClickListener {
    private final LayoutInflater mInflater;

    private int[] mTo;
    private String[] mFrom;

    private List<? extends Map<String, ?>> mData;

    private int mResource;

    OnItemOnClickListener onItemOnClickListener;

    public SimpleAdapter(Context context, List<? extends Map<String, ?>> data,
                         @LayoutRes int resource, String[] from, @IdRes int[] to) {
        mData = data;
        mResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.onItemOnClickListener = onItemOnClickListener;
    }

    public SimpleAdapter(Context context, @LayoutRes int resource, @IdRes int id, List<String> data) {
        mResource = resource;
        mTo = new int[]{id};
        mFrom = new String[]{"data"};
        mData = new ArrayList<>();
        ArrayList<ArrayMap<String, String>> array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ArrayMap<String, String> map = new ArrayMap<>();
            map.put("data", data.get(i));
            array.add(map);
        }
        mData = array;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SimpleViewHolder(mInflater.inflate(mResource, parent, false), this);
    }


    @Override
    public void onViewRecycled(SimpleViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (onItemOnClickListener != null) {
            onItemOnClickListener.onItemOnClick(position, v);
        }

    }

    protected class SimpleViewHolder extends BaseViewHolder {
        View.OnClickListener onClickListener;

        public SimpleViewHolder(View itemView, View.OnClickListener onClickListener) {
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

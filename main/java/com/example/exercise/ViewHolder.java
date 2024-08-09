package com.example.exercise;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    private SparseArray<View> mViews;
    private View item;
    private int position;
    private Context context;

    private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
        mViews = new SparseArray<>();
        this.context = context;
        View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        convertView.setTag(this);
        item = convertView;
    }

    public static ViewHolder bind(Context context, View convertView, ViewGroup parent, int layoutRes, int position) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(context, parent, layoutRes);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.item = convertView;
        }
        holder.position = position;
        return holder;
    }

    public <T extends View> T getView(int id) {
        T t = (T) mViews.get(id);
        if (t == null) {
            t = (T) item.findViewById(id);
            mViews.put(id, t);
        }
        return t;
    }

    public View getItemView() {
        return item;
    }

    public int getItemPosition() {
        return position;
    }

    public ViewHolder setImageResource(int id, int drawableRes) {
        View view = getView(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(drawableRes);
        } else {
            view.setBackgroundResource(drawableRes);
        }
        return this;
    }

    public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
        getView(id).setOnClickListener(listener);
        return this;
    }

    public ViewHolder setVisibility(int id, int visible){
        getView(id).setVisibility(visible);
        return this;
    }

    public ViewHolder setTag (int id, Object obj){
        getView(id).setTag(obj);
        return this;
    }

    public void setText (int id, CharSequence text){
        View view = getView(id);
        if (view instanceof TextView) {
            ((TextView)view).setText(text);
        }
    }
}

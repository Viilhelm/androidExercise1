package com.example.exercise;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class MyAdapterBase<T> extends BaseAdapter {

    private ArrayList<T> dataList;
    private int mLayoutRes;

    public MyAdapterBase(){
    }

    public MyAdapterBase(ArrayList<T> dataList, int mLayoutRes) {
        this.dataList = dataList;
        this.mLayoutRes = mLayoutRes;
    }
    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes, position);
        bindView(holder,getItem(position));
        return holder.getItemView();
    }

    public abstract void bindView(ViewHolder holder, T obj);

    public void add(T data) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.add(data);
        notifyDataSetChanged();
    }

    public void add(T data, int position) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(T data) {
        if (dataList != null) {
            dataList.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (dataList != null) {
            dataList.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (dataList != null) {
            dataList.clear();
        }
        notifyDataSetChanged();
    }


}



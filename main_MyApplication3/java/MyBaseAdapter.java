import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> dataList;
    protected Context context;
    protected LayoutInflater inflater;

    public MyBaseAdapter() {
    }

    public MyBaseAdapter(Context context, List<T> dataList){
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
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
        if (convertView == null) {
            convertView = createView(position, parent);
        }
        bindView(position, convertView, getItem(position));
        return convertView;
    }

    protected abstract View createView(int position, ViewGroup parent);

    protected abstract void bindView(int position, View view, T item);
}

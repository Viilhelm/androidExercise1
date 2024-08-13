import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StringAdapter extends MyBaseAdapter<String>{
    public StringAdapter() {
    }

    public StringAdapter(Context context, List<String> dataList){
        super(context, dataList);
    }

    @Override
    protected View createView(int position, ViewGroup parent) {
        return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    protected void bindView(int position, View view, String item) {
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(item);
    }
}

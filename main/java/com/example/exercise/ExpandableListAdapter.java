package com.example.exercise;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private Map<String, Map<Integer, Boolean>> checkBoxStates = new HashMap<>();

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;

        for (String header : listDataHeader) {
            checkBoxStates.put(header, new HashMap<>());
        }

    }
    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listHashMap.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView listHeader = (TextView) convertView.findViewById(R.id.list_header);
        listHeader.setText(headerTitle);
        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.detail_list_item, null);
        }

        TextView textView = convertView.findViewById(R.id.child_text_view);
        CheckBox checkBox = convertView.findViewById(R.id.checkbox_item);

        String headerTitle = (String) getGroup(groupPosition);
        // 使用 groupPosition, childPosition 和 TestItem 的标题生成唯一键
        String uniqueKey = headerTitle + "_" + groupPosition + "_" + childPosition + "_" + childText;
        SharedPreferences preferences = context.getSharedPreferences("CheckBoxPrefs", Context.MODE_PRIVATE);

        if (childText.equals("正常")) {
            textView.setText(childText);
            textView.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.GONE);

            textView.setOnClickListener(v -> {
                clearAllCheckBoxes(groupPosition);

                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
            });

        } else {
            checkBox.setText(childText);
            checkBox.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

            // 恢复 CheckBox 状态
            checkBox.setOnCheckedChangeListener(null);
            boolean isChecked = preferences.getBoolean(uniqueKey, false);
            checkBox.setChecked(isChecked);

            // 更新 CheckBox 状态
            checkBox.setOnCheckedChangeListener((buttonView, isCheckedState) -> {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(uniqueKey, isCheckedState);
                editor.apply();
            });
        }
        return convertView;


    }

    private void clearAllCheckBoxes(int groupPosition) {
        String headerTitle = (String) getGroup(groupPosition);
        SharedPreferences preferences = context.getSharedPreferences("CheckBoxPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for (int i = 0; i < getChildrenCount(groupPosition); i++) {
            String childText = (String) getChild(groupPosition, i);
            String uniqueKey = headerTitle + "_" + groupPosition + "_" + i + "_" + childText;
            editor.putBoolean(uniqueKey, false);
        }

        editor.apply();
        notifyDataSetChanged();

    }




}

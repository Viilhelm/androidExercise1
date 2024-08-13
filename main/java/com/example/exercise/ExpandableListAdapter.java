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
import java.util.Objects;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private List<CheckBoxWrapper> allCheckBoxes = new ArrayList<>();

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
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

        // 独立的 SharedPreferences 文件名
        String prefsName = "CheckBoxPrefs_" + groupPosition + "_" + childPosition + "_" + context.getClass().getSimpleName();
        SharedPreferences preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);

        if (childText.equals("正常")) {
            textView.setText(childText);
            textView.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.GONE);

            textView.setOnClickListener(v -> {
                clearAllCheckBoxes(groupPosition); // 清除当前组的 CheckBox 状态
                //updateUIForSelection(groupPosition, childPosition, "green");

                // 清除状态后，保存到 SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
            });

        } else {
            checkBox.setText(childText);
            checkBox.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

            // 将 CheckBox 与其对应的 groupPosition 和 childPosition 关联存储
            CheckBoxWrapper wrapper = new CheckBoxWrapper(groupPosition, childPosition, checkBox);

            if (!allCheckBoxes.contains(wrapper)) {
                allCheckBoxes.add(wrapper);
            }

            boolean isChecked = preferences.getBoolean("checkbox_state", false);

            // 恢复 CheckBox 状态
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(isChecked);

            checkBox.setOnCheckedChangeListener((buttonView, isCheckedState) -> {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("checkbox_state", isCheckedState);
                editor.apply();
            });
        }
        return convertView;
    }

    private void clearAllCheckBoxes(int groupPosition) {
        // 只清除当前组的 CheckBox 状态
        for (CheckBoxWrapper wrapper : allCheckBoxes) {
            if (wrapper.getGroupPosition() == groupPosition) {
                String prefsName = "CheckBoxPrefs_" + groupPosition + "_" + wrapper.getChildPosition() + "_" + context.getClass().getSimpleName();
                SharedPreferences preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                wrapper.getCheckBox().setOnCheckedChangeListener(null);
                wrapper.getCheckBox().setChecked(false);
            }
        }
    }

    // 包装 CheckBox 和其对应的 groupPosition 和 childPosition
    private static class CheckBoxWrapper {
        private final int groupPosition;
        private final int childPosition;
        private final CheckBox checkBox;

        public CheckBoxWrapper(int groupPosition, int childPosition, CheckBox checkBox) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
            this.checkBox = checkBox;
        }

        public int getGroupPosition() {
            return groupPosition;
        }

        public int getChildPosition() {
            return childPosition;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CheckBoxWrapper that = (CheckBoxWrapper) o;
            return groupPosition == that.groupPosition &&
                    childPosition == that.childPosition &&
                    checkBox.equals(that.checkBox);
        }

        @Override
        public int hashCode() {
            return Objects.hash(groupPosition, childPosition, checkBox);
        }
    }

}

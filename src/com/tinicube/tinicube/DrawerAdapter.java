package com.tinicube.tinicube;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class DrawerAdapter extends BaseExpandableListAdapter{
	private Context mContext;
	private ArrayList<DrawerGroup> mDrawerGroupList;
	
	public DrawerAdapter(Context context, ArrayList<DrawerGroup> drawerGroupList) {
		super();
		this.mContext = context;
		this.mDrawerGroupList = drawerGroupList;
	}

	@Override
	public int getGroupCount() {
		return mDrawerGroupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mDrawerGroupList.get(groupPosition).getDrawerItemList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mDrawerGroupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mDrawerGroupList.get(groupPosition).getDrawerItemList().get(childPosition);
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
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.drawer_group, null);
			
			DrawerGroup curGroup = mDrawerGroupList.get(groupPosition);
			TextView tvTitle = (TextView) convertView.findViewById(R.id.tvDrawerGroupTitle);
			tvTitle.setText(curGroup.getTitle());
		} 
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.drawer_item, null);
			
			DrawerGroup curGroup = mDrawerGroupList.get(groupPosition);
			DrawerItem curItem = curGroup.getDrawerItemList().get(childPosition);
			TextView tvTitle = (TextView) convertView.findViewById(R.id.tvDrawerItemTitle);
			tvTitle.setText(curItem.getTitle());
		} 
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}

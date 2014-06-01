package com.tinicube.tinicube;

import java.util.ArrayList;

import com.tinicube.tinicube.common.C;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import arcanelux.library.baseclass.BaseArrayAdapter;

public class DrawerAdapter extends BaseExpandableListAdapter {
	protected final String TAG = this.getClass().getName();
	private String mFontFileName = "NanumBarunGothic.mp3";
	private boolean hasCustomFontFile = false;
	private static Typeface mTypeface;
	
	private Context mContext;
	private ArrayList<DrawerGroup> mDrawerGroupList;
	
	public DrawerAdapter(Context context, ArrayList<DrawerGroup> drawerGroupList) {
		super();
		this.mContext = context;
		this.mDrawerGroupList = drawerGroupList;
		this.hasCustomFontFile = true;
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
			convertView = inflateWithCustomFont(inflater, R.layout.drawer_group, C.CUSTOM_FONT_TITILLIUM_1);
			
			DrawerGroup curGroup = mDrawerGroupList.get(groupPosition);
			TextView tvTitle = (TextView) convertView.findViewById(R.id.tvDrawerGroupTitle);
			tvTitle.setText(curGroup.getTitle());
		} 
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflateWithCustomFont(inflater, R.layout.drawer_item);
		
		DrawerGroup curGroup = mDrawerGroupList.get(groupPosition);
		DrawerItem curItem = curGroup.getDrawerItemList().get(childPosition);
		TextView tvTitle = (TextView) view.findViewById(R.id.tvDrawerItemTitle);
		tvTitle.setText(curItem.getTitle());
		return view;
//		View view;
//		if(convertView == null) {
//			
//		} else{
//			view = convertView;
//		}
//		
//		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	
	/** Inflate시 CustomFont를 적용한 View를 리턴해주는 함수 **/
	// inflater, layoutRes
	protected View inflateWithCustomFont(LayoutInflater inflater, int layoutRes){
		return inflateWithCustomFont(inflater, null, layoutRes, mFontFileName);
	}
	// inflater, layoutRes, fontFileName
		protected View inflateWithCustomFont(LayoutInflater inflater, int layoutRes, String fontFileName){
			return inflateWithCustomFont(inflater, null, layoutRes, fontFileName);
		}
	// inflater, container, layoutRes
	protected View inflateWithCustomFont(LayoutInflater inflater, ViewGroup container, int layoutRes){
		return inflateWithCustomFont(inflater, container, layoutRes, mFontFileName);
	}
	// inflater, container, layoutRes, fontFileName
	protected View inflateWithCustomFont(LayoutInflater inflater, ViewGroup container, int layoutRes, String fontFileName) {
		View view = inflater.inflate(layoutRes, container, false);
		if(!hasCustomFontFile){
			Log.e(TAG, "FontFileName Missing!");
		} else{
			if (DrawerAdapter.mTypeface == null)
				DrawerAdapter.mTypeface = Typeface.createFromAsset(mContext.getAssets(), fontFileName);
			setGlobalFont((ViewGroup) view);
		}
		return view;
		
	}

	void setGlobalFont(ViewGroup root) {
		for (int i = 0; i < root.getChildCount(); i++) {
			View child = root.getChildAt(i);
			if (child instanceof TextView){
				((TextView)child).setTypeface(mTypeface);
			}
			else if (child instanceof ViewGroup){
				setGlobalFont((ViewGroup)child);
			}
		}
	}

}

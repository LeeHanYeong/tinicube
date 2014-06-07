package com.tinicube.tinicube;

import java.util.ArrayList;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.tinicube.base.function.BASE_C;

public class DrawerAdapter extends BaseExpandableListAdapter {
	protected final String TAG = this.getClass().getName();
	private String mFontFileName = "NanumBarunGothic.mp3";
	private boolean hasCustomFontFile = false;
	private static Typeface mTypeface;
	
	private Context mContext;
	protected AQuery aq;
	private ArrayList<DrawerGroup> mDrawerGroupList;
	
	private DataSetObservable dataSetObservable = new DataSetObservable();
	
	public DrawerAdapter(Context context, ArrayList<DrawerGroup> drawerGroupList) {
		super();
		this.mContext = context;
		this.mDrawerGroupList = drawerGroupList;
		this.hasCustomFontFile = true;
		this.aq = new AQuery(mContext);
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
			convertView = inflateWithCustomFont(inflater, R.layout.drawer_group, BASE_C.CUSTOM_FONT_TITILLIUM_1);
			
			DrawerGroup curGroup = mDrawerGroupList.get(groupPosition);
			TextView tvTitle = (TextView) convertView.findViewById(R.id.tvDrawerGroupTitle);
			tvTitle.setText(curGroup.getTitle());
			
			ImageView ivIcon = (ImageView) convertView.findViewById(R.id.ivDrawerGroupIcon);
			if(curGroup.isHasResThumbnail()){
				int resIcon = curGroup.getResThumbnail();
				ivIcon.setImageResource(resIcon);
			} else if(curGroup.isHasUrlThumbnail()){
				String urlIcon = curGroup.getUrlThumbnail();
				aq.id(ivIcon).image(urlIcon);
			}
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
		return true;
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


	
//	private DataSetObservable getDataSetObservable(){
//		return dataSetObservable;
//	}
//	@Override
//	public void notifyDataSetChanged() {
////		super.notifyDataSetChanged();
//		this.getDataSetObservable().notifyChanged();
//	}
//	@Override
//	public void notifyDataSetInvalidated() {
////		super.notifyDataSetInvalidated();
//		this.getDataSetObservable().notifyInvalidated();
//	}
//
//	@Override
//	public void registerDataSetObserver(DataSetObserver observer) {
////		super.registerDataSetObserver(observer);
//		this.getDataSetObservable().registerObserver(observer);
//	}
//
//	@Override
//	public void unregisterDataSetObserver(DataSetObserver observer) {
////		super.unregisterDataSetObserver(observer);
//		this.getDataSetObservable().unregisterObserver(observer);
//	}
	

}

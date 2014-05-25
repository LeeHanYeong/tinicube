package com.tinicube.tinicube;

import java.util.ArrayList;

import android.support.v4.widget.DrawerLayout.DrawerListener;

public class DrawerGroup extends DrawerItem {
	private ArrayList<DrawerItem> drawerItemList;
	private boolean hasItem = false;	
	
	public DrawerGroup(String urlThumbnail, String title, int value) {
		super(urlThumbnail, title, value);
		this.hasItem = false;
	}
	
	public DrawerGroup(String title, int value) {
		super(title, value);
		this.hasItem = false;
	}

	public DrawerGroup(String urlThumbnail, String title, int value, ArrayList<DrawerItem> drawerItemList) {
		super(urlThumbnail, title, value);
		this.drawerItemList = drawerItemList;
		this.hasItem = true;
	}
	
	public DrawerGroup(String title, int value, ArrayList<DrawerItem> drawerItemList) {
		super(title, value);
		this.drawerItemList = drawerItemList;
		this.hasItem = true;
	}
	
	public void addDrawerItem(DrawerItem item){
		if(this.drawerItemList == null || this.drawerItemList.size() == 0){
			this.drawerItemList = new ArrayList<DrawerItem>();
			this.hasItem = true;
		}
		drawerItemList.add(item);
	}

	public ArrayList<DrawerItem> getDrawerItemList() {
		return drawerItemList;
	}

	public boolean isHasItem() {
		return hasItem;
	}
	
}

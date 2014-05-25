package com.tinicube.tinicube.data;

import java.util.ArrayList;

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

	public ArrayList<DrawerItem> getDrawerItemList() {
		return drawerItemList;
	}

	public boolean isHasItem() {
		return hasItem;
	}
	
}

package com.tinicube.tinicube.data;

import java.util.ArrayList;

public class DrawerGroup {
	private String urlThumbnail;
	private String title;
	private int value;
	private boolean hasItem = false;
	private ArrayList<DrawerItem> drawerItemList;
	
	public DrawerGroup(String urlThumbnail, String title, int value) {
		super();
		this.urlThumbnail = urlThumbnail;
		this.title = title;
		this.value = value;
		this.hasItem = false;
	}
	public DrawerGroup(String urlThumbnail, String title, int value, ArrayList<DrawerItem> drawerItemList) {
		super();
		this.urlThumbnail = urlThumbnail;
		this.title = title;
		this.value = value;
		this.drawerItemList = drawerItemList;
		this.hasItem = true;
	}
	
	
	
	
	
}

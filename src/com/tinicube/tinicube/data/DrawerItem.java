package com.tinicube.tinicube.data;


public class DrawerItem {
	protected String urlThumbnail;
	protected boolean hasThumbnail = false;
	protected String title;
	protected int value;
	
	public DrawerItem(String urlThumbnail, String title, int value) {
		super();
		this.urlThumbnail = urlThumbnail;
		this.title = title;
		this.value = value;
		this.hasThumbnail = true;
	}
	public DrawerItem(String title, int value) {
		super();
		this.title = title;
		this.value = value;
		this.hasThumbnail = false;
	}
	public String getUrlThumbnail() {
		return urlThumbnail;
	}
	public boolean isHasThumbnail() {
		return hasThumbnail;
	}
	public String getTitle() {
		return title;
	}
	public int getValue() {
		return value;
	}
	
	
}

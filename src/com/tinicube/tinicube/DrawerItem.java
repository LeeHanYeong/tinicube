package com.tinicube.tinicube;


public class DrawerItem {
	protected String urlThumbnail;
	protected int resThumbnail;
	protected boolean hasResThumbnail = false;
	protected boolean hasUrlThumbnail = false;
	protected String title;
	protected int value;
	
	public DrawerItem(String urlThumbnail, String title, int value) {
		super();
		this.urlThumbnail = urlThumbnail;
		this.title = title;
		this.value = value;
		this.hasUrlThumbnail = true;
	}
	public DrawerItem(int resThumbnail, String title, int value) {
		this.resThumbnail = resThumbnail;
		this.title = title;
		this.value = value;
		this.hasResThumbnail = true;
	}
	public DrawerItem(String title, int value) {
		super();
		this.title = title;
		this.value = value;
	}
	
	public int getResThumbnail() {
		return resThumbnail;
	}
	public String getUrlThumbnail() {
		return urlThumbnail;
	}
	
	

	public boolean isHasResThumbnail() {
		return hasResThumbnail;
	}
	public boolean isHasUrlThumbnail() {
		return hasUrlThumbnail;
	}
	public String getTitle() {
		return title;
	}
	public int getValue() {
		return value;
	}
	
	
}

package com.tinicube.tinicube.data;


public class DrawerItem {
	private String urlThumbnail;
	private String title;
	private int value;
	
	public DrawerItem(String urlThumbnail, String title, int value) {
		super();
		this.urlThumbnail = urlThumbnail;
		this.title = title;
		this.value = value;
	}

	public String getUrlThumbnail() {
		return urlThumbnail;
	}

	public void setUrlThumbnail(String urlThumbnail) {
		this.urlThumbnail = urlThumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}

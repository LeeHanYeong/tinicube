package com.tinicube.tinicube.common;

public class C {
	
	
	/**
	 * NavigationDrawer 관련 변수
	 */
	public static final int DRAWERGROUP_CATEGORY = 9998;
	public static final int DRAWERGROUP_SETTING = 9999;
	
	public static final int DRAWERITEM_HOME = 10000;
	public static final int DRAWERITEM_ALL_WORKS = 10001;
	public static final int DRAWERITEM_ALL_AUTHORS = 10002;
	public static final int DRAWERITEM_FAVORITE_WORKS = 10003;
	public static final int DRAWERITEM_FAVORITE_AUTHORS = 10004;
	public static final int DRAWERITEM_SIGNIN = 10005;
	public static final int DRAWERITEM_SIGNOUT = 10006;
	public static final int DRAWERITEM_SETTING = 10007;
	

	/**
	 * API URL
	 */
	public static final String URL_BASE = "http://192.168.56.1:8000";
	public static final String API_BASE = URL_BASE + "/api/";
	public static final String API_RECENT_UPDATE_CHAPTER_LIST = API_BASE + "tinicube/chapter/recentupdatelist/";
	public static final String API_NEWWORK_LIST = API_BASE + "tinicube/work/newlist/";
	public static final String API_POPWORK_LIST = API_BASE + "tinicube/work/poplist/";
	public static final String API_NEWAUTHOR_LIST = API_BASE + "tinicube/author/newlist/";
	public static final String API_POPAUTHOR_LIST = API_BASE + "tinicube/author/poplist/";
	
	/**
	 * Pref에 저장할 JSONObject 이름
	 */
	public static final String JSONDATA_RECENT_UPDATE_CHAPTER_LIST = "jsondata_recent_update_chapter_list";
	public static final String JSONDATA_NEWWORK_LIST = "jsondata_newwork_list";
	public static final String JSONDATA_POPWORK_LIST = "jsondata_popwork_list";
	public static final String JSONDATA_NEWAUTHOR_LIST = "jsondata_newauthor_list";
	public static final String JSONDATA_POPAUTHOR_LIST = "jsondata_popauthor_list";
}

package com.tinicube.tinicube;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import arcanelux.library.activity.AdlibrActionBarActivity;
import arcanelux.library.baseclass.BaseAsyncTask;

import com.tinicube.base.data.DataUser;
import com.tinicube.base.function.BASE_C;
import com.tinicube.base.function.BASE_Pref;
import com.tinicube.base.login.TiniCubeLoginActivity;
import com.tinicube.tinicube.authorlist.AuthorListFragment;
import com.tinicube.tinicube.common.C;
import com.tinicube.tinicube.common.Pref;
import com.tinicube.tinicube.main.MainFragment;

public class MainActivity extends AdlibrActionBarActivity {
	public static boolean confirmFinish = false;
	
	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerView;
	private ExpandableListView mDrawerList;
	private ArrayList<DrawerGroup> mDrawerGroupList;
	private DrawerAdapter mDrawerAdapter;
	private ActionBarDrawerToggle mDrawerToggle;

	// DrawerViewUserInfo
	private View mDrawerViewUserInfo;
	private TextView tvNickname, tvEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		showAd = false;
		super.onCreate(savedInstanceState);
		useCustomFont(BASE_C.CUSTOM_FONT_FILE_NAME);
		setContentView(R.layout.main_activity);

		// ActionBar 설정
		mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background)));
		mActionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setDisplayUseLogoEnabled(false);
		View viewActionBar = inflateWithCustomFont(mInflater, R.layout.actionbar_main, BASE_C.CUSTOM_FONT_TITILLIUM_2);
		mActionBar.setCustomView(viewActionBar);
		mActionBar.setTitle("");

		// DrawerViewUserInfo 설정
		mDrawerViewUserInfo = (View) findViewById(R.id.navigationDrawerViewUserInfo);
		tvNickname = (TextView) mDrawerViewUserInfo.findViewById(R.id.tvNavigationDrawerUserInfoNickname);
		tvEmail = (TextView) mDrawerViewUserInfo.findViewById(R.id.tvNavigationDrawerUserInfoEmail);

		if(isLogin()){
			DataUser curLoginUser = new DataUser(BASE_Pref.getLoginUserInfoJSONObject(mContext));
			String nickname = curLoginUser.getNickname();
			String email = curLoginUser.getUsername();

			tvNickname.setText(nickname);
			tvEmail.setText(email);
			mDrawerViewUserInfo.setVisibility(View.VISIBLE);
		} else {
			mDrawerViewUserInfo.setVisibility(View.GONE);
		}

		// DrawerGroup, DrawerItem 설정
		setCategoryItem();

		// NavigationDrawer 설정
		//		mDrawerLayout : Drawer,Activity전체
		//		mDrawerView : Drawer전체
		//		mDrawerViewUserInfo : Drawer상단의 UserInfo. 비로그인 시 visibility를 gone으로 설정
		//		mDrawerList : Drawer의 List부분)
		mDrawerLayout = (DrawerLayout) findViewById(R.id.navigationDrawerLayout);
		mDrawerView = (LinearLayout) findViewById(R.id.navigationDrawerView);
		mDrawerViewUserInfo = (View) findViewById(R.id.navigationDrawerViewUserInfo);
		mDrawerViewUserInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectItem(C.DRAWERITEM_USERINFO);
			}
		});
		mDrawerList = (ExpandableListView) findViewById(R.id.navigationDrawerLeftDrawer);
		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// NavigationDrawer의 ListView에 Adapter 연결, 클릭리스너 할당
		//		for(int i=0; i<mDrawerGroupList.size(); i++){
		//			Log.d(TAG, "DrawerGroup [" + i + "]");
		//			for(int j=0; i<mDrawerGroupList.get(i).getDrawerItemList().size(); j++){
		//				Log.d(TAG, "DrawerItem (" + i + ", " + j + ")");
		//			}
		//		}
		mDrawerAdapter = new DrawerAdapter(mContext, mDrawerGroupList);
		mDrawerList.setAdapter(mDrawerAdapter);
		mDrawerList.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
		expandGroup();

		// ActionBar AppIcon으로 NavigationDrawer 작동하도록 설정
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(C.DRAWERITEM_HOME);
		}
	}

	// NavigationDrawer Group Click Listener
	private class DrawerGroupClickListener implements ExpandableListView.OnGroupClickListener {

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
			Log.d(TAG, "Group Click, GroupPos : " + groupPosition);
			DrawerGroup curGroup = mDrawerGroupList.get(groupPosition);
			return false;
		}
	}

	// NavigationDrawer Item Click Listener	
	private class DrawerItemClickListener implements ExpandableListView.OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			Log.d(TAG, "Chilc Click, GroupPos : " + groupPosition + ", ChildPos : " + childPosition);
			DrawerGroup curGroup = mDrawerGroupList.get(groupPosition);
			DrawerItem curItem = curGroup.getDrawerItemList().get(childPosition);

			// Category 그룹
			if(curGroup.getTitle().equals("Category")){
				if(curItem.getTitle().equals("홈")){
					selectItem(C.DRAWERITEM_HOME);
				} else if(curItem.getTitle().equals("전체 작품 보기")){
					selectItem(C.DRAWERITEM_ALL_WORKS);
				} else if(curItem.getTitle().equals("전체 작가 보기")){
					selectItem(C.DRAWERITEM_ALL_AUTHORS);
				} else if(curItem.getTitle().equals("즐겨찾는 작품")){
					selectItem(C.DRAWERITEM_FAVORITE_WORKS);
				} else if(curItem.getTitle().equals("즐겨찾는 작가")){
					selectItem(C.DRAWERITEM_FAVORITE_AUTHORS);
				}
			}
			// Settings 그룹
			if(curGroup.getTitle().equals("Settings")){
				if(curItem.getTitle().equals("로그인")){
					selectItem(C.DRAWERITEM_SIGNIN);
				} else if(curItem.getTitle().equals("로그아웃")){
					selectItem(C.DRAWERITEM_SIGNOUT);
				} else if(curItem.getTitle().equals("설정")){
					selectItem(C.DRAWERITEM_SETTING);
				}
			}
			return false;
		}
	}
	/** 
	 * NavigationDrawer의 아이템 클릭시 발생하는 이벤트
	 * 해당 Position의 Fragment를 만들어주며, 클릭 시 Drawer를 닫아줌
	 */
	private void selectItem(int position) {
		switch(position){
		case C.DRAWERITEM_USERINFO:
			
			break;
		case C.DRAWERITEM_HOME:
			MainFragment fragment = new MainFragment(mContext, BASE_C.CUSTOM_FONT_FILE_NAME);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

			// update selected item and title, then close the drawer			
			mDrawerList.setItemChecked(position, true);
			closeDrawer();
			break;
		case C.DRAWERITEM_ALL_WORKS:
			break;
		case C.DRAWERITEM_ALL_AUTHORS:
			new AuthorListInitializeTask(mContext, BASE_C.API_ALL_AUTHOR_LIST, "작가 목록을 불러오는 중입니다...", "전체 작가 목록", "전체 작가 목록입니다").execute();
			break;
		case C.DRAWERITEM_FAVORITE_WORKS:
			break;
		case C.DRAWERITEM_FAVORITE_AUTHORS:
			new AuthorListInitializeTask(mContext, BASE_C.API_FAVORITE_AUTHOR_LIST, "작가 목록을 불러오는 중입니다...", "즐겨찾는 작가 목록", "즐겨찾는 작가 목록입니다").execute();
			break;
		case C.DRAWERITEM_SIGNIN:
			Intent intent = new Intent(MainActivity.this, TiniCubeLoginActivity.class);
			startActivityForResult(intent, BASE_C.REQ_LOGIN);
			break;
		case C.DRAWERITEM_SIGNOUT:			
			logout();
			closeDrawer();
			Toast.makeText(mContext, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
			break;
		case C.DRAWERITEM_SETTING:
			break;
		}		
		//		ComicListFragment fragment = null;
		//		if(position == 0){
		//			DataCategory1 curCategory1 = mDescription.getDataCategory1List().get(0);
		//			fragment = new ComicListFragment("업데이트 순으로 보기", curCategory1.getDescription(), BASE_C.URL_BASE + curCategory1.getCategory1LogoImage().getUrl(), mDescription.getPostList());
		//		} else{
		//			position = position - 1;
		//			DataCategory1 curCategory1 = mDataCategory1List.get(position);
		//			Log.d(TAG, curCategory1.getTitle());
		//			
		//			fragment = new ComicListFragment(curCategory1.getTitle(), curCategory1.getDescription(), BASE_C.URL_BASE + curCategory1.getCategory1LogoImage().getUrl(), curCategory1.getPostList());				
		//		}
		//		FragmentManager fragmentManager = getSupportFragmentManager();
		//		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		//
		//		// update selected item and title, then close the drawer			
		//		mDrawerList.setItemChecked(position, true);
		//		mDrawerLayout.closeDrawer(mDrawerView);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerView);
		//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/** Actionbar Home/Up 버튼으로 NavigationDrawer 작동시키려면 이 함수 필요 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		// Home/Up 버튼으로 NavigationDrawer 작동시키려면 return true;
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		//        switch(item.getItemId()) {
		//        case R.id.action_websearch:
		//            // create intent to perform web search for this planet
		//            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		//            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
		//            // catch event that there's no activity to handle intent
		//            if (intent.resolveActivity(getPackageManager()) != null) {
		//                startActivity(intent);
		//            } else {
		//                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
		//            }
		//            return true;
		//        default:
		//            return super.onOptionsItemSelected(item);
		//        }
		return super.onOptionsItemSelected(item);
	}


	private boolean isLogin(){
		return BASE_Pref.hasLoginUserInfo(mContext);
	}

	private void setCategoryItem(){
		Log.d(TAG, "isLogin : " + isLogin());
		if(mDrawerGroupList != null){
			mDrawerGroupList.clear(); 
		} else{
			mDrawerGroupList = new ArrayList<DrawerGroup>();	
		}
		
		if(isLogin()){
			DrawerGroup drawerGroupCategory = new DrawerGroup(R.drawable.menu_category, "Category", C.DRAWERGROUP_CATEGORY);
			drawerGroupCategory.addDrawerItem(new DrawerItem("홈", C.DRAWERITEM_HOME));
			drawerGroupCategory.addDrawerItem(new DrawerItem("전체 작품 보기", C.DRAWERITEM_ALL_WORKS));
			drawerGroupCategory.addDrawerItem(new DrawerItem("전체 작가 보기", C.DRAWERITEM_ALL_AUTHORS));
			drawerGroupCategory.addDrawerItem(new DrawerItem("즐겨찾는 작품", C.DRAWERITEM_FAVORITE_WORKS));
			drawerGroupCategory.addDrawerItem(new DrawerItem("즐겨찾는 작가", C.DRAWERITEM_FAVORITE_AUTHORS));
			mDrawerGroupList.add(drawerGroupCategory);

			DrawerGroup drawerGroupSetting = new DrawerGroup(R.drawable.menu_settings, "Settings", C.DRAWERGROUP_SETTING);
			drawerGroupSetting.addDrawerItem(new DrawerItem("로그아웃", C.DRAWERITEM_SIGNOUT));
			drawerGroupSetting.addDrawerItem(new DrawerItem("설정", C.DRAWERITEM_SETTING));
			mDrawerGroupList.add(drawerGroupSetting);
		} else {
			DrawerGroup drawerGroupCategory = new DrawerGroup(R.drawable.menu_category, "Category", C.DRAWERGROUP_CATEGORY);
			drawerGroupCategory.addDrawerItem(new DrawerItem("홈", C.DRAWERITEM_HOME));
			drawerGroupCategory.addDrawerItem(new DrawerItem("전체 작품 보기", C.DRAWERITEM_ALL_WORKS));
			drawerGroupCategory.addDrawerItem(new DrawerItem("전체 작가 보기", C.DRAWERITEM_ALL_AUTHORS));
			mDrawerGroupList.add(drawerGroupCategory);

			DrawerGroup drawerGroupSetting = new DrawerGroup(R.drawable.menu_settings, "Settings", C.DRAWERGROUP_SETTING);
			drawerGroupSetting.addDrawerItem(new DrawerItem("로그인", C.DRAWERITEM_SIGNIN));
			drawerGroupSetting.addDrawerItem(new DrawerItem("설정", C.DRAWERITEM_SETTING));
			mDrawerGroupList.add(drawerGroupSetting);	
		}

		if(mDrawerAdapter != null) {
			Log.d(TAG, "mDrawerAdapter.notifyDataSetChanged");
			mDrawerAdapter.notifyDataSetChanged();
			expandGroup();
		}
	}

	private void expandGroup(){
		// NavigationDrawer의 ExpandableListView에 Expand설정
		for(int i=0; i<mDrawerGroupList.size(); i++){
			mDrawerList.expandGroup(i);	
		}
	}
	
	private void closeDrawer(){
		mDrawerLayout.closeDrawer(mDrawerView);
	}

	private void logout(){
		BASE_Pref.removeLoginInfo(mContext);
		setCategoryItem();
		Toast.makeText(mContext, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
		closeDrawer();
	}

	@Override
	protected void onResume() {
		mDrawerList.setOnGroupClickListener(new DrawerGroupClickListener());
		mDrawerList.setOnChildClickListener(new DrawerItemClickListener());
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		confirmFinish = false;
		switch(requestCode){
		// LoginActivity에 다녀온 후
		case BASE_C.REQ_LOGIN:
			if(resultCode == RESULT_OK){
				String sessionKey = data.getStringExtra("sessionKey");
				String strJsonObjectLoginUserInfo = data.getStringExtra("strJsonObjectLoginUserInfo");
				setCategoryItem();
				Log.d(TAG, "onActivityResult, SessionKey : " + sessionKey);
				Toast.makeText(mContext, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	/**
	 * AuthorListFragment를 생성하는 Task
	 * 		모든 작가 목록은 C.API_ALL_WORK_LIST
	 * 		인기 작가 목록은 C.API_FAVORITE_WORK_LIST
	 */
	class AuthorListInitializeTask extends BaseAsyncTask {
		private String mUrl;
		private String mFragmentTitle, mFragmentDescription;
		private String resultString;

		public AuthorListInitializeTask(Context context, String url, String title, String fragmentTitle, String fragmentDescription) {
			super(context, title);
			this.mUrl = url;
			this.mFragmentTitle = fragmentTitle;
			this.mFragmentDescription = fragmentDescription;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			HashMap<String, String> valuePair = new HashMap<String, String>();
			valuePair.put("username", Pref.getUsername(mContext));
			resultString = postRequest(mUrl, valuePair);
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			try {
				JSONObject jsonObjectAuthorList = new JSONObject(resultString);
				JSONArray jsonArrayAuthorList = jsonObjectAuthorList.getJSONArray("authors");
				
				ArrayList<DataUser> dataUserList = new ArrayList<DataUser>();
				dataUserList = new ArrayList<DataUser>();
				dataUserList.clear();
				for(int i=0; i<jsonArrayAuthorList.length(); i++){
					JSONObject curJsonObjectCurUser = jsonArrayAuthorList.getJSONObject(i);
					DataUser curUser = new DataUser(curJsonObjectCurUser);
					dataUserList.add(curUser);
				}
				
				AuthorListFragment fragment = new AuthorListFragment(mContext, mFragmentTitle, mFragmentDescription, dataUserList, BASE_C.CUSTOM_FONT_FILE_NAME);
				FragmentManager fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

				// update selected item and title, then close the drawer			
//				mDrawerList.setItemChecked(position, true);
				closeDrawer();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void onBackPressed() {
		if(confirmFinish){
			finish();
		} else{
			Toast.makeText(mContext, "뒤로가기 버튼을 한 번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT).show();
			confirmFinish = true;
		}
	}
}

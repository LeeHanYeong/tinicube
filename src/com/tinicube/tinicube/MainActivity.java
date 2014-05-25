package com.tinicube.tinicube;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import arcanelux.library.activity.AdlibrActionBarActivity;

public class MainActivity extends AdlibrActionBarActivity {
	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerView;
	private ExpandableListView mDrawerList;
	private View viewDrawerHeader;
	private ActionBarDrawerToggle mDrawerToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		// NavigationDrawer 설정 (Layout-Drawer, Activity전체, View-Drawer전체, List-Drawer의 List부분)
		mDrawerLayout = (DrawerLayout) findViewById(R.id.navigationDrawerLayout);
		mDrawerView = (LinearLayout) findViewById(R.id.navigationDrawerView);
		mDrawerList = (ExpandableListView) findViewById(R.id.navigationDrawerLeftDrawer);
		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
				
		// NavigationDrawer의 ListView에 Header View추가
//		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		viewDrawerHeader = inflater.inflate(R.layout.drawer_header, null);
//		mDrawerList.addHeaderView(viewDrawerHeader);
		
		// NavigationDrawer의 ListView에 Adapter 연결, 클릭리스너 할당
//		mCategoryAdapter = new CategoryAdapter(mContext, R.layout.drawer_item, R.layout.drawer_item2, mDataCategory1List, mCategory2ClickListener);
//		mDrawerList.setAdapter(mCategoryAdapter);
//		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

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
			selectItem(0);
		}
	}
	
	/** 
	 * NavigationDrawer의 아이템 클릭시 발생하는 이벤트
	 * 해당 Position의 Fragment를 만들어주며, 클릭 시 Drawer를 닫아줌
	 */
	private void selectItem(int position) {
//		ComicListFragment fragment = null;
//		if(position == 0){
//			DataCategory1 curCategory1 = mDescription.getDataCategory1List().get(0);
//			fragment = new ComicListFragment("업데이트 순으로 보기", curCategory1.getDescription(), C.URL_BASE + curCategory1.getCategory1LogoImage().getUrl(), mDescription.getPostList());
//		} else{
//			position = position - 1;
//			DataCategory1 curCategory1 = mDataCategory1List.get(position);
//			Log.d(TAG, curCategory1.getTitle());
//			
//			fragment = new ComicListFragment(curCategory1.getTitle(), curCategory1.getDescription(), C.URL_BASE + curCategory1.getCategory1LogoImage().getUrl(), curCategory1.getPostList());				
//		}
//		FragmentManager fragmentManager = getSupportFragmentManager();
//		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//		// update selected item and title, then close the drawer			
//		mDrawerList.setItemChecked(position, true);
//		mDrawerLayout.closeDrawer(mDrawerList);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

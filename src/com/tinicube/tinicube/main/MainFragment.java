package com.tinicube.tinicube.main;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import arcanelux.library.baseclass.BaseFragment;
import arcanelux.library.baseclass.BasePagerAdapter;

import com.androidquery.callback.ImageOptions;
import com.tinicube.base.data.DataUser;
import com.tinicube.base.function.BASE_C;
import com.tinicube.comicbase.data.work.DataChapter;
import com.tinicube.comicbase.data.work.DataWork;
import com.tinicube.tinicube.R;
import com.tinicube.tinicube.common.C;
import com.tinicube.tinicube.data.DataCoverImage;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class MainFragment extends BaseFragment { 
	// CoverImage ViewPager
	private ViewPager mCoverImageViewPager;
	private ImageCoverPagerAdapter mCoverImagePagerAdapter;
	private PageIndicator mIndicator;
	private ArrayList<DataCoverImage> mCoverImageList;

	// Recent Update Items
	private LinearLayout llRecentUpdate;
	private ArrayList<DataChapter> mRecentUpdateChapterList;
	
	// New Work Items
	private LinearLayout llNewWork;
	private ArrayList<DataWork> mNewWorkList;
	
	// Popular Work Items
	private LinearLayout llPopularWork;
	private ArrayList<DataWork> mPopularWorkList;
	
	// New Author Items
	private LinearLayout llNewAuthor;
	private ArrayList<DataUser> mNewAuthorList;
	
	// Popular Author Items
	private LinearLayout llPopularAuthor;
	private ArrayList<DataUser> mPopularAuthorList;

	public MainFragment(Context context, String fontFileName) {
		super(context, fontFileName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflateWithCustomFont(inflater, container, savedInstanceState, R.layout.main_fragment);

		// CoverImage ViewPager 설정
		mCoverImageList = new ArrayList<DataCoverImage>();
		mCoverImageList.add(new DataCoverImage());
		mCoverImageList.add(new DataCoverImage());
		mCoverImageList.add(new DataCoverImage());
		mCoverImageList.add(new DataCoverImage());
		mCoverImageList.add(new DataCoverImage());
		mCoverImageViewPager = (ViewPager) view.findViewById(R.id.vpMainCover);
		mCoverImagePagerAdapter = new ImageCoverPagerAdapter(mContext, mCoverImageList, BASE_C.CUSTOM_FONT_FILE_NAME);
		mCoverImageViewPager.setAdapter(mCoverImagePagerAdapter);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicatorMainCover);
		mIndicator.setViewPager(mCoverImageViewPager);

		// Recent Update Items 설정	
		mRecentUpdateChapterList = new ArrayList<DataChapter>();
		mRecentUpdateChapterList.add(new DataChapter());
		mRecentUpdateChapterList.add(new DataChapter());
		mRecentUpdateChapterList.add(new DataChapter());
		mRecentUpdateChapterList.add(new DataChapter());
		Log.d(TAG, "mRecentUpdateChapterList Size : " + mRecentUpdateChapterList.size());
		llRecentUpdate = (LinearLayout) view.findViewById(R.id.llMainRecentUpdate);
		for(int i=0; i<mRecentUpdateChapterList.size(); i++) {
			DataChapter chapter = mRecentUpdateChapterList.get(i);
			String title = i + chapter.getTitle();
			View recentItemView = inflateWithCustomFont(inflater, container, R.layout.listitem_chapter);
			if(i % 2 == 0){
				recentItemView.setBackgroundColor(getResources().getColor(R.color.main_listitem_color2));
			} else {
				recentItemView.setBackgroundColor(getResources().getColor(R.color.main_listitem_color1));
			}
			ImageView ivChapterThumbnail = (ImageView) recentItemView.findViewById(R.id.ivListItemChapterThumbnail);
			TextView tvWorkTitle = (TextView) recentItemView.findViewById(R.id.tvListItemChapterTitle);
			TextView tvChapterTitle = (TextView) recentItemView.findViewById(R.id.tvListItemChapterDescription);

			ImageOptions options = new ImageOptions();
			options.round = (int)(10);
			if(i % 2 == 0) {
				aq.id(ivChapterThumbnail).image("http://archivenew.vop.co.kr/images/ee5f3848fcea041ce9cd70fa338ef6f6/2012-12/18090524_1.jpg", options);
			} else {
				aq.id(ivChapterThumbnail).image("http://img.sbs.co.kr/newsnet/etv/upload/2012/12/22/30000204526_700.jpg", options);	
			}
			
			tvWorkTitle.setText("Work " + i);
			tvChapterTitle.setText("14.05.31 / Chapter " + i);

			llRecentUpdate.addView(recentItemView);
		}
		
		// New Work Items 설정
		mNewWorkList = new ArrayList<DataWork>();
		mNewWorkList.add(new DataWork(new JSONObject()));
		mNewWorkList.add(new DataWork(new JSONObject()));
		mNewWorkList.add(new DataWork(new JSONObject()));
		mNewWorkList.add(new DataWork(new JSONObject()));
		llNewWork = (LinearLayout) view.findViewById(R.id.llMainNewWork);
		for(int i=0; i<mNewWorkList.size(); i++) {
			DataWork work = mNewWorkList.get(i);
			View newWorkView = inflateWithCustomFont(inflater, container, R.layout.main_new_work_item);
			ImageView ivWorkCover = (ImageView) newWorkView.findViewById(R.id.ivMainNewWorkItemCover);
			
			ImageOptions options = new ImageOptions();
			options.round = (int)(10);
			if(i % 2 == 0) {
				aq.id(ivWorkCover).image("http://kkoksara.com/def/sample01.png", options);
			} else {
				aq.id(ivWorkCover).image("http://kkoksara.com/def/sample02.png", options);	
			}
			llNewWork.addView(newWorkView);
		}
		
		// Popular Work Items 설정
		mPopularWorkList = new ArrayList<DataWork>();
		mPopularWorkList.add(new DataWork(new JSONObject()));
		mPopularWorkList.add(new DataWork(new JSONObject()));
		mPopularWorkList.add(new DataWork(new JSONObject()));
		mPopularWorkList.add(new DataWork(new JSONObject()));
		llPopularWork = (LinearLayout) view.findViewById(R.id.llMainPopularWork);
		for(int i=0; i<mPopularWorkList.size(); i++) {
			DataWork work = mPopularWorkList.get(i);
			View newWorkView = inflateWithCustomFont(inflater, container, R.layout.main_new_work_item);
			ImageView ivWorkCover = (ImageView) newWorkView.findViewById(R.id.ivMainNewWorkItemCover);
			
			ImageOptions options = new ImageOptions();
			options.round = (int)(10);
			if(i % 2 == 0) {
				aq.id(ivWorkCover).image("http://kkoksara.com/def/sample01.png", options);
			} else {
				aq.id(ivWorkCover).image("http://kkoksara.com/def/sample02.png", options);	
			}
			llPopularWork.addView(newWorkView);
		}
		
		// New Author Items 설정
		mNewAuthorList = new ArrayList<DataUser>();
		mNewAuthorList.add(new DataUser());
		mNewAuthorList.add(new DataUser());
		mNewAuthorList.add(new DataUser());
		llNewAuthor = (LinearLayout) view.findViewById(R.id.llMainNewAuthor);
		for(int i=0; i<mNewAuthorList.size(); i++) {
			View newAuthorView = inflateWithCustomFont(inflater, container, R.layout.main_author_item);
			ImageView ivAuthorThumbnail = (ImageView) newAuthorView.findViewById(R.id.ivMainAuthorItemThumbnail);
			TextView tvAuthorNickname= (TextView) newAuthorView.findViewById(R.id.tvMainAuthorItemNickname);
			TextView tvAuthorDescription = (TextView) newAuthorView.findViewById(R.id.tvMainAuthorItemDescription);
			
			ImageOptions options = new ImageOptions();
			options.round = (int)(10);
			if(i % 2 == 0) {
				aq.id(ivAuthorThumbnail).image("http://cfile9.uf.tistory.com/image/2133F83651F15FCB389DB0", options);
			} else {
				aq.id(ivAuthorThumbnail).image("http://img.sbs.co.kr/newsnet/etv/upload/2012/12/22/30000204526_700.jpg", options);	
			}
			
			tvAuthorNickname.setText("Author Nickname [" + i + "]");
			tvAuthorDescription.setText("Author One line description [" + i + "]");
			llNewAuthor.addView(newAuthorView);
		}

		return view;
	}

	/**
	 * CoverImage ViewPager의 Adapater
	 */
	class ImageCoverPagerAdapter extends BasePagerAdapter<DataCoverImage> {
		private ArrayList<DataCoverImage> mCoverImageList;

		public ImageCoverPagerAdapter(Context context, ArrayList<DataCoverImage> objects, String fontFileName) {
			super(context, objects, fontFileName);
			mCoverImageList = objects;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View curView = inflateWithCustomFont(inflater, R.layout.pageritem_maincoverimage);
			ImageView ivCover = (ImageView) curView.findViewById(R.id.ivPagerItemMainCoverImage);

			DataCoverImage curCoverImage = mCoverImageList.get(position);
			String url = curCoverImage.getCoverImage().getUrl();

			ImageOptions options = new ImageOptions();
			options.round = (int)(15 * 2);
			if(position % 2 == 0){
				aq.id(ivCover).image("https://pbs.twimg.com/profile_images/2822166592/41ad73223bc94c1657947a97321ed7d7.jpeg", options);	
			} else {
				aq.id(ivCover).image("http://cfile4.uf.tistory.com/original/263C824351DF1CB85F7675", options);	
				
			}
			

			((ViewPager) container).addView(curView, 0);
			return curView;
		}
	}




}

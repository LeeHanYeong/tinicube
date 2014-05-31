package com.tinicube.tinicube.main;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import arcanelux.library.baseclass.BaseFragment;
import arcanelux.library.baseclass.BasePagerAdapter;

import com.androidquery.callback.ImageOptions;
import com.tinicube.tinicube.R;
import com.tinicube.tinicube.common.C;
import com.tinicube.tinicube.data.DataCoverImage;
import com.tinicube.tinicubebase.data.work.DataChapter;
import com.tinicube.tinicubebase.data.work.DataWork;
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
		mCoverImagePagerAdapter = new ImageCoverPagerAdapter(mContext, mCoverImageList, C.CUSTOM_FONT_FILE_NAME);
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
			View recentItemView = inflateWithCustomFont(inflater, container, R.layout.main_recent_update_item);
			if(i % 2 == 0){
				recentItemView.setBackgroundColor(getResources().getColor(R.color.common_background_gray));
			}
			ImageView ivChapterThumbnail = (ImageView) recentItemView.findViewById(R.id.ivMainRecentUpdateItemThumbnail);
			TextView tvWorkTitle = (TextView) recentItemView.findViewById(R.id.tvMainRecentUpdateItemWorkTitle);
			TextView tvChapterTitle = (TextView) recentItemView.findViewById(R.id.tvMainRecentUpdateItemChapterTitle);

			ImageOptions options = new ImageOptions();
			options.round = (int)(10);
			if(i % 2 == 0) {
				aq.id(ivChapterThumbnail).image("http://thumb.comic.naver.net/webtoon/81482/thumbnail/title_thumbnail_20131025191151_t125x101.jpg", options);
			} else {
				aq.id(ivChapterThumbnail).image("http://img.sbs.co.kr/newsnet/etv/upload/2012/12/22/30000204526_700.jpg", options);	
			}
			
			
			tvWorkTitle.setText("Work " + i);
			tvChapterTitle.setText("Chapter " + i);

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
				aq.id(ivWorkCover).image("http://cfile9.uf.tistory.com/image/2133F83651F15FCB389DB0", options);
			} else {
				aq.id(ivWorkCover).image("http://img.sbs.co.kr/newsnet/etv/upload/2012/12/22/30000204526_700.jpg", options);	
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
				aq.id(ivWorkCover).image("http://cfile9.uf.tistory.com/image/2133F83651F15FCB389DB0", options);
			} else {
				aq.id(ivWorkCover).image("http://img.sbs.co.kr/newsnet/etv/upload/2012/12/22/30000204526_700.jpg", options);	
			}
			llPopularWork.addView(newWorkView);
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
			aq.id(ivCover).image("http://thumb.comic.naver.net/webtoon/624632/thumbnail/title_thumbnail_20140519170435_t218x120.jpg", options);

			((ViewPager) container).addView(curView, 0);
			return curView;
		}
	}




}

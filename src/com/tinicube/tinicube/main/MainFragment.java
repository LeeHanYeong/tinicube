package com.tinicube.tinicube.main;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import arcanelux.library.baseclass.BaseFragment;
import arcanelux.library.baseclass.BasePagerAdapter;

import com.androidquery.callback.ImageOptions;
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

package com.tinicube.tinicube.main;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import arcanelux.library.baseclass.BaseFragment;
import arcanelux.library.baseclass.BasePagerAdapter;

import com.androidquery.callback.ImageOptions;
import com.tinicube.base.data.DataAuthorInfo;
import com.tinicube.base.data.DataUser;
import com.tinicube.base.function.BASE_C;
import com.tinicube.comicbase.data.work.DataChapter;
import com.tinicube.comicbase.data.work.DataWork;
import com.tinicube.tinicube.R;
import com.tinicube.tinicube.common.C;
import com.tinicube.tinicube.common.Pref;
import com.tinicube.tinicube.data.DataCoverImage;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class MainFragment extends BaseFragment { 
	// OnClickListener
	private WorkClickListener mWorkClickListener;
	
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
		// OnClickListener 세팅
		mWorkClickListener = new WorkClickListener();
		
		// 리스트 아이템 세팅
		initData();
		// MainFragment의 리스트들 세팅
		initMainList(view, inflater, container);
		
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
	
	class ChapterClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			DataChapter chapter = (DataChapter) v.getTag();
			
		}
	}
	
	class WorkClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			DataWork work = (DataWork) v.getTag();
			String workId = work.getId() + "";
			new ComicInitializeTask(mContext, "로딩 중...", true, workId).execute();
		}
	}

	private void initData(){
		// Loading에서 받아온 MainFragment표시내용 JSONObject
		JSONObject jsonObjectRecentUpdateChapterList = Pref.getJsonObject(mContext, C.JSONDATA_RECENT_UPDATE_CHAPTER_LIST);
		JSONObject jsonObjectNewWorkList = Pref.getJsonObject(mContext, C.JSONDATA_NEWWORK_LIST);
		JSONObject jsonObjectPopWorkList = Pref.getJsonObject(mContext, C.JSONDATA_POPWORK_LIST);
		JSONObject jsonObjectNewAuthorList = Pref.getJsonObject(mContext, C.JSONDATA_NEWAUTHOR_LIST);
		JSONObject jsonObjectPopAuthorList = Pref.getJsonObject(mContext, C.JSONDATA_POPAUTHOR_LIST);

		try {
			JSONArray jsonArrayRecentUpdateChapterList = jsonObjectRecentUpdateChapterList.getJSONArray("chapters");
			JSONArray jsonArrayNewWorkList = jsonObjectNewWorkList.getJSONArray("works");
			JSONArray jsonArrayPopWorkList = jsonObjectPopWorkList.getJSONArray("works");
			JSONArray jsonArrayNewAuthorList = jsonObjectNewAuthorList.getJSONArray("authors");
			JSONArray jsonArrayPopAuthorList = jsonObjectPopAuthorList.getJSONArray("authors");

			mRecentUpdateChapterList = new ArrayList<DataChapter>();
			for(int i=0; i<jsonArrayRecentUpdateChapterList.length(); i++){
				JSONObject jsonObjectCurChapter = jsonArrayRecentUpdateChapterList.getJSONObject(i);
				DataChapter curChapter = new DataChapter(jsonObjectCurChapter);
				mRecentUpdateChapterList.add(curChapter);
			}
			mNewWorkList = new ArrayList<DataWork>();
			for(int i=0; i<jsonArrayNewWorkList.length(); i++){
				JSONObject jsonObjectCurWork = jsonArrayNewWorkList.getJSONObject(i);
				DataWork curWork = new DataWork(jsonObjectCurWork);
				mNewWorkList.add(curWork);
			}
			mPopularWorkList = new ArrayList<DataWork>();
			for(int i=0; i<jsonArrayPopWorkList.length(); i++){
				JSONObject jsonObjectCurWork = jsonArrayPopWorkList.getJSONObject(i);
				DataWork curWork = new DataWork(jsonObjectCurWork);
				mPopularWorkList.add(curWork);
			}
			mNewAuthorList = new ArrayList<DataUser>();
			for(int i=0; i<jsonArrayNewAuthorList.length(); i++){
				JSONObject jsonObjectCurAuthor = jsonArrayNewAuthorList.getJSONObject(i);
				DataUser curUser = new DataUser(jsonObjectCurAuthor);
				mNewAuthorList.add(curUser);
			}
			mPopularAuthorList = new ArrayList<DataUser>();
			for(int i=0; i<jsonArrayPopAuthorList.length(); i++){
				JSONObject jsonObjectCurAuthor = jsonArrayPopAuthorList.getJSONObject(i);
				DataUser curUser = new DataUser(jsonObjectCurAuthor);
				mPopularAuthorList.add(curUser);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initMainList(View view, LayoutInflater inflater, ViewGroup container){
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
		llRecentUpdate = (LinearLayout) view.findViewById(R.id.llMainRecentUpdate);
		for(int i=0; i<mRecentUpdateChapterList.size(); i++) {
			DataChapter chapter = mRecentUpdateChapterList.get(i);
			String urlThumbnail = C.URL_BASE + chapter.getThumbnail().getUrl();
			Log.d(TAG, "URL Thumbnail : " + urlThumbnail);
			String workTitle = chapter.getWorkTitle();
			String chapterTitle = chapter.getCreated() + " / " + chapter.getTitle();

			View recentItemView = inflateWithCustomFont(inflater, container, R.layout.listitem_chapter_main);
			if(i % 2 == 0){
				recentItemView.setBackgroundColor(getResources().getColor(R.color.listitem_color1));
			} else {
				recentItemView.setBackgroundColor(getResources().getColor(R.color.listitem_color2));
			}
			ImageView ivChapterThumbnail = (ImageView) recentItemView.findViewById(R.id.ivListItemChapterThumbnail);
			TextView tvWorkTitle = (TextView) recentItemView.findViewById(R.id.tvListItemChapterTitle);
			TextView tvChapterTitle = (TextView) recentItemView.findViewById(R.id.tvListItemChapterDescription);

			ImageOptions options = new ImageOptions();
			options.round = (int)(10);

			aq.id(ivChapterThumbnail).image(urlThumbnail);
			tvWorkTitle.setText(workTitle);
			tvChapterTitle.setText(chapterTitle);

			llRecentUpdate.addView(recentItemView);
		}

		// New Work Items 설정
		llNewWork = (LinearLayout) view.findViewById(R.id.llMainNewWork);
		for(int i=0; i<mNewWorkList.size(); i++) {
			DataWork work = mNewWorkList.get(i);
			String urlCover = C.URL_BASE + work.getCover().getUrl();
			String workTitle = work.getTitle();
			String authorTitle = work.getAuthor();
			String workDescription = work.getDescription();

			View newWorkView = inflateWithCustomFont(inflater, container, R.layout.listitem_work);
			if(i % 2 == 0){
				newWorkView.setBackgroundColor(getResources().getColor(R.color.listitem_color1));
			} else{
				newWorkView.setBackgroundColor(getResources().getColor(R.color.listitem_color2));
			}
			ImageView ivWorkCover = (ImageView) newWorkView.findViewById(R.id.ivListItemWorkCover);
			TextView tvWorkTitle = (TextView) newWorkView.findViewById(R.id.tvListItemWorkTitle);
			TextView tvAuthor = (TextView) newWorkView.findViewById(R.id.tvListItemWorkAuthor);
			TextView tvDescription = (TextView) newWorkView.findViewById(R.id.tvListItemWorkDescription);

			aq.id(ivWorkCover).image(urlCover);
			tvWorkTitle.setText(workTitle);
			tvAuthor.setText(authorTitle);
			tvDescription.setText(workDescription);
			
			newWorkView.setTag(work);
			newWorkView.setOnClickListener(mWorkClickListener);
			llNewWork.addView(newWorkView);
		}

		// Popular Work Items 설정
		llPopularWork = (LinearLayout) view.findViewById(R.id.llMainPopularWork);
		for(int i=0; i<mPopularWorkList.size(); i++) {
			DataWork work = mPopularWorkList.get(i);
			String urlCover = C.URL_BASE + work.getCover().getUrl();
			String workTitle = work.getTitle();
			String authorTitle = work.getAuthor();
			String workDescription = work.getDescription();
			View popWorkView = inflateWithCustomFont(inflater, container, R.layout.listitem_work);
			if(i % 2 == 0){
				popWorkView.setBackgroundColor(getResources().getColor(R.color.listitem_color1));
			} else{
				popWorkView.setBackgroundColor(getResources().getColor(R.color.listitem_color2));
			}

			ImageView ivWorkCover = (ImageView) popWorkView.findViewById(R.id.ivListItemWorkCover);
			TextView tvWorkTitle = (TextView) popWorkView.findViewById(R.id.tvListItemWorkTitle);
			TextView tvAuthor = (TextView) popWorkView.findViewById(R.id.tvListItemWorkAuthor);
			TextView tvDescription = (TextView) popWorkView.findViewById(R.id.tvListItemWorkDescription);

			aq.id(ivWorkCover).image(urlCover);
			tvWorkTitle.setText(workTitle);
			tvAuthor.setText(authorTitle);
			tvDescription.setText(workDescription);
			
			popWorkView.setTag(work);
			popWorkView.setOnClickListener(mWorkClickListener);
			llPopularWork.addView(popWorkView);
		}

		// New Author Items 설정
		llNewAuthor = (LinearLayout) view.findViewById(R.id.llMainNewAuthor);
		for(int i=0; i<mNewAuthorList.size(); i++) {
			DataUser author = mNewAuthorList.get(i);
			DataAuthorInfo authorInfo = author.getAuthorInfo();
			View newAuthorView = inflateWithCustomFont(inflater, container, R.layout.listitem_author);
			String urlThumbnail = C.URL_BASE + authorInfo.getAuthorThumbnail().getUrl();
			String authorNickname = authorInfo.getAuthorNickname();
			String authorIntroduceSimple = authorInfo.getIntroduceSimple();

			ImageView ivAuthorThumbnail = (ImageView) newAuthorView.findViewById(R.id.ivListItemAuthorThumbnail);
			TextView tvAuthorNickname= (TextView) newAuthorView.findViewById(R.id.tvListItemAuthorNickname);
			TextView tvAuthorDescription = (TextView) newAuthorView.findViewById(R.id.tvListItemAuthorDescription);

			aq.id(ivAuthorThumbnail).image(urlThumbnail);
			tvAuthorNickname.setText(authorNickname);
			tvAuthorDescription.setText(authorIntroduceSimple);
			llNewAuthor.addView(newAuthorView);
		}

		// Popular Author Items 설정
		llPopularAuthor = (LinearLayout) view.findViewById(R.id.llMainPopularAuthor);
		for(int i=0; i<mPopularAuthorList.size(); i++){
			DataUser author = mPopularAuthorList.get(i);
			DataAuthorInfo authorInfo = author.getAuthorInfo();
			View popAuthorView = inflateWithCustomFont(inflater, container, R.layout.listitem_author);
			String urlThumbnail = C.URL_BASE + authorInfo.getAuthorThumbnail().getUrl();
			String authorNickname = authorInfo.getAuthorNickname();
			String authorIntroduceSimple = authorInfo.getIntroduceSimple();

			ImageView ivAuthorThumbnail = (ImageView) popAuthorView.findViewById(R.id.ivListItemAuthorThumbnail);
			TextView tvAuthorNickname= (TextView) popAuthorView.findViewById(R.id.tvListItemAuthorNickname);
			TextView tvAuthorDescription = (TextView) popAuthorView.findViewById(R.id.tvListItemAuthorDescription);

			aq.id(ivAuthorThumbnail).image(urlThumbnail);
			tvAuthorNickname.setText(authorNickname);
			tvAuthorDescription.setText(authorIntroduceSimple);
			llPopularAuthor.addView(popAuthorView);
		}

	}



}

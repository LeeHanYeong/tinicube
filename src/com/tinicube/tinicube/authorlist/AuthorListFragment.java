package com.tinicube.tinicube.authorlist;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import arcanelux.library.baseclass.BaseArrayAdapter;
import arcanelux.library.baseclass.BaseFragment;

import com.tinicube.base.data.DataAuthorInfo;
import com.tinicube.base.data.DataUser;
import com.tinicube.base.function.BASE_C;
import com.tinicube.comicbase.author.TiniCubeAuthorInitializeTask;
import com.tinicube.tinicube.FragmentTouchListener;
import com.tinicube.tinicube.R;
import com.tinicube.tinicube.common.C;

public class AuthorListFragment extends BaseFragment {
	private String mFragmentTitle, mFragmentDescription;
	private TextView tvTitle, tvDescription;
	
	private ListView lv;
	private AuthorListAdapter mAdapter;
	private ArrayList<DataUser> mDataUserList;
	
	public AuthorListFragment(Context context, String fragmentTitle, String fragmentDescription, ArrayList<DataUser> dataUserList, String fontFileName) {
		super(context, fontFileName);
		this.mFragmentTitle = fragmentTitle;
		this.mFragmentDescription = fragmentDescription;
		mDataUserList = dataUserList;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflateWithCustomFont(inflater, container, savedInstanceState, R.layout.authorlist_fragment);
		
		tvTitle = (TextView) view.findViewById(R.id.tvAuthorListTitle);
		tvDescription = (TextView) view.findViewById(R.id.tvAuthorListDescription);
		tvTitle.setText(mFragmentTitle);
		tvDescription.setText(mFragmentDescription);
		
		lv = (ListView) view.findViewById(R.id.lvAuthorListList);
		mAdapter = new AuthorListAdapter(mContext, R.layout.listitem_author, mDataUserList, BASE_C.CUSTOM_FONT_FILE_NAME);
		lv.setAdapter(mAdapter);

		view.setOnTouchListener(new FragmentTouchListener());
		return view;
	}
	
	
	class AuthorListAdapter extends BaseArrayAdapter<DataUser> implements OnClickListener{
		private ArrayList<DataUser> mDataAuthorInfoList;
		
		public AuthorListAdapter(Context context, int resource, ArrayList<DataUser> objects, String fontFileName) {
			super(context, resource, objects, fontFileName);
			this.mDataAuthorInfoList = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View curView = convertView;
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(curView == null){
				curView= inflateWithCustomFont(inflater, R.layout.listitem_author);
			}
			
			DataUser user = mDataAuthorInfoList.get(position);
			DataAuthorInfo authorInfo = user.getAuthorInfo();
			if(position % 2 == 0){
				curView.setBackgroundColor(getResources().getColor(R.color.listitem_color1));
			} else {
				curView.setBackgroundColor(getResources().getColor(R.color.listitem_color2));
			}
			
			String urlThumbnail = C.URL_BASE + authorInfo.getAuthorThumbnail().getUrl();
			Log.d(TAG, "URL Thumbnail : " + urlThumbnail);
			String authorNickname = authorInfo.getAuthorNickname();
			String authorIntroduceSimple = authorInfo.getIntroduceSimple();

			ImageView ivAuthorThumbnail = (ImageView) curView.findViewById(R.id.ivListItemAuthorThumbnail);
			TextView tvAuthorNickname= (TextView) curView.findViewById(R.id.tvListItemAuthorNickname);
			TextView tvAuthorDescription = (TextView) curView.findViewById(R.id.tvListItemAuthorDescription);

			aq.id(ivAuthorThumbnail).image(urlThumbnail);
			tvAuthorNickname.setText(authorNickname);
			tvAuthorDescription.setText(authorIntroduceSimple);
			
			curView.setTag(position);
			curView.setOnClickListener(this);
			
			return curView;
		}

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			DataUser user = mDataAuthorInfoList.get(position);
			DataAuthorInfo authorInfo = user.getAuthorInfo();
			String authorUserId = user.getId() + "";
			
			new TiniCubeAuthorInitializeTask(mContext, "작가정보를 불러오는 중입니다..", true, authorUserId).execute();
		}
	}
	

}

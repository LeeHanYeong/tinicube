package com.tinicube.tinicube.loading;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import arcanelux.library.ArcaneluxFunction;
import arcanelux.library.activity.AdlibrLoadingActivity;

import com.tinicube.base.function.BASE_C;
import com.tinicube.base.function.BASE_Pref;
import com.tinicube.tinicube.MainActivity;
import com.tinicube.tinicube.R;
import com.tinicube.tinicube.common.C;
import com.tinicube.tinicube.common.Pref;

public class LoadingActivity extends AdlibrLoadingActivity {
	private TextView tvLoadingTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar.hide();
		showAd = false;
		D = true;
//		View view = inflateWithCustomFont(getLayoutInflater(), R.layout.loading_activity, C.CUSTOM_FONT_TITILLIUM_2);
		setContentView(R.layout.loading_activity);
		
		tvLoadingTitle = (TextView) findViewById(R.id.tvLoadingTitle);
		ArcaneluxFunction.setTypeface(mContext, tvLoadingTitle, BASE_C.CUSTOM_FONT_TITILLIUM_2);
		ArcaneluxFunction.setTypeface(mContext, tvLoadingMessage, BASE_C.CUSTOM_FONT_TITILLIUM_2);
		
		new TiniCubeInitializeTask(mContext, "초기화", false).execute();
	}
	
	class TiniCubeVersionCheckTask extends VersionCheckTask {
		public TiniCubeVersionCheckTask(Context context, String title, boolean showDialog) {
			super(context, title, showDialog);
		}
		@Override
		protected Integer doInBackground(Void... params) {
			return super.doInBackground(params);
		}
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}
	}
	
	class TiniCubeInitializeTask extends InitializeTask {
		private String strJSONObjectRecentUpdateChapterList;
		private String strJSONObjectNewWorkList;
		private String strJSONObjectPopWorkList;
		private String strJSONObjectNewAuthorList;
		private String strJSONObjectPopAuthorList;
		
		public TiniCubeInitializeTask(Context context, String title, boolean showDialog) {
			super(context, title, showDialog);
		}
		@Override
		protected Integer doInBackground(Void... params) {
			/** MainFragment 표시내용 받아옴 **/
			strJSONObjectRecentUpdateChapterList = postRequest(C.API_RECENT_UPDATE_CHAPTER_LIST);
			strJSONObjectNewWorkList = postRequest(C.API_NEWWORK_LIST);
			strJSONObjectPopWorkList = postRequest(C.API_POPWORK_LIST);
			strJSONObjectNewAuthorList = postRequest(C.API_NEWAUTHOR_LIST);
			strJSONObjectPopAuthorList = postRequest(C.API_POPAUTHOR_LIST);
			
			if(D) {
				Log.d(TAG, "-- RecentUpdate -- ");
				Log.d(TAG, strJSONObjectRecentUpdateChapterList);
				Log.d(TAG, "\n");
				
				Log.d(TAG, "-- NewWorkList --");
				Log.d(TAG, strJSONObjectNewWorkList);
				Log.d(TAG, "\n");
				
				Log.d(TAG, "-- PopWorkList --");
				Log.d(TAG, strJSONObjectPopWorkList);
				Log.d(TAG, "\n");
				
				Log.d(TAG, "-- NewAuthorList --");
				Log.d(TAG, strJSONObjectNewAuthorList);
				Log.d(TAG, "\n");
				
				Log.d(TAG, "-- PopAuthorList --");
				Log.d(TAG, strJSONObjectPopAuthorList);
				Log.d(TAG, "\n");
			}
			return super.doInBackground(params);
		}
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			try {
				/** MainFragment 에 사용할 내용 JSONObject validation **/
				JSONObject jsonObjectRecentUpdateChapterList = new JSONObject(strJSONObjectRecentUpdateChapterList);
				JSONObject jsonObjectNewWorkList = new JSONObject(strJSONObjectNewWorkList);
				JSONObject jsonObjectPopWorkList = new JSONObject(strJSONObjectPopWorkList);
				JSONObject jsonObjectNewAuthorList = new JSONObject(strJSONObjectNewAuthorList);
				JSONObject jsonObjectPopAuthorList = new JSONObject(strJSONObjectPopAuthorList);
				Pref.setJsonObjectString(mContext, C.JSONDATA_RECENT_UPDATE_CHAPTER_LIST, strJSONObjectRecentUpdateChapterList);
				
				Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(mContext, "서버와의 통신에 실패했습니다", Toast.LENGTH_SHORT).show();
			}
			
			
		}
		
	}

}

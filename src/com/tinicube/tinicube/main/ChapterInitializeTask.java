package com.tinicube.tinicube.main;

import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import arcanelux.library.baseclass.BaseAsyncTask;

import com.tinicube.base.function.BASE_C;
import com.tinicube.base.function.BASE_Pref;
import com.tinicube.comicbase.TiniCubeComicChapterListActivity;
import com.tinicube.comicbase.data.work.DataWorkAndChapters;

public class ChapterInitializeTask extends BaseAsyncTask {
	private String workId;
	private String resultString;
	private boolean loadSuccess;
	
	public ChapterInitializeTask(Context context, String title, boolean showDialog, String workId) {
		super(context, title, showDialog);
		this.workId = workId;
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			BASE_Pref.setIdWork(mContext, workId);
			HashMap<String, String> valuePair = new HashMap<String, String>();
			valuePair.put("work_id", workId);
			resultString = postRequest(BASE_C.API_CHAPTER_LIST, valuePair);

			// 받은 데이터 확인 후, 구성요소 있는지 테스트
			DataWorkAndChapters dataList = new DataWorkAndChapters(new JSONObject(resultString));
			dataList.getDataChapters();
			dataList.getDataWork();
			BASE_Pref.setJsonObjectString(mContext, BASE_C.JSONDATA_WORK_AND_CHAPTERS, resultString);
			loadSuccess = true;
		} catch(Exception e){
			e.printStackTrace();
			loadSuccess = false;
		}
		return super.doInBackground(params);
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if(loadSuccess){
			Intent intent = null;
//			intent = new Intent(mContext, ComicChapterListActivity.class);
			intent = new Intent(mContext, TiniCubeComicChapterListActivity.class);
			((Activity) mContext).startActivity(intent);
		} else{
			Toast.makeText(mContext, "서버와의 통신에 실패했습니다", Toast.LENGTH_SHORT).show();
		}
	}
	
	

}

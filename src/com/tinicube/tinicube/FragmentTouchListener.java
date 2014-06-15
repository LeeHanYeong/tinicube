package com.tinicube.tinicube;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class FragmentTouchListener implements OnTouchListener {
	private final String TAG = this.getClass().getSimpleName();
	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		Log.d(TAG, event.getAction() + "");
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP){
			MainActivity.confirmFinish = false;
			Log.d(TAG, "confirmFinish False");
		}
		return false;
	}

}

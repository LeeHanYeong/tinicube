package com.tinicube.tinicube.loading;

import android.os.Bundle;
import android.widget.TextView;
import arcanelux.library.ArcaneluxFunction;
import arcanelux.library.activity.AdlibrLoadingActivity;

import com.tinicube.tinicube.R;
import com.tinicube.tinicube.common.C;

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
		ArcaneluxFunction.setTypeface(mContext, tvLoadingTitle, C.CUSTOM_FONT_TITILLIUM_2);
		ArcaneluxFunction.setTypeface(mContext, tvLoadingMessage, C.CUSTOM_FONT_TITILLIUM_2);
	}

}

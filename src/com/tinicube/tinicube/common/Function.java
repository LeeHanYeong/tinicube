package com.tinicube.tinicube.common;

import java.util.ArrayList;

import android.view.View;
import android.widget.LinearLayout;

public class Function {
	public static void AddViewToLinearLayout(LinearLayout linear, View view){
		linear.addView(view);
	}
	public static void AddViewsToLinearLayout(LinearLayout linear, ArrayList<View> viewList){
		for(View view : viewList){
			linear.addView(view);
		}
	}
}

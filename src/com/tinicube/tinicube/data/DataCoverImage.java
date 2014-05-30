package com.tinicube.tinicube.data;

import org.json.JSONException;
import org.json.JSONObject;

import arcanelux.library.data.BaseData;

public class DataCoverImage extends BaseData {

	public DataCoverImage() {
		super();
	}

	public DataCoverImage(JSONObject jsonObject) {
		super(jsonObject);
	}

	public DataImage getCoverImage(){
		try {
			return new DataImage(mJsonObject.getJSONObject("image"));
		} catch (JSONException e) {
			e.printStackTrace();
			return new DataImage(new JSONObject());
		}
	}
}

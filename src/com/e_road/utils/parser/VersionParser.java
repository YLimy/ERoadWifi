package com.e_road.utils.parser;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.e_road.vo.Version;

/**
 * 版本解析<br>
 * 对应服务器返回的数据<br>
 * key:"version"
 * 
 * @author CaiMeng
 * 
 */
public class VersionParser extends BaseParser<Version> {

	/**
	 * 解析版本号
	 * 
	 * @param 服务器端返回的数据
	 * @return Version || null
	 * @exception JSONException
	 */
	@Override
	public Version parseJson(String paramString) throws JSONException {
		if (!TextUtils.isEmpty(paramString)) {
			JSONObject jsonObj = JSONObject.parseObject(paramString);
			return jsonObj.getObject("version", Version.class);
		}
		// 如果服务器端返回空
		return null;
	}

	@Override
	public Version parseJson4App(String paramString) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}

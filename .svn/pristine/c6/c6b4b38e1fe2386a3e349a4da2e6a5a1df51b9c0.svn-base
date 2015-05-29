package com.e_road.utils.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.e_road.vo.UserInfo;

public class UserModifyParser extends BaseParser<Object> {

	@Override
	public Object parseJson(String paramString) throws JSONException {
		String responseStr = checkResponse(paramString);
		if (null != responseStr) {
			if (responseStr.equals("now_pswd_error")) {
//				JSONObject jsonObj = JSONObject.parseObject(paramString);
//				return jsonObj.getString("update_erro");
				return "密码错误";
			} else if(responseStr.equals("alpswd_success")){
//				JSONObject jsonObj = JSONObject.parseObject(paramString);
//				return jsonObj.getObject("userinfo", UserInfo.class);
				return new UserInfo();
			}
		}
		return responseStr;
	}

	@Override
	public Object parseJson4App(String paramString) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.e_road.utils.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.e_road.utils.IConstant;
import com.e_road.utils.LoggerUtil;

/**
 * 解析数据工具类
 * 
 * @author CaiMeng
 * 
 * @param <T>
 */
public abstract class BaseParser<T> {
	
	/**
	 * 解析Json字符串
	 * 
	 * @param paramString
	 * @return <T>
	 * @throws JSONException
	 */
	public abstract T parseJson(String paramString) throws JSONException;
	
	public abstract T parseJson4App(String paramString) throws JSONException;

	/**
	 * 检查返回结果
	 * 
	 * @param paramString
	 * @return 返回数据为空：null<br>
	 *         返回IConstant.RESPONSE为空：null<br>
	 *         返回IConstant.RESPONSE为IConstant.RESPONSE_ERRO：null<br>
	 *         success:返回IConstant.RESPONSE的value
	 * @throws JSONException
	 */
	public static String checkResponse(String paramString) throws JSONException {
		
		LoggerUtil.d("BaseParser", "paramString=>>" + paramString);
		// 非空过滤
		if (null == paramString)
			return null;
		
		LoggerUtil.d("BaseParser", paramString);
		
		JSONObject jsonObject = JSONObject.parseObject(paramString);
		String result = jsonObject.getString(IConstant.RESPONSE);
		// 是否包含response标签，以及是否正常数据
//		if (result !=null && !"".equals(result) && !result.equals("response_erro")){
//			return result;
//		}
//		else{
//			return null;
//		}
		if(null == result || "".equals(result)){
			return null;
		}else if(result.equals(IConstant.RESPONSE_ERRO)){
			return null;
		}else{
			return result;
		}
	}
}

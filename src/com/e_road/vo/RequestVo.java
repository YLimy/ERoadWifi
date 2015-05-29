package com.e_road.vo;

import java.util.HashMap;

import android.content.Context;

import com.e_road.utils.parser.BaseParser;

/**
 * 请求对象
 * @author CaiMeng
 *
 */
public class RequestVo {

	/** 链接资源 */
	private int requestUrl;
	/** 获取资源时要用到的上下文信息 */
	private Context context;
	/** 发送的数据包 */
	private HashMap<String, String> requestMap;
	/** 返回数据的解析方法 */
	private BaseParser<?> parser;
	
//	public RequestVo(){}

	/**
	 * 请求对象
	 * @param requestUrl 链接资源
	 * @param context 上下文
	 * @param requestMap 数据封装
	 * @param parser     解析工具
	 */
	public RequestVo(int requestUrl, Context context, HashMap<String, String> requestMap,
			BaseParser<?> parser) {
		super();
		this.requestUrl = requestUrl;
		this.context = context;
		this.requestMap = requestMap;
		this.parser = parser;
	}

	public int getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(int requestUrl) {
		this.requestUrl = requestUrl;
	}

	public HashMap<String, String> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(HashMap<String, String> requestMap) {
		this.requestMap = requestMap;
	}

	public BaseParser<?> getParser() {
		return parser;
	}

	public void setParser(BaseParser<?> parser) {
		this.parser = parser;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}

package com.e_road.utils;

import java.io.File;

import android.content.Context;

import com.e_road.vo.DownLoadFinishedFiles;

/**
 * 解析已下载文件的信息
 * @author CaiMeng
 *
 */
public abstract class DownLoadFileInfo {
	
//	public abstract DownLoadFinishedFiles getFileInfo(Context context, String path);
	
	public static float getFileLength(String path){
		File file = new File(path);
		if(!file.exists() || file.isDirectory()){
			return 0;
		}
		long length = file.length();
		//单位到M
		float f = length / 1024 /1024;
		//保留两位小数
		return (float)Math.round((f*100)/100);
	}
	
}

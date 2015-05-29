package com.e_road.vo;

import android.graphics.drawable.Drawable;

/**
 * 已下载完成的文件
 * @author CaiMeng
 *
 */
public class DownLoadFinishedFiles {
	//TODO icon

	/** 文件名 */
	private String fileName;
	/** 文件大小 */
	private float fileLength = 0;
	/** 文件图标 */
	private Drawable icon;
	
	public DownLoadFinishedFiles(){}
	
	public DownLoadFinishedFiles(String fileName, float fileLength,
			Drawable icon) {
		this.fileName = fileName;
		this.fileLength = fileLength;
		this.icon = icon;
	}
	public String getFileName() {
		return fileName == null?  "":fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public float getFileLength() {
		return fileLength;
	}
	public void setFileLength(float fileLength) {
		this.fileLength = fileLength;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
}

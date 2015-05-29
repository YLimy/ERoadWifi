package com.e_road.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 资源下载实体类
 * @author CaiMeng
 *
 */
public class DownLoadInfo implements Parcelable{

	/** 下载文件ID */
	private int id;
	/** 下载地址 */
	private String url;
	/** 下文件名 */
	private String fileName;
	/** 文件图标 */
	private String icon;
	/** 已下载数量 */
	private int length;
	/** 下载文件总大小 */
	private int total;
	/** 下载速度 */
	private int speed;
	/** 是否正在下载 */
	private boolean isDownload;
	
	public DownLoadInfo(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isDownload() {
		return isDownload;
	}
	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// 写入Parcel流
		//创建映射
		dest.writeInt(id);
		dest.writeString(url);
		dest.writeString(fileName);
		dest.writeString(icon);
		dest.writeInt(length);
		dest.writeInt(total);
		dest.writeInt(speed);
		dest.writeBooleanArray(new boolean[]{isDownload});
	}
	
	public static final Parcelable.Creator<DownLoadInfo> CREATOR = new Creator<DownLoadInfo>() {
		
		@Override
		public DownLoadInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new DownLoadInfo[size];
		}
		
		@Override
		public DownLoadInfo createFromParcel(Parcel source) {
			return new DownLoadInfo(source);
		}
	};
	
	public DownLoadInfo(Parcel source){
		id = source.readInt();
		url = source.readString();
		fileName = source.readString();
		icon = source.readString();
		length = source.readInt();
		total = source.readInt();
		speed = source.readInt();
		source.readBooleanArray(new boolean[]{isDownload});
	}
}

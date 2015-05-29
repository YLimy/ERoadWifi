package com.e_road.vo;

/**
 * 版本
 * 
 * @author CaiMeng
 * 
 */
public class Version {

	/** 新版本 */
	public static int VERSION_NEW = 1;
	/** 就版本 */
	public static int VERSION_OLD = 0;

	/** 需要强制更新 */
	public static int VERSION_FORCE = 1;
	/** 不需要强制更新 */
	public static int VERSION_FORCE_NOT = 2;

	/** 是否新版本 */
	private int isNew;
	/** 版本号 */
	private String version;
	/** 是否强制更新 */
	private int force;
	/** 更新地址 */
	private String url;

	public Version() {
	}

	public Version(int isNew, String version, int force, String url) {
		this.isNew = isNew;
		this.version = version;
		this.force = force;
		this.url = url;
	}

	/** 获取版本号 */
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/** 获取更新地址 */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 是否新版本
	 * 
	 * @return 1：新版本<br>
	 *         0：旧版本
	 */
	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	/**
	 * 是否需要强制更新
	 * 
	 * @return 1:需要强制更新<br>
	 *         2:不需要强制更新
	 */
	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}
}

package com.e_road.vo;

/**
 * 用户手机信息
 * @author CaiMeng
 *
 */
public class UserInfo {
	/** 序号 */
	private long id;
	/** 用户名 */
	private String username;
	/** 用户昵称 */
	private String nickname;
	/** 手机MAC */
	private String mac;
	/** 手机IMEI */
	private String imei;
	/** 手机型号 */
	private String phone;
	//与服务器对象一致
//	/** 操作系统型号 */
//	private String version;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
//	public String getVersion() {
//		return version;
//	}
//	public void setVersion(String version) {
//		this.version = version;
//	}
	public long getiId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}

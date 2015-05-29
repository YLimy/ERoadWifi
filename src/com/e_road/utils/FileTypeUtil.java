package com.e_road.utils;

import java.io.File;

public class FileTypeUtil {
	
	/**
	 * 文件类型
	 * @author CaiMeng
	 *
	 */
	public static enum FileType{
		pdf,audio,video,image,apk,others
	}

	/**
	 * 通过文件获取文件后缀名
	 * @param f
	 * @return
	 */
	public static FileType getMIMEType(File f) {
		String fName = f.getName();
		return getMIMEType(fName);
	}

	/**
	 * 通过文件名获取文件类型
	 * @param fName
	 * @return
	 */
	public static FileType getMIMEType(String fName) {
		FileType type;
		/* 取得扩展名 */
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		/* 依扩展名的类型决定MimeType */
		if (end.equals("pdf")) {
//			type = "application/pdf";
			type = FileType.pdf;
		} else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
//			type = "audio/*";
			type = FileType.audio;
		} else if (end.equals("3gp") || end.equals("mp4")) {
//			type = "video/*";
			type = FileType.video;
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
//			type = "image/*";
			type = FileType.image;
		} else if (end.equals("apk")) {
			/* android.permission.INSTALL_PACKAGES */
//			type = "application/vnd.android.package-archive";
			type = FileType.apk;
		}
		// else if(end.equals("pptx")||end.equals("ppt")){
		// type = "application/vnd.ms-powerpoint";
		// }else if(end.equals("docx")||end.equals("doc")){
		// type = "application/vnd.ms-word";
		// }else if(end.equals("xlsx")||end.equals("xls")){
		// type = "application/vnd.ms-excel";
		// }
		else {
			// /*如果无法直接打开，就跳出软件列表给用户选择 */
//			type = "*/*";
			type = FileType.others;
		}
		return type;
	}
}

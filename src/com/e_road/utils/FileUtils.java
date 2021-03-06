package com.e_road.utils;

import java.io.File;

import android.content.Intent;
import android.net.Uri;

public class FileUtils {
	public static final String TAG = "FileUtils";
	
	/**
	 * 获取一个文件Intent
	 * @param file
	 * @return
	 */
	public static Intent getFileIntent(File file){  
//      Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");  
       Uri uri = Uri.fromFile(file);  
       String type = getMIMEType(file);  
       LoggerUtil.i(TAG, "type="+type);  
       Intent intent = new Intent("android.intent.action.VIEW");  
       intent.addCategory("android.intent.category.DEFAULT");  
       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
       intent.setDataAndType(uri, type);  
       return intent;  
     }
	
	/**
	 * 获取文件的拓展名
	 * @param f
	 * @return
	 */
	private static String getMIMEType(File f){     
	      String type="";    
	      String fName=f.getName();    
	      /* 取得扩展名 */    
	      String end=fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();  
	      LoggerUtil.d(TAG, end);
	      /* 依扩展名的类型决定MimeType */  
	      if(end.equals("pdf")){  
	          type = "application/pdf";//  
	      }  
	      else if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||    
	      end.equals("xmf")||end.equals("ogg")||end.equals("wav")){    
	        type = "audio/*";     
	      }    
	      else if(end.equals("3gp")||end.equals("mp4")){    
	        type = "video/*";    
	      }    
	      else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||    
	      end.equals("jpeg")||end.equals("bmp")){    
	        type = "image/*";    
	      }    
	      else if(end.equals("apk")){     
	        /* android.permission.INSTALL_PACKAGES */     
	        type = "application/vnd.android.package-archive";   
	      }  
//	      else if(end.equals("pptx")||end.equals("ppt")){  
//	        type = "application/vnd.ms-powerpoint";   
//	      }else if(end.equals("docx")||end.equals("doc")){  
//	        type = "application/vnd.ms-word";  
//	      }else if(end.equals("xlsx")||end.equals("xls")){  
//	        type = "application/vnd.ms-excel";  
//	      }  
	      else{  
//	        /*如果无法直接打开，就跳出软件列表给用户选择 */    
	        type="*/*";  
	      }  
	      return type;  
	    }

}

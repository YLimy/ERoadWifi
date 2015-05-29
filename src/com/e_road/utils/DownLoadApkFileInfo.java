package com.e_road.utils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.e_road.vo.AppInfo;
import com.e_road.vo.DownLoadFinishedFiles;

/**
 * 已下载的APK文件
 * @author CaiMeng
 *
 */
public class DownLoadApkFileInfo extends DownLoadFileInfo {

	public static DownLoadFinishedFiles getFileInfo(Context contex, String path) {
		AppInfo appInfo = getApkFileInfo(contex, path);
		float f = getFileLength(path);
		if(appInfo != null){
			return new DownLoadFinishedFiles(appInfo.getAppName(), f, appInfo.getAppIcon());
		}
		return null;
	}

	/**
	 * 获取未安装APK文件信息
	 * @param ctx
	 * @param apkPath
	 * @return
	 */
	public static AppInfo getApkFileInfo(Context ctx, String apkPath) {
		System.out.println(apkPath);
		File apkFile = new File(apkPath);
		if (!apkFile.exists() || !apkPath.toLowerCase().endsWith(".apk")) {
			System.out.println("文件路径不正确  或  非APK文件");
			return null;
		}
		AppInfo appInfoData;
		String PATH_PackageParser = "android.content.pm.PackageParser";
		String PATH_AssetManager = "android.content.res.AssetManager";
		try {
			// 反射得到pkgParserCls对象并实例化,有参数
			Class<?> pkgParserCls = Class.forName(PATH_PackageParser);
			Class<?>[] typeArgs = { String.class };
			Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);
			Object[] valueArgs = { apkPath };
			Object pkgParser = pkgParserCt.newInstance(valueArgs);

			// 从pkgParserCls类得到parsePackage方法
			DisplayMetrics metrics = new DisplayMetrics();
			metrics.setToDefaults();// 这个是与显示有关的, 这边使用默认
			typeArgs = new Class<?>[] { File.class, String.class,
					DisplayMetrics.class, int.class };
			Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(
					"parsePackage", typeArgs);

			valueArgs = new Object[] { new File(apkPath), apkPath, metrics, 0 };

			// 执行pkgParser_parsePackageMtd方法并返回
			Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
					valueArgs);

			// 从返回的对象得到名为"applicationInfo"的字段对象
			if (pkgParserPkg == null) {
				return null;
			}
			Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(
					"applicationInfo");

			// 从对象"pkgParserPkg"得到字段"appInfoFld"的值
			if (appInfoFld.get(pkgParserPkg) == null) {
				return null;
			}
			ApplicationInfo info = (ApplicationInfo) appInfoFld
					.get(pkgParserPkg);

			// 反射得到assetMagCls对象并实例化,无参
			Class<?> assetMagCls = Class.forName(PATH_AssetManager);
			Object assetMag = assetMagCls.newInstance();
			// 从assetMagCls类得到addAssetPath方法
			typeArgs = new Class[1];
			typeArgs[0] = String.class;
			Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(
					"addAssetPath", typeArgs);
			valueArgs = new Object[1];
			valueArgs[0] = apkPath;
			// 执行assetMag_addAssetPathMtd方法
			assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);

			// 得到Resources对象并实例化,有参数
			Resources res = ctx.getResources();
			typeArgs = new Class[3];
			typeArgs[0] = assetMag.getClass();
			typeArgs[1] = res.getDisplayMetrics().getClass();
			typeArgs[2] = res.getConfiguration().getClass();
			Constructor<Resources> resCt = Resources.class
					.getConstructor(typeArgs);
			valueArgs = new Object[3];
			valueArgs[0] = assetMag;
			valueArgs[1] = res.getDisplayMetrics();
			valueArgs[2] = res.getConfiguration();
			res = (Resources) resCt.newInstance(valueArgs);

			// 读取apk文件的信息
			appInfoData = new AppInfo();
			if (info != null) {
				if (info.icon != 0) {// 图片存在，则读取相关信息
					Drawable icon = res.getDrawable(info.icon);// 图标
					appInfoData.setAppIcon(icon);
				}
				if (info.labelRes != 0) {
					String name = (String) res.getText(info.labelRes);// 名字
					appInfoData.setAppName(name);
				} else {
					String apkName = apkFile.getName();
					appInfoData.setAppName(apkName.substring(0,
							apkName.lastIndexOf(".")));
				}
				String pkgName = info.packageName;// 包名
				appInfoData.setPackageName(pkgName);
			} else {
				return null;
			}
			PackageManager pm = ctx.getPackageManager();
			PackageInfo packageInfo = pm.getPackageArchiveInfo(apkPath,
					PackageManager.GET_ACTIVITIES);
			if (packageInfo != null) {
				// appInfoData.setAppversion(packageInfo.versionName);//版本号
				appInfoData.setVersionCode(packageInfo.versionCode);// 版本码
			}
			return appInfoData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

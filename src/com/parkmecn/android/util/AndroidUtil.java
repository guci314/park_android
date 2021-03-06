package com.parkmecn.android.util;

import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.parkmecn.android.CommonApplication;

/**
 * android系统相关常用操作
 * 
 * @author Zhoujun 说明：提供一些android系统常用操作：如系统版本，图片操作等
 */
public class AndroidUtil {

	/**
	 * 获取sdk版本
	 * 
	 * @return
	 */
	public static int getAndroidSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 返回当前程序版本号
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			// 这里的context.getPackageName()可以换成你要查看的程序的包名
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionCode;
	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "0.0.0";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			// 这里的context.getPackageName()可以换成你要查看的程序的包名
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * 获得设备识别认证码
	 * 
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) {
			return null;
		}
		return tm.getDeviceId();
	}

	/**
	 * 退出应用
	 * 
	 * @param context
	 */
	public static void exitApp(Context context) {
		List<Activity> list = ((CommonApplication) context.getApplicationContext()).getActivities();
		for (Activity ac : list) {
			ac.finish();
		}
		list.clear();
		System.gc();
		// 关闭service
		// Intent it = new Intent(context,RingService.class);
		// context.stopService(it);

	}

	/**
	 * 关闭软键盘
	 * 
	 * @param context
	 */
	public static void hideInputKeyboard(Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		final View v = ((Activity) context).getWindow().peekDecorView();
		imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 判断sd卡是否安装
	 * 
	 * @return
	 */
	public static boolean existSdcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得屏幕密度
	 * 
	 * @param activity
	 * @return
	 */
	public static float getDensity(Context context) {
		DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
		return dm.density;
	}

	/**
	 * 手机震动工具类
	 * 
	 * @author Administrator
	 * 
	 */

	/**
	 * final Context context ：调用该方法的Activity实例 long milliseconds ：震动的时长，单位是毫秒
	 * long[] pattern ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
	 * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
	 */
	public static void Vibrate(Context context, long milliseconds) {
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	public static void Vibrate(Context context, long[] pattern, boolean isRepeat) {
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}
}

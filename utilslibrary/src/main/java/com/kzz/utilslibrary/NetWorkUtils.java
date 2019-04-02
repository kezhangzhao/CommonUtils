package com.kzz.utilslibrary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class NetWorkUtils {

	/**
	 * 检查手机数据网络状态
	 * @param context 上下文
	 * @return true有网 false没网
	 */
	public static boolean checkMobileNet(Context context) {
		boolean isConnect=false;
		NetworkInfo netInfo = getManager(context).getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE);
		if(netInfo!=null){
			State state=netInfo.getState();
			if(state== State.CONNECTED || state== State.CONNECTING){
				isConnect=true;
			}
		}
		return isConnect;
	}

	/**
	 * 检查手机WIFI状态
	 * @param context 上下文
	 * @return true有WIFI false没WIFI
	 */
	public static boolean checkMobileWifi(Context context){
		boolean isConnect=false;
		NetworkInfo netInfo=getManager(context).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(netInfo!=null){
			State state=netInfo.getState();
			if(state== State.CONNECTED || state== State.CONNECTING){
				isConnect=true;
			}
		}
		return isConnect;
	}

	/**
	 * 检查是否有可用网络
	 * @param context 上下文
	 * @return 是否有可用网络（手机能不能上网）
	 */
	public static boolean isUsableNetWork(Context context){
		return checkMobileNet(context) || checkMobileWifi(context);
	}

	private static ConnectivityManager getManager(Context context) {
		return (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

}

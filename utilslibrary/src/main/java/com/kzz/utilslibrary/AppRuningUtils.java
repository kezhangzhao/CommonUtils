package com.kzz.utilslibrary;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * author : zhangzhao.ke
 * time   : 2018/11/21
 * desc   : APP运行工具
 */

public class AppRuningUtils {

    /**
     * 判断App是否处于前台
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        if (am != null) {
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            return !TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName());
        }
        return false;
    }

    /**
     * App是否处于后台
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean isRunningbackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals("com.bit.elevator")
                        && info.baseActivity.getPackageName().equals("com.bit.elevator")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将本应用置顶到最前端
     * 当本应用位于后台时，则将它切换到最前端
     *
     * @param mContext Activity
     * @param cls      备用跳转activity（若没有找到运行的task，用户结束了task或被系统释放，则重新启动该指定activity）
     */
    public static void setTopApp(Context mContext, @NonNull Class<?> cls) {
        if (!isRunningForeground(mContext)) {
            //获取ActivityManager
            ActivityManager activityManager = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
            //获得当前运行的task(任务)
            if (activityManager != null) {
                List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
                for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                    //找到本应用的 task，并将它切换到前台
                    if (taskInfo.topActivity.getPackageName().equals(mContext.getPackageName())) {
                        activityManager.moveTaskToFront(taskInfo.id, 0);
                        return;
                    }
                }
                Intent intent = new Intent();
                intent.setClass(mContext, cls);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        }
    }

}
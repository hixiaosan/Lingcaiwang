package com.bgf.lingcaiwang.ui;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.bgf.lingcaiwang.R;
import com.bgf.lingcaiwang.utils.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class BaseActivity extends FragmentActivity {

    public static final String TAG = "BaseActivity";

    public static boolean isActive = false;

    public static boolean isGetUser = false;

    /**
     * 进度条提示框，冻结屏幕，直到耗时操作完成
     */
    private AlertDialog mpDialog;
    /**
     * 进度条提示框提示信息TextView
     */
    private TextView mpDialogTextView;

    /**
     *
     */
    @SuppressWarnings("unused")
    private static ExecutorService mEsMaintenance;

    /**
     * 是否调用app外的其他服务
     */
    @SuppressWarnings("unused")
    private boolean isCallAnotherService;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        isActive = true;
        if (arg0 != null) {
            Logger.i(TAG, "-------- MainActivity saved bundle not null, restoring ---------");
        }
        initProgressDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) { // 注掉，用心的广播捕捉home�?
            isActive = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isActive) { // 从后台回来
            isActive = true;
            if (!isGetUser) { // 防止重复登录
                isGetUser = true;
                /*
                 * UserDataCenter.getInstance().get(new
				 * OnDataLoadListener<YLResult<User>>() {
				 * 
				 * @Override public void onPreExecute() {
				 * 
				 * }
				 * 
				 * @Override public void onPostExecute(YLResult<User> result) {
				 * if (result != null) { if (result.isRequestSuccess()) {
				 * UserDataCenter.getInstance().saveUserInfo(result); } }
				 * isGetUser = false; } }, true, this);
				 */
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        // unregisterReceiver(mBroadcastReceiver);//注销广播接收�?
        super.onDestroy();
    }

    /**
     * 程序是否在前台
     *
     * @return
     */
    private boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否锁屏
     *
     * @return
     */
    @SuppressWarnings("unused")
    private boolean isScreenOn() {
        // Returns a list of application processes that are running on the
        // device
        PowerManager manager = (PowerManager) getApplicationContext().getSystemService(Activity.POWER_SERVICE);
        return manager.isScreenOn();
    }

    /**
     * 初始化进度条提示
     */
    @SuppressLint("InflateParams")
    public void initProgressDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        mBuilder.setView(view);
        mpDialogTextView = (TextView) view.findViewById(R.id.progress_dialog_tv);
        mpDialog = mBuilder.create();
    }

    /**
     * 设置对话框中的提示信
     *
     * @param resid
     */
    public void setProgressDialogText(int resid) {
        if (mpDialogTextView != null) {
            mpDialogTextView.setText(resid);
        }
    }

    /**
     * 显示进度图标
     */
    public void showProgressDialog() {
        if (mpDialog != null && !mpDialog.isShowing()) {
            mpDialog.show();
        }
    }

    /**
     * 隐藏进度图标
     */
    public void hideProgressDialog() {
        if (mpDialog != null && mpDialog.isShowing()) {
            mpDialog.dismiss();
        }
    }
}

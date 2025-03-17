package com.my.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 一键锁屏小程序
 * <p>
 * Created by YJH on 2016/10/28 13:38.
 */
public class MainActivity extends AppCompatActivity {

    public static DevicePolicyManager policyManager;
    private static ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.translucent);    //设置透明的Activity
        transParentStatusBarAndBottomNavigationBar();   //透明状态栏和底部导航栏

        //获取设备管理服务
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        //AdminReceiver 继承自 DeviceAdminReceiver
        componentName = new ComponentName(this, AdminReceiver.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        lockScreen();
    }

    /**
     * 锁屏
     */
    private void lockScreen() {
        Log.i("MainActivity", "lockScreen");
        boolean active = policyManager.isAdminActive(componentName);
        if (!active) {   //若无权限
            activeManage();//去获得权限
        } else {
            setAppScreenBrightness(10);
            policyManager.lockNow();//直接锁屏
            //killSelf ，锁屏之后就立即kill掉我们的Activity，避免资源的浪费;
            killSelf();
        }
    }

    /**
     * 激活设备
     */
    private void activeManage() {
        //启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        //权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        //描述(additional explanation)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活后才可以使用锁屏功能 ^.^ \n激活后才可以使用锁屏功能 ^.^ \n激活后才可以使用锁屏功能 ^.^ \n激活后才可以使用锁屏功能 ^.^ \n激活后才可以使用锁屏功能 ^.^ ");

        startActivity(intent);
    }

    /**
     * 透明状态栏和底部导航栏
     */
    private void transParentStatusBarAndBottomNavigationBar() {
        View decorView = getWindow().getDecorView();  //获取到了当前界面的DecorView
        //SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION，表示会让应用的主体内容占用系统导航栏的空间
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        hideActionBar();
    }

    /**
     * 隐藏ActionBar效果
     */
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        } else {
            Log.e("hideActionBar", "ActionBar为空");
        }
    }

//    /**
//     * 1.获取系统默认屏幕亮度值 屏幕亮度值范围（0-255）
//     **/
//    private int getScreenBrightness(Context context) {
//        ContentResolver contentResolver = context.getContentResolver();
//        int defVal = 125;
//        return Settings.System.getInt(contentResolver,
//                Settings.System.SCREEN_BRIGHTNESS, defVal);
//    }

    /**
     * 2.设置 APP界面屏幕亮度值方法
     **/
    private void setAppScreenBrightness(int birghtessValue) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = birghtessValue / 255.0f;
        window.setAttributes(lp);
    }


    /**
     * kill自己
     */
    private void killSelf() {
        //killMyself ，锁屏之后就立即kill掉我们的Activity，避免资源的浪费;
        finish();
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

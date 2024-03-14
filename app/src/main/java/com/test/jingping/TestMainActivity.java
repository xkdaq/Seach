package com.test.jingping;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dalipan.search.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.e("xxxxx","解密结果====="+decrypts("RmFyUExDYUFpMWkxK2oza3lzYmp6QVN2N2xCUFpiVjFHRHpTYVdyWkpNWT0="));
//        Log.e("xxxxx","解密结果====="+decrypts("WWM1Y1gvdXRtZGJwMTNmMHNRR3RuVitwdjhEY1hmSHc="));
//        Log.e("xxxxx","解密结果====="+decrypts("c3VzQzMrSDlJSEM4NzFpZTZDcmJJZz09"));
//        Log.e("xxxxx","解密结果====="+decrypts("U3F3SWRxYnR1YnVyMXRkOW5YN2NrYnpTYlRWcno5blpJbUt2YjVWWEE3emxyMmpCS296TE13PT0="));
//        Log.e("xxxxx","解密结果====="+decrypts("alJQOXB5aXBzdVk5V2UvMitMRU9YODhGb3A4czJEOG0="));
//        Log.e("xxxxx","解密结果====="+decrypts("U3F3SWRxYnR1YnVyMXRkOW5YN2NrZlp2Q0xjMlFWMlR1WFoyT1B4M2Vjb04vbGJOV24wMEFRPT0="));
//        Log.e("xxxxx","解密结果====="+decrypts("U3F3SWRxYnR1YnVyMXRkOW5YN2NrYnpTYlRWcno5blpiWDZRUTh4Z3NPbmxyMmpCS296TE13PT0="));

        setContentView(R.layout.activity_test);

        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        });

        findViewById(R.id.btn_get2).setOnClickListener(v -> {
            //5.1+以上
            deleteFolder(); //删除之前的文件
            showUsageStats();
        });

        Log.e("xxxx", "=======" + isGrantedUsagePermission(this));

    }

    public static boolean isGrantedUsagePermission(@NonNull Context context) {
        boolean granted;
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }
        return granted;
    }

    private void showUsageStats() {

        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

//        Calendar calendar1 = Calendar.getInstance();
//        long endTime = calendar1.getTimeInMillis();
//        calendar1.add(Calendar.DAY_OF_YEAR, -1); // 查询过去一天的使用统计
//        long startTime = calendar1.getTimeInMillis();

        long startTime = Long.MIN_VALUE;
        long endTime = System.currentTimeMillis();

        List<UsageStats> stats1 = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, startTime, endTime);


        long currentTime = System.currentTimeMillis();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.MONTH, -1); // 查询过去一个月的使用统计
        long oneMonthAgo = calendar2.getTimeInMillis();

        List<UsageStats> stats2 = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_MONTHLY, oneMonthAgo, currentTime);


        Calendar calendar3 = Calendar.getInstance();
        long endTime3 = calendar3.getTimeInMillis();
        calendar3.add(Calendar.DAY_OF_WEEK, -1); // 查询过去一周的使用统计
        long startTime3 = calendar3.getTimeInMillis();

        List<UsageStats> stats3 = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_WEEKLY, startTime3, endTime3);


        List<UsageStats> stats = stats1;

        StringBuilder statsStr = new StringBuilder();


        try {

            JSONArray statsArray = new JSONArray();

            if (stats != null && !stats.isEmpty()) {
                for (UsageStats usageStats : stats) {
                    JSONObject statsObj = new JSONObject();

                    String packageName = usageStats.getPackageName();
                    //if("com.danaberlari.res".equals(packageName)) {
                    statsObj.put("包名 packageName", packageName);


                    int appLaunchCount = 0; //应用被拉起启动次数
                    int launchCount = 0;//应用前台启动次数(包括自己启动其他activity)
                    try {
                        appLaunchCount = (int) usageStats.getClass().getDeclaredMethod("getAppLaunchCount").invoke(usageStats);
                        launchCount = usageStats.getClass().getDeclaredField("mLaunchCount").getInt(usageStats);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    statsObj.put("应用被拉起启动次数 appLaunchCount", "" + appLaunchCount);
                    statsObj.put("应用前台启动次数 launchCount", "" + launchCount);

                    //long getAppLaunchCount = usageStats.getAppLaunchCount();
                    long getFirstTimeStamp = usageStats.getFirstTimeStamp();
                    statsObj.put("起始时间 firstTimeStamp", "" + getFirstTimeStamp);
                    //long lastTimeAnyComponentUsed = usageStats.getLastTimeAnyComponentUsed();

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        long lastTimeForegroundServiceUsed = usageStats.getLastTimeForegroundServiceUsed();
                        statsObj.put("前台服务最后使用时间 lastTimeForegroundServiceUsed", "" + lastTimeForegroundServiceUsed);
                    }
                    long lastTimeStamp = usageStats.getLastTimeStamp();
                    //long appLaunchCount = usageStats.getAppLaunchCount();
                    statsObj.put("结束时间 lastTimeStamp", "" + lastTimeStamp);

                    long lastTimeUsed = usageStats.getLastTimeUsed();
                    statsObj.put("最后一次使用时间 lastTimeUsed", "" + lastTimeUsed);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        long lastTimeVisible = usageStats.getLastTimeVisible();
                        statsObj.put("最后一次可见时间 lastTimeVisible", "" + lastTimeVisible);
                    }

                    long mTotalTimeInForeground = usageStats.getTotalTimeInForeground();
                    statsObj.put("所有前台活动的累计时间 mTotalTimeInForeground", "" + mTotalTimeInForeground);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        long getTotalTimeForegroundServiceUsed = usageStats.getTotalTimeForegroundServiceUsed();
                        statsObj.put("前台服务的累计运行时间 totalTimeForegroundServiceUsed", getTotalTimeForegroundServiceUsed);
                    }

                    long totalTimeInForeground = usageStats.getTotalTimeInForeground();
                    statsObj.put("应用程序在前台运行的总时间 totalTimeInForeground", totalTimeInForeground);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        long totalTimeVisible = usageStats.getTotalTimeVisible();
                        statsObj.put("应用程序可见总时间 totalTimeVisible", totalTimeVisible);
                    }
                    statsArray.put(statsObj);
                    //}
                }
                Log.e("xxxx", "数据大小====" + statsArray.length());
                Log.e("xxxx", "====" + statsArray);
                questionFile(statsArray.toString());
            } else {
                statsStr.append("No usage stats available.");
            }
        } catch (Exception e) {
        }
    }

    public void questionFile(String conent) {
        try {
            FileWriter writer = new FileWriter(getFilesDir() + "stats.txt", true);
            writer.write(conent + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFolder(){
        File file1 = new File( getFilesDir() + "data.txt");
        deleteFolder(file1);
    }

    public void deleteFolder(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFolder(files[i]);
            }
        }
        file.delete();
    }


    public static String decrypts(String encryptedText) {
        try {
            // 使用Base64解码密文
            byte[] t10 = Base64.getDecoder().decode(encryptedText);
            byte[] t11 = Base64.getDecoder().decode("aWp1c3g3bDQ="); //cash box
            //byte[] t11 = Base64.getDecoder().decode("YW1wbG9hbjY="); //AMP
            String str2 = new String(t10);
            try {
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(2, new SecretKeySpec(t11, "DES"));
                return new String(cipher.doFinal(Base64.getDecoder().decode(str2)), "utf-8");
            } catch (Exception e10) {
                e10.printStackTrace();
                return str2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}

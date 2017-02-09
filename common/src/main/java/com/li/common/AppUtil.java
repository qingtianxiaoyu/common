package com.li.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by liweifa on 2016/12/9.
 */

public class AppUtil {
    private final static String TAG = "AppUril";

    /**
     * @param context     ApplicationContext
     * @param packageName
     * @return
     */
    public int getSignature(Context context, String packageName) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        int sig = 0;
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature[] s = pi.signatures;
            sig = s[0].hashCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return sig;
    }

    /**
     * 查看应用是否处于调试状态
     *
     * @param context
     * @return
     */
    public boolean checkDebugging(Context context) {
        if ((context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            android.os.Process.killProcess(android.os.Process.myPid());//直接退出应用
            return true;
        } else if (android.os.Debug.isDebuggerConnected()) {
            android.os.Process.killProcess(android.os.Process.myPid());//直接退出应用
            return true;
        }
        return false;
    }


    public boolean isRunningInEmulator() {
        boolean qemuKernel = false;
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("getprop ro.kernel.qemu");
            os = new DataOutputStream(process.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            os.writeBytes("exit\n");//执行退出
            os.flush();               //刷新输入流
            process.waitFor();
            qemuKernel = (Integer.valueOf(in.readLine()) == 1);//判断ro.kernel.qemu属性值是否为1
            Log.d(TAG, "is running in Emulator");
        } catch (Exception e) {
            qemuKernel = false;
        }
        return qemuKernel;
    }

    /**
     * 校验保护
     * 检查classes.dex文件是否被重新编译过
     * 原理
     * 判断软件是否被重新打包过
     * 可以检查程序安装后classes.dex文件的hash值（MD5或CRC都可以）
     * android sdk中有专门处理zip压缩包以及获取CERC检验的方法
     * 所以我们直接利用其中的CRC作为校验算法
     * 每次编译代码后，软件的CRC都会改变，因为无法在代码中保存他的值来进行判断，
     * 文件的CRC校验值可以保存在Assert目录中的文件或者字符串资源中，
     * 也可以保存在网络上，软件运行是再联网读取
     *
     * @return
     */
    public boolean checkCRC(Context context, String s) {
        boolean beModified = false;
        long crc = Long.parseLong(s);
        ZipFile zf;
        try {
            zf = new ZipFile(context.getApplicationContext().getPackageCodePath());
            ZipEntry ze = zf.getEntry("classes.dex");
            if (ze.getCrc() == crc) {
                beModified = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return beModified;
    }
}

package com.li.common;

import android.app.Activity;

import java.util.Stack;

/**
 * activity堆栈式管理
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 */
public class AppManager {

    private Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                instance = new AppManager();
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.push(activity);
    }


    public void removeAll() {
        if (activityStack != null && !activityStack.empty()) {
            while (!activityStack.empty()) {
                getCurrentActivity().finish();

            }
            activityStack.clear();
        }

    }

    public Activity getCurrentActivity() {
        return activityStack.peek();
    }

    public Activity getBackActivity() {
        if (activityStack.size() >= 2) {
            return activityStack.get(activityStack.size() - 2);
        } else {
            return null;
        }
    }

    public void pop() {
        //activityStack.remove(activity);
        if (activityStack != null && !activityStack.empty()) {
            activityStack.pop();
        }
    }


}

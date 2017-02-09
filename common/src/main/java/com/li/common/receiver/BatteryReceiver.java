package com.li.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.li.watchdog.Logger;

public class BatteryReceiver extends BroadcastReceiver {
	private int batteryN; // 目前电量
	private int batteryV; // 电池电压
	private double batteryT; // 电池温度
	private int batteryStatus; // 电池状态
	private int batteryTemp; // 电池使用情况

	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
			batteryN = intent.getIntExtra("level", 0); // 目前电量
			batteryV = intent.getIntExtra("voltage", 0); // 电池电压
			batteryT = intent.getIntExtra("temperature", 0); // 电池温度

			batteryStatus = intent.getIntExtra("status",
					BatteryManager.BATTERY_STATUS_UNKNOWN);
			batteryTemp = intent.getIntExtra("health",
					BatteryManager.BATTERY_HEALTH_UNKNOWN);

			Logger.d(
					"目前电量为" + batteryN + "% --- " + batteryStatus + "\n"
							+ "电压为" + batteryV + "mV --- " + batteryTemp + "\n"
							+ "温度为" + (batteryT * 0.1) + "℃");
			// 如果其中一个有问题。就说明这台手机有问题 可能是模拟器
			if (batteryN == 0 || batteryV == 0 || (batteryT * 0.1) == 0
					|| batteryStatus == BatteryManager.BATTERY_STATUS_UNKNOWN
					|| batteryTemp == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
			} else {
			}
			

		}
	}
}

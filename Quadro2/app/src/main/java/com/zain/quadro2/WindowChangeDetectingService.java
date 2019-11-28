package com.zain.quadro2;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class WindowChangeDetectingService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        //Configure these here for compatibility with API 13 and below.
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;


        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null && event.getClassName() != null) {
                ComponentName componentName = new ComponentName(
                        event.getPackageName().toString(),
                        event.getClassName().toString()
                );

                ActivityInfo activityInfo = tryGetActivity(componentName);
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);



                boolean isActivity = activityInfo != null;
                if (isActivity)
                    Log.i("CurrentActivity", componentName.flattenToShortString());
                if (componentName.flattenToShortString().equals("com.facebook.katana/.activity.FbMainTabActivity")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "facebook \n CPU will be adjusted to medium preformance", Toast.LENGTH_LONG);
                    toast.show();
                    //wifiManager.setWifiEnabled(true);


                }
                else if (batteryManager.isCharging()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "charging", Toast.LENGTH_SHORT);
                    toast.show();

                }else if (componentName.flattenToShortString().equals("com.whatsapp/.Conversation")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "whatsapp \n CPU will be adjusted to medium preformance", Toast.LENGTH_LONG);
                    toast.show();
                    //wifiManager.setWifiEnabled(true);

                } else if (componentName.flattenToShortString().equals("com.google.android.apps.maps/com.google.android.maps.MapsActivity")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Trip Mode Activated \n CPU will be adjusted to minimum preformance", Toast.LENGTH_LONG);
                    toast.show();
                    //wifiManager.setWifiEnabled(false);


                } else if (componentName.flattenToShortString().equals("com.google.android.youtube/com.google.android.apps.youtube.app.WatchWhileActivity")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Video Mode Activated  \n CPU will be adjusted to medium preformance", Toast.LENGTH_LONG);
                    toast.show();
                    //wifiManager.setWifiEnabled(true);

                } else if (componentName.flattenToShortString().equals("com.sec.android.app.music/.common.activity.MusicMainActivity")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Music On \n CPU will be adjusted to minimum preformance", Toast.LENGTH_LONG);
                    toast.show();
                    //wifiManager.setWifiEnabled(true);


                } else if (componentName.flattenToShortString().equals("com.supercell.clashofclans/.GameApp")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "game mode On \n CPU will be adjusted to Maximum preformance", Toast.LENGTH_LONG);
                    toast.show();


                }else if (!powerManager.isInteractive()) {
                    //wifiManager.setWifiEnabled(false);
                }  else {
                    //wifiManager.setWifiEnabled(true);
                }


            }

        }
    }


    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt() {}
}

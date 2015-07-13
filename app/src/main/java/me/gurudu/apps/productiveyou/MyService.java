package me.gurudu.apps.productiveyou;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    Context ctx;
    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this,"Service Started", Toast.LENGTH_LONG).show();

        final String str = "";
        Timer timer  =  new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int phonelaunched = 0,phoneclosed =0;
            int phonelaunches = 1;
            @Override
            public void run() {
                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

                for ( ActivityManager.RunningAppProcessInfo appProcess: runningAppProcessInfo ) {
                    Log.d(appProcess.processName.toString(),"is running");
                    if (appProcess.processName.equals("com.android.dialer")) {
                        if ( appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND  /*isForeground(getApplicationContext(),runningAppProcessInfo.get(i).processName)*/){
                            if (phonelaunched == 0 ){
                                phonelaunched = 1;
                                Log.d(str,"dude phone has been launched");
                            }
                            else if (phoneclosed == 1){
                                phonelaunches++;
                                phoneclosed = 0;
                                Log.d(String.valueOf(phonelaunches),"dude that was counter");
                            }
                        }
                        else {
                            phoneclosed = 1;
                            Log.d(str,"dude phone has been closed");

                        }
                    }
                }
            }
        },2000,3000);

        return START_STICKY;
    }
    

    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

}

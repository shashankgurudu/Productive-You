package me.gurudu.apps.productiveyou;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {


    static DBHelper db;
    Context context;
    @Override public void onCreate() {
        context = getApplicationContext();
        this.db = new DBHelper(context);

    }


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
           // int launched = 0,closed =0;
           // int launches = 1;
            @Override
            public void run() {
                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

                for ( ActivityManager.RunningAppProcessInfo appProcess: runningAppProcessInfo ) {
                    Log.d(appProcess.processName.toString(), "is running");
                    Cursor s = db.getData(appProcess.processName);
                    try {
                            s.moveToFirst();
                            String packagename = s.getString(s.getColumnIndex(DBHelper.CONTACTS_COLUMN_PKNAME));
                            Log.d(packagename, "is retreived packagename");
                            int claunches = s.getInt(s.getColumnIndex(DBHelper.CONTACTS_COLUMN_LAUNCHES));
                            int claunched = s.getInt(s.getColumnIndex(DBHelper.CONTACTS_COLUMN_LAUNCHED));
                            int cclosed = s.getInt(s.getColumnIndex(DBHelper.CONTACTS_COLUMN_CLOSED));

                            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                                Log.d(appProcess.processName, " is foreground app");


                                Log.d(packagename, "is current package");
                                if (claunched == 0) {
                                    claunched = 1;
                                    Log.d(appProcess.processName, "dude  has been launched");
                                    db.updatelaunches(appProcess.processName, claunches, claunched, cclosed);
                                    Log.d(Integer.toString(claunched), "launched");
                                    s.close();

                                } else if (cclosed == 1) {
                                    claunches++;
                                    cclosed = 0;
                                    Log.d(String.valueOf(claunches), "dude that was counter");
                                    db.updatelaunches(appProcess.processName, claunches, claunched, cclosed);
                                    Log.d(Integer.toString(claunches), "number of launches");
                                    s.close();
                                }
                            } else if (claunched == 1) {

                                cclosed = 1;
                                Log.d(appProcess.processName, "dude has been closed");

                                Log.d(Integer.toString(cclosed), " is cclosed");
                                db.updatelaunches(appProcess.processName, claunches, claunched, cclosed);
                                Log.d("dude ", "updated");
                                s.close();
                            }
                    } catch (Exception e) {

                        Log.e("","process not found : " ,e);
                        db.insertlaunches(appProcess.processName.toString(), 0, 0, 0);
                        Log.d(" inserted in catch", "block");
                    }
                }
            }
        },2000,3000);

        return START_STICKY;
    }


    public void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(uiUpdated);
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

}
/*isForeground(getApplicationContext(),runningAppProcessInfo.get(i).processName)*/
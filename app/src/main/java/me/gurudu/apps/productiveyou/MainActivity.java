package me.gurudu.apps.productiveyou;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.TrafficStats;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity {


    DBHelper db = new DBHelper(MainActivity.this);
    public static ArrayList al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int launches = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        al = new <String>ArrayList();
        //db.insertlaunches("com.android.dialer", 0,0,0);
       ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo appProcess : runningAppProcessInfo) {
            //al.add(appProcess.processName.toString());
            db.insertlaunches(appProcess.processName.toString(), 0,0,0);
        }

       /* ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = getPackageManager();
        final List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            CharSequence appName = null;
            try {
                appName = pm.getApplicationLabel(pm.getApplicationInfo(processInfo.processName, PackageManager.GET_META_DATA));
                Log.d(appName.toString()," is package name");

            } catch (PackageManager.NameNotFoundException e) {
                Log.e("","Application info not found for process : " + processInfo.processName,e);
            }
        }*/

    }

    public void startService(View view){
        startService(new Intent(getBaseContext(), MyService.class));
    }

    public void stopService(View view){
        stopService(new Intent(getBaseContext(), MyService.class));
    }

     public void onResume() {
        super.onResume();

        Cursor a = db.getDatWithoutpackage();
         a.moveToFirst();
        int rs = db.numberOfRows();
        for (int i =0;i<rs;i++) {
            String packagename = a.getString(a.getColumnIndex(DBHelper.CONTACTS_COLUMN_PKNAME));
            String launchescounter = a.getString(a.getColumnIndex(DBHelper.CONTACTS_COLUMN_LAUNCHES));

            Log.d(packagename, " is the packagename");
            Log.d(launchescounter," is the counter of the above packagename");
            a.moveToNext();
           /* TextView appname = (TextView) findViewById(R.id.Appname);
            appname.setText(packagename);
            TextView launches = (TextView) findViewById(R.id.Launches);
            launches.setText(launchescounter);*/

        }
    }
}

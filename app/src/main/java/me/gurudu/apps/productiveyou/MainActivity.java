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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private static MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<String> packages;
    private static ArrayList<String> launches;

    private static ArrayList<Integer> removedItems;


    DBHelper db = new DBHelper(MainActivity.this);
    public static ArrayList al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int launches = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo appProcess : runningAppProcessInfo) {
            //al.add(appProcess.processName.toString());
            db.insertlaunches(appProcess.processName.toString(), 0,0,0);
        }



    }

    public void startService(View view){
        startService(new Intent(getBaseContext(), MyService.class));
    }

    public void stopService(View view){
        stopService(new Intent(getBaseContext(), MyService.class));
    }

     public void onResume() {
        super.onResume();
         recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
         recyclerView.setHasFixedSize(true);

         layoutManager = new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);

         packages = db.getAllpackages();
         launches = db.getAlllaunches();

         adapter = new MyAdapter(packages,launches);
         recyclerView.setAdapter(adapter);
    }
}
/*
Cursor a = db.getDatWithoutpackage();
         a.moveToFirst();
        int rs = db.numberOfRows();
        for (int i =0;i<rs;i++) {
            String packagename = a.getString(a.getColumnIndex(DBHelper.CONTACTS_COLUMN_PKNAME));
            String launchescounter = a.getString(a.getColumnIndex(DBHelper.CONTACTS_COLUMN_LAUNCHES));

            Log.d(packagename, " is the packagename");
            Log.d(launchescounter," is the counter of the above packagename");
            a.moveToNext();

        }
 */
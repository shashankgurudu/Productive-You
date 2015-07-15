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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity {


    DBHelper db = new DBHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int launches = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db.insertlaunches("com.android.dialer",0);
    }

    public void startService(View view){
            startService(new Intent(getBaseContext(), MyService.class));
    }

    public void stopService(View view){
            stopService(new Intent(getBaseContext(), MyService.class));
    }

    public void onResume() {
        super.onResume();

        Cursor rs = db.getData("com.android.dialer");
        rs.moveToFirst();
        String packagename = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PKNAME));
        String launchescounter = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_LAUCHES));

            TextView appname = (TextView)findViewById(R.id.Appname);
            appname.setText(packagename);
            TextView launches = (TextView)findViewById(R.id.Launches);
            launches.setText(launchescounter);

    }


}

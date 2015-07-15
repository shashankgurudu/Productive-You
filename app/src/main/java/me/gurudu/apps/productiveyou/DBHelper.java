package me.gurudu.apps.productiveyou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// this i database helper
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "applaunches";
    public static final String CONTACTS_TABLE_NAME = "applaunches";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_PKNAME = "packagename";
    public static final String CONTACTS_COLUMN_LAUNCHES = "launches";
    public static final String CONTACTS_COLUMN_LAUNCHED = "launched";
    public static final String CONTACTS_COLUMN_CLOSED = "closed";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method
        db.execSQL(
                "create table if not exists applaunches " +
                        "(id integer primary key, packagename text, launches int, launched int, closed int)"
        );
        Log.d("", "Table created... "); // is it getting printed? no
    }

    public boolean insertlaunches(String packagename,int launches,int launched, int closed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("packagename",packagename);
        contentValues.put("launches", launches);
        contentValues.put("launched", launched);
        contentValues.put("closed", closed);
        db.insert("applaunches", null, contentValues);
        return true;
    }

    public Cursor getData(String packagename){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from applaunches where packagename='"+packagename+"'", null );
       // Cursor res = db.rawQuery("select * from applaunches",null);
        if (res != null){
            return res;
        }
        else {
            return null;
        }
   }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }


    public boolean updatelaunches (String packagename, int launches,int launched,int closed)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("launched", launched);
        contentValues.put("closed", closed);
        db.update("applaunches", contentValues, "packagename = ? ", new String[]{packagename});
        db.execSQL("UPDATE applaunches SET launches = launches +1 WHERE packagename = ?", new String[] {packagename});
        return true;
    }


    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from applaunches", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PKNAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
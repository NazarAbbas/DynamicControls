package com.vdoers.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class LibraryDatabaseHelper extends SQLiteOpenHelper {

    // common column
    public static final String Row_ID = BaseColumns._ID;

    //tbl state city
    public static final String TBL_STATE_CITY_LIST = "tbl_state_city_list";
    public static final String COL_ACTION = "action";
    public static final String COL_CITY_ID = "city_id";
    public static final String COL_CITY_NAME = "city_name";
    public static final String COL_STATE_ID = "state_id";
    public static final String COL_STATE_NAME = "state_name";
    public static final String COL_STATE_CITY_LIST_DATA = "state_city_list_data";
    public static final String COL_CREATED_DATE = "created_date";


    private static final String DB_NAME
            = "city_state_Db";
    private static final int DATABASE_VERSION = 1;

    public LibraryDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            createTables(db);
            Thread.sleep(1000);
            //execSQL(context, db);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void createTables(SQLiteDatabase db) {
        createTable_state_city_List(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void close(SQLiteDatabase db) {
        try {
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void close(LibraryDatabaseHelper dbHelper) {
        if (dbHelper != null) {
            try {
                dbHelper.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private void createTable_state_city_List(SQLiteDatabase db) {
        try {
            String str_xml = "CREATE TABLE " + TBL_STATE_CITY_LIST + "("
                    + Row_ID + " integer primary key autoincrement, "
                    + COL_CITY_ID + " integer, "
                    + COL_CITY_NAME + " varchar, "
                    + COL_STATE_ID + " integer, "
                    + COL_STATE_NAME + " varchar, "
                    + COL_ACTION + " varchar, "
                    + COL_STATE_CITY_LIST_DATA + " varchar, "
                    + COL_CREATED_DATE + " DATETIME DEFAULT CURRENT_DATE)";

            db.execSQL(str_xml);

            Log.d("Table: ", "Table creation query: " + str_xml);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

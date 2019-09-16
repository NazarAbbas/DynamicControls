package commdel.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // common column
    public static final String Row_ID = BaseColumns._ID;
    public static final String col_id = BaseColumns._ID;
    // tbl image
    public static final String TBL_FILE = "tbl_image";
    public static final String COL_FILE_NAME = "file_name";
    public static final String COL_FILE_PATH = "file_path";
    public static final String COL_CREATED_DATE = "created_date";


    //tbl Json workfloe
    public static final String TBL_JSON_WORKFLOW_INFO = "tbl_json_workflow_info";
    public static final String COL_JSON_WORKFLOW_ID = "workflow_Id";
    public static final String COL_JSON_WORKFLOW_INFORMATION = "json_workflow_info_information";
    public static final String COL_ID = "id";

    private static final String DB_NAME
            = "mSales_Db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
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

        createTableFiles(db);
        createTable_json_workflow(db);

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

    public static void close(DatabaseHelper dbHelper) {
        if (dbHelper != null) {
            try {
                dbHelper.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private void createTableFiles(SQLiteDatabase db) {
        try {
            String str_image = "CREATE TABLE " + TBL_FILE + "("
                    + Row_ID + " integer primary key autoincrement, "
                    + COL_FILE_NAME + " varchar, "
                    + COL_FILE_PATH + " varchar, "
                    + COL_CREATED_DATE + " DATETIME DEFAULT CURRENT_DATE)";
            db.execSQL(str_image);
            Log.d("Table: ", "Table creation query: " + str_image);
        } catch (Exception ex) {

        }

    }

    private void createTable_json_workflow(SQLiteDatabase db) {
        try {
            String str_xml = "CREATE TABLE " + TBL_JSON_WORKFLOW_INFO + "(" +
                    Row_ID + " integer primary key autoincrement, "
                    + COL_JSON_WORKFLOW_ID + " integer,"
                    + COL_ID + " varchar,"
                    + COL_JSON_WORKFLOW_INFORMATION + " varchar,"
                    + COL_CREATED_DATE + " DATETIME DEFAULT CURRENT_DATE)";

            db.execSQL(str_xml);
            Log.d("Table: ", "Table creation query: " + str_xml);
        } catch (Exception ex) {

        }

    }

}

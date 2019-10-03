package commdel.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class FileDBHandler {
    public static void saveFile(Context context, FileSavedModel fileSavedModel) {
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        deleteFile(context, fileSavedModel.getFileName());
        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.COL_FILE_NAME, fileSavedModel.getFileName());
            contentValues.put(DatabaseHelper.COL_FILE_PATH, fileSavedModel.getFilePath());
            contentValues.put(DatabaseHelper.COL_CASE_ID, fileSavedModel.getCaseId());
            sqdb.insert(DatabaseHelper.TBL_FILE, null, contentValues);

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
    }


    public static List<FileSavedModel> getUnsendFileList(Context context) {
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        Cursor cursor = null;
        List<FileSavedModel> fileSavedModelsList = new ArrayList<>();
        FileSavedModel fileSavedModels;
        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getReadableDatabase();
            cursor = sqdb.query(DatabaseHelper.TBL_FILE, new String[]{DatabaseHelper.COL_FILE_NAME, DatabaseHelper.COL_FILE_PATH}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                fileSavedModels = new FileSavedModel();
                fileSavedModels.setFileName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FILE_NAME)));
                fileSavedModels.setFilePath(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FILE_PATH)));
                fileSavedModelsList.add(fileSavedModels);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //MyApplication.getInstance().trackException(ex);
        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
        return fileSavedModelsList;
    }

    public static void deleteFile(Context context, String fileName) {
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            sqdb.delete(DatabaseHelper.TBL_FILE, DatabaseHelper.COL_FILE_NAME + "='" + fileName + "'", null);
        } catch (Exception ex) {
            ex.printStackTrace();
            //MyApplication.getInstance().trackException(ex);

        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
    }


    public static LinkedList<Integer> getUnsendFileIds(Context context) {
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        Cursor cursor = null;
        LinkedList<Integer> finalList = new LinkedList<>();
        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            cursor = sqdb.query(DatabaseHelper.TBL_FILE, new String[]{DatabaseHelper.Row_ID}, null, null, null, null, null);

            while (cursor.moveToNext()) {
                finalList.add(cursor.getInt(0));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //MyApplication.getInstance().trackException(ex);

        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }

        return finalList;
    }


    public static FileSavedModel getFileDetails(Context context, int rowId) {
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        Cursor cursor = null;
        FileSavedModel fileSavedModel = null;
        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            cursor = sqdb.query(DatabaseHelper.TBL_FILE, new String[]{DatabaseHelper.COL_FILE_NAME,
                            DatabaseHelper.COL_FILE_PATH, DatabaseHelper.COL_CASE_ID},
                    DatabaseHelper.Row_ID + "=" + rowId, null, null, null, null);
            while (cursor.moveToNext()) {
                fileSavedModel = new FileSavedModel();
                fileSavedModel.setFileName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FILE_NAME)));
                fileSavedModel.setFilePath(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FILE_PATH)));
                fileSavedModel.setCaseId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CASE_ID)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // MyApplication.getInstance().trackException(ex);
        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
        return fileSavedModel;
    }


    public static int getNumberOfRows(Context context) {
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        Cursor cursor = null;
        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            cursor = sqdb.query(DatabaseHelper.TBL_FILE, new String[]{"count(*)"}, null, null, null, null, null);
            if (cursor.moveToNext()) {
                return cursor.getInt(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //MyApplication.getInstance().trackException(ex);

        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
        return 0;
    }


}

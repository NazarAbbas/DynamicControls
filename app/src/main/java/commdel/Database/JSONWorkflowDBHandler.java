package commdel.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import com.vdoers.dynamiccontrolslibrary.mControls.contols.JsonWorkflowList;

import java.util.ArrayList;
import java.util.List;


public class JSONWorkflowDBHandler {


    public static void saveData(Context context, List<JsonWorkflowList> jsonWorkflowList) {
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            Gson gson = new Gson();
            for (int i = 0; i < jsonWorkflowList.size(); i++) {
                String jsonWorkflowInformation = gson.toJson(jsonWorkflowList.get(i));
                contentValues.put(DatabaseHelper.COL_JSON_WORKFLOW_ID, jsonWorkflowList.get(i).getWorkflowId());
                contentValues.put(DatabaseHelper.COL_ID, jsonWorkflowList.get(i).getId());
                contentValues.put(DatabaseHelper.COL_JSON_WORKFLOW_INFORMATION, jsonWorkflowInformation);
                sqdb.insert(DatabaseHelper.TBL_JSON_WORKFLOW_INFO, null, contentValues);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            // MyApplication.getInstance().trackException(ex);
        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
    }


    public static JsonWorkflowList getJsonWorkFlow(Context context, int workflowId, String id) {
        JsonWorkflowList workflowList = null;
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        Cursor cursor = null;

        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            String query = "Select " + DatabaseHelper.COL_JSON_WORKFLOW_INFORMATION
                    + " from " + DatabaseHelper.TBL_JSON_WORKFLOW_INFO +
                    " where "
                    + DatabaseHelper.COL_ID + "='" + id
                    + "' and " + DatabaseHelper.COL_JSON_WORKFLOW_ID + "=" + workflowId;

            //+ DatabaseHelper.COL_JSON_WORKFLOW_ID + "=" + workflowId;
            cursor = sqdb.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_JSON_WORKFLOW_INFORMATION));
                Gson gson = new Gson();
                workflowList = gson.fromJson(value, JsonWorkflowList.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
        return workflowList;
    }

    public static JsonWorkflowList getJsonWorkFlowByWorkflowId(Context context, int workflowId) {
        JsonWorkflowList workflowList = null;
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        Cursor cursor = null;

        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            String query = "Select " + DatabaseHelper.COL_JSON_WORKFLOW_INFORMATION
                    + " from " + DatabaseHelper.TBL_JSON_WORKFLOW_INFO +
                    " where "
                    + DatabaseHelper.COL_JSON_WORKFLOW_ID + "=" + workflowId;

            //+ DatabaseHelper.COL_JSON_WORKFLOW_ID + "=" + workflowId;
            cursor = sqdb.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_JSON_WORKFLOW_INFORMATION));
                Gson gson = new Gson();
                workflowList = gson.fromJson(value, JsonWorkflowList.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
        return workflowList;
    }


    public static ArrayList<JsonWorkflowList> getJsonWorkFlow(Context context, int workflowId) {
        ArrayList<JsonWorkflowList> jsonWorkflowList = new ArrayList<>();
        JsonWorkflowList workflowList = null;
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        Cursor cursor = null;

        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            String query = "Select " + DatabaseHelper.COL_JSON_WORKFLOW_INFORMATION
                    + " from " + DatabaseHelper.TBL_JSON_WORKFLOW_INFO +
                    " where "
                    + DatabaseHelper.COL_JSON_WORKFLOW_ID + "=" + workflowId;
            cursor = sqdb.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_JSON_WORKFLOW_INFORMATION));
                Gson gson = new Gson();
                workflowList = gson.fromJson(value, JsonWorkflowList.class);
                jsonWorkflowList.add(workflowList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
        return jsonWorkflowList;
    }


    public static int delete(Context context) {
        DatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        try {
            dbHelper = new DatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            return sqdb.delete(DatabaseHelper.TBL_JSON_WORKFLOW_INFO, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            DatabaseHelper.close(sqdb);
            DatabaseHelper.close(dbHelper);
        }
        return -1;
    }
}

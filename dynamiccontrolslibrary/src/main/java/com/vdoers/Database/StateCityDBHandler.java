package com.vdoers.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.vdoers.Models.StateCityList;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.JsonWorkflowList;
import com.vdoers.dynamiccontrolslibrary.mControls.models.DynamicSpinnerModel;

import java.util.ArrayList;
import java.util.List;


public class StateCityDBHandler {

    public static void save(Context context, List<StateCityList> stateCityList) {
        LibraryDatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;

        try {
            dbHelper = new LibraryDatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            for (int i = 0; i < stateCityList.size(); i++) {
                Gson gson = new Gson();
                String information = gson.toJson(stateCityList.get(i));
                ContentValues contentValues = new ContentValues();
                contentValues.put(LibraryDatabaseHelper.COL_STATE_ID, stateCityList.get(i).getStateId());
                contentValues.put(LibraryDatabaseHelper.COL_STATE_NAME, stateCityList.get(i).getStateName());
                contentValues.put(LibraryDatabaseHelper.COL_CITY_ID, stateCityList.get(i).getCityId());
                contentValues.put(LibraryDatabaseHelper.COL_CITY_NAME, stateCityList.get(i).getCityName());
                contentValues.put(LibraryDatabaseHelper.COL_ACTION, stateCityList.get(i).getAction());
                contentValues.put(LibraryDatabaseHelper.COL_STATE_CITY_LIST_DATA, information);
                sqdb.insertOrThrow(LibraryDatabaseHelper.TBL_STATE_CITY_LIST, null, contentValues);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            LibraryDatabaseHelper.close(sqdb);
            LibraryDatabaseHelper.close(dbHelper);
        }
    }


    public static List<DynamicSpinnerModel> getSpinnerStateValue(Context context, int stateId) {
        List<DynamicSpinnerModel> spinnerModelList = new ArrayList<>();
        StateCityList stateCityList;
        Cursor cursor = null;
        LibraryDatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        try {
            dbHelper = new LibraryDatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            String query = "Select " + LibraryDatabaseHelper.COL_STATE_CITY_LIST_DATA
                    + " from " + LibraryDatabaseHelper.TBL_STATE_CITY_LIST +
                    " where " + LibraryDatabaseHelper.COL_STATE_ID + "=" + stateId;
            cursor = sqdb.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(LibraryDatabaseHelper.COL_STATE_CITY_LIST_DATA));
                Gson gson = new Gson();
                stateCityList = gson.fromJson(value, StateCityList.class);
                DynamicSpinnerModel spinnerModel = new DynamicSpinnerModel();
                spinnerModel.setId(stateCityList.getStateId() + "");
                spinnerModel.setValue(stateCityList.getStateName());
                spinnerModelList.add(spinnerModel);
                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            LibraryDatabaseHelper.close(sqdb);
        }
        DynamicSpinnerModel spinnerModel = new DynamicSpinnerModel();
        spinnerModel.setId("-1");
        spinnerModel.setValue("Please select state");
        spinnerModelList.add(0, spinnerModel);
        return spinnerModelList;
    }

    public static List<JsonWorkflowList.OptionList> getSpinnerState(Context context) {
        List<JsonWorkflowList.OptionList> spinnerModelList = new ArrayList<>();
        StateCityList stateCityList;
        Cursor cursor = null;
        LibraryDatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        try {
            dbHelper = new LibraryDatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            String query = "Select " + LibraryDatabaseHelper.COL_STATE_CITY_LIST_DATA
                    + " from " + LibraryDatabaseHelper.TBL_STATE_CITY_LIST;
            cursor = sqdb.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(LibraryDatabaseHelper.COL_STATE_CITY_LIST_DATA));
                Gson gson = new Gson();
                stateCityList = gson.fromJson(value, StateCityList.class);
                JsonWorkflowList.OptionList spinnerModel = new JsonWorkflowList().new OptionList();
                spinnerModel.setId(stateCityList.getStateId() + "");
                spinnerModel.setValue(stateCityList.getStateName());
                spinnerModelList.add(spinnerModel);
                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            LibraryDatabaseHelper.close(sqdb);
        }
    /*    JsonWorkflowList.OptionList spinnerModel = new JsonWorkflowList().new OptionList();
        spinnerModel.setId("-1");
        spinnerModel.setValue("Please select state");
        spinnerModelList.add(0, spinnerModel);*/
        return spinnerModelList;
    }

    public static List<JsonWorkflowList.OptionList> getSpinnerAllCities(Context context) {
        List<JsonWorkflowList.OptionList> spinnerModelList = new ArrayList<>();
        StateCityList stateCityList;
        Cursor cursor = null;
        LibraryDatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        try {
            dbHelper = new LibraryDatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            String query = "Select " + LibraryDatabaseHelper.COL_STATE_CITY_LIST_DATA
                    + " from " + LibraryDatabaseHelper.TBL_STATE_CITY_LIST;
            cursor = sqdb.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(LibraryDatabaseHelper.COL_STATE_CITY_LIST_DATA));
                Gson gson = new Gson();
                stateCityList = gson.fromJson(value, StateCityList.class);
                JsonWorkflowList.OptionList spinnerModel = new JsonWorkflowList().new OptionList();
                spinnerModel.setId(stateCityList.getCityId() + "");
                spinnerModel.setValue(stateCityList.getCityName());
                spinnerModelList.add(spinnerModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            LibraryDatabaseHelper.close(sqdb);
        }
        return spinnerModelList;
    }


    public static List<DynamicSpinnerModel> getSpinnerCityValue(Context context, int stateId) {
        List<DynamicSpinnerModel> spinnerModelList = new ArrayList<>();
        StateCityList stateCityList;
        Cursor cursor = null;
        LibraryDatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        try {
            dbHelper = new LibraryDatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            String query = "Select " + LibraryDatabaseHelper.COL_STATE_CITY_LIST_DATA
                    + " from " + LibraryDatabaseHelper.TBL_STATE_CITY_LIST +
                    " where " + LibraryDatabaseHelper.COL_STATE_ID + "=" + stateId;
            cursor = sqdb.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(LibraryDatabaseHelper.COL_STATE_CITY_LIST_DATA));
                Gson gson = new Gson();
                stateCityList = gson.fromJson(value, StateCityList.class);
                DynamicSpinnerModel spinnerModel = new DynamicSpinnerModel();
                spinnerModel.setId(stateCityList.getCityId() + "");
                spinnerModel.setValue(stateCityList.getCityName());
                spinnerModelList.add(spinnerModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            LibraryDatabaseHelper.close(sqdb);
        }
        return spinnerModelList;
    }


    public static void deleteData(Context context) {
        LibraryDatabaseHelper dbHelper = null;
        SQLiteDatabase sqdb = null;
        try {
            dbHelper = new LibraryDatabaseHelper(context);
            sqdb = dbHelper.getWritableDatabase();
            sqdb.delete(LibraryDatabaseHelper.TBL_STATE_CITY_LIST, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            LibraryDatabaseHelper.close(sqdb);
            LibraryDatabaseHelper.close(dbHelper);
        }
    }

}


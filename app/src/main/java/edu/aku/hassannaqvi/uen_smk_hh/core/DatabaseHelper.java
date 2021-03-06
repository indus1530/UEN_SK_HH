package edu.aku.hassannaqvi.uen_smk_hh.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.aku.hassannaqvi.uen_smk_hh.contracts.AdolscentContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.AdolscentContract.SingleAdolscent;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.BLRandomContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.BLRandomContract.RandomHH;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.ChildContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.DistrictContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.DistrictContract.DistrictTable;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.EnumBlockContract.EnumBlockTable;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.FamilyMembersContract.SingleMember;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.KishMWRAContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.KishMWRAContract.SingleKishMWRA;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.MWRAContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.MWRAContract.MWRATable;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.MWRA_PREContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.MWRA_PREContract.SingleMWRAPRE;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.MortalityContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.MortalityContract.SingleMortality;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.UsersContract;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.VersionAppContract;

import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.DATABASE_VERSION;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_ADOLSCENT_TABLE;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_BL_RANDOM;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_CHILD_TABLE;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_DISTRICTS;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_FAMILY_MEMBERS;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_FORMS;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_KISH_TABLE;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_MWRAPRE_TABLE;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_MWRA_TABLE;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_PSU_TABLE;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.uen_smk_hh.utils.CreateTable.SQL_CREATE_VERSIONAPP;


/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public String spDateT = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date().getTime());

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_FORMS);
        db.execSQL(SQL_CREATE_PSU_TABLE);
        db.execSQL(SQL_CREATE_BL_RANDOM);
        db.execSQL(SQL_CREATE_DISTRICTS);
        db.execSQL(SQL_CREATE_VERSIONAPP);
        db.execSQL(SQL_CREATE_FAMILY_MEMBERS);
        db.execSQL(SQL_CREATE_KISH_TABLE);
        db.execSQL(SQL_CREATE_MWRA_TABLE);
        db.execSQL(SQL_CREATE_MWRAPRE_TABLE);
        db.execSQL(SQL_CREATE_CHILD_TABLE);
        db.execSQL(SQL_CREATE_ADOLSCENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }


    public int syncEnumBlocks(JSONArray enumList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EnumBlockContract.EnumBlockTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < enumList.length(); i++) {
                JSONObject jsonObjectCC;
                try {
                    jsonObjectCC = enumList.getJSONObject(i);
                    EnumBlockContract Vc = new EnumBlockContract();
                    Vc.Sync(jsonObjectCC);

                    ContentValues values = new ContentValues();

                    values.put(EnumBlockContract.EnumBlockTable.COLUMN_DIST_ID, Vc.getDsit_code());
                    values.put(EnumBlockContract.EnumBlockTable.COLUMN_GEO_AREA, Vc.getGeoarea());
                    values.put(EnumBlockContract.EnumBlockTable.COLUMN_CLUSTER_AREA, Vc.getCluster());

                    long rowID = db.insert(EnumBlockContract.EnumBlockTable.TABLE_NAME, null, values);
                    if (rowID != -1) insertCount++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "syncEnumBlocks(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncBLRandom(JSONArray bllist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RandomHH.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < bllist.length(); i++) {
                JSONObject jsonObjectCC = bllist.getJSONObject(i);

                BLRandomContract Vc = new BLRandomContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(RandomHH.COLUMN_ID, Vc.get_ID());
                values.put(RandomHH.COLUMN_LUID, Vc.getLUID());
                values.put(RandomHH.COLUMN_STRUCTURE_NO, Vc.getStructure());
                values.put(RandomHH.COLUMN_FAMILY_EXT_CODE, Vc.getExtension());
                values.put(RandomHH.COLUMN_HH, Vc.getHh());
                values.put(RandomHH.COLUMN_ENUM_BLOCK_CODE, Vc.getSubVillageCode());
                values.put(RandomHH.COLUMN_RANDOMDT, Vc.getRandomDT());
                values.put(RandomHH.COLUMN_HH_HEAD, Vc.getHhhead());
                values.put(RandomHH.COLUMN_CONTACT, Vc.getContact());
                values.put(RandomHH.COLUMN_HH_SELECTED_STRUCT, Vc.getSelStructure());
                values.put(RandomHH.COLUMN_SNO_HH, Vc.getSno());

                long rowID = db.insert(RandomHH.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }
        } catch (Exception e) {
            Log.d(TAG, "syncBLRandomBlocks(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncVersionApp(JSONObject VersionList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VersionAppContract.VersionAppTable.TABLE_NAME, null, null);
        long count = 0;
        try {
            JSONObject jsonObjectCC = ((JSONArray) VersionList.get(VersionAppContract.VersionAppTable.COLUMN_VERSION_PATH)).getJSONObject(0);
            VersionAppContract Vc = new VersionAppContract();
            Vc.Sync(jsonObjectCC);

            ContentValues values = new ContentValues();

            values.put(VersionAppContract.VersionAppTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            count = db.insert(VersionAppContract.VersionAppTable.TABLE_NAME, null, values);
            if (count > 0) count = 1;

        } catch (Exception ignored) {
        } finally {
            db.close();
        }

        return (int) count;
    }

    public int syncDistrict(JSONArray distList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DistrictContract.DistrictTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < distList.length(); i++) {

                JSONObject jsonObjectUser = distList.getJSONObject(i);

                DistrictContract dist = new DistrictContract();
                dist.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(DistrictTable.COLUMN_DIST_ID, dist.getDist_id());
                values.put(DistrictTable.COLUMN_DIST_NAME, dist.getDistrict());
                values.put(DistrictTable.COLUMN_PROVINCE_NAME, dist.getProvince());
                long rowID = db.insert(DistrictTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncDist(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncUser(JSONArray userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersContract.UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < userList.length(); i++) {

                JSONObject jsonObjectUser = userList.getJSONObject(i);

                UsersContract user = new UsersContract();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersContract.UsersTable.ROW_USERNAME, user.getUserName());
                values.put(UsersContract.UsersTable.ROW_PASSWORD, user.getPassword());
                values.put(UsersContract.UsersTable.DIST_ID, user.getDIST_ID());
                long rowID = db.insert(UsersContract.UsersTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }


    public VersionAppContract getVersionApp() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VersionAppContract.VersionAppTable._ID,
                VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE,
                VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME,
                VersionAppContract.VersionAppTable.COLUMN_PATH_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = null;

        VersionAppContract allVC = new VersionAppContract();
        try {
            c = db.query(
                    VersionAppContract.VersionAppTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allVC.hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }

    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + UsersContract.UsersTable.TABLE_NAME + " WHERE " + UsersContract.UsersTable.ROW_USERNAME + "=? AND " + UsersContract.UsersTable.ROW_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {

            if (mCursor.getCount() > 0) {

                if (mCursor.moveToFirst()) {
                    mCursor.close();
                }
                return true;
            }
        }
        return false;
    }

    public Long addForm(FormsContract fc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECT_NAME, fc.getProjectName());
        values.put(FormsTable.COLUMN_UID, fc.get_UID());
        values.put(FormsTable.COLUMN_FORMDATE, fc.getFormDate());
        values.put(FormsTable.COLUMN_LUID, fc.getLuid());
        values.put(FormsTable.COLUMN_USER, fc.getUser());
        values.put(FormsTable.COLUMN_ISTATUS, fc.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS88x, fc.getIstatus88x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, fc.getEndingdatetime());
        values.put(FormsTable.COLUMN_SINFO, fc.getsInfo());
        values.put(FormsTable.COLUMN_SE, fc.getsE());
        values.put(FormsTable.COLUMN_SM, fc.getsM());
        values.put(FormsTable.COLUMN_SN, fc.getsN());
        values.put(FormsTable.COLUMN_SO, fc.getsO());
        values.put(FormsTable.COLUMN_GPSLAT, fc.getGpsLat());
        values.put(FormsTable.COLUMN_GPSLNG, fc.getGpsLng());
        values.put(FormsTable.COLUMN_GPSDATE, fc.getGpsDT());
        values.put(FormsTable.COLUMN_GPSACC, fc.getGpsAcc());
        values.put(FormsTable.COLUMN_DEVICETAGID, fc.getDevicetagID());
        values.put(FormsTable.COLUMN_DEVICEID, fc.getDeviceID());
        values.put(FormsTable.COLUMN_APPVERSION, fc.getAppversion());
        values.put(FormsTable.COLUMN_CLUSTERCODE, fc.getClusterCode());
        values.put(FormsTable.COLUMN_HHNO, fc.getHhno());
        values.put(FormsTable.COLUMN_FORMTYPE, fc.getFormType());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsTable.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addFamilyMember(FamilyMembersContract fmc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SingleMember.COLUMN_ID, fmc.get_id());
        values.put(SingleMember.COLUMN_UID, fmc.getUid());
        values.put(SingleMember.COLUMN_UUID, fmc.getUuid());
        values.put(FamilyMembersContract.SingleMember.COLUMN_FORMDATE, fmc.getFormdate());
        values.put(FamilyMembersContract.SingleMember.COLUMN_CLUSTERNO, fmc.getClusterno());
        values.put(FamilyMembersContract.SingleMember.COLUMN_HHNO, fmc.getHhno());
        values.put(FamilyMembersContract.SingleMember.COLUMN_SERIAL_NO, fmc.getSerialno());
        values.put(SingleMember.COLUMN_NAME, fmc.getName());
        values.put(SingleMember.COLUMN_RELATION_HH, fmc.getRelHH());
        values.put(SingleMember.COLUMN_AGE, fmc.getAge());
        values.put(SingleMember.COLUMN_MONTH_FM, fmc.getMonthfm());
        values.put(SingleMember.COLUMN_DAY_FM, fmc.getDayfm());
        values.put(SingleMember.AGE_IN_DAYS, fmc.getAgeInDays());
        values.put(SingleMember.COLUMN_MOTHER_NAME, fmc.getMother_name());
        values.put(SingleMember.COLUMN_MOTHER_SERIAL, fmc.getMother_serial());
        values.put(FamilyMembersContract.SingleMember.COLUMN_GENDER, fmc.getGender());
        values.put(SingleMember.COLUMN_MARITAL, fmc.getMarital());
        values.put(SingleMember.COLUMN_SD, fmc.getsD());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SingleMember.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addMortality(MortalityContract morc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SingleMortality.COLUMN__UUID, morc.get_UUID());
        values.put(SingleMortality.COLUMN_DEVICEID, morc.getDeviceId());
        values.put(SingleMortality.COLUMN_DEVICETAGID, morc.getDevicetagID());
        values.put(SingleMortality.COLUMN_FORMDATE, morc.getFormDate());
        values.put(SingleMortality.COLUMN_USER, morc.getUser());
        values.put(SingleMortality.COLUMN_SE3, morc.getsE3());
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SingleMortality.TABLE_NAME,
                SingleMortality.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addKishMWRA(KishMWRAContract kishmwra) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(SingleKishMWRA.COLUMN_UID, kishmwra.getUID());
        values.put(SingleKishMWRA.COLUMN__UUID, kishmwra.get_UUID());
        values.put(SingleKishMWRA.COLUMN_DEVICEID, kishmwra.getDeviceId());
        values.put(SingleKishMWRA.COLUMN_FORMDATE, kishmwra.getFormDate());
        values.put(SingleKishMWRA.COLUMN_USER, kishmwra.getUser());
        values.put(SingleKishMWRA.COLUMN_SF, kishmwra.getsF());
        values.put(SingleKishMWRA.COLUMN_SG, kishmwra.getsG());
        values.put(SingleKishMWRA.COLUMN_SH2, kishmwra.getsH2());
        values.put(SingleKishMWRA.COLUMN_SK, kishmwra.getsK());
        values.put(SingleKishMWRA.COLUMN_SUN, kishmwra.getsUN());
        values.put(SingleKishMWRA.COLUMN_SL, kishmwra.getsL());
        values.put(SingleKishMWRA.COLUMN_DEVICETAGID, kishmwra.getDevicetagID());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SingleKishMWRA.TABLE_NAME,
                SingleKishMWRA.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addMWRA(MWRAContract mwra) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
//        values.put(MWRATable._ID, mwra.get_ID());
        values.put(MWRATable.COLUMN_UUID, mwra.get_UUID());
        values.put(MWRATable.COLUMN_DEVICEID, mwra.getDeviceId());
        values.put(MWRATable.COLUMN_FORMDATE, mwra.getFormDate());
        values.put(MWRATable.COLUMN_USER, mwra.getUser());
        values.put(MWRATable.COLUMN_DEVICETAGID, mwra.getDevicetagID());
        values.put(MWRATable.COLUMN_SE1, mwra.getsE1());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MWRATable.TABLE_NAME,
                null,
                values);
        return newRowId;
    }

    public Long addAdolscent(AdolscentContract adolscentContract) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
//        values.put(MWRATable._ID, mwra.get_ID());
        values.put(SingleAdolscent.COLUMN__UUID, adolscentContract.get_UUID());
        values.put(SingleAdolscent.COLUMN_DEVICEID, adolscentContract.getDeviceId());
        values.put(SingleAdolscent.COLUMN_FORMDATE, adolscentContract.getFormDate());
        values.put(SingleAdolscent.COLUMN_USER, adolscentContract.getUser());
        values.put(SingleAdolscent.COLUMN_SAH1, adolscentContract.getsAH1());
        values.put(SingleAdolscent.COLUMN_SAH2, adolscentContract.getsAH2());
        values.put(SingleAdolscent.COLUMN_SAH3, adolscentContract.getsAH3());
        values.put(SingleAdolscent.COLUMN_DEVICETAGID, adolscentContract.getDevicetagID());


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SingleAdolscent.TABLE_NAME,
                null,
                values);
        return newRowId;
    }


    public Long addChild(ChildContract childContract) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
//        values.put(MWRATable._ID, mwra.get_ID());
        values.put(ChildContract.ChildTable.COLUMN__UUID, childContract.get_UUID());
        values.put(ChildContract.ChildTable.COLUMN_DEVICEID, childContract.getDeviceId());
        values.put(ChildContract.ChildTable.COLUMN_FORMDATE, childContract.getFormDate());
        values.put(ChildContract.ChildTable.COLUMN_USER, childContract.getUser());
        values.put(ChildContract.ChildTable.COLUMN_SH1, childContract.getsH1());
        values.put(ChildContract.ChildTable.COLUMN_SJ, childContract.getsJ());
        values.put(ChildContract.ChildTable.COLUMN_DEVICETAGID, childContract.getDevicetagID());


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                ChildContract.ChildTable.TABLE_NAME,
                null,
                values);
        return newRowId;
    }

    public Long addPregnantMWRA(MWRA_PREContract mwra) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
//        values.put(MWRATable._ID, mwra.get_ID());
        values.put(SingleMWRAPRE.COLUMN__UUID, mwra.get_UUID());
        values.put(SingleMWRAPRE.COLUMN_DEVICEID, mwra.getDeviceId());
        values.put(SingleMWRAPRE.COLUMN_FORMDATE, mwra.getFormDate());
        values.put(SingleMWRAPRE.COLUMN_USER, mwra.getUser());
        values.put(SingleMWRAPRE.COLUMN_DEVICETAGID, mwra.getDevicetagID());
        values.put(SingleMWRAPRE.COLUMN_SE2, mwra.getsE2());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SingleMWRAPRE.TABLE_NAME,
                null,
                values);
        return newRowId;
    }

    public FormsContract isDataExists(String studyId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;

// New value for one column
        String[] columns = {
                FormsTable.COLUMN_LUID,
                FormsTable.COLUMN_ISTATUS,

        };

// Which row to update, based on the ID
        String selection = FormsTable.COLUMN_LUID + " = ? AND "
                + FormsTable.COLUMN_ISTATUS + " = ?";
        String[] selectionArgs = new String[]{studyId, "1"};

        FormsContract allFC = new FormsContract();
        try {
            c = db.query(FormsTable.TABLE_NAME, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                       //filter by row groups
                    null);                   // The sort order

            while (c.moveToNext()) {
                allFC.setLuid(c.getString(c.getColumnIndex(FormsTable.COLUMN_LUID)));
                allFC.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;


    }

    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateMWRAs(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MWRAContract.MWRATable.COLUMN_SYNCED, true);
        values.put(MWRAContract.MWRATable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = MWRAContract.MWRATable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                MWRAContract.MWRATable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public int updateFormID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_UID, MainApp.fc.get_UID());

// Which row to update, based on the ID
        String selection = FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public Collection<FormsContract> getAllForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_FORMTYPE,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<FamilyMembersContract> getAllFamilyMembersForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                SingleMember.COLUMN_ID,
                SingleMember.COLUMN_UID,
                SingleMember.COLUMN_UUID,
                SingleMember.COLUMN_FORMDATE,
                SingleMember.COLUMN_CLUSTERNO,
                SingleMember.COLUMN_HHNO,
                SingleMember.COLUMN_SERIAL_NO,
                SingleMember.COLUMN_NAME,
                SingleMember.COLUMN_RELATION_HH,
                SingleMember.COLUMN_AGE,
                SingleMember.COLUMN_MONTH_FM,
                SingleMember.COLUMN_DAY_FM,
                SingleMember.AGE_IN_DAYS,
                SingleMember.COLUMN_MOTHER_NAME,
                SingleMember.COLUMN_MOTHER_SERIAL,
                SingleMember.COLUMN_GENDER,
                SingleMember.COLUMN_MARITAL,
                SingleMember.COLUMN_SD,
        };
        String whereClause = SingleMember.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FamilyMembersContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    SingleMember.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FamilyMembersContract fc = new FamilyMembersContract();
                allFC.add(fc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<FormsContract> checkFormExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_FORMTYPE,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<MWRAContract> getUnsyncedMWRA() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                MWRAContract.MWRATable.COLUMN_ID,
                MWRAContract.MWRATable.COLUMN_UID,
                MWRAContract.MWRATable.COLUMN_UUID,
                MWRAContract.MWRATable.COLUMN_FORMDATE,
                MWRAContract.MWRATable.COLUMN_USER,
                MWRAContract.MWRATable.COLUMN_SE1,
                MWRAContract.MWRATable.COLUMN_DEVICEID,
                MWRAContract.MWRATable.COLUMN_DEVICETAGID
        };
        String whereClause = MWRAContract.MWRATable.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                MWRAContract.MWRATable.COLUMN_ID + " ASC";

        Collection<MWRAContract> allMC = new ArrayList<MWRAContract>();
        try {
            c = db.query(
                    MWRAContract.MWRATable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MWRAContract mc = new MWRAContract();
                allMC.add(mc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allMC;
    }

    public Collection<MWRA_PREContract> getUnsyncedPregMWRA() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                SingleMWRAPRE._ID,
                SingleMWRAPRE.COLUMN_UID,
                SingleMWRAPRE.COLUMN__UUID,
                SingleMWRAPRE.COLUMN_DEVICEID,
                SingleMWRAPRE.COLUMN_FORMDATE,
                SingleMWRAPRE.COLUMN_USER,
                SingleMWRAPRE.COLUMN_SE2,
                SingleMWRAPRE.COLUMN_DEVICETAGID,

        };
        String whereClause = SingleMWRAPRE.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                SingleMWRAPRE._ID + " ASC";

        Collection<MWRA_PREContract> allMC = new ArrayList<MWRA_PREContract>();
        try {
            c = db.query(
                    SingleMWRAPRE.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MWRA_PREContract mc = new MWRA_PREContract();
                allMC.add(mc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allMC;
    }

    public Collection<MortalityContract> getUnsyncedMortality() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                SingleMortality._ID,
                SingleMortality.COLUMN_UID,
                SingleMortality.COLUMN__UUID,
                SingleMortality.COLUMN_DEVICEID,
                SingleMortality.COLUMN_DEVICETAGID,
                SingleMortality.COLUMN_FORMDATE,
                SingleMortality.COLUMN_USER,
                SingleMortality.COLUMN_SE3,


        };
        String whereClause = SingleMWRAPRE.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                SingleMWRAPRE._ID + " ASC";

        Collection<MortalityContract> allMC = new ArrayList<MortalityContract>();
        try {
            c = db.query(
                    SingleMortality.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MortalityContract mc = new MortalityContract();
                allMC.add(mc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allMC;
    }

    public Collection<KishMWRAContract> getUnsyncedKishMWRA() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                SingleKishMWRA._ID,
                SingleKishMWRA.COLUMN_UID,
                SingleKishMWRA.COLUMN__UUID,
                SingleKishMWRA.COLUMN_DEVICEID,
                SingleKishMWRA.COLUMN_FORMDATE,
                SingleKishMWRA.COLUMN_USER,
                SingleKishMWRA.COLUMN_SF,
                SingleKishMWRA.COLUMN_SG,
                SingleKishMWRA.COLUMN_SH2,
                SingleKishMWRA.COLUMN_SK,
                SingleKishMWRA.COLUMN_SUN,
                SingleKishMWRA.COLUMN_SL,
                SingleKishMWRA.COLUMN_DEVICETAGID,
        };
        String whereClause = SingleKishMWRA.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                SingleKishMWRA._ID + " ASC";

        Collection<KishMWRAContract> allMC = new ArrayList<KishMWRAContract>();
        try {
            c = db.query(
                    SingleKishMWRA.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                KishMWRAContract mc = new KishMWRAContract();
                allMC.add(mc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allMC;
    }

    public Collection<FormsContract> getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS88x,
                FormsTable.COLUMN_LUID,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SM,
                FormsTable.COLUMN_SN,
                FormsTable.COLUMN_SO,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_FORMTYPE
        };


        String whereClause = FormsTable.COLUMN_SYNCED + " is null";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<AdolscentContract> getUnsyncedAdolscentForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                SingleAdolscent._ID,
                SingleAdolscent.COLUMN_UID,
                SingleAdolscent.COLUMN__UUID,
                SingleAdolscent.COLUMN_DEVICEID,
                SingleAdolscent.COLUMN_FORMDATE,
                SingleAdolscent.COLUMN_USER,
                SingleAdolscent.COLUMN_SAH1,
                SingleAdolscent.COLUMN_SAH2,
                SingleAdolscent.COLUMN_SAH3,
                SingleAdolscent.COLUMN_DEVICETAGID,
        };


        String whereClause = SingleAdolscent.COLUMN_SYNCED + " is null";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                SingleAdolscent._ID + " ASC";

        Collection<AdolscentContract> allFC = new ArrayList<AdolscentContract>();
        try {
            c = db.query(
                    SingleAdolscent.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                AdolscentContract fc = new AdolscentContract();
                allFC.add(fc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<ChildContract> getUnsyncedChildsForms() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildContract.ChildTable._ID,
                ChildContract.ChildTable.COLUMN_UID,
                ChildContract.ChildTable.COLUMN__UUID,
                ChildContract.ChildTable.COLUMN_DEVICEID,
                ChildContract.ChildTable.COLUMN_FORMDATE,
                ChildContract.ChildTable.COLUMN_USER,
                ChildContract.ChildTable.COLUMN_SH1,
                ChildContract.ChildTable.COLUMN_SJ,
                ChildContract.ChildTable.COLUMN_DEVICETAGID,
        };


        String whereClause = ChildContract.ChildTable.COLUMN_SYNCED + " is null";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                ChildContract.ChildTable._ID + " ASC";

        Collection<ChildContract> allFC = new ArrayList<ChildContract>();
        try {
            c = db.query(
                    ChildContract.ChildTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ChildContract fc = new ChildContract();
                allFC.add(fc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    public Collection<FormsContract> getTodayForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS88x,
                FormsTable.COLUMN_LUID,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SM,
                FormsTable.COLUMN_SN,
                FormsTable.COLUMN_SO,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_FORMTYPE

        };
        String whereClause = FormsTable.COLUMN_FORMDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_ISTATUS, MainApp.fc.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS88x, MainApp.fc.getIstatus88x());
//        values.put(FormsTable.COLUMN_SE, MainApp.fc.getsE());
//        values.put(FormsTable.COLUMN_STATUS, MainApp.fc.getStatus());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, MainApp.fc.getEndingdatetime());


// Which row to update, based on the ID
        String selection = FormsTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    //Get BLRandom data
    public BLRandomContract getHHFromBLRandom(String subAreaCode, String hh) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                RandomHH.COLUMN_ID,
                RandomHH.COLUMN_LUID,
                RandomHH.COLUMN_STRUCTURE_NO,
                RandomHH.COLUMN_FAMILY_EXT_CODE,
                RandomHH.COLUMN_HH,
                RandomHH.COLUMN_ENUM_BLOCK_CODE,
                RandomHH.COLUMN_RANDOMDT,
                RandomHH.COLUMN_HH_SELECTED_STRUCT,
                RandomHH.COLUMN_CONTACT,
                RandomHH.COLUMN_HH_HEAD,
                RandomHH.COLUMN_SNO_HH
        };

        String whereClause = RandomHH.COLUMN_ENUM_BLOCK_CODE + "=? AND " + RandomHH.COLUMN_HH + "=?";
        String[] whereArgs = new String[]{subAreaCode, hh};
        String groupBy = null;
        String having = null;

        String orderBy =
                RandomHH.COLUMN_ID + " ASC";

        BLRandomContract allBL = null;
        try {
            c = db.query(
                    RandomHH.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allBL = new BLRandomContract().hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allBL;
    }

    //Get EnumBlock
    public EnumBlockContract getEnumBlock(String cluster) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                EnumBlockTable._ID,
                EnumBlockTable.COLUMN_DIST_ID,
                EnumBlockTable.COLUMN_GEO_AREA,
                EnumBlockTable.COLUMN_CLUSTER_AREA
        };

        String whereClause = EnumBlockTable.COLUMN_CLUSTER_AREA + " =?";
        String[] whereArgs = new String[]{cluster};
        String groupBy = null;
        String having = null;

        String orderBy = EnumBlockTable._ID + " ASC";
        EnumBlockContract allEB = null;
        try {
            c = db.query(
                    EnumBlockTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB = new EnumBlockContract().HydrateEnum(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }

    //Generic update FormColumn
    public int updatesFormColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormsTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Generic update FamilyMemberColumn
    public int updatesFamilyMemberColumn(String column, String value, String valueID) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = SingleMember._ID + " =? ";
        String[] selectionArgs = {String.valueOf(valueID)};

        return db.update(FamilyMembersContract.SingleMember.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Generic update MWRAPREColumn
    public int updatesMWRAPREColumn(MWRA_PREContract mwra_pre) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(SingleMWRAPRE.COLUMN_UID, mwra_pre.getUID());

        String selection = SingleMWRAPRE._ID + " =? ";
        String[] selectionArgs = {String.valueOf(mwra_pre.get_ID())};

        return db.update(SingleMWRAPRE.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Generic update KishMWRAColumn
    public int updatesKishMWRAColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = SingleKishMWRA._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.kish.get_ID())};

        return db.update(SingleKishMWRA.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Generic update MWRAColumn
    public int updateMWRAUID(MWRAContract mwra) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(MWRATable.COLUMN_UID, mwra.getUID());

        String selection = MWRATable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(mwra.get_ID())};

        return db.update(MWRATable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Generic update ChildColumn
    public int updatesAdolsColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = SingleAdolscent._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.adolscent.get_ID())};

        return db.update(SingleAdolscent.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Generic update ChildColumn
    public int updatesChildColumn(String column, String value) {

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = ChildContract.ChildTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.child.get_ID())};

        return db.update(ChildContract.ChildTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    // ANDROID DATABASE MANAGER
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }


    public void updateSyncedAdolsForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(SingleAdolscent.COLUMN_SYNCED, true);
        values.put(SingleAdolscent.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = SingleAdolscent._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                SingleAdolscent.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedKishMWRAForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(SingleKishMWRA.COLUMN_SYNCED, true);
        values.put(SingleKishMWRA.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = SingleKishMWRA._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                SingleKishMWRA.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedPregMWRAForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(SingleMWRAPRE.COLUMN_SYNCED, true);
        values.put(SingleMWRAPRE.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = SingleMWRAPRE._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                SingleMWRAPRE.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedFamilyMemForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(SingleMember.COLUMN_SYNCED, true);
        values.put(SingleMember.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = SingleMember._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                SingleMember.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedMWRAForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MWRATable.COLUMN_SYNCED, true);
        values.put(MWRATable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = MWRATable._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                MWRATable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedChildForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ChildContract.ChildTable.COLUMN_SYNCED, true);
        values.put(ChildContract.ChildTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = MWRATable._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                ChildContract.ChildTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void resetAll() {

        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, (byte[]) null);
        values.put(FormsTable.COLUMN_SYNCED_DATE, (byte[]) null);

// Which row to update, based on the title
        String where = null;
        String[] whereArgs = null;

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);


        ContentValues values2 = new ContentValues();
        values2.put(FamilyMembersContract.SingleMember.COLUMN_SYNCED, (byte[]) null);
        values2.put(FamilyMembersContract.SingleMember.COLUMN_SYNCED_DATE, (byte[]) null);

// Which row to update, based on the title
        String where2 = null;
        String[] whereArgs2 = null;

        int count2 = db.update(
                FamilyMembersContract.SingleMember.TABLE_NAME,
                values2,
                where2,
                whereArgs2);


        ContentValues values3 = new ContentValues();
        values3.put(AdolscentContract.SingleAdolscent.COLUMN_SYNCED, (byte[]) null);
        values3.put(AdolscentContract.SingleAdolscent.COLUMN_SYNCED_DATE, (byte[]) null);

// Which row to update, based on the title
        String where3 = null;
        String[] whereArgs3 = null;

        int count3 = db.update(
                SingleAdolscent.TABLE_NAME,
                values3,
                where3,
                whereArgs3);


        ContentValues values4 = new ContentValues();
        values4.put(ChildContract.ChildTable.COLUMN_SYNCED, (byte[]) null);
        values4.put(ChildContract.ChildTable.COLUMN_SYNCED_DATE, (byte[]) null);

// Which row to update, based on the title
        String where4 = null;
        String[] whereArgs4 = null;

        int count4 = db.update(
                ChildContract.ChildTable.TABLE_NAME,
                values4,
                where4,
                whereArgs4);

        ContentValues values5 = new ContentValues();
        values5.put(KishMWRAContract.SingleKishMWRA.COLUMN_SYNCED, (byte[]) null);
        values5.put(KishMWRAContract.SingleKishMWRA.COLUMN_SYNCED_DATE, (byte[]) null);

// Which row to update, based on the title
        String where5 = null;
        String[] whereArgs5 = null;

        int count5 = db.update(
                KishMWRAContract.SingleKishMWRA.TABLE_NAME,
                values5,
                where5,
                whereArgs5);


        ContentValues values6 = new ContentValues();
        values6.put(MWRATable.COLUMN_SYNCED, (byte[]) null);
        values6.put(MWRATable.COLUMN_SYNCED_DATE, (byte[]) null);

// Which row to update, based on the title
        String where6 = null;
        String[] whereArgs6 = null;

        int count6 = db.update(
                MWRATable.TABLE_NAME,
                values6,
                where6,
                whereArgs6);

        ContentValues values7 = new ContentValues();
        values7.put(MWRA_PREContract.SingleMWRAPRE.COLUMN_SYNCED, (byte[]) null);
        values7.put(MWRA_PREContract.SingleMWRAPRE.COLUMN_SYNCED_DATE, (byte[]) null);

// Which row to update, based on the title
        String where7 = null;
        String[] whereArgs7 = null;

        int count7 = db.update(
                MWRA_PREContract.SingleMWRAPRE.TABLE_NAME,
                values7,
                where7,
                whereArgs7);
    }

    public List<String> getMWRAS(String _uuid) {

        //Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();

        List<String> mwras = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select distinct m.mother_serial as mother_serial, m.mother_name as mother_name, ageInDays from familymembers m where CAST(m.ageInDays as int) = (select min(CAST(ageInDays as int)) from familymembers where _uuid = m._uuid) AND m._uuid = '" + _uuid + "' AND m.mother_serial != '97' AND CAST(m.mother_serial as int) > 0", null);
        mwras.add("....");
        if (res.moveToFirst()) {
            do {
                mwras.add(res.getString(res.getColumnIndex("mother_serial")) + ":" + res.getString(res.getColumnIndex("mother_name")));
            } while (res.moveToNext());
        }

        res.close();
        db.close();
        return mwras;
    }

    public List<String> getAdolescent(String _uuid) {

        //Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();

        List<String> mwras = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select distinct serial_no, name from familymembers where _uuid = '" + _uuid + "' AND CAST(ageInDays as int) >= 3650 AND CAST(ageInDays as int) <= 6935", null);
        mwras.add("....");
        if (res.moveToFirst()) {
            do {
                mwras.add(res.getString(res.getColumnIndex("serial_no")) + ":" + res.getString(res.getColumnIndex("name")));
            } while (res.moveToNext());
        }

        res.close();
        db.close();
        return mwras;
    }


    //Get All Districts
    public List<DistrictContract> getDistrictProv() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                DistrictTable._ID,
                DistrictTable.COLUMN_DIST_ID,
                DistrictTable.COLUMN_DIST_NAME,
                DistrictTable.COLUMN_PROVINCE_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = DistrictTable._ID + " ASC";
        List<DistrictContract> allEB = new ArrayList<>();
        try {
            c = db.query(
                    DistrictTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB.add(new DistrictContract().Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }
}
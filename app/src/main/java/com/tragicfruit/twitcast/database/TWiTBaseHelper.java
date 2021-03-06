package com.tragicfruit.twitcast.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tragicfruit.twitcast.constants.Constants;
import com.tragicfruit.twitcast.database.TWiTDbSchema.EpisodeTable;
import com.tragicfruit.twitcast.database.TWiTDbSchema.ShowTable;
import com.tragicfruit.twitcast.utils.QueryPreferences;

/**
 * Created by Jeremy on 5/03/2016.
 */
public class TWiTBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 5;
    private static final String DATABASE_NAME = "twitBase.db";

    private Context mContext;

    public TWiTBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ShowTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ShowTable.Cols.TITLE + ", " +
                ShowTable.Cols.SHORT_CODE + ", " +
                ShowTable.Cols.COVER_ART_URL + ", " +
                ShowTable.Cols.COVER_ART_URL_SMALL + ", " +
                ShowTable.Cols.COVER_ART_LOCAL_PATH + ", " +
                ShowTable.Cols.ID + ", " +
                ShowTable.Cols.DESCRIPTION + ", " +
                ShowTable.Cols.VIDEO_HD_FEED + ", " +
                ShowTable.Cols.VIDEO_LARGE_FEED + ", " +
                ShowTable.Cols.VIDEO_SMALL_FEED + ", " +
                ShowTable.Cols.AUDIO_FEED + ", " +
                ShowTable.Cols.LOADED_ALL_EPISODES +
                ")"
        );

        db.execSQL("create table " + EpisodeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                EpisodeTable.Cols.TITLE + ", " +
                EpisodeTable.Cols.PUBLICATION_DATE + ", " +
                EpisodeTable.Cols.SUBTITLE + ", " +
                EpisodeTable.Cols.SHOW_NOTES + ", " +
                EpisodeTable.Cols.VIDEO_HD_URL + ", " +
                EpisodeTable.Cols.VIDEO_LARGE_URL + ", " +
                EpisodeTable.Cols.VIDEO_SMALL_URL + ", " +
                EpisodeTable.Cols.AUDIO_URL + ", " +
                EpisodeTable.Cols.RUNNING_TIME + ", " +
                EpisodeTable.Cols.SHOW_ID + ", " +
                "FOREIGN KEY(" + EpisodeTable.Cols.SHOW_ID +  ") REFERENCES " + ShowTable.NAME + "(_id)" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 4) {
            db.execSQL("alter table " + ShowTable.NAME + " add " + ShowTable.Cols.COVER_ART_URL_SMALL);
            newVersion++;
        }

        if (newVersion == 5) {
            QueryPreferences.setStreamSource(mContext, Constants.DEFAULT_SOURCE);
        }

        QueryPreferences.setForceRefetchShows(mContext, true);
    }
}

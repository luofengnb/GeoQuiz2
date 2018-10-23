package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.geoquiz.QuestionDbSchema.QuestionTable;

/**
 * Created by luofeng on 2017/12/11.
 */

public class QuestionBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=4;
    private static final String DATABASE_NAME="questionBase.db";


    public QuestionBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+ QuestionTable.NAME+"("+
                "id integer primary key autoincrement, "+QuestionTable.Cols.UUID+" integer,"+
                QuestionTable.Cols.TXT+" text, "+QuestionTable.Cols.TUREORFALSE+" blob)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("create table "+ QuestionTable.NAME+"("+
                "id integer primary key autoincrement, "+QuestionTable.Cols.UUID+" integer,"+
                QuestionTable.Cols.TXT+" text, "+QuestionTable.Cols.TUREORFALSE+" blob)");
    }
}

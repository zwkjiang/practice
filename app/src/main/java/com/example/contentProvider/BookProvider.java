package com.example.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BookProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.provider.book";
    public static final Uri BOOK_CONTENT = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher uirMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private Context mContext;
    private SQLiteDatabase mDb;


    static {
        uirMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        uirMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private String getTableName(Uri uri) {
        String tabName = null;
        switch (uirMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tabName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tabName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tabName;
    }

    @Override
    public boolean onCreate() {
        Log.i("ZWK", "onCreate current " + Thread.currentThread().getName());
        mContext = getContext();
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'Android');");
        mDb.execSQL("insert into book values(5,'Ios');");
        mDb.execSQL("insert into book values(4,'html');");
        mDb.execSQL("insert into user values(2,'jake',1);");
        mDb.execSQL("insert into user values(1,'jasmine',0);");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i("ZWK", "query current " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(tableName, projection, selection, selectionArgs, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i("ZWK", "getType current " + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i("ZWK", "insert current " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i("ZWK", "delete current " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i("ZWK", "update current " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int update = mDb.update(tableName, values, selection, selectionArgs);
        if (update>0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return update;
    }
}

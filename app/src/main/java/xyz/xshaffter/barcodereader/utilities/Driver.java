package xyz.xshaffter.barcodereader.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Driver extends SQLiteOpenHelper {
    private static Driver instance = null;
    private SQLiteDatabase db = null;

    public static synchronized Driver getInstance(Context context) {
        if (instance == null) {
            instance = new Driver(context, "oxxo", null, 2);
        }
        instance.db = instance.getWritableDatabase();
        return instance;
    }

    private Driver(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_QUERY_PRODUCTOS = "CREATE TABLE 'PRODUCTOS'(" +
                "'CODIGO' VARCHAR(255) PRIMARY KEY," +
                "'NOMBRE' VARCHAR(50)," +
                "'CANTIDAD' INTEGER(4))";
        db.execSQL(SQL_QUERY_PRODUCTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS 'PRODUCTOS'");
            onCreate(db);
        }
    }

    public String AddOrUpgrade(final String codigo, final String nombre, final int cantidad) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("codigo", codigo);
            values.put("nombre", nombre);
            values.put("cantidad", cantidad);

            int rows = db.update("PRODUCTOS", values, "codigo = ?", new String[]{codigo});
            if (rows > 0) {
                db.setTransactionSuccessful();
                return "updated";
            } else {
                long id = db.insertOrThrow("PRODUCTOS", null, values);
                db.setTransactionSuccessful();
                return "added";
            }
        } catch (Exception ex) {
            Log.d("TAG", "Error");
        } finally {
            db.endTransaction();
        }
        return "true";
    }

    public Cursor getFirstFromTable(final String codigo) {
        final String SQL_QUERY_SELECT = String.format("SELECT * FROM PRODUCTOS WHERE CODIGO = '%S'", codigo);
        Cursor cursor = db.rawQuery(SQL_QUERY_SELECT, null);

        try {
            return cursor;
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
            return null;
        }
    }
}

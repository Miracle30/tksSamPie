package d1711062183.thi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocationHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "location";
    private static final int SCHEMA_VER = 5;

    public LocationHelper(Context context){
        super(context, DB_NAME, null, SCHEMA_VER);
    }

    //tao bang du lieu de luu tru
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS loca(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, zip TEXT, country TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS loca");
        onCreate(db);
    }

}

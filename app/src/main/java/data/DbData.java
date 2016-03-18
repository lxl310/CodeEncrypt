package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/9/15.
 */
public class DbData extends SQLiteOpenHelper {
    public DbData(Context context) {
        super(context,"user_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE user(_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT DEFAULT \"\",gro TEXT DEFAULT \"\",name TEXT DEFAULT \"\",password TEXT DEFAULT \"\",remark TEXT DEFAULT \"\")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

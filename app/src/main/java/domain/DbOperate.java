package domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import data.DbData;

/**
 * Created by Administrator on 2015/9/15.
 */
public class DbOperate {
    AESUtils encrypt = new AESUtils();
    public void inserts(Context context,String data1,String data2,String data3,String data4,String data5){
        DbData db = new DbData(context);
        SQLiteDatabase dbwrite = db.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put("title",data1);
        cv.put("gro",data2);
        cv.put("name",data3);
        data4 = encrypt.encrypt(data4,data4);
        cv.put("password",data4);
        cv.put("remark",data5);
        dbwrite.insert("user",null,cv);
        dbwrite.close();
    }


    //返回cursor数据用于绑定listview
    public Cursor read(Context context) {
        DbData db = new DbData(context);
        SQLiteDatabase dbread = db.getReadableDatabase();
        Cursor cs = dbread.query("user", null, null, null, null, null, null);
        return cs;
    }

    //返回特定列的cursor数据
    public Cursor readSpecial(Context context,String lie){
        DbData db = new DbData(context);
        SQLiteDatabase dbread = db.getReadableDatabase();
        String selection = "gro=?" ;
        String[] selectionArgs = new  String[]{lie};
        Cursor cs = dbread.query("user", null, selection,selectionArgs, null, null, null);
        return cs;
    }
    //删除数据
    public void delete(Context context,int itemid){
        DbData db = new DbData(context);
        SQLiteDatabase dbwrite = db.getWritableDatabase();
        dbwrite.delete("user","_id=?",new String[]{itemid+""});
    }

    //更新数据
    public void updata(Context context,String data1,String data2,String data3,String data4,String data5,int itemid){
        DbData db = new DbData(context);
        SQLiteDatabase dbwrite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title",data1);
        cv.put("gro",data2);
        cv.put("name",data3);
        data4 = encrypt.encrypt(data4,data4);
        cv.put("password",data4);
        cv.put("remark",data5);
        dbwrite.update("person", cv, "id=?", new String[] {itemid+""});
        db.close();
    }



    //ContentValues 存储基本类型数据 int string 无法存储对象
    //Cursor 每行的集合

   /* public void readd(Context context){
        DbData db = new DbData(context);
        SQLiteDatabase dbread = db.getReadableDatabase();
        Cursor cs = dbread.query("user",null,null,null,null,null,null);
        while (cs.moveToNext()){
            String data1 = cs.getString(cs.getColumnIndex("title"));
            String data2 = cs.getString(cs.getColumnIndex("fenzu"));
            String data3 = cs.getString(cs.getColumnIndex("name"));
            String data4 = cs.getString(cs.getColumnIndex("password"));
            String data5 = cs.getString(cs.getColumnIndex("remark"));
            System.out.println(String.format("title=%s,fenzu=%s,name=%s,password=%s,remark=%s",data1,
                    data2,data3,data4,data5));
        }
    }*/
}

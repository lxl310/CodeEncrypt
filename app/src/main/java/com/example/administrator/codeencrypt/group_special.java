package com.example.administrator.codeencrypt;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import domain.AESUtils;
import domain.DbOperate;

/**
 * Created by Administrator on 2015/9/22.
 * 查看特定分组
 */
public class group_special extends Activity {
    @Bind(R.id.speciallist) ListView speciallist;
    private SimpleCursorAdapter adapter;
    DbOperate s = new DbOperate();
    AESUtils encrypt = new AESUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupspecial_aty);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String lie = intent.getStringExtra("lie");
       // System.out.println(lie+"bdknaksjsdkfnaskjfkd");
        adaptera(lie);
    }

    private void init() {
        speciallist = (ListView) findViewById(R.id.speciallist);
    }
    //刷新数据
    public void refreshListView(String data){
        Cursor c = s.readSpecial(group_special.this,data);
        adapter.changeCursor(c);
    }

    //adapter的数据绑定与解密
    private void adaptera(String data){
        adapter = new SimpleCursorAdapter(group_special.this,R.layout.special_group,s.read(group_special.this),new String[]{"title","gro","name","password","remark"},new int[]{R.id.titleeq,R.id.groq,
                R.id.nameeq,R.id.passworddq,R.id.remarkkq});

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.passworddq) {    //"gro"为数据库中对应的属性列
                    EditText tv = (EditText) view;
                    System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                    tv.setText(encrypt.decrypt("1", cursor.getString(cursor.getColumnIndex("password"))));
                    return true;
                }
                // System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                return false;
            }
        });
        speciallist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        refreshListView(data);
    }
}

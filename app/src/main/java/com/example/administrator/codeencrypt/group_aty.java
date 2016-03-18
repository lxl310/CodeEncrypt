package com.example.administrator.codeencrypt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.fuzen;

/**
 * Created by Administrator on 2015/9/21.
 */
public class group_aty extends Activity {

    @Bind(R.id.addgroup_btn) Button addgroup;
    @Bind(R.id.group_list) ListView grouplist;

   // private ListView grouplist;
    private ArrayAdapter<String> adapter;
   // public static List<String> listdata ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_aty);
        ButterKnife.bind(this);

        //只初始化一次
        databind();
        grouplist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(group_aty.this).setTitle("remind").setMessage("really delete").setNegativeButton("cancel", null).setPositiveButton("suer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = (String) parent.getItemAtPosition(position);
                        adapter.remove(adapter.getItem(position));
                        adapter.notifyDataSetChanged();
                    }
                }).show();
                return true;
            }
        });

        grouplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemname = ((TextView)view).getText().toString();
                System.out.println(itemname+"lddddddddddddddd");
                Intent z = new Intent(group_aty.this,group_special.class);
                z.putExtra("lie",itemname);
                startActivity(z);
            }
        });
    }

    @OnClick(R.id.addgroup_btn)
    public void addgroup_btn(){
        final EditText et = new EditText(group_aty.this);
        new AlertDialog.Builder(group_aty.this).setTitle("please write group name").setIcon(android.R.drawable.ic_dialog_info).setView(et).setPositiveButton("sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = et.getText().toString();
                fuzen.listdata.add(str);
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("cannel", null).show();

    }

    //此处应该从text文件读取
    public void databind(){
        if(fuzen.listdata.isEmpty() ==true ){
            fuzen.listdata.add(0, "null");
        }
        adapter = new ArrayAdapter<String>(group_aty.this,android.R.layout.simple_list_item_1,fuzen.listdata);
        //添加并且显示
        grouplist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //长点击事件
}

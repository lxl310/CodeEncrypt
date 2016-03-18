package com.example.administrator.codeencrypt;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;

import com.example.administrator.codeencrypt.SetPassword.SetPasswordActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import domain.AESUtils;
import domain.DbOperate;


public class MainActivity extends ListActivity {
    @Bind(R.id.add_btn) ImageButton add_btn;
    @Bind(R.id.pop_btn) ImageButton pop_btn;

    private Context mContext = null;
    private SimpleCursorAdapter adapter;
    DbOperate s = new DbOperate();
    AESUtils encrypt = new AESUtils();

    //������������



    //ɾ��
    private AdapterView.OnItemLongClickListener listviewitemlongclicklisten = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            new AlertDialog.Builder(MainActivity.this).setTitle("remind").setMessage("really delete").setNegativeButton("cancle", null).setPositiveButton("sure", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Cursor c = adapter.getCursor();
                    c.moveToPosition(position);
                    int itemid = c.getInt(c.getColumnIndex("_id"));

                  /*  s.updata(MainActivity.this,titlee.getText().toString(),gro.getText().toString(),namee.getText().toString(),
                            passwordd.getText().toString(), remarkk.getText().toString(),itemid);*/
                    s.delete(MainActivity.this, itemid);
                    refreshListView();
                }
            }).show();
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        //adapter�����ݰ������
        adaptera();
        getListView().setOnItemLongClickListener(listviewitemlongclicklisten);
    }

    @OnClick(R.id.add_btn)
    public void setAdd_btn(){
        Intent i = new Intent(MainActivity.this, add_message_aty.class);
        startActivity(i);

    }


    @OnClick(R.id.pop_btn)
    public void setPop_btn(View view){
        showPopupWindow(view);


    }



    //ˢ������
    public void refreshListView(){
        Cursor c = s.read(this);
        adapter.changeCursor(c);
    }

    //adapter�����ݰ������
    private void adaptera(){
        adapter = new SimpleCursorAdapter(this,R.layout.message_aty,s.read(this),new String[]{"title","gro","name","password","remark"},new int[]{R.id.titlee,R.id.gro,
                R.id.namee,R.id.passwordd,R.id.remarkk});

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.passwordd) {    //"gro"Ϊ���ݿ��ж�Ӧ��������
                    EditText tv = (EditText) view;
                    // System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                    tv.setText(encrypt.decrypt("1", cursor.getString(cursor.getColumnIndex("password"))));  //�����ݿ��е����ݳ���1000�Ժ�����ʾ
                    return true;
                }
                // System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                return false;
            }
        });
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        refreshListView();
    }

    //pop
    public void showPopupWindow(View view ){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_window,null);
        //���õ���¼�
        Button button = (Button) contentView.findViewById(R.id.group_look);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(MainActivity.this,group_aty.class);
                startActivity(j);
            }
        });
        Button button2 = (Button) contentView.findViewById(R.id.tvReset);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(MainActivity.this,SetPasswordActivity.class);
                startActivity(a);
            }
        });




        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //���ܲ�׽����Ŀռ�
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // �����������true�Ļ���touch�¼���������
                // ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
            }
        });

        // ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
        //���ñ���Ϊ����dismiss
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // ���úò���֮����show
        popupWindow.showAsDropDown(view);
    }




}

package com.example.administrator.codeencrypt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.fuzen;
import domain.DbOperate;

/**
 * Created by Administrator on 2015/9/17.
 */
public class add_message_aty extends Activity {

    @Bind(R.id.sure_btn) Button sure_btn;
    @Bind(R.id.titl) EditText title;
    @Bind(R.id.grop) EditText grou;
    @Bind(R.id.name) AutoCompleteTextView name;
    @Bind(R.id.password) AutoCompleteTextView password;
    @Bind(R.id.remark) EditText remark;
    @Bind(R.id.fenzu) Spinner spinner;
    private ArrayAdapter<String> adapter;
    DbOperate dbdata = new DbOperate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_message_aty);
        ButterKnife.bind(this);
        spinnerSet();
    }

    //ȷ�����
    @OnClick(R.id.sure_btn)
    public void setSure_btn(){
        dbdata.inserts(add_message_aty.this, title.getText().toString(), grou.getText().toString(), name.getText().toString(),
                password.getText().toString(), remark.getText().toString());
        //dbdata.inserts(add_message_aty.this,"hello","s","b","s","s");
        Intent i = new Intent(add_message_aty.this, MainActivity.class);
        startActivity(i);

    }
    //spinnerSET
    public void spinnerSet(){
        if(fuzen.listdata.isEmpty() ==true) {
            fuzen.listdata.add(0, "null");
        }
        //����ѡ������ArrayAdapter��������
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, fuzen.listdata);

        //���������б�ķ��
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //��adapter ��ӵ�spinner��
        spinner.setAdapter(adapter);

        //����¼�Spinner�¼�����
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        //����Ĭ��ֵ
        spinner.setVisibility(View.VISIBLE);
    }

    //ʹ��������ʽ����
    class SpinnerSelectedListener  implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            grou.setText(arg0.getSelectedItem().toString());

        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}


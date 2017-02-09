package com.li.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.li.common.R;
import com.li.common.widget.MyToolbar;

import java.util.ArrayList;

/**
 *
 */
public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<String> list;
    private String title = "选择";
    private ListView listView;
    private MyToolbar toolbar;
    public static final int SUCCESS_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        if (getIntent() != null) {
            list = getIntent().getStringArrayListExtra("data");
            title = getIntent().getStringExtra("title");
        }

        initView();
        toolbar.setCenterTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.item, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);


    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list_data);
        toolbar = (MyToolbar) findViewById(R.id.toolbar);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("item", list.get(position));
        setResult(SUCCESS_CODE, intent);
        finish();
    }
}

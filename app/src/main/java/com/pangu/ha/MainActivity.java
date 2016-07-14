package com.pangu.ha;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.mobeta.android.dslv.DragSortListView;

import java.util.ArrayList;
import java.util.List;

import bean.Bean;
import adapter.MyAdapter;


public class MainActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private List<Bean> mDatas = new ArrayList<Bean>();

    private DragSortListView listView;
    private MyAdapter customAdapter;
    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                        Bean item = (Bean) customAdapter.getItem(from);//得到listview的适配器
                        customAdapter.remove(from);//在适配器中”原位置“的数据。
                        customAdapter.insert(item, to);//在目标位置中插入被拖动的控件。
                    }
                }
            };

    private Button click_remove;
    private Bean bean;
    private CheckBox checkBox;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        click_remove = (Button) findViewById(R.id.click_remove);
        listView = (DragSortListView) findViewById(R.id.ListView);
        checkBox = (CheckBox) findViewById(R.id.mCheckBox);

        customAdapter = new MyAdapter(MainActivity.this);

        for (int i = 0; i < 30; i++) {
            bean = new Bean();
            bean.setChecked(false);
            bean.setName(i + "测试");
            bean.setAmbo(i + "SH");
            mDatas.add(bean);
        }
        customAdapter.setData(mDatas);
        listView.setAdapter(customAdapter);
        listView.setDropListener(onDrop);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDragEnabled(true); //设置是否可拖动。
        click_remove.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        ArrayList<Bean> checked = new ArrayList<>();
        for (Bean bean : mDatas) {
            bean.setChecked(b);
            checked.add(bean);
        }
        customAdapter.setData(mDatas);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.click_remove) {  //删除
            ArrayList<Bean> removeBeans = new ArrayList<>();
            for (Bean bean : mDatas) {
                if (bean.getChecked()) {
                    removeBeans.add(bean);
                }
            }
            mDatas.removeAll(removeBeans);
            customAdapter.setData(mDatas);
        }

    }
}
